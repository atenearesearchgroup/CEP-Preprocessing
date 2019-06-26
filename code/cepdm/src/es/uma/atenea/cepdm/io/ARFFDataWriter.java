package es.uma.atenea.cepdm.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceInformation;

/**
 * An ARFF file writer to be invoked by CEP subscribers
 * @author Aurora
 * @version 1
 * */

public class ARFFDataWriter {

	/** Singleton instance */
	private static ARFFDataWriter singleton = null;

	/** File writer */
	private Writer writer;
	
	/** A flag to indicate whether metadata has been already written */
	private boolean isMetadataWritten;
	
	/** The name of the output file */
	private String fileName;
	
	/**
	 * Private constructor
	 * */
	private ARFFDataWriter() {
		this.isMetadataWritten = false;
	}
	
	/**
	 * Method to get the unique instance of the writer
	 * */
	public static ARFFDataWriter getARFFDataWriter() {
		if(singleton == null)
			singleton = new ARFFDataWriter();
		return singleton;
	}

	/**
	 * Create output file
	 * @param fileName The file name
	 * */
	public void createFile(String fileName) {
		try {
			this.fileName = fileName;
			this.isMetadataWritten = false; // reset
			this.writer = new BufferedWriter(new FileWriter(this.fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To know if the metadata has been written
	 * @return True if metadata has been written, false otherwise
	 * */
	public boolean isMetadataWriten() {
		return this.isMetadataWritten;
	}
	
	/**
	 * Write metadata only
	 * @param metadata String with metadata information (header)
	 * */
	public void writeMetadata(String metadata) {
		try {
			String relation = this.fileName.substring(this.fileName.lastIndexOf("/")+1, this.fileName.indexOf(".arff"));
			this.writer.write("@relation " + relation + "\n");
			this.writer.write(metadata);
			this.isMetadataWritten = true;
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a new instance in the file
	 * @param metadata The instance information (attributes)
	 * @param instance The instance data
	 * */
	public void writeInstance(InstanceInformation metadata, Instance instance) {
		try {
			this.writer.write(instanceAsString(metadata, instance));
			this.writer.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close file
	 * */
	public void closeFile() {
		try {
			this.writer.close();
			this.isMetadataWritten = false; // reset
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the instance data as string
	 * @param metadata The instance information (attributes)
	 * @param instance The instance data
	 * @return A string with the instance data ready to be written
	 * */
	protected String instanceAsString(InstanceInformation metadata, Instance instance) {
		
		StringBuffer sb = new StringBuffer();
		int n = metadata.numAttributes();
		Attribute att;
		List<String> attValues;
		
		for(int i=0; i<n-1; i++) {
			att = metadata.attribute(i);
			if(att.isNumeric()) {
				if(Double.isNaN(instance.value(i)))
					sb.append("?,");
				else
					sb.append(instance.value(i) + ",");
			}
			else if(att.isNominal()) {
				attValues = att.getAttributeValues();
				sb.append(attValues.get((int)instance.value(i))+",");
			}
		}
		
		// Last value without comma
		att = metadata.attribute(n-1);
		if(att.isNumeric()) {
			if(Double.isNaN(instance.value(n-1)))
				sb.append("?");
			else
				sb.append(instance.value(n-1));
		}
		else if(att.isNominal()) {
			attValues = att.getAttributeValues();
			sb.append(attValues.get((int)instance.value(n-1)));
		}
		sb.append("\n");
		return sb.toString();
	}
}

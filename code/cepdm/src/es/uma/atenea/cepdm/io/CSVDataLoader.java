package es.uma.atenea.cepdm.io;

import java.io.File;

import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esperio.csv.AdapterInputSource;
import com.espertech.esperio.csv.CSVInputAdapter;


/**
 * A class to load events from data instances stored in CSV format
 * @author Aurora
 * @version 1
 * */

public class CSVDataLoader {

	/** The name of the file containing the dataset */
	private String fileName;
	
	/**
	 * Parameterized constructor
	 * @param fileName The name of the file
	 * */
	public CSVDataLoader(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Set the name of the file to read from
	 * @param fileName The name of the file
	 * */
	public void setDatasetFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Read the data from the file and put it into the provided runtime
	 * @param runtime The CEP runtime where the events will be sent
	 * @param eventTypeName The type of event to read from the file
	 * */
	public void loadEventsFromCsv(EPRuntime runtime, String eventTypeName) {
		File file = new File(this.fileName);
		AdapterInputSource source = new AdapterInputSource(file);
		CSVInputAdapter csvAdapter = new CSVInputAdapter(runtime, source, eventTypeName);
		csvAdapter.start();
	}
}

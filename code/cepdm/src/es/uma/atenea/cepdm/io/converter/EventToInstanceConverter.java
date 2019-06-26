package es.uma.atenea.cepdm.io.converter;

import java.util.List;

import com.yahoo.labs.samoa.instances.Attribute;
import com.yahoo.labs.samoa.instances.DenseInstance;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceImpl;
import com.yahoo.labs.samoa.instances.InstanceInformation;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import es.uma.atenea.cepdm.event.AbstractEvent;

/**
 * An abstract class to convert CEP events into MOA instances. This class creates the 
 * metadata information (attributes) and provides some methods to insert values.
 * To be refined by subclasses depending on the event types.
 * @author Aurora
 * @version 1
 * */

public abstract class EventToInstanceConverter {

	/** The event to be converted */
	protected AbstractEvent event;
	
	/** The attribute information */
	protected InstanceInformation metadata;
	
	protected Attribute [] attributes;
	
	/** The MOA instance */
	protected InstanceImpl instance;
	
	/**
	 * Create an instance from an event. Metadata will be created only once. 
	 * Then, a dense instance is created and filled. 
	 * */
	public Instance createInstanceFromEvent(AbstractEvent event) {
		this.event = event;
		if(this.metadata == null) {
			createMetadata();
		}
		
		this.instance = new DenseInstance(this.metadata.numAttributes());
		InstancesHeader header = new InstancesHeader(metadata);
		this.instance.setInstanceHeader(header);
		
		fillInstanceWithValues();
		return this.instance;
	}
	
	/**
	 * Get the instance information
	 * @return InstanceInformation object with metadata
	 * */
	public InstanceInformation getMetadata() {
		return this.metadata;
	}
	
	/**
	 * Get the attributes
	 * */
	public Attribute[] getAttributes() {
		return this.attributes;
	}
	
	/**
	 * Create metadata (attributes)
	 * */
	public void createMetadata() {
		this.metadata = new InstanceInformation();
		this.attributes = createAttributes();
		this.metadata.setAttributes(attributes);
		
		int classIndex = getClassAttributeIndex();
		if(classIndex != -1) {
			this.metadata.setClassIndex(classIndex);
		}
	}
	
	/**
	 * Provide the list of attributes (specific for the event type)
	 * @return An array of attributes
	 * */
	protected abstract Attribute [] createAttributes();
	
	/**
	 * Fill the instance with the values of the event object
	 * */
	public abstract void fillInstanceWithValues();
	
	/**
	 * Get the index of the class attribute
	 * @return The index of the attribute to be used for classification
	 * */
	public abstract int getClassAttributeIndex();
	
	/**
	 * Create a numerical attribute
	 * @param name Attribute name
	 * @return A MOA attribute
	 * */
	protected Attribute createNumericalAttribute(String name) {
		Attribute att = new Attribute(name);
		return att;
	}
	
	/**
	 * Create a categorical attribute
	 * @param name Attribute name
	 * @param categories List of possible values
	 * @return A MOA attribute
	 * */
	protected Attribute createCategoricalAttribute(String name, List<String> categories) {
		Attribute att = new Attribute(name, categories);
		return att;
	}
	
	/**
	 * Set the value of an numerical attribute
	 * @param index Attribute index
	 * @param value Attribute value
	 * */
	protected void setNumericalValue(int index, double value) {
		this.instance.setValue(index, value);
	}
	
	/**
	 * Set the value of a categorical attribute
	 * @param index Attribute index
	 * @param category Attribute category (internally converted to double value)
	 * */
	protected void setCategoricalValue(int index, String category) {
		double value = this.metadata.attribute(index).indexOfValue(category);
		this.instance.setValue(index, value);
	}
	
	/**
	 * Set the value of the class attribute
	 * @param index Class index
	 * @param value Class value
	 * */
	protected void setClassValue(int index, double classValue) {
		this.instance.setClassValue(index, classValue);
	}
	
	/**
	 * Obtain a string with the metadata information
	 * @return A string
	 * */
	public String getMetadataAsString() {
		StringBuffer bf = new StringBuffer();
		int n = this.metadata.numAttributes();
		Attribute att;
		for(int i=0; i<n; i++) {
			att = this.metadata.attribute(i);
			bf.append("@attribute " + att.name() + " ");
			if(att.isNumeric())
				bf.append("numeric");
			else if (att.isNominal()) {
				List<String> values = att.getAttributeValues();
				bf.append("{");
				for(int j=0; j<values.size()-1; j++) {
					bf.append(values.get(j)+",");
				}
				bf.append(values.get(values.size()-1) + "}");
			}
			bf.append("\n");
		}
		bf.append("\n@data\n");
		return bf.toString();
	}
}

package es.uma.atenea.cepdm.io;

import java.util.concurrent.TimeUnit;

import com.espertech.esper.client.EPRuntime;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.InstanceImpl;

import es.uma.atenea.cepdm.event.AbstractEvent;
import es.uma.atenea.cepdm.io.converter.InstanceToEventConverter;
import moa.core.TimingUtils;
import moa.streams.ArffFileStream;

/**
 * A class to load an ARFF dataset as a data stream and convert the instances to events.
 * @author Aurora
 * @version 1
 * */
public class ARFFDataLoader {

	/** A specific reader for ARFF files */
	protected ArffFileStream stream;
	
	/** The file name (dataset) */
	protected String fileName;

	/** Time required to load and create objects (for testing purposes) */
	protected long time = 0;
	
	/**
	 * Parameterized constructor
	 * @param fileName The dataset
	 * */
	public ARFFDataLoader(String fileName) {
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
	 * Configure the ARFF stream
	 * */
	public void configure() {
		this.stream = new ArffFileStream();
		this.stream.arffFileOption.setValue(this.fileName);
		this.stream.prepareForUse();
	}
	
	/**
	 * Load the instance, convert to appropriate event and send it to ESPER
	 * @param runtime EP runtime to receive events
	 * @param converter The specific instance-event converter
	 * */
	public void loadInstancesAsEvents(EPRuntime runtime, InstanceToEventConverter converter) {
		Instance instance;
		AbstractEvent event;
		long startTime, endTime;
		while(this.stream.hasMoreInstances()){
			TimingUtils.enablePreciseTiming();
			startTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
			instance = this.stream.nextInstance().getData();
			event = converter.createEventFromInstance((InstanceImpl)instance);
			endTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
			this.time += TimeUnit.NANOSECONDS.toMillis((endTime - startTime));
			runtime.sendEvent(event);
		}
	}
	
	/**
	 * Get the time required to load instances and create objects
	 * @return Time in milliseconds
	 * */
	public long getLoadTime() {
		return this.time;
	}
}

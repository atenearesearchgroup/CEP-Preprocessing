package es.uma.atenea.cepdm.service.room;

import es.uma.atenea.cepdm.io.ARFFDataLoader;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.io.converter.room.InstanceToSensorRoomConverter;
import es.uma.atenea.cepdm.service.CEPService;
import es.uma.atenea.cepdm.subscriber.room.*;

/**
 * A specific CEP service for room sensor events (demo program in main)
 * @author Aurora
 * @version 1
 * */

public class CEPServiceRoomOccupancy extends CEPService {

	/** The package with the types of event for this application */
	protected String eventType = "es.uma.atenea.cepdm.event.room";

	/**
	 * Default constructor
	 * */
	public CEPServiceRoomOccupancy() {
		// do nothing
	}

	@Override
	protected void configureEvents() {
		String eventType = "es.uma.atenea.cepdm.event.room";
		super.configuration.addEventTypeAutoName(eventType);
	}

	@Override
	protected void removeEvents() {
		super.provider.getEPAdministrator().getConfiguration().removeEventType(this.eventType, true);
	}

	/**
	 * Register the subscribers to replace missing data by estimated values
	 * @param windowSize Maximum number of samples for least squares estimation
	 * */
	public void registerEstimationStatements(int windowSize) {
		SensorRoomMissingEstimationSubscriber subscriber1 = new SensorRoomMissingEstimationSubscriber(windowSize);
		SensorRoomNoMissingEstimationSubscriber subscriber2 = new SensorRoomNoMissingEstimationSubscriber();
		SensorRoomClearEstimationSubscriber subscriber3 = new SensorRoomClearEstimationSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber1);
		super.registerStatementFromSubscriber(subscriber2);
		super.registerStatementFromSubscriber(subscriber3);
	}

	/**
	 * Register the subscribers to remove missing data
	 * */
	public void registerRemoveStatements() {
		SensorRoomRemoveMissingSubscriber subscriber = new SensorRoomRemoveMissingSubscriber();
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Register the subscribers to replace missing data by average values
	 * @param windowSize Window size for average function
	 * */
	public void registerAvgStatements(int windowSize) {
		SensorRoomMissingAvgSubscriber subscriber1 = new SensorRoomMissingAvgSubscriber(windowSize);
		SensorRoomNoMissingSubscriber subscriber2 = new SensorRoomNoMissingSubscriber();
		super.registerStatementFromSubscriber(subscriber1);
		super.registerStatementFromSubscriber(subscriber2);
	}

	/**
	 * Register the subscribers to replace missing data by last values
	 * */
	public void registerLastStatements() {
		SensorRoomMissingLastSubscriber subscriber1 = new SensorRoomMissingLastSubscriber();
		SensorRoomNoMissingSubscriber subscriber2 = new SensorRoomNoMissingSubscriber();
		super.registerStatementFromSubscriber(subscriber1);
		super.registerStatementFromSubscriber(subscriber2);
	}

	/**
	 * Demo program
	 * */
	public static void main(String[] args) {

		int windowSize = 30;
		CEPServiceRoomOccupancy service = new CEPServiceRoomOccupancy();
		service.configure();
	
		// Estimation of missing values
		service.registerEstimationStatements(windowSize);
		
		// Create the ARFF writer, so it is ready to receive events
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		writer.createFile("datasets/occupancy-randomMissing-5-estimated.arff");

		// Load events from ARFF dataset
		String fileName = "datasets/occupancy-randomMissing-5.arff";
		
		// Use 'true' with remove missing subscriber, false otherwise
		InstanceToSensorRoomConverter converter = new InstanceToSensorRoomConverter(false);
		ARFFDataLoader reader = new ARFFDataLoader(fileName);
		reader.configure();
		reader.loadInstancesAsEvents(service.getRuntime(), converter);

		// Free resources
		writer.closeFile();
		service.destroy();
	}
}

package es.uma.atenea.cepdm.service.electricity;

import es.uma.atenea.cepdm.io.ARFFDataLoader;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.io.converter.electricity.InstanceToElectricityConverter;
import es.uma.atenea.cepdm.service.CEPService;
import es.uma.atenea.cepdm.subscriber.electricity.*;

/**
 * A specific CEP service for electricity events (demo program in main)
 * @author Aurora
 * @version 1
 * */
public class CEPServiceElectricity extends CEPService {

	/** The package with the types of event for this application */
	protected String eventType = "es.uma.atenea.cepdm.event.electricity";
	
	/**
	 * Empty constructor
	 * */
	public CEPServiceElectricity() {
		super();
	}

	/**
	 * Get the event type
	 * @return String with the path to the event definition
	 * */
	public String getEventType() {
		return this.eventType;
	}
	
	/**
	 * Register the subscriber to reduce electricity instances with average price and demand
	 * @param windowSize Window size for aggregation function
	 * */
	public void registerReducedAvgStatements(int windowSize) {
		ElectricityReducedAvgSubscriber subscriber = new ElectricityReducedAvgSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);	
	}
	
	/**
	 * Register the subscriber to enrich electricity instances with average price and demand
	 * @param windowSize Window size for aggregation function
	 * */
	public void registerEnrichedAvgStatements(int windowSize) {
		ElectricityEnrichedAvgSubscriber subscriber = new ElectricityEnrichedAvgSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}
	
	/**
	 * Register the subscriber to reduce electricity instances with min/max price and demand
	 * @param windowSize Window size for aggregation function
	 * */
	public void registerReducedMinMaxStatements(int windowSize) {
		ElectricityReducedMinMaxSubscriber subscriber = new ElectricityReducedMinMaxSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}
	
	/**
	 * Register the subscriber to enrich electricity instances with min/max price and demand
	 * @param windowSize Window size for aggregation function
	 * */
	public void registerEnrichedMinMaxStatements(int windowSize) {
		ElectricityEnrichedMinMaxSubscriber subscriber = new ElectricityEnrichedMinMaxSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
				
	}
	
	@Override
	protected void configureEvents() {
		String eventType = "es.uma.atenea.cepdm.event.electricity";
		super.configuration.addEventTypeAutoName(eventType); 	
	}
	
	@Override
	protected void removeEvents() {
		super.provider.getEPAdministrator().getConfiguration().removeEventType(this.eventType, true);
	}
	
	/**
	 * Demo program
	 * */
	public static void main(String[] args) {
	
		// Create the service
		int windowSize = 1000;
		CEPServiceElectricity service = new CEPServiceElectricity();
		service.configure();
		service.registerReducedMinMaxStatements(windowSize);
		
		// Create the ARFF writer, so it is ready to receive events
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		writer.createFile("datasets/electricity-reduced-minmax-"+windowSize+".arff");
				
		// Load events from ARFF dataset
		String fileName = "datasets/elecNormNew.arff";
		InstanceToElectricityConverter converter = new InstanceToElectricityConverter();
		ARFFDataLoader reader = new ARFFDataLoader(fileName);
		reader.configure();
		reader.loadInstancesAsEvents(service.getRuntime(), converter);
		
		// Free resources
		writer.closeFile();
		service.destroy();
	}
}

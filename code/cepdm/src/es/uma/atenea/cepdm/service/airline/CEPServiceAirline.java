package es.uma.atenea.cepdm.service.airline;

import es.uma.atenea.cepdm.io.ARFFDataLoader;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.io.converter.airline.InstanceToFlightConverter;
import es.uma.atenea.cepdm.service.CEPService;
import es.uma.atenea.cepdm.subscriber.airline.*;

/**
 * A specific CEP service for flight events (demo program in main)
 * @author Aurora
 * @version 1
 * */

public class CEPServiceAirline extends CEPService {

	/** The package with the types of event for this application */
	protected String eventType = "es.uma.atenea.cepdm.event.airline";

	/**
	 * Empty constructor
	 * */
	public CEPServiceAirline() {
		// do nothing
	}

	@Override
	protected void configureEvents() {
		String eventType = "es.uma.atenea.cepdm.event.airline";
		super.configuration.addEventTypeAutoName(eventType);
	}

	@Override
	protected void removeEvents() {
		super.provider.getEPAdministrator().getConfiguration().removeEventType(this.eventType, true);
	}

	/**
	 * Register the subscriber to count departure airport delays
	 * @param windowSize Window size for count function
	 * */
	public void registerAirportToDelayStatements(int windowSize) {
		FlightAirportToDelaySubscriber subscriber = new FlightAirportToDelaySubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Register the subscriber to count arrival airport delays
	 * @param windowSize Window size for count function
	 * */
	public void registerAirportFromDelayStatements(int windowSize) {
		FlightAirportFromDelaySubscriber subscriber = new FlightAirportFromDelaySubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Register the subscriber to count delays depending on the day of the week
	 * @param windowSize Window size for count function
	 * */
	public void registerDayOfWeekDelayStatements(int windowSize) {
		FlightWeekDelaySubscriber subscriber = new FlightWeekDelaySubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Register the subscriber to count airline delays
	 * @param windowSize Window size for count function
	 * */
	public void registerAirlineDelayStatements(int windowSize) {
		FlightAirlineDelaySubscriber subscriber = new FlightAirlineDelaySubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Register the subscriber to count delays for all attributes
	 * @param windowSize Window size for count function
	 * */
	public void registerFourCountersDelayStatements(int windowSize) {
		FlightFourDelaySubscriber subscriber = new FlightFourDelaySubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	/**
	 * Demo program
	 * */
	public static void main(String[] args) {

		String dir = "datasets";
		int windowSize = 1000;
		
		// Create the service
		CEPServiceAirline service = new CEPServiceAirline();
		service.configure();
		service.registerFourCountersDelayStatements(windowSize);

		// Create the ARFF writer, so it is ready to receive events
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		writer.createFile(dir+"/airlines-countFourDelays-" + windowSize + ".arff");

		// Load events from ARFF dataset
		String fileName = dir + "/airlines.arff";
		InstanceToFlightConverter converter = new InstanceToFlightConverter();
		ARFFDataLoader reader = new ARFFDataLoader(fileName);
		reader.configure();
		reader.loadInstancesAsEvents(service.getRuntime(), converter);

		// Free resources
		writer.closeFile();
		service.destroy();
	}
}

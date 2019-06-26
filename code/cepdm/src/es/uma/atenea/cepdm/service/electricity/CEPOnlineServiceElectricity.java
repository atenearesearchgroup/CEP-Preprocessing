package es.uma.atenea.cepdm.service.electricity;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;

import es.uma.atenea.cepdm.io.ARFFDataLoader;
import es.uma.atenea.cepdm.io.converter.electricity.ElectricityEnrichedAvgToInstanceConverter;
import es.uma.atenea.cepdm.io.converter.electricity.InstanceToElectricityConverter;
import es.uma.atenea.cepdm.learning.classification.ExperimentHoeffingTreeThread;
import es.uma.atenea.cepdm.service.CEPOnlineService;
import es.uma.atenea.cepdm.subscriber.electricity.ElectricityEnrichedAvgOnlineSubscriber;
import es.uma.atenea.cepdm.subscriber.electricity.ElectricityEnrichedAvgSubscriber;
import moa.core.InstanceExample;

/**
 * A specific CEP service for electricity events (online version)
 * @author Aurora
 * @version 1
 * */

public class CEPOnlineServiceElectricity extends CEPOnlineService{

	/** The package with the types of event for this application */
	protected String eventType = "es.uma.atenea.cepdm.event.electricity";

	/**
	 * Parameterized constructor
	 * @param queue Queue of incoming instances
	 * @param header Instance metadata
	 * */
	public CEPOnlineServiceElectricity(BlockingQueue<InstanceExample> queue, InstancesHeader header) {
		super(queue, header);
	}

	/**
	 * Get the event type
	 * @return String with the path to the event definition
	 * */
	public String getEventType() {
		return this.eventType;
	}

	/**
	 * Register the subscriber to enrich electricity instances with average attributes
	 * @param windowSize Window size for count function
	 * */
	public void registerStatements(int windowSize) {
		ElectricityEnrichedAvgSubscriber subscriber = new ElectricityEnrichedAvgSubscriber(windowSize);
		super.registerStatementFromSubscriber(subscriber);
	}

	@Override
	public void prepareLearningProcess() {
		this.experiment = new ExperimentHoeffingTreeThread(getQueue(),getDataHeader());
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

		// Create the queue to share the instances between subscribers and learner
		BlockingQueue<InstanceExample> queue = new LinkedBlockingQueue<InstanceExample>();

		// Create the header
		ElectricityEnrichedAvgToInstanceConverter converter = ElectricityEnrichedAvgToInstanceConverter.getConverter();
		converter.createMetadata();
		InstancesHeader header = new InstancesHeader(new Instances("Electricity", converter.getAttributes(), 0));
		header.setClassIndex(converter.getClassAttributeIndex());
		
		// Create the service
		CEPOnlineServiceElectricity service = new CEPOnlineServiceElectricity(queue,header);
		service.configure();

		// Register the online subscriber
		int windowSize = 10;
		ElectricityEnrichedAvgOnlineSubscriber subscriber = new ElectricityEnrichedAvgOnlineSubscriber(queue,windowSize);
		service.registerStatementFromSubscriber(subscriber);

		try {
			
			// Initialize learning process
			service.prepareLearningProcess();
			service.initializeLearningProcess();
			
			// Load events from ARFF dataset
			String fileName = "datasets/elecNormNew.arff";
			InstanceToElectricityConverter inputConverter = new InstanceToElectricityConverter();
			ARFFDataLoader reader = new ARFFDataLoader(fileName);
			reader.configure();
			reader.loadInstancesAsEvents(service.getRuntime(), inputConverter);

		}catch(Exception e) {
			// Free resources
			service.destroy();
		}
	}
}

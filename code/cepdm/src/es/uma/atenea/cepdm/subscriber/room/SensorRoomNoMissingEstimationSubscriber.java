package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.function.LeastSquaresEstimation;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events without missing values. The event is converted to instance 
 * and used to update the samples of the estimator based on least squares.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomNoMissingEstimationSubscriber implements StatementSubscriber {

	/**
	 * Default constructor
	 * */
	public SensorRoomNoMissingEstimationSubscriber() {
		super();
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select * from SensorRoom where (temperature is not null)";
		return expression;
	}
	
	/**
	 * Update method (listener)
	 * @param roomEvent Complete event
	 * */
	public void update(SensorRoom roomEvent) {
		// Update the estimation model with a new sample
		LeastSquaresEstimation.addObservation(
				roomEvent.getTemperature(), 
				roomEvent.getHumidity(), 
				roomEvent.getLight(),
				roomEvent.getCO2(),
				roomEvent.getHumidityRatio());
		
		// Save event as instance
		Instance instance = roomEvent.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(roomEvent.getConverter().getMetadataAsString());
		}
		writer.writeInstance(roomEvent.getConverter().getMetadata(), instance);
	}
}

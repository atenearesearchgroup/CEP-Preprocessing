package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.function.LeastSquaresEstimation;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events with missing values and 
 * replace them with an estimation based on least squares. The class
 * value is set to the most frequent value.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomMissingEstimationSubscriber implements StatementSubscriber {
	
	/** Window size */
	protected int windowSize;
	
	/**
	 * Default constructor
	 * */
	public SensorRoomMissingEstimationSubscriber() {
		super();
		this.windowSize = 10;
	}

	/**
	 * Parameterized constructor
	 * @param windowSize Window size (number of samples) for estimation
	 * */
	public SensorRoomMissingEstimationSubscriber(int windowSize) {
		super();
		this.windowSize = windowSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select timestamp, "
				+ "(select window(occupancy).mostFrequent() from SensorRoom#length("+this.windowSize+")) "
				+ "from SensorRoom where (temperature is null)";
		return expression;
	}

	/**
	 * Update method (listener)
	 * @param timestamp Event timestamp
	 * @param classPrediction The most frequent class value
	 * */
	public void update(double timestamp, String classPrediction) {
		// Get the estimation of sensor measurements
		double [] estimation = LeastSquaresEstimation.getEstimation();

		// Check that the estimation is valid (it could happen that the estimator has been reset or the error is excessive)
		if(isValidEstimation(estimation)) {
			
			LeastSquaresEstimation.addObservation(estimation[0], estimation[1], estimation[2], estimation[3], estimation[4]);

			// Create event
			SensorRoom roomEvent = new SensorRoom();
			roomEvent.setTemperature(estimation[0]);
			roomEvent.setHumidity(estimation[1]);
			roomEvent.setLight(estimation[2]);
			roomEvent.setCO2(estimation[3]);
			roomEvent.setHumidityRatio(estimation[4]);
			roomEvent.setOccupancy(classPrediction);

			// Convert to instance
			Instance instance = roomEvent.toMOAInstance();
			ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
			if(!writer.isMetadataWriten()) {
				writer.writeMetadata(roomEvent.getConverter().getMetadataAsString());
			}
			writer.writeInstance(roomEvent.getConverter().getMetadata(), instance);
		}
	}
	
	/**
	 * Check that the result of the estimation is valid for all attributes
	 * @param estimation Estimation of sensor measurements
	 * @return True if the estimation is valid, false otherwise
	 * */
	protected boolean isValidEstimation(double [] estimation) {
		return (!Double.isNaN(estimation[0]) && !Double.isNaN(estimation[1]) && 
				!Double.isNaN(estimation[2]) && !Double.isNaN(estimation[3]) && !Double.isNaN(estimation[4]));
	}
}

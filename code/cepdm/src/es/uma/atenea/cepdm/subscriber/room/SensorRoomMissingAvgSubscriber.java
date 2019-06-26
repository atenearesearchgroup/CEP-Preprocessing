package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events with missing values and 
 * replace them with the average within a window. The class
 * value is set to the most frequent value.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomMissingAvgSubscriber implements StatementSubscriber {

	/** Window size */
	protected int windowSize;

	/**
	 * Default constructor
	 * */
	public SensorRoomMissingAvgSubscriber() {
		super();
		this.windowSize = 10;
	}

	/**
	 * Parameterized constructor
	 * @param windowSize Window size for avg operator
	 * */
	public SensorRoomMissingAvgSubscriber(int windowSize) {
		super();
		this.windowSize = windowSize;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select "
				+ "(select avg(temperature) from SensorRoom#length("+this.windowSize+") where (temperature is not null)), "
				+ "(select avg(humidity) from SensorRoom#length("+this.windowSize+") where (humidity is not null)), "
				+ "(select avg(light) from SensorRoom#length("+this.windowSize+") where (light is not null)), " 
				+ "(select avg(CO2) from SensorRoom#length("+this.windowSize+") where (CO2 is not null)), "
				+ "(select avg(humidityRatio) from SensorRoom#length("+this.windowSize+") where (humidityRatio is not null)), "
				+ "(select window(occupancy).mostFrequent() from SensorRoom#length("+this.windowSize+") where (occupancy is not null)) "
				+ "from SensorRoom where (temperature is null)";
		return expression;
	}

	/**
	 * Update method (listener)
	 * @param temperature Average temperature
	 * @param humidity Average humidity
	 * @param light Average light
	 * @param co2 Average CO2
	 * @param humidityRatio Average humidity ratio
	 * @param classPrediction The most frequent class value
	 * */
	public void update(double temperature, double humidity, double light, double co2, double humidityRatio, String classPrediction) {

		// Create event
		SensorRoom roomEvent = new SensorRoom();
		roomEvent.setTemperature(temperature);
		roomEvent.setHumidity(humidity);
		roomEvent.setLight(light);
		roomEvent.setCO2(co2);
		roomEvent.setHumidityRatio(humidityRatio);
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

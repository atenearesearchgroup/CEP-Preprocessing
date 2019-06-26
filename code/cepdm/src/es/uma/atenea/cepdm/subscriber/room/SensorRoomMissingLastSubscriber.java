package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events with missing values and 
 * replace them with last available value.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomMissingLastSubscriber implements StatementSubscriber {

	/**
	 * Default constructor
	 * */
	public SensorRoomMissingLastSubscriber() {
		super();
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select "
				+ "(select window(temperature).lastOf() from SensorRoom#keepall where (temperature is not null)), "
				+ "(select window(humidity).lastOf() from SensorRoom#keepall where (humidity is not null)), "
				+ "(select window(light).lastOf() from SensorRoom#keepall where (light is not null)), " 
				+ "(select window(CO2).lastOf() from SensorRoom#keepall where (CO2 is not null)), "
				+ "(select window(humidityRatio).lastOf() from SensorRoom#keepall where (humidityRatio is not null)), "
				+ "(select window(occupancy).lastOf() from SensorRoom#keepall where (occupancy is not null)) "
				+ "from SensorRoom where (temperature is null)";
		return expression;
	}

	/**
	 * Update method (listener)
	 * @param temperature Last temperature value
	 * @param humidity Last humidity value
	 * @param light Last light value
	 * @param co2 Last CO2 value
	 * @param humidityRatio Last humidity ratio value
	 * @param classPrediction Last class value value
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

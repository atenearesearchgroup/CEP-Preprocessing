package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events whose temperature attribute contains missing values.
 * The event is simply converted to instance.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomNoMissingSubscriber implements StatementSubscriber {
	
	/**
	 * Default constructor
	 * */
	public SensorRoomNoMissingSubscriber() {
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

		// Save event as instance
		Instance instance = roomEvent.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(roomEvent.getConverter().getMetadataAsString());
		}
		writer.writeInstance(roomEvent.getConverter().getMetadata(), instance);
	}
}

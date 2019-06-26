package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;
import com.yahoo.labs.samoa.instances.Instance;

import es.uma.atenea.cepdm.event.room.SensorRoom;
import es.uma.atenea.cepdm.io.ARFFDataWriter;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that captures events without missing values. It works when missing data
 * is introduced in the flow as null events, i.e. the whole event is missing.
 * The event is simply converted to instance.
 * @author Aurora
 * @version 1
 * */

public class SensorRoomRemoveMissingSubscriber implements StatementSubscriber {

	/**
	 * Default constructor
	 * */
	public SensorRoomRemoveMissingSubscriber() {
		super();
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select * from SensorRoom where exists (select * from SensorRoom#length(1))";
		return expression;
	}

	/**
	 * Update method (listener)
	 * @param roomEvent The complete event
	 * */
	public void update(SensorRoom roomEvent) {
		Instance instance = roomEvent.toMOAInstance();
		ARFFDataWriter writer = ARFFDataWriter.getARFFDataWriter();
		if(!writer.isMetadataWriten()) {
			writer.writeMetadata(roomEvent.getConverter().getMetadataAsString());
		}
		writer.writeInstance(roomEvent.getConverter().getMetadata(), instance);
	}
}

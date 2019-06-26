package es.uma.atenea.cepdm.subscriber.room;

import com.espertech.esper.client.Configuration;

import es.uma.atenea.cepdm.function.LeastSquaresEstimation;
import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * A subscriber that periodically clean previous data for least square estimation
 * @author Aurora
 * @version 1
 * */
public class SensorRoomClearEstimationSubscriber implements StatementSubscriber {

	/** Clean frequency */
	protected int cleanFrequency;
	
	/**
	 * Parameterized constructor
	 * @param cleanFrequency Clean frequency
	 * */
	public SensorRoomClearEstimationSubscriber(int cleanFrequency) {
		super();
		this.cleanFrequency = cleanFrequency;
	}

	@Override
	public String getStatement() {
		Configuration configuration = new Configuration();
		configuration.addImport("es.uma.atenea.cepdm.event.room.*");
		String expression = "select count(*) from SensorRoom#length_batch("+this.cleanFrequency+")";
		// An alternative with seconds instead of number of events
		//String expression = "select count(*) from SensorRoom#time_batch(" + this.cleanFrequency + " sec)";
		return expression;
	}
	
	/**
	 * Update method (listener)
	 * Invoke cleaning method in the estimation at the given frequency
	 * */
	public void update(long count) {
		// Clear counters
		if(count == this.cleanFrequency) {
			LeastSquaresEstimation.clearData();
		}
	}
}

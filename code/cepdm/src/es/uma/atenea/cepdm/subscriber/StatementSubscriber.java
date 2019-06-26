package es.uma.atenea.cepdm.subscriber;

/**
 * Just a convenience interface to let us easily contain the Esper statements with the Subscribers -
 * just for clarity so it's easy to see the statements the subscribers are registered against.
 * @author Nathalie
 * @version 1
 */
public interface StatementSubscriber {
	
	/**
     * Get the EPL Statement the Subscriber will listen to.
     * @return EPL Statement
     */
	public String getStatement();
}

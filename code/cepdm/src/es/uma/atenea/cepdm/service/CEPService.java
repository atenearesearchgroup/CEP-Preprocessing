package es.uma.atenea.cepdm.service;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import es.uma.atenea.cepdm.subscriber.StatementSubscriber;

/**
 * The general CEP service
 * @author Aurora
 * @version 1
 * */

public abstract class CEPService {

	/** The configuration object */
	protected Configuration configuration;
	
	/** The service provider */
	protected EPServiceProvider provider;
	
	/**
	 * Empty construction. It creates a default provider
	 * with a configuration object with the required events.
	 * @see configureEvents
	 * */
	public CEPService() {
		// do nothing
	}
		
	public void configure() {
		this.configuration = new Configuration();
		configureEvents();
		this.provider = EPServiceProviderManager.getDefaultProvider(this.configuration);
		this.provider.initialize();
	}
	
	/**
	 * Register a simple statement (query)
	 * @param statement The query in EPL syntax
	 * */
	public void registerStatement(String statement) {
		this.provider.getEPAdministrator().createEPL(statement);
	}
	
	/**
	 * Register a statement (query) and add a subscriber to it
	 * @param subscriber The subscriber, which contains the statement
	 * */
	public void registerStatementFromSubscriber(StatementSubscriber subscriber) {
		EPStatement statement = this.provider.getEPAdministrator().createEPL(subscriber.getStatement());
		statement.setSubscriber(subscriber);
	}
	
	/**
	 * Get the EP runtime
	 * */
	public EPRuntime getRuntime() {
		return this.provider.getEPRuntime();
	}
	
	/**
	 * Destroy all statements and events currently registered in the runtime
	 * @see removeEvents
	 * */
	public void destroy() {
		this.provider.getEPAdministrator().destroyAllStatements();
		removeEvents();
	}
	
	// Abstract methods
	
	/**
	 * Register the specific events in the configuration object.
	 * To be done by subclasses
	 * */
	protected abstract void configureEvents();
	
	/**
	 * Remove the specific events from the service provider.
	 * To be done by subclasses
	 * */
	protected abstract void removeEvents();
}

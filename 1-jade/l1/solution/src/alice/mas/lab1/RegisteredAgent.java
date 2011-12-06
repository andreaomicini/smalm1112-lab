/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * This class models an agent which registers himself to the DF service.
 * 
 * @author Danilo Pianini
 * @version 20110926
 * 
 */
public abstract class RegisteredAgent extends Agent {

	private static final long serialVersionUID = -4719347252386324974L;
	private final String serviceType, serviceName;

	/**
	 * Default constructor, to be called by subclasses.
	 * 
	 * @param serviceType
	 *            the service the agent will offer
	 * @param serviceName
	 *            the application name. For MAS Laboratory, set it to "MASLab"
	 */
	public RegisteredAgent(String serviceType, String serviceName) {
		super();
		this.serviceType = serviceType;
		this.serviceName = serviceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		final DFAgentDescription agd = new DFAgentDescription();
		agd.setName(getAID());
		final ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType);
		sd.setName(serviceName);
		agd.addServices(sd);
		try {
			DFService.register(this, agd);
			System.out.println(this + ": has been registered as " + serviceType);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.Agent#takeDown()
	 */
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLocalName();
	}

}

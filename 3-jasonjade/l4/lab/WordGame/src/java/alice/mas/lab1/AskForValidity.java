/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import java.util.StringTokenizer;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Danilo Pianini
 * @version 20111102
 */
public class AskForValidity extends CyclicBehaviour {

	private static final long serialVersionUID = -2094385720464037161L;
	private final Broker b;

	public AskForValidity(Broker b) {
		super();
		this.b = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate
				.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null && msg.getContent() != null) {
			log("Received cheating info request: " + msg.getContent());
			final String content = msg.getContent().replace("property(", "")
					.replace(")", "");
			final StringTokenizer tk = new StringTokenizer(content, " ,");
			if (tk.hasMoreElements()) {
				final String expAID = tk.nextToken();
				if (tk.hasMoreElements()) {
					final String word = tk.nextToken();
					final AID aid = b.checkValid(word);
					ACLMessage reply = msg.createReply();
					if (aid != null && aid.getLocalName().equals(expAID)) {
						reply.setPerformative(ACLMessage.CONFIRM);
						reply.setContent("property("+aid.getLocalName()+","+word+")");
						log(aid + " has word " + word);
					} else {
						reply.setPerformative(ACLMessage.DISCONFIRM);
						reply.setContent("He's a cheater. Kill him.");
						log("Someone tries to piss everybody off");
					}
					myAgent.send(reply);
				}
			}
		} else {
			block();
		}
	}

	private void log(Object s) {
		System.out.println(myAgent + ": " + s);
	}
}

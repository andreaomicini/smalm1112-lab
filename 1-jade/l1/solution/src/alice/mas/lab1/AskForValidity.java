/**
 * 
 */
package alice.mas.lab1;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Danilo Pianini
 * @version 20111005
 */
public class AskForValidity extends CyclicBehaviour {

	private static final long serialVersionUID = -2094385720464037161L;
	private final Broker b;
	
	
	public AskForValidity(Broker b){
		super();
		this.b = b;
	}

	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM_IF);
		ACLMessage msg = myAgent.receive(mt);
		if(msg!= null){
			String word = msg.getContent();
			if(word!=null){
				 AID aid = b.checkValid(word);
				 ACLMessage reply = msg.createReply();
				 if(aid != null){
					 reply.setPerformative(ACLMessage.CONFIRM);
					 reply.setContent(aid.toString());
					 System.out.println(aid+" has word "+word);
				 } else {
					 reply.setPerformative(ACLMessage.DISCONFIRM);
					 reply.setContent("He's a cheater. Kill him.");
					 System.out.println(aid+" tries to piss everybody off");
				 }
			}
		} else {
			block();
		}
	}

}

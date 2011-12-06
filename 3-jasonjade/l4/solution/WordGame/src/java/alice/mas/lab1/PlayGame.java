/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author Danilo Pianini
 * @version 20110926
 *
 */
public class PlayGame extends CyclicBehaviour {

	private static final long serialVersionUID = 9119628968846882932L;
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(StaticUtils.GAME);
	private final Player myAgent;

	public PlayGame(Player a) {
		super(a);
		this.myAgent = (Player)(super.myAgent);
	} 
	
	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		ACLMessage msg = myAgent.receive(mt);
		if(msg!=null){
			final AID oppositor = msg.getSender();
			final String lastSent = myAgent.getLastSent(msg.getSender());
			final String response = msg.getContent();
			System.out.println(myAgent+" received word "+response);
			if(lastSent != null){
				final String toMatch = lastSent.substring(lastSent.length()-1, lastSent.length());
				final String check = response.substring(0, 1);
				if(!toMatch.equals(check)){
					System.out.println(myAgent+" says: "+check+" differs from "+toMatch+"!");
					System.out.println(myAgent+" says: "+oppositor.getLocalName()+" is a cheater! I won't play with him any longer!");
					ACLMessage fck = msg.createReply();
					fck.setPerformative(StaticUtils.GAME);
					fck.setContent("* You f↓¢ĸ→ñŋ cheater! *");
					myAgent.send(fck);
					return;
				}
			}
			ACLMessage word_msg;
			try {
				word_msg = StaticUtils.askForWord(myAgent, response.substring(response.length()-1, response.length()));
				if(word_msg.getPerformative() == StaticUtils.REJECT){
					System.out.println(myAgent+" received: "+msg.getContent());
					System.out.println(myAgent+" cries and dies.");
					myAgent.doDelete();
				} else {
					final String word = word_msg.getContent();
					ACLMessage pong = msg.createReply();
					pong.setPerformative(StaticUtils.GAME);
					pong.setContent(word);
					System.out.println(myAgent+" replies with "+word);
					myAgent.setOpponentLastSent(oppositor, word);
					myAgent.send(pong);
				}
			} catch (FIPAException e) {
				e.printStackTrace();
			}
		} else {
			block();
		}
	}

}

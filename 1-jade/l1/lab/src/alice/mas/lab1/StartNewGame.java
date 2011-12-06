/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Set;

/**
 * Search for other players, if found, play with them.
 * 
 * @author Danilo Pianini
 * @version 20110926
 */
public class StartNewGame extends SimpleBehaviour {

	private static final long serialVersionUID = -3478925536348257778L;
	private final Player myAgent;

	public StartNewGame(Player a) {
		super(a);
		this.myAgent = (Player)(super.myAgent);
	} 

	/* (non-Javadoc)
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	public void action() {
		try {
			final AID[] plrs = StaticUtils.searchServiceProviders(myAgent, "player");
			final ArrayList<AID> players = new ArrayList<AID>(plrs.length);
			final Set<AID> games = myAgent.getCurrentGames();
			for(AID aid: plrs){
				if(!games.contains(aid) && !aid.equals(myAgent.getAID()))
					players.add(aid);
			}
			System.out.println(myAgent+": found "+players.size()+" new players!");
			for(AID opponent: players){
				final ACLMessage msg = StaticUtils.askForWord(myAgent, StaticUtils.randomASCIIchar());
				if(msg.getPerformative() == StaticUtils.REJECT){
					System.out.println(myAgent+" received: "+msg.getContent());
					myAgent.doDelete();
				} else {
					final String word = msg.getContent();
					System.out.println(myAgent+" sending "+word+" to "+opponent.getLocalName());
					myAgent.setOpponentLastSent(opponent,word);
					StaticUtils.sendMessage(myAgent, StaticUtils.GAME, opponent, word);
				}
			}
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean done() {
		return true;
	}

}

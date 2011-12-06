/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import jade.core.AID;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A player for the word game
 * 
 * @author Danilo Pianini
 * @version 20110926
 * 
 */
public class Player extends RegisteredAgent {

	private static final long serialVersionUID = -3970467687140152104L;
	private final ConcurrentHashMap<AID, String> games = new ConcurrentHashMap<AID, String>();

	public Player() {
		super("player", "MASLab");
	}
	
	/* (non-Javadoc)
	 * @see alice.mas.lab1.RegisteredAgent#setup()
	 */
	@Override
	public void setup(){
		super.setup();
		addBehaviour(new StartNewGame(this));
		addBehaviour(new PlayGame(this));
	}
	
	/**
	 * @return the list of the players this agent played with
	 */
	public Set<AID> getCurrentGames(){
		return games.keySet();
	}

	/**
	 * Sets the last word i sent to an opponent, in order to avoid cheating.
	 * 
	 * @param opponent
	 * @param word
	 */
	public void setOpponentLastSent(AID opponent, String word) {
		games.put(opponent, word);
	}
	
	/**
	 * Gets the last word this agent sent to some opponent
	 * 
	 * @param opponent
	 * @return
	 */
	public String getLastSent(AID opponent){
		return games.get(opponent);
	}

	/**
	 * Removes an oppositor from the list of the games
	 * 
	 * @param oppositor
	 */
	public void removeGame(AID oppositor) {
		games.remove(oppositor);
	}
	
}

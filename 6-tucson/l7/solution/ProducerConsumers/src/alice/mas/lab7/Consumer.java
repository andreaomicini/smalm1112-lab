/**
 * 
 */
package alice.mas.lab7;

import alice.logictuple.LogicTuple;

/**
 * @author Danilo Pianini
 * @version 20111204
 *
 */
public class Consumer extends BasicAgent {
	
	private final LogicTuple token = new LogicTuple("token");
	
	public Consumer(String address) {
		super("default@"+address);
	}

	@Override
	public void doAction() {
		in(token);
		say("Item comsumed");
	}
	
	

}

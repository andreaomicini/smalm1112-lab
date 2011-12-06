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
public class Producer extends BasicAgent {
	
	private final static LogicTuple token = new LogicTuple("token");
	
	public Producer(String address) {
		super("default@"+address);
	}

	@Override
	public void doAction() {
		out(token);
		say("Item produced");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

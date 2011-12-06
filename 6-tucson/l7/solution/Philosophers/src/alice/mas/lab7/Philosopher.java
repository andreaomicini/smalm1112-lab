/**
 * 
 */
package alice.mas.lab7;

import alice.logictuple.InvalidLogicTupleException;
import alice.logictuple.LogicTuple;

/**
 * @author Danilo Pianini
 *
 */
public class Philosopher extends BasicAgent {
	
	private LogicTuple chops;

	public Philosopher(int i, int j, String address) {
		super("table@"+address);
		try {
			chops = LogicTuple.parse("chops("+i+","+j+")");
		} catch (InvalidLogicTupleException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doAction() {
		eat();
		think();
	}

	private void think() {
		say("Thinking about...");
		someTime();
	}

	private void eat() {
		say("Searching for chops.");
		in(chops);
		say("Eating.");
		someTime();
		out(chops);
		say("Ate! Chops released.");
	}
	
	private void someTime(){
		try {
			Thread.sleep((long)(Math.random()*10000));
		} catch (InterruptedException e) {
		}
	}

	
	
}

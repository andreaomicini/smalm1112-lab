/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import java.io.IOException;

/**
 * Word seller agent
 * 
 * @author Danilo Pianini
 * @version 20110915
 * 
 */
public class Broker extends RegisteredAgent {

	private static final long serialVersionUID = -6529460255350534351L;

	public Broker() {
		super("broker", "MASLab");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see alice.mas.lab1.RegisteredAgent#setup()
	 */
	@Override
	protected void setup() {
		super.setup();
		try {
			addBehaviour(new SendWord());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

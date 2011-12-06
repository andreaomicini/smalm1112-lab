/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import jade.core.AID;

import java.io.IOException;
import java.util.HashMap;

/**
 * Word seller agent
 * 
 * @author Danilo Pianini
 * @version 20111005
 * 
 */
public class Broker extends RegisteredAgent {

	private static final long serialVersionUID = -6529460255350534351L;
	private final HashMap<String, AID> words = new HashMap<String, AID>();

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
			addBehaviour(new SendWord(this));
			addBehaviour(new AskForValidity(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public AID checkValid(String word) {
		return words.get(word);
	}

	public void registerWord(String word, AID sender) {
		words.put(word, sender);
	}

}

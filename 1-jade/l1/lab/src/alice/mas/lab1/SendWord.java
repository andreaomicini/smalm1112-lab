/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Behavior for the word seller agent.
 * 
 * @author Danilo Pianini
 * @version 20110926
 *
 */
public class SendWord extends CyclicBehaviour {

	private static final long serialVersionUID = 4215530659649763885L;
	private final HashMap<String,LinkedList<String>> map = new HashMap<String, LinkedList<String>>();
	private final HashMap<AID,Integer> credits = new HashMap<AID, Integer>();
	
	public SendWord() throws IOException{
		String dict = StaticUtils.fileToString("english-words.70");
		StringTokenizer tkn = new StringTokenizer(dict, "\n");
		while(tkn.hasMoreElements()){
			String n = tkn.nextToken();
			n = n.toLowerCase();
			boolean proceed = true;
			for(char c: n.toCharArray()){
				if((c<65 || c>90) && (c<97 || c>122)){
					proceed = false;
					break;
				}
			}
			if(n.length()>0 && proceed){
				String first = n.substring(0, 1);
				LinkedList<String> words = map.get(first);
				if(words == null){
					words = new LinkedList<String>();
					map.put(first, words);
				}
				words.add(n.trim());
			}
		}
	}

	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
		ACLMessage msg = myAgent.receive(mt);
		if(msg!= null){
			String letter = msg.getContent();
			log("received request for "+letter);
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
			if(letter.length()!=1){
				reply.setContent("You must specify a single letter only.");
			} else {
				char c = (char)(letter.toCharArray()[0]);
				if((c<65 || c>90) && (c<97 || c>122)){
					reply.setContent("Only ASCII chars, please. Don't try to piss me off :)");
				} else {
					final AID sender = msg.getSender();
					if(!credits.containsKey(sender)){
						credits.put(sender, 25);
					}
					final Integer credit = credits.get(sender);
					if(credit<=0){
						reply.setContent("Your credit is over, you loose.");
					} else {
						final LinkedList<String> words = map.get(letter.toLowerCase());
						if(words.size()>0){
							String word = words.removeFirst();
							reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							reply.setContent(word);
							credits.put(sender, credit-1);
						} else {
							reply.setContent("No more words, sorry, you loose.");
						}
					}
				}
			}
			myAgent.send(reply);
			log("sent "+reply.getContent()+" (preformative: "+reply.getPerformative()+") to "+msg.getSender().getLocalName());
		} else {
			block();
		}
	}

	public void log(String s){
		System.out.println(myAgent+": "+s);
	}
	
}

/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * A set of utilities, made to lighten the behaviour code.
 * 
 * @author Danilo Pianini
 * @version 20110926
 *
 */
public class StaticUtils {
	
	public final static int REJECT = ACLMessage.REJECT_PROPOSAL;
	public final static int ACCEPT = ACLMessage.ACCEPT_PROPOSAL;
	public final static int GAME = ACLMessage.INFORM;
	private static Random rnd = new Random();
	private final static MessageTemplate T_ACC = MessageTemplate.MatchPerformative(ACCEPT);
	private final static MessageTemplate T_REJ = MessageTemplate.MatchPerformative(REJECT);
	
	public static AID[] searchServiceProviders(Agent a, String service) throws FIPAException{
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(service);
		template.addServices(sd);
		DFAgentDescription[] result = DFService.search(a, template);
		AID[] agents = new AID[result.length];
		for(int i=0; i<result.length; i++){
			agents[i] = result[i].getName();
		}
		return agents;
	}
	
	public static void sendMessage(Agent source, int type, AID dest, String content){
		ACLMessage msg = new ACLMessage(type);
		msg.addReceiver(dest);
		msg.setContent(content);
		source.send(msg);
	}
	
	public static String randomASCIIchar(){
		char c = (char) (rnd.nextInt(26)+65);
		return new String(new char[]{c});
	}
	
	public static ACLMessage askForWord(Agent myAgent, String letter) throws FIPAException{
		final AID broker = StaticUtils.searchServiceProviders(myAgent, "broker")[0];
		StaticUtils.sendMessage(myAgent, ACLMessage.PROPOSE, broker, letter);
		ACLMessage msg = myAgent.receive(MessageTemplate.or(T_ACC, T_REJ));
		while(msg==null){
			msg = myAgent.receive(MessageTemplate.or(T_ACC, T_REJ));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}
	
	public static String fileToString(File f) throws IOException {
		if(f.exists()){
			BufferedReader br = new BufferedReader(new FileReader(f));
			char[] res = new char[(int)f.length()];
			br.read(res);
			br.close();
			return new String(res);
		}
		return null;
	}

	public static String fileToString(String path) throws IOException {
		return fileToString(new File(path));
	}

}

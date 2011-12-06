/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package alice.mas.lab4;

import jason.asSemantics.TransitionSystem;
import jason.infra.jade.JadeAgArch;
import jason.infra.jade.JasonBridgeArch;

/**
 * A set of utilities, made to lighten the behaviour code.
 * 
 * @author Danilo Pianini
 * @version 20111102
 *
 */
public class StaticUtils {
	
	public static JadeAgArch getJadeAgent(TransitionSystem ts){
        // get a reference to the JADE agent that represents this Jason agent
        JasonBridgeArch runtime = (JasonBridgeArch)ts.getUserAgArch().getArchInfraTier();
        return runtime.getJadeAg();
	}
	
}

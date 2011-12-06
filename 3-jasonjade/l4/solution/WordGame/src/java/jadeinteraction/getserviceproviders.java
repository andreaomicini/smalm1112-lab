/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package jadeinteraction;

import jade.core.AID;
import jade.core.Agent;
import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.Atom;
import jason.asSyntax.ListTerm;
import jason.asSyntax.ListTermImpl;
import jason.asSyntax.StringTerm;
import jason.asSyntax.Term;
import alice.mas.lab1.StaticUtils;

/**
 * @author Danilo Pianini
 * @version 20111102
 */
public class getserviceproviders extends DefaultInternalAction {

	private static final long serialVersionUID = 7475422473701045838L;

	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args)
			throws Exception {
		Agent a = alice.mas.lab4.StaticUtils.getJadeAgent(ts);
		StringTerm type = (StringTerm) args[0];
		AID[] aids = StaticUtils.searchServiceProviders(a, type.getString());
		
		ListTerm l = new ListTermImpl();
		
		for(AID aid: aids){
			if(!aid.equals(a.getAID())){
				l.append(new Atom(aid.getLocalName()));
			}
		}
		
		return un.unifies(args[1], l);
	}
}

/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package words;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

/**
 * @author Danilo Pianini
 * @version 20111018
 */
public class random_letter extends DefaultInternalAction {

	private static final long serialVersionUID = 8515022103448742865L;
	private final static char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	/** 
	 * random_letter(?Letter)
	 */
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args){
		int rnd = (int)(Math.random()*letters.length);
		final String l = ""+letters[rnd];
		final StringTerm result = new StringTermImpl(l);
		return un.unifies(args[0], result);
	}

}

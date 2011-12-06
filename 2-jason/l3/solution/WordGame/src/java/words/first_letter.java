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
public class first_letter extends DefaultInternalAction {

	private static final long serialVersionUID = 8515022103448742865L;
	
	/** 
	 * last_letter(+Word, ?Letter)
	 */
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args){
		final StringTerm w = (StringTerm)args[0];
		final String word = w.getString();
		final String last = word.substring(0, 1);
		final StringTerm result = new StringTermImpl(last);
		return un.unifies(args[1], result);
	}

}

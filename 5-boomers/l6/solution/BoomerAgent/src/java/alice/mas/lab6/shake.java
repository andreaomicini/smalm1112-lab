/**
 * 
 */
package alice.mas.lab6;

import java.util.Collections;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.ListTerm;
import jason.asSyntax.Term;

/**
 * @author danysk
 *
 */
public class shake extends DefaultInternalAction {

	private static final long serialVersionUID = -1253954102155144753L;
	
	@Override
	public Object execute(TransitionSystem ts, Unifier un, Term[] args){
		ListTerm lt = (ListTerm)args[0];
		ListTerm result = lt.cloneLT();
		Collections.shuffle(result);
		return un.unifies(args[1], result);
	}

}

/**
 * This work by Danilo Pianini is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 3.0 Italy License.
 * Permissions beyond the scope of this license may be available at www.danilopianini.org.
 */
package words;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import alice.mas.lab3.StaticUtils;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTerm;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

/**
 * @author Danilo Pianini
 * @version 20111103
 *
 */
public class get_word extends DefaultInternalAction {

	private static final long serialVersionUID = 8515022103448742865L;
	private static Object token = new Object();
	private static HashMap<String, ? extends LinkedList<String>> map = null;
	
	public get_word(){
		super();
		synchronized (token) {
			if(map == null){
				try {
					map = StaticUtils.scanDict();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/** 
	 * get_word(+Letter, ?Word)
	 */
	@Override
	public synchronized Object execute(TransitionSystem ts, Unifier un, Term[] args){
		final String letter = ((StringTerm)args[0]).toString().replace("\"", "");
		if(letter.length() == 1){
			LinkedList<String> l = map.get(letter);
			if(l.size()>0){
				final StringTerm res = new StringTermImpl(l.removeFirst());
				return un.unifies(args[1], res);
			}
		}
		return false;
	}

}

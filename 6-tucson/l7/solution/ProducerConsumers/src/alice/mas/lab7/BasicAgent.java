/**
 * 
 */
package alice.mas.lab7;

import java.util.concurrent.atomic.AtomicInteger;

import alice.logictuple.LogicTuple;
import alice.tucson.api.Tucson;
import alice.tucson.api.TucsonAgentId;
import alice.tucson.api.TucsonContext;
import alice.tucson.api.TucsonTupleCentreId;
import alice.tucson.api.exception.ContextNotAvailableException;
import alice.tucson.api.exception.LanguageNotFoundException;
import alice.tucson.api.exception.TucsonInvalidAgentIdException;
import alice.tucson.api.exception.TucsonInvalidTupleCentreIdException;
import alice.tucson.api.exception.TucsonOperationNotPossibleException;
import alice.tucson.api.exception.UnreachableNodeException;
import alice.tucson.service.TucsonAgentLanguageManager;
import alice.tuplecentre.core.OperationTimeOutException;

/**
 * @author Danilo Pianini
 * @version 20111204
 *
 */
public abstract class BasicAgent extends Thread {
	
	private final static AtomicInteger singleton = new AtomicInteger();
	private final String name = this.getClass().getSimpleName().toLowerCase()+singleton.getAndIncrement();
	private TucsonContext tc;
	private TucsonTupleCentreId tid;
	private boolean isAlive = true;
	
	public BasicAgent(String tid){
		try {
			this.tid = new TucsonTupleCentreId(tid);
			tc = Tucson.getContext(new TucsonAgentId(getAgentName()), TucsonAgentLanguageManager.getCurLanguage());
		} catch (TucsonInvalidTupleCentreIdException e) {
			e.printStackTrace();
		} catch (ContextNotAvailableException e) {
			e.printStackTrace();
		} catch (TucsonInvalidAgentIdException e) {
			e.printStackTrace();
		}
	}
	
	protected String getAgentName(){
		return name;
	}
	
	public void run(){
		while(isAlive){
			doAction();
		}
	}
	
	public abstract void doAction();

	public void kill(){
		isAlive = false;
	}
	
	protected LogicTuple out(LogicTuple t){
		try {
			return tc.out(tid, t, Long.MAX_VALUE);
		} catch (TucsonOperationNotPossibleException | UnreachableNodeException
				| LanguageNotFoundException | OperationTimeOutException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected LogicTuple in(LogicTuple t){
		try {
			return tc.in(tid, t, Long.MAX_VALUE);
		} catch (TucsonOperationNotPossibleException | UnreachableNodeException
				| LanguageNotFoundException | OperationTimeOutException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected LogicTuple rd(LogicTuple t){
		try {
			return tc.rd(tid, t, Long.MAX_VALUE);
		} catch (TucsonOperationNotPossibleException | UnreachableNodeException
				| LanguageNotFoundException | OperationTimeOutException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void say(String s){
		System.out.println("["+getAgentName()+"]: "+s);
	}
	
}

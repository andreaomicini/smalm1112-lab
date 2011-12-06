package alice.mas.lab7;

import java.io.IOException;

import alice.logictuple.InvalidLogicTupleException;
import alice.logictuple.LogicTuple;
import alice.tucson.api.Tucson;
import alice.tucson.api.TucsonAgentId;
import alice.tucson.api.TucsonContext;
import alice.tucson.api.TucsonTupleCentreId;
import alice.tucson.api.exception.LanguageNotFoundException;
import alice.tucson.api.exception.TucsonException;
import alice.tucson.service.Node;
import alice.tucson.service.TucsonAgentLanguageManager;
import alice.tuplecentre.core.OperationTimeOutException;

/**
 * 
 */

/**
 * @author Danilo Pianini
 * @version 20111204
 *
 */
public class DPLauncher {
	
	private static final String addr = "localhost";
	private static final int PHILONUM = 5;

	public static void main(String[] args) throws TucsonException, InterruptedException, InvalidLogicTupleException, LanguageNotFoundException, OperationTimeOutException, IOException{
		if(addr.equals("localhost")){
			new Thread(){
				public void run(){
					Node.main(new String[0]);
				}
			}.start();
			while(!Node.isInstalled()){
				Thread.sleep(100);
			}
			TucsonTupleCentreId tid = new TucsonTupleCentreId("table@"+addr);
			TucsonContext tc = Tucson.getContext(new TucsonAgentId("populator"), TucsonAgentLanguageManager.getCurLanguage());
			for(int i=0; i<PHILONUM; i++){
				tc.out(tid, LogicTuple.parse("chop("+i+")"), Long.MAX_VALUE);
			}
			tc.setSpec(tid, StaticUtils.fileToString("spec.rsp"), Long.MAX_VALUE);
		}
		for(int i=0; i<PHILONUM-1; i++){
			new Philosopher(i,i+1,addr).start();
		}
		new Philosopher(PHILONUM-1,0,addr).start();
	}
	
}

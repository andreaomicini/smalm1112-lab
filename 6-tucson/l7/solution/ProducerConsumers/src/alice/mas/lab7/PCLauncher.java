package alice.mas.lab7;

import alice.tucson.api.exception.TucsonException;
import alice.tucson.service.Node;

/**
 * 
 */

/**
 * @author Danilo Pianini
 * @version 20111204
 *
 */
public class PCLauncher {
	
	private static final String addr = "localhost";

	public static void main(String[] args) throws TucsonException, InterruptedException{
		if(addr.equals("localhost")){
			new Thread(){
				public void run(){
					Node.main(new String[0]);
				}
			}.start();
			while(!Node.isInstalled()){
				Thread.sleep(100);
			}
		}
		for(int i=0; i<100; i++){
			new Consumer(addr).start();
		}
		new Producer(addr).start();
		Thread.sleep(1);
		
	}
	
}

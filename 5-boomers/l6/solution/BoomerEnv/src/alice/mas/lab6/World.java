/**
 * 
 */
package alice.mas.lab6;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

/**
 * @author Danilo Pianini
 * @version 20111114
 *
 */
public class World extends Thread {
	
	private final Prolog p = new Prolog();
	private final Random r = new Random();
	private final WorldMonitor wm = new WorldMonitor(this);
	
	public World() throws FileNotFoundException, IOException, InvalidTheoryException, MalformedGoalException{
		final Theory th = new Theory(new FileInputStream(new File("prolog/world.pl")));
		p.setTheory(th);
		for(int i=0; i<500; i++){
			final int x = r.nextInt(100);
			final int y = r.nextInt(100);
			p.solve("assert(block("+x+","+y+")).");
		}
		WorldMonitor.buildFrame(wm);
		wm.repaint();
	}
	
	@Override
	public void run(){
		try {
			final ServerSocket ss = new ServerSocket(1099);
			while(true){
				final Socket s = ss.accept();
				final ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				try {
					final Object mo = ois.readObject();
					if(mo instanceof String){
						final String msg = (String)mo;
						ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
						try {
							final String playerid = s.getInetAddress().toString().replace(".", "").replace("-", "").replace("/", "");
							System.out.println("Executing: op("+playerid+","+msg+").");
							synchronized (p) {
								final SolveInfo sol = p.solve("op("+playerid+","+msg+").");
								oos.writeObject(sol);
								System.out.println(sol);
							}
						} catch (MalformedGoalException e) {
							oos.writeObject(e);
						}
						oos.flush();
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				wm.repaint();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] a){
		try {
			new World().start();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidTheoryException e) {
			e.printStackTrace();
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean block(int x, int y) {
		SolveInfo s;
		try {
			s = p.solve("block("+x+","+y+").");
			return s.isSuccess();
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Prolog getProlog(){
		return p;
	}

}

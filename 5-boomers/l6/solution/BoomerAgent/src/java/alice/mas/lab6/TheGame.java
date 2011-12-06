package alice.mas.lab6;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Number;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.UnknownVarException;
import cartago.Artifact;
import cartago.OPERATION;
import cartago.OpFeedbackParam;

public class TheGame extends Artifact {
	
	private String ip="localhost";
	
	void init(){
//	   defineObsProperty("north","dunno");
//	   defineObsProperty("south","dunno");
//	   defineObsProperty("east","dunno");
//	   defineObsProperty("west","dunno");
	}
	
	@OPERATION void newgame(String ip){
		this.ip = ip;
		runcommand("new");
	}
	
	@OPERATION void bomb(){
		runcommand("bomb");
	}
	
	@OPERATION void smell(){
		final SolveInfo pos = runcommand("pos(X,Y)");
		if(pos.isSuccess()){
			try {
				int x = ((Number)pos.getTerm("X")).intValue();
				int y = ((Number)pos.getTerm("Y")).intValue();
				final SolveInfo n = runcommand(buildsmell(x, y+1));
				updatesmell(n, "north");
				final SolveInfo s = runcommand(buildsmell(x, y-1));
				updatesmell(s, "south");
				final SolveInfo e = runcommand(buildsmell(x+1, y));
				updatesmell(e, "east");
				final SolveInfo w = runcommand(buildsmell(x-1, y));
				updatesmell(w, "west");
			} catch (NoSolutionException e) {
				e.printStackTrace();
			} catch (UnknownVarException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Something went wrong.");
		}
	}
	
	@OPERATION void move(String d, OpFeedbackParam<String> r){
		final SolveInfo pos = runcommand("move("+d.substring(0, 1)+",R)");
		if(pos.isSuccess()){
			try {
				r.set(pos.getVarValue("R").toString());
			} catch (NoSolutionException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Something went wrong.");
		}
	}
	
	private void updatesmell(SolveInfo s, String pName) throws NoSolutionException, UnknownVarException{
		if(s.isSuccess()){
			signal(s.getTerm("R").toString(),pName);
		}
	}
	
	private static String buildsmell(int x, int y){
		return "smell("+x+","+y+",R)";
	}
	
	private SolveInfo runcommand(String c){
		try {
			Socket s = new Socket(ip, 1099);
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(c);
			oos.flush();
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			SolveInfo si = (SolveInfo)ois.readObject();
			System.out.println(si);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}

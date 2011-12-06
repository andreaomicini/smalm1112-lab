package alice.mas.lab6;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import alice.tuprolog.MalformedGoalException;
import alice.tuprolog.NoSolutionException;
import alice.tuprolog.Number;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Struct;

/**
 * @author Danilo Pianini
 * @version 20111114
 *
 */
public class WorldMonitor extends JPanel {

	private static final long serialVersionUID = -7859975998112971561L;
	private final World w;
	private static final int S = 20;
	
	public WorldMonitor(World w){
		super();
		this.w = w;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		final int xspan = getWidth()/S;
		final int yspan = getHeight()/S; 
		for(int i=0; i<S*S; i++){
			final int x = i%S;
			final int y = i/S;
			if(w.block(x,y)){
				g.setColor(Color.BLACK);
			} else {
				g.setColor(Color.GREEN);
			}
			g.fillRect(x*xspan, getHeight()-yspan-(y*yspan), xspan, yspan);
		}
		try {
			final SolveInfo s = w.getProlog().solve("findall([P,X,Y],player(P,X,Y),L).");
			Struct l = (Struct)s.getVarValue("L");
			while(!l.isEmptyList()){
				final Struct p = (Struct)l.listHead();
				final int x = ((Number)p.listTail().listHead().getTerm()).intValue();
				final int y = ((Number)p.listTail().listTail().listHead().getTerm()).intValue();
				g.setColor(Color.BLUE);
				g.fillRect(x*xspan, getHeight()-yspan-(y*yspan), xspan, yspan);
				g.drawString(p.getArg(0).toString(), x*xspan, getHeight()-yspan-(y*yspan));
				l = l.listTail();
			}
			final SolveInfo sb = w.getProlog().solve("findall([X,Y],bomb(X,Y),L).");
			Struct lb = (Struct)sb.getVarValue("L");
			while(!lb.isEmptyList()){
				final Struct p = (Struct)lb.listHead();
				final int x = ((Number)p.listHead()).intValue();
				final int y = ((Number)p.listTail().listHead()).intValue();
				g.setColor(Color.RED);
				g.fillRect(x*xspan, getHeight()-yspan-(y*yspan), xspan, yspan);
				lb = lb.listTail();
			}
		} catch (MalformedGoalException e) {
			e.printStackTrace();
		} catch (NoSolutionException e) {
			e.printStackTrace();
		}
	}
	
	public static void buildFrame(WorldMonitor w){
		final JFrame f = new JFrame("MAS Lab");
		f.setContentPane(w);
		f.setSize(510, 530);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}

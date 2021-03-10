
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    private String textToDisplay;
    private Map<String, Double> data;
    private Map<String, Double> relativeData;
    private Ellipse2D.Double seth;
    private int numRows;
    private ArrayList<Axis> axes;
    private List<HyrumPolyline> lines;
    private Rectangle box;
    private Point corner;
    private boolean firstRun;


    public Vis() {
        super();
        textToDisplay = "There's nothing to see here.";
        relativeData = new HashMap<>();
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        addMouseListener(this);
        addMouseMotionListener(this);
        numRows=0;
        lines = new ArrayList<>();
        axes = new ArrayList<>();
        box = null;

        try {
          axes.clear();
          lines.clear();
        }catch (Exception e) {
			// TODO: handle exception
		}
    }

    public void setText(String t) {
        textToDisplay = t;
        repaint();
    }
    public void setRows(int r) {
    	numRows = r;
    }

    public void clear() {
        try {
          axes.clear();
          lines.clear();
        }catch (Exception e) {
			// TODO: handle exception
		}
	}


//    
    
    public void setAxes(ArrayList<Axis> ax) {
    	axes = ax;
    	repaint();
    }
    
    


    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight()); 

        //render visualization
        g.setColor(Color.BLACK);
        
        double marginH = 0.95;
        double marginW = 0.05;
        final double h = getHeight()*marginH;
        final double w = getWidth();
        int numAxes = axes.size();
        

        try {
            for (Axis a : axes) {
            	a.setHeight(h);
                a.setData();
            }
        }catch (Exception e) {
			// TODO: handle exception
        	System.out.println(e);
	         System.err.println("  Message:    " + e.getMessage());
		}
        
        int q =0;
        for(Axis a: axes) {
        	double buffer = w/(numAxes+1);
            a.setGeometry((q+1)*buffer, h);
//            axes.add(a);
            a.draw(g);
            g.drawString(a.columnName, (int) ((q+1)*buffer)-20, (int) h+20);
        	
        	q++;
        }




        try {       	
            for (int i=0; i<numRows; i++) {
                var poly = new HyrumPolyline();
                for (int j=0; j<numAxes; j++) {
                	axes.get(j).drawLabels(g);
                	poly.addPoint(axes.get(j).getPointAt(i));
                    lines.add(poly);
                                       
                }
            }
            for (var pl : lines) {
                pl.draw(g);
            }
//            lines.clear();
		} catch (Exception e) {
	
//			 System.out.println(e);
//	         System.err.println("  Message:    " + e.getMessage());
			
		}
        
        // draw the box
        if (box != null) {
            g.setColor(Color.CYAN);
            g.draw(box);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
    	 corner = new Point(e.getX(), e.getY());
    	 box = new Rectangle(corner);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	 box = null;
    	 repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    	int x = e.getX();
        int y = e.getY();
        box.setFrameFromDiagonal(corner.x, corner.y, x, y);
        repaint();
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();        
      //for each polyline, measure the distance from the mouse to the polyline
        //highlight the polyline that's closest (within a given threshold)
        double min = 100000;
        HyrumPolyline selected = new HyrumPolyline();
        for (var p : lines) {
            p.unhighlight();
            double dist = p.getDistanceFromPoint(x,y);
            if (dist < min) {
                min = dist;
                selected = p;
            }
        }
//        lines.clear();
        selected.highlight();
        repaint();
    }
}

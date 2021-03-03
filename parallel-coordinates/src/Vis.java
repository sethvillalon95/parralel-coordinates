
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
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
    

    public Vis() {
        super();
        textToDisplay = "There's nothing to see here.";
        relativeData = new HashMap<>();
        seth = new Ellipse2D.Double(50, 100, 40, 40);
        addMouseListener(this);
        addMouseMotionListener(this);
        numRows=0;
        axes = new ArrayList<>();
    }

    public void setText(String t) {
        textToDisplay = t;
        repaint();
    }
    public void setRows(int r) {
    	numRows = r;
    }

    public void setData(Map<String, Double> acacia) {
        data = acacia;
        var allValues = data.values();
        double max = 0;
        for (var kaipo : allValues) {
            if (kaipo > max) {
                max = kaipo;
            }
        }
        for (var key : data.keySet()) {
            relativeData.put(key, data.get(key) / max);
        }
        repaint();
    }

//    public void setData(List<Point2D> acacia) {
//        double maxX = 0;
//        double maxY = 0;
//        for (var kaipo : acacia) {
//            if (kaipo.getX() > maxX) {
//                maxX = kaipo.getX();
//            }
//            if (kaipo.getY() > maxY) {
//                maxY = kaipo.getY();
//            }
//        }
//        for (var kaipo : acacia) {
//            var gilmo = new Point2D.Double(kaipo.getX() / maxX, kaipo.getY() / maxY);
//            relativeScatterData.add(gilmo);
//        }
//        repaint();
//    }
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
        g.drawString(textToDisplay, 10, 20);

        final int h = getHeight();
        final int w = getWidth();
       

        //pseudocode for drawing polylines
        //find out how many rows there are
        for(int i=0; i<numRows; i++) {
        	 //loop that many times
            //  instantiate a polyline object
        	// 	HyrumPolyline poly = new HyrumPolyline();
            //  for each axis
            //      lookup the value in the axis for that row, add it to polyline

        }
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
     
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      
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
       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (seth.contains(x,y)) {
            //System.out.println("Hello, Seth's ellipse!");
            setToolTipText("Hello from " + x + "," + y);
        } else {
            setToolTipText(null);
        }
    }
}

package edu.byuh.cis.cs490r.starter;

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
import java.util.*;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener, MouseMotionListener {

    public List<Axis> axes;
    private List<String> dummy;
    private List<HyrumPolyline> lines;
    private ArrayList<String> strings;
    private ArrayList<HyrumPolyline> selectedLine;
    private ArrayList<HyrumPolyline> notselectedLine;
    private boolean firstRun;
    Axis alex;
    private Rectangle box;
    private Point corner;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Vis() {
        super();
        firstRun = true;
        axes = new ArrayList<>();
        lines = new ArrayList<>();
        selectedLine= new ArrayList<>();
        notselectedLine= new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);
        box = null;
    }



    /**
     * setting Axes to an arraylist
     *
     * @param acacia
     */
    public void setAxes(List<Axis> acacia) {
        for (var kaipo : acacia) {
            axes.add(kaipo);
        }
        repaint();
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void setToNormal(){
        for(var v: lines){
            v.unhighlight();
        }
        repaint();
    }


    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render visualization
        g.setColor(Color.BLACK);
        final int h = getHeight();
        final int w = getWidth();

        if(firstRun){

        }
        int numAxes = axes.size();
        for (int i = 0; i < numAxes; i++) {
            double buffer = w / (numAxes);
            Axis gilmo = axes.get(i);
            gilmo.setGeometry((i + 0.5) * buffer, h - 30);
//            double buffer = w/(numAxes+1);
//            Axis gilmo =axes.get(i);
//            gilmo.setGeometry((i+1)*buffer, h);

            double max = 0;
            for (var kaipo : gilmo.data) {
                String str = kaipo.toString();
                if (isNumeric(str)) {
                    double d = Double.parseDouble(str);
                    if (d > max) {
                        max = d;
                    }
                }
            }
            int ratio = getHeight() / 4;
            if (gilmo.columnName.equals("GENDER") || gilmo.columnName.equals("AGEGROUP") || gilmo.columnName.equals("HOME") || gilmo.columnName.equals("MAJOR")) {
                strings = new ArrayList<>();
                for (var vv : gilmo.data) {
                    if (!strings.contains(vv.toString())) {
                        strings.add(vv.toString());
                    }
                }
                int strSize = strings.size();
                Collections.sort(strings, Collections.reverseOrder());
                for (int k = 0; k < strSize; k++) {
                    //b= ((index+1)* geometry.y2-geometry.y1 /(max))/(max+(max/2));
                    int b = ((k + 1) * h - 30 / (strSize)) / (strSize + (strSize / 2));
                    g.drawString(strings.get(k), (int) ((i + 0.4) * buffer) + (w / 80), b);
                }
            } else {
                for (int j = 0; j < 4; j++) {
                    if (max == 2019.0) {
                        max = 2019.0;
                    } else {
                        max = (max / 4) * (4 - j);
                    }

                    max = Math.round((max * 100.0) / 100.0);
                    String str = String.valueOf(max);
                    g.setColor(Color.RED);
                    if (str.equals("2019.0")) {
                        g.drawString("2019", (int) ((i + 0.4) * buffer + (w / 80)), h / 50);
                    } else {
                        g.drawString(str, (int) ((i + 0.4) * buffer) + (w / 80), h / 50 + (j * ratio));
                    }
                    g.drawString("0.0", (int) ((i + 0.4) * buffer) + (w / 80), getHeight() - 30);
                }
            }

            g.setColor(Color.BLACK);
            gilmo.draw(g);
            g.drawString(gilmo.columnName, (int) ((i + 0.4) * buffer), getHeight());
        }

        int numLines;

        if (axes.size() != 0) {
            if(firstRun){
                firstRun =false;
                lines.clear();
                alex = axes.get(0);
                numLines = alex.data.size();
                for (int i = 0; i < numLines; i++) {
                    var acacia = new HyrumPolyline();
                    for (int j = 0; j < numAxes; j++) {
                        int index = 0;
                        alex = axes.get(j);
                        dummy = new ArrayList<>();
                        for (var vv : alex.data) {
                            if (!dummy.contains(vv.toString())) {
                                dummy.add(vv.toString());
                            }
                        }
                        double max = 0;
                        for (var kaipo : alex.data) {
                            String str = kaipo.toString();
                            if (isNumeric(str)) {
                                double d = Double.parseDouble(str);
                                if (d > max) {
                                    max = d;
                                }
                            } else {
                                index = dummy.indexOf(alex.data.get(i));
                            }
                        }


                        if (alex.data.get(0) instanceof String) {
                            acacia.addPoint(axes.get(j).getPointAt(i, dummy.size(), index));
                        } else {
                            acacia.addPoint(axes.get(j).getPointAt(i, max, index));
                        }
                        lines.add(acacia);
                    }
                }
            }
            }
        if (box!=null){
            g.setColor(Color.BLUE);
            g.draw(box);
        }
        for (var pl : lines) {
            pl.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        corner = new Point(e.getX(), e.getY());
        box = new Rectangle(corner);
        selectedLine.clear();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        for (var v: selectedLine){
//            v.highlight();
//        }
//        for (var v: notselectedLine){
//            v.fade();
//        }
//        for(var v: notselectedLine){
//            v.fade();
//        }
        repaint();
        box = null;
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
        for (var p : lines) {
            if(!selectedLine.contains(p)){
                if(p.getIntersact(box)) {
                    p.highlight();
                    selectedLine.add(p);
                }
                else{
                    p.fade();
                    notselectedLine.add(p);
                }
            }
        }
        for(var p: selectedLine){
            p.highlight();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int gilmo =0;
        if (lines.size() > 0) {
            double min = 100000;
            HyrumPolyline selected = null;
            for (var p : lines) {
                p.unhighlight();
                double dist = p.getDistanceFromPoint(x, y);
                if (dist < min) {
                    min = dist;
                    selected = p;
                }
            }
            gilmo = lines.indexOf(selected)/6;
            String tooltip = "";

            selected.highlight();
            for(var v: axes){
                tooltip+=(v.columnName);
                tooltip+=": ";
                tooltip+=(v.data.get(gilmo));
                tooltip+=", ";
            }
            setToolTipText(tooltip);

        }

//        Loop through all the polylines:
//        gilmo = the index of the polyline closest to the mouse
//        String tooltip = ""
//        Loop through all the axes:
//        ask the axis for the value at index gilmo in its list of values
//        add these values to tooltip
//        setTooltipText(tooltip)
        if(selectedLine.size()==0){
            repaint();
        }
    }
}

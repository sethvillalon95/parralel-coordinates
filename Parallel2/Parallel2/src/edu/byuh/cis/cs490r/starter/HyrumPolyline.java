/*********************************
 * This file is part of the "Interactive Fan Chart Demo"
 * by Geoffrey M. Draper.
 * 
 * Geoffrey M. Draper, author of this work, hereby grants
 * everyone permission to use and distribute this code,
 * with or without modifications for any purpose,
 * commercial or otherwise. 
 * 
 * It is requested, although not required, that derivative
 * works include an acknowledgment somewhere in the
 * derivative work's credits.
 *********************************/
/*********************************
 * This file is part of the "Interactive Fan Chart Demo"
 * by Geoffrey M. Draper.
 *
 * Geoffrey M. Draper, author of this work, hereby grants
 * everyone permission to use and distribute this code,
 * with or without modifications for any purpose,
 * commercial or otherwise.
 *
 * It is requested, although not required, that derivative
 * works include an acknowledgment somewhere in the
 * derivative work's credits.
 *********************************/

package edu.byuh.cis.cs490r.starter;


import java.awt.*;
import java.awt.geom.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//This composite class gives me the precision of the new Path2D
//class, while still providing the low-level access to individual
//vertices I got accustomed to when using the legacy Polygon class.

//NOTE: I backported from Path2D.Double to GeneralPath to maintain
//compatibility with Java 1.5.

public class HyrumPolyline
		implements Serializable, Comparable<HyrumPolyline> {
	private GeneralPath polygon;
	private List<Point2D> points;
	private static Stroke regularLine = new BasicStroke(1);
	private static Stroke thickLine = new BasicStroke(5);

	public double getDistanceFromPoint(int x, int y) {
		double min = Double.MAX_VALUE;
//		System.out.println("points: "+points.size());
		for (int i=1; i<points.size(); i++) {
			double x1 = points.get(i-1).getX();
			double y1 = points.get(i-1).getY();
			double x2 = points.get(i).getX();
			double y2 = points.get(i).getY();
			//System.out.println("x1: "+x1+" y1: "+y1+" x2: "+x1+" y2: "+y2);
			double dist = Line2D.ptSegDist(x1, y1, x2, y2, x, y);
			//System.out.println("dist----"+dist);
			if (dist < min) {
				min = dist;
			}
		}
		return min;
	}
	public boolean getIntersact(Rectangle2D b){
		boolean inter = false;
		for (int i=1; i<points.size(); i++) {
			double x1 = points.get(i-1).getX();
			double y1 = points.get(i-1).getY();
			double x2 = points.get(i).getX();
			double y2 = points.get(i).getY();
			Line2D.Double high=new Line2D.Double(x1,y1,x2,y2);
			if (high.intersects(b)){
				inter = true;
			}
			//System.out.println("x1: "+x1+" y1: "+y1+" x2: "+x1+" y2: "+y2);
		}
		return inter;
	}

	private enum State {
		NORMAL,
		HIGHLIGHTED,
		INVISIBLE
	}

	private Color invisibleColor = new Color(230,230,230);
	State state = State.NORMAL;

	public HyrumPolyline() {
		polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		points = new ArrayList<Point2D>();
	}

	public HyrumPolyline(HyrumPolyline hp) {
		polygon = new GeneralPath(hp.polygon);
		points = new ArrayList<Point2D>(hp.points);
	}

	public void addPoint(final Point2D p) {
		addPoint(p.getX(), p.getY());
	}

	public void addPoint(final Double x, final Double y) {
		if (points.isEmpty()) {
			polygon.moveTo(x, y);
		} else {
			polygon.lineTo(x, y);
		}
		points.add(new Point2D.Double(x,y));
	}

	public Point2D getPointAt(final int index) {
		return points.get(index);
	}

	public int getNumPoints() {
		return points.size();
	}

	//trivial delegate methods
	public void closePath() {
		if (points.size() > 2) {
			polygon.closePath();
		}
	}

	public void reset() {
		polygon.reset();
		points.clear();
	}

	public void draw(final Graphics2D g) {
		g.setStroke(regularLine);
		if (state == State.HIGHLIGHTED) {
			g.setStroke(thickLine);
			g.setColor(Color.RED);
		} else if (state==State.NORMAL) {
			g.setColor(Color.BLACK);
		} else {//invisible
			g.setColor(invisibleColor);
		}
		g.draw(polygon);
	}

	public void draw(Graphics2D g, Color c) {
		g.setColor(c);
		g.draw(polygon);
	}

	public void highlight() {
		state = State.HIGHLIGHTED;
	}

	public void fade() {
		state = State.INVISIBLE;
	}

	public void unhighlight() {
		state = State.NORMAL;
	}

	public boolean isInvisible() {
		return (state == State.INVISIBLE);
	}

	public boolean isHighlighted() {
		return (state == State.HIGHLIGHTED);
	}

	public boolean isNormal() {
		return (state == State.NORMAL);
	}

	@Override
	public int compareTo(HyrumPolyline other) {
		if (isHighlighted()) {
			return 1;
		}
		if (isInvisible()) {
			return -1;
		}
		if (isNormal()) {
			if (other.isHighlighted()) {
				return -1;
			}
			if (other.isInvisible()) {
				return 1;
			}
		}
		return 0;
	}
}

//package edu.byuh.cis.cs490r.starter;
//
//
//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.GeneralPath;
//import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
////This composite class gives me the precision of the new Path2D
////class, while still providing the low-level access to individual
////vertices I got accustomed to when using the legacy Polygon class.
//
////NOTE: I backported from Path2D.Double to GeneralPath to maintain
////compatibility with Java 1.5.
//
//public class HyrumPolyline
//		implements Serializable, Comparable<HyrumPolyline> {
//	private GeneralPath polygon;
//	private List<Point2D> points;
//	private static Stroke regularLine = new BasicStroke(1);
//	private static Stroke thickLine = new BasicStroke(5);
//
//	public double getDistanceFromPoint(int x, int y) {
//		double min = Double.MAX_VALUE;
//		for (int i=1; i<points.size(); i++) {
//			double x1 = points.get(i-1).getX();
//			double y1 = points.get(i-1).getY();
//			double x2 = points.get(i).getX();
//			double y2 = points.get(i).getY();
//			double dist = Line2D.ptLineDistSq(x1, y1, x2, y2, x, y);
//			if (dist < min) {
//				min = dist;
//			}
//		}
//		return min;
//	}
//
//	private enum State {
//		NORMAL,
//		HIGHLIGHTED,
//		INVISIBLE
//	}
//
//	private Color invisibleColor = new Color(230,230,230);
//	State state = State.NORMAL;
//
//	public HyrumPolyline() {
//		polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
//		points = new ArrayList<Point2D>();
//	}
//
//	public HyrumPolyline(HyrumPolyline hp) {
//		polygon = new GeneralPath(hp.polygon);
//		points = new ArrayList<Point2D>(hp.points);
//	}
//
//	public void addPoint(final Point2D p) {
//		addPoint(p.getX(), p.getY());
//
//
//	}
//
//	public void addPoint(final Double x, final Double y) {
//		if (points.isEmpty()) {
//			polygon.moveTo(x, y);
//		} else {
//			polygon.lineTo(x, y);
//		}
//		points.add(new Point2D.Double(x,y));
//	}
//
//	public Point2D getPointAt(final int index) {
//		return points.get(index);
//	}
//
//	public int getNumPoints() {
//		return points.size();
//	}
//
//	//trivial delegate methods
//	public void closePath() {
//		if (points.size() > 2) {
//			polygon.closePath();
//		}
//	}
//
//	public void reset() {
//		polygon.reset();
//		points.clear();
//	}
//
//	public void draw(final Graphics2D g) {
//		if (state == State.HIGHLIGHTED) {
//			g.setStroke(thickLine);
//			g.setColor(Color.RED);
//		} else if (state==State.NORMAL) {
//			g.setColor(Color.BLACK);
//		} else {//invisible
//			g.setColor(invisibleColor);
//		}
//		g.draw(polygon);
//	}
//
//	public void draw(Graphics2D g, Color c) {
//		g.setColor(c);
//		g.draw(polygon);
//	}
//
//	public void highlight() {
//		state = State.HIGHLIGHTED;
//	}
//
//	public void fade() {
//		state = State.INVISIBLE;
//	}
//
//	public void unhighlight() {
//		state = State.NORMAL;
//	}
//
//	public boolean isInvisible() {
//		return (state == State.INVISIBLE);
//	}
//
//	public boolean isHighlighted() {
//		return (state == State.HIGHLIGHTED);
//	}
//
//	public boolean isNormal() {
//		return (state == State.NORMAL);
//	}
//
//	@Override
//	public int compareTo(HyrumPolyline other) {
//		if (isHighlighted()) {
//			return 1;
//		}
//		if (isInvisible()) {
//			return -1;
//		}
//		if (isNormal()) {
//			if (other.isHighlighted()) {
//				return -1;
//			}
//			if (other.isInvisible()) {
//				return 1;
//			}
//		}
//		return 0;
//	}
//}

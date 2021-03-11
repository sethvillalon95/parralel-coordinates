package edu.byuh.cis.cs490r.starter;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;
    private Line2D.Double geometry;

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
    }

    /**
     * extracting data from colums
     * @param rs
     */
    public void extractData(ResultSet rs) {
        try {
            Object item = rs.getObject(columnName);
            data.add(item);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

//    public void debug() {
//        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
//        for (var d : data) {
//            System.out.println(d);
//        }
//    }

    public void setGeometry(double x, double h) {
        geometry = new Line2D.Double(x, 0, x, h);
    }

    /**
     * drawing geomet
     * @param g
     */
    public void draw(Graphics2D g) {
        g.draw(geometry);
    }

    // String str = kaipo.toString();
    //                if(isNumeric(str)){
    //                    double d = Double.valueOf(str).doubleValue();

    public Point2D.Double getPointAt(int i,double max,int index) {
        double b=0;
        var o =this.data.get(i);
        if (o instanceof Double){
            String str = o.toString();
            double d = Double.parseDouble(str);
            b= (max-d)/max*(geometry.y2-geometry.y1);
        }
        else if(o instanceof String){
            String str = o.toString();
            b= ((index+1)* geometry.y2-geometry.y1 /(max))/(max+(max/2));
            //System.out.println("height"+b+" y2-y1:"+(geometry.y2 - geometry.y1)+" max:"+max);

        }
        else {
            String str = o.toString();
            double d = Double.parseDouble(str);
            b = (max-d) / max * (geometry.y2 - geometry.y1);
        }
        //double y = o*(geometry.y2-geometry.y1);
        return new Point2D.Double(geometry.x1, b);
    }
}

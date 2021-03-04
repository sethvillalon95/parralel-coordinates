import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;
    ArrayList<Double> relDataNum;
    ArrayList<String> relDataStr;
    ArrayList<Double>relData;
    private Line2D.Double geometry;
    private double uniqueVals;
    double height;
    boolean isString =false;
    boolean isNumeric = false;
    

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
        relDataNum = new ArrayList<>();
        relDataStr = new ArrayList<>();
        relData = new ArrayList<>();
        try {
            height =geometry.y2-geometry.y1;

        }catch (Exception e) {
			// TODO: handle exception
		}

    } 

    public void extractData(ResultSet rs) {
        try {
            Object item = rs.getObject(columnName);
            
            data.add(item);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    // call this (once) after you run the extractData in a for loop. 
    public void setData() {
    	System.out.println("Setting...");
    	// type check item if it is a double or String
        // then get the max and the min
        // if String, compare them 
    	double maxNum = 0; 
    	double minNum = 0;
    	ArrayList<Double> tempDouble =  new ArrayList<>();
    	uniqueVals = 0;
    	HashSet<String> uniqueSet = new HashSet<String>();

//        System.out.println("The Height is "+ height);

    	for(Object item:data) {
//    		System.out.println("The item is "+item);
            if(item instanceof Number) {
            	isNumeric = true;
            	Number b = (Number)item;
            	double n = b.doubleValue();
//            	System.out.println("the number is" +n);
            	tempDouble.add(n);
            	
            }else if(item instanceof String){
            	isString = true;
//            	System.out.println("String "+item.getClass());
            	String s = (String)item;
//            	System.out.println(s);
            	
            	// this sets how many unique values you have. 
            	if(uniqueSet.add(s)) {
            		uniqueVals++;
            	}

            }else {
            	System.out.println("String "+item.getClass());
            	System.out.println("The data is neither a String or BigDecimal");
//            	new Exception("Error in checking the instanceof an object");
            }
    	}
    	
//    Sort the tempDouble to get the min and Max;
    try {
    	  Collections.sort(tempDouble); 
//        then loop through the tempDouble to then divide everything by the max number;
      	  maxNum = tempDouble.get(tempDouble.size()-1);
      	  minNum = tempDouble.get(0);
      	  for(double d:tempDouble) {
      		  relData.add((d/maxNum));
//      		  System.out.println(d/maxNum);
      	  }
//      	  System.out.println("The Max number is "+ maxNum);
      	  
		
	} catch (Exception e) {
		// TODO: handle exception
	}
    	
//    	System.out.println("The number of unique values is " + uniqueVals);
    	if(isString) {
        	calcYString(uniqueSet);
//        	System.out.println("The size of RelData is "+relData.size());

    	}
    	
    	if(isNumeric) {
//        	System.out.println("I am numeric The size of RelData is "+relData.size());

    	}
    	

    }


    private void calcYString(HashSet<String> hash) {
    	double tempM = divider();
    	System.out.println("calculating ");
    	try {
			relData.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	for(var a: data) {
    		String b =a.toString();
    		int jk = 0;
    		for(var c:hash) {
    			if(b.equals(c)) {
            		relData.add(tempM*(jk+1));

    			}
    			jk++;
    		}
    		
//    		for(int jk = 0; jk<hash.size();jk++){
//    			var temp = hash.
//    			if()
//        		relData.add(tempM*(jk+1));
//        	}
    	}
    	
//    	for(var a:relData) {
//    		System.out.println("Printing the relData for the String "+a+" for "+ columnName);
//    	}
//		
	}
    
    private double divider() {
    	double tempR = height/(uniqueVals+1);
    	System.out.println("Height is "+ height);
    	return tempR;
    }
    public void debug() {
        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
        for (var d : data) {
            System.out.println(d);
        }
    }

    public void setGeometry(double x, double h) {
        geometry = new Line2D.Double(x, 0, x, h);
    }

    public void draw(Graphics2D g) {
        g.draw(geometry);
    }

    public Point2D.Double getPointAt(int i) {
//        double y = Math.random()*(geometry.y2-geometry.y1);
//        System.out.println("Y is "+ y + " and  the other is "+(geometry.y2-geometry.y1) +" height is "+height);
//    	System.out.println("The i is"+ i);
    	
        double y = 0;
//        
        if(isString) {
        	y = relData.get(i) ;
        }else if(isNumeric) {
        	y = (relData.get(i)*(geometry.y2-geometry.y1));
        }
//        
//        geometry.r

        return new Point2D.Double(geometry.x1, y);
    }

	public void setHeight(double h) {
		// TODO Auto-generated method stub
		height=h;
		
	}
}

import java.awt.Graphics;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;
    ArrayList<Object> relData;
    double maxNum;
    double minNum;

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
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
    	// type check item if it is a double or String
        // then get the max and the min
        // if String, compare them 
    	double maxNum; 
    	double minNum;
    	
    	for(Object item:data) {
    		System.out.println("The item is "+item);
            if(item instanceof BigDecimal) {
            	// check the min and max
            	System.out.println("Integer");
            	
            }else if(item instanceof String){
            	System.out.println("String "+item.getClass());

            }else {
            	System.out.println("String "+item.getClass());
            	System.out.println("The data is neither a String or BigDecimal");
            	new Exception("Error in checking the instanceof an object");
            }
    	}
        

    }
    
    public void draw(Graphics g) {
    	for(var d:data) {
    		// calculate the x and y 
    	}
    	try {
        	// create an arraylist for x and  the y coordinates
    		// compute the for y coordiantes, y = rel*h
    		// draw the polyline using the array 
    		// and the x
    	}catch(Exception e) {
    		
    	}

    	
    }

    public void debug() {
        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
        for (var d : data) {
            System.out.println(d);
        }
    }
}

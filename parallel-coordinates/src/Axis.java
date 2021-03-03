import java.awt.Graphics;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Axis {
    String columnName;
    //what type is it? (string, numbers)
    ArrayList<Object> data;
    ArrayList<Double> relDataNum;
    ArrayList<String> relDataStr;
    ArrayList<Double>relData;
    

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
        relDataNum = new ArrayList<>();
        relDataStr = new ArrayList<>();
        relData = new ArrayList<>();

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
    	double maxNum = 0; 
    	double minNum = 0;
    	ArrayList<Double> tempDouble =  new ArrayList<>();
    	double uniqueVals = 0;
    	HashSet<String> uniqueSet = new HashSet<String>();

    
    	for(Object item:data) {
//    		System.out.println("The item is "+item);
            if(item instanceof Number) {
            	Number b = (Number)item;
            	double n = b.doubleValue();
//            	System.out.println("the number is" +n);
            	tempDouble.add(n);
            	
            }else if(item instanceof String){
//            	System.out.println("String "+item.getClass());
            	String s = (String)item;
            	System.out.println(s);
            	
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
      		  relDataNum.add(d/maxNum);
      		  System.out.println(d/maxNum);
      	  }
//      	  System.out.println("The Max number is "+ maxNum);
      	  
      	  // do the same for the String 
		
	} catch (Exception e) {
		// TODO: handle exception
		
//		System.out.println("This is the one for the String");
		
	}
    	
    	System.out.println("The number of unique values is " + uniqueVals);
        
    	
    	

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

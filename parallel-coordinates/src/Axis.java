import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Axis {
    String columnName;
    ArrayList<Object> data;
    ArrayList<Double> relDataNum;
    ArrayList<String> relDataStr;
    ArrayList<Double>relData;
    // this is the rawData for the String for sorting 
    ArrayList<String>rawData;
    
    
    // this hash map is for the  String type labels. 
    HashMap<String, Double> unSortedLabels;
    Map<String, Double> sortedLabels;

    private Line2D.Double geometry;
    private double uniqueVals;
    double height;
    boolean isString =false;
    boolean isNumeric = false;
    double max=0;
    double min =0;
    double xPos =0;
    
    

    public Axis(String name) {
        columnName = name;
        data = new ArrayList<>();
        relDataNum = new ArrayList<>();
        relDataStr = new ArrayList<>();
        relData = new ArrayList<>();
        unSortedLabels = new HashMap<>();
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
    	rawData = new ArrayList<>();

//        System.out.println("The Height is "+ height);
    	
    	tempDouble.clear();
    	unSortedLabels.clear();
    	

    	for(Object item:data) {
            if(item instanceof Number) {
            	isNumeric = true;
            	Number b = (Number)item;
            	double n = b.doubleValue();
            	tempDouble.add(n);
            	
            	if (columnName.equals("GRADYEAR")) {
            		isNumeric = false;
            		isString = true;
            		String string = item.toString();
            		
            		if(uniqueSet.add(string)) {
            			uniqueVals++;
            		}
            		
				}else {
					if(n>maxNum) {
                		maxNum =n;
                		max = n;
                	}else {
                		minNum = n;
//                		min = n;
                	}
				}
            	
            }else if(item instanceof String){
            	isString = true;
            	String s = (String)item;
            	
            	// this sets how many unique values you have. 
            	if(uniqueSet.add(s)) {
            		uniqueVals++;
            	}

            }else {
            	System.out.println("String "+item.getClass());
            	System.out.println("The data is neither a String or BigDecimal");
            }
    	}
    	
//    Sort the tempDouble to get the min and Max;
    try {
//    	labels = uniqueSet.toArray();
//    		System.out.println(labels);
    	  Collections.sort(tempDouble); 
//        then loop through the tempDouble to then divide everything by the max number;
//      	  maxNum = tempDouble.get(tempDouble.size()-1);
      	  min = tempDouble.get(0);
      	  for(double d:tempDouble) {
//      		  if(d==2019) {
//      			  relData.add((double) 1);
//      		  }else {
          		  relData.add((d/maxNum));

//      		  }
//      		  System.out.println(d/maxNum);
      	  }
//      	  System.out.println("The Max number is "+ maxNum);
      	  
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e);
	}

    	if(isString) {
        	calcYString(uniqueSet);
    	}

    	

    }


    private void calcYString(HashSet<String> hash) {
    	double tempM = divider();
    	System.out.println("calculating ");
    	ArrayList<String> list = new ArrayList<String>(hash); 
        Collections.sort(list);     	
        try {
			relData.clear();
//			labels.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	try {
    		for(var a: data) {
        		String b =a.toString();
        		int jk = 0;
        		for(var c:list) {
        			if(b.equals(c)) {
                		relData.add(tempM*(jk+1));
                		double tempRel =height-(tempM*(jk+1));
                		unSortedLabels.put(b,tempRel);

        			}
        			jk++;
        		}
        	}
    		
    		try {
    			sortedLabels.clear();
    		} catch (Exception e) {
    			// TODO: handle exception
    		}
    		
    		sortedLabels = new TreeMap<>(unSortedLabels);

		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	
    		

//		
    	
	}
    
    private double divider() {
    	double tempR = height/(uniqueVals+1);
//    	System.out.println("Height is "+ height);
    	return tempR;
    }
    public void debug() {
        System.out.println("PRINTING DATA FOR COLUMN " + columnName);
        for (var d : data) {
            System.out.println(d);
        }
    }

    public void setGeometry(double x, double h) {
    	xPos = x;
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
        	y = height-relData.get(i) ;
        }else if(isNumeric) {
        	y = (height-relData.get(i)*(geometry.y2-geometry.y1));
//        	y = (i+1)*height/(max/(max+(max/2)));
        	
//            if(columnName.equals("GRADYEAR")) {
//            	System.out.println(y+" the relative data is "+relData.get(i));
//            }
        }
//        
//        geometry.r

        return new Point2D.Double(geometry.x1, y);
    }

	public void setHeight(double h) {
		// TODO Auto-generated method stub
		height=h;
		
	}
	
	// this method is sorting the hasmap;
	public void sortData() {
//		try {
//			rawData.clear();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		for(var v: data) {
//			rawData.add(v.toString());
//		}
//		
//		Collections.sort(rawData);
		try {
			sortedLabels.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		sortedLabels = new TreeMap<>(unSortedLabels);
		
	}
	
	public void drawLabels(Graphics2D g) {
		if(isString) {
			// for loop the hashMap
//			setData();
			for(var entry:sortedLabels.entrySet()) {
				double tempY = entry.getValue();
				int yPos = (int)tempY;

				g.drawString(entry.getKey(),(int)xPos,yPos);
//				System.out.println(entry.getKey()+" the x Pos is and the yPos "+" "+xPos+" "+yPos);
			}
		}else if(isNumeric) {
	        double multiplier =1;
	        double yMultiplier =1;
			for(int j=0; j<4;j++) {
	        	 String yValue = String.format("%.2f",max*multiplier);
	        	 g.setColor(Color.RED);
	             g.drawString(yValue, (int)xPos,(int)(height*.04*yMultiplier));
//	             System.out.println("The yPosAxis is "+yPosAxis);
	             multiplier-=.25;    
	             yMultiplier+=10;
	        }
		String yValue = String.format("%.2f",min);
		g.drawString(yValue,(int)xPos,(int)height);
		}
	}
}


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame {

    private Vis mainPanel;
    private List<Axis> axes;

    public Main() {

        axes = new ArrayList<>();

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Put the title of your program here");
        setVisible(true);
    }

    //select count(*) from derbyDB
    // use to test the connection to the DB
    private int runSimpleCountQuery(String q, int year) {
        try {
        	Connection c =null;
        	if(year==2012) {
                c = DriverManager.getConnection("jdbc:derby:scatterPlot");
        	}else if(year == 2019) {
                c = DriverManager.getConnection("jdbc:derby:sethFinal");

        	}else {
        		System.out.println("Will not connect to anything");
        	}
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
            System.out.println("could not connect to Derby!");
            System.out.println(e);
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            System.err.println("  Message:    " + e.getNextException());

            
            return 0;
        }
    }



    private void performUltimateQuery(String q, int year) {
        //List<Point2D> results = new ArrayList<>();
        try {
        	Connection c =null;
        	if(year==2012) {
                c = DriverManager.getConnection("jdbc:derby:scatterPlot");
        	}else if(year == 2019) {
                c = DriverManager.getConnection("jdbc:derby:sethFinal");

        	}else {
        		System.out.println("Will not connect to anything");
        	}
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            System.out.println("The num of column is " + numColumns); 
            for (int i=1; i<=numColumns; i++) {
                Axis seth = new Axis(md.getColumnName(i));
                axes.add(seth);
            }
            while (rs.next()) {
                for (Axis a : axes) {
                    a.extractData(rs);
                }
            }
            for (Axis a : axes) {
                a.debug();
            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem item1 = new JMenuItem("CIS 2012");
        JMenuItem item3 = new JMenuItem("Seth's scatterplot");
        var item4 = new JMenuItem("Perform ultimate query!");
        JMenu subMenu = new JMenu("Submenu");
        JMenuItem item2 = new JMenuItem("Item 2");



        //CIS 2012 DataSet
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// I accidentally created the table name into cis2019 
                System.out.println("Just clicked menu item 1");
                int rows = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019",2012);
                mainPanel.setRows(rows);
                System.out.println("I found " + rows + " rows in the table.");
//                mainPanel.setText("I found " + rows + " rows in the table.");
                // it should be cis2012 but accidentally name it cis2019
                var sethQuery = "SELECT * FROM cis2019";
                performUltimateQuery(sethQuery,2012);

            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        
        item3.addActionListener(e -> {

        });

        item4.addActionListener(e -> {
        	
        	 int rows = runSimpleCountQuery("SELECT COUNT(*) FROM cis2019",2019);
             System.out.println("I found " + rows + " rows in the table.");
             mainPanel.setRows(rows);

//             mainPanel.setText("I found " + gilmo + " rows in the table.");
            var sethQuery = "SELECT * FROM CIS2019";
            performUltimateQuery(sethQuery,2019);
        });


        //now hook them all together
        subMenu.add(item2);
        fileMenu.add(item1);
        fileMenu.add(item3);
        fileMenu.add(item4);
        fileMenu.add(subMenu);
        menuBar.add(fileMenu);

        return menuBar;
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
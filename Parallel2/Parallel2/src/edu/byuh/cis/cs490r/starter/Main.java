package edu.byuh.cis.cs490r.starter;

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

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Put the title of your program here");
        setVisible(true);
    }
    private void performUltimateQuery(String q) {
        //List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyTable2");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                Axis seth = new Axis(md.getColumnName(i));
                axes.add(seth);
            }
            while (rs.next()) {
                for (Axis a : axes) {
                    a.extractData(rs);
                }
            }
            mainPanel.setAxes(axes);
//            for (Axis a : axes) {
//                a.debug();
//            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }
    private void performUltimateQuery2(String q) {
        //List<Point2D> results = new ArrayList<>();
        try {
            Connection c = DriverManager.getConnection("jdbc:derby:MyTable1");
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int numColumns = md.getColumnCount();
            for (int i = 1; i <= numColumns; i++) {
                Axis seth = new Axis(md.getColumnName(i));
                axes.add(seth);
            }
            while (rs.next()) {
                for (Axis a : axes) {
                    a.extractData(rs);
                }
            }
            mainPanel.setAxes(axes);
//            for (Axis a : axes) {
//                a.debug();
//            }
        } catch (SQLException e) {
            System.out.println("could not connect to Derby!");
        }
        //return results;
    }

    private JMenuBar setupMenu() {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        var item1 = new JMenuItem("Table 2012");
        var item2 = new JMenuItem("Table 2019");
        var item3 = new JMenuItem("Reset");

        item1.addActionListener(e -> {
            mainPanel.axes.clear();
            axes.clear();
            var sethQuery = "SELECT * FROM cis2019";
            performUltimateQuery(sethQuery);
            repaint();
        });
        item2.addActionListener(e -> {
            mainPanel.axes.clear();
            axes.clear();
            var sethQuery = "SELECT * FROM cis2019";
            performUltimateQuery2(sethQuery);
            repaint();
        });
        item3.addActionListener(e -> {
            mainPanel.setToNormal();
//            axes.clear();
//            var sethQuery = "SELECT * FROM cis2019";
//            performUltimateQuery2(sethQuery);
//            repaint();
        });


        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);
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

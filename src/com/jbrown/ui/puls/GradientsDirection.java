package com.jbrown.ui.puls;
 
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GradientsDirection extends JPanel {
  public void paint(Graphics g) {
	  super.paint(g);
	    Graphics2D g2d = (Graphics2D) g;
	    GradientPaint gp1 = new GradientPaint(25, 25, Color.blue, 15, 25, Color.black, true);
	    g2d.setPaint(gp1);
	    g2d.fillRect(20, 200, 300, 40);
	    //g2d.drawLine(20, 200, 300, 40);
  }
  public static void main(String[] args) {
    JFrame frame = new JFrame("GradientsDirection");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(new GradientsDirection());
    frame.setSize(350, 350);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
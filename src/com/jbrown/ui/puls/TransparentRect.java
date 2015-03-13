package com.jbrown.ui.puls;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TransparentRect extends JFrame {
    
    public TransparentRect() {
        
        initUI();
    }
    
    private void initUI() {
        
        setTitle("Transparent rectangles");
                
        add(new Surface());
        
        setSize(590, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            
    }
    
    
    public static void main(String[] args) {
        System.out.println("hello");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                TransparentRect tr = new TransparentRect();
                tr.setVisible(true);
            }
        });
    }
}

class Surface extends JPanel {    
    
    private void doDrawing(Graphics g) {        
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        for (int i = 1; i <= 10; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    i * 0.1f));
            //g2d.fillRect(50 * i, 20, 40, 40);
            g2d.fillRect(50 * i, 20, 40, 40);
        }        
    }
        
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}


package com.jbrown.ui.puls;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.javafx.event.EventUtil;

public class TransparentRect extends JFrame {
    
	EventSenserUI _ui;
	
    public TransparentRect() {
        
        initUI();
    }
    
    private void initUI() {
        
        setTitle("Transparent rectangles");
        _ui = new EventSenserUI();
        add(_ui);
        
        setSize(590, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);            
    }
    
    public void sense(){
    	_ui.doSense();
    }
    
    public static void main(String[] args) {
        System.out.println("hello");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                TransparentRect tr = new TransparentRect();
                tr.setVisible(true);
                tr.sense();
                
            }
        });
    }
}

class EventSenserUI extends JPanel {    
    
     private int _i = 1;
     
     public void rePaint(){
    	 this.repaint();
     }
     
     public void doSense(){
    	 SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					_i++;
					try {
						new Thread().sleep(100);
						rePaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println(_i);
					if(_i == 10){
						_i=1;
					}
				}
			}
		});
    	 
    	  
     }
     
    private void doDrawing(Graphics g) {     
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLUE);

        //for (int i = 1; i <= 10; i++) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    _i * 0.1f));
            System.out.println(_i);
            g2d.fillRect(50 * _i, 20, 40, 40);
        //}   
    }
        
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}


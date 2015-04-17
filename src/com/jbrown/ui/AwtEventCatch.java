package com.jbrown.ui;
 
import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class AwtEventCatch {
//    static Toolkit tk = Toolkit.getDefaultToolkit();
//    static long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK
//           + AWTEvent.KEY_EVENT_MASK;

    public static void main(String[] args) {
//        tk.addAWTEventListener(new AWTEventListener() {
//            @Override
//            public void eventDispatched(AWTEvent e) {
//                System.out.println(e.getID() + ", " + e);
//            }
//        }, eventMask);

    	XStepLocalEvent event = new XStepLocalEvent();
    	LocalEventObserver observer = new LocalEventObserver();
    	event.addObserver(observer);
    	
        JFrame test = new JFrame();
        test.getContentPane().setLayout(new GridLayout(10,10));
        test.getContentPane().add(new JTextField(10));
 
        test.setBounds(0, 0, 100, 100);
        test.setVisible(true);
        
        while(true){
        	boolean isLocalEvent = observer.isLocalEvent();
        	if(isLocalEvent){
        		System.out.println(isLocalEvent);
        	}
        }
    }
}

class LocalEventObserver implements Observer {
	private static volatile boolean _isLocalEvent;
	private static volatile AWTEvent _event;
	
	@Override
	public void update(Observable o, Object obj) {
		if(obj != null && obj instanceof AWTEvent){
			_event = (AWTEvent) obj;
			_isLocalEvent = true;
		}
	}
	
	public boolean isLocalEvent(){
		boolean ret = _isLocalEvent;
		_isLocalEvent = false;
		return ret;
	}
}

class XStepLocalEvent extends Observable implements AWTEventListener {
	public XStepLocalEvent() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		long eventMask = AWTEvent.MOUSE_MOTION_EVENT_MASK
				+ AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK;
		tk.addAWTEventListener(this, eventMask);
	}
	
	@Override
	public void eventDispatched(AWTEvent e) {
		setChanged();
		notifyObservers(e);
	}
}
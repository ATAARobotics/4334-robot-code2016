package org.usfirst.frc.team4334.control;

import java.util.Vector;

public class MultiLooper implements Loopable {
    private Looper looper;
    private Vector<Loopable> loopables = new Vector<Loopable>();

    public MultiLooper(String name, double period) {
    	looper = new Looper(name, this, period);
    }

    public void update() {
        int i;
        for (i = 0; i < loopables.size(); ++i) {
            Loopable c = loopables.elementAt(i);
            if (c != null) {
                c.update();
            }
        }
    }

    public void start() {
        looper.start();
        
    }

    public void stop() {
        looper.stop();
    }

    public void addLoopable(Loopable c) {
        loopables.addElement(c);
    }
}


package net.daboross.games.gemagame.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.beans.Transient;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ApplicationMainClass extends JPanel implements MainClass {

    JFrame jFrame;

    public ApplicationMainClass(ObjectHandler objectHandler) {
        jFrame = new JFrame();
        jFrame.setTitle("Gema Game");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        objectHandler.setjFrame(jFrame);
        setBackground(Color.BLACK);
        objectHandler.setFocused(false);
        objectHandler.setApplet(false);
        objectHandler.setMainClass(this);
        objectHandler.setjPanel(this);
        jFrame.add(this);
        jFrame.pack();
        new LoadingScreen(objectHandler);
    }

    public static void main(String[] args) {
        ObjectHandler objectHandler = new ObjectHandler();
        if (args.length > 0) {
            if (args[0].equals("debug")) {
                objectHandler.setDebug(true);
                System.out.println("Debuging");
            } else {
                objectHandler.setDebug(false);
            }
        } else {
            objectHandler.setDebug(false);
        }
        new ApplicationMainClass(objectHandler);
    }

    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    @Override
    public void addKeyListener(KeyListener l) {
        jFrame.addKeyListener(l);
    }

    @Override
    public void removeKeyListener(KeyListener l) {
        jFrame.removeKeyListener(l);
    }

    @Override
    public void addFocusListener(FocusListener l) {
        jFrame.addFocusListener(l);
    }

    @Override
    public void removeFocusListener(FocusListener l) {
        jFrame.removeFocusListener(l);
    }
}
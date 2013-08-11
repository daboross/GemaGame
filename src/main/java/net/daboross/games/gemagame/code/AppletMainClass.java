package net.daboross.games.gemagame.code;

import java.awt.Color;

import javax.swing.JApplet;

/**
 * @author daboross
 *
 */
@SuppressWarnings("serial")
public class AppletMainClass extends JApplet implements MainClass {

    @Override
    /**
     * This is the initial function called by the applet html page or applet
     * viewer
     */
    public void init() {
        System.out.println("Gema Game");
    }

    @Override
    /**
     * This function is called by the applet's html page or applet viewer
     */
    public void start() {
        ObjectHandler objectHandler = new ObjectHandler();
        objectHandler.setDebug(false);
        objectHandler.setMainClass(this);
        objectHandler.setApplet(true);
        objectHandler.setjApplet(this);
        setBackground(Color.BLACK);
        setFocusable(true);
        setVisible(true);
        setForeground(Color.black);
        new LoadingScreen(objectHandler);
    }

    @Override
    /**
     * This is an unused Function
     */
    public void stop() {
        System.out.println("Stopping Gema Game");
    }

    @Override
    /**
     * This is an unused Function
     */
    public void destroy() {
        System.out.println("Destroying Gema Game");
    }
}
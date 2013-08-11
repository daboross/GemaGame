package net.daboross.games.gemagame.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import net.daboross.gameengine.graphics.ImageHandler;
import net.daboross.gameengine.graphics.Paintable;

public class Menu implements Runnable, MouseListener, MouseMotionListener, Paintable {

    private final int numberOfButtons = 2;
    private final int buttonWidth = 200;
    private final int buttonHeight = 50;
    private final int buttonNameOffSet = 15;
    private final int buttonPressedNameOffSet = 10;
    private final int startingButtonVOffSet = 10;
    private int vButtonOffSet;
    private MainClass mainClass;
    private ObjectHandler objectHandler;
    private Image upperImage;
    private Image selectedButton;
    private Image unSelectedButton;
    private int optionSelected;
    private boolean debug, alive;
    private String[] buttonNames;
    /* first box is which button, second box is which value */
    private int[][] buttons;

    public Menu(ObjectHandler objectHandler) {
        objectHandler.setMenu(this);
        debug = objectHandler.isDebug();
        vButtonOffSet = startingButtonVOffSet;
        optionSelected = -1;
        alive = true;
        this.objectHandler = objectHandler;
        mainClass = objectHandler.getMainClass();
        buttonNames = new String[numberOfButtons];
        buttonNames[0] = "Play Levels";
        buttonNames[1] = "Make Levels";
        ImageHandler ih = objectHandler.getImageHandler();
        upperImage = ih.getImage("menu/upperImage.png");
        selectedButton = ih.getImage("menu/selectedButton.png");
        unSelectedButton = ih.getImage("menu/unSelectedButton.png");
    }

    @Override
    public void paint(Graphics2D g) {
        if (upperImage != null) {
            g.drawImage(
                    upperImage,
                    (objectHandler.getScreenWidth() - upperImage.getWidth(null)) / 2,
                    10, null);
        }
        g.setColor(Color.gray);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String pathString = (new File("")).getAbsolutePath();
        g.drawString("Will Save Levels To:", 10, 350);
        g.drawString(pathString, 10, 400);
        g.drawString("/GemaGameLevels/", 10, 450);
        for (int i = 0; i < numberOfButtons; i++) {
            int x = (objectHandler.getScreenWidth() - buttonWidth) / 2;
            int y = 200 + i * (buttonHeight + vButtonOffSet);
            if (i == optionSelected) {
                g.setFont(new Font("Arial", Font.BOLD, 30));
                if (selectedButton != null) {
                    if (selectedButton != null) {
                        g.drawImage(selectedButton, x, y, null);
                    }
                }
                g.drawString(buttonNames[i], buttonPressedNameOffSet, y + 30);
            } else {
                g.setFont(new Font("Arial", Font.PLAIN, 30));
                if (unSelectedButton != null) {
                    if (unSelectedButton != null) {
                        g.drawImage(unSelectedButton, x, y, null);
                    }
                }
                g.drawString(buttonNames[i], buttonNameOffSet, y + 30);
            }
        }
    }

    @Override
    public void run() {
        /* Tells the Image Handler to paint this */
        objectHandler.getImageHandler().setPaintable(this, false);
        if (debug) {
            System.out.println("Running Menu");
        }
        buttons = new int[3][4];
        for (int i = 0; i < numberOfButtons; i++) {
            /* x Pos */
            buttons[i][0] = (objectHandler.getScreenWidth() - 200) / 2;
            /* y Pos */
            buttons[i][1] = 200 + i * (buttonHeight + vButtonOffSet);
            /* x Length */
            buttons[i][2] = buttonWidth;
            /* y Length */
            buttons[i][3] = buttonHeight;
        }
        mainClass.addMouseListener(this);
        mainClass.addMouseMotionListener(this);
        while (alive) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        mainClass.removeMouseListener(this);
        mainClass.removeMouseMotionListener(this);
    }

    public void end() {
        if (optionSelected == 0) {
            alive = false;
            RunLevel runLevel = new RunLevel(objectHandler);
            new LevelLoader(objectHandler);
            Thread runLevelThread = new Thread(runLevel);
            objectHandler.setRunLevelThread(runLevelThread);
            runLevelThread.start();
        } else if (optionSelected == 1) {
            alive = false;
            LevelCreator levelCreator = new LevelCreator(objectHandler);
            Thread levelCreatorThread = new Thread(levelCreator);
            objectHandler.setLevelCreatorThread(levelCreatorThread);
            levelCreatorThread.start();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        boolean notFound = true;
        int x = objectHandler.getImageHandler().screenX(e.getX());
        int y = objectHandler.getImageHandler().screenY(e.getY());
        for (int i = 0; i < buttons.length && notFound; i++) {
            if (Collision.pointOnPlane(x, y, buttons[i][0], buttons[i][1],
                    buttons[i][2], buttons[i][3])) {
                optionSelected = i;
                notFound = false;
            }
        }
        if (notFound) {
            optionSelected = -1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (optionSelected >= 0) {
            end();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
package net.daboross.games.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.daboross.gameengine.FileHandler;
import net.daboross.gameengine.graphics.ButtonReactor;
import net.daboross.gameengine.graphics.ImageHandler;
import net.daboross.gameengine.graphics.OverlayButton;
import net.daboross.gameengine.graphics.OverlayHandler;
import net.daboross.gameengine.graphics.Paintable;

public class LevelCreator implements Runnable, Paintable, KeyListener,
        MouseListener, FocusListener, MouseMotionListener, ButtonReactor {

    private ObjectHandler objectHandler;
    private boolean debug, alive, mouseIn, isMouseOnPlatform, shiftPressed;
    private List<Integer> xPos, yPos, xLengths, yLengths;
    private int countTillSave, countForSave = 1000;
    private int mousePlatformID, numberOfPlatforms, dragDifferenceX,
            dragDifferenceY, mouseX, mouseY, scroll, mouseButton;
    private BackgroundHandler backgroundH;
    private Image platform, selectedPlatform;

    public LevelCreator(ObjectHandler objectHandler) {
        ImageHandler ih = objectHandler.getImageHandler();
        platform = ih.getImage("platform.png");
        selectedPlatform = ih.getImage("platformSelected.png");
        backgroundH = new BackgroundHandler(objectHandler);
        this.objectHandler = objectHandler;
        objectHandler.setLevelCreator(this);
        mouseIn = true;
        mousePlatformID = -1;
        numberOfPlatforms = 0;
        mouseX = objectHandler.getScreenWidth() / 2;
        mouseY = objectHandler.getScreenHeight() / 2;
        List<String> fileLineList = FileHandler.readFile(new File(new File("GemaGameLevels"), "level.txt"));
        if (fileLineList == null) {
            fileLineList = FileHandler.readInternalFile("levels/level.txt");
            if (fileLineList == null) {
                xPos = new ArrayList<Integer>();
                yPos = new ArrayList<Integer>();
                xLengths = new ArrayList<Integer>();
                yLengths = new ArrayList<Integer>();
                numberOfPlatforms = 0;
            } else {
                PlatformList pl = objectHandler.getLevelLoader().loadToList(fileLineList);
                xPos = pl.xPosList;
                yPos = pl.yPosList;
                xLengths = pl.xLengthList;
                yLengths = pl.yLengthList;
                numberOfPlatforms = pl.xPosList.size();
            }
        } else {
            PlatformList pl = objectHandler.getLevelLoader().loadToList(
                    fileLineList);
            xPos = pl.xPosList;
            yPos = pl.yPosList;
            xLengths = pl.xLengthList;
            yLengths = pl.yLengthList;
            numberOfPlatforms = pl.xPosList.size();
        }
        if (debug) {
            System.out.println("Loaded " + numberOfPlatforms + " platforms.");
        }
    }

    @Override
    public void run() {
        objectHandler.getOverlayHandler().addButton("savefile", platform, 0, 0,
                40, 40, this, OverlayHandler.TYPE_BOTH);
        objectHandler.getOverlayHandler().addButton("exit", platform, 50, 0,
                40, 40, this, OverlayHandler.TYPE_BOTH);
        objectHandler.getMainClass().addKeyListener(this);
        objectHandler.getMainClass().addFocusListener(this);
        objectHandler.getOverlayHandler().addMouseListener(this);
        objectHandler.getMainClass().addMouseMotionListener(this);
        alive = true;
        objectHandler.getImageHandler().setPaintable(this, false);
        while (alive) {
            if (mouseIn) {
                if (mouseX > objectHandler.getScreenWidth() - 30) {
                    scroll -= 5;
                    backgroundH.changeDifX(-5, 0);
                }
                if (mouseX < 30) {
                    scroll += 5;
                    backgroundH.changeDifX(5, 0);
                }
                backgroundH.update();
            }
            if (countTillSave <= 0) {
                saveFile();
                countTillSave = countForSave;
            } else {
                countTillSave--;
            }
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
            }
        }
        objectHandler.getOverlayHandler().removeAllButtons();
        objectHandler.getMainClass().removeKeyListener(this);
        objectHandler.getMainClass().removeFocusListener(this);
        objectHandler.getOverlayHandler().removeMouseListener(this);
        objectHandler.getMainClass().removeMouseMotionListener(this);
        Menu menu = new Menu(objectHandler);
        Thread menuThread = new Thread(menu);
        objectHandler.setMenuThread(menuThread);
        menuThread.start();
        saveFile();

    }

    @Override
    public void paint(Graphics2D g) {
        backgroundH.paint(g);
        if (numberOfPlatforms > 0) {
            for (int i = 0; i < numberOfPlatforms; i++) {
                if (i == mousePlatformID) {
                    g.drawImage(selectedPlatform, xPos.get(i) + scroll,
                            yPos.get(i), xLengths.get(i), yLengths.get(i), null);
                } else {
                    g.drawImage(platform, xPos.get(i) + scroll, yPos.get(i),
                            xLengths.get(i), yLengths.get(i), null);
                }
            }
        }
        g.setColor(Color.white);
        g.fillRect(mouseX - 2, mouseY - 2, 4, 4);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            alive = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        xPos.add(mouseX - scroll);
        yPos.add(mouseY);
        xLengths.add(50);
        yLengths.add(50);
        mousePlatformID = numberOfPlatforms++;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseIn = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseIn = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseButton = e.getButton();
        int mX = objectHandler.getImageHandler().screenX(e.getX());
        int mY = objectHandler.getImageHandler().screenY(e.getY());
        boolean isFound = false;
        if (numberOfPlatforms != 0) {
            for (int i = 0; i < numberOfPlatforms; i++) {
                if (Collision.pointOnPlane(mX - scroll, mY, xPos.get(i),
                        yPos.get(i), xLengths.get(i), yLengths.get(i))) {
                    mousePlatformID = i;
                    dragDifferenceX = xPos.get(i) + scroll - mX;
                    dragDifferenceY = yPos.get(i) - mY;
                    isFound = true;
                }
            }
        }
        isMouseOnPlatform = isFound;

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < numberOfPlatforms; i++) {
            if (xLengths.get(i) < 0) {
                xPos.set(i, xPos.get(i) + xLengths.get(i));
                xLengths.set(i, -xLengths.get(i));
            }
            if (yLengths.get(i) < 0) {
                yPos.set(i, yPos.get(i) + yLengths.get(i));
                yLengths.set(i, -yLengths.get(i));
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        mouseIn = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int mX = objectHandler.getImageHandler().screenX(e.getX());
        int mY = objectHandler.getImageHandler().screenY(e.getY());
        if (isMouseOnPlatform) {
            if (mouseButton == MouseEvent.BUTTON3 || shiftPressed) {
                xLengths.set(mousePlatformID,
                        mX - scroll - xPos.get(mousePlatformID));
                yLengths.set(mousePlatformID, mY - yPos.get(mousePlatformID));
            } else {
                xPos.set(mousePlatformID, mX + dragDifferenceX - scroll);
                yPos.set(mousePlatformID, mY + dragDifferenceY);
            }
        }
        mouseX = mX;
        mouseY = mY;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = objectHandler.getImageHandler().screenX(e.getX());
        mouseY = objectHandler.getImageHandler().screenY(e.getY());
    }

    public void saveFile() {
        ArrayList<String> finalLines = new ArrayList<String>();
        for (int i = 0; i < numberOfPlatforms; i++) {
            finalLines.add(xPos.get(i) + " " + yPos.get(i) + " "
                    + xLengths.get(i) + " " + yLengths.get(i));
        }
        if (FileHandler.writeFile(new File(new File("GemaGameLevels"), "level.txt"), finalLines)) {
            if (debug) {
                System.out.println("Wrote File");
            }
        } else {
            for (String str : finalLines) {
                System.out.println(str);
            }
        }
    }

    @Override
    public void buttonReact(OverlayButton button) {
        if (button.getID().equals("savefile")) {
            saveFile();
        }
        if (button.getID().equals("exit")) {
            alive = false;
        }
    }
}

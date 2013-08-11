package net.daboross.games.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import net.daboross.gameengine.FileHandler;
import net.daboross.gameengine.graphics.ImageHandler;
import net.daboross.gameengine.graphics.Paintable;

public class RunLevel implements Runnable, KeyListener, FocusListener, Paintable {

    private boolean debug, paused, alive, escaped, pauseToEscape;
    private final boolean ultraDebug = false;
    /**
     * The Character in this game
     */
    private Character character;
    /**
     * The Background Handler in this game
     */
    private BackgroundHandler backgroundH;
    /**
     * The Game's Platform Handler
     */
    private PlatformHandler platformHandler;
    /**
     * Various image variables that are defined in initial function
     */
    private Image proj0, proj1, proj2, proj3, characterImage, platform,
            pauseOverlay, pauseOverlay2;
    /**
     * These are variables that keep track of whether or not keys are pressed.
     */
    private boolean wPressed, aPressed, dPressed = false;
    /**
     * This variable holds this games MainClass
     */
    private MainClass mainClass;
    private ObjectHandler objectHandler;

    /**
     * This is the init for the RunLevel Function Sets certain things in the
     * program. Gets the images for paint() to use and assigns them to already
     * created variables
     *
     * @param objectHandler this is the game's objectHandler
     */
    public RunLevel(ObjectHandler objectHandler) {
        objectHandler.setRunLevel(this);
        debug = objectHandler.isDebug();
        if (debug) {
            System.out.println("Initializing RunLevel");
        }
        this.objectHandler = objectHandler;
        this.mainClass = objectHandler.getMainClass();
        ImageHandler ih = objectHandler.getImageHandler();
        platform = ih.getImage("platform.png");
        characterImage = ih.getImage("Character.png");
        proj0 = ih.getImage("projectileLeft.png");
        proj1 = ih.getImage("projectileUp.png");
        proj2 = ih.getImage("projectileRight.png");
        proj3 = ih.getImage("projectileDown.png");
        pauseOverlay = ih.getImage("paused.png");
        pauseOverlay2 = ih.getImage("pausedEscape.png");
        /**
         * Creates the Background Handler, Character, and Platform Handler
         * classes
         */
        backgroundH = new BackgroundHandler(objectHandler);
        character = new Character(objectHandler);
        platformHandler = new PlatformHandler(objectHandler);
        LevelLoader levelLoader = new LevelLoader(objectHandler);
        if (!levelLoader.load(FileHandler.readFile(new File(new File("GemaGameLevels"), "level.txt")))) {
            if (debug) {
                System.out.println("Level Created Not Found");
            }
            levelLoader.load(FileHandler.readInternalFile("/daboross/gemagame/data/levels/level.txt"));
        }
    }

    @Override
    /**
     * This function runs the game.
     */
    public void run() {
        alive = true;
        paused = false;
        mainClass.addFocusListener(this);
        mainClass.addKeyListener(this);
        if (debug) {
            System.out.println("Starting RunLevel");
        }
        long timeK = System.nanoTime();
        while (alive) {
            if (debug) {
                System.out.println("UnPausing");
            }

            /* Tells the Image Handler that this is the object to paint */
            objectHandler.getImageHandler().setPaintable(this, false);
            while (alive && !paused) {
                if (ultraDebug) {
                    System.out.println((System.nanoTime() - timeK)
                            + "   :After looping");
                    timeK = System.nanoTime();
                }
                /* Calls Character update Function for Movement Updates */
                character.update(wPressed, aPressed, dPressed);
                if (ultraDebug) {
                    System.out.println((System.nanoTime() - timeK)
                            + "   :After Character updates");
                    timeK = System.nanoTime();
                }
                /*
                 * Calls Background Handler Update Function for Movement Updates
                 */
                backgroundH.update();
                if (ultraDebug) {
                    System.out.println((System.nanoTime() - timeK)
                            + "   :After background Updates");
                    timeK = System.nanoTime();
                }
                if (ultraDebug) {
                    System.out.println((System.nanoTime() - timeK)
                            + "   :After Painting");
                    timeK = System.nanoTime();
                }
                /* Tries to sleep the thread for 17 milliseconds */
                try {
                    Thread.sleep(20);
                } catch (Exception e) {
                    if (debug) {
                        System.out.println("Exception Caught in RunLevel");
                        e.printStackTrace();
                    }
                }
                if (ultraDebug) {
                    System.out.println((System.nanoTime() - timeK)
                            + "   :After Pausing");
                    timeK = System.nanoTime();
                }
            }
            if (debug) {
                System.out.println("Pausing");
            }
            try {
                Thread.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mainClass.removeKeyListener(this);
        mainClass.removeFocusListener(this);
        Menu menu = new Menu(objectHandler);
        Thread menuThread = new Thread(menu);
        objectHandler.setMenuThread(menuThread);
        menuThread.start();
    }

    /**
     * This will paint all the game objects given a graphics (g)
     *
     * @param g This is the graphics to paint all the objects onto
     */
    @Override
    public void paint(Graphics2D g) {
        /* This loop goes through and draws each layer of background */
        backgroundH.paint(g);
        /* This loop goes through and draws each platform in PlatformHandler */
        for (int i = 0; i < platformHandler.listLength(); i++) {
            g.drawImage(
                    platform,
                    (int) (platformHandler.xPosList(i) + backgroundH.getDifX()),
                    (int) (platformHandler.yPosList(i) + 0),
                    (int) (platformHandler.xLengthList(i) + 0),
                    (int) (platformHandler.yLengthList(i) + 0), null);
        }
        if (debug) {
            g.setColor(Color.CYAN);
            g.drawRect(
                    (int) character.getLeftLimit(),
                    (int) character.getTopLimit(),
                    (int) (character.getRightLimit() - character.getLeftLimit()),
                    (int) (character.getBottomLimit() - character.getTopLimit()));
        }
        Graphics2D g2 = (Graphics2D) g;
        /* Move the graphics to the characters location */
        int transX = character.getCenterX();
        int transY = character.getCenterY();
        g2.translate(transX, transY);
        /* Rotates the graphics, which will make the drawn image rotated */
        double rotate = character.rotation();
        g2.rotate(rotate);
        /* Draws the image with the characterImage, lengthX, and lengthY */
        g2.drawImage(characterImage, -character.lengthX, -character.lengthY,
                null);
        /* UnRotates the graphics so that the other objects aren't rotated. */
        g2.rotate(-rotate);
        /*
         * UnTranslates the graphics so that the other objects aren't Translated
         */
        g2.translate(-transX, -transY);
        // Goes through and draws every Character projectile on the screen
        ArrayList<Projectile> projectiles = character.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            /*
             * Checks what direction the projectile is facing, so that the
             * correct image is used. proj0-3 images represent the four
             * directions(up,right,down,left(not necessarily in that order) The
             * projectile class keeps track of what direction it is facing with
             * an integer.
             */
            switch (p.getDirection()) {
                case 0:
                    g.drawImage(proj0, (int) p.getCenterX() - 1,
                            (int) p.getCenterY() - 1, null);
                    break;
                case 1:
                    g.drawImage(proj1, (int) p.getCenterX() - 1,
                            (int) p.getCenterY() - 1, null);
                    break;
                case 2:
                    g.drawImage(proj2, (int) p.getCenterX() - 1,
                            (int) p.getCenterY() - 1, null);
                    break;
                case 3:
                    g.drawImage(proj3, (int) p.getCenterX() - 1,
                            (int) p.getCenterY() - 1, null);
                    break;

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // Records all key presses into variables
        int eventChar = keyEvent.getKeyCode();
        if (!paused) {
            if (eventChar == KeyEvent.VK_W && !wPressed) {
                /*
                 * If w is pressed, and the variable that keeps track of whether
                 * w is pressed (wPressed) is not true, then set wPressed to
                 * true
                 */
                wPressed = true;
            }
            if (eventChar == KeyEvent.VK_A && !aPressed) {
                /*
                 * If a is pressed, and the variable that keeps track of whether
                 * a is pressed (aPressed) is not true, then set aPressed to
                 * true
                 */
                aPressed = true;
            }
            if (eventChar == KeyEvent.VK_D && !dPressed) {
                /*
                 * If d is pressed, and the variable that keeps track of whether
                 * d is pressed (dPressed) is not true, then set dPressed to
                 * true
                 */
                dPressed = true;
            }
            if (eventChar == KeyEvent.VK_UP) {
                /*
                 * every refresh that up key is pressed, launch the character
                 * shoot function with a x velocity of 0 and a y velocity of -1,
                 * meaning going up. Note that this function (character.shoot)
                 * tells the character to shoot after the timer has counted down
                 * from that last shoot, so this part does not need to keep
                 * track of that.
                 */
                character.shoot(0, -1);
            }
            if (eventChar == KeyEvent.VK_DOWN) {
                /*
                 * every refresh that up key is pressed, launch the character
                 * shoot function with a x velocity of 0 and a y velocity of 1,
                 * meaning going down. Note that this function (character.shoot)
                 * tells the character to shoot after the timer has counted down
                 * from that last shoot, so this part does not need to keep
                 * track of that.
                 */
                character.shoot(0, 1);
            }
            if (eventChar == KeyEvent.VK_LEFT) {
                /*
                 * every refresh that up key is pressed, launch the character
                 * shoot function with a x velocity of -1 and a y velocity of 0,
                 * meaning going left. Note that this function (character.shoot)
                 * tells the character to shoot after the timer has counted down
                 * from that last shoot, so this part does not need to keep
                 * track of that.
                 */
                character.shoot(-1, 0);
            }
            if (eventChar == KeyEvent.VK_RIGHT) {
                /*
                 * every refresh that up key is pressed, launch the character
                 * shoot function with a x velocity of 1 and a y velocity of 0,
                 * meaning going right. Note that this function
                 * (character.shoot) tells the character to shoot after the
                 * timer has counted down from that last shoot, so this part
                 * does not need to keep track of that.
                 */
                character.shoot(1, 0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        /* When a key is released, resets the key variable */
        int eventChar = keyEvent.getKeyCode();
        /* This defines the integer to represent the key release */
        if (!paused) {
            if (eventChar == KeyEvent.VK_W && wPressed) {
                /*
                 * If a is released, and the variable was true, set the variable
                 * to false. wPressed keeps track of if w was lasted pressed or
                 * released
                 */
                wPressed = false;
            }
            if (eventChar == KeyEvent.VK_A && aPressed) {
                /*
                 * If a is released, and the variable was true, set the variable
                 * to false. aPressed keeps track of if a was lasted pressed or
                 * released
                 */
                aPressed = false;
            }
            if (eventChar == KeyEvent.VK_D) {
                /*
                 * If a is released, and the variable was true, set the variable
                 * to false. dPressed keeps track of if d was lasted pressed or
                 * released
                 */
                dPressed = false;
            }
            if (eventChar == KeyEvent.VK_ESCAPE) {
                paused = true;
                escaped = true;
                pauseToEscape = true;
                objectHandler.getOverlayHandler()
                        .setPauseOverlay(pauseOverlay2);
            }
        } else if (escaped) {
            if (eventChar == KeyEvent.VK_ESCAPE) {
                paused = false;
                escaped = false;
                pauseToEscape = false;
                objectHandler.getOverlayHandler().removePauseOverlay();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (pauseToEscape) {
            escaped = true;
            paused = true;
            objectHandler.getOverlayHandler().setPauseOverlay(pauseOverlay2);
        } else {
            paused = false;
            objectHandler.getOverlayHandler().removePauseOverlay();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (escaped) {
            pauseToEscape = true;
            escaped = false;
            paused = true;
            objectHandler.getOverlayHandler().setPauseOverlay(pauseOverlay);
        } else {
            paused = true;
            objectHandler.getOverlayHandler().setPauseOverlay(pauseOverlay);
        }
        wPressed = false;
        aPressed = false;
        dPressed = false;
    }
}
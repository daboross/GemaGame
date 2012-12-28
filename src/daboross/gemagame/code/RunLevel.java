package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import daboross.code.engine.FileHandler;
import daboross.code.engine.ImageHandler;

public class RunLevel implements Runnable, KeyListener, FocusListener,
		Paintable {
	private boolean paused, alive, escaped, pauseToEscape;
	/** The Character in this game */
	private Character character;
	/** The Background Handler in this game */
	private BackgroundHandler backgroundH;
	/** The Game's Platform Handler */
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
	 * This is an array that holds all the different background images
	 */
	private Image[] backgroundImages;
	/** This variable holds this games MainClass */
	private MainClass mainClass;
	private ObjectHandler objectHandler;

	/**
	 * This is the init for the RunLevel Function Sets certain things in the
	 * program. Gets the images for paint() to use and assigns them to already
	 * created variables
	 * 
	 * @param objectHandler
	 *            this is the game's objectHandler
	 */
	public RunLevel(ObjectHandler objectHandler) {
		objectHandler.setRunLevel(this);
		System.out.println("Initializing RunLevel");
		this.objectHandler = objectHandler;
		this.mainClass = objectHandler.getMainClass();
		backgroundImages = new Image[1];
		ImageHandler ih = objectHandler.getImageHandler();
		backgroundImages[0] = ih.getImage("Background.png");
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
		if (objectHandler.getjFrame() == null) {
			try {
				levelLoader.load(FileHandler.ReadInternalFile(
						"levels/level.txt", objectHandler.getMainClass()));
			} catch (Exception e) {
				System.out.println("Loading Internal File Failed");
			}
		} else {
			try {
				levelLoader.load(FileHandler
						.ReadFile("GemaGameLevels/level.txt"));
			} catch (Exception e) {
				System.out.println("Level Created Not Found");
				try {
					levelLoader.load(FileHandler.ReadInternalFile(
							"levels/level.txt", objectHandler.getMainClass()));
				} catch (Exception ex) {
					System.out.println("Loading Internal File Failed");
					ex.printStackTrace();
				}
				try {
					FileHandler.WriteFile("GemaGameLevels/", "level.txt",
							FileHandler.ReadInternalFile("levels/level.txt",
									objectHandler.getMainClass()));
				} catch (Exception ex) {
					System.out.println("Loading Internal File Failed");
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	/**This function runs the game.*/
	public void run() {
		alive = true;
		paused = false;
		mainClass.addFocusListener(this);
		mainClass.addKeyListener(this);
		System.out.println("Starting RunLevel");
		while (alive) {
			while (alive && !paused) {
				/* Calls Character update Function for Movement Updates */
				character.update(wPressed, aPressed, dPressed);
				/*
				 * Calls Background Handler Update Function for Movement Updates
				 */
				backgroundH.update();
				/* Repaints the screen */
				objectHandler.getImageHandler().paint(this);
				/* Tries to sleep the thread for 17 milliseconds */
				try {
					Thread.sleep(17);
				} catch (InterruptedException e) {
				}
			}
			while (alive && paused) {
				objectHandler.getImageHandler().paint(this);
				try {
					Thread.sleep(68);
				} catch (InterruptedException e) {
				}
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
	 * @param g
	 *            This is the graphics to paint all the objects onto
	 */
	@Override
	public void paint(Graphics g) {
		Graphics gtp = null;
		BufferedImage pauseImage = null;
		if (paused) {
			gtp = g;
			pauseImage = new BufferedImage(640, 480,
					BufferedImage.TYPE_INT_ARGB);
			g = pauseImage.getGraphics();
			g.drawImage(pauseOverlay, 0, 0, null);
		}
		/* This loop goes through and draws each layer of background */
		for (int i = 0; i < backgroundH.getNumberLayers(); i++) {
			for (int k = 0; k < 2; k++) {
				int xPos = (int) backgroundH.getBgX(k, i);
				int yPos = (int) backgroundH.getBgY(k, i);
				g.drawImage(backgroundImages[0], xPos, yPos, null);
			}
		}
		/* This loop goes through and draws each platform in PlatformHandler */
		for (int i = 0; i < platformHandler.listLength(); i++) {
			g.drawImage(
					platform,
					(int) (platformHandler.xPosList(i) + backgroundH.getDifX()),
					(int) (platformHandler.yPosList(i) + 0),
					(int) (platformHandler.xLengthList(i) + 0),
					(int) (platformHandler.yLengthList(i) + 0), null);
		}
		g.setColor(Color.CYAN);

		/*
		 * g.drawRect((int) character.getLeftLimit(), (int)
		 * character.getTopLimit(), (int) (character.getRightLimit() -
		 * character.getLeftLimit()), (int) (character.getBottomLimit() -
		 * character.getTopLimit()));
		 */
		/* Get a graphics2d for better manipulation */
		Graphics2D g2d = (Graphics2D) g;
		/* Move the graphics to the characters location */
		g2d.translate(character.getCenterX(), character.getCenterY());
		/* Rotates the graphics, which will make the drawn image rotated */
		double rotate = character.rotation();
		g2d.rotate(rotate);
		/* Draws the image with the characterImage, lengthX, and lengthY */
		g2d.drawImage(characterImage, -character.lengthX, -character.lengthY,
				null);
		/* UnRotates the graphics so that the other objects aren't rotated. */
		g2d.rotate(-rotate);
		/*
		 * UnTranslates the graphics so that the other objects aren't Translated
		 */
		g2d.translate(-character.getCenterX(), -character.getCenterY());
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
		if (paused) {
			if (escaped) {
				g.drawImage(pauseOverlay2, 0, 0, null);
			} else {
				g.drawImage(pauseOverlay, 0, 0, null);
			}
			gtp.drawImage(pauseImage, 0, 0, null);
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
			}
		} else if (escaped) {
			if (eventChar == KeyEvent.VK_ESCAPE) {
				paused = false;
				escaped = false;
				pauseToEscape = false;
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
		} else {
			paused = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (escaped) {
			pauseToEscape = true;
			escaped = false;
			paused = true;
		} else {
			paused = true;
		}
		wPressed = false;
		aPressed = false;
		dPressed = false;
	}
}
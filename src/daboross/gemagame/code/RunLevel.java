package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class RunLevel implements Runnable, KeyListener, Paintable {
	private boolean alive;
	/** The Character in this game */
	private Character character;
	/** The Background Handler in this game */
	private BackgroundHandler backgroundH;
	/** The Game's Platform Handler */
	private PlatformHandler platformHandler;
	/**
	 * Various image variables that are defined in initial function
	 */
	private Image proj0, proj1, proj2, proj3, characterImage, platform;
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
	private ClassHandler classHandler;

	/**
	 * This is the init for the RunLevel Function Sets certain things in the
	 * program. Gets the images for paint() to use and assigns them to already
	 * created variables
	 * 
	 * @param mainClass
	 *            This is this Game's mainClass
	 */
	public RunLevel(ClassHandler classHandler) {
		classHandler.setRunLevel(this);
		System.out.println("Initializing RunLevel");
		this.classHandler = classHandler;
		this.mainClass = classHandler.getMainClass();
		backgroundImages = new Image[1];
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			if (classHandler.getjFrame() == null) {
				String baseURL = "daboross/gemagame/data/images/";
				backgroundImages[0] = tk
						.createImage(baseURL + "Background.png");
				platform = tk.createImage(baseURL + "platform.png");
				characterImage = tk.createImage(baseURL + "Character.png");
				proj0 = tk.createImage(baseURL + "projectileLeft.png");
				proj1 = tk.createImage(baseURL + "projectileUp.png");
				proj2 = tk.createImage(baseURL + "projectileRight.png");
				proj3 = tk.createImage(baseURL + "projectileDown.png");
			} else {
				String baseURL = "/daboross/gemagame/data/images/";
				Class<? extends JFrame> j = classHandler.getjFrame().getClass();
				backgroundImages[0] = tk.createImage(j.getResource(baseURL
						+ "Background.png"));
				platform = tk.createImage(j.getResource(baseURL
						+ "platform.png"));
				characterImage = tk.createImage(j.getResource(baseURL
						+ "Character.png"));
				proj0 = tk.createImage(j.getResource(baseURL
						+ "projectileLeft.png"));
				proj1 = tk.createImage(j.getResource(baseURL
						+ "projectileUp.png"));
				proj2 = tk.createImage(j.getResource(baseURL
						+ "projectileRight.png"));
				proj3 = tk.createImage(j.getResource(baseURL
						+ "projectileDown.png"));

			}
			System.out.println("Loaded Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed");
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				if (classHandler.getjFrame() != null) {
					String baseURL = ((AppletMainClass) classHandler
							.getMainClass()).getDocumentBase()
							+ "daboross/gemagame/data/images/menu/";
					platform = tk.createImage(baseURL + "platform.png");
					characterImage = tk.createImage(baseURL + "Character.png");
					proj0 = tk.createImage(baseURL + "projectileLeft.png");
					proj1 = tk.createImage(baseURL + "projectileUp.png");
					proj2 = tk.createImage(baseURL + "projectileRight.png");
					proj3 = tk.createImage(baseURL + "projectileDown.png");
				}
				System.out.println("Loaded Menu Images");
			} catch (Exception ew) {
				e.printStackTrace();
				System.out.println("Menu Load Images Failed");
			}
		}
		/**
		 * Creates the Background Handler, Character, and Platform Handler
		 * classes
		 */
		backgroundH = new BackgroundHandler(classHandler);
		character = new Character(classHandler);
		platformHandler = new PlatformHandler(classHandler);
		LevelLoader levelLoader = new LevelLoader(classHandler);
		levelLoader.loadTxt("levels/level.txt");

	}

	@Override
	/**This function runs the game.*/
	public void run() {
		alive = true;
		mainClass.keyListenerAdd(this);
		System.out.println("Starting RunLevel");
		while (alive) {
			/* Calls Character update Function for Movement Updates */
			character.update(wPressed, aPressed, dPressed);
			/*
			 * Calls Background Handler Update Function for Movement Updates
			 */
			backgroundH.update();
			/* Repaints the screen */
			mainClass.paint(this);
			/* Tries to sleep the thread for 17 milliseconds */
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Menu menu = new Menu(classHandler);
		Thread menuThread = new Thread(menu);
		classHandler.setMenuThread(menuThread);
		menuThread.start();
	}

	/**
	 * This will paint all the game objects given a graphics (g)
	 * 
	 * @param g
	 *            This is the graphics to paint all the objects onto
	 */
	public void paint(Graphics g) {
		// This loop goes through and draws each layer of background
		for (int i = 0; i < backgroundH.getNumberLayers(); i++) {
			for (int k = 0; k < 2; k++) {
				int xPos = (int) backgroundH.getBgX(k, i);
				int yPos = (int) backgroundH.getBgY(k, i);
				g.drawImage(backgroundImages[0], xPos, yPos, null);
			}
		}
		// This loop goes through and draws each platform in PlatformHandler
		for (int i = 0; i < platformHandler.listLength(); i++) {
			g.drawImage(platform,
					(int) (platformHandler.xPosList().get(i) + backgroundH
							.getDifX()),
					(int) (platformHandler.yPosList().get(i) + 0),
					(int) (platformHandler.xLengthList().get(i) + 0),
					(int) (platformHandler.yLengthList().get(i) + 0), null);
		}
		g.setColor(Color.CYAN);

		/*
		 * g.drawRect((int) character.getLeftLimit(), (int)
		 * character.getTopLimit(), (int) (character.getRightLimit() -
		 * character.getLeftLimit()), (int) (character.getBottomLimit() -
		 * character.getTopLimit()));
		 */
		// Get a graphics2d for better manipulation
		Graphics2D g2d = (Graphics2D) g;
		// Move the graphics to the characters location
		g2d.translate(character.getCenterX(), character.getCenterY());
		// Rotates the graphics, which will make the drawn image rotated
		double rotate = character.rotation();
		g2d.rotate(rotate);
		// Draws the image with the characterImage, lengthX, and lengthY
		g2d.drawImage(characterImage, -character.lengthX, -character.lengthY,
				null);
		// UnRotates the graphics so that the other objects aren't rotated.
		g2d.rotate(-rotate);
		// UnTranslates the graphics so that the other objects aren't
		// Translated
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
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// Records all key presses into variables
		int eventChar = keyEvent.getKeyCode();
		if (eventChar == KeyEvent.VK_W && !wPressed) {
			// If w is pressed, and the variable that keeps track of whether w
			// is pressed (wPressed) is not true, then set wPressed to true
			wPressed = true;
		}
		if (eventChar == KeyEvent.VK_A && !aPressed) {
			// If a is pressed, and the variable that keeps track of whether a
			// is pressed (aPressed) is not true, then set aPressed to true
			aPressed = true;
		}
		if (eventChar == KeyEvent.VK_D && !dPressed) {
			// If d is pressed, and the variable that keeps track of whether d
			// is pressed (dPressed) is not true, then set dPressed to true
			dPressed = true;
		}
		if (eventChar == KeyEvent.VK_UP) {
			// every refresh that up key is pressed, launch the character shoot
			// function with a x velocity of 0 and a y velocity of -1, meaning
			// going up. Note that this function (character.shoot) tells the
			// character to shoot after the timer has counted down from that
			// last shoot, so this part does not need to keep track of that.
			character.shoot(0, -1);
		}
		if (eventChar == KeyEvent.VK_DOWN) {
			// every refresh that up key is pressed, launch the character shoot
			// function with a x velocity of 0 and a y velocity of 1, meaning
			// going down. Note that this function (character.shoot) tells the
			// character to shoot after the timer has counted down from that
			// last shoot, so this part does not need to keep track of that.
			character.shoot(0, 1);
		}
		if (eventChar == KeyEvent.VK_LEFT) {
			// every refresh that up key is pressed, launch the character shoot
			// function with a x velocity of -1 and a y velocity of 0, meaning
			// going left. Note that this function (character.shoot) tells the
			// character to shoot after the timer has counted down from that
			// last shoot, so this part does not need to keep track of that.
			character.shoot(-1, 0);
		}
		if (eventChar == KeyEvent.VK_RIGHT) {
			// every refresh that up key is pressed, launch the character shoot
			// function with a x velocity of 1 and a y velocity of 0, meaning
			// going right. Note that this function (character.shoot) tells the
			// character to shoot after the timer has counted down from that
			// last shoot, so this part does not need to keep track of that.
			character.shoot(1, 0);
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// When a key is released, resets the key variable
		int eventChar = keyEvent.getKeyCode();
		// This defines the integer to represent the key release

		if (eventChar == KeyEvent.VK_W && wPressed) {
			// If a is released, and the variable was true, set the variable to
			// false. wPressed keeps track of if w was lasted pressed or
			// released
			wPressed = false;
		}
		if (eventChar == KeyEvent.VK_A && aPressed) {
			// If a is released, and the variable was true, set the variable to
			// false. aPressed keeps track of if a was lasted pressed or
			// released
			aPressed = false;
		}
		if (eventChar == KeyEvent.VK_D) {
			// If a is released, and the variable was true, set the variable to
			// false. dPressed keeps track of if d was lasted pressed or
			// released
			dPressed = false;
		}
		if (eventChar == KeyEvent.VK_ESCAPE) {
			alive = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
	}
}
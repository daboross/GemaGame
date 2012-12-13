package code;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

@SuppressWarnings({ "serial" })
public class MainClass extends Applet implements Runnable, KeyListener {
	// defines the width and height that the applet will act as if it is
	private int height = 480;
	private int width = 640;

	// Variables are defined in Initial function
	private Character character; // Character
	private static BackgroundHandler backgroundH; // Background Handler
	private static PlatformHandler platformHandler; // Platform Handler

	private Graphics second; // Graphics variable used in painting objects
	private Image image, proj0, proj1, proj2, proj3, characterImage, platform;
	// Various image variables that are defined in initial function
	private URL base; // The base of this applet's .jar
	private boolean wPressed, sPressed, aPressed, dPressed, upPressed,
			downPressed, leftPressed, rightPressed = false;
	// These are variables that keep track of whether or not keys are pressed

	private Image[] backgroundImages;// This is an array that holds all the
										// different background images
	private boolean drawImage = false;// This variable flips every tick, and it
										// makes it so it only draws images
										// every other tick
	private int imageTranslationX, imageTranslationY = 0; // These variables
	private int contractedImageX, contractedImageY;
	// These variables record the amount that the main graphics is Translated
	// and Contracted
	private int rememberWidth, rememberHeight;
	// These variables remember the last Width and Height that the screen was,
	// so that the Applet knows if it needs to resize its graphics
	private int[][] drawRect;

	// Initial Method.
	// Gets images and sets up program.
	@Override
	public void init() {
		// Sets certain things in the program.
		setSize(640, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Gets the images for paint() to use and assigns them to already
		// created variables
		backgroundImages = new Image[1];
		backgroundImages[0] = getImage(base, "images/contrastBackground.png");
		platform = getImage(base, "images/platform.png");
		characterImage = getImage(base, "images/Character.png");
		proj0 = getImage(base, "images/projectileLeft.png");
		proj1 = getImage(base, "images/projectileUp.png");
		proj2 = getImage(base, "images/projectileRight.png");
		proj3 = getImage(base, "images/projectileDown.png");
	}

	@Override
	public void start() {
		// Creates the Background Handler, Character, and Platform Handler
		// classes
		backgroundH = new BackgroundHandler();
		character = new Character();
		platformHandler = new code.PlatformHandler();
		for (int i = -10; i < 11; i++) {
			platformHandler.addPlatForm(i * 30,
					(int) ((Math.random() * height)), 100, 50);
		}
		// Starts this thread/applet
		Thread Thread = new Thread(this);
		Thread.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void run() {
		// Removes any non-alive projectiles from the projectile list
		while (true) {
			ArrayList<Projectile> projectiles = character.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				if (p.isAlive()) {
					p.update(width + 5);
				} else {
					projectiles.remove(i);
				}
			}
			// Calls Character update Function for Movement Updates
			character.update(wPressed, sPressed, aPressed, dPressed, width,
					height);

			if (drawImage) {
				// Calls Background Handler Update Function for Movement Updates
				backgroundH.update();

				// repaints the screen
				repaint();
			} else {
				drawImage = true;
			}
			// Tries to sleep the thread for 17 milliseconds
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Graphics g) {
		// defines image if it does not exist.
		if (image == null) {
			image = createImage(width, height);
			second = image.getGraphics();
			rememberWidth = this.getWidth();
			rememberHeight = this.getHeight();
			int contractedImageSubtractionX = 0;
			int contractedImageSubtractionY = 0;
			if (height > this.getHeight()) {
				contractedImageY = this.getHeight();
				contractedImageX = this.getHeight() / height * width;
			}
			if (width > contractedImageX) {
				contractedImageX = this.getWidth();
				contractedImageY = contractedImageX / width * height;
			}
			System.out.println("ContImSubX: " + contractedImageX
					+ " ContImSubY: " + contractedImageY);

			imageTranslationX = (this.getWidth() - contractedImageX) / 2;
			imageTranslationY = (this.getHeight() - contractedImageY) / 2;
			drawRect = new int[4][4];
			setDrawRect(0, 0, 0, imageTranslationX, this.getHeight());
			setDrawRect(1, 0, 0, this.getWidth(), imageTranslationY);
			setDrawRect(2, this.getWidth() - imageTranslationX - 1, 0,
					imageTranslationX + 1, this.getHeight());
			setDrawRect(3, 0, this.getHeight() - imageTranslationY - 1,
					this.getWidth(), imageTranslationY + 1);
		}

		// If the screen is not the same size at remembered, then re-run the
		// image transformations
		if (!(rememberWidth == this.getWidth() && rememberHeight == this
				.getHeight())) {
			rememberWidth = this.getWidth();
			rememberHeight = this.getHeight();
			if (height > this.getHeight()) {
				contractedImageY = this.getHeight();
				contractedImageX = this.getHeight() / height * width;
			}
			if (width > contractedImageX) {
				contractedImageX = this.getWidth();
				contractedImageY = contractedImageX / width * height;
			}
			System.out.println("ContImSubX: " + contractedImageX
					+ " ContImSubY: " + contractedImageY);
			imageTranslationX = (this.getWidth() - contractedImageX) / 2;
			imageTranslationY = (this.getHeight() - contractedImageY) / 2;
			drawRect = new int[4][4];
			setDrawRect(0, 0, 0, imageTranslationX, this.getHeight());
			setDrawRect(1, 0, 0, this.getWidth(), imageTranslationY);
			setDrawRect(2, this.getWidth() - imageTranslationX - 1, 0,
					imageTranslationX + 1, this.getHeight());
			setDrawRect(3, 0, this.getHeight() - imageTranslationY,
					this.getWidth(), imageTranslationY);

		}
		// clears the screen
		second.setColor(getBackground());
		second.fillRect(0, 0, this.getWidth(), this.getHeight());
		second.setColor(getForeground());
		// Runs the Paint method in order to get the images for all the objects
		// on the screen.
		paint(second);
		// draws the Image with the translations that were already defined, to
		// make it in the center of the screen
		g.drawImage(image, imageTranslationX, imageTranslationY,
				contractedImageX, contractedImageY, this); // TODO
		for (int k = 0; k < 4; k++) {
			g.fillRect(drawRect[0][k], drawRect[1][k], drawRect[2][k],
					drawRect[3][k]);
		}// This draws rectangles of black around the image in order
	}

	@Override
	// draws images for all the objects
	public void paint(Graphics g) {
		// This loop goes through and draws each layer of background
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < backgroundH.getNumberLayers(); i++) {
				int xPos = (int) backgroundH.getBgX(k, i);
				int yPos = (int) backgroundH.getBgY(k, i);
				g.drawImage(backgroundImages[0], xPos, yPos, this);
			}
		}

		// This loop goes through and draws each platform in PlatformHandler
		for (int i = 0; i <= platformHandler.listLength(); i++) {
			g.drawImage(platform,
					(int) (platformHandler.xPosList().get(i) + backgroundH
							.getDifX()),
					(int) (platformHandler.yPosList().get(i) + 0),
					(int) (platformHandler.xLengthList().get(i) + 0),
					(int) (platformHandler.yLengthList().get(i) + 0), this);
		}
		// Get a graphics2d for better manipulation
		Graphics2D g2d = (Graphics2D) g;
		// Move the graphics to the characters location
		g2d.translate(character.getCenterX(), character.getCenterY());
		// Rotates the graphics, which will make the drawn image rotated
		double rotate = character.rotation();
		g2d.rotate(rotate);
		// Draws the image with the characterImage, lengthX, and lengthY
		g2d.drawImage(characterImage, -character.lengthX, -character.lengthY,
				this);
		// Unrotates the graphics so that the other objects aren't rotated.
		g2d.rotate(-rotate);
		// Untranslates the graphics so that the other objects aren't
		// Translated
		g2d.translate(-character.getCenterX(), -character.getCenterY());
		// Goes through and draws every Character projectile on the screen
		ArrayList<Projectile> projectiles = character.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			// Checks what direction the projectile is facing, so that the
			// correct image is used
			switch (p.getDirection()) {
			case 0:
				g.drawImage(proj0, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 1:
				g.drawImage(proj1, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 2:
				g.drawImage(proj2, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 3:
				g.drawImage(proj3, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// Records all key presses into variables
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_W:
			if (!wPressed) {
				wPressed = true;
			}
			break;
		case KeyEvent.VK_A:
			if (!aPressed) {
				aPressed = true;
			}
			break;
		case KeyEvent.VK_D:
			if (!dPressed) {
				dPressed = true;
			}
			break;
		case KeyEvent.VK_UP:
			if (!upPressed) {
				upPressed = true;
			}
			character.shoot(0, -1);
			break;
		case KeyEvent.VK_DOWN:
			if (!downPressed) {
				downPressed = true;
			}
			character.shoot(0, 1);
			break;
		case KeyEvent.VK_LEFT:
			if (!leftPressed) {
				leftPressed = true;
			}
			character.shoot(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			if (!rightPressed) {
				rightPressed = true;
			}
			character.shoot(1, 0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// When a key is released, resets the key variable
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_W:
			if (wPressed) {
				wPressed = false;
			}
			break;
		case KeyEvent.VK_A:
			if (aPressed) {
				aPressed = false;
			}
			break;
		case KeyEvent.VK_D:
			if (dPressed) {
				dPressed = false;
			}
			break;
		case KeyEvent.VK_UP:
			if (upPressed) {
				upPressed = false;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (downPressed) {
				downPressed = false;
			}
			break;
		case KeyEvent.VK_LEFT:
			if (leftPressed) {
				leftPressed = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (rightPressed) {
				rightPressed = false;
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Unused Function
	}

	public static void changeBg(double changeX, double changeY) {
		// Forwards function to the Background Handler
		backgroundH.changeDifX(changeX, changeY);
	}

	public static BackgroundHandler background() {
		// Returns the background handler for other classes to use
		return backgroundH;
	}

	public static double xDif() {
		// Gets the x Difference from the background handler for other classes
		// to use
		return backgroundH.getDifX();
	}

	public static PlatformHandler getPlatformHandler() {
		// Returns the Platform Handler for other classes to use
		return platformHandler;
	}

	public void addPlatform(double xPos, double yPos, double xLength,
			double yLength) {
		// Forwards the add platform function to the platform handler
		platformHandler.addPlatForm(xPos, yPos, xLength, yLength);
	}

	private void setDrawRect(int drawNumber, int xPos, int yPos, int xLength,
			int yLength) {
		// This is a helper function for the graphics function
		drawRect[0][drawNumber] = xPos;
		drawRect[1][drawNumber] = yPos;
		drawRect[2][drawNumber] = xLength;
		drawRect[3][drawNumber] = yLength;
		// [0][] is x position
		// [1][] is y position
		// [2][] is x length
		// [3][] is y length

	}
}
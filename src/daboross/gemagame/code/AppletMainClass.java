package daboross.gemagame.code;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author daboross
 * 
 */
@SuppressWarnings({ "serial" })
public class AppletMainClass extends JApplet implements MainClass {
	private JFrame jFrame;
	/** These are different Threads in the Game */
	Thread runLevelThread, levelFileWriterThread, menuThread;
	/**
	 * This is the Height of the Applet. The applet will auto-resize the
	 * graphics of this size to fit in the current window.
	 */
	public static final int height = 480;
	/**
	 * This is the Width of the Applet. The applet will auto-resize the graphics
	 * of this size to fit in the current window.
	 */
	public static final int width = 640;
	/**
	 * This variable is used to tell if the MainClass should paint the Game, or
	 * the Menu. true is game, false in menu
	 */
	private boolean paintGame;
	/** This variable is used in Painting the applet */
	private Graphics bufferedGraphics;
	/** This variable is used in Painting the applet */
	private Image image;
	/** This variable is used in Painting the applet */
	private int imageTranslationX, imageTranslationY = 0;
	/** This variable is used in Painting the applet */
	private int contractedImageX, contractedImageY;
	/** This variable is used in Painting the applet */
	private int rememberWidth, rememberHeight;
	/** This variable is used in Painting the applet */
	private int[][] drawRect;
	/** This is a package of classes */
	private ClassHandler classHandler;

	@Override
	/** This is the initial function called by the applet html page or applet viewer*/
	public void init() {
		setFocusable(true);
		setVisible(true);
		System.out.println("I'm an Applet");
	}

	@Override
	/** This function is called by the applet's html page or applet viewer */
	public void start() {
		classHandler = new ClassHandler();
		classHandler.setMainClass(this);
		classHandler.setScreenHeight(height);
		classHandler.setScreenWidth(width);
		System.out.println("MainClass init.");
		setBackground(Color.BLACK);
		setFocusable(true);
		LoadingScreen loadingScreen = new LoadingScreen(classHandler);
		Thread loadingScreenThread = new Thread(loadingScreen);
		classHandler.setLoadingScreenThread(loadingScreenThread);
		loadingScreenThread.start();
	}

	@Override
	/**This is an unused Function*/
	public void stop() {
	}

	@Override
	/**This is an unused Function*/
	public void destroy() {
	}

	/**
	 * Call this function when you want to update the game's graphics.
	 * 
	 * @param gameOn
	 *            this is a boolean that represents whether to paint the
	 *            RunLevel graphics or the Menu graphics. true is runLevel false
	 *            is Menu
	 */
	@Override
	public void paint(boolean gameOn) {
		paintGame = gameOn;
		if (getjFrame() != null) {
			getjFrame().setLayout(new FlowLayout());
			BufferedImage buffImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);
			Graphics graphics = buffImage.getGraphics();
			update(graphics);
			graphics.dispose();
			JLabel label = new JLabel(new ImageIcon(buffImage));
			getjFrame().add(label);
		} else {
			update(this.getGraphics());
		}

	}

	/**
	 * This is a function called by Java when rePaint is called. Do not call
	 * this function manually
	 * 
	 * @param g
	 *            This is the graphics to paint the images onto.
	 * 
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		// defines image if it does not exist.
		if (image == null) {
			image = createImage(width, height);
			bufferedGraphics = image.getGraphics();
			rememberWidth = this.getWidth();
			rememberHeight = this.getHeight();
			// Remembers The current Height and width, so that it can check if
			// the height has changed before running this again
			contractedImageX = width;
			contractedImageY = height;
			// redefines contractedImage width and height so that they are not
			// the ones we defined last time
			if (contractedImageY > this.getHeight()) {
				// If the graphics Y is bigger then the screen Y, resize it to
				// fit
				contractedImageY = this.getHeight();
				// Resize graphics X so that it matches graphics Y
				contractedImageX = (int) ((double) contractedImageY
						/ (double) height * width);
			}
			if (contractedImageX > this.getWidth()) {
				// If the graphics Y is bigger then the screen Y after
				// resizing(or not resizing) y, then resize it to be even
				// smaller
				contractedImageX = this.getWidth();
				// resize Y so they match
				contractedImageY = (int) ((double) contractedImageX
						/ (double) width * height);
			}
			// Calculate how far the image should be moved in order to be in the
			// center of the screen
			imageTranslationX = (this.getWidth() - contractedImageX) / 2;
			imageTranslationY = (this.getHeight() - contractedImageY) / 2;
			// Creates a new drawRect Variable. This v2D array remembers the
			// Coordinates that the system should draw the outside rectangles
			// The rectangles that make black edges around the center
			drawRect = new int[4][4];
			setDrawRect(0, 0, 0, imageTranslationX, this.getHeight());
			setDrawRect(1, 0, 0, this.getWidth(), imageTranslationY);
			setDrawRect(2, this.getWidth() - imageTranslationX - 1, 0,
					imageTranslationX + 2, this.getHeight());
			setDrawRect(3, 0, this.getHeight() - imageTranslationY - 1,
					this.getWidth(), imageTranslationY + 2);
		}

		// If the screen is not the same size at remembered, then re-run the
		// image transformations
		if (!(rememberWidth == this.getWidth() && rememberHeight == this
				.getHeight())) {
			rememberWidth = this.getWidth();
			rememberHeight = this.getHeight();
			// Remembers The current Height and width, so that it can check if
			// the height has changed before running this again
			contractedImageX = width;
			contractedImageY = height;
			// redefines contractedImage width and height so that they are not
			// the ones we defined last time
			if (contractedImageY > this.getHeight()) {
				// If the graphics Y is bigger then the screen Y, resize it to
				// fit
				contractedImageY = this.getHeight();
				// Resize graphics X so that it matches graphics Y
				contractedImageX = (int) ((double) contractedImageY
						/ (double) height * width);
			}
			if (contractedImageX > this.getWidth()) {
				// If the graphics Y is bigger then the screen Y after
				// resizing(or not resizing) y, then resize it to be even
				// smaller
				contractedImageX = this.getWidth();
				// resize Y so they match
				contractedImageY = (int) ((double) contractedImageX
						/ (double) width * height);
			}
			// Calculate how far the image should be moved in order to be in the
			// center of the screen
			imageTranslationX = (this.getWidth() - contractedImageX) / 2;
			imageTranslationY = (this.getHeight() - contractedImageY) / 2;
			// Creates a new drawRect Variable. This v2D array remembers the
			// Coordinates that the system should draw the outside rectangles
			// The rectangles that make black edges around the center
			drawRect = new int[4][4];
			setDrawRect(0, 0, 0, imageTranslationX, this.getHeight());
			setDrawRect(1, 0, 0, this.getWidth(), imageTranslationY);
			setDrawRect(2, this.getWidth() - imageTranslationX - 1, 0,
					imageTranslationX + 2, this.getHeight());
			setDrawRect(3, 0, this.getHeight() - imageTranslationY - 1,
					this.getWidth(), imageTranslationY + 2);

		}
		// clears the screen
		bufferedGraphics.setColor(getBackground());
		bufferedGraphics.fillRect(0, 0, this.getWidth() + 1,
				this.getHeight() + 1);
		bufferedGraphics.setColor(getForeground());
		// Runs the Paint method in order to get the images for all the objects
		// on the screen.
		if (paintGame) {
			classHandler.getRunLevel().paint(bufferedGraphics);
		} else {
			classHandler.getMenu().paint(bufferedGraphics);
		}
		// draws the Image with the translations that were already defined, to
		// make it in the center of the screen
		g.drawImage(image, imageTranslationX, imageTranslationY,
				contractedImageX, contractedImageY, this);
		// This draws rectangles of black around the image in order
		for (int k = 0; k < 4; k++) {
			g.fillRect(drawRect[0][k], drawRect[1][k], drawRect[2][k],
					drawRect[3][k]);
		}
	}

	/**
	 * This is a helper function for the Graphics Update that records rectangle
	 * values into a 2D array
	 * 
	 * @param drawNumber
	 *            This is second number to use in setting the 2D array
	 * @param xPos
	 *            This is the x position to store
	 * @param yPos
	 *            This is the y position to store
	 * @param xLength
	 *            This is the x length to store
	 * @param yLength
	 *            This is the y length to store
	 */
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

	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		jFrame.setTitle("Gema Game");
		// jFrame.getContentPane().add(applet);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setFocusable(true);
		jFrame.setSize(width, height);
		System.out.println("I'm an application!");
		// jFrame.setLayout(new FlowLayout());
		AppletMainClass applet = new AppletMainClass();
		applet.setjFrame(jFrame);
		jFrame.setLayout(null);
		System.out.println(width + "." + height);
		applet.setFocusable(true);
		applet.setVisible(true);
		jFrame.add(applet);
		applet.start();

	}

	public void keyListenerAdd(KeyListener keyListener) {
		if (getjFrame() != null) {
			getjFrame().addKeyListener(keyListener);
		}
		addKeyListener(keyListener);
	}

	@Override
	public void keyListenerRemove(KeyListener keyListener) {
		if (getjFrame() != null) {
			getjFrame().removeKeyListener(keyListener);
		}
		removeKeyListener(keyListener);
	}

	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}
}
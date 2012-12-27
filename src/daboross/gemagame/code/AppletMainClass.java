package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JApplet;

/**
 * @author daboross
 * 
 */
@SuppressWarnings("serial")
public class AppletMainClass extends JApplet implements MainClass {
	private Graphics bufferedGraphics;
	private Image image;
	private int imageTranslationX, imageTranslationY, contractedImageX,
			contractedImageY, rememberWidth, rememberHeight, height, width;
	private int[][] drawRect;
	private Paintable paintingObject;
	private ObjectHandler classHandler;

	@Override
	/**
	 * This is the initial function called by the applet html page or applet viewer
	 */
	public void init() {
		System.out.println("Running Gema Game");
		setFocusable(true);
		setVisible(true);
	}

	@Override
	/**
	 * This function is called by the applet's html page or applet viewer
	 */
	public void start() {
		System.out.println("MainClass init.");
		classHandler = new ObjectHandler();
		classHandler.setMainClass(this);
		this.width = classHandler.getScreenWidth();
		this.height = classHandler.getScreenHeight();
		setBackground(Color.BLACK);
		setFocusable(true);
		setVisible(true);
		setForeground(Color.black);
		LoadingScreen loadingScreen = new LoadingScreen(classHandler);
		loadingScreen.load();
	}

	@Override
	/**
	 * This is an unused Function
	 */
	public void stop() {
	}

	@Override
	/**
	 * This is an unused Function
	 */
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
	public void paint(Paintable pt) {
		paintingObject = pt;
		setFocusable(true);
		update(this.getGraphics());
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
			contractedImageY = this.getHeight();
			// Resize graphics X so that it matches graphics Y
			contractedImageX = (int) ((double) contractedImageY
					/ (double) height * width);
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
			contractedImageY = this.getHeight();
			// Resize graphics X so that it matches graphics Y
			contractedImageX = (int) ((double) contractedImageY
					/ (double) height * width);
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
		bufferedGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		bufferedGraphics.setColor(getForeground());
		// Runs the Paint method in order to get the images for all the objects
		// on the screen.
		paintingObject.paint(bufferedGraphics);
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

	public int realX(double x) {
		int returnX = (int) (imageTranslationX + (((x) / this.getWidth()) * contractedImageX));
		return returnX;
	}

	@Override
	public int realY(double y) {
		int returnY = (int) (imageTranslationY + (((y) / this.getHeight()) * contractedImageY));
		return returnY;
	}
}
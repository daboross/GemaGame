package gemagame.code;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

@SuppressWarnings({ "serial" })
public class MainClass extends Applet {
	// defines the width and height that the applet will act as if it is
	private int height = 480;
	private int width = 640;
	private boolean paintGame;

	// Variables are defined in Initial function
	private Graphics secondaryGraphics; // Graphics variable used in painting
										// objects
	private Image image;
	// Various image variables that are defined in initial function

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

	private RunLevel runLevel;

	@Override
	public void init() {
		System.out.println("MainClass init.");
		// Sets certain things in the program.
		runLevel = new RunLevel(this);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(runLevel);
	}

	@Override
	public void start() {
		System.out.println("MainClass start");
		// Starts this thread/applet
		Thread Thread = new Thread(runLevel);
		Thread.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void update(Graphics g) {
		// defines image if it does not exist.
		if (image == null) {
			image = createImage(width, height);
			secondaryGraphics = image.getGraphics();
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
		secondaryGraphics.setColor(getBackground());
		secondaryGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		secondaryGraphics.setColor(getForeground());
		// Runs the Paint method in order to get the images for all the objects
		// on the screen.
		if (paintGame) {
			runLevel.paint(secondaryGraphics);
		}
		// draws the Image with the translations that were already defined, to
		// make it in the center of the screen
		g.drawImage(image, imageTranslationX, imageTranslationY,
				contractedImageX, contractedImageY, this);
		for (int k = 0; k < 4; k++) {
			g.fillRect(drawRect[0][k], drawRect[1][k], drawRect[2][k],
					drawRect[3][k]);
		}// This draws rectangles of black around the image in order
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

	public void paintMe(boolean gameOn) {
		// Runs this functions paint method on RunLevel's paint method.
		// If gameOn then run the method on RunLevel, otherwise run it on the
		// menu class.
		paintGame = gameOn;
		repaint();
	}

	public RunLevel getRunLevel() {
		return runLevel;
	}
}
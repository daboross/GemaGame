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
	private Graphics bufferedGraphics1, bufferedGraphics2;
	private Image bImage1, bImage2;
	private int imageTranslationX, imageTranslationY, contractedImageX,
			contractedImageY, rememberWidth, rememberHeight, height, width;
	private Paintable paintingObject;
	private ObjectHandler objectHandler;

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
		objectHandler = new ObjectHandler();
		objectHandler.setMainClass(this);
		this.width = objectHandler.getScreenWidth();
		this.height = objectHandler.getScreenHeight();
		setBackground(Color.BLACK);
		setFocusable(true);
		setVisible(true);
		setForeground(Color.black);
		LoadingScreen loadingScreen = new LoadingScreen(objectHandler);
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

	public void update(Graphics g) {
		/*
		 * If the screen is not the same size at remembered, then re-run the
		 * image transformations
		 */
		if (!(rememberWidth == this.getWidth() && rememberHeight == this
				.getHeight())) {
			System.out.println("Re-sizing");
			/* Create New Images */
			bImage1 = createImage(this.getWidth(), this.getHeight());
			bImage2 = createImage(width, height);
			bufferedGraphics2 = bImage2.getGraphics();
			bufferedGraphics1 = bImage1.getGraphics();
			/*
			 * Remember The current Height and width, so that it can check if
			 * the height has changed before running this again
			 */
			rememberWidth = this.getWidth();
			rememberHeight = this.getHeight();
			/*
			 * Define contractedImageX and y depending on the height of the
			 * screen
			 */
			contractedImageY = this.getHeight();
			contractedImageX = (int) ((double) contractedImageY
					/ (double) height * width);
			/*
			 * If the graphics defined by using the height make it go off the
			 * sides of the screen, redefine with the width
			 */
			if (contractedImageX > this.getWidth()) {
				contractedImageX = this.getWidth();
				contractedImageY = (int) ((double) contractedImageX
						/ (double) width * height);
			}
			/*
			 * Re Calculate Image Translations so that they position the image
			 * correctly
			 */
			imageTranslationX = (this.getWidth() - contractedImageX) / 2;
			imageTranslationY = (this.getHeight() - contractedImageY) / 2;

		}
		// clears the screen
		bufferedGraphics2.setColor(Color.black);
		bufferedGraphics2.fillRect(0, 0, 640, 480);
		paintingObject.paint(bufferedGraphics2);
		bufferedGraphics1
				.drawImage(bImage2, imageTranslationX, imageTranslationY,
						contractedImageX + imageTranslationX, contractedImageY
								+ imageTranslationY, 0, 0, width, height, null);
		g.drawImage(bImage1, 0, 0, this);
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
package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author daboross
 * 
 */
public class BackgroundHandler implements Paintable {
	/**
	 * The length that one background is graphically represented x-wise
	 */
	final int lengthX = 640;
	/**
	 * totalDifX holds the total amount that the backgrounds have scrolled with
	 * Respect to the front most background, and the objects on the screen difX
	 * holds how far the background should scroll next update. It is added to
	 * every time the changeDifX function is called, and it is used and set to 0
	 * every update
	 */
	private double totalDifX, difX = 0;
	/**
	 * The number of background layers that exist on the screen
	 */
	private int numberOfLayers;
	/**
	 * Every background layer has to have 2 parts that interchange and shift
	 * around each other. This allows them to scroll infinitely. The first //
	 * number in this 2D array is which of these 2 you are talking about. one is
	 * always going to be lengthX farther then the other. The second number is
	 * which background layer it is.
	 */
	private double[][] xPositions, yPositions;
	/**
	 * The rate at which each background layer will scroll. The number held in
	 * this is multiplied by the total scroll distance to determine how far this
	 * background scrolls
	 */
	private double[] increaseRates;
	private Image img;
	private int height;

	/** The initial function for creating a BackgroundHandler */
	public BackgroundHandler(ObjectHandler objectHandler) {
		height = objectHandler.getScreenHeight();
		img = objectHandler.getImageHandler().getImage("pBackground.png");
		objectHandler.setBackgroundHandler(this);
		numberOfLayers = 3;
		xPositions = new double[2][numberOfLayers];
		yPositions = new double[2][numberOfLayers];
		increaseRates = new double[numberOfLayers];
		/** Give each background its initial x Position */
		for (int i = 0; i < numberOfLayers; i++) {
			xPositions[0][i] = 0;
			xPositions[1][i] = lengthX;
		}
		/** Give each background its initial y Position */
		for (int i = 0; i < numberOfLayers; i++) {
			double yPos = i * 200;
			yPositions[0][i] = yPos;
			yPositions[1][i] = yPos;
		}
		/** Define the increase rates for each background */
		increaseRates[0] = 0.4;
		increaseRates[1] = 0.7;
		increaseRates[2] = 0.99;
	}

	/**
	 * This function tells the BackgroundHandler to update, moving each
	 * background the amount it should
	 */
	public void update() {
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < numberOfLayers; i++) {
				xPositions[k][i] += difX * increaseRates[i];
				// Using xDif in each background in each layer to move the
				// background accordingly
			}
		}
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < numberOfLayers; i++) {
				if (xPositions[k][i] < -lengthX) {
					xPositions[k][i] += lengthX * 2;
				}
				if (xPositions[k][i] > lengthX) {
					xPositions[k][i] -= lengthX * 2;
				}
				// These 2 checks shift around the background that are off
				// screen so that they will look like they are infinitely
				// scrolling
			}
		}
		difX = 0;
		// Reseting difX
	}

	/**
	 * Returns the visual position for the background number and layer
	 * specified, if they are not correct then you get an error in the console,
	 * and they value 0 is returned.
	 * 
	 * @param backgroundNumber
	 *            The number of the Background to get. Only use 0 or 1.
	 * @param layerNumber
	 *            The layer number of the Background to get. Will return 0 if
	 *            invalid number is specified
	 * @return the background x position on screen
	 */
	public double getBgX(int backgroundNumber, int layerNumber) {
		if ((layerNumber == 0 || layerNumber == 1 || layerNumber == 2)
				&& (backgroundNumber == 0 || backgroundNumber == 1)) {
			return xPositions[backgroundNumber][layerNumber];
		} else {
			System.out.println("INVALID INPUT INTO getBgX background: "
					+ backgroundNumber + " layer: " + layerNumber);
			return 0;
		}
	}

	/**
	 * Gets the total scrolling difference, useful for getting the visual
	 * positions of objects that scroll with the background when the character
	 * scrolls the screen.
	 */
	public double getDifX() {
		return totalDifX;
	}

	/**
	 * Returns the visual position for the background number and layer
	 * specified, if they are not correct then you get an error in the console,
	 * and they value 0 is returned.
	 */
	public double getBgY(int backgroundNumber, int layerNumber) {
		if ((layerNumber == 0 || layerNumber == 1 || layerNumber == 2)
				&& (backgroundNumber == 0 || backgroundNumber == 1)) {
			return yPositions[backgroundNumber][layerNumber];
		} else {
			System.out.println("INVALID INPUT INTO getBgY background: "
					+ backgroundNumber + " layer: " + layerNumber);
			return 0;
		}
	}

	/**
	 * Makes this class scroll everything by the amount specified. currently
	 * changeY does not do anything.
	 */
	public void changeDifX(double changeX, double changeY) {
		difX += changeX;
		totalDifX += changeX;
	}

	/** @return The number of background layers there are. */
	public int getNumberLayers() {
		return numberOfLayers;
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < numberOfLayers; i++) {
			for (int k = 0; k < 2; k++) {
				int xPos = (int) xPositions[k][i];
				int yPos = (int) yPositions[k][i];
				g.drawImage(img, xPos, yPos, lengthX, height, null);
			}
		}
	}
}

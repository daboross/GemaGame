package code;

public class BackgroundHandler {
	final int lengthX = 2000;// The length that one background is graphically
								// represented x-wise
	final int lengthY = 0;// ?
	private double totalDifX, difX = 0;
	// totalDifX holds the total amount that the backgrounds have scrolled with
	// Respect to the front most background, and the objects on the screen
	// difX holds how far the background should scroll next update. It is added
	// to every time the changeDifX function is called, and it is used and set
	// to 0 every update
	private int numberOfLayers;// The number of background layers that exist on
								// the screen
	private double[][] xPositions, yPositions;
	// Every background layer has to have 2 parts that interchange and shift
	// around each other. This allows them to scroll infinitely. The first
	// number
	// in this 2D array is which of these 2 you are talking about. one is always
	// going to be lengthX farther then the other. The second number is which
	// background layer it is.
	private double[] increaseRates;

	// The rate at which each background layer will scroll. The number held in
	// this is multiplied by the total scroll distance to determine how far this
	// background scrolls

	public BackgroundHandler() {
		numberOfLayers = 3;// The number of background layers
		xPositions = new double[2][numberOfLayers];
		yPositions = new double[2][numberOfLayers];
		increaseRates = new double[numberOfLayers];
		// Initializing these three variables.
		for (int i = 0; i < numberOfLayers; i++) {
			// giving the different background of different layers their initial
			// x positions
			xPositions[0][i] = 0;
			xPositions[1][i] = lengthX;
		}
		for (int i = 0; i < numberOfLayers; i++) {
			double yPos = i * 200;
			yPositions[0][i] = yPos;
			yPositions[1][i] = yPos;
		}
		increaseRates[0] = 0.4;
		increaseRates[1] = 0.7;
		increaseRates[2] = 0.99;
		// Setting the increase rates for each of these backgrounds
	}

	public void update() {
		// Repeats for every background in every layer.
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

	public double getBgX(int backgroundNumber, int layerNumber) {
		// Returns the visual position for the background number and layer
		// specified, if they are not correct then you get an error in the
		// console, and they value 0 is returned.
		if ((layerNumber == 0 || layerNumber == 1 || layerNumber == 2)
				&& (backgroundNumber == 0 || backgroundNumber == 1)) {
			return xPositions[backgroundNumber][layerNumber];
		} else {
			System.out.println("INVALID INPUT INTO getBgX background: "
					+ backgroundNumber + " layer: " + layerNumber);
			return 0;
		}
	}

	public double getDifX() {
		return totalDifX;
		// Gets the total scrolling difference, useful for getting the visual
		// positions of objects that scroll with the background when the
		// character scrolls the screen.
	}

	public double getBgY(int backgroundNumber, int layerNumber) {
		// Returns the visual position for the background number and layer
		// specified, if they are not correct then you get an error in the
		// console, and they value 0 is returned.
		if ((layerNumber == 0 || layerNumber == 1 || layerNumber == 2)
				&& (backgroundNumber == 0 || backgroundNumber == 1)) {
			return yPositions[backgroundNumber][layerNumber];
		} else {
			System.out.println("INVALID INPUT INTO getBgY background: "
					+ backgroundNumber + " layer: " + layerNumber);
			return 0;
		}
	}

	public void changeDifX(double changeX, double changeY) {
		// Makes this class scroll everything by the amount specified. currently
		// changeY does not do anything.
		difX += changeX;
		totalDifX += changeX;
	}

	public int getNumberLayers() {
		// Returns the number of layers.
		return numberOfLayers;
	}
}

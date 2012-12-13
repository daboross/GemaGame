package code;

public class BackgroundHandler {
	final int lengthX = 2160;
	final int lengthY = 0;
	private double totalDifX, difX = 0;
	private int numberOfLayers;
	private double[][] xPositions, yPositions;
	private double[] increaseRates;

	public BackgroundHandler() {
		numberOfLayers = 3;
		xPositions = new double[2][numberOfLayers];
		yPositions = new double[2][numberOfLayers];
		increaseRates = new double[numberOfLayers];
		for (int i = 0; i < numberOfLayers; i++) {
			xPositions[0][i] = 0;
			yPositions[0][i] = 0;
			xPositions[1][i] = lengthX;
			yPositions[1][i] = 0;
		}
		increaseRates[0] = 0.4;
		increaseRates[1] = 0.7;
		increaseRates[2] = 1.0;
		increaseRates[0] = 1 / 3;
		increaseRates[1] = 2 / 3;
		increaseRates[2] = 4 / 3;
	}

	public void update() {
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < numberOfLayers; i++) {
				xPositions[k][i] += difX * increaseRates[i];
			}
			for (int i = 0; i < numberOfLayers; i++) {
				if (xPositions[k][i] <= -lengthX) {
					xPositions[k][i] += lengthX * 2;
				}
				if (xPositions[k][i] > lengthX) {
					xPositions[k][i] -= lengthX * 2;
				}
			}
		}
		difX = 0;
	}

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

	public double getDifX() {
		return totalDifX;
	}

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

	public void changeDifX(double changeX, double changeY) {
		difX += changeX;
		totalDifX += changeX;
	}

	public int getNumberLayers() {
		return numberOfLayers;
	}
}

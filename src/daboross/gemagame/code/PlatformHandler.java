package daboross.gemagame.code;

import java.util.ArrayList;

public class PlatformHandler {
	/**
	 * Each platform is basically an entry into each of these lists, for example
	 * platform 6 would be xPosList.get(6), yPosList.get(6), xLengthLisy.get(6)
	 * and yLengthList.get(6). those would be the values that define the
	 * platform 6.
	 */
	ArrayList<Double> xPosList, yPosList, xLengthList, yLengthList;
	/** The number of platforms */
	int listLength;

	/** Initial Platform Handler Function */
	public PlatformHandler() {
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		xLengthList = new ArrayList<Double>();
		yLengthList = new ArrayList<Double>();
		listLength = 0;
	}

	/**
	 * Adds a platform to the list of platforms
	 * 
	 * @param xPos
	 *            this is the x position of the platform you are adding
	 * @param yPos
	 *            this is the y position of the platform you are adding
	 * @param xLength
	 *            this is the x length of the platform you are adding
	 * @param yLength
	 *            this is the y length of the platform you are adding
	 */
	public void addPlatForm(double xPos, double yPos, double xLength,
			double yLength) {
		xPosList.add(xPos);
		yPosList.add(yPos);
		xLengthList.add(xLength);
		yLengthList.add(yLength);
		listLength += 1;
	}

	/** This returns an ArrayList of the x Positions of all the platforms. */
	public ArrayList<Double> xPosList() {
		return xPosList;
	}

	/** This returns an ArrayList of the y Positions of all the platforms. */
	public ArrayList<Double> yPosList() {
		return yPosList;
	}

	/** This returns an ArrayList of the x Lengths of all the platforms. */
	public ArrayList<Double> xLengthList() {
		// returns the xLength to be used to get values.
		// Never use this to alter the list.
		return xLengthList;
	}

	/** This returns an ArrayList of the y Lengths of all the platforms. */
	public ArrayList<Double> yLengthList() {
		// returns the yLength to be used to get values.
		// Never use this to alter the list.
		return yLengthList;
	}

	/** Removes all platforms. */
	public void clearPlatformList() {
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		xLengthList = new ArrayList<Double>();
		yLengthList = new ArrayList<Double>();
		listLength = 0;
	}

	/** Returns the number of platforms */
	public int listLength() {
		return listLength;
	}
}
package daboross.gemagame.code;

import java.util.ArrayList;

public class PlatformHandler {
	/**
	 * Each platform is basically an entry into each of these lists, for example
	 * platform 6 would be xPosList.get(6), yPosList.get(6), xLengthLisy.get(6)
	 * and yLengthList.get(6). those would be the values that define the
	 * platform 6.
	 */
	private ArrayList<Integer> xPosList, yPosList, xLengthList, yLengthList;
	/** The number of platforms */
	private int listLength;

	/**
	 * Initial Platform Handler Function
	 * 
	 * @param objectHandler
	 */
	public PlatformHandler(ObjectHandler objectHandler) {
		objectHandler.setPlatformHandler(this);
		xPosList = new ArrayList<Integer>();
		yPosList = new ArrayList<Integer>();
		xLengthList = new ArrayList<Integer>();
		yLengthList = new ArrayList<Integer>();
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
	public void addPlatForm(int xPos, int yPos, int xLength, int yLength) {
		xPosList.add(xPos);
		yPosList.add(yPos);
		xLengthList.add(xLength);
		yLengthList.add(yLength);
		listLength++;
	}

	/** Returns the x position of the platform i */
	public double xPosList(int i) {
		return xPosList.get(i);
	}

	/** Returns the y position of the platform i */
	public double yPosList(int i) {
		return yPosList.get(i);
	}

	/** Returns the x length of the platform i */
	public double xLengthList(int i) {
		return xLengthList.get(i);
	}

	/** Returns the y length of the platform i */
	public double yLengthList(int i) {
		return yLengthList.get(i);
	}

	/** Removes all platforms. */
	public void clearPlatformList() {
		xPosList = new ArrayList<Integer>();
		yPosList = new ArrayList<Integer>();
		xLengthList = new ArrayList<Integer>();
		yLengthList = new ArrayList<Integer>();
		listLength = 0;
	}

	public void loadFromList(PlatformList pl) {
		if (pl != null) {
			xPosList = pl.xPosList;
			xLengthList = pl.xLengthList;
		}
	}

	/** Returns the number of platforms */
	public int listLength() {
		return listLength;
	}
}
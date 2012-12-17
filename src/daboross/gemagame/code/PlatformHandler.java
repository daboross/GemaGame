package daboross.gemagame.code;

import java.util.ArrayList;

public class PlatformHandler {

	ArrayList<Double> xPosList, yPosList, xLengthList, yLengthList;
	// Each platform is basically an entry into each of these lists, for example
	// platform 6 would be xPosList.get(6), yPosList.get(6), xLengthLisy.get(6)
	// and yLengthList.get(6). those would be the values that define the
	// platform 6.
	int listLength;

	// Stores the number of platforms

	public PlatformHandler() {
		// initializes the ArrayLists, and list length.
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		xLengthList = new ArrayList<Double>();
		yLengthList = new ArrayList<Double>();
		listLength = 0;
	}

	public void addPlatForm(double xPos, double yPos, double xLength,
			double yLength) {
		// Adds the values given to each list, respectively, and adds one to
		// list length
		xPosList.add(xPos);
		yPosList.add(yPos);
		xLengthList.add(xLength);
		yLengthList.add(yLength);
		listLength += 1;
	}

	public ArrayList<Double> xPosList() {
		// returns the xPosList to be used to get values.
		// Never use this to alter the list.
		return xPosList;
	}

	public ArrayList<Double> yPosList() {
		// returns the yPosList to be used to get values.
		// Never use this to alter the list.
		return yPosList;
	}

	public ArrayList<Double> xLengthList() {
		// returns the xLength to be used to get values.
		// Never use this to alter the list.
		return xLengthList;
	}

	public ArrayList<Double> yLengthList() {
		// returns the yLength to be used to get values.
		// Never use this to alter the list.
		return yLengthList;
	}

	public int listLength() {
		return listLength;
	}
}
package gemagame.framework;

import java.util.ArrayList;

public class PlatformHandler {

	ArrayList<Double> xPosList, yPosList, xLengthList, yLengthList;
	int listLength;

	public PlatformHandler() {
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		xLengthList = new ArrayList<Double>();
		yLengthList = new ArrayList<Double>();
		listLength = 0;
	}

	public void addPlatForm(double xPos, double yPos, double xLength,
			double yLength) {
		xPosList.add(xPos);
		yPosList.add(yPos);
		xLengthList.add(xLength);
		yLengthList.add(yLength);
		listLength += 1;
	}

	public ArrayList<Double> xPosList() {
		return xPosList;
	}

	public ArrayList<Double> yPosList() {
		return yPosList;
	}

	public ArrayList<Double> xLengthList() {
		return xLengthList;
	}

	public ArrayList<Double> yLengthList() {
		return yLengthList;
	}

	public int listLength() {
		return listLength-1;
	}
}
package gemagame.framework;

public class PlatformHandler {

	double[] xPosList, yPosList, xLengthList, yLengthList;
	int listLength;

	public PlatformHandler() {
		xPosList = new double[1];
		yPosList = new double[1];
		xLengthList = new double[1];
		yLengthList = new double[1];
		listLength = 0;
	}

	public void addPlatForm(double xPos, double yPos, double xLength,
			double yLength) {
		double[] xPosListTemp = xPosList;
		double[] yPosListTemp = yPosList;
		double[] xLengthListTemp = xLengthList;
		double[] yLengthListTemp = yLengthList;
		xPosList = new double[listLength + 2];
		for (int i = 0; i <= listLength; i++) {
			xPosList[i] = xPosListTemp[i];
		}
		xPosList[listLength + 1] = xPos;
		yPosList = new double[listLength + 2];
		for (int i = 0; i <= listLength; i++) {
			yPosList[i] = yPosListTemp[i];
		}
		yPosList[listLength + 1] = yPos;
		xLengthList = new double[listLength + 2];
		for (int i = 0; i <= listLength; i++) {
			xLengthList[i] = xLengthListTemp[i];
		}
		xLengthList[listLength + 1] = xLength;
		yLengthList = new double[listLength + 2];
		for (int i = 0; i <= listLength; i++) {
			yLengthList[i] = yLengthListTemp[i];
		}
		yLengthList[listLength + 1] = yLength;
		listLength += 1;
	}

	public double[] xPosList() {
		return xPosList;
	}

	public double[] yPosList() {
		return yPosList;
	}

	public double[] xLengthList() {
		return xLengthList;
	}

	public double[] yLengthList() {
		return yLengthList;
	}

	public int listLength() {
		return listLength;
	}
}
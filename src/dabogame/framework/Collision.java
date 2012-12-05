package dabogame.framework;

public class Collision {
	public static boolean isCollided(double xPos1, double yPos1, double xPos2,
			double yPos2, double xLength1, double yLength1, double xLength2,
			double yLength2) {
		double xMin1 = xPos1;
		double xMax1 = xPos1 + xLength1;
		double xMin2 = xPos2;
		double xMax2 = xPos2 + xLength2;
		double yMax1 = yPos1;
		double yMin1 = yPos1 + yLength1;
		double yMax2 = yPos2;
		double yMin2 = yPos2 + yLength2;
		if (((xMin1 > xMin2 && xMin1 < xMax2)
				|| (xMax1 > xMin2 && xMax1 < xMax2)
				|| (xMin2 > xMin1 && xMin2 < xMax1) || (xMax2 > xMin1 && xMax2 < xMax1))
				&& ((yMax1 > yMax2 && yMax1 < yMin2)
						|| (yMin1 > yMax2 && yMin1 < yMin2)
						|| (yMax2 > yMax1 && yMax2 < yMin1) || (yMin2 > yMax1 && yMin2 < yMin1))) {
			return true;
		}
		return false;
	}

	/*
	 * Is Collided Above. Returns true if 1 is above 2.
	 */
	public static boolean isCollided1Above2(double xPos1, double yPos1,
			double xPos2, double yPos2, double xLength1, double yLength1,
			double xLength2, double yLength2) {
		double xMin1 = xPos1;
		double xMax1 = xPos1 + xLength1;
		double xMin2 = xPos2;
		double xMax2 = xPos2 + xLength2;
		double yMin1 = yPos1 + yLength1;
		double yMax2 = yPos2;
		double yMin2 = yPos2 + yLength2;
		//System.out.println("");
		// System.out.println("Xmin1 "+xMin1);
		// System.out.println("Xmax1 "+xMax1);
		// System.out.println("Xmin2 "+xMin2);
		// System.out.println("Xmax2 "+xMax2);
		//System.out.println("Ymin1 " + yMin1);
		//System.out.println("Ymin2 " + yMin2);
		//System.out.println("Ymax2 " + yMax2);
		//if (yMin1 >= yMax2 && yMin1 <= yMin2) {
		//	System.out.println("true");
		//} else {
		//	System.out.println("false");
		//}

		if (((xMin1 > xMin2 && xMin1 < xMax2)
				|| (xMax1 >= xMin2 && xMax1 <= xMax2)
				|| (xMin2 >= xMin1 && xMin2 <= xMax1) || (xMax2 >= xMin1 && xMax2 <= xMax1))

				&& (yMin1 >= yMax2 && yMin1 <= yMin2)) {
			return true;
		}
		return false;

	}
}
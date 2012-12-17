package code;

public class Collision {
	public static boolean isCollided(double xPos1, double yPos1, double xPos2,
			double yPos2, double xLength1, double yLength1, double xLength2,
			double yLength2) {
		// Returns true if the 2 objects defined are collided on both X and Y
		double xMin1 = xPos1;
		double xMax1 = xPos1 + xLength1;
		double xMin2 = xPos2;
		double xMax2 = xPos2 + xLength2;
		double yMin1 = yPos1;
		double yMax1 = yPos1 + yLength1;
		double yMin2 = yPos2;
		double yMax2 = yPos2 + yLength2;
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
		if (((xMin1 > xMin2 && xMin1 < xMax2)
				|| (xMax1 > xMin2 && xMax1 < xMax2)
				|| (xMin2 > xMin1 && xMin2 < xMax1) || (xMax2 > xMin1 && xMax2 < xMax1))

				&& (yMin1 >= yMax2 && yMin1 <= yMin2)) {
			return true;
		}
		return false;

	}

	public static boolean isCollided1LeftOf2(double xPos1, double yPos1,
			double xPos2, double yPos2, double xLength1, double yLength1,
			double xLength2, double yLength2) {
		// tries to return true if object 1 is to the left of object 2, and is
		// collided with object 2.
		// object one is defined by upper left point(xPos1,yPos1), and
		// length(xLength1, yLength1)
		// Object two is defined by upper left point(xPos2,yPos2), and
		// length(xLength2, yLength2)
		double xMin1 = xPos1;
		double xMin2 = xPos2;
		double xMax1 = xPos1 + xLength1;
		double xMax2 = xPos2 + xLength2;
		double yMax1 = yPos1 + yLength1;
		double yMin1 = yPos1;
		double yMin2 = yPos2;
		double yMax2 = yPos2 + yLength2;
		if (((yMin1 > yMin2 && yMin1 < yMax2)
				|| (yMax1 > yMin2 && yMax1 < yMax2)
				|| (yMin2 > yMin1 && yMin2 < yMax1) || (yMax2 > yMin1 && yMax2 < yMax1))
				&& (xMin1 <= xMax2 && xMin1 >= xMin2)
				&& !(xMax1 <= xMax2 && xMax1 >= xMin2)) {
			return true;
		}
		return false;
	}

	public static boolean isCollided1D(double p1, double length1, double p2,
			double length2) {
		// returns true if object1(with left point p1 and length length1) is
		// collided on a 1D graph with object2(with left point p2 and length
		// length2) otherwise returns false
		double min1 = p1;
		double max1 = p1 + length1;
		double min2 = p2;
		double max2 = p2 + length2;
		if ((min1 > min2 && min1 < max2) || (max1 > min2 && max1 < max2)
				|| (min2 > min1 && min2 < max1) || (max2 > min1 && max2 < max1)) {
			return true;
		}
		return false;
	}
}

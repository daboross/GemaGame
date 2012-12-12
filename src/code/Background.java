package code;

public class Background {
	private double bgX0, bgX1, bgX2, bgY0, bgY1, bgY2, difX, totalDifX = 0;
	private double errorInt;

	public Background(int x, int y) {
		bgX0 = x;
		bgX1 = x;
		bgX2 = x;
		bgY0 = y;
		bgY1 = y;
		bgY2 = y;
	}

	public void update() {
		bgX0 += difX * 3 / 3;
		bgX1 += difX * 2 / 3;
		bgX2 += difX * 1 / 3;
		difX = 0;
		if (bgX0 <= -2160) {
			bgX0 += 4320;
		}
		if (bgX0 > 2160) {
			bgX0 -= 4320;
		}
		if (bgX1 <= -2160) {
			bgX1 += 4320;
		}
		if (bgX1 > 2160) {
			bgX1 -= 4320;
		}
		if (bgX2 <= -2160) {
			bgX2 += 4320;
		}
		if (bgX2 > 2160) {
			bgX2 -= 4320;
		}
	}

	public double getBgX(int backgroundNumber) {
		if (backgroundNumber == 0) {
			return bgX0;

		}
		if (backgroundNumber == 1) {
			return bgX1;

		}
		if (backgroundNumber == 2) {
			return bgX2;

		}
		System.out.println("INVALID INPUT INTO getBgX");
		return 0;
	}

	public double getDifX() {
		return totalDifX;
	}

	public double getBgY(int backgroundNumber) {
		if (backgroundNumber == 0) {
			return bgY0;

		}
		if (backgroundNumber == 1) {
			return bgY1;

		}
		if (backgroundNumber == 2) {
			return bgY2;

		}
		System.out.println("INVALID INPUT INTO getBgY");
		return errorInt;
	}

	public void changeDifX(double changeX, double changY) {
		difX += changeX;
		totalDifX += changeX;
	}
}

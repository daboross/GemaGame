package gemagame;

public class Background {
	private double bgX, bgY, difX, totalDifX = 0;

	public Background(int x, int y) {
		bgX = x;
		bgY = y;
	}

	public void update() {
		bgX += difX;
		difX = 0;
		if (bgX <= -2160) {
			bgX += 4320;
		}
		if (bgX > 2160) {
			bgX -= 4320;
		}
	}

	public double getBgX() {
		return bgX;
	}

	public double getDifX() {
		return totalDifX;
	}

	public double getBgY() {
		return bgY;
	}

	public void changeDifX(double change) {
		difX += change;
		totalDifX += change;
	}
}

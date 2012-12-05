package dabogame;

public class Projectile {
	private double centerY, speedX, speedY, comX, comY, centerX = 0;
	private int animationDirection;
	private Background bg1 = StartingClass.bg1();
	private boolean alive = true;

	public void movUpdate(int rightEdge) {
		comX += speedX;
		comY += speedY;
		centerX = comX + bg1.getDifX();
		centerY = comY;
		if (centerX > -6 && centerX < rightEdge + 6) {
		} else {
			alive = false;
		}
	}

	public void update(int rightEdge) {
		this.movUpdate(rightEdge);
	}

	public double getCenterY() {
		return centerY;
	}

	public double getCenterX() {
		return centerX;
	}

	public int getDirection() {
		return animationDirection;
	}

	public Projectile(double comX, double comY, double speedX, double speedY) {
		this.comX = comX - bg1.getDifX();
		this.comY = comY;
		this.speedX = speedX;
		this.speedY = speedY;
		if (speedX < 0) {
			animationDirection = 0;
		} else if (speedY < 0) {
			animationDirection = 1;
		} else if (speedX > 0) {
			animationDirection = 2;
		} else if (speedY > 0) {
			animationDirection = 3;
		}
	}

	public boolean isAlive() {
		return alive;
	}

}

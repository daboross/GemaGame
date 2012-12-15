package code;

public class Projectile {
	private double centerY, speedX, speedY, comX, comY, centerX = 0;
	private int animationDirection;
	// These are variables that the projectile class uses to keep track of
	// itself. Each instance of this class represents one projectile.
	private BackgroundHandler bg = MainClass.background();
	// Gets the background handler from MainClass so that it can be used through
	// the class without repetitive retrievings.
	private boolean alive = true; // Keeps track of if this projectile should be
									// removed at next update

	public void update(int rightEdge, int bottomEdge) {
		// This function updates the position of this projectile
		comX += speedX;
		comY += speedY;
		// Adding the speed to the comX and comY.
		// com X and Y are the c and y values without taking into account the
		// scrolling difference.
		centerX = comX + bg.getDifX();
		// Sets the center X to comX plus the scroll.
		centerY = comY;
		// Because the game does not scroll vertically, sets the center Y to
		// comY directly.
		if (!(centerX > -6 && centerX < rightEdge + 6 && centerY > -6 && centerY < bottomEdge + 6)) {
			// If the projectile is not within these boundaries, mark it for
			// deletion on next refresh.
			alive = false;
		}
	}

	public double getCenterY() {
		return centerY;
		// The center Y position including the scrolling difference
	}

	public double getCenterX() {
		return centerX;
		// The center X position including the scrolling difference
	}

	public int getDirection() {
		// The direction that the projectile is facing, represented with an
		// integer
		return animationDirection;
	}

	public Projectile(double comX, double comY, double speedX, double speedY) {
		// first definition of this class.
		this.comX = comX - bg.getDifX();
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
		// If the projectile should be deleted at next update.
	}

}

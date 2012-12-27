package daboross.gemagame.code;

public class Projectile {
	/**
	 * These are variables that the projectile class uses to keep track of
	 * itself. Each instance of this class represents one projectile.
	 */
	private double centerY, speedX, speedY, comX, comY, centerX = 0;
	/**
	 * This is the right edge and bottom edge of the screen, defined in the
	 * initial function
	 */
	private int rightEdge, bottomEdge;
	private int animationDirection;
	/**
	 * Gets the background handler from MainClass so that it can be used through
	 * the class without repetitive retrievings.
	 */
	private BackgroundHandler bg;
	/**
	 * Keeps track of if this projectile should be removed at next update
	 */
	private boolean alive = true;

	/**
	 * first definition of this class.
	 * 
	 * @param comX
	 *            This is the starting x Position relative to the scrolling 0,0.
	 *            Should include the current background scroll
	 * @param comY
	 *            The starting Y position of this projectile
	 * @param speedX
	 *            this is the X speed of this projectile for its entire life
	 * @param speedY
	 *            this is the Y speed of this projectile for its entire life
	 * @param rightEdge
	 *            this is the width of the game
	 * @param bottomEdge
	 *            this is the height of the game
	 */
	public Projectile(double comX, double comY, double speedX, double speedY,
			int rightEdge, int bottomEdge, ObjectHandler objectHandler) {
		bg = objectHandler.getBackgroundHandler();
		this.rightEdge = rightEdge;
		this.bottomEdge = bottomEdge;
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

	/**
	 * This function updates the position of this projectile
	 */
	public void update() {
		/**
		 * Adding the speed to the comX and comY. com X and Y are the c and y
		 * values without taking into account the scrolling difference.
		 */
		comX += speedX;
		comY += speedY;
		/** Sets the center X to comX plus the scroll */
		centerX = comX + bg.getDifX();
		/**
		 * Because the game does not scroll vertically(yet) , sets the center Y
		 * to comY directly.
		 */
		centerY = comY;
		/**
		 * If the projectile is not within these boundaries, mark it for
		 * deletion on next refresh.
		 */
		if (!(centerX > -6 && centerX < rightEdge + 6 && centerY > -6 && centerY < bottomEdge + 6)) {
			alive = false;
		}
	}

	/**
	 * Gets the center Y position including the scrolling difference
	 * 
	 * @return The on-screen Y position of the center of This projectile
	 */
	public double getCenterY() {
		return centerY;
	}

	/**
	 * Gets the center X position including the scrolling difference
	 * 
	 * @return The on-screen X position of the center of this Projectile
	 */
	public double getCenterX() {
		return centerX;
	}

	/**
	 * Gets The direction that the projectile is facing, represented with an
	 * integer
	 * 
	 * @return The animation Direction this Projectile is facing
	 */
	public int getDirection() {
		return animationDirection;
	}

	/**
	 * This function returns the value of the is alive variable. If it is false,
	 * this objects should be deleted ASAP
	 * 
	 * @return Whether or not this Projectile is alive
	 */
	public boolean isAlive() {
		return alive;
	}

}

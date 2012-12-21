package daboross.gemagame.code;

import java.util.ArrayList;

public class Character {
	/** Half the total Width of the Character */
	final int lengthX = 10;
	/** Half the total Height of the Character */
	final int lengthY = 10;
	/** How much the speedY increases when the character jumps */
	final int jumpHeight = 20;
	/**
	 * How many jumps should the character have after he leaves the ground? (1
	 * is no extra jumps)
	 */
	final int maxJumpsLeft = 1;
	/**
	 * the center of the character's x position and y position. These define the
	 * center, not the upper left hand corner
	 */
	private double centerX, centerY = 100;
	/**
	 * The amount of distance that the character travels each update. Each //
	 * update these are multiplied by a Decimal
	 */
	private double speedX, speedY = 0;
	/**
	 * the amount that speedX will increase by each update
	 */
	private double velocityX = 0;
	/**
	 * the amount that is subtracted from speedY each update
	 */
	private double gravity = 1;
	/**
	 * These are changed to represent the nearest wall or floor or ceiling in
	 * each direction
	 */
	private double leftLimit, topLimit, bottomLimit, rightLimit = 0;
	/**
	 * How close the character gets to the left edge of the screen before he
	 * starts scrolling the view.
	 */
	private double leftScrollEdgeOffSet = 150;
	/**
	 * How close the character gets to the right edge of the screen before he
	 * starts scrolling the view.
	 */
	private double rightScrollEdgeOffSet = 150;
	/**
	 * These are updated each update to represent the width and height of the
	 * screen
	 */
	private int screenWidth, screenHeight;
	/**
	 * A timer used in counting how long until the character can shoot again
	 */
	private int shootTimer = 0;
	/**
	 * The amount the shootTimer is reset to when the character shoots
	 */
	private int shootTimerReset = 20;
	/**
	 * When an arrow key is pressed and the timer isn't reset yet, this variable
	 * is set to true.
	 */
	private boolean shootWhenReady = false;
	/**
	 * When the character can shoot again, if shootWhenReady, then it will shoot
	 * with the speeds shootWhenReadyX and shootWhenReadyY.
	 */
	private int shootWhenReadyX, shootWhenReadyY = 0;
	/**
	 * How fast projectiles the character shoots go.
	 */
	private int projSpeed = 10;
	/**
	 * This variable is updated when the character moves, and represents how
	 * much the character has turned or rotated. Used when the character is
	 * drawn
	 */
	private double rotation = 0;
	/**
	 * These variables are updated to hold whether the Character is limited in
	 * moving to the Left, Right, Top or Bottom
	 */
	private boolean isLimitedLeft, isLimitedRight, isLimitedTop,
			isLimitedBottom = false;
	/**
	 * This is an ArrayList of all the projectiles that are in the air made by
	 * the character.
	 */
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private ClassHandler classHandler;

	/**
	 * This defines the Character.
	 * 
	 * @param rightEdge
	 *            this is the width of the screen
	 * @param bottomEdge
	 *            This is the height of the screen
	 */
	public Character(ClassHandler classHandler, int rightEdge, int bottomEdge) {
		classHandler.setCharacter(this);
		this.classHandler = classHandler;
		screenWidth = rightEdge;
		screenHeight = bottomEdge;
		centerY = bottomEdge - lengthY;
		centerX = 0;
	}

	/**
	 * This updates the screen width and height variables with ones passed //
	 * when the update function is called
	 * 
	 * @param wPressed
	 *            This represents the state of the W key. True is pressed, false
	 *            is released
	 * @param aPressed
	 *            This represents the state of the A key. True is pressed, false
	 *            is released
	 * @param dPressed
	 *            This represents the state of the D key. True is pressed, false
	 *            is released
	 */
	public void update(boolean wPressed, boolean aPressed, boolean dPressed) {
		/**
		 * This runs a function that checks if the Character is Limited in any
		 * direction, and it sets isLimitedRight, isLimitedLeft, isLimitedTop,
		 * isLimitedBottom, leftLimit, rightLimit,topLimit,bottomLimit
		 */
		setBoundaries();
		checkBoundaries();

		/**
		 * This checks resolve conflicts with move left key and move right // If
		 * the character would move right or left, and it is limited in that
		 * direction, stop it.
		 */
		if (dPressed) {
			if (aPressed) {
				velocityX = 0;
			} else {
				velocityX = 1;
			}
		} else if (aPressed) {
			velocityX = -1;
		} else {
			velocityX = 0;
		}
		speedX += velocityX;
		speedX *= 0.9;

		// Changes in speed Y
		if ((!(isLimitedTop || isLimitedBottom))
				|| (isLimitedTop && !isLimitedBottom && speedY >= 0)) {
			speedY *= .95;
			speedY += gravity;
		}
		/** If jump key is pressed, and Character is grounded, jump */
		if (wPressed && isLimitedBottom) {
			speedY = -jumpHeight;
		}

		enforceBoundaries();

		// X
		rotation += 0.1 * speedX;
		if (centerX + speedX < leftScrollEdgeOffSet) {
			classHandler.getBackgroundHandler().changeDifX(
					leftScrollEdgeOffSet - (centerX + speedX), 0);
			centerX = leftScrollEdgeOffSet;
		} else if (centerX + speedX > screenWidth - rightScrollEdgeOffSet) {
			classHandler.getBackgroundHandler().changeDifX(
					(screenWidth - rightScrollEdgeOffSet) - (centerX + speedX),
					0);
			centerX = screenWidth - rightScrollEdgeOffSet;
		} else {
			centerX += speedX;
		}

		// Y
		if ((isLimitedBottom && !isLimitedTop && speedY < 0)
				|| (isLimitedTop && !isLimitedBottom && speedY > 0)
				|| (!isLimitedBottom && !isLimitedTop)) {
			centerY += speedY;
		}

		// Shooting
		if (shootTimer > 0) {
			shootTimer -= 1;
		} else if (shootWhenReady) {
			Projectile p = new Projectile(centerX, centerY, shootWhenReadyX
					* (projSpeed), shootWhenReadyY * (projSpeed), screenWidth,
					screenHeight, classHandler);
			projectiles.add(p);
			shootTimer = shootTimerReset;
			shootWhenReady = false;
		}
		// Prunes projectile list
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update();
			if (!p.isAlive()) {
				projectiles.remove(i);
			}
		}
	}

	/**
	 * This sets the Boundaries of the Character, It does not require any
	 * Parameters and does not return anything, as it is manipulating private
	 * variables. It sets the boundary variables, topLimit, bottomLimit,
	 * leftLimit and rightLimit
	 */
	private void setBoundaries() {
		double xDif = classHandler.getBackgroundHandler().getDifX();
		double checkX = centerX - lengthX;
		double checkY = centerY - lengthY;
		double checkLengthX = lengthX * 2;
		double checkLengthY = lengthY * 2;
		PlatformHandler platformHandler = classHandler.getPlatformHandler();
		ArrayList<Double> leftCheckList = new ArrayList<Double>();
		ArrayList<Double> rightCheckList = new ArrayList<Double>();
		ArrayList<Double> topCheckList = new ArrayList<Double>();
		ArrayList<Double> bottomCheckList = new ArrayList<Double>();
		double nearestBoundryLeft = 1000;
		double nearestBoundryRight = 1000;
		double nearestBoundryUp = 1000;
		double nearestBoundryDown = 1000;
		leftLimit = 0;
		rightLimit = screenWidth;
		topLimit = 0;
		bottomLimit = screenHeight;
		for (int i = 0; i < platformHandler.listLength(); i++) {
			if (Collision.isCollided1D(platformHandler.yPosList.get(i),
					platformHandler.yLengthList.get(i), checkY + speedY,
					checkLengthY)) {
				// If the platform is collided with Character on the y axis,
				// then add its sides as possible x-boundaries
				leftCheckList.add(platformHandler.xPosList.get(i)
						+ platformHandler.xLengthList.get(i) + xDif);
				rightCheckList.add(platformHandler.xPosList.get(i) + xDif);
			}
			if (Collision.isCollided1D(platformHandler.xPosList.get(i) + xDif,
					platformHandler.xLengthList.get(i), checkX + speedX,
					checkLengthX)) {
				// If the platform is collided with Character on the x axis,
				// then add its sides as possible y-boundaries
				topCheckList.add(platformHandler.yPosList.get(i)
						+ platformHandler.yLengthList.get(i));
				bottomCheckList.add(platformHandler.yPosList.get(i));
			}
		}
		double leftCheckX = checkX;
		for (double checkB : leftCheckList) {
			if ((checkB <= leftCheckX)
					&& (Math.abs(leftCheckX - checkB) < nearestBoundryLeft)) {
				nearestBoundryLeft = Math.abs(leftCheckX - checkB);
				leftLimit = checkB;
			}
		}
		double rightCheckX = checkX + checkLengthX;
		for (double checkB : rightCheckList) {
			if ((checkB >= rightCheckX)
					&& (Math.abs(rightCheckX - checkB) < nearestBoundryRight)) {
				nearestBoundryRight = Math.abs(rightCheckX - checkB);
				rightLimit = checkB;
			}
		}
		double topCheckY = checkY;
		for (double checkB : topCheckList) {
			if ((checkB <= topCheckY)
					&& (Math.abs(topCheckY - checkB) < nearestBoundryUp)) {
				nearestBoundryUp = Math.abs(topCheckY - checkB);
				topLimit = checkB;
			}
		}
		double bottomCheckY = checkY + checkLengthY;
		for (double checkB : bottomCheckList) {
			if ((checkB >= bottomCheckY)
					&& (Math.abs(bottomCheckY - checkB) < nearestBoundryDown)) {
				nearestBoundryDown = Math.abs(bottomCheckY - checkB);
				bottomLimit = checkB;
			}
		}
	}

	/**
	 * This checks if the Character is collided with the Boundaries that are set
	 * by setBoundaries. This function sets isLimitedLeft, isLimitedRight,
	 * isLimitedTop, and isLimitedBottom
	 */
	private void checkBoundaries() {
		double leftCheckX = centerX - lengthX + speedX;
		double rightCheckX = centerX + lengthX + speedX;
		double topCheckX = centerY - lengthY + speedY;
		double bottomCheckX = centerY + lengthY + speedY;
		if (leftCheckX <= leftLimit) {
			isLimitedLeft = true;
		} else {
			isLimitedLeft = false;
		}
		if (rightCheckX >= rightLimit) {
			isLimitedRight = true;
		} else {
			isLimitedRight = false;
		}
		if (topCheckX <= topLimit) {
			isLimitedTop = true;
		} else {
			isLimitedTop = false;
		}
		if (bottomCheckX >= bottomLimit) {
			isLimitedBottom = true;
		} else {
			isLimitedBottom = false;
		}
	}

	/**
	 * This enforces the Boundaries, using variables set by setBoundaries and
	 * checkBoundaries. This function uses the info provided by those functions
	 * and sets the speedX, speedY, centerX, and centerY.
	 */
	private void enforceBoundaries() {
		if ((isLimitedLeft && speedX < 0)) {
			speedX = 0;
			centerX = leftLimit + lengthX;
		}
		if (isLimitedRight && speedX > 0) {
			speedX = 0;
			centerX = rightLimit - lengthX;

		}
		if ((isLimitedTop && speedY < 0)) {
			speedY = 0;
			centerY = topLimit + lengthY;
		}
		if (isLimitedBottom && speedY > 0) {
			speedY = 0;
			centerY = bottomLimit - lengthY;
		}
		if ((velocityX > 0 && isLimitedRight)
				|| (velocityX < 0 && isLimitedLeft)) {
			velocityX = 0;
		}
	}

	/**
	 * This function tells the Character to shoot at the next opportunity
	 * 
	 * @param xRate
	 *            this is the x Speed that the projectile will move. This is
	 *            multiplied by the Character's ProjectileSpeed when the
	 *            Projectile is created, so only use 1, 0, or -1 if you are not
	 *            trying to make super slow or super fast projectiles
	 */
	public void shoot(int xRate, int yRate) {
		shootWhenReady = true;
		shootWhenReadyX = xRate;
		shootWhenReadyY = yRate;
	}

	/**
	 * This gets the x Position of this Characters Center.
	 * 
	 * @return the x position of the center of this character.Note that this is
	 *         the center, not the upper left hand corner
	 */
	public int getCenterX() {
		return (int) centerX;
	}

	/**
	 * This gets the y Position of this Characters Center.
	 * 
	 * @return the y position of the center of this character. Note that this is
	 *         the center, not the upper left hand corner
	 */
	public int getCenterY() {
		return (int) centerY;
	}

	/**
	 * This function returns an ArrayList of Projectiles
	 * 
	 * @return an ArrayList of the Projectiles in the Air
	 */
	public ArrayList<Projectile> getProjectiles() {
		// The projectiles that are in the air
		return projectiles;
	}

	/**
	 * This returns the Characters current rotation
	 * 
	 * @return This characters current rotation
	 */
	public double rotation() {
		return rotation;
	}

	/**
	 * @return the leftLimit
	 */
	public double getLeftLimit() {
		return leftLimit;
	}

	/**
	 * @return the topLimit
	 */
	public double getTopLimit() {
		return topLimit;
	}

	/**
	 * @return the bottomLimit
	 */
	public double getBottomLimit() {
		return bottomLimit;
	}

	/**
	 * @return the rightLimit
	 */
	public double getRightLimit() {
		return rightLimit;
	}
}
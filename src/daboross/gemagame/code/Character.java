package daboross.gemagame.code;

import java.util.ArrayList;

public class Character {
	final int lengthX = 10;// Half the total Width of the Character
	final int lengthY = 10;// Half the total Height of the Character
	final int jumpHeight = 20;// How much the speedY increases when the
								// character jumps
	final int maxJumpsLeft = 1;// How many jumps should the character have after
								// he leaves the ground? (1 is no extra jumps)
	private double centerX, centerY = 100;
	// the center of the character's x position and y position. These define the
	// center, not the upper left hand corner
	private double speedX, speedY = 0;
	// The amount of distance that the character travels each update. Each
	// update these are multiplied by a Decimal
	private double velocityX = 0;
	// the amount that speedX will increase by each update
	private double gravity = 1;
	// the amount that is subtracted from speedY each update
	private double leftLimit, topLimit, bottomLimit, rightLimit = 0;
	// These are changed to represent the nearest wall or floor or ceiling in
	// each direction

	private double leftScrollEdgeOffSet = 150;
	private double rightScrollEdgeOffSet = 150;
	// How close the character gets to the edge of the screen before he starts
	// scrolling the view.

	private double screenWidth, screenHeight;
	// These are updated each update to represent the width and height of the
	// screen

	private int shootTimer = 0;
	// A timer used in counting how long until the character can shoot again
	private int shootTimerReset = 20;
	// The amount the shootTimer is reset to when the character shoots
	private boolean shootWhenReady = false;
	// When an arrow key is pressed and the timer isn't reset yet, this variable
	// is set to true.
	private int shootWhenReadyX, shootWhenReadyY = 0;
	// When the character can shoot again, if shootWhenReady, then it will shoot
	// with the speeds shootWhenReadyX and shootWhenReadyY.
	private int projSpeed = 10;
	// How fast projectiles the character shoots go.
	private double rotation = 0;
	// This variable is updated when the character moves, and represents how
	// much the character has turned or rotated. Used when the character is
	// drawn

	private boolean isLimitedLeft, isLimitedRight = false;
	private boolean isLimitedTop, isLimitedBottom = false;
	// These variables are updated to hold whether the Character is limited in
	// moving to the Left, Right, Top or Bottom

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public Character(int rightEdge, int bottomEdge) {
		screenWidth = rightEdge;
		screenHeight = bottomEdge;
	}

	// This is an ArrayList of all the projectiles that are in the air made by
	// the character.
	public void update(boolean wPressed, boolean sPressed, boolean aPressed,
			boolean dPressed) {
		// This updates the screen width and height variables with ones passed
		// when the update function is called

		setBoundries();
		checkBoundries();
		// This runs a function that checks if the Character is Limited in any
		// direction, and it sets isLimitedRight, isLimitedLeft, isLimitedTop,
		// isLimitedBottom, leftLimit, rightLimit,topLimit,bottomLimit

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
		// Those if checks resolve conflicts with move left key and move right
		// If the character would move right or left, and it is limited in that
		// direction, stop it.
		speedX += velocityX;
		speedX *= 0.9;

		// Changes in speed Y

		if ((!(isLimitedTop || isLimitedBottom))
				|| (isLimitedTop && !isLimitedBottom && speedY >= 0)) {
			speedY *= .95d;
			speedY += gravity;
		}

		if (wPressed && isLimitedBottom) {
			speedY -= jumpHeight;
		}
		// If jump key is pressed, and it has been released since it was last
		// pressed, then jump.

		// Edge Check
		setBoundries();
		checkBoundries();
		enforceBoundries();

		// X
		rotation += 0.1 * speedX;
		if (centerX + speedX < leftScrollEdgeOffSet) {
			RunLevel.changeBg(leftScrollEdgeOffSet - (centerX + speedX), 0);
			centerX = leftScrollEdgeOffSet;
		} else if (centerX + speedX > screenWidth - rightScrollEdgeOffSet) {
			RunLevel.changeBg((screenWidth - rightScrollEdgeOffSet)
					- (centerX + speedX), 0);
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
					* (projSpeed), shootWhenReadyY * (projSpeed));
			projectiles.add(p);
			shootTimer = shootTimerReset;
			shootWhenReady = false;
		}
		// Prunes projectile list
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if (p.isAlive()) {
				p.update((int) screenWidth, (int) screenHeight);
			} else {
				projectiles.remove(i);
			}
		}
	}

	private void setBoundries() {
		double xDif = RunLevel.getBackgroundHandler().getDifX();
		double checkX = centerX - lengthX;
		double checkY = centerY - lengthY;
		double checkLengthX = lengthX * 2;
		double checkLengthY = lengthY * 2;
		PlatformHandler platformHandler = RunLevel.getPlatformHandler();
		ArrayList<Double> leftCheckList = new ArrayList<Double>();
		ArrayList<Double> rightCheckList = new ArrayList<Double>();
		ArrayList<Double> topCheckList = new ArrayList<Double>();
		ArrayList<Double> bottomCheckList = new ArrayList<Double>();
		// These are List of possible boundaries in each direction.
		double nearestBoundryLeft = 1000;
		double nearestBoundryRight = 1000;
		double nearestBoundryUp = 1000;
		double nearestBoundryDown = 1000;
		leftLimit = 1;
		rightLimit = screenWidth - 1;
		topLimit = 1;
		bottomLimit = screenHeight - 1;
		for (int i = 0; i < platformHandler.listLength(); i++) {
			if (Collision.isCollided1D(platformHandler.yPosList.get(i),
					platformHandler.yLengthList.get(i), checkY, checkLengthY)) {
				// If the platform is collided with Character on the y axis,
				// then add its sides as possible x-boundaries
				leftCheckList.add(platformHandler.xPosList.get(i)
						+ platformHandler.xLengthList.get(i) + xDif);
				rightCheckList.add(platformHandler.xPosList.get(i) + xDif);
			}
			if (Collision.isCollided1D(platformHandler.xPosList.get(i) + xDif,
					platformHandler.xLengthList.get(i), checkX, checkLengthX)) {
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

	private void checkBoundries() {
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

	private void enforceBoundries() {
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

	public void shoot(int xRate, int yRate) {
		// Tells the Character to shoot at next opportunity
		shootWhenReady = true;
		shootWhenReadyX = xRate;
		shootWhenReadyY = yRate;
	}

	public int getCenterX() {
		// The X for the center position of the Character, not the upper-left
		// hand corner
		return (int) centerX;
	}

	public int getCenterY() {
		// The Y for the center position of the Character, not the upper-left
		// hand corner
		return (int) centerY;
	}

	public ArrayList<Projectile> getProjectiles() {
		// The projectiles that are in the air
		return projectiles;
	}

	public double rotation() {
		return rotation;
	}

	public double getLeftLimit() {
		return leftLimit;
	}

	public double getTopLimit() {
		return topLimit;
	}

	public double getBottomLimit() {
		return bottomLimit;
	}

	public double getRightLimit() {
		return rightLimit;
	}
}
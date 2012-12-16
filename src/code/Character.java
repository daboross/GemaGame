package code;

import java.util.ArrayList;

public class Character {
	final int lengthX = 10;// Half the total Width of the Character
	final int lengthY = 10;// Half the total Height of the Character
	final int jumpHeight = 20;// How much the speedY increases when the
								// character jumps
	final int maxJumpsLeft = 3;// How many jumps should the character have after
								// he leaves the ground? (1 is no extra jumps)
	private double centerX, centerY = 100;// the center of the character's x
											// position and y position. These
											// define the center, not the upper
											// left hand corner
	private double speedX, speedY = 0;// The amount of distance that the
										// character travels each update. Each
										// update these are multiplied by a
										// Decimal
	private double velocityX = 0;// the amount that speedX will increase by
									// each update
	private double gravity = 1;// the amount that is subtracted from speedY each
								// update
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
	private boolean jumpVar = false;
	// This is a variable that represents if the jump button has been released
	// sense it was last pressed, making it so that you have to release and
	// repress the jump key every time you want to jump

	private int jumpsLeft = 0;
	// This variable represents how many more times the character can jump
	// before he has to touch the ground again
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// This is an ArrayList of all the projectiles that are in the air made by
	// the character.
	public void update(boolean wPressed, boolean sPressed, boolean aPressed,
			boolean dPressed, int rightEdge, int bottomEdge) {
		screenWidth = rightEdge;
		screenHeight = bottomEdge;
		// This updates the screen width and height variables with ones passed
		// when the update function is called

		edgeVarCheck();
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

		if ((velocityX > 1 && isLimitedRight)
				|| (velocityX < 1 && isLimitedLeft)) {
			velocityX = 0;
		}
		// If the character would move right or left, and it is limited in that
		// direction, stop it.
		speedX += velocityX;
		speedX *= 0.9;

		// Changes in speed Y

		if ((!(isLimitedTop || isLimitedBottom))
				|| (isLimitedTop && !isLimitedBottom && speedY >= 0)) {
			speedY *= .9;
			speedY += gravity;
		}

		if (isLimitedBottom) {
			jumpsLeft = maxJumpsLeft;
		}
		// If the Character is on the ground, reset its number of jumps it can
		// do.

		if (isLimitedTop) {
			jumpsLeft = 0;
		}
		// If the character is touch the ceiling, set its number of jumps left
		// to 0.

		if (wPressed) {
			if (jumpVar) {
				if (jumpsLeft > 0) {
					jumpVar = false;
					speedY -= jumpHeight;
					jumpsLeft -= 1;
				}
			}
		} else {
			jumpVar = true;
		}
		// If jump key is pressed, and it has been released since it was last
		// pressed, then jump.

		// Edge Check
		edgeCheck();

		// X
		rotation += 0.1 * speedX;
		if (centerX + speedX < leftScrollEdgeOffSet) {
			MainClass.changeBg(leftScrollEdgeOffSet - (centerX + speedX), 0);
			centerX = leftScrollEdgeOffSet;
		} else if (centerX + speedX > screenWidth - rightScrollEdgeOffSet) {
			MainClass.changeBg((screenWidth - rightScrollEdgeOffSet)
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

	public void edgeCheck() {
		boolean isDoneChecking;
		double xDif = MainClass.xDif();
		double checkX = centerX - lengthX + speedX;
		double checkY = centerY - lengthY + speedY;
		double checkLengthX = lengthX * 2;
		double checkLengthY = lengthY * 2;
		PlatformHandler platformHandler = MainClass.getPlatformHandler();

		// Left Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength()) && (!isDoneChecking); i++) {

			if (Collision
					.isCollided1LeftOf2(checkX, checkY, platformHandler
							.xPosList().get(i) + xDif, platformHandler
							.yPosList().get(i), checkLengthX, checkLengthY,
							platformHandler.xLengthList().get(i),
							platformHandler.yLengthList().get(i))) {
				leftLimit = platformHandler.xPosList().get(i)
						+ platformHandler.xLengthList().get(i) + xDif;
				isLimitedLeft = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedLeft = false;
			isDoneChecking = true;
		}
		// Right Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength()) && (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(platformHandler.xPosList().get(i)
					+ xDif, platformHandler.yPosList().get(i), checkX, checkY,
					platformHandler.xLengthList().get(i), platformHandler
							.yLengthList().get(i), checkLengthX, checkLengthY)) {
				rightLimit = platformHandler.xPosList().get(i) + xDif;
				isLimitedRight = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedRight = false;
			isDoneChecking = true;
		}

		// Horizontal Limiters

		if (isLimitedLeft && !isLimitedRight) {
			if (speedX < 0) {
				speedX = 0;
				centerX = leftLimit + lengthX;
			}
		}
		if (isLimitedRight && !isLimitedLeft) {
			if (speedX > 0) {
				speedX = 0;
				centerX = rightLimit - lengthX;
			}
		}

		// Updating checkX
		checkX = centerX - lengthX + speedX;

		// Bottom Edge Check

		isDoneChecking = false;
		for (int i = 0; (i < platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision
					.isCollided1Above2(checkX, checkY, platformHandler
							.xPosList().get(i) + xDif, platformHandler
							.yPosList().get(i), checkLengthX, checkLengthY,
							platformHandler.xLengthList().get(i),
							platformHandler.yLengthList().get(i))) {
				bottomLimit = platformHandler.yPosList().get(i);
				isLimitedBottom = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			bottomLimit = screenHeight;
			if (checkY + checkLengthY >= bottomLimit) {
				isLimitedBottom = true;
				isDoneChecking = true;
			}
			if (!isDoneChecking) {
				isLimitedBottom = false;
				isDoneChecking = true;
			}
		}

		// Top Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList().get(i)
					+ xDif, platformHandler.yPosList().get(i), checkX, checkY,
					platformHandler.xLengthList().get(i), platformHandler
							.yLengthList().get(i), checkLengthX, checkLengthY)) {
				topLimit = platformHandler.yPosList().get(i)
						+ platformHandler.yLengthList().get(i);
				isLimitedTop = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			topLimit = 0;
			if (checkY <= topLimit) {
				isLimitedTop = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedTop = false;
			isDoneChecking = true;
		}

		// Vertical Limiters

		if (isLimitedBottom && !isLimitedTop) {
			if (speedY >= 0) {
				speedY = 0;
				centerY = bottomLimit - lengthY;
			}
		}

		if (isLimitedTop && !isLimitedBottom) {
			if (speedY <= 0) {
				speedY = 0;
				centerY = topLimit + lengthY;
			}
		}
		if (isLimitedBottom && isLimitedTop) {
			speedY = 0;
			centerY = bottomLimit - lengthY;
		}
	}

	public void edgeVarCheck() {
		boolean isDoneChecking;
		double xDif = MainClass.xDif();
		double checkX = centerX - lengthX + speedX;
		double checkY = centerY - lengthY + speedY;
		double checkLengthX = lengthX * 2;
		double checkLengthY = lengthY * 2;
		PlatformHandler platformHandler = MainClass.getPlatformHandler();

		// Left Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength()) && (!isDoneChecking); i++) {

			if (Collision
					.isCollided1LeftOf2(checkX, checkY, platformHandler
							.xPosList().get(i) + xDif, platformHandler
							.yPosList().get(i), checkLengthX, checkLengthY,
							platformHandler.xLengthList().get(i),
							platformHandler.yLengthList().get(i))) {
				leftLimit = platformHandler.xPosList().get(i)
						+ platformHandler.xLengthList().get(i) + xDif;
				isLimitedLeft = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedLeft = false;
			isDoneChecking = true;
		}
		// Right Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength()) && (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(platformHandler.xPosList().get(i)
					+ xDif, platformHandler.yPosList().get(i), checkX, checkY,
					platformHandler.xLengthList().get(i), platformHandler
							.yLengthList().get(i), checkLengthX, checkLengthY)) {
				rightLimit = platformHandler.xPosList().get(i) + xDif;
				isLimitedRight = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedRight = false;
			isDoneChecking = true;
		}
		// Bottom Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision
					.isCollided1Above2(checkX, checkY, platformHandler
							.xPosList().get(i) + xDif, platformHandler
							.yPosList().get(i), checkLengthX, checkLengthY,
							platformHandler.xLengthList().get(i),
							platformHandler.yLengthList().get(i))) {
				bottomLimit = platformHandler.yPosList().get(i);
				isLimitedBottom = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			bottomLimit = screenHeight;
			if (checkY + checkLengthY >= bottomLimit) {
				isLimitedBottom = true;
				isDoneChecking = true;
			}
			if (!isDoneChecking) {
				isLimitedBottom = false;
				isDoneChecking = true;
			}
		}

		// Top Edge Check

		isDoneChecking = false;

		for (int i = 0; (i < platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList().get(i)
					+ xDif, platformHandler.yPosList().get(i), checkX, checkY,
					platformHandler.xLengthList().get(i), platformHandler
							.yLengthList().get(i), checkLengthX, checkLengthY)) {
				topLimit = platformHandler.yPosList().get(i)
						+ platformHandler.yLengthList().get(i);
				isLimitedTop = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			topLimit = 0;
			if (checkY <= topLimit) {
				isLimitedTop = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedTop = false;
			isDoneChecking = true;
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
}
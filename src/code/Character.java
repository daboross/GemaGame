package code;

import java.util.ArrayList;

@SuppressWarnings({ "unused" })
public class Character {
	final int lengthX = 10;
	final int lengthY = 10;
	final int jumpHeight = 20;
	final int maxJumpsLeft = 3;
	private int unused = 0;
	private double centerX, centerY = 100;
	private double speedX, speedY = 0;
	private double velocityX = 0;
	private double gravity = 1;
	private double leftLimit, topLimit, bottomLimit, rightLimit = 0;

	private double leftScrollEdgeOffSet = 150;
	private double rightScrollEdgeOffSet = 150;

	private double screenWidth, screenHeight;

	private int shootTimer = 0;
	private int shootTimerReset = 20;
	private int shootWhenReadyX, shootWhenReadyY = 0;
	private int projSpeed = 10;
	private double rotation = 0;

	private boolean isLimitedLeft, isLimitedRight = false;
	private boolean isLimitedTop, isLimitedBottom = false;
	private boolean shootWhenReady, jumpVar = false;

	private int jumpsLeft = 0;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update(boolean wPressed, boolean sPressed, boolean aPressed,
			boolean dPressed, int rightEdge, int bottomEdge) {
		screenWidth = rightEdge;
		screenHeight = bottomEdge;
		edgeVarCheck();
		// This sets check variables for the following to use.
		// Changes in speed X
		if (dPressed && !isLimitedRight) {
			if (aPressed && !isLimitedLeft) {
				velocityX = 0;
			} else {
				velocityX = 1;
			}
		} else if (aPressed && !isLimitedLeft) {
			velocityX = -1;
		} else {
			velocityX = 0;
		}
		speedX += velocityX;
		speedX *= 0.9;
		rotation += 0.1 * speedX;

		// Changes in speed Y

		if ((!(isLimitedTop || isLimitedBottom))
				|| (isLimitedTop && !isLimitedBottom && speedY >= 0)) {
			speedY *= .9;
			speedY += gravity;
		}
		if (isLimitedBottom) {
			jumpsLeft = maxJumpsLeft;
		}
		if (isLimitedTop) {
			jumpsLeft = 0;
		}
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

		// Edge Check
		edgeCheck();

		// X
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
	}

	public int getCenterX() {
		return (int) centerX;
	}

	public int getCenterY() {
		return (int) centerY;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean getGrounded() {
		return isLimitedBottom;
	}

	public void shoot(int xRate, int yRate) {
		shootWhenReady = true;
		shootWhenReadyX = xRate;
		shootWhenReadyY = yRate;
	}

	public double rotation() {
		return rotation;
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

		for (int i = 0; (i <= platformHandler.listLength()) && (!isDoneChecking); i++) {

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

		for (int i = 0; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

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
		for (int i = 0; (i <= platformHandler.listLength())
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

		for (int i = 0; (i <= platformHandler.listLength())
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

		for (int i = 0; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

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

		for (int i = 0; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

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

		for (int i = 0; (i <= platformHandler.listLength())
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

		for (int i = 0; (i <= platformHandler.listLength())
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
}
package gemagame;

import gemagame.framework.Collision;
import gemagame.framework.PlatformHandler;

import java.util.ArrayList;

@SuppressWarnings({ "rawtypes", "unused" })
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
					// System.out.println("jump");
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
			MainClass.changeBg(leftScrollEdgeOffSet - (centerX + speedX));
			centerX = leftScrollEdgeOffSet;
		} else if (centerX + speedX > screenWidth - rightScrollEdgeOffSet) {
			MainClass.changeBg((screenWidth - rightScrollEdgeOffSet)
					- (centerX + speedX));
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

		// System.out.println(" BottomLimit: " + bottomLimit + " centerY: "+
		// centerY + " isLimitedBottom: " + isLimitedBottom+ " isLimitedTop: " +
		// isLimitedTop + " speedY: " + speedY+ " topLimit: " + topLimit);
		// System.out.println(" SpeedX: " + (int) speedX + " CenterX: "
		// + (int) centerX + " Right Limit: " + (int) rightLimit
		// + " Left Limit: " + (int) leftLimit + " Is Limited Left: "
		// + isLimitedLeft + " Is Limited Right: " + isLimitedRight);
	}

	public int getCenterX() {
		return (int) centerX;
	}

	public int getCenterY() {
		return (int) centerY;
	}

	public ArrayList getProjectiles() {
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
		System.out.print(" CheckX: " + (int) checkX + " SpeedX: "
				+ (int) speedX);
		System.out.println(" Is Limited Left: " + isLimitedLeft
				+ " Is Limited Right: " + isLimitedRight + " Is Limited Top: "
				+ isLimitedTop + " Is Limmited Bottom: " + isLimitedBottom);
		// Bottom Edge Check

		isDoneChecking = false;

		MainClass.bg1().getDifX();
		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(checkX, checkY,
					platformHandler.xPosList()[i] + xDif + 1,
					platformHandler.yPosList()[i], checkLengthX, checkLengthY,
					platformHandler.xLengthList()[i] - 2,
					platformHandler.yLengthList()[i])) {
				bottomLimit = platformHandler.yPosList()[i];
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

		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList()[i]
					+ xDif + 1, platformHandler.yPosList()[i], checkX, checkY,
					platformHandler.xLengthList()[i] - 2,
					platformHandler.yLengthList()[i], checkLengthX,
					checkLengthY)) {
				topLimit = platformHandler.yPosList()[i]
						+ platformHandler.yLengthList()[i];
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

		// Left Edge Check

		isDoneChecking = false;

		for (int i = 1; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(checkX, checkY,
					platformHandler.xPosList()[i] + xDif - 1,
					platformHandler.yPosList()[i], checkLengthX, checkLengthY,
					platformHandler.xLengthList()[i] + 2,
					platformHandler.yLengthList()[i])) {
				leftLimit = platformHandler.xPosList()[i]
						+ platformHandler.xLengthList()[i] + xDif + 1;
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

		for (int i = 1; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(platformHandler.xPosList()[i]
					+ xDif - 1, platformHandler.yPosList()[i], checkX, checkY,
					platformHandler.xLengthList()[i] + 2,
					platformHandler.yLengthList()[i], checkLengthX,
					checkLengthY)) {
				rightLimit = platformHandler.xPosList()[i] + xDif - 1;
				isLimitedRight = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedRight = false;
			isDoneChecking = true;
		}

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

		// Bottom Edge Check

		isDoneChecking = false;

		MainClass.bg1().getDifX();
		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(checkX, checkY,
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], checkLengthX, checkLengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				bottomLimit = platformHandler.yPosList()[i];
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

		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList()[i]
					+ xDif, platformHandler.yPosList()[i], checkX, checkY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i], checkLengthX,
					checkLengthY)) {
				topLimit = platformHandler.yPosList()[i]
						+ platformHandler.yLengthList()[i];
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
			if (!isDoneChecking) {
				isLimitedTop = false;
				isDoneChecking = true;
			}
		}

		// Left Edge Check

		isDoneChecking = false;

		for (int i = 1; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(checkX, checkY,
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], checkLengthX, checkLengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				leftLimit = platformHandler.xPosList()[i]
						+ platformHandler.xLengthList()[i] + xDif;
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

		for (int i = 1; (i <= platformHandler.listLength())
				&& (!isDoneChecking); i++) {

			if (Collision.isCollided1LeftOf2(platformHandler.xPosList()[i]
					+ xDif, platformHandler.yPosList()[i], checkX, checkY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i], checkLengthX,
					checkLengthY)) {
				rightLimit = platformHandler.xPosList()[i] + xDif;
				isLimitedRight = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			isLimitedRight = false;
			isDoneChecking = true;
		}
	}
}
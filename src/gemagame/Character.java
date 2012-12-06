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
	private double centerX, centerY = 100;
	private double speedX, speedY = 0;
	private double velocityX = 0;
	private double cGravity = 1;
	private double gravity = 1;
	private double leftLimit, topLimit, bottomLimit, rightLimit = 0;

	private double leftEdgeOffSet = 150;
	private double rightEdgeOffSet = 150;
	private double bottomEdgeOffSet = 10;
	private double topEdgeOffSet = 10;

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
	private double platformGround = 0;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update(boolean wPressed, boolean sPressed, boolean aPressed,
			boolean dPressed, int rightEdge, int bottomEdge) {
		screenWidth = rightEdge;
		screenHeight = bottomEdge;
		edgeCheck();
		// X
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
		rotation += 0.1 * speedX;
		if (centerX + speedX < leftEdgeOffSet) {
			StartingClass.changeBg(leftEdgeOffSet - (centerX + speedX));
			centerX = leftEdgeOffSet;
		} else if (centerX + speedX > screenWidth - rightEdgeOffSet) {
			StartingClass.changeBg((screenWidth - rightEdgeOffSet)
					- (centerX + speedX));
			centerX = screenWidth - rightEdgeOffSet;
		} else {
			centerX += speedX;
		}

		// Y

		if (sPressed) {
			gravity = cGravity;
		} else {
			gravity = 0.5*cGravity;
		}
		if (isLimitedBottom) {
			if (speedY < 0) {
				centerY += speedY;
			}
		} else {
			if (!isLimitedTop) {
				centerY += speedY;
				speedY += gravity;
				if (speedY < 0) {
					speedY *= 0.8;
				} else {
					speedY *= 0.995;
				}
			} else if (speedY > 0) {
				centerY += speedY;
			}
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
					System.out.println("jump");
					jumpsLeft -= 1;
				}
			}
		} else {
			jumpVar = true;
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
		System.out.println(" BottomLimit: " + bottomLimit + " centerY: "
				+ centerY + " isLimitedBottom: " + isLimitedBottom
				+ " isLimitedTop: " + isLimitedTop + " speedY: " + speedY
				+ " topLimit: " + topLimit);
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

	public void isOnGroundCheck() {
		boolean isDoneChecking = false;
		double xDif = StartingClass.xDif();
		PlatformHandler platformHandler = StartingClass.getPlatformHandler();
		StartingClass.bg1().getDifX();
		for (int i = 1; i <= (platformHandler.listLength())
				|| (isDoneChecking == true); i++) {
			if (Collision.isCollided1Above2(centerX, centerY,
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], lengthX, lengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				platformGround = platformHandler.yPosList()[i];
				bottomLimit = platformGround - bottomEdgeOffSet;
				centerY = bottomLimit;

				isLimitedBottom = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			if (centerY + speedY >= screenHeight - bottomEdgeOffSet) {
				bottomLimit = screenHeight - bottomEdgeOffSet;
				centerY = bottomLimit;

				isLimitedBottom = true;
				isDoneChecking = true;
			}
			if (!isDoneChecking) {
				isLimitedBottom = false;
				isDoneChecking = true;
			}
		}
	}

	public void edgeCheck() {
		boolean isDoneChecking;
		double xDif = StartingClass.xDif();
		PlatformHandler platformHandler = StartingClass.getPlatformHandler();
		// TopEdgeCheck

		isDoneChecking = false;

		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList()[i]
					+ xDif, platformHandler.yPosList()[i], centerX - lengthX,
					centerY - lengthY, platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i], lengthX * 2, lengthY * 2)) {
				topLimit = platformHandler.yPosList()[i]
						+ platformHandler.yLengthList()[i] + topEdgeOffSet;
				speedY = 0;
				if (!isLimitedBottom) {
					centerY = topLimit;
					speedY = 1;
				}
				isLimitedTop = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			topLimit = topEdgeOffSet;
			if (centerY + speedY <= topLimit) {
				speedY = 0;
				centerY = topLimit + 1;
				isLimitedTop = true;
				isDoneChecking = true;
			}
			if (!isDoneChecking) {
				isLimitedTop = false;
				isDoneChecking = true;
			}
		}
		// Bottom

		isDoneChecking = false;

		StartingClass.bg1().getDifX();
		for (int i = 1; (i <= platformHandler.listLength())
				&& (isDoneChecking == false); i++) {
			if (Collision.isCollided1Above2(centerX, centerY,
					
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], lengthX, lengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				platformGround = platformHandler.yPosList()[i];
				bottomLimit = platformGround - bottomEdgeOffSet;
				centerY = bottomLimit;

				isLimitedBottom = true;
				isDoneChecking = true;
			}
		}
		if (!isDoneChecking) {
			if (centerY + speedY >= screenHeight - bottomEdgeOffSet) {
				bottomLimit = screenHeight - bottomEdgeOffSet;
				centerY = bottomLimit;

				isLimitedBottom = true;
				isDoneChecking = true;
			}
			if (!isDoneChecking) {
				isLimitedBottom = false;
				isDoneChecking = true;
			}
		}
	}
}
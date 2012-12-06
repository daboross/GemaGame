package gemagame;

import java.util.ArrayList;

import gemagame.framework.Collision;
import gemagame.framework.PlatformHandler;

@SuppressWarnings({ "rawtypes", "unused" })
public class Character {
	final int lengthX = 10;
	final int lengthY = 10;
	final int jumpHeight = 20;
	final int maxJumpsLeft = 3;
	private double centerX, centerY = 100;
	private double speedX, speedY = 0;
	private double velocityX = 0;
	private double cGravity = 2;
	private double gravity = 1;
	private double leftEdge, topEdge = 0;
	private double sideEdgeOffSet = 150;
	private double bottomEdgeOffSet = 10;
	private double topEdgeOffSet = 10;
	private double bottomEdge;
	private int shootTimer = 0;
	private int shootTimerReset = 20;
	private int shootWhenReadyX, shootWhenReadyY = 0;
	private int projSpeed = 10;
	private double rotation = 0;
	private boolean isGrounded, isOnCeiling, isOnPlatform, shootWhenReady,
			jumpVar = false;
	private int jumpsLeft = 0;
	private double platformGround = 0;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	public void update(boolean wPressed, boolean sPressed, boolean aPressed,
			boolean dPressed, int rightEdge, int bottomEdgeSet) {
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
		if (centerX + speedX < leftEdge + sideEdgeOffSet) {
			StartingClass.changeBg((leftEdge + sideEdgeOffSet)
					- (centerX + speedX));
			centerX = leftEdge + sideEdgeOffSet;
		} else if (centerX + speedX > rightEdge - sideEdgeOffSet) {
			StartingClass.changeBg((rightEdge - sideEdgeOffSet)
					- (centerX + speedX));
			centerX = rightEdge - sideEdgeOffSet;
		} else {
			centerX += speedX;
		}

		// Y

		if (sPressed) {
			gravity = cGravity;
		} else {
			gravity = 0.5;
		}

		isOnGroundCheck(bottomEdgeSet);
		if (isGrounded) {
			if (!(speedY > 0)) {
				speedY += gravity;
				centerY += speedY;
				speedY *= 0.8;
			}
		} else {
			if (!isOnCeiling) {
				centerY += speedY;
				speedY += gravity;
				if (speedY < 0) {
					speedY *= 0.8;
				}
			}
		}
		if (isGrounded) {
			jumpsLeft = maxJumpsLeft;
		}
		if (isOnCeiling) {
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
		return isGrounded;
	}

	public void shoot(int xRate, int yRate) {
		shootWhenReady = true;
		shootWhenReadyX = xRate;
		shootWhenReadyY = yRate;
	}

	public double rotation() {
		return rotation;
	}

	public boolean isOnGroundCheck(double bottomEdgeSet) {
		double xDif = StartingClass.xDif();
		PlatformHandler platformHandler = StartingClass.getPlatformHandler();
		StartingClass.bg1().getDifX();
		for (int i = 1; i <= platformHandler.listLength(); i++) {
			if (Collision.isCollided1Above2(centerX, centerY,
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], lengthX, lengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				isOnPlatform = true;
				platformGround = platformHandler.yPosList()[i];
				bottomEdge = platformGround - bottomEdgeOffSet;
				isGrounded = true;
				if (!isOnCeilingCheck()) {
					if (speedY > 0) {
						speedY = 0;
						centerY = bottomEdge;
					}

				}
				return true;
			}
		}
		if (centerY + speedY >= bottomEdgeSet - bottomEdgeOffSet) {
			isOnPlatform = false;
			bottomEdge = bottomEdgeSet - bottomEdgeOffSet;
			isGrounded = true;
			isOnCeilingCheck();
			return true;
		}
		isGrounded = false;
		isOnCeilingCheck();
		return false;
	}

	public boolean isOnCeilingCheck() {
		double xDif = StartingClass.xDif();
		PlatformHandler platformHandler = StartingClass.getPlatformHandler();
		StartingClass.bg1().getDifX();
		for (int i = 1; i <= platformHandler.listLength(); i++) {
			if (Collision.isCollided1Above2(platformHandler.xPosList()[i]
					+ xDif, platformHandler.yPosList()[i], centerX, centerY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i], lengthX, lengthY)) {
				topEdge = platformHandler.yPosList()[i]
						+ platformHandler.yLengthList()[i] + topEdgeOffSet;
				speedY = 0;
				if (!isGrounded) {
					centerY = topEdge;
				}
				isOnCeiling = true;
				return true;
			}
		}
		if (centerY + speedY <= 0) {
			topEdge = topEdgeOffSet;
			speedY = 0;
			centerY = topEdge;
			isOnCeiling = true;
			return true;
		}
		topEdge = 0;
		isOnCeiling = false;
		return false;
	}
}
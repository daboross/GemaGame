package dabogame;

import java.util.ArrayList;

import dabogame.framework.PlatformHandler;
import dabogame.framework.Collision;

@SuppressWarnings({ "rawtypes", "unused" })
public class Character {
	final int lengthX = 10;
	final int lengthY = 10;
	private double centerX, centerY = 100;
	private double speedX, speedY = 0;
	private double velocityX = 0;
	private double cGravity = 2;
	private double gravity = 1;
	private double leftEdge, topEdge = 0;
	private double sideEdgeOffSet = 150;
	private double bottomEdgeOffSet = 10;
	private double bottomEdge;
	private int shootTimer = 0;
	private int shootTimerReset = 20;
	private boolean shootWhenReady = false;
	private int shootWhenReadyX, shootWhenReadyY = 0;
	private int projSpeed = 10;
	private double rotation = 0;
	private boolean isGrounded = false;
	private boolean isOnPlatform = false;
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
		isGrounded = isOnGroundCheck(bottomEdgeSet);
		if (!isGrounded) {
			isOnCeilingCheck();
		} else {
			topEdge = 0;
		}
		if (isGrounded) {
			if (speedY > 0) {
				speedY = 0;
				centerY = bottomEdge;
			} else {
				centerY += speedY;
				speedY += gravity;
				speedY *= 0.8;
			}
		} else {
			if (speedY + centerY < topEdge) {
				speedY = 0;
				centerY = topEdge;
			} else {
				centerY += speedY;
				speedY += gravity;
				if (speedY < 0) {
					speedY *= 0.8;
				}
			}
		}

		if (sPressed) {
			gravity = cGravity;
		} else {
			gravity = 0.5;
		}

		if (wPressed && isGrounded) {
			// System.out.println("Jump");
			speedY = -25;
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
				return true;
			}
		}
		if (centerY + speedY >= bottomEdgeSet - bottomEdgeOffSet) {
			isOnPlatform = false;
			bottomEdge = bottomEdgeSet - bottomEdgeOffSet;
			return true;
		}
		return false;
	}

	public boolean isOnCeilingCheck() {
		double xDif = StartingClass.xDif();
		PlatformHandler platformHandler = StartingClass.getPlatformHandler();
		StartingClass.bg1().getDifX();
		for (int i = 1; i <= platformHandler.listLength(); i++) {
			if (Collision.isCollided1Above2(centerX, centerY,
					platformHandler.xPosList()[i] + xDif,
					platformHandler.yPosList()[i], lengthX, lengthY,
					platformHandler.xLengthList()[i],
					platformHandler.yLengthList()[i])) {
				platformGround = platformHandler.yPosList()[i];
				topEdge = platformGround - bottomEdgeOffSet;
				return true;
			}
		}
		if (centerY + speedY <= 0) {
			topEdge = 0 - bottomEdgeOffSet;
			return true;
		}
		return false;
	}
}
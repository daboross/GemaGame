package net.daboross.games.gemagame.code;

import java.util.ArrayList;

public class Character {

    /**
     * Half the total Width of the Character
     */
    final int lengthX = 10;
    /**
     * Half the total Height of the Character
     */
    final int lengthY = 10;
    /**
     * How much the speedY increases when the character jumps
     */
    final int jumpHeight = 20;
    /**
     * the center of the character's x position and y position. These define the
     * center, not the upper left hand corner
     */
    private double centerX, centerY;
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
    private ObjectHandler objectHandler;

    /**
     * This defines the Character.
     *
     * @param objectHandler this is the game's objectHandler
     */
    public Character(ObjectHandler objectHandler) {
        objectHandler.setCharacter(this);
        this.objectHandler = objectHandler;
        screenWidth = objectHandler.getScreenWidth();
        screenHeight = objectHandler.getScreenHeight();
        centerY = screenHeight - lengthY;
        centerX = 0;
    }

    /**
     * This updates the screen width and height variables with ones passed //
     * when the update function is called
     *
     * @param wPressed This represents the state of the W key. True is pressed,
     * false is released
     * @param aPressed This represents the state of the A key. True is pressed,
     * false is released
     * @param dPressed This represents the state of the D key. True is pressed,
     * false is released
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
        /**
         * If jump key is pressed, and Character is grounded, jump
         */
        if (wPressed && isLimitedBottom) {
            speedY = -jumpHeight;
        }

        enforceBoundaries();

        // X
        rotation += 0.1 * speedX;
        if (centerX + speedX < leftScrollEdgeOffSet) {
            objectHandler.getBackgroundHandler().changeDifX(
                    leftScrollEdgeOffSet - (centerX + speedX), 0);
            centerX = leftScrollEdgeOffSet;
        } else if (centerX + speedX > screenWidth - rightScrollEdgeOffSet) {
            objectHandler.getBackgroundHandler().changeDifX(
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
                    screenHeight, objectHandler);
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

    private void setBoundaries() {
        double xDif = objectHandler.getBackgroundHandler().getDifX();
        double checkX1 = centerX - lengthX;
        double checkY1 = centerY - lengthY;
        double checkX2 = centerX - lengthX + speedX;
        double checkY2 = centerY - lengthY + speedY;
        double checkLengthX = lengthX * 2;
        double checkLengthY = lengthY * 2;
        PlatformHandler platformHandler = objectHandler.getPlatformHandler();
        double nearestBoundryLeft = 1000;
        double nearestBoundryRight = 1000;
        double nearestBoundryUp = 1000;
        double nearestBoundryDown = 1000;
        leftLimit = 0;
        rightLimit = screenWidth;
        topLimit = 0;
        bottomLimit = screenHeight;
        // Setting these variables makes it so that if the checks at the bottom
        // do not fail
        int platformLimitIDLeft = -4;
        int platformLimitIDTop = -3;
        int platformLimitIDRight = -2;
        int platformLimitIDBottom = -1;
        for (int i = 0; i < platformHandler.listLength(); i++) {
            if (Collision.isCollided1D(platformHandler.xPosList(i) + xDif,
                    platformHandler.xLengthList(i), checkX1, checkLengthX)
                    || Collision.isCollided1D(platformHandler.xPosList(i)
                    + xDif, platformHandler.xLengthList(i), checkX2,
                    checkLengthX)) {
                // Check Top Platform (ctp)
                double ctp = platformHandler.yPosList(i)
                        + platformHandler.yLengthList(i);
                if ((ctp <= centerY)
                        && (Math.abs(centerY + lengthY - ctp) < nearestBoundryUp)) {
                    nearestBoundryUp = Math.abs(centerY + lengthY - ctp);
                    topLimit = ctp;
                    platformLimitIDTop = i;
                }
                // Check Bottom Platform (cbp)
                double cbp = platformHandler.yPosList(i);
                if ((cbp >= centerY)
                        && (Math.abs(centerY - lengthY - cbp) < nearestBoundryDown)) {
                    nearestBoundryDown = Math.abs(centerY - lengthY - cbp);
                    bottomLimit = cbp;
                    platformLimitIDBottom = i;
                }
            }
            if (Collision.isCollided1D(platformHandler.yPosList(i),
                    platformHandler.yLengthList(i), checkY1, checkLengthY)
                    || Collision.isCollided1D(platformHandler.yPosList(i),
                    platformHandler.yLengthList(i), checkY2,
                    checkLengthY)) {
                // Check Left Platform (clp)
                double clp = platformHandler.xPosList(i)
                        + platformHandler.xLengthList(i) + xDif;
                if ((clp <= centerX)
                        && (Math.abs(centerX + lengthX - clp) < nearestBoundryLeft)) {
                    nearestBoundryLeft = Math.abs(centerX + lengthX - clp);
                    leftLimit = clp;
                    platformLimitIDLeft = i;
                }
                // Check Right Platform (crp)
                double crp = platformHandler.xPosList(i) + xDif;
                if ((crp >= centerX)
                        && (Math.abs(centerX - lengthX - crp) < nearestBoundryRight)) {
                    nearestBoundryRight = Math.abs(centerX - lengthX - crp);
                    rightLimit = crp;
                    platformLimitIDRight = i;
                }
            }

            if (platformLimitIDTop == platformLimitIDBottom) {
                bottomLimit = platformHandler.yPosList(platformLimitIDTop);
            }
            if (platformLimitIDTop != platformLimitIDBottom
                    && platformLimitIDLeft == platformLimitIDTop) {
                leftLimit = 0;
            }
            if (platformLimitIDTop != platformLimitIDBottom
                    && platformLimitIDRight == platformLimitIDTop) {
                rightLimit = screenWidth;
            }
            if (platformLimitIDTop != platformLimitIDBottom
                    && platformLimitIDLeft == platformLimitIDBottom) {
                leftLimit = 0;
            }
            if (platformLimitIDTop != platformLimitIDBottom
                    && platformLimitIDRight == platformLimitIDBottom) {
                rightLimit = screenWidth;
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
        double leftCheckX2 = centerX - lengthX;
        double rightCheckX2 = centerX + lengthX;
        double topCheckX2 = centerY - lengthY;
        double bottomCheckX2 = centerY + lengthY;
        if (leftCheckX <= leftLimit || leftCheckX2 <= leftLimit) {
            isLimitedLeft = true;
        } else {
            isLimitedLeft = false;
        }
        if (rightCheckX >= rightLimit || rightCheckX2 >= rightLimit) {
            isLimitedRight = true;
        } else {
            isLimitedRight = false;
        }
        if (topCheckX <= topLimit || topCheckX2 <= topLimit) {
            isLimitedTop = true;
        } else {
            isLimitedTop = false;
        }
        if (bottomCheckX >= bottomLimit || bottomCheckX2 >= bottomLimit) {
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
     * @param xRate this is the x Speed that the projectile will move. This is
     * multiplied by the Character's ProjectileSpeed when the Projectile is
     * created, so only use 1, 0, or -1 if you are not trying to make super slow
     * or super fast projectiles
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
     * the center, not the upper left hand corner
     */
    public int getCenterX() {
        return (int) centerX;
    }

    /**
     * This gets the y Position of this Characters Center.
     *
     * @return the y position of the center of this character. Note that this is
     * the center, not the upper left hand corner
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
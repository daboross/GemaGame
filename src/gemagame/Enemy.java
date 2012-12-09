package gemagame;

public class Enemy {
	private int health, maxHealth = 3;
	private double centerX, centerY, comX = 0;
	private Background bg1 = MainClass.bg1();

	public void movUpdate() {
		centerX = comX + bg1.getDifX();
	}

	public void die() {

	}

	public void attack() {

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setComX(double comX) {
		this.comX = comX;
	}
}

package gemagame;

public class Enemy1 extends Enemy {

	public Enemy1(double centerX, double centerY) {
		setCenterY(centerY);
		setComX(centerX);
	}

	public void update() {
		this.movUpdate();

	}

}

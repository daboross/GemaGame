package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;

public class OverlayButton {
	private Image img;
	private int xPos;
	private int yPos;
	private int xLength;
	private int yLength;
	private ButtonReactor buttonReactor;

	public OverlayButton(Image img, int xPos, int yPos, int xLength,
			int yLength, ButtonReactor buttonReactor) {
		this.img = img;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xLength = xLength;
		this.yLength = yLength;
		this.buttonReactor = buttonReactor;
	}

	public OverlayButton(Image img, int xPos, int yPos,
			ButtonReactor buttonReactor) {
		this.img = img;
		this.xPos = xPos;
		this.yPos = yPos;
		if (img != null) {
			this.xLength = img.getWidth(null);
			this.yLength = img.getHeight(null);
		}
		this.buttonReactor = buttonReactor;
	}

	public void paintMe(Graphics g) {
		if (img != null) {
			g.drawImage(img, xPos, yPos, xLength, yLength, null);
		}
	}

	public void update(int mX, int mY) {
		if (Collision.pointOnPlane(mX, mY, xPos, yPos, xLength, yLength)) {
			buttonReactor.react(this);
		}
	}
}

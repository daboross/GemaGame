package daboross.gemagame.code.engine;

import java.awt.Graphics;
import java.awt.Image;

import daboross.gemagame.code.Collision;

public class OverlayButton {
	private Image img, hImage;
	private int xPos;
	private int yPos;
	private int xLength;
	private int yLength;
	private ButtonReactor buttonReactor;

	public OverlayButton(Image img, int xPos, int yPos, int xLength,
			int yLength, ButtonReactor buttonReactor) {
		this.img = img;
		this.hImage = img;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xLength = xLength;
		this.yLength = yLength;
		this.buttonReactor = buttonReactor;
	}

	public OverlayButton(Image img, int xPos, int yPos,
			ButtonReactor buttonReactor) {
		this.img = img;
		this.hImage = img;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xLength = img.getWidth(null);
		this.yLength = img.getHeight(null);
		this.buttonReactor = buttonReactor;
	}

	public OverlayButton(Image img, Image hoveredImage, int xPos, int yPos,
			int xLength, int yLength, ButtonReactor buttonReactor) {
		this.img = img;
		this.hImage = hoveredImage;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xLength = xLength;
		this.yLength = yLength;
		this.buttonReactor = buttonReactor;
	}

	public void update(int mX, int mY, Graphics g) {
		if (Collision.pointOnPlane(mX, mY, xPos, yPos, xLength, yLength)) {
			if (hImage != null && img != null) {
				g.drawImage(hImage, xPos, yPos, xLength, yLength, null);
			}
		} else if (img != null) {
			g.drawImage(img, xPos, yPos, xLength, yLength, null);
		}
	}

	public void clickUpdate(int mX, int mY) {
		if (Collision.pointOnPlane(mX, mY, xPos, yPos, xLength, yLength)) {
			buttonReactor.react(this);
		}
	}
}
package daboross.gemagame.code.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import daboross.gemagame.code.ObjectHandler;
import daboross.gemagame.code.Paintable;

@SuppressWarnings("unused")
public class OverlayHandler implements MouseListener, MouseMotionListener,
		FocusListener, Paintable {
	private boolean isPressed, isIn, pauseOverlayOn;
	private int mouseXPos, mouseYPos;
	private BufferedImage pauseOverlayImage;
	private ObjectHandler objectHandler;
	private ArrayList<OverlayButton> overlayButtons;

	public OverlayHandler(ObjectHandler objectHandler) {
		this.objectHandler = objectHandler;
		objectHandler.setOverlayHandler(this);
	}

	@Override
	public void paint(Graphics g) {
		if (pauseOverlayOn && pauseOverlayImage != null) {
			g.drawImage(pauseOverlayImage, 0, 0, null);
		}
		for (int i = 0; i < overlayButtons.size(); i++) {
			overlayButtons.get(i).update(mouseXPos, mouseYPos, g);
		}
	}

	public void setPauseOverlay(Image i) {
		pauseOverlayImage = (BufferedImage) i;
		pauseOverlayOn = true;
	}

	public void removePauseOverlay() {
		pauseOverlayOn = false;
	}

	public boolean isOverlayOn() {
		return pauseOverlayOn;
	}

	public boolean addButton(Image img, int xPos, int yPos,
			ButtonReactor buttonReactor) {
		if (img != null) {
			OverlayButton ob = new OverlayButton(img, xPos, yPos, buttonReactor);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	public boolean addButton(Image img, int xPos, int yPos, int xLength,
			int yLength, ButtonReactor buttonReactor) {
		if (img != null) {
			OverlayButton ob = new OverlayButton(img, xPos, yPos, xLength,
					yLength, buttonReactor);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	public boolean addButton(Image img, Image hoveredImage, int xPos, int yPos,
			int xLength, int yLength, ButtonReactor buttonReactor) {
		if (img != null) {
			OverlayButton ob = new OverlayButton(img, hoveredImage, xPos, yPos,
					xLength, yLength, buttonReactor);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseXPos = e.getX();
		mouseYPos = e.getY();
		isPressed = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseXPos = e.getX();
		mouseYPos = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		isPressed = false;
		for (int i = 0; i < overlayButtons.size(); i++) {
			overlayButtons.get(i).clickUpdate(mouseXPos, mouseYPos);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		isIn = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		isPressed = false;
		isIn = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		isPressed = true;
		isIn = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		isPressed = false;
		isIn = true;
	}

	@Override
	public void focusGained(FocusEvent e) {
		isIn = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		isIn = false;
		isPressed = false;
	}
}

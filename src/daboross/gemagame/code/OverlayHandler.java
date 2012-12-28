package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class OverlayHandler implements MouseListener, MouseMotionListener,
		FocusListener, Paintable {
	private boolean isPressed, isIn, pauseOverlayOn;
	private int xPos, yPos;
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

	@Override
	public void mouseDragged(MouseEvent e) {
		xPos = e.getX();
		yPos = e.getY();
		isPressed = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		xPos = e.getX();
		yPos = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		isPressed = false;
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

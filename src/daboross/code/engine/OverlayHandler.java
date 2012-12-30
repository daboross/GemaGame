package daboross.code.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import daboross.gemagame.code.MainClass;
import daboross.gemagame.code.ObjectHandler;
import daboross.gemagame.code.Paintable;

/**
 * This is a class to handle things that should be overlaid over the rest of the
 * game
 */
public class OverlayHandler implements MouseListener, MouseMotionListener,
		Paintable {
	private boolean pauseOverlayOn;
	private int mouseXPos, mouseYPos;
	private BufferedImage pauseOverlayImage;
	private ArrayList<OverlayButton> overlayButtons;
	private ArrayList<MouseListener> mouseListeners;

	/**
	 * Creates a new Overlay Handler, should only be used once per program
	 * 
	 * @param objectHandler
	 *            This is the programs objectHandler
	 */
	public OverlayHandler(ObjectHandler objectHandler) {
		objectHandler.setOverlayHandler(this);
		mouseListeners = new ArrayList<MouseListener>();
		overlayButtons = new ArrayList<OverlayButton>();
		MainClass mc = objectHandler.getMainClass();
		mc.addMouseListener(this);
		mc.addMouseMotionListener(this);
	}

	/**
	 * Run this function to have the overlay handler paint all of the
	 * overlays/buttons onto the graphics provided
	 * 
	 * @param g
	 *            the graphics to paint to.
	 */
	@Override
	public void paint(Graphics g) {
		if (pauseOverlayOn && pauseOverlayImage != null) {
			g.drawImage(pauseOverlayImage, 0, 0, null);
		}
		for (int i = 0; i < overlayButtons.size(); i++) {
			if ((overlayButtons.get(i).getType() == TYPE_BOTH)
					|| (overlayButtons.get(i).getType() == TYPE_PAUSEONLY && pauseOverlayOn)
					|| (overlayButtons.get(i).getType() == TYPE_UNPAUSEONLY && !pauseOverlayOn))
				overlayButtons.get(i).update(mouseXPos, mouseYPos, g);
		}
	}

	/**
	 * This creates a new overlay that will be drawn over the rest of the game.
	 * If there is already a PauseOverlay then this function will overwrite it
	 * 
	 * @param pauseOverlay2
	 *            This is the image to overlay
	 */
	public void setPauseOverlay(Image pauseOverlay2) {
		pauseOverlayImage = (BufferedImage) pauseOverlay2;
		pauseOverlayOn = true;
	}

	/** This removes the current pause overlay */
	public void removePauseOverlay() {
		pauseOverlayOn = false;
	}

	/**
	 * This function checks if there is currently a pause overlay @return
	 * Whether of not there is a Pause Overlay
	 */
	public boolean isOverlayOn() {
		return pauseOverlayOn;
	}

	/**
	 * When this is specified as a type for a button, the button will only
	 * appear of a pause overlay is on
	 */
	public static final int TYPE_PAUSEONLY = 0;
	/**
	 * When this is specified as a type for a button, the button will only
	 * appear when there is no pause overlay on
	 */
	public static final int TYPE_UNPAUSEONLY = 1;
	/**
	 * When specified as a type for a button, the button will appear both when
	 * there is a pause overlay and when there is not
	 */
	public static final int TYPE_BOTH = 2;

	/**
	 * This adds a button to the screen which will react when clicked.
	 * 
	 * @param xPos
	 *            this is the x position of the button
	 * @param yPos
	 *            this is the y position of the button
	 * @param img
	 *            this is the image to paint the button as
	 * @param type
	 *            this is the type of button to have, use one of the constants
	 *            from Overlay Button
	 * @param buttonReactor
	 *            the button will call the react() function of this class when
	 *            it is clicked
	 * @return This will return true if you have successfully added a button,
	 *         false otherwise
	 * @see TYPE_PAUSEONLY
	 * @see TYPE_UNPAUSEONLY
	 * @see TYPE_BOTH
	 */
	public boolean addButton(String ID, BufferedImage img, int xPos, int yPos,
			ButtonReactor buttonReactor, int type) {
		if (img != null) {
			OverlayButton ob = new OverlayButton(ID, img, xPos, yPos,
					buttonReactor, type);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	/**
	 * This adds a button to the screen which will react when clicked.
	 * 
	 * @param xPos
	 *            this is the x position of the button
	 * @param yPos
	 *            this is the y position of the button
	 * @param xLength
	 *            This is the horizontal length of the button
	 * @param yLength
	 *            This is the vertical length of the button
	 * @param platform
	 *            this is the image to paint the button as
	 * @param type
	 *            this is the type of button to have, use one of the constants
	 *            from Overlay Button
	 * @param buttonReactor
	 *            the button will call the react() function of this class when
	 *            it is clicked
	 * @return This will return true if you have successfully added a button,
	 *         false otherwise
	 * @see TYPE_PAUSEONLY
	 * @see TYPE_UNPAUSEONLY
	 * @see TYPE_BOTH
	 */
	public boolean addButton(String ID, Image platform, int xPos, int yPos,
			int xLength, int yLength, ButtonReactor buttonReactor, int type) {
		if (platform != null) {
			OverlayButton ob = new OverlayButton(ID, platform, xPos, yPos,
					xLength, yLength, buttonReactor, type);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	/**
	 * This adds a button to the screen which will react when clicked.
	 * 
	 * @param xPos
	 *            this is the x position of the button
	 * @param yPos
	 *            this is the y position of the button
	 * @param xLength
	 *            This is the horizontal length of the button
	 * @param yLength
	 *            This is the vertical length of the button
	 * @param img
	 *            this is the image to paint the button as
	 * @param hoveredImage
	 *            this is the image to paint the button as when it is being
	 *            hovered over
	 * @param type
	 *            this is the type of button to have, use one of the constants
	 *            from Overlay Button
	 * @param buttonReactor
	 *            the button will call the react() function of this class when
	 *            it is clicked
	 * @return This will return true if you have successfully added a button,
	 *         false otherwise
	 * @see TYPE_PAUSEONLY
	 * @see TYPE_UNPAUSEONLY
	 * @see TYPE_BOTH
	 */
	public boolean addButton(String ID, Image img, Image hoveredImage,
			int xPos, int yPos, int xLength, int yLength,
			ButtonReactor buttonReactor, int type) {
		if (img != null) {
			OverlayButton ob = new OverlayButton(ID, img, hoveredImage, xPos,
					yPos, xLength, yLength, buttonReactor, type);
			overlayButtons.add(ob);
			return true;
		}
		return false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseXPos = e.getX();
		mouseYPos = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseXPos = e.getX();
		mouseYPos = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		boolean reactedButtonExists = false;
		for (int i = 0; i < overlayButtons.size() && !reactedButtonExists; i++) {
			reactedButtonExists = overlayButtons.get(i).clickUpdate(mouseXPos,
					mouseYPos);
		}
		if (!reactedButtonExists) {
			for (MouseListener m : mouseListeners) {
				m.mouseClicked(e);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (MouseListener m : mouseListeners) {
			m.mouseEntered(e);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (MouseListener m : mouseListeners) {
			m.mouseExited(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MouseListener m : mouseListeners) {
			m.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MouseListener m : mouseListeners) {
			m.mouseReleased(e);
		}
	}

	/**
	 * This adds a mouse listener that is the object specified, the mouseClick
	 * event will only be passed if it is not clicked on a button
	 */
	public void addMouseListener(MouseListener m) {
		mouseListeners.add(m);
	}

	/**
	 * This removes a mouseListener added with addMouseListener
	 * 
	 * @see addMouseListener
	 */
	public void removeMouseListener(MouseListener m) {
		if (mouseListeners.contains(m)) {
			mouseListeners.remove(m);
		}
	}

	/** This removes all buttons from the screen */
	public void removeAllButtons() {
		overlayButtons = new ArrayList<OverlayButton>();
	}
}

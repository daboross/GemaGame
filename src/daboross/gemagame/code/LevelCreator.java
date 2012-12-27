package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFrame;

public class LevelCreator implements Runnable, Paintable, KeyListener,
		MouseListener, FocusListener, MouseMotionListener {
	private ObjectHandler classHandler;
	@SuppressWarnings("unused")
	private boolean alive, focused, mouseIn, mousePressed, shiftPressed, nVar,
			isMouseOnPlatform;
	private ArrayList<Integer> xPos, yPos, xLengths, yLengths;
	private int mousePlatformID, numberOfPlatforms, dragDifferenceX,
			dragDifferenceY, mouseX, mouseY, scroll;
	private BackgroundHandler backgroundH;
	private Image platform, backgroundImage;

	public LevelCreator(ObjectHandler classHandler) {
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			if (classHandler.getjFrame() != null) {
				String baseURL = "/daboross/gemagame/data/images/";
				Class<? extends JFrame> j = classHandler.getjFrame().getClass();
				backgroundImage = tk.createImage(j.getResource(baseURL
						+ "Background.png"));
				platform = tk.createImage(j.getResource(baseURL
						+ "platform.png"));
			} else {
				AppletMainClass apm = ((AppletMainClass) classHandler
						.getMainClass());
				URL base = new URL(apm.getDocumentBase(),
						"/daboross/gemagame/data/images/");
				backgroundImage = apm.getImage(base, "Background.png");
				platform = apm.getImage(base, "platform.png");
			}
			System.out.println("Loaded Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed");
		}
		backgroundH = new BackgroundHandler(classHandler);
		this.classHandler = classHandler;
		classHandler.setLevelCreator(this);
		focused = true;
		mouseIn = true;
		mousePressed = false;
		mousePlatformID = -1;
		numberOfPlatforms = 0;
		mouseX = classHandler.getScreenWidth() / 2;
		mouseY = classHandler.getScreenHeight() / 2;
		xPos = new ArrayList<Integer>();
		yPos = new ArrayList<Integer>();
		xLengths = new ArrayList<Integer>();
		yLengths = new ArrayList<Integer>();
	}

	@Override
	public void run() {
		classHandler.getMainClass().addKeyListener(this);
		classHandler.getMainClass().addFocusListener(this);
		classHandler.getMainClass().addMouseListener(this);
		classHandler.getMainClass().addMouseMotionListener(this);
		alive = true;
		while (alive) {
			classHandler.getMainClass().paint(this);
			if (mouseIn) {
				if (mouseX > classHandler.getScreenWidth() - 30) {
					scroll -= 5;
					backgroundH.changeDifX(-5, 0);
				}
				if (mouseX < 30) {
					scroll += 5;
					backgroundH.changeDifX(5, 0);
				}
				backgroundH.update();
			}
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
			}
		}
		classHandler.getMainClass().removeKeyListener(this);
		classHandler.getMainClass().removeFocusListener(this);
		classHandler.getMainClass().removeMouseListener(this);
		classHandler.getMainClass().removeMouseMotionListener(this);
		for (int i = 0; i < numberOfPlatforms; i++) {
		}
		Menu menu = new Menu(classHandler);
		Thread menuThread = new Thread(menu);
		classHandler.setMenuThread(menuThread);
		menuThread.start();
	}

	@Override
	public void paint(Graphics g) {
		for (int i = 0; i < backgroundH.getNumberLayers(); i++) {
			for (int k = 0; k < 2; k++) {
				int xPos = (int) backgroundH.getBgX(k, i);
				int yPos = (int) backgroundH.getBgY(k, i);
				g.drawImage(backgroundImage, xPos, yPos, null);
			}
		}
		if (numberOfPlatforms > 0) {
			for (int i = 0; i < numberOfPlatforms; i++) {
				g.drawImage(platform, xPos.get(i) + scroll, yPos.get(i),
						xLengths.get(i), yLengths.get(i), null);
			}
		}
		g.setColor(Color.white);
		g.fillRect(mouseX - 10, mouseY - 10, 20, 20);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int eventChar = e.getKeyCode();
		if (eventChar == KeyEvent.VK_N && nVar) {
			if (mouseIn) {
				nVar = false;
				xPos.add(mouseX - scroll);
				yPos.add(mouseY);
				xLengths.add(50);
				yLengths.add(50);
				numberOfPlatforms++;
			}
		}
		if (eventChar == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int eventChar = e.getKeyCode();
		if (eventChar == KeyEvent.VK_ESCAPE) {
			alive = false;
		}
		if (eventChar == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
		if (eventChar == KeyEvent.VK_N) {
			nVar = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseIn = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseIn = false;
		mousePressed = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		int mX = classHandler.getMainClass().realX(e.getX());
		int mY = classHandler.getMainClass().realY(e.getY());
		if (numberOfPlatforms != 0) {
			for (int i = 0; i < numberOfPlatforms; i++) {
				if (Collision.pointOnPlane(mX - scroll, mY, xPos.get(i),
						yPos.get(i), xLengths.get(i), yLengths.get(i))) {
					mousePlatformID = i;
					dragDifferenceX = xPos.get(i) + scroll - mX;
					dragDifferenceY = yPos.get(i) - mY;
					isMouseOnPlatform = true;
					System.out.println("OnPlatform");
					return;
				}
			}
		}
		isMouseOnPlatform = false;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}

	@Override
	public void focusGained(FocusEvent e) {
		focused = true;
	}

	@Override
	public void focusLost(FocusEvent e) {
		focused = false;
		mouseIn = false;
		mousePressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mX = classHandler.getMainClass().realX(e.getX());
		int mY = classHandler.getMainClass().realY(e.getY());
		if (isMouseOnPlatform) {
			if (shiftPressed) {
				xLengths.set(mousePlatformID,
						mX - scroll - xPos.get(mousePlatformID));
				yLengths.set(mousePlatformID, mY - yPos.get(mousePlatformID));
			} else {
				xPos.set(mousePlatformID, mX + dragDifferenceX - scroll);
				yPos.set(mousePlatformID, mY + dragDifferenceY);
			}
		}
		mouseX = mX;
		mouseY = mY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = classHandler.getMainClass().realX(e.getX());
		mouseY = classHandler.getMainClass().realY(e.getY());
	}
}

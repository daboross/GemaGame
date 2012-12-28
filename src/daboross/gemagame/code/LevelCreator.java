package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import daboross.gemagame.code.engine.FileHandler;
import daboross.gemagame.code.engine.ImageHandler;

public class LevelCreator implements Runnable, Paintable, KeyListener,
		MouseListener, FocusListener, MouseMotionListener {
	private ObjectHandler objectHandler;
	private boolean alive, mouseIn, isMouseOnPlatform;
	private ArrayList<Integer> xPos, yPos, xLengths, yLengths;
	private int mousePlatformID, numberOfPlatforms, dragDifferenceX,
			dragDifferenceY, mouseX, mouseY, scroll, mouseButton;
	private BackgroundHandler backgroundH;
	private Image platform, backgroundImage;

	public LevelCreator(ObjectHandler objectHandler) {
		ImageHandler ih = objectHandler.getImageHandler();
		backgroundImage = ih.getImage("Background.png");
		platform = ih.getImage("platform.png");
		backgroundH = new BackgroundHandler(objectHandler);
		this.objectHandler = objectHandler;
		objectHandler.setLevelCreator(this);
		mouseIn = true;
		mousePlatformID = -1;
		numberOfPlatforms = 0;
		mouseX = objectHandler.getScreenWidth() / 2;
		mouseY = objectHandler.getScreenHeight() / 2;
		try {
			PlatformList pl = objectHandler.getLevelLoader().loadToList(
					FileHandler.ReadFile("GemaGameLevels/level.txt"));
			xPos = pl.xPosList;
			yPos = pl.yPosList;
			xLengths = pl.xLengthList;
			yLengths = pl.yLengthList;
			numberOfPlatforms = pl.xPosList.size();
			System.out.println("Loaded " + pl.xPosList.size() + " platforms.");
		} catch (Exception e) {
			xPos = new ArrayList<Integer>();
			yPos = new ArrayList<Integer>();
			xLengths = new ArrayList<Integer>();
			yLengths = new ArrayList<Integer>();
			PlatformList pl;
			try {
				pl = objectHandler.getLevelLoader().loadToList(
						FileHandler.ReadInternalFile("levels/level.txt",
								objectHandler.getMainClass()));
				xPos = pl.xPosList;
				yPos = pl.yPosList;
				xLengths = pl.xLengthList;
				yLengths = pl.yLengthList;
				numberOfPlatforms = pl.xPosList.size();
				System.out.println("Loaded " + pl.xPosList.size()
						+ " platforms.");
			} catch (Exception e1) {
			}
		}
	}

	@Override
	public void run() {
		objectHandler.getMainClass().addKeyListener(this);
		objectHandler.getMainClass().addFocusListener(this);
		objectHandler.getMainClass().addMouseListener(this);
		objectHandler.getMainClass().addMouseMotionListener(this);
		alive = true;
		while (alive) {
			objectHandler.getImageHandler().paint(this);
			if (mouseIn) {
				if (mouseX > objectHandler.getScreenWidth() - 30) {
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
		objectHandler.getMainClass().removeKeyListener(this);
		objectHandler.getMainClass().removeFocusListener(this);
		objectHandler.getMainClass().removeMouseListener(this);
		objectHandler.getMainClass().removeMouseMotionListener(this);
		ArrayList<String> finalLines = new ArrayList<String>();
		for (int i = 0; i < numberOfPlatforms; i++) {
			finalLines.add(xPos.get(i) + " " + yPos.get(i) + " "
					+ xLengths.get(i) + " " + yLengths.get(i));
		}
		try {
			FileHandler.WriteFile("GemaGameLevels/", "level.txt", finalLines);
			System.out.println("Wrote File");
		} catch (Exception e) {
			e.printStackTrace();
			for (String str : finalLines) {
				System.out.println(str);
			}
		}
		Menu menu = new Menu(objectHandler);
		Thread menuThread = new Thread(menu);
		objectHandler.setMenuThread(menuThread);
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
		g.fillRect(mouseX - 2, mouseY - 2, 4, 4);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			alive = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		xPos.add(mouseX - scroll);
		yPos.add(mouseY);
		xLengths.add(50);
		yLengths.add(50);
		numberOfPlatforms++;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseIn = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseIn = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButton = e.getButton();
		int mX = objectHandler.getImageHandler().screenX(e.getX());
		int mY = objectHandler.getImageHandler().screenY(e.getY());
		boolean isFound = false;
		if (numberOfPlatforms != 0) {
			for (int i = 0; i < numberOfPlatforms && !isFound; i++) {
				if (Collision.pointOnPlane(mX - scroll, mY, xPos.get(i),
						yPos.get(i), xLengths.get(i), yLengths.get(i))) {
					mousePlatformID = i;
					dragDifferenceX = xPos.get(i) + scroll - mX;
					dragDifferenceY = yPos.get(i) - mY;
					isFound = true;
				}
			}
		}
		isMouseOnPlatform = isFound;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void focusLost(FocusEvent e) {
		mouseIn = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int mX = objectHandler.getImageHandler().screenX(e.getX());
		int mY = objectHandler.getImageHandler().screenY(e.getY());
		if (isMouseOnPlatform) {
			if (mouseButton == MouseEvent.BUTTON3) {
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
		mouseX = objectHandler.getImageHandler().screenX(e.getX());
		mouseY = objectHandler.getImageHandler().screenY(e.getY());
	}
}

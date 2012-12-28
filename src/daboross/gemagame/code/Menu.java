package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import daboross.code.engine.ImageHandler;

public class Menu implements Runnable, MouseListener, MouseMotionListener,
		Paintable {
	private final int buttonWidth = 200;
	private final int buttonHeight = 50;
	private final int buttonNameOffSet = 15;
	private final int buttonPressedNameOffSet = 20;
	private MainClass mainClass;
	private ObjectHandler objectHandler;
	private Image upperImage;
	private Image upperImageOverlay;
	private Image selectedButton;
	private Image unSelectedButton;
	private int optionSelected;
	private boolean alive;
	private String[] buttonNames;
	/* first box is which button, second box is which value */
	private int[][] buttons;

	public Menu(ObjectHandler objectHandler) {
		objectHandler.setMenu(this);
		optionSelected = -1;
		alive = true;
		this.objectHandler = objectHandler;
		mainClass = objectHandler.getMainClass();
		buttonNames = new String[3];
		buttonNames[0] = "Play";
		buttonNames[1] = "Level Maker";
		buttonNames[2] = "None Yet";
		ImageHandler ih = objectHandler.getImageHandler();
		upperImage = ih.getImage("menu/upperImage.png");
		upperImageOverlay = ih.getImage("menu/upperImage0.png");
		selectedButton = ih.getImage("menu/selectedButton.png");
		unSelectedButton = ih.getImage("menu/unSelectedButton.png");
	}

	@Override
	public void paint(Graphics g) {
		try {
			g.drawImage(
					upperImage,
					(objectHandler.getScreenWidth() - upperImage.getWidth(null)) / 2,
					10, null);
			g.drawImage(upperImageOverlay, 0, 0, null);
			g.setColor(Color.gray);
			for (int i = 0; i < 3; i++) {
				int x = (objectHandler.getScreenWidth() - buttonWidth) / 2;
				int y = 200 + i * (buttonHeight + 20);
				if (i == optionSelected) {
					g.setFont(new Font("Arial", Font.BOLD, 30));
					if (selectedButton != null) {
						g.drawImage(selectedButton, x, y, null);
					}
					g.drawString(buttonNames[i], buttonNameOffSet, y + 30);
				} else {
					g.setFont(new Font("Arial", Font.PLAIN, 30));
					if (unSelectedButton != null) {
						g.drawImage(unSelectedButton, x, y, null);
					}
					g.drawString(buttonNames[i], buttonPressedNameOffSet,
							y + 30);
				}
			}
		} catch (Exception e) {
			System.out.println("Menu Graphics Failed");
		}
	}

	@Override
	public void run() {
		System.out.println("Running Menu");
		buttons = new int[3][4];
		for (int i = 0; i < 3; i++) {
			/* x Pos */
			buttons[i][0] = (objectHandler.getScreenWidth() - 200) / 2;
			/* y Pos */
			buttons[i][1] = 200 + i * (buttonHeight + 20);
			/* x Length */
			buttons[i][2] = buttonWidth;
			/* y Length */
			buttons[i][3] = buttonHeight;
		}
		mainClass.addMouseListener(this);
		mainClass.addMouseMotionListener(this);
		while (alive) {
			try {
				objectHandler.getImageHandler().paint(this);
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mainClass.removeMouseListener(this);
		mainClass.removeMouseMotionListener(this);
	}

	public void end() {
		if (optionSelected == 0) {
			alive = false;
			RunLevel runLevel = new RunLevel(objectHandler);
			new LevelLoader(objectHandler);
			Thread runLevelThread = new Thread(runLevel);
			objectHandler.setRunLevelThread(runLevelThread);
			runLevelThread.start();
		} else if (optionSelected == 1) {
			alive = false;
			LevelCreator levelCreator = new LevelCreator(objectHandler);
			Thread levelCreatorThread = new Thread(levelCreator);
			objectHandler.setLevelCreatorThread(levelCreatorThread);
			levelCreatorThread.start();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		boolean notFound = true;
		for (int i = 0; i < buttons.length && notFound; i++) {

			if (Collision.pointOnPlane(e.getX(), e.getY(), buttons[i][0],
					buttons[i][1], buttons[i][2], buttons[i][3])) {
				optionSelected = i;
				notFound = false;
			}
		}
		if (notFound) {
			optionSelected = -1;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (optionSelected >= 0) {
			end();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
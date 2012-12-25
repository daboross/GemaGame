package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Menu implements Runnable, KeyListener, Paintable {
	private MainClass mainClass;
	private ClassHandler classHandler;
	private Image upperImage;
	private Image upperImageOverlay;
	private Image selectedButton;
	private Image unSelectedButton;
	private int optionSelected;
	private boolean alive;
	private int typeTimer;
	private boolean errorMessagedGraphics = false;

	public Menu(ClassHandler classHandler) {
		classHandler.setMenu(this);
		typeTimer = 1;
		optionSelected = 0;
		alive = true;
		this.classHandler = classHandler;
		mainClass = classHandler.getMainClass();
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			if (classHandler.getjFrame() != null) {
				Class<? extends JFrame> j = classHandler.getjFrame().getClass();
				String baseURL = "/daboross/gemagame/data/images/menu/";
				upperImage = tk.createImage(j.getResource(baseURL
						+ "upperImage.png"));
				upperImageOverlay = tk.createImage(j.getResource(baseURL
						+ "upperImage0.png"));
				selectedButton = tk.createImage(j.getResource(baseURL
						+ "selectedButton.png"));
				unSelectedButton = tk.createImage(j.getResource(baseURL
						+ "unSelectedButton.png"));
			} else {
				String baseURL = "daboross/gemagame/data/images/menu/";
				upperImage = tk.createImage(baseURL + "upperImage.png");
				upperImageOverlay = tk.createImage(baseURL + "upperImage0.png");
				selectedButton = tk.createImage(baseURL + "selectedButton.png");
				unSelectedButton = tk.createImage(baseURL
						+ "unSelectedButton.png");
			}
			System.out.println("Loaded Menu Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Menu Load Images Failed");
		}
	}

	public void paint(Graphics g) {
		try {
			g.setColor(Color.black);
			g.fillRect(0, 0, classHandler.screenWidth,
					classHandler.screenHeight);
			g.drawImage(upperImage,
					(classHandler.screenWidth - upperImage.getWidth(null)) / 2,
					10, null);
			g.drawImage(upperImageOverlay, 0, 0, null);
			for (int i = 0; i < 3; i++) {
				if (i == optionSelected) {
					g.drawImage(selectedButton,
							(classHandler.screenWidth - selectedButton
									.getWidth(null)) / 2, 200 + i * 70, null);
				} else {
					g.drawImage(unSelectedButton,
							(classHandler.screenWidth - unSelectedButton
									.getWidth(null)) / 2, 200 + i * 70, null);
				}
			}
		} catch (Exception e) {
			if (errorMessagedGraphics == false) {
				System.out.println("Menu Graphics Failed");
				errorMessagedGraphics = true;
			}
		}
	}

	@Override
	public void run() {
		System.out.println("Running Menu");
		mainClass.keyListenerAdd(this);
		while (alive) {
			if (typeTimer > 0) {
				typeTimer -= 1;
			}
			mainClass.paint(this);
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		mainClass.keyListenerRemove(this);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (typeTimer == 0) {
			System.out.println("Got Input");
			int eventChar = keyEvent.getKeyCode();
			if (eventChar == 32) {
				end();
			} else if (eventChar == 38) {
				if (optionSelected > 0) {
					optionSelected -= 1;
				}
			} else if (eventChar == 40) {
				if (optionSelected < 2) {
					optionSelected += 1;
				}
			}
			typeTimer = 1;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		typeTimer = 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public void end() {
		alive = false;
		if (optionSelected == 0) {
			RunLevel runLevel = new RunLevel(classHandler);
			new FileLoader(classHandler);
			new LevelLoader(classHandler);
			Thread runLevelThread = new Thread(runLevel);
			classHandler.setRunLevelThread(runLevelThread);
			runLevelThread.start();
		} else if (optionSelected == 1) {
			LevelCreator levelCreator = new LevelCreator(classHandler);
			Thread levelCreatorThread = new Thread(levelCreator);
			classHandler.setLevelCreatorThread(levelCreatorThread);
			levelCreatorThread.start();
		} else {
			Menu menu = new Menu(classHandler);
			Thread menuThread = new Thread(menu);
			classHandler.setMenuThread(menuThread);
			menuThread.start();
		}
	}
}
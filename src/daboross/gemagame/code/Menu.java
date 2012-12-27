package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.JFrame;

public class Menu implements Runnable, KeyListener, FocusListener, Paintable {
	private MainClass mainClass;
	private ObjectHandler objectHandler;
	private Image upperImage;
	private Image upperImageOverlay;
	private Image selectedButton;
	private Image unSelectedButton;
	private int optionSelected;
	private boolean alive;
	private int typeTimer;
	private String[] buttonNames;

	public Menu(ObjectHandler objectHandler) {
		objectHandler.setMenu(this);
		typeTimer = 1;
		optionSelected = 0;
		alive = true;
		this.objectHandler = objectHandler;
		mainClass = objectHandler.getMainClass();
		Toolkit tk = Toolkit.getDefaultToolkit();
		buttonNames = new String[3];
		buttonNames[0] = "Play";
		buttonNames[1] = "Level Maker";
		buttonNames[2] = "None Yet";
		try {
			if (objectHandler.getjFrame() != null) {
				Class<? extends JFrame> j = objectHandler.getjFrame()
						.getClass();
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
				AppletMainClass apm = ((AppletMainClass) objectHandler
						.getMainClass());
				URL base = new URL(apm.getDocumentBase(),
						"/daboross/gemagame/data/images/menu/");
				upperImage = apm.getImage(base, "upperImage.png");
				upperImageOverlay = apm.getImage(base, "upperImage0.png");
				selectedButton = apm.getImage(base, "selectedButton.png");
				unSelectedButton = apm.getImage(base, "unSelectedButton.png");
			}
			System.out.println("Loaded Menu images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Menu Load Images Failed");
		}
	}

	public void paint(Graphics g) {
		try {
			g.drawImage(
					upperImage,
					(objectHandler.getScreenWidth() - upperImage.getWidth(null)) / 2,
					10, null);
			g.drawImage(upperImageOverlay, 0, 0, null);
			g.setColor(Color.gray);
			for (int i = 0; i < 3; i++) {
				int x = (objectHandler.getScreenWidth() - selectedButton
						.getWidth(null)) / 2;
				int y = 200 + i * 70;
				if (i == optionSelected) {
					g.setFont(new Font("Arial", Font.BOLD, 30));
					g.drawImage(selectedButton, x, y, null);
					g.drawString(buttonNames[i], 20, y + 30);
				} else {
					g.setFont(new Font("Arial", Font.PLAIN, 30));
					g.drawImage(unSelectedButton, x, y, null);
					g.drawString(buttonNames[i], 25, y + 30);
				}
			}
		} catch (Exception e) {
			System.out.println("Menu Graphics Failed");
		}
	}

	@Override
	public void run() {
		System.out.println("Running Menu");
		mainClass.addKeyListener(this);
		mainClass.addFocusListener(this);
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
		mainClass.removeKeyListener(this);
		mainClass.removeFocusListener(this);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (typeTimer == 0) {
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
			RunLevel runLevel = new RunLevel(objectHandler);
			new LevelLoader(objectHandler);
			Thread runLevelThread = new Thread(runLevel);
			objectHandler.setRunLevelThread(runLevelThread);
			runLevelThread.start();
		} else if (optionSelected == 1) {
			LevelCreator levelCreator = new LevelCreator(objectHandler);
			Thread levelCreatorThread = new Thread(levelCreator);
			objectHandler.setLevelCreatorThread(levelCreatorThread);
			levelCreatorThread.start();
		} else {
			Menu menu = new Menu(objectHandler);
			Thread menuThread = new Thread(menu);
			objectHandler.setMenuThread(menuThread);
			menuThread.start();
		}
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		System.out.println("Gained Focus");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		System.out.println("Lost Focus");
	}
}
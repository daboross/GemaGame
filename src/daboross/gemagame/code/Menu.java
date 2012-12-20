package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu implements Runnable, KeyListener {
	private MainClass mainClass;
	private ClassHandler classHandler;
	private Image upperImage;
	private Image upperImageOverlay;

	public Menu(ClassHandler classHandler) {
		typeTimer = 1;
		optionSelected = 0;
		alive = true;
		this.classHandler = classHandler;
		mainClass = classHandler.getMainClass();
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			String baseURL = "daboross/gemagame/data/images/menu/";
			upperImage = tk.createImage(baseURL + "upperImage.png");
			upperImageOverlay = tk.createImage(baseURL + "upperImage0.png");
			selectedButton = tk.createImage(baseURL + "selectedButton.png");
			unSelectedButton = tk.createImage(baseURL + "unSelectedButton.png");
			System.out.println("Loaded Menu Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Menu Load Images Failed");
		}
	}

	public void paint(Graphics g) {
		try {
			g.drawImage(
					upperImage,
					(classHandler.getScreenWidth() - upperImage.getWidth(null)) / 2,
					0, null);
			g.drawImage(upperImageOverlay, 0, 0, null);
			for (int i = 0; i < 3; i++) {
				if (i == optionSelected) {
					g.drawImage(selectedButton,
							(classHandler.getScreenWidth() - selectedButton
									.getWidth(null)) / 2, 200 + i * 70, null);
				} else {
					g.drawImage(unSelectedButton,
							(classHandler.getScreenWidth() - unSelectedButton
									.getWidth(null)) / 2, 200 + i * 70, null);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("Running Menu");
		mainClass.keyListenerAdd(this);
		while (alive) {
			mainClass.paint(false);
			while (optionSelected >= 3) {
				optionSelected -= 3;
			}
			while (optionSelected < 0) {
				optionSelected += 3;
			}
			if (typeTimer > 0) {
				typeTimer -= 1;
			}
			try {
				classHandler.getMenuThread();
				Thread.sleep(30L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (typeTimer == 0) {
			int eventChar = keyEvent.getKeyCode();
			if (eventChar == 32) {
				end();
			} else if (eventChar == 38) {
				optionSelected -= 1;
			} else if (eventChar == 40) {
				optionSelected += 1;
			}
			typeTimer = 1;
		}
	}

	private Image selectedButton;
	private Image unSelectedButton;

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void end() {
		mainClass.keyListenerRemove(this);
		classHandler.getRunLevelThread().start();

		alive = false;
		System.out.println("Menu Ended");
	}

	private int optionSelected;
	private boolean alive;
	private int typeTimer;
}
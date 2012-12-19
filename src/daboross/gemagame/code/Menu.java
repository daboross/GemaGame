package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Menu implements Runnable, KeyListener {

	MainClass mainClass;
	Image upperImage;
	boolean menuAlive = true;
	ClassHandler classHandler;

	public Menu(ClassHandler classHandler) {
		this.classHandler = classHandler;
		mainClass = classHandler.getMainClass();
		try {
			URL base = mainClass.getDocumentBase();
			URL imageBase = new URL(base, "daboross/gemagame/data/images/");
			upperImage = mainClass.getImage(imageBase, "platform.png");
			System.out.println("Loaded Menu Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Menu Load Images Failed");
		}
	}

	public void paint(Graphics g) {
		mainClass.setVisible(true);
		g.setColor(Color.cyan);
		g.fillRect(1, 1, classHandler.getScreenWidth() - 2,
				classHandler.getScreenHeight() / 4 - 2);
		g.fillRect(2, 2, 638, 200);
		g.drawImage(upperImage, 2, 2, null);
		System.out.println("painting Menu");
	}

	@Override
	public void run() {
		System.out.println("Running Menu");
		mainClass.addKeyListener(this);
		mainClass.paint(false);
		System.out.println("Running Menu");
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		mainClass.removeKeyListener(this);
		classHandler.getRunLevelThread().start();
		System.out.println("Menu Ended");
	}

}

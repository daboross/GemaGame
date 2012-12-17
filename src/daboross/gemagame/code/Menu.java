package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class Menu implements Runnable, KeyListener {

	MainClass mainClass;
	Image upperImage;
	boolean menuAlive = true;

	public Menu(MainClass mainClassSet) {
		mainClass = mainClassSet;
		try {
			URL base = mainClass.getDocumentBase();
			URL imageBase = new URL(base, "daboross/gemagame/data/images/menu/");
			upperImage = mainClass.getImage(imageBase, "upperImage.png");
			System.out.println("Loaded Images -Menu");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed -Menu");
		}
	}

	public void paint(Graphics g) {
		g.drawImage(upperImage, 0, 0, null);
	}

	@Override
	public void run() {
		mainClass.addKeyListener(this);
		while (menuAlive) {
		}
		mainClass.removeKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {
		int eventChar = keyEvent.getKeyCode();
		if (eventChar == KeyEvent.VK_UP) {
		} else if (eventChar == KeyEvent.VK_DOWN) {
		} else if (eventChar == KeyEvent.VK_SPACE) {
		}
	}

}

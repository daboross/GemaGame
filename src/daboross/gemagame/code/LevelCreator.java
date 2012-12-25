package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class LevelCreator implements Runnable, Paintable, KeyListener {
	private ClassHandler classHandler;
	private boolean alive;

	public LevelCreator(ClassHandler classHandler) {
		this.classHandler = classHandler;
		classHandler.setLevelCreator(this);
	}

	@Override
	public void run() {
		classHandler.getMainClass().keyListenerAdd(this);
		alive = true;
		while (alive) {
			classHandler.getMainClass().paint(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		classHandler.getMainClass().keyListenerRemove(this);
		Menu menu = new Menu(classHandler);
		Thread menuThread = new Thread(menu);
		classHandler.setMenuThread(menuThread);
		menuThread.start();
	}

	@Override
	public void paint(Graphics g) {
		Random random = new Random();
		int i = random.nextInt(3);
		if (i == 0) {
			g.setColor(Color.blue);
		} else if (i == 1) {
			g.setColor(Color.pink);
		} else if (i == 2) {
			g.setColor(Color.cyan);
		}
		g.fillRect(0, 0, classHandler.screenWidth, classHandler.screenHeight);
		if (i == 0) {
			g.setColor(Color.pink);
		} else if (i == 1) {
			g.setColor(Color.cyan);
		} else if (i == 2) {
			g.setColor(Color.blue);
		}
		Font font = new Font("sansserif", Font.BOLD, 50);
		g.setFont(font);
		g.drawString("Press Escape", 10, classHandler.screenHeight / 2);
		g.drawString("To Return", 10, classHandler.screenHeight / 2 + 50);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		int eventChar = keyEvent.getKeyCode();
		if (eventChar == KeyEvent.VK_ESCAPE) {
			alive = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}

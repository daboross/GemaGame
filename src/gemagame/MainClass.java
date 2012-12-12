package gemagame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

import gemagame.engine.Animation;
import gemagame.engine.PlatformHandler;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class MainClass extends Applet implements Runnable, KeyListener {
	final int height = 480;
	final int width = 640;
	private Character character;
	private Graphics second;
	private Image image, background, currentCharacterSprite, proj0, proj1,
			proj2, proj3, characterImage, platform;
	private URL base;
	private boolean wPressed, sPressed, aPressed, dPressed, upPressed,
			downPressed, leftPressed, rightPressed = false;
	private int thisWidth, thisHeight = 0;
	private static Background bg1, bg2;
	private Animation characterAnimation;
	private static PlatformHandler platformHandler;

	private int graphicsWidthScale, graphicsHieghtScale = 1;
	private int graphicsTranslationX, graphicsTranslationY;

	@Override
	public void init() {
		setSize(640, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		platform = getImage(base, "gemagame/data/images/platform.png");
		characterImage = getImage(base, "gemagame/data/images/Character.png");
		background = getImage(base, "gemagame/data/images/background.png");
		proj0 = getImage(base, "gemagame/data/images/projectileLeft.png");
		proj1 = getImage(base, "gemagame/data/images/projectileUp.png");
		proj2 = getImage(base, "gemagame/data/images/projectileRight.png");
		proj3 = getImage(base, "gemagame/data/images/projectileDown.png");
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		character = new Character();
		platformHandler = new gemagame.engine.PlatformHandler();
		for (int i = -150; i < 150; i++) {
			platformHandler.addPlatForm(i * 30, (int) (Math.random() * height),
					100, 50);
		}
		Thread Thread = new Thread(this);
		Thread.start();
	}

	@Override
	public void stop() {

	}

	@Override
	public void destroy() {
	}

	@Override
	public void run() {
		while (true) {
			ArrayList projectiles = character.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isAlive()) {
					p.update(this.getWidth() + 5);
				} else {
					projectiles.remove(i);
				}
			}
			character.update(wPressed, sPressed, aPressed, dPressed,
					this.getWidth(), this.getHeight());
			bg1.update();
			bg2.update();
			repaint();
			animate();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void animate() {
		// characterAnimation.update(25);
		// characterJumpAnimation.update(25);
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
			thisWidth = this.getWidth();
			thisHeight = this.getHeight();
		}
		if (!(thisWidth == this.getWidth() && thisHeight == this.getHeight())) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
			thisWidth = this.getWidth();
			thisHeight = this.getHeight();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, this.getWidth(), this.getHeight());
		second.setColor(getForeground());
		paint(second);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, (int) bg1.getBgX(), (int) bg1.getBgY(), this);
		g.drawImage(background, (int) bg2.getBgX(), (int) bg2.getBgY(), this);
		for (int i = 0; i <= platformHandler.listLength(); i++) {
			g.drawImage(platform,
					(int) (platformHandler.xPosList().get(i) + bg1.getDifX()),
					(int) (platformHandler.yPosList().get(i) + 0),
					(int) (platformHandler.xLengthList().get(i) + 0),
					(int) (platformHandler.yLengthList().get(i) + 0), this);
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(character.getCenterX(), character.getCenterY());
		double rotate = character.rotation();
		g2d.rotate(rotate);
		g2d.drawImage(characterImage, -character.lengthX, -character.lengthY,
				this);
		g2d.rotate(-rotate);
		g2d.translate(-character.getCenterX(), -character.getCenterY());
		ArrayList projectiles = character.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			switch (p.getDirection()) {
			case 0:
				g.drawImage(proj0, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 1:
				g.drawImage(proj1, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 2:
				g.drawImage(proj2, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			case 3:
				g.drawImage(proj3, (int) p.getCenterX() - 1,
						(int) p.getCenterY() - 1, this);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_W:
			if (!wPressed) {
				wPressed = true;
			}
			break;
		case KeyEvent.VK_A:
			if (!aPressed) {
				aPressed = true;
			}
			break;
		case KeyEvent.VK_D:
			if (!dPressed) {
				dPressed = true;
			}
			break;
		case KeyEvent.VK_UP:
			if (!upPressed) {
				upPressed = true;
			}
			character.shoot(0, -1);
			break;
		case KeyEvent.VK_DOWN:
			if (!downPressed) {
				downPressed = true;
			}
			character.shoot(0, 1);
			break;
		case KeyEvent.VK_LEFT:
			if (!leftPressed) {
				leftPressed = true;
			}
			character.shoot(-1, 0);
			break;
		case KeyEvent.VK_RIGHT:
			if (!rightPressed) {
				rightPressed = true;
			}
			character.shoot(1, 0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_W:
			if (wPressed) {
				wPressed = false;
			}
			break;
		case KeyEvent.VK_A:
			if (aPressed) {
				aPressed = false;
			}
			break;
		case KeyEvent.VK_D:
			if (dPressed) {
				dPressed = false;
			}
			break;
		case KeyEvent.VK_UP:
			if (upPressed) {
				upPressed = false;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (downPressed) {
				downPressed = false;
			}
			break;
		case KeyEvent.VK_LEFT:
			if (leftPressed) {
				leftPressed = false;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (rightPressed) {
				rightPressed = false;
			}
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public static void changeBg(double change) {
		bg1.changeDifX(change);
		bg2.changeDifX(change);
	}

	public static Background bg1() {
		return bg1;
	}

	public static Background bg2() {
		return bg2;
	}

	public static double xDif() {
		return bg1.getDifX();
	}

	public static PlatformHandler getPlatformHandler() {
		return platformHandler;
	}

	public void addPlatform(double xPos, double yPos, double xLength,
			double yLength) {
		platformHandler.addPlatForm(xPos, yPos, xLength, yLength);
	}

	public Image getCurrentCharacterSprite() {
		return currentCharacterSprite;
	}

	public void setCurrentCharacterSprite(Image currentCharacterSprite) {
		this.currentCharacterSprite = currentCharacterSprite;
	}
}
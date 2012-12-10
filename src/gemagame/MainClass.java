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

import gemagame.framework.Animation;
import gemagame.framework.PlatformHandler;

@SuppressWarnings({ "serial", "rawtypes", "unused" })
public class MainClass extends Applet implements Runnable, KeyListener {
	final int height = 480;
	final int width = 640;
	private Character character;
	private Graphics second;
	private Image image, background, currentCharacterSprite, enemy1Image,
			proj0, proj1, proj2, proj3;
	private Image character0, character1, character2, character3, character4,
			character5, character6, character7, character8, character9,
			character10, character11, character12, character13, character14,
			character15, character16, character17, character18, character19,
			characterJump0, characterJump1, characterJump2, characterJump3,
			characterJump4, characterJump5, characterJump6, characterJump7,
			characterJump8, characterJump9, characterJump10, characterJump11,
			characterJump12, characterJump13, characterJump14, characterJump15,
			characterJump16, characterJump17, characterJump18, characterJump19,
			rotateCharacter, platform;
	private URL base;
	private boolean wPressed, sPressed, aPressed, dPressed, upPressed,
			downPressed, leftPressed, rightPressed = false;
	private int thisWidth, thisHeight = 0;
	private static Background bg1, bg2;
	private static Enemy1 enemy10, enemy11;
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
		platform = getImage(base, "data/platform.png");
		rotateCharacter = getImage(base, "data/RotateCharacter.png");
		enemy1Image = getImage(base, "data/enemy1.png");
		character0 = getImage(base, "data/character0.png");
		character1 = getImage(base, "data/character1.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");
		character4 = getImage(base, "data/character4.png");
		character5 = getImage(base, "data/character5.png");
		character6 = getImage(base, "data/character6.png");
		character7 = getImage(base, "data/character7.png");
		character8 = getImage(base, "data/character8.png");
		character9 = getImage(base, "data/character9.png");
		character10 = getImage(base, "data/character10.png");
		character11 = getImage(base, "data/character11.png");
		character12 = getImage(base, "data/character12.png");
		character13 = getImage(base, "data/character13.png");
		character14 = getImage(base, "data/character14.png");
		character15 = getImage(base, "data/character15.png");
		character16 = getImage(base, "data/character16.png");
		character17 = getImage(base, "data/character17.png");
		character18 = getImage(base, "data/character18.png");
		character19 = getImage(base, "data/character19.png");
		characterJump0 = getImage(base, "data/characterjump0.png");
		characterJump1 = getImage(base, "data/characterjump1.png");
		characterJump2 = getImage(base, "data/characterjump2.png");
		characterJump3 = getImage(base, "data/characterjump3.png");
		characterJump4 = getImage(base, "data/characterjump4.png");
		characterJump5 = getImage(base, "data/characterjump5.png");
		characterJump6 = getImage(base, "data/characterjump6.png");
		characterJump7 = getImage(base, "data/characterjump7.png");
		characterJump8 = getImage(base, "data/characterjump8.png");
		characterJump9 = getImage(base, "data/characterjump9.png");
		characterJump10 = getImage(base, "data/characterjump10.png");
		characterJump11 = getImage(base, "data/characterjump11.png");
		characterJump12 = getImage(base, "data/characterjump12.png");
		characterJump13 = getImage(base, "data/characterjump13.png");
		characterJump14 = getImage(base, "data/characterjump14.png");
		characterJump15 = getImage(base, "data/characterjump15.png");
		characterJump16 = getImage(base, "data/characterjump16.png");
		characterJump17 = getImage(base, "data/characterjump17.png");
		characterJump18 = getImage(base, "data/characterjump18.png");
		characterJump19 = getImage(base, "data/characterjump19.png");
		background = getImage(base, "data/background.png");
		proj0 = getImage(base, "data/projectileLeft.png");
		proj1 = getImage(base, "data/projectileUp.png");
		proj2 = getImage(base, "data/projectileRight.png");
		proj3 = getImage(base, "data/projectileDown.png");
		characterAnimation = new Animation();
		characterAnimation.addFrame(character0, 50);
		characterAnimation.addFrame(character1, 50);
		characterAnimation.addFrame(character2, 50);
		characterAnimation.addFrame(character3, 50);
		characterAnimation.addFrame(character4, 50);
		characterAnimation.addFrame(character5, 50);
		characterAnimation.addFrame(character6, 50);
		characterAnimation.addFrame(character7, 50);
		characterAnimation.addFrame(character8, 50);
		characterAnimation.addFrame(character9, 50);
		characterAnimation.addFrame(character10, 50);
		characterAnimation.addFrame(character11, 50);
		characterAnimation.addFrame(character12, 50);
		characterAnimation.addFrame(character13, 50);
		characterAnimation.addFrame(character14, 50);
		characterAnimation.addFrame(character15, 50);
		characterAnimation.addFrame(character16, 50);
		characterAnimation.addFrame(character17, 50);
		characterAnimation.addFrame(character18, 50);
		characterAnimation.addFrame(character19, 50);
		/*
		 * characterJumpAnimation = new Animation();
		 * characterJumpAnimation.addFrame(characterJump0, 50);
		 * characterJumpAnimation.addFrame(characterJump1, 50);
		 * characterJumpAnimation.addFrame(characterJump2, 50);
		 * characterJumpAnimation.addFrame(characterJump3, 50);
		 * characterJumpAnimation.addFrame(characterJump4, 50);
		 * characterJumpAnimation.addFrame(characterJump5, 50);
		 * characterJumpAnimation.addFrame(characterJump6, 50);
		 * characterJumpAnimation.addFrame(characterJump7, 50);
		 * characterJumpAnimation.addFrame(characterJump8, 50);
		 * characterJumpAnimation.addFrame(characterJump9, 50);
		 * characterJumpAnimation.addFrame(characterJump10, 50);
		 * characterJumpAnimation.addFrame(characterJump11, 50);
		 * characterJumpAnimation.addFrame(characterJump12, 50);
		 * characterJumpAnimation.addFrame(characterJump13, 50);
		 * characterJumpAnimation.addFrame(characterJump14, 50);
		 * characterJumpAnimation.addFrame(characterJump15, 50);
		 * characterJumpAnimation.addFrame(characterJump16, 50);
		 * characterJumpAnimation.addFrame(characterJump17, 50);
		 * characterJumpAnimation.addFrame(characterJump18, 50);
		 * characterJumpAnimation.addFrame(characterJump19, 50);
		 * setCurrentCharacterSprite(character0);
		 */
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		enemy10 = new Enemy1(340, 360);
		enemy11 = new Enemy1(700, 360);
		character = new Character();
		platformHandler = new gemagame.framework.PlatformHandler();
		for (int i = -150; i < 150; i++) {
			platformHandler.addPlatForm(i * 30,
					(int) (Math.random() * height), 100, 50);
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
			enemy10.update();
			enemy11.update();
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
		g.drawImage(enemy1Image, (int) enemy10.getCenterX() - 20,
				(int) enemy10.getCenterY() - 5, this);
		g.drawImage(enemy1Image, (int) enemy11.getCenterX() - 20,
				(int) enemy11.getCenterY() - 5, this);
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(character.getCenterX(), character.getCenterY());
		double rotate = character.rotation();
		g2d.rotate(rotate);
		g2d.drawImage(rotateCharacter, -character.lengthX, -character.lengthY,
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
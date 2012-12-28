package daboross.gemagame.code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class ApplicationMainClass implements MainClass, Runnable {
	private JFrame jFrame;
	private ObjectHandler objectHandler;
	private int height = 480;
	private int width = 640;
	private Image bImage1, bImage2;
	private Graphics bufferedGraphics1, bufferedGraphics2;
	private int contractedImageX, contractedImageY, rememberWidth,
			rememberHeight, imageTranslationX, imageTranslationY;
	private Paintable paintingObject, overlayObject;

	public ApplicationMainClass(ObjectHandler objectHandler) {
		this.objectHandler = objectHandler;
	}

	public static void main(String[] args) {
		ObjectHandler objectHandler = new ObjectHandler();
		ApplicationMainClass mainClass = new ApplicationMainClass(objectHandler);
		Thread mainT = new Thread(mainClass);
		objectHandler.setMainThread(mainT);
		mainT.start();

	}

	@Override
	public void run() {
		jFrame = new JFrame();
		jFrame.setSize(640, 480);
		jFrame.setTitle("Gema Game");
		jFrame.setLayout(new BorderLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBackground(Color.BLACK);
		rememberWidth = -2;
		rememberHeight = -2;
		this.width = objectHandler.getScreenWidth();
		this.height = objectHandler.getScreenHeight();
		objectHandler.setFocused(false);
		objectHandler.setApplet(false);
		objectHandler.setMainClass(this);
		objectHandler.setjFrame(jFrame);
		LoadingScreen loadingScreen = new LoadingScreen(objectHandler);
		loadingScreen.load();
	}

	@Override
	public void paint(Paintable pt) {
		paintingObject = pt;
		update(jFrame.getGraphics());
	}

	public void update(Graphics g) {
		/*
		 * If the screen is not the same size at remembered, then re-run the
		 * image transformations
		 */
		if (objectHandler.isFocused() == true) {
			if ((!(rememberWidth == jFrame.getWidth() && rememberHeight == jFrame
					.getHeight()))) {
				System.out.println("Re-sizing");
				/* Create New Images */
				bImage1 = jFrame.createImage(jFrame.getWidth(),
						jFrame.getHeight());
				bImage2 = jFrame.createImage(width, height);
				bufferedGraphics2 = bImage2.getGraphics();
				bufferedGraphics1 = bImage1.getGraphics();
				/*
				 * Remember The current Height and width, so that it can check
				 * if the height has changed before running this again
				 */
				rememberWidth = jFrame.getWidth();
				rememberHeight = jFrame.getHeight();
				/*
				 * Define contractedImageX and y depending on the height of the
				 * screen
				 */
				contractedImageY = jFrame.getHeight();
				contractedImageX = (int) ((double) contractedImageY
						/ (double) height * width);
				/*
				 * If the graphics defined by using the height make it go off
				 * the sides of the screen, redefine with the width
				 */
				if (contractedImageX > jFrame.getWidth()) {
					contractedImageX = jFrame.getWidth();
					contractedImageY = (int) ((double) contractedImageX
							/ (double) width * height);
				}
				/*
				 * Re Calculate Image Translations so that they position the
				 * image correctly
				 */
				imageTranslationX = (jFrame.getWidth() - contractedImageX) / 2;
				imageTranslationY = (jFrame.getHeight() - contractedImageY) / 2;

			}
			// clears the screen
			bufferedGraphics2.setColor(Color.black);
			bufferedGraphics2.fillRect(0, 0, width, height);
			paintingObject.paint(bufferedGraphics2);
			if (overlayObject != null) {
				overlayObject.paint(bufferedGraphics2);
			}
			bufferedGraphics1.drawImage(bImage2, imageTranslationX,
					imageTranslationY, contractedImageX + imageTranslationX,
					contractedImageY + imageTranslationY, 0, 0, width, height,
					null);
			g.drawImage(bImage1, 0, 0, jFrame);
		}
	}

	@Override
	public void addKeyListener(KeyListener l) {
		jFrame.addKeyListener(l);
	}

	@Override
	public void removeKeyListener(KeyListener l) {
		jFrame.removeKeyListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		jFrame.addMouseListener(l);
	}

	@Override
	public void removeMouseListener(MouseListener l) {
		jFrame.removeMouseListener(l);
	}

	@Override
	public void addFocusListener(FocusListener l) {
		jFrame.addFocusListener(l);
	}

	@Override
	public void removeFocusListener(FocusListener l) {
		jFrame.removeFocusListener(l);
	}

	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		jFrame.addMouseMotionListener(l);
	}

	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		jFrame.removeMouseMotionListener(l);
	}

	@Override
	public int realX(int x) {
		return (int) ((((x - (double) imageTranslationX) / contractedImageX) * width));
	}

	@Override
	public int realY(int y) {
		return (int) ((((y - (double) imageTranslationY) / contractedImageY) * height));
	}

	@Override
	public void setPaintableOverlay(Paintable p) {
		overlayObject = p;
	}
}
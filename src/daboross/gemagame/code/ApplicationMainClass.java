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

public class ApplicationMainClass implements MainClass {
	private JFrame jFrame;
	private ObjectHandler classHandler;
	private int height = 480;
	private int width = 640;
	private Image image;
	private Graphics bufferedGraphics;
	private int contractedImageX, contractedImageY, rememberWidth,
			rememberHeight, imageTranslationX, imageTranslationY;
	private int[][] drawRect;
	private Paintable paintingObject;

	public ApplicationMainClass() {

	}

	public static void main(String[] args) {
		ApplicationMainClass mainClass = new ApplicationMainClass();
		mainClass.runMe();
	}

	public void runMe() {
		jFrame = new JFrame();
		jFrame.setVisible(true);
		jFrame.setFocusable(true);
		jFrame.setSize(640, 480);
		jFrame.setTitle("Gema Game");
		jFrame.setLayout(new BorderLayout());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setBackground(Color.BLACK);
		image = jFrame.createImage(width, height);
		bufferedGraphics = image.getGraphics();
		rememberWidth = 0;
		rememberHeight = 0;
		contractedImageX = width;
		contractedImageY = height;
		imageTranslationX = 0;
		imageTranslationY = 0;
		drawRect = new int[4][4];
		setDrawRect(0, 0, 0, 0, 0);
		setDrawRect(1, 0, 0, 0, 0);
		setDrawRect(2, 0, 0, 0, 0);
		setDrawRect(3, 0, 0, 0, 0);
		classHandler = new ObjectHandler();
		classHandler.setMainClass(this);
		classHandler.setjFrame(jFrame);
		this.width = classHandler.getScreenWidth();
		this.height = classHandler.getScreenHeight();
		LoadingScreen loadingScreen = new LoadingScreen(classHandler);
		loadingScreen.load();
	}

	@Override
	public void paint(Paintable pt) {
		paintingObject = pt;
		update(jFrame.getGraphics());
	}

	public void update(Graphics g) {
		// If the screen is not the same size at remembered, then re-run the
		// image transformations
		if (!(rememberWidth == jFrame.getWidth() && rememberHeight == jFrame
				.getHeight())) {
			image = jFrame.createImage(width, height);
			bufferedGraphics = image.getGraphics();
			rememberWidth = jFrame.getWidth();
			rememberHeight = jFrame.getHeight();
			// Remembers The current Height and width, so that it can check if
			// the height has changed before running this again
			contractedImageX = width;
			contractedImageY = height;
			// redefines contractedImage width and height so that they are not
			// the ones we defined last time
			contractedImageY = jFrame.getHeight();
			// Resize graphics X so that it matches graphics Y
			contractedImageX = (int) ((double) contractedImageY
					/ (double) height * width);
			if (contractedImageX > jFrame.getWidth()) {
				// If the graphics Y is bigger then the screen Y after
				// resizing(or not resizing) y, then resize it to be even
				// smaller
				contractedImageX = jFrame.getWidth();
				// resize Y so they match
				contractedImageY = (int) ((double) contractedImageX
						/ (double) width * height);
			}
			// Calculate how far the image should be moved in order to be in the
			// center of the screen
			imageTranslationX = (jFrame.getWidth() - contractedImageX) / 2;
			imageTranslationY = (jFrame.getHeight() - contractedImageY) / 2;
			// Creates a new drawRect Variable. This v2D array remembers the
			// Coordinates that the system should draw the outside rectangles
			// The rectangles that make black edges around the center
			drawRect = new int[4][4];
			setDrawRect(0, 0, 0, imageTranslationX, jFrame.getHeight());
			setDrawRect(1, 0, 0, jFrame.getWidth(), imageTranslationY);
			setDrawRect(2, jFrame.getWidth() - imageTranslationX - 1, 0,
					imageTranslationX + 2, jFrame.getHeight());
			setDrawRect(3, 0, jFrame.getHeight() - imageTranslationY - 1,
					jFrame.getWidth(), imageTranslationY + 2);

		}
		// clears the screen
		bufferedGraphics.setColor(jFrame.getBackground());
		bufferedGraphics.fillRect(0, 0, jFrame.getWidth() + 1,
				jFrame.getHeight() + 1);
		paintingObject.paint(bufferedGraphics);
		for (int k = 0; k < 4; k++) {
			g.fillRect(drawRect[0][k], drawRect[1][k], drawRect[2][k],
					drawRect[3][k]);
		}
		g.setColor(Color.white);
		g.fillRect(imageTranslationX, imageTranslationY, 20, 20);
	}

	/**
	 * This is a helper function for the Graphics Update that records rectangle
	 * values into a 2D array
	 * 
	 * @param drawNumber
	 *            This is second number to use in setting the 2D array
	 * @param xPos
	 *            This is the x position to store
	 * @param yPos
	 *            This is the y position to store
	 * @param xLength
	 *            This is the x length to store
	 * @param yLength
	 *            This is the y length to store
	 */
	private void setDrawRect(int drawNumber, int xPos, int yPos, int xLength,
			int yLength) {
		// This is a helper function for the graphics function
		drawRect[0][drawNumber] = xPos;
		drawRect[1][drawNumber] = yPos;
		drawRect[2][drawNumber] = xLength;
		drawRect[3][drawNumber] = yLength;
		// [0][] is x position
		// [1][] is y position
		// [2][] is x length
		// [3][] is y length
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
	public int realX(double x) {
		return (int) ((((x) / contractedImageX) * width) - imageTranslationX);

	}

	@Override
	public int realY(double y) {
		return (int) ((((y) / contractedImageY) * height) - imageTranslationY);
	}
}
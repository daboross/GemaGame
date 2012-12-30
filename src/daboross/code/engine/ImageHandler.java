package daboross.code.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import daboross.gemagame.code.ObjectHandler;
import daboross.gemagame.code.Paintable;

public class ImageHandler implements Runnable {
	private final boolean ultraDebug = false;
	private boolean debug;
	private ObjectHandler objectHandler;
	private int rememberWidth, rememberHeight, contractedImageX,
			contractedImageY, imageTranslationX, imageTranslationY, realWidth,
			realHeight, fixedWidth, fixedHeight;
	private BufferedImage bImage;
	private Graphics bufferedGraphics, finalGraphics;
	private boolean isApplet;
	private Paintable p;

	public ImageHandler(ObjectHandler objH) {
		debug = objH.isDebug();
		objH.setImageHandler(this);
		objectHandler = objH;
		isApplet = objectHandler.isApplet();
		fixedWidth = objectHandler.getScreenWidth();
		fixedHeight = objectHandler.getScreenHeight();
	}

	public void setPaintable(Paintable p) {
		this.p = p;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				System.out.println("Exception Caught in ImageHandler");
				e.printStackTrace();
			}
			paint();
		}
	}

	private void paint() {
		/*
		 * If the screen is not the same size at remembered, then re-run the
		 * image transformations
		 */
		realWidth = getRealWidth();
		realHeight = getRealHeight();
		long timeK;
		if (objectHandler.isFocused() == true
				|| objectHandler.isApplet() == true) {
			if (finalGraphics == null
					|| isResizeCheck()
					|| !(rememberWidth == realWidth && rememberHeight == realHeight)) {
				if (debug)
					System.out.println("Re-sizing");
				if (isApplet) {
					if (objectHandler.getjApplet() != null) {
						finalGraphics = (Graphics2D) objectHandler.getjApplet()
								.getGraphics();
					}
				} else {
					if (objectHandler.getjPanel() != null) {
						finalGraphics = (Graphics2D) objectHandler.getjPanel()
								.getGraphics();
					}
				}
				if (finalGraphics != null) {
					finalGraphics.setColor(Color.black);
					finalGraphics.fillRect(0, 0, realWidth, realHeight);
				}
				/* Create New Images */
				bImage = new BufferedImage(fixedWidth, fixedHeight,
						BufferedImage.TYPE_INT_ARGB);
				bufferedGraphics = (Graphics2D) bImage.getGraphics();
				finalGraphics.setColor(Color.black);
				finalGraphics.fillRect(0, 0, realWidth, realHeight);
				/*
				 * Remember The current Height and width, so that it can check
				 * if the height has changed before running this again
				 */
				rememberWidth = realWidth;
				rememberHeight = realHeight;
				/*
				 * Define contractedImageX and y depending on the height of the
				 * screen
				 */
				contractedImageY = realHeight;
				contractedImageX = (int) ((double) contractedImageY
						/ (double) fixedHeight * fixedWidth);
				/*
				 * If the graphics defined by using the height make it go off
				 * the sides of the screen, redefine with the width
				 */
				if (debug) {
					System.out.println("1Real Height:" + realHeight + " Width:"
							+ realWidth + " contractedHeight:"
							+ contractedImageY + " contractedWidth:"
							+ contractedImageX);
				}
				if (contractedImageX > realWidth) {
					contractedImageX = realWidth;
					contractedImageY = (int) ((double) contractedImageX
							/ (double) fixedWidth * fixedHeight);
				}
				if (debug) {
					System.out.println("2Real Height:" + realHeight + " Width:"
							+ realWidth + " contractedHeight:"
							+ contractedImageY + " contractedWidth:"
							+ contractedImageX);
				}
				/*
				 * Re Calculate Image Translations so that they position the
				 * image correctly
				 */
				imageTranslationX = (realWidth - contractedImageX) / 2;
				imageTranslationY = (realHeight - contractedImageY) / 2;
				if (debug) {
					System.out.println("X: " + imageTranslationX + " Y: "
							+ imageTranslationY);
				}
			}
			if (p != null) {
				if (ultraDebug) {
					timeK = System.nanoTime();
				}
				// clears the screen
				bufferedGraphics.setColor(Color.black);
				bufferedGraphics.fillRect(0, 0, fixedWidth, fixedHeight);
				if (ultraDebug) {
					System.out.println((System.nanoTime() - timeK)
							+ "   :After Filling In");
					timeK = System.nanoTime();
				}
				p.paint(bufferedGraphics);
				if (ultraDebug) {
					System.out.println((System.nanoTime() - timeK)
							+ "   :After Painting Paintable:");
					timeK = System.nanoTime();
				}
				if (objectHandler.getOverlayHandler() != null) {
					objectHandler.getOverlayHandler().paint(bufferedGraphics);
				}
				if (ultraDebug) {
					System.out.println((System.nanoTime() - timeK)
							+ "   :After Overlay Handler:");
					timeK = System.nanoTime();
				}
				finalGraphics.drawImage(bImage, imageTranslationX,
						imageTranslationY,
						contractedImageX + imageTranslationX, contractedImageY
								+ imageTranslationY, 0, 0, fixedWidth,
						fixedHeight, null);
				if (ultraDebug) {
					System.out.println((System.nanoTime() - timeK)
							+ "   :After Final Drawing");
				}
			}
		}
	}

	private boolean isResizeCheck() {

		Rectangle bounds = ((Graphics2D) finalGraphics)
				.getDeviceConfiguration().getBounds();
		if (bounds.height < realHeight || bounds.width < realWidth) {
			return true;
		}
		return false;
	}

	private int getRealWidth() {
		if (objectHandler.isApplet()) {
			if (objectHandler.getjApplet() != null) {
				Integer w = objectHandler.getjApplet().getWidth();
				if (w != null && w > 0) {
					return w;
				}
			}
		} else {
			if (objectHandler.getjPanel() != null) {
				Integer w = objectHandler.getjPanel().getWidth();
				if (w != null && w > 0) {
					return w;
				}
			}
		}
		return fixedWidth;
	}

	private int getRealHeight() {
		if (objectHandler.isApplet()) {
			if (objectHandler.getjApplet() != null) {
				Integer h = objectHandler.getjApplet().getHeight();
				if (h != null && h > 0) {
					return h;
				}
			}
		} else {
			if (objectHandler.getjPanel() != null) {
				Integer h = objectHandler.getjPanel().getHeight();
				if (h != null && h > 0) {
					return h;
				}
			}
		}
		return fixedHeight;
	}

	public Image getImage(String imgName) {
		if (debug) {
			System.out.println("Getting image: /daboross/gemagame/data/images/"
					+ imgName);
		}
		BufferedImage img = null;
		URL fl = ImageHandler.class
				.getResource("/daboross/gemagame/data/images/" + imgName);
		if (fl != null) {
			try {
				img = ImageIO.read(fl);
			} catch (IOException e) {
				if (debug) {
					System.out.println("Failed to load Image");
				}
			}
		} else {
			if (debug) {
				System.out
						.println("Failed to load Image. Resource Stream Not Found");
			}
		}
		if (debug) {
			if (img == null) {
				System.out.println("Failed, returning null");
			} else {
				System.out.println("Got Image, returning image");
			}
		}
		return img;
	}

	public int screenX(int x) {
		return (int) ((((x - (double) imageTranslationX) / contractedImageX) * fixedWidth));
	}

	public int screenY(int y) {
		return (int) ((((y - (double) imageTranslationY) / contractedImageY) * fixedHeight));
	}
}

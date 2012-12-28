package daboross.code.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import daboross.gemagame.code.ObjectHandler;
import daboross.gemagame.code.Paintable;

public class ImageHandler {
	private boolean debug = false;
	private ObjectHandler objectHandler;
	private int rememberWidth, rememberHeight, contractedImageX,
			contractedImageY, imageTranslationX, imageTranslationY, realWidth,
			realHeight, fixedWidth, fixedHeight;
	private Image bImage1, bImage2;
	private Graphics bufferedGraphics1, bufferedGraphics2;
	private Paintable overlay;
	private boolean resizeNext, isApplet;

	public ImageHandler(ObjectHandler objH) {
		debug = objH.isDebug();
		objH.setImageHandler(this);
		objectHandler = objH;
		isApplet = objectHandler.isApplet();
		fixedWidth = objectHandler.getScreenWidth();
		fixedHeight = objectHandler.getScreenHeight();
	}

	public void paint(Paintable p) {
		/*
		 * If the screen is not the same size at remembered, then re-run the
		 * image transformations
		 */
		realWidth = getRealWidth();
		realHeight = getRealHeight();
		if (objectHandler.isFocused() == true
				|| objectHandler.isApplet() == true) {
			if (!(rememberWidth == realWidth && rememberHeight == realHeight)
					|| resizeNext) {
				resizeNext = false;
				System.out.println("Re-sizing");
				/* Create New Images */
				if (isApplet) {
					if (objectHandler.getApplet() != null) {
						bImage1 = objectHandler.getApplet().createImage(
								realWidth, realHeight);
						bImage2 = objectHandler.getApplet().createImage(
								fixedWidth, fixedHeight);
					}
				} else {
					if (objectHandler.getjFrame() != null) {
						bImage1 = objectHandler.getjFrame().createImage(
								realWidth, realHeight);
						bImage2 = objectHandler.getjFrame().createImage(
								fixedWidth, fixedHeight);
					}
				}
				if (bImage1 != null) {
					bufferedGraphics1 = bImage1.getGraphics();
				}
				if (bImage2 != null) {
					bufferedGraphics2 = bImage2.getGraphics();
				}
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
				if (contractedImageX > realWidth) {
					contractedImageX = realHeight;
					contractedImageY = (int) ((double) contractedImageX
							/ (double) fixedWidth * fixedHeight);
				}
				/*
				 * Re Calculate Image Translations so that they position the
				 * image correctly
				 */
				imageTranslationX = (realWidth - contractedImageX) / 2;
				imageTranslationY = (realHeight - contractedImageY) / 2;

			}
			// clears the screen
			bufferedGraphics2.setColor(Color.black);
			bufferedGraphics2.fillRect(0, 0, fixedWidth, fixedHeight);
			p.paint(bufferedGraphics2);
			if (overlay != null) {
				overlay.paint(bufferedGraphics2);
			}
			bufferedGraphics1.drawImage(bImage2, imageTranslationX,
					imageTranslationY, contractedImageX + imageTranslationX,
					contractedImageY + imageTranslationY, 0, 0, fixedWidth,
					fixedHeight, null);
			drawFinalImage(bImage1);
		}
	}

	private void drawFinalImage(Image img) {
		if (isApplet) {
			if (objectHandler.getApplet() != null) {
				objectHandler.getApplet().getGraphics()
						.drawImage(img, 0, 0, objectHandler.getApplet());
			}
		} else {
			if (objectHandler.getjFrame() != null) {
				objectHandler.getjFrame().getGraphics()
						.drawImage(img, 0, 0, objectHandler.getjFrame());
			}
		}
	}

	private int getRealWidth() {
		if (objectHandler.isApplet()) {
			if (objectHandler.getApplet() != null) {
				Integer w = objectHandler.getApplet().getWidth();
				if (w != null && w > 0) {
					return w;
				}
			}
		} else {
			if (objectHandler.getjFrame() != null) {
				Integer w = objectHandler.getjFrame().getWidth();
				if (w != null && w > 0) {
					return w;
				}
			}
		}
		return fixedWidth;
	}

	private int getRealHeight() {
		if (objectHandler.isApplet()) {
			if (objectHandler.getApplet() != null) {
				Integer h = objectHandler.getApplet().getHeight();
				if (h != null && h > 0) {
					return h;
				}
			}
		} else {
			if (objectHandler.getjFrame() != null) {
				Integer h = objectHandler.getjFrame().getHeight();
				if (h != null && h > 0) {
					return h;
				}
			}
		}
		return fixedHeight;
	}

	public Image getImage(String imgName) {
		Image returnImg = null;
		if (debug) {
			System.out.println("Getting image: /daboross/gemagame/data/images/"
					+ imgName);
		}
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			returnImg = tk.createImage(getClass().getResource(
					"/daboross/gemagame/data/images/" + imgName));
			if (debug) {
				System.out.println("Loaded Image");
			}
		} catch (Exception e) {
			if (debug) {
				System.out.println("Failed to load Image");
			}
		}
		if (debug) {
			if (returnImg == null) {
				System.out.println("Image null, returning null");
			} else {
				System.out.println("Image Found, not returning null");
			}
		}
		return returnImg;
	}

	public int screenX(int x) {
		return (int) ((((x - (double) imageTranslationX) / contractedImageX) * fixedWidth));
	}

	public int screenY(int y) {
		return (int) ((((y - (double) imageTranslationY) / contractedImageY) * fixedHeight));
	}

	public void setPaintableOverlay(Paintable p) {
		overlay = p;
	}
}

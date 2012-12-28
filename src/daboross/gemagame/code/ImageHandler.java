package daboross.gemagame.code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ImageHandler {
	private ObjectHandler objectHandler;
	private int rememberWidth, rememberHeight, contractedImageX,
			contractedImageY, imageTranslationX, imageTranslationY, realWidth,
			realHeight, fixedWidth, fixedHeight;
	private Image bImage1, bImage2;
	private Graphics bufferedGraphics1, bufferedGraphics2;
	private Paintable overlay;
	private boolean resizeNext, isApplet;

	public ImageHandler(ObjectHandler objH) {
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
		if (objectHandler.isFocused() == true) {
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
		if (isApplet) {
			if (objectHandler.getApplet() != null) {
				try {
					URL base = new URL(objectHandler.getApplet()
							.getDocumentBase(),
							"/daboross/gemagame/data/images/");
					objectHandler.getApplet().getImage(base, imgName);
				} catch (Exception e) {
					System.out.println("Failed to load Image: "
							+ "/daboross/gemagame/data/images/" + imgName
							+ " From Applet");
				}
			}
		} else {
			try {
				Toolkit tk = Toolkit.getDefaultToolkit();
				return tk.createImage(getClass().getResource(
						"/daboross/gemagame/data/images/" + imgName));
			} catch (Exception e) {
				System.out.println("Failed to load Image: "
						+ "/daboross/gemagame/data/images/" + imgName
						+ " From JFrame");
			}

		}
		return null;
	}
}

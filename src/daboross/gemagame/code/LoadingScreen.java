package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

public class LoadingScreen implements Paintable {

	private ObjectHandler objectHandler;
	private Image loadingImage;

	public LoadingScreen(ObjectHandler objectHandler) {
		objectHandler.setLoadingScreen(this);
		this.objectHandler = objectHandler;
	}

	public void load() {
		System.out.println("Loading...");
		try {
			if (objectHandler.getjFrame() == null) {
				AppletMainClass apm = ((AppletMainClass) objectHandler
						.getMainClass());
				URL base = new URL(apm.getDocumentBase(),
						"/daboross/gemagame/data/images/");
				loadingImage = apm.getImage(base, "loading.png");
			} else {
				Toolkit tk = Toolkit.getDefaultToolkit();
				String baseURL = "/daboross/gemagame/data/images/";
				Class<? extends JFrame> j = objectHandler.getjFrame().getClass();
				loadingImage = tk.createImage(j.getResource(baseURL
						+ "loading.png"));

			}
			System.out.println("Loaded Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed");
		}
		objectHandler.getMainClass().paint(this);
		objectHandler.setLoadingScreen(this);
		Menu menu = new Menu(objectHandler);
		Thread menuThread = new Thread(menu);
		objectHandler.setMenuThread(menuThread);
		menuThread.start();
	}

	public void paint(Graphics g) {
		g.drawImage(loadingImage, 0, 0, 640, 480, null);
	}
}

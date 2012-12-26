package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

public class LoadingScreen implements Paintable {

	private ClassHandler classHandler;
	private Image loadingImage;

	public LoadingScreen(ClassHandler classHandler) {
		classHandler.setLoadingScreen(this);
		this.classHandler = classHandler;
	}

	public void load() {
		System.out.println("Loading...");
		try {
			if (classHandler.getjFrame() == null) {
				AppletMainClass apm = ((AppletMainClass) classHandler
						.getMainClass());
				URL base = new URL(apm.getDocumentBase(),
						"/daboross/gemagame/data/images/");
				loadingImage = apm.getImage(base, "loading.png");
			} else {
				Toolkit tk = Toolkit.getDefaultToolkit();
				String baseURL = "/daboross/gemagame/data/images/";
				Class<? extends JFrame> j = classHandler.getjFrame().getClass();
				loadingImage = tk.createImage(j.getResource(baseURL
						+ "loading.png"));

			}
			System.out.println("Loaded Images");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Images Failed");
		}
		classHandler.getMainClass().paint(this);
		classHandler.setLoadingScreen(this);
		Menu menu = new Menu(classHandler);
		Thread menuThread = new Thread(menu);
		classHandler.setMenuThread(menuThread);
		menuThread.start();
	}

	public void paint(Graphics g) {
		g.drawImage(loadingImage, 0, 0, 640, 480, null);
	}
}

package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class LoadingScreen implements Paintable {

	private ClassHandler classHandler;
	private Image loadingImage;

	public LoadingScreen(ClassHandler classHandler) {
		classHandler.setLoadingScreen(this);
		this.classHandler = classHandler;
	}

	public void load(boolean isJFrame) {
		System.out.println("Loading...");
		try {
			Toolkit tk = Toolkit.getDefaultToolkit();
			if (classHandler.getjFrame() == null) {
				String baseURL = "daboross/gemagame/data/images/";
				loadingImage = tk.createImage(baseURL + "loading.png");
			} else {
				String baseURL = "/daboross/gemagame/data/images/";
				Class<? extends JFrame> j = classHandler.getjFrame().getClass();
				loadingImage = tk.createImage(j.getResource(baseURL
						+ "Background.png"));

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

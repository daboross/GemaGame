package daboross.gemagame.code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class LoadingScreen {

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
				loadingImage = tk.createImage(baseURL + "Background.png");
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
		classHandler.getMainClass().paint(2);
		classHandler.setLoadingScreen(this);
		RunLevel runLevel = new RunLevel(classHandler);
		Menu menu = new Menu(classHandler);
		new FileLoader(classHandler);
		LevelFileWriter levelFileWriter = new LevelFileWriter(classHandler);
		new LevelLoader(classHandler);
		Thread levelFileWriterThread = new Thread(levelFileWriter);
		Thread menuThread = new Thread(menu);
		Thread runLevelThread = new Thread(runLevel);
		classHandler.setMenuThread(menuThread);
		classHandler.setRunLevelThread(runLevelThread);
		classHandler.setLevelWriterThread(levelFileWriterThread);
		menuThread.start();
	}

	public void paint(Graphics g) {
		g.drawImage(loadingImage, 0, 0, 640, 480, null);
	}
}

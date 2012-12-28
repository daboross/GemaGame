package daboross.gemagame.code;

import java.io.File;

import daboross.code.engine.FileHandler;
import daboross.code.engine.ImageHandler;
import daboross.code.engine.OverlayHandler;

public class LoadingScreen {

	public LoadingScreen(ObjectHandler objectHandler) {
		System.out.println("Loading...");
		if (!objectHandler.isApplet()
				&& !(new File("GemaGameLevels/level.txt")).exists()) {
			try {
				FileHandler.WriteFile("GemaGameLevels/", "level.txt",
						FileHandler.ReadInternalFile("levels/level.txt",
								objectHandler.getMainClass()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		new ImageHandler(objectHandler);
		Menu menu = new Menu(objectHandler);
		new LevelLoader(objectHandler);
		new OverlayHandler(objectHandler);
		Thread menuThread = new Thread(menu);
		objectHandler.setMenuThread(menuThread);
		menuThread.start();
		if (objectHandler.getjFrame() != null) {
			objectHandler.getjFrame().setFocusable(true);
			objectHandler.getjFrame().setVisible(true);
			objectHandler.getjFrame().setSize(objectHandler.getScreenWidth(),
					objectHandler.getScreenHeight());
			objectHandler.setFocused(true);
		}
		System.out.println("Done Loading");
	}
}

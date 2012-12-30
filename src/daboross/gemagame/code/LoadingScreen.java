package daboross.gemagame.code;

import java.io.File;
import java.util.ArrayList;

import daboross.code.engine.FileHandler;
import daboross.code.engine.ImageHandler;
import daboross.code.engine.OverlayHandler;

public class LoadingScreen {

	public LoadingScreen(ObjectHandler objectHandler) {
		boolean debug = objectHandler.isDebug();
		if (debug) {
			System.out.println("Loading...");
		}
		if (!(new File("GemaGameLevels/level.txt")).exists()) {
			ArrayList<String> lines = FileHandler
					.ReadInternalFile("/daboross/gemagame/data/levels/level.txt");
			if (lines != null) {
				FileHandler.WriteFile("GemaGameLevels/", "level.txt", lines);
			}

		}
		if (debug) {
			System.out.println("FilePath: "
					+ (new File("GemaGameLevels/level.txt")).getAbsolutePath());
		}
		ImageHandler ih = new ImageHandler(objectHandler);
		Menu menu = new Menu(objectHandler);
		new LevelLoader(objectHandler);
		new OverlayHandler(objectHandler);
		Thread menuThread = new Thread(menu);
		objectHandler.setMenuThread(menuThread);
		menuThread.start();
		Thread iHThread = new Thread(ih);
		iHThread.start();
		if (objectHandler.getjFrame() != null) {
			objectHandler.getjFrame().setFocusable(true);
			objectHandler.getjFrame().setVisible(true);
			objectHandler.getjFrame().setSize(objectHandler.getScreenWidth(),
					objectHandler.getScreenHeight());
			objectHandler.setFocused(true);
		}
		if (debug) {
			System.out.println("Done Loading");
		}
	}
}

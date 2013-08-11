package net.daboross.games.gemagame.code;

import java.io.File;
import java.util.List;
import net.daboross.gameengine.FileHandler;
import net.daboross.gameengine.graphics.ImageHandler;
import net.daboross.gameengine.graphics.OverlayHandler;

public class LoadingScreen {

    public LoadingScreen(ObjectHandler objectHandler) {
        boolean debug = objectHandler.isDebug();
        if (debug) {
            System.out.println("Loading...");
        }
        if (!(new File("GemaGameLevels/level.txt")).exists()) {
            List<String> lines = FileHandler.readInternalFile("/daboross/gemagame/data/levels/level.txt");
            if (lines != null) {
                FileHandler.writeFile(new File(new File("GemaGameLevels"), "level.txt"), lines);
            }

        }
        if (debug) {
            System.out.println("FilePath: "
                    + (new File("GemaGameLevels/level.txt")).getAbsolutePath());
        }
        ImageHandler ih = new ImageHandler(objectHandler.getScreenWidth(), objectHandler.getScreenHeight(), objectHandler.getjFrame(), "net/daboross/games/gemagame/data");
        objectHandler.setImageHandler(ih);
        Menu menu = new Menu(objectHandler);
        new LevelLoader(objectHandler);
        objectHandler.setOverlayHandler(new OverlayHandler(objectHandler.getjFrame(), ih));
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

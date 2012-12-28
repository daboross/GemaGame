package daboross.gemagame.code;

import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JFrame;

import daboross.gemagame.code.engine.ImageHandler;

/**
 * This class is basically a bundle that holds a bunch of other class variables.
 * So that any class that has access to this class will have access to all of
 * the different classes that make up this applet.
 * 
 * @author daboross
 * 
 */
public class ObjectHandler implements Paintable, Runnable {
	private BackgroundHandler backgroundHandler;
	private Character character;
	private boolean isApplet, isFocused;
	private JFrame jFrame;
	private LevelCreator levelCreator;
	private LevelLoader levelLoader;
	private LoadingScreen loadingScreen;
	private MainClass mainClass;
	private Thread mainThread, runLevelThread, levelFileWriterThread,
			menuThread, loadingScreenThread, levelCreatorThread;
	private Menu menu;
	private OverlayHandler overlayHandler;
	private PlatformHandler platformHandler;
	private RunLevel runLevel;
	private ImageHandler imageHandler;
	private final int screenHeight = 480;
	private final int screenWidth = 640;
	private JApplet applet;

	public ObjectHandler() {
	}

	/**
	 * @return the backgroundHandler
	 */
	public BackgroundHandler getBackgroundHandler() {
		return backgroundHandler;
	}

	/**
	 * @param backgroundHandler
	 *            the backgroundHandler to set
	 */
	public void setBackgroundHandler(BackgroundHandler backgroundHandler) {
		this.backgroundHandler = backgroundHandler;
	}

	/**
	 * @return the character
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 * @param character
	 *            the character to set
	 */
	public void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 * @return the isApplet
	 */
	public boolean isApplet() {
		return isApplet;
	}

	/**
	 * @param isApplet
	 *            the isApplet to set
	 */
	public void setApplet(boolean isApplet) {
		this.isApplet = isApplet;
	}

	/**
	 * @return the isFocused
	 */
	public boolean isFocused() {
		return isFocused;
	}

	/**
	 * @param isFocused
	 *            the isFocused to set
	 */
	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
	}

	/**
	 * @return the jFrame
	 */
	public JFrame getjFrame() {
		return jFrame;
	}

	/**
	 * @param jFrame
	 *            the jFrame to set
	 */
	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	/**
	 * @return the levelCreator
	 */
	public LevelCreator getLevelCreator() {
		return levelCreator;
	}

	/**
	 * @param levelCreator
	 *            the levelCreator to set
	 */
	public void setLevelCreator(LevelCreator levelCreator) {
		this.levelCreator = levelCreator;
	}

	/**
	 * @return the levelLoader
	 */
	public LevelLoader getLevelLoader() {
		return levelLoader;
	}

	/**
	 * @param levelLoader
	 *            the levelLoader to set
	 */
	public void setLevelLoader(LevelLoader levelLoader) {
		this.levelLoader = levelLoader;
	}

	/**
	 * @return the loadingScreen
	 */
	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	/**
	 * @param loadingScreen
	 *            the loadingScreen to set
	 */
	public void setLoadingScreen(LoadingScreen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	/**
	 * @return the mainClass
	 */
	public MainClass getMainClass() {
		return mainClass;
	}

	/**
	 * @param mainClass
	 *            the mainClass to set
	 */
	public void setMainClass(MainClass mainClass) {
		this.mainClass = mainClass;
	}

	/**
	 * @return the mainThread
	 */
	public Thread getMainThread() {
		return mainThread;
	}

	/**
	 * @param mainThread
	 *            the mainThread to set
	 */
	public void setMainThread(Thread mainThread) {
		this.mainThread = mainThread;
	}

	/**
	 * @return the runLevelThread
	 */
	public Thread getRunLevelThread() {
		return runLevelThread;
	}

	/**
	 * @param runLevelThread
	 *            the runLevelThread to set
	 */
	public void setRunLevelThread(Thread runLevelThread) {
		this.runLevelThread = runLevelThread;
	}

	/**
	 * @return the levelFileWriterThread
	 */
	public Thread getLevelFileWriterThread() {
		return levelFileWriterThread;
	}

	/**
	 * @param levelFileWriterThread
	 *            the levelFileWriterThread to set
	 */
	public void setLevelFileWriterThread(Thread levelFileWriterThread) {
		this.levelFileWriterThread = levelFileWriterThread;
	}

	/**
	 * @return the menuThread
	 */
	public Thread getMenuThread() {
		return menuThread;
	}

	/**
	 * @param menuThread
	 *            the menuThread to set
	 */
	public void setMenuThread(Thread menuThread) {
		this.menuThread = menuThread;
	}

	/**
	 * @return the loadingScreenThread
	 */
	public Thread getLoadingScreenThread() {
		return loadingScreenThread;
	}

	/**
	 * @param loadingScreenThread
	 *            the loadingScreenThread to set
	 */
	public void setLoadingScreenThread(Thread loadingScreenThread) {
		this.loadingScreenThread = loadingScreenThread;
	}

	/**
	 * @return the levelCreatorThread
	 */
	public Thread getLevelCreatorThread() {
		return levelCreatorThread;
	}

	/**
	 * @param levelCreatorThread
	 *            the levelCreatorThread to set
	 */
	public void setLevelCreatorThread(Thread levelCreatorThread) {
		this.levelCreatorThread = levelCreatorThread;
	}

	/**
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}

	/**
	 * @param menu
	 *            the menu to set
	 */
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the overlayHandler
	 */
	public OverlayHandler getOverlayHandler() {
		return overlayHandler;
	}

	/**
	 * @param overlayHandler
	 *            the overlayHandler to set
	 */
	public void setOverlayHandler(OverlayHandler overlayHandler) {
		this.overlayHandler = overlayHandler;
	}

	/**
	 * @return the platformHandler
	 */
	public PlatformHandler getPlatformHandler() {
		return platformHandler;
	}

	/**
	 * @param platformHandler
	 *            the platformHandler to set
	 */
	public void setPlatformHandler(PlatformHandler platformHandler) {
		this.platformHandler = platformHandler;
	}

	/**
	 * @return the runLevel
	 */
	public RunLevel getRunLevel() {
		return runLevel;
	}

	/**
	 * @param runLevel
	 *            the runLevel to set
	 */
	public void setRunLevel(RunLevel runLevel) {
		this.runLevel = runLevel;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics g) {

	}

	/**
	 * @return the imageHandler
	 */
	public ImageHandler getImageHandler() {
		return imageHandler;
	}

	/**
	 * @param imageHandler
	 *            the imageHandler to set
	 */
	public void setImageHandler(ImageHandler imageHandler) {
		this.imageHandler = imageHandler;
	}

	/**
	 * @return the applet
	 */
	public JApplet getApplet() {
		return applet;
	}

	/**
	 * @param applet
	 *            the applet to set
	 */
	public void setApplet(JApplet applet) {
		this.applet = applet;
	}
}

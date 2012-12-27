package daboross.gemagame.code;

import javax.swing.JFrame;

/**
 * This class is basically a bundle that holds a bunch of other class variables.
 * So that any class that has access to this class will have access to all of
 * the different classes that make up this applet.
 * 
 * @author daboross
 * 
 */
public class ObjectHandler {
	private final int screenWidth = 640;
	private final int screenHeight = 480;
	private boolean isApplet;
	private JFrame jFrame;
	private Thread runLevelThread, levelFileWriterThread, menuThread,
			loadingScreenThread, levelCreatorThread;
	private MainClass mainClass;
	private RunLevel runLevel;
	private PlatformHandler platformHandler;
	private Character character;
	private BackgroundHandler backgroundHandler;
	private LevelFileWriter levelFileWriter;
	private Menu menu;
	private LevelLoader levelLoader;
	private FileLoader fileLoader;
	private LoadingScreen loadingScreen;
	private LevelCreator levelCreator;
	private FileHandler fileHandler;

	public ObjectHandler() {
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
	 * @return the levelFileWriter
	 */
	public LevelFileWriter getLevelFileWriter() {
		return levelFileWriter;
	}

	/**
	 * @param levelFileWriter
	 *            the levelFileWriter to set
	 */
	public void setLevelFileWriter(LevelFileWriter levelFileWriter) {
		this.levelFileWriter = levelFileWriter;
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
	 * @return the fileLoader
	 */
	public FileLoader getFileLoader() {
		return fileLoader;
	}

	/**
	 * @param fileLoader
	 *            the fileLoader to set
	 */
	public void setFileLoader(FileLoader fileLoader) {
		this.fileLoader = fileLoader;
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
	 * @return the fileHandler
	 */
	public FileHandler getFileHandler() {
		return fileHandler;
	}

	/**
	 * @param fileHandler
	 *            the fileHandler to set
	 */
	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}
}

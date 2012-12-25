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
public class ClassHandler {
	public final int screenWidth = 640;
	public final int screenHeight =480;
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

	public ClassHandler() {
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

	public LevelFileWriter getLevelFileWriter() {
		return levelFileWriter;
	}

	public void setLevelFileWriter(LevelFileWriter levelFileWriter) {
		this.levelFileWriter = levelFileWriter;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public LevelLoader getLevelLoader() {
		return levelLoader;
	}

	public void setLevelLoader(LevelLoader levelLoader) {
		this.levelLoader = levelLoader;
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
	 * @return the levelWriterThread
	 */
	public Thread getLevelFileWriterThread() {
		return levelFileWriterThread;
	}

	/**
	 * @param levelWriterThread
	 *            the levelWriterThread to set
	 */
	public void setLevelFileWriterThread(Thread levelWriterThread) {
		this.levelFileWriterThread = levelWriterThread;
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

	public boolean isApplet() {
		return isApplet;
	}

	public void setApplet(boolean isApplet) {
		this.isApplet = isApplet;
	}

	public JFrame getjFrame() {
		return jFrame;
	}

	public void setjFrame(JFrame jFrame) {
		this.jFrame = jFrame;
	}

	public FileLoader getFileLoader() {
		return fileLoader;
	}

	public void setFileLoader(FileLoader fileLoader) {
		this.fileLoader = fileLoader;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	public void setLoadingScreen(LoadingScreen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	public Thread getLoadingScreenThread() {
		return loadingScreenThread;
	}

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
}

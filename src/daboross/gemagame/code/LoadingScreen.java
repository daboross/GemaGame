package daboross.gemagame.code;

public class LoadingScreen implements Runnable {

	private ClassHandler classHandler;

	public LoadingScreen(ClassHandler classHandler) {
		classHandler.setLoadingScreen(this);
		this.classHandler = classHandler;
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {
		System.out.println("Loading...");
		classHandler.setLoadingScreen(this);
		RunLevel runLevel = new RunLevel(classHandler);
		Menu menu = new Menu(classHandler);
		FileLoader fileLoader = new FileLoader(classHandler);
		LevelFileWriter levelFileWriter = new LevelFileWriter(classHandler);
		LevelLoader levelLoader = new LevelLoader(classHandler);

		Thread levelFileWriterThread = new Thread(levelFileWriter);
		Thread menuThread = new Thread(menu);
		Thread runLevelThread = new Thread(runLevel);

		classHandler.setMenuThread(menuThread);
		classHandler.setRunLevelThread(runLevelThread);
		classHandler.setLevelWriterThread(levelFileWriterThread);

		System.out.println("Done Loading");
		menuThread.start();
	}
}

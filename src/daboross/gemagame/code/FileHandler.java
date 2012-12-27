package daboross.gemagame.code;

public class FileHandler {
	private ObjectHandler classhandler;

	public FileHandler(ObjectHandler classHandler) {
		this.classhandler = classHandler;
		this.classhandler.setFileHandler(this);
	}

}

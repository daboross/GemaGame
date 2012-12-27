package daboross.gemagame.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LevelFileWriter {
	private ObjectHandler classHandler;

	public LevelFileWriter(ObjectHandler classHandler) {
		this.classHandler = classHandler;
		classHandler.setLevelFileWriter(this);
	}

	public void writeFile(ArrayList<String> linesToWrite) {
		BufferedWriter writer = null;
		File file = null;
		try {
			file = new File("level.txt");
			System.out.println("n");
			FileWriter fileWriter = new FileWriter(file);
			System.out.println("m");
			writer = new BufferedWriter(fileWriter);
			System.out.println("b");
			if (linesToWrite.size() > 0) {
				for (String lineToWrite : linesToWrite) {
					System.out.println("asd");
					writer.write(lineToWrite + "\n");
					if (lineToWrite != null) {
						System.out.println("Writing Line: " + lineToWrite);
					}
				}
			}
		} catch (IOException e) {
		}
		if (classHandler.getLevelLoader() == null) {
			new LevelLoader(classHandler);
		}
		classHandler.getLevelLoader().loadFile(file);
	}
}

/**
 * 
 */
package daboross.gemagame.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author daboross
 * 
 */
public class FileLoader {
	ClassHandler classHandler;

	public FileLoader(ClassHandler classHandler) {
		classHandler.setFileLoader(this);
		this.classHandler = classHandler;
	}

	public ArrayList<String> loadFile(File file) {
		ArrayList<String> lineList = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferInputReader = new BufferedReader(fileReader);
			while (true) {
				String currentLine = bufferInputReader.readLine();
				if (currentLine == null) {
					bufferInputReader.close();
					break;
				} else {
					lineList.add(currentLine);
				}
			}
			System.out.println("Loaded File: " + file);
		} catch (Exception e) {
			System.out.println("Failed to Load File: " + file);
		}
		return lineList;
	}

	public ArrayList<String> loadFile(String filePath) {
		ArrayList<String> lineList = new ArrayList<String>();
		try {
			InputStream inputStream;
			if (classHandler.getjFrame() == null) {
				inputStream = classHandler.getMainClass().getClass()
						.getResourceAsStream(filePath);
			} else {
				inputStream = classHandler
						.getjFrame()
						.getClass()
						.getResourceAsStream(
								"/daboross/gemagame/code/" + filePath);
			}
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream);
			BufferedReader bufferInputReader = new BufferedReader(
					inputStreamReader);
			while (true) {
				String currentLine = bufferInputReader.readLine();
				// no more lines to read
				if (currentLine == null) {
					bufferInputReader.close();
					break;
				} else {
					lineList.add(currentLine);
				}
			}
			System.out.println("Loaded File: " + filePath);
		} catch (Exception e) {
			System.out.println("Failed to Load File: " + filePath);
		}
		return lineList;
	}

}

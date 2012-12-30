package daboross.code.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/** This class contains various helpful functions */
public final class FileHandler {

	/**
	 * Writes a file with text lines to filePath/fileName
	 * 
	 * @param fileName
	 *            this is the name of the file to write
	 * @param filePath
	 *            this is where to put the file
	 * @param lines
	 *            this is the text to put in the file
	 * @return True if successful, false otherwise
	 */
	public static boolean WriteFile(String filePath, String fileName,
			ArrayList<String> lines) {
		if (lines == null || fileName == null || filePath == null) {
			return false;
		}
		try {
			System.out.println("Making Directory:"
					+ new File(filePath).mkdirs());
			System.out.println("Making File:"
					+ new File(filePath + fileName).createNewFile());
			System.out.println("Writing File: " + filePath + fileName);
			BufferedWriter bf = new BufferedWriter(new FileWriter(filePath
					+ fileName));
			for (int i = 0; i < lines.size(); i++) {
				bf.write(lines.get(i));
				bf.newLine();
			}
			bf.close();
		} catch (Exception e) {
			return false;
		}
		System.out.println("Finished Writing File: " + filePath + fileName);
		return true;
	}

	/**
	 * Reads a file into an array list of strings
	 * 
	 * @param filePath
	 *            this is the path and the name of the file
	 * @return The text in the file, or null if it doesn't exist
	 */
	public static ArrayList<String> ReadFile(String filePath) {
		System.out.println("Reading File: " + filePath);
		ArrayList<String> lines = null;
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(fr);
			lines = new ArrayList<String>();
			while (true) {
				String line = bf.readLine();
				if (line == null) {
					break;
				}
				lines.add(line);
			}
			bf.close();
		} catch (Exception e) {
			System.out.println("Failed to read file: " + filePath);
		}
		System.out.println("Finished Reading File: " + filePath);

		return lines;
	}

	/**
	 * This reads a file from within the .jar to an ArrayList of strings
	 * 
	 * @param filePath
	 *            this is the location of the file within the jar archive.
	 *            starts with a /
	 * @return an ArrayList of strings that is the text of the file
	 */
	public static ArrayList<String> ReadInternalFile(String filePath) {
		System.out.println("Reading File: " + filePath);
		ArrayList<String> lines = null;
		InputStream is = FileHandler.class.getResourceAsStream(filePath);
		if (is != null) {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader bf = new BufferedReader(isr);
				lines = new ArrayList<String>();
				while (true) {
					String line = bf.readLine();
					if (line == null) {
						break;
					}
					lines.add(line);
				}
				bf.close();
			} catch (Exception e) {
				System.out.println("Failed to read file: " + filePath);
			}
		} else {
			System.out.println("Input Stream Reader Get Failed for File: "
					+ filePath);
		}
		System.out.println("Finished Reading File: " + filePath);
		return lines;
	}
}

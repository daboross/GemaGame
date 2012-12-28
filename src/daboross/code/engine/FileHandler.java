package daboross.code.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class FileHandler {

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

	public static ArrayList<String> ReadInternalFile(String filePath){
		System.out.println("Reading File: " + filePath);
		ArrayList<String> lines = null;
		InputStream is = Class.class.getResourceAsStream(filePath);
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

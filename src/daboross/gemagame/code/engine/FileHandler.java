package daboross.gemagame.code.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import daboross.gemagame.code.MainClass;

public final class FileHandler {

	public static void WriteFile(String filePath, String fileName,
			ArrayList<String> lines) throws Exception {
		System.out.println("Making Directory:" + new File(filePath).mkdirs());
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
		System.out.println("Finished Writing File: " + filePath + fileName);
	}

	public static ArrayList<String> ReadFile(String filePath) throws Exception {
		System.out.println("Reading File: " + filePath);
		ArrayList<String> lines = new ArrayList<String>();

		BufferedReader bf = new BufferedReader(new FileReader(filePath));
		while (true) {
			String line = bf.readLine();
			if (line == null) {
				break;
			}
			lines.add(line);
		}

		bf.close();
		System.out.println("Finished Reading File: " + filePath);

		return lines;
	}

	public static ArrayList<String> ReadInternalFile(String filePath,
			MainClass mainClass) throws Exception {
		System.out.println("Reading File: " + filePath);
		ArrayList<String> lines = new ArrayList<String>();
		InputStreamReader isr = new InputStreamReader(mainClass.getClass()
				.getResourceAsStream(filePath));
		BufferedReader bf = new BufferedReader(isr);
		while (true) {
			String line = bf.readLine();
			if (line == null) {
				break;
			}
			lines.add(line);
		}
		bf.close();
		System.out.println("Finished Reading File: " + filePath);
		return lines;
	}
}

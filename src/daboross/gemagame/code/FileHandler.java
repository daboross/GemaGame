package daboross.gemagame.code;

import java.io.*;
import java.util.ArrayList;

public final class FileHandler {

	public static void WriteFile(String filePath, ArrayList<String> lines)
			throws Exception {
		System.out.println("Writing File: " + filePath);
		BufferedWriter bf = new BufferedWriter(new FileWriter(filePath));
		for (int i = 0; i < lines.size(); i++) {
			bf.write(lines.get(i));
			bf.newLine();
		}
		bf.close();
		System.out.println("Finished Writing File: " + filePath);
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

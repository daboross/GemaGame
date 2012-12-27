package daboross.gemagame.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileHandler {

	public static void WriteFile(String filePath, ArrayList<String> lines)
			throws Exception {
		BufferedWriter bf = new BufferedWriter(new FileWriter(filePath));
		for (int i = 0; i < lines.size(); i++) {
			bf.write(lines.get(i) + "/n");
		}
		bf.close();
	}

	public static ArrayList<String> ReadFile(String filePath) throws Exception {
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
		return lines;
	}

}

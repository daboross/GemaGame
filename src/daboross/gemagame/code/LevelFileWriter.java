package daboross.gemagame.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelFileWriter implements Runnable {
	private static ArrayList<String> linesToWrite;
	private static File file;

	public LevelFileWriter() {
		linesToWrite = new ArrayList<String>();
		file = new File("level.txt");
	}

	@Override
	public void run() {
		while (true) {
			BufferedReader bufferReader = new BufferedReader(
					new InputStreamReader(System.in));
			String input;
			while (true) {
				System.out.print("Next Line ");
				try {
					input = bufferReader.readLine();
					System.out.println("Input Received:" + input + ".");
					if (input.equals("refresh") || input.equals("")) {
						break;
					} else if (input.equals("list")) {
						for (String line : linesToWrite) {
							System.out.println(line);
						}
					} else if (input.equals("refresh")) {
						break;
					} else {
						linesToWrite.add(input);
					}
				} catch (IOException e) {
					System.out.println("Error!");
				}
			}
			writeFile(linesToWrite);
			LevelLoader.loadFile(file);
		}
	}

	public static void writeFile(ArrayList<String> linesToWrite) {
		BufferedWriter writer = null;
		try {
			FileWriter fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
			for (String lineToWrite : linesToWrite) {
				writer.write(lineToWrite + "\n");
				System.out.println("Writing Line: " + lineToWrite);
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
}

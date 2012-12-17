package daboross.gemagame.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LevelFileWriter {

	public static void main(String[] args) {
		ArrayList<String> linesToWrite = new ArrayList<String>();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
				System.in));
		String input;
		while (true) {
			System.out.print("Next Line ");
			try {
				input = bufferReader.readLine();
				System.out.println("Input Received:" + input + ".");
				if (input.equals("")) {
					System.out.println("Breaking");
					break;
				} else {
					linesToWrite.add(input);
				}
			} catch (IOException e) {
				System.out.println("Error!");
			}
		}
		writeFile(linesToWrite);
	}

	public static void writeFile(ArrayList<String> linesToWrite) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter("./src/daboross/gemagame/code/levels/level.txt"));
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

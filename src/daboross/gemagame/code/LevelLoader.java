package daboross.gemagame.code;

import java.io.File;
import java.util.ArrayList;

public class LevelLoader {
	private String fileName;
	private ClassHandler classHandler;

	public LevelLoader(ClassHandler classHandler) {
		classHandler.setLevelLoader(this);
		this.classHandler = classHandler;
	}

	public void loadTxt(String fileName) {
		this.fileName = fileName;
		if (classHandler.getFileLoader() == null) {
			classHandler.setFileLoader(new FileLoader(classHandler));
		}
		ArrayList<String> lineList = classHandler.getFileLoader().loadFile(
				fileName);
		ArrayList<Double> platformsToAddXPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddXLength = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYLength = new ArrayList<Double>();
		int numberOfPlatformsToAdd = 0;
		// These four ArrayLists store the platform properties that are to be
		// added at end of function.
		// Variables to be used in the following for loop.
		char currentChar;
		int nextNumberEquals;
		// This variable will keep track of what to do with the next number in
		// the sequence in the line. it basically keeps track of what the next
		// number represents, for instance whether it is the x position or y
		// position of the platform.
		// 0 is x position
		// 1 is y position
		// 2 is x length
		// 3 is y length
		// 4 is quit loop

		// Define variable constants to use, for name simplicity.

		// For loops that go through every character in the line.
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			double[] currentAdditions = new double[4];
			for (int i = 0; i < 4; i++) {
				currentAdditions[i] = 0;
			}
			// This is a double[] storing all the current platform's properties.
			// 0 is x position
			// 1 is y position
			// 2 is x length
			// 3 is y length

			int lineLength = lineList.get(lineNumber).length();
			// Gets the current line's length for later use

			nextNumberEquals = 0;// reset the nextNumberEquals variable.
			// Loop to go through all characters in this line.
			int charNumber = 0;
			String currentSequence = "";
			while (nextNumberEquals < 4) {
				if (charNumber < lineLength) {
					currentChar = lineList.get(lineNumber).charAt(charNumber);
					// This is the current Character.
					// If the current Character is a ;,
					if (java.lang.Character.isDigit(currentChar)
							|| currentChar == '-') {
						currentSequence = (currentSequence + currentChar);
						charNumber++;
					} else if (currentSequence != "") {
						currentAdditions[nextNumberEquals] = Integer
								.valueOf(currentSequence);
						currentSequence = "";
						nextNumberEquals++;
					} else {
						charNumber++;
					}
				} else if (currentSequence != "") {
					currentAdditions[nextNumberEquals] = Integer
							.valueOf(currentSequence);
					currentSequence = "";
					nextNumberEquals++;
				} else {
					System.out.println("Line Ended Abruptly");
					break;
				}
			}// End of current line Loop

			// This checks if the current addition platform has a length and
			// height, and all 4 values have been gone through and added.
			if (currentAdditions[2] < 0) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is negetive. In File: ");

				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > classHandler.getScreenHeight()) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is bigger then screen height. In File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
				numberOfPlatformsToAdd++;
				System.out.println("Loading Plaform: xPos."
						+ currentAdditions[0] + " yPos." + currentAdditions[1]
						+ " xLength." + currentAdditions[2] + " yLength."
						+ currentAdditions[3]);
			} else {
				System.out.print("4 seperated Integers not found in File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			}
			System.out.println("Looping for new line, currentLine:"
					+ lineNumber + " numberOfLines:" + lineList.size());

		}// End of main loop

		for (int i = 0; i < numberOfPlatformsToAdd; i++) {
			// Loops through all the platform properties that have been gotten
			// from the file, and adds each of them to the platform Handler
			// stored in Main Class
			classHandler.getPlatformHandler().addPlatForm(
					platformsToAddXPos.get(i), platformsToAddYPos.get(i),
					platformsToAddXLength.get(i), platformsToAddYLength.get(i));
		}
		System.out.println("Loaded " + numberOfPlatformsToAdd
				+ " platforms from " + fileName + ".");
	}// End of Function

	public void reloadTxt() {
		ArrayList<String> lineList = classHandler.getFileLoader().loadFile(
				fileName);
		ArrayList<Double> platformsToAddXPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddXLength = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYLength = new ArrayList<Double>();
		int numberOfPlatformsToAdd = 0;
		// These four ArrayLists store the platform properties that are to be
		// added at end of function.

		// Variables to be used in the following for loop.
		char currentChar;
		int nextNumberEquals;
		// This variable will keep track of what to do with the next number in
		// the sequence in the line. it basically keeps track of what the next
		// number represents, for instance whether it is the x position or y
		// position of the platform.
		// 0 is x position
		// 1 is y position
		// 2 is x length
		// 3 is y length
		// 4 is quit loop

		// Define variable constants to use, for name simplicity.

		// For loops that go through every character in the line.
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			double[] currentAdditions = new double[4];
			for (int i = 0; i < 4; i++) {
				currentAdditions[i] = 0;
			}
			// This is a double[] storing all the current platform's properties.
			// 0 is x position
			// 1 is y position
			// 2 is x length
			// 3 is y length

			int lineLength = lineList.get(lineNumber).length();
			// Gets the current line's length for later use

			nextNumberEquals = 0;// reset the nextNumberEquals variable.
			// Loop to go through all characters in this line.
			int charNumber = 0;
			String currentSequence = "";
			while (nextNumberEquals < 4) {
				if (charNumber < lineLength) {
					currentChar = lineList.get(lineNumber).charAt(charNumber);
					// This is the current Character.
					// If the current Character is a ;,
					if (java.lang.Character.isDigit(currentChar)
							|| currentChar == '-') {
						currentSequence = (currentSequence + currentChar);
						charNumber++;
					} else if (currentSequence != "") {
						currentAdditions[nextNumberEquals] = Integer
								.valueOf(currentSequence);
						currentSequence = "";
						nextNumberEquals++;
					} else {
						charNumber++;
					}
				} else if (currentSequence != "") {
					currentAdditions[nextNumberEquals] = Integer
							.valueOf(currentSequence);
					currentSequence = "";
					nextNumberEquals++;
				} else {
					System.out.println("Line Ended Abruptly");
					break;
				}
			}// End of current line Loop

			// This checks if the current addition platform has a length and
			// height, and all 4 values have been gone through and added.
			if (currentAdditions[2] < 0) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is negetive. In File: ");

				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > classHandler.getScreenHeight()) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is bigger then screen height. In File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
				numberOfPlatformsToAdd++;
				System.out.println("Loading Plaform: xPos."
						+ currentAdditions[0] + " yPos." + currentAdditions[1]
						+ " xLength." + currentAdditions[2] + " yLength."
						+ currentAdditions[3]);
			} else {
				System.out.print("4 seperated Integers not found in File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			}
			System.out.println("Looping for new line, currentLine:"
					+ lineNumber + " numberOfLines:" + lineList.size());

		}// End of main loop

		classHandler.getPlatformHandler().clearPlatformList();
		for (int i = 0; i < numberOfPlatformsToAdd; i++) {
			// Loops through all the platform properties that have been gotten
			// from the file, and adds each of them to the platform Handler
			// stored in Main Class
			classHandler.getPlatformHandler().addPlatForm(
					platformsToAddXPos.get(i), platformsToAddYPos.get(i),
					platformsToAddXLength.get(i), platformsToAddYLength.get(i));
		}
		System.out.println("Loaded " + numberOfPlatformsToAdd
				+ " platforms from " + fileName + ".");
	}// End of Function

	public void loadFile(File file) {
		ArrayList<String> lineList = classHandler.getFileLoader()
				.loadFile(file);
		ArrayList<Double> platformsToAddXPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYPos = new ArrayList<Double>();
		ArrayList<Double> platformsToAddXLength = new ArrayList<Double>();
		ArrayList<Double> platformsToAddYLength = new ArrayList<Double>();
		int numberOfPlatformsToAdd = 0;
		// These four ArrayLists store the platform properties that are to be
		// added at end of function.
		// Variables to be used in the following for loop.
		char currentChar;
		int nextNumberEquals;
		// This variable will keep track of what to do with the next number in
		// the sequence in the line. it basically keeps track of what the next
		// number represents, for instance whether it is the x position or y
		// position of the platform.
		// 0 is x position
		// 1 is y position
		// 2 is x length
		// 3 is y length
		// 4 is quit loop

		// Define variable constants to use, for name simplicity.

		// For loops that go through every character in the line.
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			double[] currentAdditions = new double[4];
			for (int i = 0; i < 4; i++) {
				currentAdditions[i] = 0;
			}
			// This is a double[] storing all the current platform's properties.
			// 0 is x position
			// 1 is y position
			// 2 is x length
			// 3 is y length

			int lineLength = lineList.get(lineNumber).length();
			// Gets the current line's length for later use

			nextNumberEquals = 0;// reset the nextNumberEquals variable.
			// Loop to go through all characters in this line.
			int charNumber = 0;
			String currentSequence = "";
			while (nextNumberEquals < 4) {
				if (charNumber < lineLength) {
					currentChar = lineList.get(lineNumber).charAt(charNumber);
					// This is the current Character.
					// If the current Character is a ;,
					if (java.lang.Character.isDigit(currentChar)
							|| currentChar == '-') {
						currentSequence = (currentSequence + currentChar);
						charNumber++;
					} else if (currentSequence != "") {
						currentAdditions[nextNumberEquals] = Integer
								.valueOf(currentSequence);
						currentSequence = "";
						nextNumberEquals++;
					} else {
						charNumber++;
					}
				} else if (currentSequence != "") {
					currentAdditions[nextNumberEquals] = Integer
							.valueOf(currentSequence);
					currentSequence = "";
					nextNumberEquals++;
				} else {
					System.out.println("Line Ended Abruptly");
					break;
				}
			}// End of current line Loop

			// This checks if the current addition platform has a length and
			// height, and all 4 values have been gone through and added.
			if (currentAdditions[2] < 0) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is negetive. In File: ");

				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > classHandler.getScreenHeight()) {
				System.out.print("Warning! Platform not loaded because yPos"
						+ "Value is bigger then screen height. In File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			} else if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
				numberOfPlatformsToAdd++;
				System.out.println("Loading Plaform: xPos."
						+ currentAdditions[0] + " yPos." + currentAdditions[1]
						+ " xLength." + currentAdditions[2] + " yLength."
						+ currentAdditions[3]);
			} else {
				System.out.print("4 seperated Integers not found in File: ");
				System.out.println(fileName + ", Line Number: "
						+ (lineNumber + 1) + ", Invalid Content: "
						+ lineList.get(lineNumber));
			}
			System.out.println("Looping for new line, currentLine:"
					+ lineNumber + " numberOfLines:" + lineList.size());

		}// End of main loop

		classHandler.getPlatformHandler().clearPlatformList();
		for (int i = 0; i < numberOfPlatformsToAdd; i++) {
			// Loops through all the platform properties that have been gotten
			// from the file, and adds each of them to the platform Handler
			// stored in Main Class
			classHandler.getPlatformHandler().addPlatForm(
					platformsToAddXPos.get(i), platformsToAddYPos.get(i),
					platformsToAddXLength.get(i), platformsToAddYLength.get(i));
		}
		System.out.println("Loaded " + numberOfPlatformsToAdd
				+ " platforms from " + fileName + ".");
	}// End of Function
}// End Of Class

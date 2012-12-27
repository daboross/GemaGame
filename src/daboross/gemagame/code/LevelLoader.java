package daboross.gemagame.code;

import java.util.ArrayList;

public class LevelLoader {
	private ObjectHandler objectHandler;

	public LevelLoader(ObjectHandler objectHandler) {
		objectHandler.setLevelLoader(this);
		this.objectHandler = objectHandler;
	}

	public void load(ArrayList<String> lineList) {
		ArrayList<Integer> platformsToAddXPos = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddYPos = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddXLength = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddYLength = new ArrayList<Integer>();
		int numberOfPlatformsToAdd = 0;
		/*
		 * These four ArrayLists store the platform properties that are to be
		 * added at end of function. Variables to be used in the following for
		 * loop.
		 */
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

		/* For loops that go through every character in the line. */
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			int[] currentAdditions = new int[4];
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
			if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
				numberOfPlatformsToAdd++;
			} else {
				System.out
						.printf("4 seperated Integers not found /n, Line Number: "
								+ (lineNumber + 1)
								+ ", Invalid Content: "
								+ lineList.get(lineNumber) + "/n");
			}

		}// End of main loop

		for (int i = 0; i < numberOfPlatformsToAdd; i++) {
			/*
			 * Loops through all the platform properties that have been gotten
			 * from the file, and adds each of them to the platform Handler
			 * stored in Main Class
			 */
			objectHandler.getPlatformHandler().addPlatForm(
					platformsToAddXPos.get(i), platformsToAddYPos.get(i),
					platformsToAddXLength.get(i), platformsToAddYLength.get(i));
		}
		System.out.println("Loaded " + numberOfPlatformsToAdd + ".");
	}

	public PlatformList loadToList(ArrayList<String> lineList) {
		ArrayList<Integer> platformsToAddXPos = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddYPos = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddXLength = new ArrayList<Integer>();
		ArrayList<Integer> platformsToAddYLength = new ArrayList<Integer>();
		/*
		 * These four ArrayLists store the platform properties that are to be
		 * added at end of function. Variables to be used in the following for
		 * loop.
		 */
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

		/* For loops that go through every character in the line. */
		for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

			int[] currentAdditions = new int[4];
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
			if (currentAdditions[2] > 0 && currentAdditions[3] > 0
					&& nextNumberEquals == 4) {
				// If it does have a width and height, then add it to the
				// Platforms To Add ArrayLists
				platformsToAddXPos.add(currentAdditions[0]);
				platformsToAddYPos.add(currentAdditions[1]);
				platformsToAddXLength.add(currentAdditions[2]);
				platformsToAddYLength.add(currentAdditions[3]);
			} else {
				System.out
						.printf("4 seperated Integers not found /n, Line Number: "
								+ (lineNumber + 1)
								+ ", Invalid Content: "
								+ lineList.get(lineNumber) + "/n");
			}

		}// End of main loop

		PlatformList pl = new PlatformList();
		pl.xPosList = platformsToAddXPos;
		pl.yPosList = platformsToAddYPos;
		pl.xLengthList = platformsToAddXLength;
		pl.yLengthList = platformsToAddYLength;
		return pl;
	}
}
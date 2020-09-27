package edu.uga.cs1302.quiz;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Question {

	private ArrayList<String> choice = new ArrayList<String>();
	private String correctAnswer;
	private int correctIndex;

	//question constructor that accepts country as a parameter
	public Question(Country country) {
		//creates an array of possible continents
		ArrayList<String> continent = new ArrayList<String>();

		//gets the correct answer based on the country that is in the question
		correctAnswer = country.getContinent();
		//fills in the choice array with values to be replaced later on
		choice.add("");
		choice.add("");
		choice.add("");

		//adds each of the possible continent choices into the continent arraylist
		continent.add("Asia");
		continent.add("Africa");
		continent.add("North America");
		continent.add("South America");
		continent.add("Antarctica");
		continent.add("Europe");
		continent.add("Oceania");

		//gets a random index to put the correct answer in and sets it as so
		correctIndex = ThreadLocalRandom.current().nextInt(3);
		choice.set(correctIndex, correctAnswer);


		String duplicate;
		int duplicateIndex;
		//gets a random incorrect index and makes sure that it is not the same as the correct index
		int incorrectIndex = ThreadLocalRandom.current().nextInt(3);
		while (incorrectIndex == correctIndex) {
			incorrectIndex = ThreadLocalRandom.current().nextInt(3);
		}

		duplicateIndex = incorrectIndex;
		//sets the incorrect answer into the incorrect index and makes sure that the incorrect answer is not the same continent as the correct one
		int randomIndex = ThreadLocalRandom.current().nextInt(continent.size());
		String randomContinent = continent.get(randomIndex);
		while (randomContinent.equals(correctAnswer)) {
			randomIndex = ThreadLocalRandom.current().nextInt(continent.size());
			randomContinent = continent.get(randomIndex);
		}
		choice.set(incorrectIndex, randomContinent);

		//gets another incorrect index and makes sure its not the same as the correct index or the last incorrect index
		incorrectIndex = ThreadLocalRandom.current().nextInt(3);
		while (incorrectIndex == correctIndex || incorrectIndex == duplicateIndex) {
			incorrectIndex = ThreadLocalRandom.current().nextInt(3);
		}

		//sets an incorrect answer into the last incorrect index and makes sure it is not same continent as the other incorrect answer or the correct one
		duplicate = randomContinent;
		randomIndex = ThreadLocalRandom.current().nextInt(continent.size());
		randomContinent = continent.get(randomIndex);
		while (randomContinent.equals(duplicate) || randomContinent.equals(correctAnswer)) {
			randomIndex = ThreadLocalRandom.current().nextInt(continent.size());
			randomContinent = continent.get(randomIndex);
		}
		choice.set(incorrectIndex, randomContinent);

	}

	//setter/getter/other methods for other classes to use
	public String getChoice(int index) {
		String getChoice = choice.get(index);
		return getChoice;

	}

	public int getCorrectChoiceIndex() {
		int correctChoiceIndex = correctIndex;
		return correctChoiceIndex;
	}

	public String getCorrectChoice() {
		String correctChoice = correctAnswer;
		return correctChoice;
	}
}

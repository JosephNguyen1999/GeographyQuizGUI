package edu.uga.cs1302.quiz;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Date;

public class Quiz {

	private ArrayList<Question>listOfQuestions = new ArrayList<Question>();
	private ArrayList<Country>listOfCountrys = new ArrayList<Country>();
	private int score;
	private Date date;

	//quiz constructor that makes the quiz and the six questions
	public Quiz() {
		//score is 0 when starting a new quiz
		score = 0;

		//parses through the csv file and gets all the countries and continents
		QuestionCollection quizCountry = new QuestionCollection();
		quizCountry.parseCountrysCSV();

		//makes the six questions with no duplicate questions with a loop
		int counter = 0;
		Country duplicate = null;
		while (counter < 6) {
			Country country = quizCountry.countryAtIndex(ThreadLocalRandom.current().nextInt(195));
			if (counter > 0) {
				while (country == duplicate) {
					country = quizCountry.countryAtIndex(ThreadLocalRandom.current().nextInt(195));
				}
			}
			duplicate = country;
			listOfCountrys.add(country);
			//adds a question to the list of questions
			Question question = new Question(country);
			listOfQuestions.add(question);

			counter++;
		}
	}

	//setter/getter/other methods for other classes to use
	public Question getQuestion(int index) {
		Question question = listOfQuestions.get(index);
		return question;
	}

	public String printQuestion(int index) {
		String question = "On which continent is " + listOfCountrys.get(index).getCountry() + " located?";
		return question;
	}

	public void updateScore() {
		score++;
	}

	public int getScore() {
		return score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}

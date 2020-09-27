package edu.uga.cs1302.quiz;

import java.io.Serializable;

public class QuizResult implements Serializable {

	private final String date;
	private final String score;

	//QuizResult constructor every score (from a completed quiz) has a corresponding date/time
	public QuizResult(String date, String score) {
		this.date = date;
		this.score = score;
	}

	//setter/getter/other methods for other classes to use
	public String getDate() {
		return date;
	}

	public String getScore() {
		return score;
	}



}

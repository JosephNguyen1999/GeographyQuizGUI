package edu.uga.cs1302.quiz;

import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class MainScreenPane extends VBox {

	private final String fileName = "quizzes.dat";

	private Stage helpStage, quizStage, pastStage;
	private Scene quizScene;
	private VBox quizLayout, pastLayout;
	private Button start, pastQuizzes, help, exit, submitButton, quizCloseButton, helpCloseButton, pastCloseButton;
	private RadioButton AnswerChoice1, AnswerChoice2, AnswerChoice3;
	private Quiz quiz;
	private Text question, helpDescription, correctness, pastQuiz;
	private String correctChoice, score, dateString;
	private Date date;
	private File tmpFile = new File(fileName);
	private int correctChoiceIndex, counter;
	private ArrayList<QuizResult> pastResults = new ArrayList<QuizResult>();
	private ArrayList<QuizResult> printedPast = new ArrayList<QuizResult>();

	public MainScreenPane() {

		//if the quizzes.dat file exists to read then it will read it into an arraylist which will be written out later
		if (tmpFile.exists()) {
			try {
				ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
				printedPast = (ArrayList<QuizResult>) inputStream.readObject();
				inputStream.close ();
			}
			catch (Exception e)
			{
				System.out.println ("Error reading file " + fileName + ".");
				System.exit (0);
			}

			//loops through and adds to an arraylist to be written out later
			for (int i = 0 ; i < printedPast.size(); i++)
			{
				score = printedPast.get(i).getScore();
				dateString = printedPast.get(i).getDate();
				pastResults.add(new QuizResult(dateString, score));

			}
		}

		//main screen with a slight description on how to start the quiz and what is the quiz going to be on
		Text description = new Text("Click start to take a quiz with 6 questions about the geography of countries!");
		description.setFont(new Font("Helvetica", 20));

		//buttons on the main screen (start, past quizzes, help, exit)
		start = new Button("Start");
		start.setOnAction(this::processButtonPress);
		pastQuizzes = new Button("Past Quizzes");
		pastQuizzes.setOnAction(this::processButtonPress);
		help = new Button("Help");
		help.setOnAction(this::processButtonPress);
		exit = new Button("Exit");
		exit.setOnAction(this::processButtonPress);

		setSpacing(20);
		getChildren().addAll(description, start, pastQuizzes, help, exit);

	}

	//this is what each button will do if pressed
	public void processButtonPress(ActionEvent event) {
		//if start is pressed then it will start a new quiz of six questions
		if (event.getSource() == start) {
			quiz = new Quiz();
			counter = 0;
			correctness = new Text();
			quizScreen();

		}
		//if past quizzes is pressed then it will show the past quizzes that the user has took
		else if (event.getSource() == pastQuizzes) {
			String holder =  "";
			//if the file exists then it will read the file and show its contents
			if (tmpFile.exists()) {
				try {
					ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName));
					printedPast = (ArrayList<QuizResult>) inputStream.readObject();
					inputStream.close();
				}
				catch (Exception e)
				{
					System.out.println ("Error reading file " + fileName + ".");
					System.exit (0);
				}
				for (int i = 0 ; i < printedPast.size(); i++)
				{
					String pastQuizzesList = ("Date and Time: " + printedPast.get(i).getDate() + "\t" + "Score: " + printedPast.get(i).getScore() + "/6");
					holder += (pastQuizzesList + "\n");
				}
			}
			pastQuiz = new Text(holder);
			pastQuiz.setFont(new Font("Helvetica", 20));

			//close button for this window
			pastCloseButton = new Button("Close");
			pastCloseButton.setOnAction(this::closeButtonHandler);

			//scroll pane for the quiz results content incase it gets too large
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			scrollPane.setPrefViewportWidth(280);
			scrollPane.setStyle("-fx-background: #87CEEB; -fx-border-color: #87CEEB");
			scrollPane.setContent(pastQuiz);

			//vbox layout for this window
			pastLayout = new VBox();
			pastLayout.setSpacing(20);
			pastLayout.getChildren().addAll(scrollPane, pastCloseButton);
			pastLayout.setAlignment(Pos.CENTER);
			pastLayout.setStyle("-fx-background-color: skyblue");
			VBox.setMargin(scrollPane, new Insets( 10, 10, 10, 10 ));

			Scene pastScene = new Scene(pastLayout, 900, 700);

			//sets modality so that it is the only window that can be interacted with when open
			pastStage = new Stage();
			pastStage.setTitle("Past Quizzes");
			pastStage.setScene( pastScene );
			pastStage.initModality( Modality.APPLICATION_MODAL );
			this.pastStage = pastStage;
			pastStage.show();

		}
		//if help is pressed then a more indepth description of what each button does and what the quiz is about will pop up
		else if (event.getSource() == help) {
			//the help description describing the program and quiz
			helpDescription = new Text("This program is for taking a geography quiz."
					+ "\nEach quiz will have 6 questions about a country and its continent"
					+ "\nClick 'Start' to start the quiz"
					+ "\nClick 'Past Quizzes' to get a list of past quizzes"
					+ "\nClick 'Exit' to exit the program");
			helpDescription.setFont(new Font("Helvetica", 20));

			//close button
			helpCloseButton = new Button("Close");
			helpCloseButton.setOnAction(this::closeButtonHandler);

			//vbox layout for this window
			VBox helpLayout = new VBox();
			helpLayout.setSpacing(20);
			helpLayout.getChildren().addAll(helpDescription, helpCloseButton);
			helpLayout.setAlignment(Pos.CENTER);
			helpLayout.setStyle("-fx-background-color: skyblue");

			Scene helpScene = new Scene(helpLayout, 900, 700);

			//sets modality so that when this window is active it is the only one that can be interacted with
			helpStage = new Stage();
			helpStage.setTitle("Help Menu");
			helpStage.setScene(helpScene);
			helpStage.initModality(Modality.APPLICATION_MODAL);
			this.helpStage = helpStage;
			helpStage.show();
		}
		//if exit is pressed then it will exit the JavaFX program and thus ending the program
		else {
			Platform.exit();
		}
	}


	//this will handle the close button and will close out the appropriate stage if pressed
	public void closeButtonHandler(ActionEvent event)
	{
		if (event.getSource() == quizCloseButton) {
			quizStage.close();
		}
		else if (event.getSource() == helpCloseButton){
			helpStage.close();
		}
		else {
			pastStage.close();
		}
	}

	//this will handle the submit button at the bottom of each question and will update the score/set the date when the quiz is finished
	public void submitButtonHandler(ActionEvent event)
	{
		//if it is the last question then the submit button will display a different screen in the same window that shows the date/time and score
		if (counter == 5){
			quizLayout.getChildren().clear();

			date = new Date();
			quiz.setDate(date);
			correctness.setText("Date: " + date + "\nYour score is: " + quiz.getScore() + "/6");
			correctness.setFont(new Font("Helvetica", 20));

			score = String.valueOf(quiz.getScore());
			dateString = date.toString();
			pastResults.add(new QuizResult(dateString, score));

			//writes the arraylist object into the file so that it can be read when past quizzes is pressed
			ObjectOutputStream outputStream = null;
			try
			{
				outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
			}
			catch (IOException e)
			{
				System.out.println ("Error opening output file " + fileName + ".");
				System.exit (0);
			}

			try
			{
				outputStream.writeObject(pastResults);
				outputStream.close ();
			}
			catch (IOException e)
			{
				System.out.println ("Error writing to file " + fileName + ".");
				System.exit (0);
			}

			//close button for the last screen after quiz is completed
			quizCloseButton = new Button("Close");
			quizCloseButton.setOnAction(this::closeButtonHandler);

			quizLayout.getChildren().addAll(correctness, quizCloseButton);
		}
		//when the first answer is selected
		else if (AnswerChoice1.isSelected()) {
			//if this was the correct choice then it will update the score and print out correct with the next question
			if (correctChoiceIndex == 0) {
				quiz.updateScore();
				correctness.setText("Correct!");
			}
			//if it was not the correct choice then it will print an incorrect statement with the next question
			else {
				correctness.setText("Incorrect! Correct Answer is " + correctChoice);
			}
			//updates the quiz screen into the next question
			counter++;
			quizStage.close();
			quizScreen();
		}
		//when the second answer is selected
		else if (AnswerChoice2.isSelected()) {
			//if this was the correct choice then it will update the score and print out correct with the next question
			if (correctChoiceIndex == 1) {
				quiz.updateScore();
				correctness.setText("Correct!");
			}
			//if it was not the correct choice then it will print an incorrect statement with the next question
			else {
				correctness.setText("Incorrect! Correct Answer is " + correctChoice);
			}
			//updates the quiz screen into the next question
			counter++;
			quizStage.close();
			quizScreen();
		}
		//when the third answer is selected
		else {
			//if this was the correct choice then it will update the score and print out correct with the next question
			if (correctChoiceIndex == 2) {
				quiz.updateScore();
				correctness.setText("Correct!");
			}
			//if it was not the correct choice then it will print an incorrect statement with the next question
			else {
				correctness.setText("Incorrect! Correct Answer is " + correctChoice);
			}
			//updates the quiz screen into the next question
			counter++;
			quizStage.close();
			quizScreen();
		}
	}

	//submit button is able to be pressed when a answer choice is chosen
	public void radioMultipleChoice(ActionEvent event) {
		submitButton.setDisable(false);
	}

	//this is a method that will take place whenever the user starts a new quiz or clicks submit to go to the next question
	public void quizScreen() {
		//creates a new question based on the counter number
		question = new Text(quiz.printQuestion(counter));
		question.setFont(new Font("Helvetica", 20));

		//creates the three radio buttons for the multiple choice answers
		ToggleGroup group = new ToggleGroup();
		AnswerChoice1 = new RadioButton(quiz.getQuestion(counter).getChoice(0));
		AnswerChoice1.setToggleGroup(group);
		AnswerChoice1.setOnAction(this::radioMultipleChoice);

		AnswerChoice2 = new RadioButton(quiz.getQuestion(counter).getChoice(1));
		AnswerChoice2.setToggleGroup(group);
		AnswerChoice2.setOnAction(this::radioMultipleChoice);

		AnswerChoice3 = new RadioButton(quiz.getQuestion(counter).getChoice(2));
		AnswerChoice3.setToggleGroup(group);
		AnswerChoice3.setOnAction(this::radioMultipleChoice);

		//stores which choice is the correct one
		correctChoiceIndex = quiz.getQuestion(counter).getCorrectChoiceIndex();
		correctChoice = quiz.getQuestion(counter).getCorrectChoice();

		//creates a submit button that is initially grayed out until an answer choice is chosen
		submitButton = new Button("Submit");
		submitButton.setOnAction(this::submitButtonHandler);
		submitButton.setDisable(true);

		//vbox layout for the quiz
		quizLayout = new VBox();
		quizLayout.getChildren().addAll(correctness, question, AnswerChoice1, AnswerChoice2, AnswerChoice3, submitButton);
		quizLayout.setAlignment( Pos.CENTER );
		quizLayout.setSpacing(20);
		quizLayout.setStyle("-fx-background-color: skyblue");
		quizScene = new Scene(quizLayout, 900, 700);

		//sets quiz modality so that when it is active then only this stage can be interacted with
		quizStage = new Stage();
		quizStage.setTitle("Geography Quiz");
		quizStage.setScene(quizScene);
		quizStage.initModality(Modality.APPLICATION_MODAL);
		this.quizStage = quizStage;
		quizStage.show();

	}

}
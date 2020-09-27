package edu.uga.cs1302.quiz;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;

public class GeographyQuiz extends Application {

	private Stage primaryStage;

	//starts JAVAFX program
	@Override
	public void start(Stage primaryStage) {

		//basic JavaFX GUI setup to start
		MainScreenPane pane = new MainScreenPane();
		pane.setAlignment(Pos.CENTER);
		pane.setStyle("-fx-background-color: skyblue");

		Scene scene = new Scene(pane, 900, 700);

		this.primaryStage = primaryStage;

		primaryStage.setTitle("Geography Quiz");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	//main method to start the launch
	public static void main(String[] args) {
		launch(args);
	}

}

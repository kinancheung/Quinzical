package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import app.model.Board;
import app.model.Player;
import app.model.Question;
import app.util.IOManager;
import app.util.ViewController;
import app.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This is the main class of the project. It contains main method.
 * 
 * @author Kinan and Kelvin
 *
 */
public class MainApp extends Application {

	private Stage window;
	private BorderPane rootLayout;
	private RootLayoutController rootLayoutController;
	private Player player;
	private Question currentQuestion;
	private boolean questionStatus;
	private String currentQuestionValue;

	/**
	 * Set the root window of the project, load up files to read, set up background
	 * skin.
	 */
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;

		// loads up files and restores the objects
		IOManager.initialiseSpeechFile();
		player = IOManager.readPlayerData();
		IOManager.loadCategories(player, IOManager.dirCategory);
		IOManager.loadThemes(player);

		// On exit button pressed on top right of window call closeProgram()
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});

		// set up background picture
		initRootLayout();
		Background savedTheme = IOManager.loadTheme(player.returnCurrentBackground());
		setRootTheme(savedTheme);
		showView("view/MainMenu.fxml");
	}

	public static void main(String[] arg) {
		launch(arg);
	}

	/**
	 * Set up root window that the other windows are based on.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			ViewController controller = (ViewController) loader.getController();
			rootLayoutController = (RootLayoutController) controller;
			controller.setMainApp(this);
			Scene scene = new Scene(rootLayout);
			window.setScene(scene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Base method to return views from other scenes reducing code duplication
	 * 
	 * @param name of the file to load
	 * @return ViewController object
	 */
	public ViewController showView(String file) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(file));
			Node view = (Node) loader.load();
			rootLayout.setCenter(view);
			ViewController controller = (ViewController) loader.getController();
			controller.setMainApp(this);

			return controller;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method set up the background picture
	 * 
	 * @param background
	 */
	public void setRootTheme(Background background) {
		rootLayout.setBackground(background);
	}

	/**
	 * Confirmation alert to check if player meant to close game.
	 */
	public void closeProgram() {
		// Confirmation messages
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Leave Game?");
		alert.setContentText("Are you sure you want to quit?");

		// add style sheet for the confimation dialogue
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets()
				.add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
		dialogPane.getStyleClass().add("Confirmation");

		// set up the procedures after OK is pressed
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			IOManager.writePlayerData(player, Board.getBoardObject());
			System.exit(0);
		}
	}

	/**
	 * This method chooses a question at random from a given ArrayList of questions.
	 * And set the currentQuestion as the chosen question. If the ArrayList of
	 * questions is empty, it automatically resets the ArrayList.
	 * 
	 * This is used in both games module and practice module.
	 * 
	 * @param An ArrayList of question objects.
	 */
	public void returnRandomQuestion(ArrayList<Question> questions) {
		// get a random number from 1 to the number of questions
		Random rand = new Random();
		int randomIndex = rand.nextInt(questions.size());

		// using the random number to set a current question
		currentQuestion = questions.get(randomIndex);

		// remove the question from question bank
		questions.remove(randomIndex);

		// if question bank is empty resets it
		if (questions.size() == 0) {
			player.resetData();
		}

	}

	public Stage getPrimaryStage() {
		return window;
	}

	public Player returnPlayer() {
		return player;
	}

	/**
	 * Set the current question that the player is answering
	 * 
	 * @param question
	 */
	public void setCurrentQuestion(Question question) {
		currentQuestion = question;
	}

	/**
	 * Return the current question that the player is answering
	 * 
	 * @return currentQuestion
	 */
	public Question returnCurrentQuestion() {
		return currentQuestion;
	}

	/**
	 * Set the status of the currentQuestion depending on whether the player answers
	 * it correctly or not
	 * 
	 * @param answer
	 */
	public void setQuestionStatus(boolean answer) {
		questionStatus = answer;
	}

	/**
	 * Set the value of a question to a specific numeric value
	 * 
	 * @param value
	 */
	public void setCurrentQuestionValue(String value) {
		currentQuestionValue = value;
	}

	/**
	 * Return the current value of the question
	 * 
	 * @return currentQuestionValue
	 */
	public String returnCurrentQuestionValue() {
		return currentQuestionValue;
	}

	/**
	 * Return the current status of the question after the player has answer it
	 * 
	 * @return questionStatus
	 */
	public boolean returnQuestionStatus() {
		return questionStatus;
	}

	public RootLayoutController returnRootLayoutController() {
		return rootLayoutController;
	}

}

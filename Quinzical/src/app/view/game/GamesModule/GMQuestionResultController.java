package app.view.game.GamesModule;

import app.MainApp;
import app.model.Speech;
import app.util.SpeechHelperThread;
import app.util.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This class has the business logic for question result page for games module
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GMQuestionResultController implements ViewController {

	private MainApp app;

	@FXML
	private Label questionStatus;

	@FXML
	private Label questionStatusMessage;

	@FXML
	private Label answerText;

	@FXML
	private Label winningsMessage;

	@FXML
	private Label winningAmount;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		String value = app.returnCurrentQuestionValue();

		//depend on the correctness of answer carry out different tasks
		if (app.returnQuestionStatus()) {
			//speak "correct"
			Speech.getSpeechObject().setSpeech("Correct");
			SpeechHelperThread helper = new SpeechHelperThread();
			helper.start();
			
			//text display on screen
			questionStatus.setText("Correct");
			questionStatus.setTextFill(Color.DARKGREEN);
			questionStatusMessage.setText("You correctly answered:");
			answerText.setText(app.returnCurrentQuestion().returnAnswer());
			winningsMessage.setText("You Earned:");
			winningAmount.setText("$" + value);
		} else {
			//speak incorrect
			Speech.getSpeechObject().setSpeech("Incorrect");
			SpeechHelperThread helper = new SpeechHelperThread();
			helper.start();
			
			//display text on screen
			questionStatus.setText("Incorrect");
			questionStatus.setTextFill(Color.RED);
			questionStatusMessage.setText("You incorrectly answered the question");
			answerText.setText(app.returnCurrentQuestion().returnAnswer());
			winningsMessage.setText("You Missed:");
			winningAmount.setText("$" + value);
		}

	}

	/**
	 * button reaction for play again button
	 * @param event
	 */
	@FXML
	void playAgain(ActionEvent event) {
		app.showView("view/game/GamesModule/GamesModuleBoard.fxml");

	}

	/**
	 * button reaction for return to main button
	 * @param event
	 */
	@FXML
	void returnToMainMenu(ActionEvent event) {
		app.showView("view/game/GamesModule/GamesModuleMenu.fxml");

	}

}

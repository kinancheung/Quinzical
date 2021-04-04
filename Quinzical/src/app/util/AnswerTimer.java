package app.util;

import java.util.Timer;
import java.util.TimerTask;

import app.MainApp;
import app.view.game.GamesModule.GMAnswerQuestionController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is an abstraction of a timer. It is used to provide a 30 second
 * timer and display the count on the screen. Used in the Games module Answer
 * Page.
 */

public class AnswerTimer {
	Timer timer;
	TextField answerInput;
	Button button1;
	MainApp app;
	GMAnswerQuestionController controllerObject;
	Label questionText;

	public AnswerTimer(TextField textField, Button button1, MainApp app, GMAnswerQuestionController object,
			Label questionText) {
		// set up the fields
		timer = new Timer();
		answerInput = textField;
		controllerObject = object;
		this.app = app;
		this.button1 = button1;
		this.questionText = questionText;

		// Fire the timer
		timer.scheduleAtFixedRate(new RemindTask(), 1000, 1000);
	}

	/**
	 * This method is invoked when times up. It directs the user to the result page.
	 */
	public void submitAction() {
		try {
			if (controllerObject != null) {
				if (controllerObject.getButtonPressed() == false) {

					// check the correctness of the input
					boolean answerCorrectness = app.returnCurrentQuestion().answer(answerInput.getText());
					if (answerCorrectness == true) {
						int questionValue = Integer.parseInt(app.returnCurrentQuestionValue());
						app.returnPlayer().addWinnings(questionValue);
					}
					app.setQuestionStatus(answerCorrectness);

					// set the visibility of the slider
					app.returnRootLayoutController().setSliderVisibility(false);

					// redirect to result page
					app.showView("view/game/GamesModule/GMQuestionResult.fxml");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is an inner class, which responsible for displaying counting on screen.
	 * 
	 * @author Kelvin
	 *
	 */
	class RemindTask extends TimerTask {
		int count = 30;

		public void run() {
			try {
				if (questionText != null) {
					// Display count on the screen
					if (count > 0) {
						Platform.runLater(() -> questionText.setText(Integer.toString(count)));
						count--;
					} else {
						Platform.runLater(() -> questionText.setText("Times Up!"));

						// Terminate the timer thread and submit the answer
						timer.cancel();
						Platform.runLater(() -> submitAction());
					}
				} else {
					timer.cancel();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
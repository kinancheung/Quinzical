package app.view.game.GamesModule;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import app.MainApp;
import app.model.Question;
import app.model.Speech;
import app.util.SpeechHelperThread;
import app.util.ViewController;
import app.view.RootLayoutController;

/**
 * This class sets the business logic for answering question page.
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GMAnswerQuestionController implements ViewController {
	private MainApp app;
	private boolean buttonPressed = false;
	private int oldCaretPosition;
	private SpeechHelperThread _helperThread;
	private RootLayoutController rootLayoutController;

	@FXML
	private Label questionText;

	@FXML
	private Button btnReplySound;

	@FXML
	private TextField answerInput;

	@FXML
	private Pane avaliableSpace;

	@FXML
	private Button btnSubmit;

	@FXML
	private Button btnDontKnow;

	@FXML
	private Label countLabel;

	/**
	 * This method sets reaction for don't know button.
	 */
	@FXML
	private void setDontKnow() {
		// set speech slider to invisible
		rootLayoutController.setSliderVisibility(false);

		// stop any still playing speech
		_helperThread.destoryProcess();

		// redirect to result page
		app.setQuestionStatus(false);
		buttonPressed = true;
		app.showView("view/game/GamesModule/GMQuestionResult.fxml");
	}

	/**
	 * This method sets the reaction for submit button.
	 */
	@FXML
	private void submitAnswer() {
		if (answerInput.getText().isBlank()) {
		} else {
			// set speech slider to invisible
			rootLayoutController.setSliderVisibility(false);

			// stop any still playing speech
			_helperThread.destoryProcess();

			// check the correctness of the answer
			boolean answerCorrectness = app.returnCurrentQuestion().answer(answerInput.getText());
			if (answerCorrectness == true) {
				int questionValue = Integer.parseInt(app.returnCurrentQuestionValue());
				app.returnPlayer().addWinnings(questionValue);
				app.returnPlayer().addEarning(questionValue);
			}
			app.setQuestionStatus(answerCorrectness);
			buttonPressed = true;

			// redirect to result page
			app.showView("view/game/GamesModule/GMQuestionResult.fxml");
		}
	}

	/**
	 * This method sets reaction for replay button.
	 */
	@FXML
	private void replaySound() {
		// destroy any now playing speech
		_helperThread.destoryProcess();

		// read out the question
		String questionSpeech = Question.replaceMacrons(app.returnCurrentQuestion().returnQuestion());
		Speech.getSpeechObject().setSpeech(questionSpeech);
		_helperThread = new SpeechHelperThread();
		_helperThread.start();
	}

	@FXML
	private void aMacron() {
		answerInput.insertText(oldCaretPosition, "ā");
	}

	@FXML
	private void eMacron() {
		answerInput.insertText(oldCaretPosition, "ē");
	}

	@FXML
	private void iMacron() {
		answerInput.insertText(oldCaretPosition, "ī");
	}

	@FXML
	private void oMacron() {
		answerInput.insertText(oldCaretPosition, "ō");
	}

	@FXML
	private void uMacron() {
		answerInput.insertText(oldCaretPosition, "ū");
	}

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;

		// Set the sound icon
		Image image = new Image(getClass().getResourceAsStream("/image/volume.png"));
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(25);
		imageView.setFitHeight(25);
		btnReplySound.setGraphic(imageView);

		// Invoke healper thread to read out the clue after that start the timer
		questionText.setText("Please listen to the question");
		String questionSpeech = Question.replaceMacrons(app.returnCurrentQuestion().returnQuestion());
		Speech.getSpeechObject().setSpeech(questionSpeech);
		_helperThread = new SpeechHelperThread(answerInput, btnSubmit, btnDontKnow, app, this, countLabel);
		_helperThread.start();

		// Set the GUI components visibility
		answerInput.setVisible(false);
		answerInput.setPromptText("Enter your answer here!");
		countLabel.setVisible(false);
		btnSubmit.setVisible(false);
		btnDontKnow.setVisible(false);
		answerInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				oldCaretPosition = answerInput.getCaretPosition();
			}
		});

		//set tool tip for submit button
		btnSubmit.setTooltip(new Tooltip("disabled when input is empty"));
		
		// set speech speed slider visible
		rootLayoutController = app.returnRootLayoutController();
		rootLayoutController.setSliderVisibility(true);
	}

	/**
	 * This is a getter method for buttonPressed
	 * 
	 * @return
	 */
	public boolean getButtonPressed() {
		return buttonPressed;
	}

}

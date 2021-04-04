package app.view.game.PracticeModule;

import app.MainApp;
import app.model.Question;
import app.model.Speech;
import app.util.SpeechHelperThread;
import app.util.ViewController;
import app.view.RootLayoutController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
/**
 * This controller is responsible for allowing the user to answer the question
 * in the Practice Module
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class PMAnswerQuestionController implements ViewController {

	private MainApp app;
	private SpeechHelperThread _helperThread;
	private int oldCaretPosition;
	private RootLayoutController rootLayoutController;

	@FXML
	private Label questionText;

	@FXML
	private TextField answerInput;
	
	@FXML
    private Button btnSubmit;

	public void setMainApp(MainApp app) {
		this.app = app;
		//set tool tip for submit button
		btnSubmit.setTooltip(new Tooltip("disabled when input is empty"));
		
		setScene();
	}

	/**
	 * This loads the screen format for the user to use when answering the question
	 */
	private void setScene() {
		// display question on screen
		answerInput.setPromptText("please enter your answer here");
		questionText.setText(app.returnCurrentQuestion().returnQuestion());
		String questionSpeech = Question.replaceMacrons(app.returnCurrentQuestion().returnQuestion());

		// Fire speech
		Speech.getSpeechObject().setSpeech(questionSpeech);
		_helperThread = new SpeechHelperThread();
		_helperThread.start();

		// add the macron buttons
		answerInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				oldCaretPosition = answerInput.getCaretPosition();
			}
		});

		// set speech speed slider visible
		rootLayoutController = app.returnRootLayoutController();
		rootLayoutController.setSliderVisibility(true);
	}

	/**
	 * This method checks the input of the textfield and sets the question status depending
	 * on the answer
	 */
	@FXML
	private void submitAnswer() {
		if (answerInput.getText().isBlank()) {
		} else {
			// set speech slider to invisible
			rootLayoutController.setSliderVisibility(false);

			// stop any still playing speech
			_helperThread.destoryProcess();

			// redirect to result page
			boolean answer = app.returnCurrentQuestion().answer(answerInput.getText());
			app.setQuestionStatus(answer);
			app.showView("view/game/PracticeModule/PMQuestionResult.fxml");
		}
	}

	/**
	 * This method allows the user to replay the question 
	 */
	@FXML
	private void replayQuestion() {
		_helperThread.destoryProcess();
		Speech.getSpeechObject().setSpeech(app.returnCurrentQuestion().returnQuestion());
		SpeechHelperThread helper = new SpeechHelperThread();
		helper.start();
	}

	/**
	 * Inputs a Maori macron into the text field
	 */
	@FXML
	private void aMacron() {
		answerInput.insertText(oldCaretPosition, "ā");
	}
	
	/**
	 * Inputs a Maori macron into the text field
	 */
	@FXML
	private void eMacron() {
		answerInput.insertText(oldCaretPosition, "ē");
	}

	/**
	 * Inputs a Maori macron into the text field
	 */
	@FXML
	private void iMacron() {
		answerInput.insertText(oldCaretPosition, "ī");
	}

	/**
	 * Inputs a Maori macron into the text field
	 */
	@FXML
	private void oMacron() {
		answerInput.insertText(oldCaretPosition, "ō");
	}

	/**
	 * Inputs a Maori macron into the text field
	 */
	@FXML
	private void uMacron() {
		answerInput.insertText(oldCaretPosition, "ū");
	}
}

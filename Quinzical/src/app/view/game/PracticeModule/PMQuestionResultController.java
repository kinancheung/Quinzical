package app.view.game.PracticeModule;

import java.util.Optional;

import app.MainApp;
import app.model.Speech;
import app.util.SpeechHelperThread;
import app.util.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
/**
 * This controller is responsible for handling the user after they have answered the
 * question in the Practice Module. The scene changes depending on the status of the
 * question answered
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class PMQuestionResultController implements ViewController{
	
	private MainApp app;
	

    @FXML
    private Label questionStatus;

    @FXML
    private Label questionStatusMessage;

    @FXML
    private Label answerText;

    @FXML
    private Label attemptsLeft;

    @FXML
    private Label hint;
    
    @FXML
    private Button playAgain;
    
	public void setMainApp(MainApp app) {
		this.app = app;
		questionStatus();
		questionAttempts();	
	}

	/**
	 * This allows the user to attempt the question again if they choose to do so
	 */
    @FXML
    private void playAgain() {
    	app.returnPlayer().useAttempt();
    	app.showView("view/game/PracticeModule/PMAnswerQuestion.fxml");
    }

    /**
     * This allows the player to return to the main menu and give up on the current question
     */
    @FXML
    private void returnToPracticeBoard() {
    	if(app.returnQuestionStatus() || app.returnPlayer().returnAttempts() == 0) {
    		app.returnPlayer().resetAttempts();
    		app.showView("view/game/PracticeModule/PracticeModuleBoard.fxml");
    	} else {
    		// An alert shows if the player has not used up all their attempts
    		Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirm");
    		alert.setHeaderText("Leave Game?");
    		alert.setContentText("Are you sure you want to return to the \nPractice Board?\n" +
    				"You will lose attempts to this question!");
    		
    		DialogPane dialogPane = alert.getDialogPane();
    		dialogPane.getStylesheets()
    				.add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
    		dialogPane.getStyleClass().add("Confirmation");
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if(result.get() == ButtonType.OK) {
    			app.returnPlayer().resetAttempts();
    			app.showView("view/game/PracticeModule/PracticeModuleBoard.fxml");
    		}
    	}
    }

    
    /**
     * This method changes the status of the current scene depending on the status of the current question.
     * This depends on whether they have answers correctly or not.
     */
    private void questionStatus() {
		if(app.returnQuestionStatus()) {
			// Player has answered correctly
			Speech.getSpeechObject().setSpeech("Correct");
			SpeechHelperThread helper = new SpeechHelperThread();
			helper.start();
			attemptsLeft.setText("You completed this question with " + 
			String.valueOf(app.returnPlayer().returnAttempts() + " more attempts!"));
			playAgain.disableProperty().set(true);
			questionStatus.setText("Correct");
			questionStatus.setTextFill(Color.DARKGREEN);
			questionStatusMessage.setText("You correctly answered:");
			answerText.setText(app.returnCurrentQuestion().returnAnswer());
			hint.setText("");
		} else {
			// Player has answered incorrectly
			Speech.getSpeechObject().setSpeech("Incorrect");
			SpeechHelperThread helper = new SpeechHelperThread();
			helper.start();
			attemptsLeft.setText("You have " + 
			String.valueOf(app.returnPlayer().returnAttempts()) + " attempts left");
			questionStatus.setText("Incorrect");
			questionStatus.setTextFill(Color.RED);
			questionStatusMessage.setText("You incorrectly answered the question");
			answerText.setText("");
			hint.setText("");
		}
    }
    
    /**
     * This method looks after the status of the scene depending on the amount of attempts left
     * from the user.
     */
    private void questionAttempts() {
		// Gives a hint of the first letter of a word if only one attempt is remaining
		if(app.returnPlayer().returnAttempts() == 1 && !app.returnQuestionStatus()) {
			String answer = app.returnCurrentQuestion().returnAnswer();
			answer = answer.toLowerCase().trim().replaceAll("^the+\\s|^a+\\s", "").trim();
			hint.setText("The first letter of the answer is '" + answer.trim().substring(0, 1) +"'");
		}
		
		// If user runs out of attempts give them the answer and disable play again
		if(app.returnPlayer().returnAttempts() == 0) {
			answerText.setText(app.returnCurrentQuestion().returnAnswer());
			playAgain.disableProperty().set(true);
		}
	}

}

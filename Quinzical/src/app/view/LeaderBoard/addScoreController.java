package app.view.LeaderBoard;

import app.MainApp;
import app.model.Score;
import app.util.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This controller is responsible for adding all scores that the players decide to add
 * into the Leader Board
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class addScoreController implements ViewController {

	MainApp app;
	Score currentGameScore;

	@FXML
	Label winningsMessage;

	@FXML
	TextField name;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		winningsMessage.setText("Input your name to record your score!\nYour score for this game is: "
				+ app.returnPlayer().getEarning().toString());
		currentGameScore = new Score();
	}

	/**
	 * This allows the player to return back to the rewards display if they decide not to add
	 * their score
	 */
	@FXML
	private void cancel() {
		app.showView("view/Rewards/RewardDisplay.fxml");
	}

	/**
	 * Adds the score with the current inputs into the Leader Board
	 */
	@FXML
	private void addScore() {
		currentGameScore = new Score(name.getText(), app.returnPlayer().getEarning().toString());
		app.returnPlayer().addScore(currentGameScore);
		app.returnPlayer().resetGameModuleData();
		app.showView("view/MainMenu.fxml");
	}

}

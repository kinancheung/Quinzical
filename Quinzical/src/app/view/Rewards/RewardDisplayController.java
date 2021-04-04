package app.view.Rewards;

import app.MainApp;
import app.util.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 * This controller is responsible for showing the summary of the current game in the Games Module
 * and prompts the player to add their score to the Leader Board or return to the Main Menu to play again
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class RewardDisplayController implements ViewController {

	private MainApp app;

	@FXML
	private Label gameWinnings;

	@FXML
	private Label prompt;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		gameWinnings.setText("You have earnt " + app.returnPlayer().returnWinningsDisplay() +
				" points in this round!");
		prompt.setText("You may add your score to the Leader Board!");
	}

	/**
	 * Allows the user to return to the main menu without recording their score
	 */
	@FXML
	void returnToMainMenu() {
		app.returnPlayer().resetGameModuleData();
		app.showView("view/MainMenu.fxml");
	}

	/**
	 * Allows the user to add their score to the leader board in another scene
	 */
	@FXML
	private void addToLeaderBoard() {
		app.showView("view/LeaderBoard/addScore.fxml");
	}
}

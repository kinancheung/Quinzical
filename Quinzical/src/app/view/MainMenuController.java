package app.view;

import java.util.ArrayList;
import java.util.Optional;

import app.MainApp;
import app.model.Board;
import app.model.Category;
import app.util.IOManager;
import app.util.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is the controller of the MainMenu. The MainMenu business logic are
 * set in this class.
 * 
 * @author se2062020
 *
 */
public class MainMenuController implements ViewController {

	private MainApp app;

	@FXML
	private Button btnGameModule;

	@FXML
	private Button btnPracticeModule;

	@FXML
	private Button quit;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
	}

	/**
	 * This method sets the procedures for when the exit button is pressed.
	 */
	@FXML
	private void exitGame() {
		app.closeProgram();
	}

	/**
	 * This method set the reaction when the Games Module button is pressed.
	 */
	@FXML
	private void setGameModule() {
		app.showView("view/game/GamesModule/GamesModuleMenu.fxml");
	}

	/**
	 * This method set the reaction when the Leader Board button is pressed.
	 */
	@FXML
	private void setLeaderBoard() {
		app.showView("view/LeaderBoard/LeaderBoard.fxml");
	}

	/**
	 * This method set the reaction when the Practice Module button is pressed.
	 */
	@FXML
	private void setPracticeGame() {
		// Alert to reset game if they go into PM to avoid cheating points
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setHeaderText("Reset game?");
		alert.setContentText("Are you sure you want to go to the\npractice module? \nThis will reset your game!");

		// load css style sheet
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets()
				.add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
		dialogPane.getStyleClass().add("Confirmation");

		// reaction when OK button is pressed
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			app.returnPlayer().resetGameModuleData();
			app.showView("view/game/PracticeModule/PracticeModuleBoard.fxml");
		}
	}

	/**
	 * This method set the reaction when the Reward Shop button is pressed.
	 */
	@FXML
	private void setRewardsShop() {
		app.showView("view/Rewards/RewardsMenu.fxml");
	}

}

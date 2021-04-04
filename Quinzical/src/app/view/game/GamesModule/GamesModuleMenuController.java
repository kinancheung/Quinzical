package app.view.game.GamesModule;

import java.util.Optional;

import app.MainApp;
import app.util.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;

/**
 * This class sets the business logic for Games Module menu page
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GamesModuleMenuController implements ViewController {
	private MainApp app;

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnViewWinnings;

	@FXML
	private Button btnReset;

	@FXML
	private Button btnReturn;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
	}

	@FXML
	void resetGame() {
		// set confirmation message
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm");
		alert.setContentText("You earned $" + app.returnPlayer().getEarning().toString() + " in this play!\n\n"
				+ "Reset the Games Module will clear all \n"
				+ "the existing data in Games Module! \n\nThe earned amount has already been \ntransfered to winnings.");

		// load css style sheet
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets()
				.add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
		dialogPane.getStyleClass().add("Confirmation");

		// set reaction when Ok is pressed
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			app.returnPlayer().resetGameModuleData();
			alert.close();
		}
	}

	/**
	 * This method sets the reaction when the play game button is pressed.
	 */
	@FXML
	void playGame() {
		// if this is a new game go select categories
		if (app.returnPlayer().checkNumOfGMCategories() < GamesModuleBoardController.numCategories) {
			app.returnPlayer().removeAllGMCategories();
			app.showView("view/game/GamesModule/GMChooseCategories.fxml");
		} else {
			// not a new game, continue from last time
			app.showView("view/game/GamesModule/GamesModuleBoard.fxml");
		}
	}

	/**
	 * This method sets reaction for return to previous button.
	 */
	@FXML
	void returnToMain() {
		app.showView("view/MainMenu.fxml");
	}

	/**
	 * This method sets reaction for view winning button.
	 */
	@FXML
	void viewWinnings() {
		app.showView("view/game/GamesModule/GMWinningsDisplay.fxml");

	}
}

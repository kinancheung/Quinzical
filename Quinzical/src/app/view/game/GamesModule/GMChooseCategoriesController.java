package app.view.game.GamesModule;

import java.util.List;

import app.MainApp;
import app.model.Category;
import app.model.Player;
import app.model.Theme;
import app.util.IOManager;
import app.util.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class set the business logic for choose category page
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GMChooseCategoriesController implements ViewController {

	private MainApp app;
	private Player player;

	@FXML
	private BorderPane root;

	@FXML
	private GridPane gridPane;

	@FXML
	private Button btnAutomatic;


	@FXML
	void automaticSelect(ActionEvent event) {
		if (player.checkNumOfGMCategories() == GamesModuleBoardController.numCategories) {
			app.showView("view/game/GamesModule/GamesModuleBoard.fxml");
		} else {
			btnAutomatic.setTooltip(new Tooltip("Please select at least 5 categories"));
		}

	}

	@FXML
	void returnToMainMenu(ActionEvent event) {
		player.removeAllGMCategories();
		app.showView("view/game/GamesModule/GamesModuleMenu.fxml");
	}

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		player = app.returnPlayer();
		// Alert to see if there are questions loaded into the game
		List<Category> categories = app.returnPlayer().returnCategories();
		if (categories.size() == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText("No questions found!");
			alert.setContentText("Please load in questions");
			alert.showAndWait();
			app.showView("view/MainMenu.fxml");
		}
		// If there are questions available, set the question selection board
		setTable(categories);
	}

	private void setTable(List<Category> categories) {
		int currentColumn = 0;
		int currentRow = 0;
		for (Category c : categories) {
			// Loads the category title component and adds it to the question board
			Button title = (Button) IOManager.loadFXML("components/PMCategoryButton.fxml");
			title.setText(c.returnTitle());
			title.setOnAction(e -> {
				if (player.checkGameModuleCategory(c) == true) {
					player.removeGamesModuleCategory(c);
					title.setStyle("-fx-text-fill: " + Theme.Pink
							+ ";-fx-font-weight: bold;-fx-border-color: transparent;-fx-border-width: 2px;-fx-border-radius: 15;-fx-background-radius: 15");
				} else if (player.checkNumOfGMCategories() < GamesModuleBoardController.numCategories) {
					player.addGameModuleCategory(c);
					title.setStyle("-fx-text-fill: " + Theme.Shadow + ";-fx-font-weight: bold;-fx-border-color: "
							+ Theme.Answered
							+ ";-fx-border-width: 2px;-fx-border-radius: 15;-fx-background-radius: 15");
				} else {
				}
			});
			GridPane.setHalignment(title, HPos.CENTER);
			gridPane.add(title, currentColumn, currentRow);
			currentColumn++;
			// Limit to 5 categories per row
			if (currentColumn == 5) {
				currentColumn = 0;
				currentRow++;
			}
		}
	}

}

package app.view.game.PracticeModule;

import java.util.List;

import app.MainApp;
import app.model.Category;
import app.util.IOManager;
import app.util.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
/**
 * This controller is responsible for displaying all the categories within the categories folder
 * for the user to practice in the Practice Module
 * 
 * @author Kinan Cheung and Kelving Shen
 *
 */
public class PracticeModuleBoardController implements ViewController {
	
	private MainApp app;
	
	@FXML
	private GridPane gridPane;


	public void setMainApp(MainApp app) {
		this.app = app;	
		//Alert to see if there are questions loaded into the game
		List<Category> categories = app.returnPlayer().returnCategories();
				if(categories.size() == 0) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("WARNING");
					alert.setHeaderText("No questions found!");
					alert.setContentText("Please load in questions");
					alert.showAndWait();
					app.showView("view/MainMenu.fxml");
				}
				//If there are questions available, set the question selection board
				setTable(categories);
	}
	
	/**
	 * This allows the player to return back to the main menu and
	 * resets the game 
	 */
	@FXML
	private void returnToMainMenu() {
		app.returnPlayer().resetData();
		app.showView("view/MainMenu.fxml");
	}
	
	/**
	 * This method sets a table of all categories within the categories folder
	 * @param categories
	 */
	private void setTable(List<Category> categories) {
		int currentColumn = 0;
		int currentRow = 0;
		for(Category c : categories) {
			//Loads the category title component and adds it to the question board
			Button title = (Button) IOManager.loadFXML("components/PMCategoryButton.fxml");
			title.setText(c.returnTitle());
			title.setOnAction(e -> {
				// Set random question as the current question
				app.returnRandomQuestion(c.returnQuestions());
				app.showView("view/game/PracticeModule/PMAnswerQuestion.fxml");
			});
			GridPane.setHalignment(title, HPos.CENTER);
			gridPane.add(title, currentColumn, currentRow);
			currentColumn++;
			// Limit to 5 categories per row
			if(currentColumn == 5) {
				currentColumn = 0;
				currentRow++;
			}
		}
	}

}

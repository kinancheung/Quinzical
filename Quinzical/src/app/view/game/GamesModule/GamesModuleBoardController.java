package app.view.game.GamesModule;

import java.util.ArrayList;

import app.MainApp;
import app.model.Board;
import app.model.Category;
import app.model.Theme;
import app.util.IOManager;
import app.util.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 * This method sets the business logic for the Games Module page.
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GamesModuleBoardController implements ViewController {

	public static int numCategories = 5;
	private Board boardObject;
	private MainApp app;



	@FXML
	private Label currentScore;

	@FXML
	private GridPane gridPane;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		boardObject = Board.getBoardObject();

		// displays the current score on screen
		currentScore.setText("Current Score: " + app.returnPlayer().getEarning().toString());

		// unlock international section
		unlockInternational();

		// initializing the clue grid
		setTable(app.returnPlayer().getGameModuleCategories());

		// check whether all clues have been answered
		checkCompletenessOfClues();

	}

	/**
	 * This method sets the reaction when the return button is pressed.
	 */
	@FXML
	private void returnToMainMenu() {
		app.showView("view/game/GamesModule/GamesModuleMenu.fxml");
	}

	/**
	 * This method creates a games module question grid.
	 * 
	 * @param categories
	 * @see GamesModuleBoardController.setMainPage
	 */
	public void setTable(ArrayList<Category> categories) {
		// for each category creates a column of 5 clue buttons
		int currentColumn = 0;
		for (Category c : categories) {
			int currentRow = 0;
			categoryNameButton(c, currentColumn, currentRow);
			try {
				// add buttons with a value from 100 to 500
				for (int i = 0; i < 5; i++) {
					currentRow++;
					Button btn;
					String name = Integer.toString(((i + 1) * 100));

					// check whether the question has been answered and set the corresponding button
					// feature
					if (boardObject.returnButtonState(i, currentColumn) == true) {
						btn = (Button) IOManager.loadFXML("components/GMAnsweredButton.fxml");
					} else {
						btn = (Button) IOManager.loadFXML("components/GMNotAnsweredButton.fxml");
					}
					btn.setText(name);

					// set the on pressed functions of the buttons
					setButtonOnPressed(i, currentColumn, btn, name, c);

					// add the button to the scene
					GridPane.setHalignment(btn, HPos.CENTER);
					gridPane.add(btn, currentColumn, currentRow);

				}
			} catch (Exception e) {
				System.out.print("exception thrown when building child buttons \n");
			}
			currentColumn++;
		}
	}

	/**
	 * this method checks whether a question with lower value is still available.
	 * 
	 * @param rowPosition
	 * @param columnPosition
	 * @return boolean=avaliability
	 */
	private boolean checkAvaliability(int rowPosition, int columnPosition) {
		boolean avaliability = true;

		// check if any of the button before the selected button is available
		for (int i = 0; i < rowPosition; i++) {
			if (boardObject.returnButtonState(i, columnPosition) == false) {
				avaliability = false;
			}
		}

		// check if the selected button has been answer
		if (boardObject.returnButtonState(rowPosition, columnPosition) == true) {
			avaliability = false;
		}
		return avaliability;
	}

	/**
	 * This method checks whether all clues in the gird has been taken. Used when
	 * first initializing the Board page.
	 * 
	 * @see GamesModuleBoardController.setMainPage
	 */
	public void checkCompletenessOfClues() {
		if (boardObject.checkAllStates() == true) {
			app.returnPlayer().addWinnings(5000);
			app.showView("view/Rewards/RewardDisplay.fxml");
		}
	}

	/**
	 * This method creates the GUI for each category name.
	 * 
	 * @param c
	 * @param currentColumn
	 * @param currentRow
	 * @see setTable
	 */
	public void categoryNameButton(Category c, int currentColumn, int currentRow) {
		// load the fxml file
		Button title = (Button) IOManager.loadFXML("components/CategoryName.fxml");

		// set text on button
		String tempTitle = c.returnTitle();
		tempTitle.replace(" ", "\n");
		title.setText(tempTitle);

		// add to scene
		GridPane.setHalignment(title, HPos.CENTER);
		gridPane.add(title, currentColumn, currentRow);
	}

	/**
	 * This method set the reaction of the buttons when pressed.
	 * 
	 * @param rowPosition
	 * @param columnPosition
	 * @param btn
	 */
	public void setButtonOnPressed(int rowPosition, int columnPosition, Button btn, String name, Category c) {
		// check whether this button is of the lowest value
		boolean avaliability = checkAvaliability(rowPosition, columnPosition);
		if (avaliability == true) {
			btn.setStyle("-fx-border-color: " + Theme.Shadow
					+ ";-fx-text-fill: black;-fx-font-weight: bold;-fx-border-color: " + Theme.Shadow
					+ ";-fx-border-width: 2px;-fx-border-radius: 15;-fx-background-radius: 15");
			btn.setOnAction(e -> {
				// change the state of this button
				boardObject.changeBoardState(rowPosition, columnPosition);
				app.setCurrentQuestionValue(name);

				// Set random question as the current question
				app.returnRandomQuestion(c.returnQuestions());
				app.showView("view/game/GamesModule/GMAnswerQuestion.fxml");
			});
		} else {
			btn.setTooltip(new Tooltip("not avaliable"));
		}
	}

	/**
	 * This method unlocks the international category when two categories are
	 * completed.
	 */
	public void unlockInternational() {
		// check whether two categories has completed
		if (boardObject.checkTwoColumnsUnAvaliable() == true
				&& app.returnPlayer().checkNumOfGMCategories() == numCategories) {
			IOManager.loadCategories(app.returnPlayer(), IOManager.dirInternational);
			Category presentCategory = app.returnPlayer().returnCategory("international");
			app.returnPlayer().addGameModuleCategory(presentCategory);
		}
	}

}

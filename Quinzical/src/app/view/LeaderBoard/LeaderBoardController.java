package app.view.LeaderBoard;

import app.MainApp;
import app.model.Score;
import app.util.ViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
/**
 * This controller is responsible for displaying the Leader Board where all players are ranked
 * depending on their scores within the Games Module
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class LeaderBoardController implements ViewController {

	MainApp app;
	ObservableList<Score> scores;

	@FXML
	TableView<Score> tableView;

	@FXML
	TableColumn<Score, String> nameCol;

	@FXML
	TableColumn<Score, String> scoreCol;

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		tableView.getStylesheets().add("app/components/leaderBoard.css");
		scores = FXCollections.observableArrayList(app.returnPlayer().returnScores());
		setTable();
	}

	/**
	 * Allows the user to return to the main menu
	 */
	@FXML
	private void returnToMainMenu() {
		app.showView("view/MainMenu.fxml");
	}

	/**
	 * Initialises the leader board by reading the scores stored within the observableArrayList
	 */
	private void setTable() {
		nameCol.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
		scoreCol.setCellValueFactory(celldata -> celldata.getValue().scoreProperty());
		tableView.setItems(scores);
	}
}

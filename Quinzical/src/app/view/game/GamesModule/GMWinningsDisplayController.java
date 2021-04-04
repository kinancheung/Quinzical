package app.view.game.GamesModule;

import app.MainApp;
import app.util.ViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * This class implements the business logic for view winning page
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class GMWinningsDisplayController implements ViewController {
	private MainApp app;

	@FXML
	private Label currentWinnings;

	@FXML
	private Button btnReturn;

	/**
	 * button reaction for return to main button
	 * 
	 * @param event
	 */
	@FXML
	void returnToGMMenu(ActionEvent event) {
		app.showView("view/game/GamesModule/GamesModuleMenu.fxml");
	}

	@Override
	public void setMainApp(MainApp app) {
		this.app = app;
		currentWinnings.setText(app.returnPlayer().returnWinningsDisplay());
	}

}

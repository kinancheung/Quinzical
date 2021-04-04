package app.view.Rewards;

import java.util.List;
import java.util.Optional;

import app.MainApp;
import app.model.Board;
import app.model.Theme;
import app.util.IOManager;
import app.util.ViewController;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
/**
 * This controller is responsible for displaying all themes in the themes folder and shows snippets
 * of these backgrounds for the user to purchase and switch to
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class RewardsMenuController implements ViewController {
	
	MainApp app;
	private int themesCost;
	
	@FXML
	GridPane gridPane;
	
	@FXML
	Label points;

	public void setMainApp(MainApp app) {
		this.app = app;
		themesCost = 5000;
		points.setText(app.returnPlayer().returnWinningsDisplay());
		setRewards(app.returnPlayer().returnThemes());
	}

	/**
	 * This allows the user to return back to the main menu
	 */
	@FXML
	private void returnToMainMenu() {
		app.showView("view/MainMenu.fxml");
		setRewards(app.returnPlayer().returnThemes());
	}
	
	/**
	 * This method sets the themes placed in the themes folder for the player to purchase 
	 * and switch backgrounds
	 * @param themes
	 */
	private void setRewards(List<Theme> themes) {
		int currentRow = 0;
		gridPane.setVgap(10);
		Background theme;
		// Set buttons for all themes in the themes folder with logic to see if the theme is owned or not
		for(Theme t: themes) {
			Button title = (Button) IOManager.loadFXML("components/ThemeButton.fxml");
			theme = IOManager.loadTheme(t);
			title.setBackground(theme);
			if(!t.themeOwned()) {
				title.setText("5000");
			} else {
				title.setText("Owned!");
			}
			title.setOnAction(e -> {
				purchaseTheme(t);
			});
			GridPane.setHalignment(title, HPos.CENTER);
			gridPane.add(title, 0, currentRow);
			currentRow++;
		}
	}
	
	/**
	 * This method allows the user to purchase themes and creates alerts where appropriate
	 * @param selectedTheme
	 */
	private void purchaseTheme(Theme selectedTheme) {
		// If theme is not owned, a confirmation alert to purchase the theme is initialised
		if(!selectedTheme.themeOwned()) {
			// If user does not have enough winnings display message that they need to earn more points
			if(app.returnPlayer().returnWinnings() < themesCost) {
				//FIX THIS the other alert types arent working
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warning");
				alert.setHeaderText("Insufficient points!");
				alert.setContentText("You do not have enough points!" 
			 		+ "\nPlay to earn more points!");
				
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
				dialogPane.getStyleClass().add("Confirmation");
				
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == ButtonType.OK) {
				}
			} else {
				// If user has enough points, display confirmation to purchase the theme
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirm");
				alert.setHeaderText("Purchase theme?");
				alert.setContentText("Are you sure you want to purchase \nthis theme?\n" +
						"\nThis will cost 5000 points!");
				
				DialogPane dialogPane = alert.getDialogPane();
				dialogPane.getStylesheets().add(this.getClass().getClassLoader().getResource("app/components/Confirmation.css").toExternalForm());
				dialogPane.getStyleClass().add("Confirmation");
				
				Optional<ButtonType> result = alert.showAndWait();
				// Purchase the theme and set to the current background
				if(result.get() == ButtonType.OK) {
				selectedTheme.purchaseTheme();
				app.returnPlayer().subtractWinnings(themesCost);
				Background purchasedTheme = IOManager.loadTheme(selectedTheme);
				app.returnPlayer().setCurrentBackground(selectedTheme);
				app.setRootTheme(purchasedTheme);
				}
			}
		} else {
			// If theme is already owned, set it to the background
			Background purchasedTheme = IOManager.loadTheme(selectedTheme);
			app.returnPlayer().setCurrentBackground(selectedTheme);
			app.setRootTheme(purchasedTheme);
		}
	}
}

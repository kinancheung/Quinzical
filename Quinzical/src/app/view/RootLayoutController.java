package app.view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.MainApp;
import app.model.Speech;
import app.util.ViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * This class sets the business logic for the root layout.
 * 
 * @author se2062020
 *
 */
public class RootLayoutController implements ViewController {

	@FXML
	private Slider slider;

	private static final double INIT_SPEED = 1.5;

	private static ExecutorService executor = Executors.newFixedThreadPool(1);

	@Override
	public void setMainApp(MainApp app) {
		setSliderVisibility(false);
	}

	/**
	 * This method allows the user to change the speed of the tts
	 * 
	 * @param event
	 */
	@FXML
	void adjustQuestionSpeed(MouseEvent event) {
		executor.execute(() -> {
			// Adding Listener to value property.
			slider.valueProperty().addListener(new ChangeListener<Number>() {

				// set the reaction when the slider changes state
				@Override
				public void changed(ObservableValue<? extends Number> source, Number oldValue, Number newValue) {
					Double value = newValue.doubleValue();

					// calculate the new speed
					if (value > INIT_SPEED) {
						value = INIT_SPEED - (value - INIT_SPEED);
					} else if (value < INIT_SPEED) {
						value = INIT_SPEED + (INIT_SPEED - value);
					}

					// set the new speed
					Speech.getSpeechObject().setSpeed(value);
				}
			});
		});
	}

	/**
	 * This method sets the visibility of the speech speed slider.
	 * 
	 * @param visibility
	 */
	public void setSliderVisibility(boolean visibility) {
		slider.setVisible(visibility);
	}

}

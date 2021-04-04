package app.util;

import java.util.stream.Stream;

import app.MainApp;
import app.model.Speech;
import app.view.game.GamesModule.GMAnswerQuestionController;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class is responsible for creating a thread for speech processes.
 *
 */
public class SpeechHelperThread extends Thread{
	
	private Process _process;
	private Button _button1;
	private Button _button2;
	private TextField _textField1;
	private MainApp app;
	private GMAnswerQuestionController contollerObject;
	private Label questionText;
	
	
	public SpeechHelperThread() {
	}
	
	public SpeechHelperThread(TextField textField, Button button1, Button button2, 
			MainApp app, GMAnswerQuestionController object, Label questionText2) {
		this.app=app;
		this.questionText = questionText2;
		_textField1=textField;
		_button1 = button1;
		_button2 = button2;
		contollerObject = object;
	}
	
	/*
	 * This method gets called when a speech process starts.
	 */
	@Override
	public void run() {
		try {
			//creates a process to red out the clues
			IOManager.changeSpeechFileContent();
			String filename = Speech.getSpeechObject().getSpeechFilePath();
			ProcessBuilder builder=new ProcessBuilder("bash","-c","festival -b "+filename);
			_process=builder.start();
			_process.waitFor();
			
			//after the clue is read out, set text fields and buttons visible
			Platform.runLater(()-> setVisible());			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	/**
	 * This method sets the UI components to visible after the speech process is over.
	 */
	public void setVisible() {
		try {
			if (questionText != null) {
				//implementation for the timer functionality
				AnswerTimer answerTimerObject = new AnswerTimer(_textField1, _button1, app, 
						contollerObject, questionText);
				questionText.setText("Timer Starts");
				
				//set UI components visible
				questionText.setVisible(true);
				_textField1.setVisible(true);
				_button1.setVisible(true);
				_button2.setVisible(true);
			}
		} catch (Exception e) {
			System.out.print("An exception is thrown when trying to set the answer page buttons to be visible.");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method destroys the current speech process
	 */
	public void destoryProcess() {
		Stream<ProcessHandle> descendants = _process.descendants();
		descendants.filter(ProcessHandle::isAlive).forEach(ph -> {
			ph.destroy();
		});
	}

}

package app.model;

import java.io.File;

/**
 * This class is responsible for synthesizing speech to be read out when the player
 * has to answer questions.
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Speech {
	private static Speech speechObject = null;
	private double _speed=1.5;
	private String _speech;
	private String _speechFilePath;
	
	private Speech() {
		File file = new File("speech.scm");
		_speechFilePath = file.getPath();
	}
	
	/**
	 * This returns the speech object and initialises it if it hasn't done already
	 * @return speechObject
	 */
	public static Speech getSpeechObject() {
		if(speechObject != null) {
			return speechObject;
		}
		speechObject = new Speech();
		return speechObject;
	}
	
	/*
	 * This method returns the speech file (speech.scm) file-path, used for process building.
	 */
	public String getSpeechFilePath() {
		return _speechFilePath;
	}

	/*
	 * This method returns the current chosen speech speed chosen by the player.
	 * By default, the speed is set to be 1.5
	 */
	public double getSpeed() {
		return _speed;
	}
	
	/**
	 * This sets the speed of the speech synthesizer in which the player can adjust
	 * @param speed
	 */
	public void setSpeed(double speed) {
		_speed=speed;
	}
	
	/**
	 * This sets the sentence in which the speecObject will read out
	 * @param words
	 */
	public void setSpeech(String words) {
		_speech=words;
	}
	

	/**
	 * This returns the sentence in which the speechObject will read out
	 * @return speech
	 */
	public String getSpeech() {
		return _speech;
	}

}

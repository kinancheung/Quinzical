package app.model;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class is responsible for holding the name of the player and the score they gain
 * in a complete run of the Games Module to display on the Leader Board
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Score implements Serializable, Comparable{
	
	private static final long serialVersionUID = 5127662219810898409L;
	private String name;
	private String score;
	private transient SimpleStringProperty nameProperty;
	private transient SimpleStringProperty scoreProperty;
	
	public Score() {
	}
	
	public Score(String name, String score) {
		this.name = name;
		this.score = score;
	}
	/**
	 * This overrides the compareTo method from Comparable and returns the scores depending on the
	 * points gained for each Score.
	 */
	@Override
	public int compareTo(Object score) {
		int compareScore = Integer.parseInt(((Score)score).scoreProperty().getValue());
		return compareScore - Integer.parseInt(this.score);
	}

	/**
	 * This returns the "Name" property used in the TableView for the Leader Board
	 * @return
	 */
	public StringProperty nameProperty() {
		nameProperty = new SimpleStringProperty(name);
		return nameProperty;
	}
	
	/**
	 * This returns the "Score" property used in the TableView for the Leader Board
	 * @return
	 */
	public StringProperty scoreProperty() {
		scoreProperty = new SimpleStringProperty(score);
		return scoreProperty;
	}
	


}

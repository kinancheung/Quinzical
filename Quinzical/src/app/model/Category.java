package app.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class contains Question types in the Category object.
 * It allows reference and grouping of specific questions to specific categories.
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Category implements Serializable {

	private static final long serialVersionUID = 7952379167696492156L;
	private ArrayList<Question> questions;
	private String title;

	public Category(String title) {
		questions = new ArrayList<>();
		this.title = title;
	}

	/**
	 * Adds a question in the Category to an ArrayList
	 * 
	 * @param questionText
	 * @param answerText
	 */
	public void addQuestion(String questionText, String answerText) {
		Question question = new Question(this, questionText, answerText);
		if (questions.indexOf(question) == -1) {
			questions.add(question);
		}
	}


	/**
	 * This returns the questions within the category object
	 * @return questions
	 */
	public ArrayList<Question> returnQuestions() {
		return questions;
	}

	/**
	 * This returns the title of the category selected
	 * @return title
	 */
	public String returnTitle() {
		return title;
	}

}

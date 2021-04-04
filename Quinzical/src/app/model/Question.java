package app.model;

import java.io.Serializable;

/**
 * This class is responsible for the format and logic of questions asked within Quinzical
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Question implements Serializable {

	private static final long serialVersionUID = 5755951885038498025L;
	private Category category;
	private String questionText;
	private String answerText;
	private boolean isAnswered;

	public Question(Category category, String questionText, String answerText) {
		this.category = category;
		this.questionText = questionText;
		this.answerText = answerText;
		this.isAnswered = false;
	}
	
	/**
	 * This method reads the user input and outputs a boolean value to see if the answer is correct
	 * @param answer input
	 * @return boolean answer correct or not
	 */
	public boolean answer(String answer) {
		isAnswered = true;
		String userAnswer;
		String givenAnswer;
		String[] finalAnswers;
		// Split the answer if there are multiple applicable answers and remove connector words "the" and "a"
		finalAnswers = answerText.split("[/]");
		userAnswer = answer.toLowerCase().replaceAll("^the+\\s|^a+\\s", "");
		userAnswer = userAnswer.replaceAll("[^a-zA-Z0-9āēīōū]", "").trim();
		for(int i = 0; i < finalAnswers.length; i++) {
			// If answer has multiple answers contained allow the user input to be in any order
			if(finalAnswers[i].contains(",")) {
				String[] multipleAnswers;
				String sectionAnswer;
				multipleAnswers = finalAnswers[i].split("[,]");
				for(int j = 0; j < multipleAnswers.length; j++) {
					sectionAnswer = multipleAnswers[j].toLowerCase().replaceAll("^the+\\s|^a+\\s", "");
					sectionAnswer = sectionAnswer.replaceAll("[^a-zA-Z0-9āēīōū]", "").trim();
					if(!userAnswer.contains(sectionAnswer)) {
						return false;
					}
					userAnswer = userAnswer.replace(sectionAnswer, "").trim();
				}
				if(userAnswer.isEmpty()) {
					return true;
				} else {
					return false;
				}
			} else {
			// If there is only one applicable answer see if user input matches it
			givenAnswer = finalAnswers[i].toLowerCase().trim().replaceAll("^the+\\s|^a+\\s", "");
			givenAnswer = givenAnswer.replaceAll("[^a-zA-Z0-9āēīōū]", "").trim();
			if (userAnswer.equals(givenAnswer)) {
				return true;
			}
		}
		}
		return false;
	}
	
	/**
	 * This method replaces macrons which festival cannot read and replaces it with double vowels
	 * to mimic the sound of the macron
	 * @param question
	 * @return formatted question
	 */
	public static String replaceMacrons(String question) {
		String formattedQuestion;
		formattedQuestion = question.replaceAll("ā", "aa");
		formattedQuestion = formattedQuestion.replaceAll("ē", "ee");
		formattedQuestion = formattedQuestion.replaceAll("ī", "ii");
		formattedQuestion = formattedQuestion.replaceAll("ō", "oo");
		formattedQuestion = formattedQuestion.replaceAll("ū", "uu");
		return formattedQuestion;
	}
	
	/**
	 * This method sets the status of a question to check whether it has been answered
	 * by the user or not
	 * 
	 * @param status
	 * @return questionStatus
	 */
	public boolean setQuestionStatus(boolean status) {
		return isAnswered = status;
	}
	
	/**
	 * Returns the current question status (unanswered or answered)
	 * @return questionStatus
	 */
	public boolean questionStatus() {
		return isAnswered;
	}
	
	/**
	 * Returns the question specified
	 * @return question
	 */
	public String returnQuestion() {
		return questionText.trim();
	}
	
	/**
	 * Returns the answer to the question specified
	 * @return answer
	 */
	public String returnAnswer() {
		return answerText.trim();
	}
	
	/**
	 * This return the category that the question selected belongs to
	 * @return category
	 */
	public Category returnCategory() {
		return category;
	}
}

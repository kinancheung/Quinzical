package app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import app.util.IOManager;
/**
 * This class is responsible for all data to do with the Player using the application
 * It stores all their statuses and implementation called when playing the app.
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Player implements Serializable {

	private Integer winnings;
	private Integer earning;
	private ArrayList<Category> categories;
	private ArrayList<Category> gameModuleCategories;
	private ArrayList<Theme> themes;
	private ArrayList<Score> scores;
	private static final long serialVersionUID = -9142357053191072849L;
	private int attempts;
	private Theme currentBackground;

	public Player() {
		winnings = 5000;
		earning = 0;
		scores = new ArrayList<Score>();
		categories = new ArrayList<Category>();
		gameModuleCategories = new ArrayList<Category>();
		themes = new ArrayList<Theme>();
		attempts = 2;
	}

	/**
	 * This method set a list of categories which stores the 5 randomly selected
	 * categories for the games module.
	 * 
	 * @param selectedCategory
	 */
	public void setGameModuleCategories(ArrayList<Category> selectedCategory) {
		gameModuleCategories = selectedCategory;
	}

	/**
	 * This method adds a Category selected by the user in the
	 * Games Module to an ArrayList
	 * @param category
	 */
	public void addGameModuleCategory(Category category) {
		gameModuleCategories.add(category);
	}

	/**
	 * This method removes a Categories selected by the user
	 * in the Games Module
	 * @param category
	 */
	public void removeGamesModuleCategory(Category category) {
		gameModuleCategories.remove(category);

	}

	/**
	 * This method checks whether the category matches the categories
	 * selected by the user in the Games Module
	 * @param category
	 * @return
	 */
	public boolean checkGameModuleCategory(Category category) {
		return gameModuleCategories.contains(category);
	}

	/**
	 * 
	 * @return the number of Games Module categories selected
	 */
	public int checkNumOfGMCategories() {
		return gameModuleCategories.size();
	}

	/**
	 * Removes all Categories selected in the Games Module when the game is reset
	 */
	public void removeAllGMCategories() {
		gameModuleCategories.clear();
	}

	/**
	 * This method returns an ArrayList of Category objects, which are the selected
	 * category objects for the games module.
	 * 
	 * @return an ArrayList<Category>
	 */
	public ArrayList<Category> getGameModuleCategories() {
		return gameModuleCategories;
	}

	/**
	 * Returns a specific theme if there are any on the Player data
	 * 
	 * @param title of the theme
	 * @return the theme itself
	 */
	public Theme returnTheme(String title) {
		if (themes != null) {
			for (Theme t : themes) {
				if (t.returnThemeTitle().equals(title)) {
					return t;
				}
			}
		}
		return null;
	}

	/**
	 * Returns a specific category if there are any in the Player data
	 * 
	 * @param title of the category
	 * @return the category itself
	 */
	public Category returnCategory(String title) {
		if (categories != null) {
			for (Category c : categories) {
				if (c.returnTitle().equals(title)) {
					return c;
				}
			}
		}
		return null;
	}

	/**
	 * This method deletes all questions within the categories and reloads them to
	 * reset
	 */
	public void resetData() {
		categories.clear();
		IOManager.loadCategories(this, IOManager.dirCategory);
	}

	/**
	 * This method resets the Games Module data
	 */
	public void resetGameModuleData() {
		resetData();
		removeAllGMCategories();
		Board.getBoardObject().clearAllStates();
		resetEarning();
	}

	/**
	 * This method sorts the scores ArrayList from highest score to lowest.
	 * 
	 * @return sorted ArrayList of scores
	 */
	public ArrayList<Score> returnScores() {
		Collections.sort(scores);
		return scores;
	}

	/**
	 * This method returns all the categories loaded in the category folder
	 * @return categories
	 */
	public ArrayList<Category> returnCategories() {
		return categories;
	}

	/**
	 * This method returns all the themes in the themes folder
	 * @return themes
	 */
	public ArrayList<Theme> returnThemes() {
		return themes;
	}

	/**
	 * This method returns the current Game Module games winnings in String form
	 * @return winnings
	 */
	public String returnWinningsDisplay() {
		return Integer.toString(winnings);
	}

	/**
	 * This method returns the current Game Module games winnings in int form
	 * @return winnings
	 */
	public int returnWinnings() {
		return winnings;
	}

	/**
	 * This sets the current background that the user has selected
	 * @param background
	 */
	public void setCurrentBackground(Theme background) {
		currentBackground = background;
	}

	/**
	 * This returns the current background that may or may not have been selected by the user
	 * @return currentBackground
	 */
	public Theme returnCurrentBackground() {
		return currentBackground;
	}

	/**
	 * This adds a Games Module game score to the leaderboard arraylist
	 * @param score
	 */
	public void addScore(Score score) {
		scores.add(score);
	}

	/**
	 * This adds all categories within the category folder to an arraylist
	 * @param category
	 */
	public void addCategory(Category category) {
		categories.add(category);
	}

	/**
	 * This adds all themes within the themes folder to an arraylist
	 * @param theme
	 */
	public void addTheme(Theme theme) {
		themes.add(theme);
	}

	/**
	 * This decrements an attempt from the current attempts the user has in the 
	 * Practice Module
	 */
	public void useAttempt() {
		attempts--;
	}

	/**
	 * This resets the total number of attempts the user has left when practicing
	 * questions in the Practice Module
	 */
	public void resetAttempts() {
		attempts = 2;
	}

	/**
	 * This returns the number of attempts left the player has for a question
	 * in the Practice Module
	 * @return attempts left
	 */
	public int returnAttempts() {
		return attempts;
	}

	/**
	 * This increments the users current Game Module game to keep count
	 * of how many points they earn in the game
	 * @param amount
	 */
	public void addEarning(int amount) {
		earning += amount;
	}

	/**
	 * This resets all the earnings of the current game in the Games Module
	 */
	public void resetEarning() {
		earning = 0;
	}

	/**
	 * This returns the current earnings of the user in the current game in the Games Module
	 * @return earning
	 */
	public Integer getEarning() {
		return earning;
	}

	
	/**
	 * This method adds points to all the users overall bank to purchase themes
	 * @param amount
	 */
	public void addWinnings(int amount) {
		winnings += amount;
	}
	
	/**
	 * This method removes points to all the users overall bank
	 * @param amount
	 */
	public void subtractWinnings(int amount) {
		winnings -= amount;
	}
}

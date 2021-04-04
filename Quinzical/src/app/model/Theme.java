package app.model;

import java.io.Serializable;

import javafx.scene.image.Image;

/**
 * This class is responsible for the statuses of themes which are loaded into the themes folder
 * It is also reponsible for colouring of specific questions as colours are related to themes
 * 
 * @author Kinan Cheung and Kelvin Shen
 *
 */
public class Theme implements Serializable{
	
	
	public static String Answered = "#8b0000";
	public static String Shadow = "#00008b";
	public static String NotAnswered = "#008b00";
	public static String Pink = "#ff00ff";

	private static final long serialVersionUID = 7409810478432379128L;
	private String title;
	private boolean isPurchased;

	
	public Theme(String title) {
		this.title = title;
		isPurchased = false;
	}


	/**
	 * Returns the title of the theme
	 * @return title
	 */
	public String returnThemeTitle() {
		return title;
	}
	
	/**
	 * Sets the status of the theme to purchased
	 */
	public void purchaseTheme() {
		isPurchased = true;
	}
	
	/**
	 * Returns the status of the theme to see if the player has purchase it or not
	 * @return isPurchased
	 */
	public boolean themeOwned() {
		return isPurchased;
	}
	
	
	
}

package app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import app.MainApp;
import app.model.Board;
import app.model.Category;
import app.model.Player;
import app.model.Speech;
import app.model.Theme;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * This class mainly focus on providing functions for reading from and writing
 * to files.
 * 
 * @author se2062020
 *
 */
public class IOManager {

	//path names
	private static String playerPath = "./PlayerData";
	private static String boardPath = "./BoardData";
	public static String dirCategory = "categories/";
	public static String dirInternational = "international/";

	/**
	 * Writes the data from the Player object into the player file. Used after
	 * answering questions or when exiting program to save data
	 * 
	 * @param player
	 */
	public static void writePlayerData(Player player, Board board) {
		try {
			// writing player object to file
			FileOutputStream fos = new FileOutputStream(playerPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(player);
			oos.close();
			fos.close();
			// writing board object to file
			fos = new FileOutputStream(boardPath);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(board);
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads data from the player file and puts it into a player object
	 * 
	 * @return player with potentially saved data
	 */
	public static Player readPlayerData() {
		Player player = new Player();
		Board board = Board.getBoardObject();
		try {
			// read player data from the file and assign it to an object
			FileInputStream fis = new FileInputStream(playerPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			player = (Player) ois.readObject();
			ois.close();
			fis.close();
			fis = new FileInputStream(boardPath);
			ois = new ObjectInputStream(fis);
			Board temporaryBoard = (Board) ois.readObject();
			boolean[][] elementsState = temporaryBoard.returnBoard();
			board.changeAllStates(elementsState);
			ois.close();
			fis.close();
		} catch (IOException e) {
		} catch (ClassNotFoundException f) {
		}
		return player;
	}

	/**
	 * Finds the categories folder and reads the files which contain questions and
	 * adds it to the player data if there is no category in the player data with
	 * the same name.
	 * 
	 * @param player
	 */
	public static void loadCategories(Player player, String dirName) {
		File folder = new File(dirName);
		if (folder.listFiles() != null) {
			File[] fileList = folder.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				Category presentCategory = player.returnCategory(file.getName());
				if (presentCategory == null) {
					Category category = readCategory(file);
					player.addCategory(category);
				}
			}
			writePlayerData(player, Board.getBoardObject());
		}
	}

	/**
	 * Loads all themes in the themes folder and adds them to an ArrayList in the
	 * player class
	 * 
	 * @param player object
	 */
	public static void loadThemes(Player player) {
		File folder = new File("themes/");
		if (folder.listFiles() != null) {
			File[] fileList = folder.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				Theme presentTheme = player.returnTheme(file.getName());
				if (presentTheme == null) {
					String title = file.getName();
					Theme theme = new Theme(title);
					player.addTheme(theme);
				}
			}
			writePlayerData(player, Board.getBoardObject());
		}
	}

	/**
	 * This method reads the questions within a category following a specific format
	 * 
	 * @param file that contains questions for the category
	 * @return category with questions sorted into answer and question
	 */
	private static Category readCategory(File file) {
		String title = file.getName();
		Category category = new Category(title);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				String[] info = line.split("[(]");
				String[] answer = info[1].split("[)]");
				category.addQuestion(info[0], answer[1]);
			}
			br.close();
			return category;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * This loads preset components added to grid pane in the answer question board
	 * as there are different types of components like labels and buttons that
	 * require unnecessary extra code.
	 * 
	 * @param The location of the fxml component
	 * @return Node fxml component
	 */
	public static Node loadFXML(String fxml) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(fxml));
			Node node = loader.load();
			return node;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method loads the background of a specific theme to be placed in the
	 * background of the game
	 * 
	 * @param theme selected
	 * @return background format
	 */
	public static Background loadTheme(Theme theme) {
		if (theme != null) {
			Image image = new Image("file:themes/" + theme.returnThemeTitle());
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true,
					true, true);
			BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			Background background = new Background(backgroundImage);
			return background;
		}
		return null;
	}

	/**
	 * This method initialises the set-up for the speech file which is used for
	 * recording the speed of speech when the UI is opened.
	 */
	public static void initialiseSpeechFile() {
		try {
			File file = new File("speech.scm");
			file.createNewFile();
		} catch (IOException e) {
			System.out.println("An error occurred when initialising the speech file.");
			e.printStackTrace();
		}
	}

	/**
	 * This method by the HelperThread when a sentence or word is going to be read
	 * out to the user. This method uses the fields in the Speech class, to write
	 * new content to the speech file when invoked.
	 */
	public static void changeSpeechFileContent() {
		try {
			Speech speechObject = Speech.getSpeechObject();
			String filename = speechObject.getSpeechFilePath();
			PrintWriter writer;
			writer = new PrintWriter(filename);
			writer.print("");
			writer.print("(voice_akl_nz_jdt_diphone)");
			writer.print("(Parameter.set 'Duration_Stretch " + Double.toString(speechObject.getSpeed()) + ")");
			writer.print("(SayText \"" + speechObject.getSpeech() + "\")");
			writer.close();
		} catch (IOException e) {
			System.out.print("An error is encountered while writing to the speech file\n");
			e.printStackTrace();
		}
	}

}

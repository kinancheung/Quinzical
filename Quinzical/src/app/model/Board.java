package app.model;

import java.io.Serializable;

/**
 * This is a singleton class. An abstraction of the games module board.
 * 
 * @return
 */
public class Board implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Board boardObject = null;
	private boolean[][] board = new boolean[5][6];

	/**
	 * private constructor to initialize the state of the board.
	 */
	private Board() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				board[i][j] = false;
			}
		}
	}

	/**
	 * This method returns the only board object.
	 * 
	 * @return a board object
	 */
	public static Board getBoardObject() {
		if (boardObject == null) {
			boardObject = new Board();
			return boardObject;
		}
		return boardObject;
	}

	/**
	 * This method checks all the state of all the board elements state. Used in the
	 * GamesModuleBoardContorller.
	 */
	public boolean checkAllStates() {
		boolean allAnswered = true;
		for (int i = 0; i < board.length; i++) {
			if (checkColumnAvaliability(i) == false) {
				allAnswered = false;
				break;
			}
		}
		return allAnswered;
	}

	/**
	 * This method checks whether more than 2 categories are fully answered.
	 * 
	 * @return a boolean, if true then 2 or more categories are answered. Otherwise,
	 *         vice versa.
	 */
	public boolean checkTwoColumnsUnAvaliable() {
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			if (checkColumnAvaliability(i) == true) {
				count++;
			}
		}
		if (count == 2) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method checks whether all the clues in a specified column are answered.
	 * 
	 * @param column
	 * @return boolean, true means all answered, otherwise vice versa
	 */
	public boolean checkColumnAvaliability(int column) {
		boolean allAnswered = true;
		for (int i = 0; i < board.length; i++) {
			if (board[i][column] == false) {
				allAnswered = false;
				break;
			}
		}
		return allAnswered;
	}

	/**
	 * This method clears all the board elements state.
	 */
	public void clearAllStates() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = false;
			}
		}
	}

	/**
	 * This method returns the state of a button on the board.
	 * 
	 * @param x=row
	 * @param y=column
	 * @return boolean, false means not pressed, else otherwise.
	 */
	public boolean returnButtonState(int x, int y) {
		return board[x][y];
	}

	/**
	 * This method returns the state of the games module board.
	 * 
	 * @return board.
	 */
	public boolean[][] returnBoard() {
		return board;
	}

	/**
	 * This method changes all the board elements state.
	 */
	public void changeAllStates(boolean[][] elementsState) {
		board = elementsState;
	}

	/**
	 * This method changes the state of the board.
	 * 
	 * @param x=row. y=column
	 */
	public void changeBoardState(int x, int y) {
		board[x][y] = true;
	}
	

}

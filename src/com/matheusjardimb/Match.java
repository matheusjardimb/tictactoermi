package com.matheusjardimb;

import java.util.Date;

import com.matheusjardimb.PlayerFactory.Player;

public class Match {
	// TODO enum?
	public static final int TIMEOUT = -11;
	public static final int ERROR = -10;
	public static final int OCCUPIED_POSITION = -8;
	public static final int OK = -7;
	public static final int WINNER = -6;
	public static final int LOOSER = -5;
	public static final int NO_MATCH = -4;
	public static final int YES = -3;
	public static final int NO = -2;
	public static final int X = -1;
	public static final int NONE = 0;
	public static final int O = 1;
	public static final int DRAW = 2;

	private Player playerX;
	private Date playerXStart;

	private Player playerO;
	private Date playerOStart;

	private int[][] board = new int[3][3];
	private int lastPlayer = Match.O;

	public Match() {
	}

	public Player getX() {
		return playerX;
	}

	public void setX(Player playerX) {
		this.playerX = playerX;
		this.playerXStart = new Date();
	}

	public Player getO() {
		return playerO;
	}

	public void setO(Player playerO) {
		this.playerO = playerO;
		this.playerOStart = new Date();
	}

	public boolean hasO() {
		return this.playerO != null;
	}

	public boolean hasX() {
		return this.playerX != null;
	}

	public Date getXStart() {
		return playerXStart;
	}

	public Date getOStart() {
		return playerOStart;
	}

	public String getBoardToString() {
		String res = "";
		for (int line = 0; line < board.length; line++) {
			for (int column = 0; column < board.length; column++) {
				if (board[line][column] == Match.X) {
					res += " X ";
				} else if (board[line][column] == Match.O) {
					res += " O ";
				} else {
					res += " " + (line * 3 + column) + " ";
				}

				if (column == 0 || column == 1)
					res += "|";
			}
			res += "\n";
		}
		return res + "\n";
	}

	public int getLastPlayer() {
		return lastPlayer;
	}

	public int getWinner() {
		if (checkLines() == Match.O || checkColumns() == Match.O || checkDiagonals() == Match.O) {
			return Match.O;
		}

		if (checkLines() == Match.X || checkColumns() == Match.X || checkDiagonals() == Match.X) {
			return Match.X;
		}

		if (allChecked()) {
			return Match.DRAW;
		}

		return Match.NONE;
	}

	private boolean allChecked() {
		int i = 0;
		for (int line = 0; line < board.length; line++) {
			for (int column = 0; column < board.length; column++) {
				if (board[line][column] != Match.NONE) {
					i++;
				}
			}
		}
		return i == 9;
	}

	private int checkLines() {
		for (int line = 0; line < board.length; line++) {
			if ((board[line][0] + board[line][1] + board[line][2]) == -3)
				return -1;
			if ((board[line][0] + board[line][1] + board[line][2]) == 3)
				return 1;
		}
		return 0;
	}

	private int checkColumns() {
		for (int column = 0; column < board.length; column++) {
			if ((board[0][column] + board[1][column] + board[2][column]) == -3)
				return -1;
			if ((board[0][column] + board[1][column] + board[2][column]) == 3)
				return 1;
		}
		return 0;
	}

	private int checkDiagonals() {
		if ((board[0][0] + board[1][1] + board[2][2]) == -3)
			return -1;
		if ((board[0][0] + board[1][1] + board[2][2]) == 3)
			return 1;
		if ((board[0][2] + board[1][1] + board[2][0]) == -3)
			return -1;
		if ((board[0][2] + board[1][1] + board[2][0]) == 3)
			return 1;

		return 0;
	}

	public int setPosition(Player p, Integer pos) {
		if (getWinner() != 0) {
			return Match.ERROR;
		}

		if (!isTurn(p)) {
			return Match.ERROR;
		}

		int c = pos % 3;
		int l = pos / 3;

		if (l > 2 || c > 2) {
			return ERROR;
		}

		if (board[l][c] != NONE) {
			return ERROR;
		}

		int mark = p.equals(getO()) ? O : X;
		board[l][c] = mark;
		lastPlayer *= -1;

		return 0;
	}

	public boolean isTurn(Player p) {
		if (p.equals(getO()) && getLastPlayer() == O)
			return false;

		if (p.equals(getX()) && getLastPlayer() == X)
			return false;

		return true;
	}

}

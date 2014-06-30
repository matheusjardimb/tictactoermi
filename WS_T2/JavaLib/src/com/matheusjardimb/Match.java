package com.matheusjardimb;

import java.util.Date;

import com.matheusjardimb.PlayerFactory.Player;

public class Match {
	public static final int TIMEOUT = -2;
	public static final int ERROR = -1;
        public static final int NO_MATCH = 0;
	public static final int X = 1;
        public static final int O = 2;
	public static final int YES = 1;
	public static final int NO = 0;
        public static final int WINNER = 2;
        public static final int LOOSER = 3;
        public static final int DRAW = 4;
	public static final int OK = 1;
        public static final int OCCUPIED_POSITION = 0;
        
        public static final int NONE = 0;

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
					res += "X";
				} else if (board[line][column] == Match.O) {
					res += "O";
				} else {
					res += ".";
				}
			}
		}
		return res;
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
			if (board[line][0] == X && board[line][1]  == X && board[line][2] == X)
				return X;
			if (board[line][0]  == O && board[line][1]  == O && board[line][2]  == O)
				return O;
		}
		return NONE;
	}

	private int checkColumns() {
		for (int column = 0; column < board.length; column++) {
			if (board[0][column]  == X && board[1][column]  == X && board[2][column] == X )
				return X;
			if (board[0][column]  == O && board[1][column] == O && board[2][column]== O)
				return O;
		}
		return NONE;
	}

	private int checkDiagonals() {
		if (board[0][0] == O && board[1][1] == O && board[2][2] == O)
			return O;
		if (board[0][0] == X && board[1][1] == X && board[2][2]== X)
			return 1;
		if (board[0][2] == O && board[1][1] == O && board[2][0]== O)
			return O;
		if (board[0][2] == X && board[1][1] == X && board[2][0]== X)
			return X;

		return NONE;
	}

	public int setPosition(Player p, Integer pos) {
		if (getWinner() != Match.NONE) {
                        System.out.println("getWinner() != Match.NONE");
			return Match.ERROR;
		}

		if (!isTurn(p)) {
                        System.out.println("!isTurn(p)");
			return Match.ERROR;
		}

		int c = pos % 3;
		int l = pos / 3;

		if (l > 2 || c > 2) {
                        System.out.println("l > 2 || c > 2");
			return ERROR;
		}

		if (board[l][c] != NONE) {
			return OCCUPIED_POSITION;
		}

		int mark = p.equals(getO()) ? O : X;
		board[l][c] = mark;
                
                if (lastPlayer == X){
                    lastPlayer = O;
                }else{
                    lastPlayer = X;    
                }
                
		return OK;
	}

	public boolean isTurn(Player p) {
		if (p.equals(getO()) && getLastPlayer() == O)
			return false;

		if (p.equals(getX()) && getLastPlayer() == X)
			return false;

		return true;
	}

}

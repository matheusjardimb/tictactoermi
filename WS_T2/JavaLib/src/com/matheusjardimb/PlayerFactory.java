package com.matheusjardimb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.matheusjardimb.exception.InvalidPlayerNameException;
import com.matheusjardimb.exception.PlayerLimitReachedException;

public class PlayerFactory {
	private static final int PLAYERS_LIMIT = 100;
	private static final ArrayList<Player> players = new ArrayList<Player>();
	private static final ArrayList<Match> matches = new ArrayList<Match>();

	private PlayerFactory() {
	}

	public static Player registerPlayer(String name) throws PlayerLimitReachedException, InvalidPlayerNameException {
		if (players.size() >= PLAYERS_LIMIT) {
			throw new PlayerLimitReachedException("Player registration limit reached");
		}

		if (name == null || name.isEmpty()) {
			throw new InvalidPlayerNameException("Player name can't be null");
		}

		boolean b = playerExist(name);
		if (b) {
			throw new InvalidPlayerNameException("Player name already registered");
		}

		int id = players.size() + 1;
		Player player = new Player(name, id);
		players.add(player);

		return player;

	}

	private static boolean playerExist(String name) {
		for (Player p : players) {
			if (p.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private static Player playerExist(Integer id) {
		for (Player p : players) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public static class Player {
		private static final long TIMEOUT = 2 * 60 * 1000;
		private static Random randomNumber = new Random();
		private static int nextHalf_id = 1;

		private String name;
		private Date created_at;

		private int id;

		private Player(String name, int id) {
			super();
			this.name = name;
			this.created_at = new Date();
			this.id = getNextId();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getCreated_at() {
			return created_at;
		}

		public int getId() {
			return id;
		}
		
		private int getNextId() {
			return (nextHalf_id++)<<8 | randomNumber.nextInt(256);
		}

		// TODO review/test this method
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Player)) {
				return false;
			}
			Player p = (Player) obj;
			return p.getId() == getId();
		}

		public boolean hasExpired() {
			Date now = new Date();
			long diff = now.getTime() - getCreated_at().getTime();
			return diff > TIMEOUT;
		}
	}

	public static Integer assignToMatch(Integer id) {
		// TODO
		/*
		 * Retorna: -2 (tempo de espera esgotado), -1 (erro), 0 (ainda não há
		 * partida), 1 (sim, há partida e o jogador inicia jogando com “X”) ou 2
		 * (sim, há partida e o jogador é o segundo a jogar, usando “O”)
		 */

		Player p = playerExist(id);

		if (p == null) {
			return Match.ERROR;
		}

		// User is signed into a match?
		for (Match match : matches) {
			if (p.equals(match.getO())) {
				if (match.hasX()) {
					return Match.O;
				}
				return Match.NO_MATCH;
			}

			if (p.equals(match.getX())) {
				if (match.hasO()) {
					return Match.X;
				}
				return Match.NO_MATCH;
			}
		}

		if (p.hasExpired()) {
			return Match.TIMEOUT;
		}

		// Assign user to a match
		for (Match match : matches) {
			if (!match.hasO()) {
				match.setO(p);
				if (match.hasX()) {
					return Match.O;
				}
				return Match.NO_MATCH;
			}

			if (!match.hasX()) {
				match.setX(p);
				if (match.hasO()) {
					return Match.X;
				}
				return Match.NO_MATCH;
			}
		}

		Match match = new Match();
		match.setX(p);
		matches.add(match);

		return Match.NO_MATCH;
	}

	public static String getBoard(Integer id) {
		Player p = playerExist(id);
		Match match = getMatch(p);
		if (match == null) {
			return null;
		}

		return match.getBoardToString();
	}

	private static Match getMatch(Player p) {
		for (Match match : matches) {
			if (p.equals(match.getO()) || p.equals(match.getX())) {
				return match;
			}

		}
		return null;
	}

	public static String getOpponent(Integer id) {
		Player p = playerExist(id);
		if (p != null) {
			Match match = getMatch(p);
			if (match != null) {
				if (p.equals(match.getO())) {
					return match.hasX() ? match.getX().getName() : "";
				}

				if (p.equals(match.getX())) {
					return match.hasO() ? match.getO().getName() : "";
				}
			}
		}

		return null;
	}

	public static int isPlayerTurn(Integer id) {
		Player p = playerExist(id);
		if (p == null) {
			return Match.ERROR;
		}

		Match match = getMatch(p);
		if (match == null) {
			return Match.ERROR;
		}

		int winner = match.getWinner();
		if (winner != Match.NONE) {
			if (winner == Match.O && match.getO().equals(p)) {
				return Match.WINNER;
			} else if (winner == Match.X && match.getX().equals(p)) {
				return Match.WINNER;
			} else if (winner == Match.DRAW) {
				return Match.DRAW;
			}
			return Match.LOOSER;
		}

		if ((match.getLastPlayer() == Match.O && match.getO().getId() == p.getId()) || (match.getLastPlayer() == Match.X && match.getX().getId() == p.getId())) {
			return Match.NO;
		}

		return Match.YES;
	}

	public static int setPosition(Integer id, Integer pos) {
		/**
		 * TODO
		 * 
		 * Retorna: 2 (partida encerrada, o que ocorrerá caso o jogador demore
		 * muito para enviar a sua jogada e ocorra o time-out de 30 segundos
		 * para envio de jogadas),
		 * 
		 * 1 (tudo certo),
		 * 
		 * 0 (posição ocupada)
		 * 
		 * -1 (erro)
		 */

		Player p = playerExist(id);
		if (p == null) {
			return Match.ERROR;
		}

		Match match = getMatch(p);
		if (match == null) {
			return Match.ERROR;
		}

		int res = match.setPosition(p, pos);
		return res;
	}

}

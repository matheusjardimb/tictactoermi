package com.matheusjardimb.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.matheusjardimb.PlayerFactory;
import com.matheusjardimb.PlayerFactory.Player;
import com.matheusjardimb.client.GameInterface;
import com.matheusjardimb.exception.InvalidPlayerNameException;
import com.matheusjardimb.exception.PlayerLimitReachedException;

public class Game extends UnicastRemoteObject implements GameInterface {
	private static final long serialVersionUID = -513804057617910473L;

	public Game() throws RemoteException {
	}

	@Override
	public Integer registraJogador(String name) throws RemoteException, InvalidPlayerNameException, PlayerLimitReachedException {
		Player p = PlayerFactory.registerPlayer(name);
		return p.getId();
	}

	@Override
	public Integer temPartida(Integer id) throws RemoteException {
		int res = PlayerFactory.assignToMatch(id);
		return res;
	}

	@Override
	public Integer ehMinhaVez(Integer id) throws RemoteException {
		int res = PlayerFactory.isPlayerTurn(id);
		return res;
	}

	@Override
	public String obtemGrade(Integer id) throws RemoteException {
		String board = PlayerFactory.getBoard(id);
		return board;
	}

	@Override
	public Integer enviaJogada(Integer id, Integer jogada) throws RemoteException {
		int res = PlayerFactory.setPosition(id, jogada);
		return res;
	}

	@Override
	public String obtemOponente(Integer id) throws RemoteException {
		String name = PlayerFactory.getOpponent(id);
		return name;
	}
}

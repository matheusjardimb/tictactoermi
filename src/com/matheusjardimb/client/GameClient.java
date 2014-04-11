package com.matheusjardimb.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.matheusjardimb.exception.InvalidPlayerNameException;
import com.matheusjardimb.exception.PlayerLimitReachedException;

public class GameClient {
	private static final int RETRY = 2 * 1000; // 2sec

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Uso: java NotasClient <maquina> <nome>");
			System.exit(1);
		}

		String server = args[0];
		String name = args[1];

		try {
			executeGame(server, name);
		} catch (Exception e) {
			System.out.println("NotasClient failed." + e.getMessage());
			e.printStackTrace();
		}
	}

	private static Integer obtemPartida(GameInterface game, Integer id) throws RemoteException {
		int res = 0;
		while (res == 0) {
			res = game.temPartida(id);

			try {
				Thread.sleep(RETRY);
			} catch (InterruptedException e) {
			}
		}
		return res;
	}

	private static void executeGame(String server, String name) throws MalformedURLException, RemoteException, NotBoundException, InvalidPlayerNameException, PlayerLimitReachedException {
		GameInterface game = (GameInterface) Naming.lookup("//" + server + "/Notas");

		Integer id1 = game.registraJogador(name + "1");
		Integer id2 = game.registraJogador(name + "2");
		System.out.println(id1 + " " + id2);
		System.out.println(game.obtemGrade(id1));

		Integer resp1 = obtemPartida(game, id1);
		Integer resp2 = obtemPartida(game, id2);
		System.out.println(resp1 + " " + resp2);

		String name2 = game.obtemOponente(id1);
		String name1 = game.obtemOponente(id2);
		System.out.println(name1 + " vs " + name2);

		int res1 = game.enviaJogada(id1, 0);
		int res2 = game.enviaJogada(id2, 3);
		System.out.println(res1 + " " + res2);
		System.out.println(game.obtemGrade(id1));

		res1 = game.enviaJogada(id1, 1);
		res2 = game.enviaJogada(id2, 4);
		System.out.println(res1 + " " + res2);
		System.out.println(game.obtemGrade(id1));

		res1 = game.enviaJogada(id1, 2);
		res2 = game.enviaJogada(id2, 5);
		System.out.println(res1 + " " + res2);
		System.out.println(game.obtemGrade(id1));

	}
}

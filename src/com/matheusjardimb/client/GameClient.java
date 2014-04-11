package com.matheusjardimb.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.matheusjardimb.Match;
import com.matheusjardimb.exception.InvalidPlayerNameException;
import com.matheusjardimb.exception.PlayerLimitReachedException;

public class GameClient {
	private static final int RETRY = 2 * 1000; // 2sec

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Uso: java NotasClient <maquina>");
			System.exit(1);
		}

		String server = args[0];

		try {
			executeGame(server);
		} catch (Exception e) {
			System.err.println("<<ERRO>> " + e.getMessage());
		}
	}

	private static Integer obtemPartida(GameInterface game, Integer id) throws RemoteException {
		int res = Match.NO_MATCH;
		while (res == Match.NO_MATCH) {
			System.out.println("...Buscando por um oponente...");
			res = game.temPartida(id);

			try {
				Thread.sleep(RETRY);
			} catch (InterruptedException e) {
			}
		}
		return res;
	}

	/**
	 * TODO
	 * 
	 * 1.Destruir a partida se ambos os usuarios conhecem o resultado, mesmo sem
	 * ter acabado o prazo
	 * 
	 * 2. JavaDoc
	 */

	/**
	 * @throws IOException
	 * @throws NotBoundException
	 * @throws InvalidPlayerNameException
	 * @throws PlayerLimitReachedException
	 * @throws IOException
	 * */
	private static void executeGame(String server) throws NotBoundException, InvalidPlayerNameException, PlayerLimitReachedException, IOException {
		GameInterface game = (GameInterface) Naming.lookup("//" + server + "/Notas");

		System.out.println("Digite seu nome: ");
		String name = readString();
		
		Integer id = game.registraJogador(name);
		System.out.println("Olá, " + name + ", seu id é " + id);

		Integer resp1 = obtemPartida(game, id);
		System.out.println("Oponente encontrado!");

		String opponent = game.obtemOponente(id);
		System.out.println("Seu oponente é: " + opponent);
		displayGameStatus(id, game);

		int res1 = game.enviaJogada(id, readPosition());
		System.out.println(game.obtemGrade(id));
	}

	private static void displayGameStatus(Integer id, GameInterface game) throws RemoteException {
		System.out.println("Estado atual do jogo:\n" + game.obtemGrade(id));
	}

	private static String readString() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

	private static int readPosition() {
		Scanner in = new Scanner(System.in);
		int i = -1;
		while (i < 0 && i > 8) {
			System.err.println("Digite um número entre 0 e 8");
			i = in.nextInt();
		}
		return i;
	}
}

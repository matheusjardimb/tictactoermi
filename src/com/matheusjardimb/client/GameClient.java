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

			sleep();
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
	 * 
	 * 3. Sysout no server
	 * 
	 * 4. Id randomico
	 */

	private static void sleep() {
		try {
			Thread.sleep(RETRY);
		} catch (InterruptedException e) {
		}
	}

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

		obtemPartida(game, id);
		System.out.println("Oponente encontrado!");

		String opponent = game.obtemOponente(id);
		System.out.println("Seu oponente é: " + opponent);

		playMatch(game, id);
	}

	private static void playMatch(GameInterface game, Integer id) throws RemoteException {
		int res1 = Match.OK;
		while (res1 != Match.ERROR && res1 != Match.TIMEOUT) {
			int res = waitMyTurn(game, id);
			displayGameStatus(id, game);

			if (res == Match.LOOSER) {
				System.out.println("Partida encerrada - você PERDEU!");
				return;
			}

			if (res == Match.WINNER) {
				System.out.println("Partida encerrada - você VENCEU!");
				return;
			}

			if (res == Match.DRAW) {
				System.out.println("Partida encerrada - EMPATOU!");
				return;
			}

			do {
				res1 = game.enviaJogada(id, readPosition());
				if (res1 == Match.OCCUPIED_POSITION) {
					System.out.println("Posição ocupada, tente outra");
				}
			} while (res1 == Match.OCCUPIED_POSITION);
			displayGameStatus(id, game);
		}
		displayGameStatus(id, game);
	}

	private static int waitMyTurn(GameInterface game, Integer id) throws RemoteException {
		int res = Match.NO;

		while (res == Match.NO) {
			res = game.ehMinhaVez(id);
			sleep();
			System.out.println("Esperando ser sua vez");
		}
		return res;
	}

	private static void displayGameStatus(Integer id, GameInterface game) throws RemoteException {
		System.out.println("Estado atual do jogo:\n" + game.obtemGrade(id));
	}

	private static String readString() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}

	private static int readPosition() {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int i = 0;
		do {
			System.out.println("Digite um número entre 0 e 8");
			i = in.nextInt();
		} while (i < 0 || i > 8);
		return i;
	}
}

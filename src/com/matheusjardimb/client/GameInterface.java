package com.matheusjardimb.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.matheusjardimb.exception.InvalidPlayerNameException;
import com.matheusjardimb.exception.PlayerLimitReachedException;

public interface GameInterface extends Remote {
	/**
	 * 1) registraJogador Recebe: string com o nome do usuário/jogador Retorna:
	 * id (valor inteiro) do usuário (que corresponde a um número de
	 * identificação único para este usuário durante uma partida), -1 se este
	 * usuário já está cadastrado ou -2 se o número máximo de jogadores tiver
	 * sido atingido
	 * 
	 * @throws RemoteException
	 * @throws InvalidPlayerNameException 
	 * @throws PlayerLimitReachedException 
	 */
	public Integer registraJogador(String name) throws RemoteException, InvalidPlayerNameException, PlayerLimitReachedException;
	
	/** 
	 * 2) temPartida Recebe: id do usuário (obtido através da chamada
	 * registraJogador) Retorna: -2 (tempo de espera esgotado), -1 (erro), 0
	 * (ainda não há partida), 1 (sim, há partida e o jogador inicia jogando com
	 * “X”) ou 2 (sim, há partida e o jogador é o segundo a jogar, usando “O”)
	  */
	public Integer temPartida(Integer id) throws RemoteException;
	
	/** 
	 * 3) ehMinhaVez Recebe: id do usuário (obtido através da chamada
	 * registraJogador) Retorna: -1 (erro), 0 (não), 1 (sim), 2 (é o vencedor),
	 * 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por
	 * WO) Observação: se ainda não houver 2 jogadores registrados na partida,
	 * esta chamada retorna o código de erro
	 */
	public Integer ehMinhaVez(Integer id) throws RemoteException;
	
	/** 
	 * 4) obtemGrade Recebe: id do usuário (obtido através da chamada
	 * registraJogador) Retorna: string vazio em caso de erro ou string com a
	 * grade de jogo
	   */
	public String obtemGrade(Integer id) throws RemoteException;
	
	/**
	 * 5) enviaJogada Recebe: id do usuário (obtido através da chamada
	 * registraJogador) e jogada (valor inteiro de 0 a 8, correspondendo à
	 * posição da jogada na grade) Retorna: 2 (partida encerrada, o que ocorrerá
	 * caso o jogador demore muito para enviar a sua jogada e ocorra o time-out
	 * de 30 segundos para envio de jogadas), 1 (tudo certo), 0 (posição
	 * ocupada) ou -1 (erro)
	   */
	public Integer enviaJogada(Integer id, Integer jogada) throws RemoteException;
	
	/**
	 * 6) obtemOponente Recebe: id do usuário (obtido através da chamada
	 * registraJogador) Retorna: string vazio para erro ou string com o nome do
	 * oponent
	 */
	public String obtemOponente(Integer id) throws RemoteException;
}

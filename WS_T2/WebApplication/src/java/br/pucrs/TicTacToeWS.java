package br.pucrs;

import com.matheusjardimb.Match;
import com.matheusjardimb.PlayerFactory;
import com.matheusjardimb.PlayerFactory.Player;
import com.matheusjardimb.client.GameClient;
import com.matheusjardimb.server.Game;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "TicTacToeWS")
public class TicTacToeWS {
    
    /*  operação 0 – preRegistro (usada para viabilizar o teste): 
    informa ao servidor o nome de usuário e o respectivo identificador que o servidor deverá utilizar para este usuário;
    */
    @WebMethod(operationName = "preRegistro")
    public String preRegistro(@WebParam(name = "name") String name, @WebParam(name = "id") Integer id) {
        System.out.println("preRegistro");
        PlayerFactory.preRegister(name, id);
        return "";
    }
    
    /*  operação 1 – registraJogador (relacionada ao jogo propriamente dito): recebe o nome do usuário 
e retorna como resposta o identificador (valor inteiro) que identifica este usuário de forma única 
no sistema (este identificador será utilizado nas chamadas subsequentes);
    */
    @WebMethod(operationName = "registraJogador")
    public Integer registraJogador(@WebParam(name = "name") String name) {
        System.out.println("registraJogador");
        Player player = PlayerFactory.registerPlayer(name);
        if (player == null){
            return Match.ERROR;
        }
        int id  = player.getId();
        PlayerFactory.assignToMatch(id);
        return id;
    }
    
    /*operação 2 – temPartida (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna um valor inteiro que pode ser -2 (tempo de espera esgotado), -1 (erro), 0 
(ainda não há partida), 1 (sim, há partida e o jogador inicia jogando com “X”) ou 2 (sim, há 
partida e o jogador é o segundo a jogar, usando “O”); */
    @WebMethod(operationName = "temPartida")
    public Integer temPartida(@WebParam(name = "id") Integer id) {
        System.out.println("temPartida");
        return PlayerFactory.assignToMatch(id);
    }
    
    /* operação 3 – ehMinhaVez (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna um valor inteiro que pode ser -1 (erro), 0 (não), 1 (sim), 2 (é o vencedor), 3 (é 
o perdedor) ou 4 (houve empate); */
    @WebMethod(operationName = "ehMinhaVez")
    public Integer ehMinhaVez(@WebParam(name = "id") Integer id) {
        System.out.println("ehMinhaVez");
        return PlayerFactory.isPlayerTurn(id);
    }
    
    /*  operação 4 – obtemGrade (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna uma cadeia de caracteres (String) vazia (em caso de erro) ou uma cadeia de 
caracteres com a representação da grade de jogo apresentada de forma linear, sem grade 
(conteúdo das posições de 0 até 8, sendo que usa-se: “.” para posição vazia, “X” para jogada do 
jogador 1 e “O” para jogada do jogador 2); */
    @WebMethod(operationName = "obtemGrade")
    public String obtemGrade(@WebParam(name = "id") Integer id) {
        System.out.println("obtemGrade");
        return PlayerFactory.getBoard(id);
    }
    
    /* operação 5 – enviaJogada (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e a jogada (valor inteiro de 0 a 8, correspondendo à posição da jogada na grade), 
retornando um valor inteiro que pode ser 2 (partida encerrada, o que ocorreria caso o jogador 
demorasse muito para enviar a sua jogada, o que não será testado nesta implementação), 1 (tudo 
certo), 0 (posição ocupada) ou -1 (erro); */
    @WebMethod(operationName = "enviaJogada")
    public Integer enviaJogada(@WebParam(name = "id") Integer id, @WebParam(name = "jogada") Integer jogada) {
        System.out.println("enviaJogada");
        return PlayerFactory.setPosition(id, jogada);
    }
    
    /* operação 6 – obtemOponente (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna uma cadeia de caracteres (String) vazia (em caso de erro) ou uma cadeia de 
caracteres com o nome do oponente. */
    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") Integer id) {
        System.out.println("obtemOponente");
        return PlayerFactory.getOpponent(id);
    }    
}

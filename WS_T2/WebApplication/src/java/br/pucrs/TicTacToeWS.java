package br.pucrs;

import com.matheusjardimb.client.GameClient;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "TicTacToeWS")
public class TicTacToeWS {
    
    /*  operação 0 – preRegistro (usada para viabilizar o teste): 
    informa ao servidor o nome de usuário e o respectivo identificador que o servidor deverá utilizar para este usuário;
    */
    @WebMethod(operationName = "preRegistro")
    public String preRegistro(@WebParam(name = "name") String txt, @WebParam(name = "id") Integer id) {
        return "Hello ";
    }
    
    /*  operação 1 – registraJogador (relacionada ao jogo propriamente dito): recebe o nome do usuário 
e retorna como resposta o identificador (valor inteiro) que identifica este usuário de forma única 
no sistema (este identificador será utilizado nas chamadas subsequentes);
    */
    @WebMethod(operationName = "registraJogador")
    public Integer registraJogador(@WebParam(name = "name") String txt) {
        return 0;
    }
    
    /*operação 2 – temPartida (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna um valor inteiro que pode ser -2 (tempo de espera esgotado), -1 (erro), 0 
(ainda não há partida), 1 (sim, há partida e o jogador inicia jogando com “X”) ou 2 (sim, há 
partida e o jogador é o segundo a jogar, usando “O”); */
    @WebMethod(operationName = "temPartida")
    public Integer temPartida(@WebParam(name = "id") Integer id) {
        return 0;
    }
    
    /* operação 3 – ehMinhaVez (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna um valor inteiro que pode ser -1 (erro), 0 (não), 1 (sim), 2 (é o vencedor), 3 (é 
o perdedor) ou 4 (houve empate); */
    @WebMethod(operationName = "ehMinhaVez")
    public Integer ehMinhaVez(@WebParam(name = "id") Integer id) {
        return 0;
    }
    
    /*  operação 4 – obtemGrade (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna uma cadeia de caracteres (String) vazia (em caso de erro) ou uma cadeia de 
caracteres com a representação da grade de jogo apresentada de forma linear, sem grade 
(conteúdo das posições de 0 até 8, sendo que usa-se: “.” para posição vazia, “X” para jogada do 
jogador 1 e “O” para jogada do jogador 2); */
    @WebMethod(operationName = "obtemGrade")
    public String obtemGrade(@WebParam(name = "id") Integer id) {
        return "Hello " ;
    }
    
    /* operação 5 – enviaJogada (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e a jogada (valor inteiro de 0 a 8, correspondendo à posição da jogada na grade), 
retornando um valor inteiro que pode ser 2 (partida encerrada, o que ocorreria caso o jogador 
demorasse muito para enviar a sua jogada, o que não será testado nesta implementação), 1 (tudo 
certo), 0 (posição ocupada) ou -1 (erro); */
    @WebMethod(operationName = "enviaJogada")
    public Integer enviaJogada(@WebParam(name = "id") Integer id, @WebParam(name = "jogada") Integer jogada) {
        return 0;
    }
    
    /* operação 6 – obtemOponente (relacionada ao jogo propriamente dito): recebe o identificador do 
usuário e retorna uma cadeia de caracteres (String) vazia (em caso de erro) ou uma cadeia de 
caracteres com o nome do oponente. */
    @WebMethod(operationName = "obtemOponente")
    public String obtemOponente(@WebParam(name = "id") Integer id) {
        return "Hello ";
    }    
}

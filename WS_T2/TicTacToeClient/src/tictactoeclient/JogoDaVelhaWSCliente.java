package tictactoeclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author roland
 */
public class JogoDaVelhaWSCliente {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
        public static void main(String[] args) throws FileNotFoundException {
        
        // Apenas para execução dentro do NetBeans
        FileInputStream is = new FileInputStream(new File("teste-001.in"));
        System.setIn(is);
        
        Scanner leitura = new Scanner(System.in);
        int numOp = leitura.nextInt();
        for (int i=0;i<numOp;++i) {
            int op = leitura.nextInt();
            String parametros = leitura.next();
            String param[] = parametros.split(":",-1);
            switch(op) {
                case 0:  // preRegistro
                    if (param.length!=2)
                        erro(op+1);
                    else
                        System.out.println(preRegistro(param[0],Integer.parseInt(param[1])));
                    break;
                case 1: // registraJogador
                    if (param.length!=1)
                        erro(op+1);
                    else
                        System.out.println(registraJogador(param[0]));
                    break;
                case 2:  // temPartida
                    if (param.length!=1)
                        erro(op+1);
                    else
                        System.out.println(temPartida(Integer.parseInt(param[0])));
                    break;
                case 3: // ehMinhaVez
                    if (param.length!=1)
                        erro(op+1);
                    else
                        System.out.println(ehMinhaVez(Integer.parseInt(param[0])));                        
                    break;
                case 4: // obtemGrade
                    if (param.length!=1)
                        erro(op+1);
                    else
                        System.out.println(obtemGrade(Integer.parseInt(param[0])));                        
                    break;
                case 5: // enviaJogada
                    if (param.length!=2)
                        erro(op+1);
                    else
                        System.out.println(enviaJogada(Integer.parseInt(param[0]),Integer.parseInt(param[1])));
                    break;
                case 6:  // obtemOponente
                    if (param.length!=1)
                        erro(op+1);
                    else
                        System.out.println(obtemOponente(Integer.parseInt(param[0])));
                    break;
                default:
                    erro(op+1);
            }
        }
    }

    private static void erro(int operacao) {
        System.err.println("Entrada invalida: erro na operacao "+operacao+".");
        System.exit(1);
    }
    
    private static int preRegistro(java.lang.String nome, int id) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.preRegistro(nome, id);
    }

    private static int registraJogador(java.lang.String nome) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.registraJogador(nome);
    }

    private static int ehMinhaVez(int id) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.ehMinhaVez(id);
    }

    private static int enviaJogada(int id, int jogada) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.enviaJogada(id, jogada);
    }

    private static String obtemGrade(int id) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.obtemGrade(id);
    }

    private static String obtemOponente(int id) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.obtemOponente(id);
    }

    private static int temPartida(int id) {
        jogodavelha.JogoDaVelhaWS_Service service = new jogodavelha.JogoDaVelhaWS_Service();
        jogodavelha.JogoDaVelhaWS port = service.getJogoDaVelhaWSPort();
        return port.temPartida(id);
    }
    
}
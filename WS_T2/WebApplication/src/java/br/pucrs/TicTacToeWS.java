/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.pucrs;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author matheusjardimb
 */
@WebService(serviceName = "TicTacToeWS")
public class TicTacToeWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hi")
    public String hi(@WebParam(name = "name") String txt) {
        return "Hi " + txt + " !";
    }
}

package com.matheusjardimb.server;

import java.rmi.Naming;
import java.rmi.RemoteException;


public class Server {
	private static final int PORT = 1099;

	public static void main (String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(PORT);
			System.out.println("RMI registry ready.");			
		} catch (RemoteException e) {
			System.err.println("RMI registry already running.");			
		}
		try {
			Naming.rebind ("Notas", new Game ());
			System.out.println ("NotasServer is ready.");
		} catch (Exception e) {
			System.err.println ("NotasServer failed:");
			e.printStackTrace();
		}
	}	
}
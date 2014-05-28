package com.matheusjardimb.exception;

public class InvalidPlayerNameException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public InvalidPlayerNameException(String string) {
		super(string);
	}
}

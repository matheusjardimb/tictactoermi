package com.matheusjardimb.exception;

public class PlayerLimitReachedException extends Exception {
	private static final long serialVersionUID = 3727375789875108843L;
	
	public PlayerLimitReachedException(String string) {
		super(string);
	}
}

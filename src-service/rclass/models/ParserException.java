package rclass.models;

public class ParserException extends Exception {

	public ParserException(Exception e) {

		super(e);
	}

	public ParserException(String message) {

		super(message);
	}

}

package edu.iup.cosc424.lexicalAnalyzer.bo;

/**
 *  This class is designed to construct a token object for the Lexical Analyzer
 *  
 * @author Eric Olechovski & Kyle Wilson
 *
 */
public class Token {
	public int type;
	public int value;

	public Token(int type, int value) {
		super();
		this.type = type;
		this.value = value;
	}

	public Token(int value) {
		super();
		this.value = value;
	}


}

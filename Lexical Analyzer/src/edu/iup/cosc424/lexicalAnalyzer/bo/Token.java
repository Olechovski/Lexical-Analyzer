package edu.iup.cosc424.lexicalAnalyzer.bo;

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

package edu.iup.cosc424.lexicalAnalyzer.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.iup.cosc424.lexicalAnalyzer.bo.CONSTANT;
import edu.iup.cosc424.lexicalAnalyzer.bo.SymbolTable;
import edu.iup.cosc424.lexicalAnalyzer.bo.Token;

public class LexicalAnalyzerReader {

	private BufferedReader in;
	private int value;
	private SymbolTable st = new SymbolTable();

	public LexicalAnalyzerReader(String fileName) throws FileNotFoundException {
		in = new BufferedReader(new FileReader(fileName));
	}

	public Token readToken() throws IOException {

		String lexeme = "";
		char character;

		int state = 0;

		while (true) {

			switch (state) {

			case 0:
				character = (char) in.read();
				if (isLetter(character)) {
					lexeme = lexeme + character;
					state = 1;
				} else if (isDigit(character)) {
					lexeme = lexeme + character;
					state = 3;
				} else if (character == '|') {
					state = 5;
				} else if (character == '&') {
					state = 7;
				} else if (character == ';') {
					state = 9;
				} else if (character == ',') {
					state = 10;
				} else if (character == '+') {
					state = 11;
				} else if (character == '-') {
					state = 12;
				} else if (character == '*') {
					state = 13;
				} else if (character == '/') {
					state = 14;
				} else if (character == '(') {
					state = 15;
				} else if (character == ')') {
					state = 16;
				} else if (character == '{') {
					state = 17;
				} else if (character == '}') {
					state = 18;
				} else if (character == '%') {
					state = 19;
				} else if (character == '<') {
					state = 20;
				} else if (character == '>') {
					state = 23;
				} else if (character == '=') {
					state = 26;
				} else if (character == '!') {
					state = 29;
				}

				break;
			// Identifier
			case 1:
				in.mark(2);
				character = (char) in.read();
				if (isLetter(character) || isDigit(character)) {
					lexeme = lexeme + character;
					state = 1;
				} else {
					state = 2;
				}
				break;
			case 2:
				retract();
				if (isKeyword(lexeme)) {
					return (new Token(CONSTANT.KEYWORD, value));
				}
				return (new Token(CONSTANT.ID, st.installID(lexeme)));

			// Number
			case 3:
				in.mark(2);
				character = (char) in.read();
				if (isDigit(character)) {
					lexeme = lexeme + character;
					state = 3;
				} else {
					state = 4;
				}
				break;
			case 4:
				retract();
				return (new Token(CONSTANT.NUM, numValue(lexeme)));
			case 5:
				character = (char) in.read();
				if (character == '|') {
					state = 6;
				}
				break;
			case 6:
				return (new Token(CONSTANT.ADDOP, CONSTANT.OR));

			case 7:
				character = (char) in.read();
				if (character == '&') {
					state = 8;
				}
				break;
			case 8:
				return (new Token(CONSTANT.ADDOP, CONSTANT.AND));
			case 9:
				return (new Token(CONSTANT.SEMICOLON));
			case 10:
				return (new Token(CONSTANT.COMMA));
			case 11:
				return (new Token(CONSTANT.ADD));
			case 12:
				return (new Token(CONSTANT.SUB));
			case 13:
				return (new Token(CONSTANT.MUL));
			case 14:
				return (new Token(CONSTANT.DIV));
			case 15:
				return (new Token(CONSTANT.LPAREN));
			case 16:
				return (new Token(CONSTANT.RPAREN));
			case 17:
				return (new Token(CONSTANT.LBRACE));
			case 18:
				return (new Token(CONSTANT.RBRACE));
			case 19:
				return (new Token(CONSTANT.MOD));
			case 20:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 21;
				} else {
					state = 22;
				}
				break;

			case 21:
				return (new Token(CONSTANT.RELOP, CONSTANT.LE));
			case 22:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.LT));
			case 23:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 24;
				} else {
					state = 25;
				}
				break;
			case 24:
				return (new Token(CONSTANT.RELOP, CONSTANT.GE));
			case 25:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.GT));
			case 26:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 27;
				} else {
					state = 28;
				}
				break;
			case 27:
				return (new Token(CONSTANT.RELOP, CONSTANT.EE));
			case 28:
				retract();
				return (new Token(CONSTANT.ASSIGNOP, CONSTANT.EQ));
			case 29:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 30;
				} else {
					state = 31;
				}
				break;
			case 30:
				return (new Token(CONSTANT.RELOP, CONSTANT.NE));
			case 31:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.EX));
			default:
				System.err.println("Syntax Error");
				System.exit(-99);
				break;
			}

		}

	}

	private int numValue(String lexeme) {
		return Integer.parseInt(lexeme);
	}

	public void retract() {
		try {
			in.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isLetter(char character) {

		if (64 < character && character < 91) {
			return true;
		} else if (96 < character && character < 123) {
			return true;
		}

		return false;

	}

	public boolean isDigit(char character) {
		if (47 < character && character < 58) {
			return true;
		}
		return false;
	}

	public void close() throws IOException {
		in.close();
	}

	public boolean isKeyword(String lexeme) {
		if (lexeme.equals("void")) {
			value = CONSTANT.VOID;
		} else if (lexeme.equals("int")) {
			value = CONSTANT.INT;
		} else if (lexeme.equals("double")) {
			value = CONSTANT.DOUBLE;
		} else if (lexeme.equals("char")) {
			value = CONSTANT.CHAR;
		} else if (lexeme.equals("if")) {
			value = CONSTANT.IF;
		} else if (lexeme.equals("else")) {
			value = CONSTANT.ELSE;
		} else if (lexeme.equals("while")) {
			value = CONSTANT.WHILE;
		} else {
			return false;
		}

		return true;
	}

}

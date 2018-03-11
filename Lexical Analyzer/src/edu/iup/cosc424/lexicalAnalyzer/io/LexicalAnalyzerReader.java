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

	public LexicalAnalyzerReader(String fileName) throws FileNotFoundException {
		in = new BufferedReader(new FileReader(fileName));
	}

	public Token readToken() throws IOException {

		SymbolTable st = new SymbolTable();

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
					state = 4;
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
				character = (char) in.read();
				in.mark(2);
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
				// Number
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
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
				character = (char) in.read();
				if (character == '=') {
					state = 24;
				} else {
					state = 25;
				}
			case 24:
				return (new Token(CONSTANT.RELOP, CONSTANT.GE));
			case 25:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.GT));
			case 26:
				character = (char) in.read();
				if (character == '=') {
					state = 27;
				} else {
					state = 28;
				}
			case 27:
				return (new Token(CONSTANT.RELOP, CONSTANT.EE));
			case 28:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.EQ));
			case 29:
				character = (char) in.read();
				if (character == '=') {
					state = 30;
				} else {
					state = 31;
				}
			case 30:
				return (new Token(CONSTANT.RELOP, CONSTANT.NE));
			case 31:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.EX));
			default:
				break;
			}

		}

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

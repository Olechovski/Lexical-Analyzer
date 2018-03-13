package edu.iup.cosc424.lexicalAnalyzer.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.iup.cosc424.lexicalAnalyzer.bo.CONSTANT;
import edu.iup.cosc424.lexicalAnalyzer.bo.SymbolTable;
import edu.iup.cosc424.lexicalAnalyzer.bo.Token;

/**
 * This class is designed to read the file and perform lexical analysis that
 * outputs a token as a result
 * 
 * @author Eric Olechovski & Kyle Wilson
 *
 */
public class LexicalAnalyzerReader {

	private BufferedReader in;
	private int value;
	private SymbolTable st = new SymbolTable();

	/**
	 * Construct the Lexical Analyzer reader by initializing the buffer reader
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public LexicalAnalyzerReader(String fileName) throws FileNotFoundException {
		in = new BufferedReader(new FileReader(fileName));
	}

	/**
	 * Responsible for creating a token based on the constructed lexeme
	 * 
	 * @return A token object
	 * @throws IOException
	 */
	public Token readToken() throws IOException {

		String lexeme = "";
		char character;

		int state = 0;

		while (in.ready()) {

			switch (state) {

			// Base case
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
					state = 21;
				} else if (character == ')') {
					state = 22;
				} else if (character == '{') {
					state = 23;
				} else if (character == '}') {
					state = 24;
				} else if (character == '%') {
					state = 25;
				} else if (character == '<') {
					state = 26;
				} else if (character == '>') {
					state = 29;
				} else if (character == '=') {
					state = 32;
				} else if (character == '!') {
					state = 35;
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

			// Arithmetic Operators
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

			// Arithmetic Operators
			case 11:
				return (new Token(CONSTANT.ADD));
			case 12:
				return (new Token(CONSTANT.SUB));
			case 13:
				return (new Token(CONSTANT.MUL));
			case 14:
				in.mark(2);
				character = (char) in.read();
				if(character == '*') {
					state = 15;
					break;
				}
			case 15:
				character = (char) in.read();
				if(character == '*') {
					state = 16;
				} else {
					lexeme += character;
				}
				break; 
				
			case 16:
				character = (char) in.read();
				if(character == '/') {
					state = 17;
				} else {
					lexeme += character;
					state = 15;
				}
				break;
				
			case 17:
				return (new Token(CONSTANT.SLASH_STAR_COMMENT, st.installID(lexeme)));
				
			case 18:
				
			case 19:
			
			case 20:
				return (new Token(CONSTANT.MULTOP, CONSTANT.DIV));
			// Control Structures
			case 21:
				return (new Token(CONSTANT.LPAREN));
			case 22:
				return (new Token(CONSTANT.RPAREN));
			case 23:
				return (new Token(CONSTANT.LBRACE));
			case 24:
				return (new Token(CONSTANT.RBRACE));

			// Arithmetic Operator
			case 25:
				return (new Token(CONSTANT.MOD));

			// Relational Operators
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
				return (new Token(CONSTANT.RELOP, CONSTANT.LE));
			case 28:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.LT));
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
				return (new Token(CONSTANT.RELOP, CONSTANT.GE));
			case 31:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.GT));
			case 32:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 33;
				} else {
					state = 34;
				}
				break;
			case 33:
				return (new Token(CONSTANT.RELOP, CONSTANT.EE));
			case 34:
				retract();
				return (new Token(CONSTANT.ASSIGNOP, CONSTANT.EQ));
			case 35:
				in.mark(2);
				character = (char) in.read();
				if (character == '=') {
					state = 36;
				} else {
					state = 37;
				}
				break;
			case 36:
				return (new Token(CONSTANT.RELOP, CONSTANT.NE));
			case 37:
				retract();
				return (new Token(CONSTANT.RELOP, CONSTANT.NOT));

			default:
				System.err.println("Syntax Error");
				System.exit(-99);
				break;
			}

		}

		// file is empty
		return null;
	}

	/**
	 * lexeme is a string that represents a number and is then parsed into an
	 * integer value
	 * 
	 * @param lexeme
	 * @return integer value
	 */
	private int numValue(String lexeme) {
		return Integer.parseInt(lexeme);
	}

	/**
	 * After reaching the end of a lexeme the 'lookahead' will recede one position
	 */
	public void retract() {
		try {
			in.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks to see if a lexeme/character is a letter [a-z A-Z]
	 * 
	 * @param character
	 * @return True or False
	 */
	public boolean isLetter(char character) {

		if (64 < character && character < 91) {
			return true;
		} else if (96 < character && character < 123) {
			return true;
		}

		return false;

	}

	/**
	 * Checks to see if a lexeme/character is a digit [0-9]
	 * 
	 * @param character
	 * @return True or False
	 */
	public boolean isDigit(char character) {
		if (47 < character && character < 58) {
			return true;
		}
		return false;
	}

	public void close() throws IOException {
		in.close();
	}

	/**
	 * Ensures that an potential identifier is not a keyword
	 * 
	 * @param lexeme
	 * @return True or False
	 */
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

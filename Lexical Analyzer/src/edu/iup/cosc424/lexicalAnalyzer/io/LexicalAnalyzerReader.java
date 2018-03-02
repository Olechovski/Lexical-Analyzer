package edu.iup.cosc424.lexicalAnalyzer.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.iup.cosc424.lexicalAnalyzer.bo.Token;

public class LexicalAnalyzerReader {
	
	private BufferedReader in;
	
	public LexicalAnalyzerReader(String fileName) throws FileNotFoundException {
		in = new BufferedReader(new FileReader(fileName));
	}

	public Token readToken() throws IOException {
		
		
		String str = "";
		char character;
		
		int state = 0;
		
		
		while(true){
			
			switch(state){
			
			case 0 : 
					character = (char) in.read(); 
					if (isLetter(character)){
						str = str + character;
						state = 1;
					}
					else if (isDigit(character)){
						str = str + character;
						state = 4;
					}
					else if ( character == '|'){
						state = 6;
					}
					else if ( character == '&'){
						state = 8;
					}
					else if ( character == ';'){
						state = 10;
					}
					else if ( character == ','){
						state = 11;
					}
					else if ( character == '+'){
						state = 12;
					}
					else if ( character == '-'){
						state = 13;
					}
					else if ( character == '*'){
						state = 14;
					}
					else if ( character == '/'){
						state = 15;
					}
					else if ( character == '('){
						state = 16;
					}
					else if ( character == ')'){
						state = 17;
					}
					else if ( character == '{'){
						state = 18;
					}
					else if ( character == '}'){
						state = 19;
					}
					else if ( character == '%'){
						state = 20;
					}
					else if ( character == '<'){
						state = 21;
					}
					else if ( character == '>'){
						state = 24;
					}
					else if ( character == '='){
						state = 24;
					}
					else if ( character == '!'){
						state = 30;
					}
					
					break;
					
			case 1 : 
					character = (char) in.read(); 
					if ( isLetter(character) || isDigit(character) ){
						str = str + character;
						state = 1;
					}
						state = 2;
						break;
					
			case 2 : 
					retract();
					installID();
					
			case 3 : 
			case 4 : 
			case 5 : 
			case 6 : 
			case 7 : 
			case 8 : 
			case 9 : 
			case 10 :
			case 11 : 
			case 12 : 
			case 13 : 
			case 14 : 
			case 15 :
			case 16 : 
			case 17 : 
			case 18 : 
			case 19 : 
			case 20 :
			case 21 : 
			case 22 : 
			case 23 : 
			case 24 : 
			case 25 :
			case 26 : 
			case 27 : 
			case 28 :
			case 29 : 
			case 30 : 
			case 31 : 
 
			}
		}
		
//		String id;
//		int value;
//
//		return new Token(id, value);
		return null;
		
	}
	
	public boolean isLetter( char character ){
		
		if ( 65 < character && character < 90){
			return true;
		}
		else if ( 97 < character && character < 122){
			return true;
		}
	
		return false;

	}
	
	public boolean isDigit( char character ){
		if ( 47 < character && character < 58){
			return true;
		}
		return false;
	}
	
	public void close() throws IOException {
		in.close();
	}
	

}

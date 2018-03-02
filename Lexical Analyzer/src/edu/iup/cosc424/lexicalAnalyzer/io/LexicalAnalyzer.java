package edu.iup.cosc424.lexicalAnalyzer.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.iup.cosc424.lexicalAnalyzer.bo.SymbolTable;
import edu.iup.cosc424.lexicalAnalyzer.bo.Token;

public class LexicalAnalyzer {

	public static void main (String [] args) throws IOException{

		if (args.length < 1) {
			System.out.println("File not found");
			System.exit(-99);
		}

		try {
			loadLexicalAnalyzer(args[0]);

		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	private static void loadLexicalAnalyzer(String fileName) throws IOException {
		LexicalAnalyzerReader reader = new LexicalAnalyzerReader(fileName);
		SymbolTable symbolTable = new SymbolTable();

		Token t;

		while ((t = reader.readToken()) != null) {
			// print token here
		}

	}


}

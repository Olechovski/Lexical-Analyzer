package edu.iup.cosc424.lexicalAnalyzer.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import edu.iup.cosc424.lexicalAnalyzer.bo.SymbolTable;
import edu.iup.cosc424.lexicalAnalyzer.bo.Token;

/**
 * This class is designed to open a file for lexical analysis
 * and to display the resulting tokens
 * 
 * @author Eric Olechovski & Kyle Wilson
 *
 */
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
			e.printStackTrace();
		} 

	}

	/** Prepares file to be read
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	private static void loadLexicalAnalyzer(String fileName) throws IOException {
		LexicalAnalyzerReader reader = new LexicalAnalyzerReader(fileName);
		Token t;

		while ((t = reader.readToken()) != null) {
			// print token here
			if (t.type == 0){
				System.out.println("<" + t.value + ">");
			}
			else{
				System.out.println("<" + t.type + "," + t.value + ">");
			}
		}

	}


}

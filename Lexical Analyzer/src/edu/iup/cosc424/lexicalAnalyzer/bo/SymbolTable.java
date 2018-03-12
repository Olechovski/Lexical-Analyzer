package edu.iup.cosc424.lexicalAnalyzer.bo;

import edu.iup.cosc424.lexicalAnalyzer.util.HashTable;

public class SymbolTable  {

	// Symbol Table for identifiers
	public static HashTable<String,Integer> symbolTable = new HashTable<String,Integer>();
	
	/**
	 * Examines symbol table for lexeme; if not found, adds lexeme to the symbol table
	 * returns 0 
	 * @param idName
	 * @return index of lexeme in table
	 */
	public int installID(String id){
			symbolTable.put(id, CONSTANT.ID);
			return symbolTable.find(id);
	}
	
	
}

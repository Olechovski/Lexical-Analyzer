package edu.iup.cosc424.lexicalAnalyzer.bo;

import edu.iup.cosc424.lexicalAnalyzer.util.HashTable;

public class SymbolTable  {

	// Symbol Table for identifiers
	public static HashTable<Integer,Integer> symbolTable = new HashTable<Integer,Integer>();
	
	
	/**
	 * Examines symbol table for lexeme; if not found, adds lexeme to the symbol table
	 * returns 0 
	 * @param idName
	 * @return index of lexeme in table
	 */
	public int installID(String idName){
			int id = valueOf(idName);
			symbolTable.put(id, CONSTANT.ID);
			return symbolTable.find(id);
	}
	

	private int valueOf(String id) {
		String idASCII = "";
		for (int i = 0; i < id.length(); i++){
			idASCII = idASCII + (int)id.charAt(i);
	}
		return Integer.parseInt(idASCII);
	}
	
	
	
	
}

package edu.iup.cosc424.lexicalAnalyzer.bo;

import edu.iup.cosc424.lexicalAnalyzer.util.HashTable;

public class SymbolTable  {

	public static HashTable<Integer,Integer> symbolTable = new HashTable<Integer,Integer>();
	
	
	public int installID(String idName){
			int id = valueOf(idName);
			symbolTable.put(id, CONSTANT.ID);
			return symbolTable.find(id);
	}
	

	public int getToken(String idName){
		int id = valueOf(idName);
		return symbolTable.get(id) != null ? symbolTable.get(id) : -1;	
	}
	
	
	
	private int valueOf(String id) {
		String idASCII = "";
		for (int i = 0; i < id.length(); i++){
			idASCII = idASCII + (int)id.charAt(i);
	}
		return Integer.parseInt(idASCII);
	}
	
	
	
	
}

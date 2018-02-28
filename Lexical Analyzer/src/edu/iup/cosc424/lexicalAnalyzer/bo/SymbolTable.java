package edu.iup.cosc424.lexicalAnalyzer.bo;

import java.util.HashMap;

public class SymbolTable {

	public static HashMap<String, Integer> symbolTable = new HashMap<String, Integer>();
	
	public static void main (String []args){
		symbolTable.put("one", 1);
		symbolTable.put("one", 1);
		
		for(String key : symbolTable.keySet()){
			System.out.println(key);
		}
	}
	
	public void installID(Token t){
		// call getToken
		
		
	}
	
	
	
	public Token getToken(Token t){
		
		return null;
		
	}
	
	
	
	
	
}

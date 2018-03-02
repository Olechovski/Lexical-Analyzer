package edu.iup.cosc424.lexicalAnalyzer.bo;

import edu.iup.cosc424.lexicalAnalyzer.util.HashTable;

public class SymbolTable  {

	
	public static HashTable<Identifier,Integer> symbolTable = new HashTable<Identifier,Integer>();
	private static int assignedIndex = 700;
	
	public int installID(String idName){
		
		Identifier id = new Identifier(idName);
			symbolTable.put(id, assignedIndex);
			
	//		return "id's index (id's value)";
	}
	
	public int getToken(String id){
		return symbolTable.containsKey(id) ? symbolTable.get(id): null;	
	}
	
	
	
	
	/**	INNER CLASS **
	 * Used to construct identifier so it can be stored into the symbol table
	 *
	 */
	public class Identifier {
		private String name;
		private int value = CONSTANT.ID;
		
		public Identifier(String name) {
			super();
			this.name = name;
		}
		
	}
	
	
	
	
}

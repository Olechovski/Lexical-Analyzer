package edu.iup.cosc424.lexicalAnalyzer.util;

public class sample {

	
	public static void main (String [] args){
		
		String id = "abc";
		String str = "";
		for (int i = 0; i < id.length(); i++){
			str = str + (int)id.charAt(i);
		
	}
		System.out.println(str);
	}
}

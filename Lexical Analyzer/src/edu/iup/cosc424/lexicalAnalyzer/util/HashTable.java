package edu.iup.cosc424.lexicalAnalyzer.util;

	import java.util.Iterator;
	import java.util.NoSuchElementException;
	import java.util.Stack;

	/**
	 * Class creates a hash table in which it can reallocate if necessary 
	 * and items can be added to the table and removed from the table
	 * 
	 * @author Eric Olechovski & Kyle Wilson
	 *
	 * @param <K>
	 * @param <V>
	 */
	public class HashTable<K,V> implements Dictionary<K,V> {
		
		private Entry<K,V> [] table;
		private static final int START_CAPACITY = 51;
		private double LOAD_THRESHOLD = 0.50;
		private int numKeys;
		private int numDeletes;
		
		private final Entry<K,V> DELETED = null;
		
		public HashTable(){
			table = new Entry[START_CAPACITY];
		}

		public int find (K key){
			// calculate the starting index.
			int index = key.hashCode () % table.length;
			if (index < 0){
				index += table.length;
			}
			
			// Increment index until an empty slot is reached
			// or the key is found
			while ((table[index] != null) && (!key.equals(table[index].key))){
				index++;
				//check for wrap around
				if (index >= table.length){
					index = 0;		// wrap around
				}
			}
			return index;
		}
		
		
		
		private void rehash() {
			Entry<K,V>[] oldTable = table;
			// create new table with double the size of the old table
			table = new Entry[2 * oldTable.length + 1];
			
			
			// reinsert all the values from oldTable into the new table
			
			numKeys = 0;
			numDeletes = 0;
			for (int i = 0;i < oldTable.length;i++){
				if ((oldTable[i] != null) && (oldTable[i] != DELETED)){
					put(oldTable[i].key, oldTable[i].value);
				}
			}
			
		}
		
		
		@Override
		public V put(K key, V value) {
			int index = find(key);
			if(table[index] == null){
				table[index] = new Entry<K,V>(key,value);
				numKeys++;
				// Check if rehash is necessary
				double loadFactor = (double) (numKeys + numDeletes) / table.length;
				
				if (loadFactor > LOAD_THRESHOLD){
					rehash();
					return null;
				}
				
			}
			// replace value for existing key
			V oldValue = table[index].value;
			table[index].value = value;
			return oldValue;
		}



		@Override
		public V get(K key) {
			int index = find(key);
			
			// If the search is successful, return the value associated with the 
			// located key.
			
			
			if (table[index] != null){
				return table[index].value;
			}
			else
				return null;	// key not found

		}

		@Override
		public V remove(K key) {
			int index = find(key);
			if(table[index] != null){
				V oldValue = table[index].value;
				table[index] = DELETED;
				numKeys--;
				numDeletes++;
				return oldValue;
				
			}
			return null;	// key was not in table
		}
		
		

		@Override
		public Iterator<K> keys() {
			return new KeyIterator<K>(table);
		}

		@Override
		public boolean isEmpty() {
			if(numKeys == 0){
				return true;
			}
			return false;
		}

		@Override
		public int noKeys() {
			return numKeys;
		}
		
		
		
		// INNER CLASS Entry
		/**
		 * Contains the <key,value> pairs for a hash table
		 * 
		 * @param <K>
		 * @param <V>
		 */
		private static class Entry<K,V>{

			private K key;
			private V value;
			
			
			public Entry(K key, V value) {
				super();
				this.key = key;
				this.value = value;
			}
			

			
		}
		
		private class KeyIterator<K> implements Iterator<K>{
			
			Stack<K> position = new Stack<K>();
			
			public KeyIterator(Entry<K,V> [] table){
				pushTable();
			}
			

			
			private void pushTable() {
				for (int i = 0; i < table.length; i++){
					if (table[i] != null){
						position.push((K) table[i].key);
					}
				}
				
			}

			@Override
			public boolean hasNext() {
				return !position.isEmpty();
			}

			@Override
			public K next() {
				if (!hasNext() ) {
					throw new NoSuchElementException();
				}
				return position.pop();

			}
		
		}

	}

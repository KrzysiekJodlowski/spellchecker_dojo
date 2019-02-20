package com.codecool.data_structures;

import com.codecool.controller.tools.StringHasher;

/**
 *
 * ICS 23 Summer 2004
 * Project #5: Lost for Words
 *
 * Implement your hash table here.  You are required to use the separate
 * chaining strategy that we discussed in lecture, meaning that collisions
 * are resolved by having each cell in the table be a linked list of all of
 * the strings that hashed to that cell.
 */

public class HashTable
{
	private int tableSize;
	private StringHasher hasher;
	private SinglyLinkedList<HashNode>[] wordsTable;

	/**
   * The constructor is given a table size (i.e. how big to make the array)
   * and a StringHasher, which is used to hash the strings.
   *
   * @param tableSize number of elements in the hash array
   *        hasher    Object that creates the hash code for a string
   * @see StringHasher
   */
	public HashTable(int tableSize, StringHasher hasher)
	{
		this.tableSize = tableSize;
		this.hasher = hasher;
		this.wordsTable = new SinglyLinkedList[this.tableSize];
	}


	/**
   * Takes a string and adds it to the hash table, if it's not already
   * in the hash table.  If it is, this method has no effect.
   *
   * @param s String to add
   */
	public void add(String s)
	{

		int generatedKey = hasher.hash(s);

		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<HashNode> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).getValue().equals(s)) {
					return;
				}
				HashNode newNode = new HashNode<>(generatedKey, s);
				curentList.add(newNode);
				resizeIfNeeded();
				return;
			}

		} else {
			wordsTable[generatedKey] = new SinglyLinkedList<>();
			HashNode newNode = new HashNode<>(generatedKey, s);
			wordsTable[generatedKey].add(newNode);
			resizeIfNeeded();
		}
	}
	

	/**
  * Takes a string and returns true if that string appears in the
	* hash table, false otherwise.
  *
  * @param s String to look up
  */
	public boolean lookup(String s)
	{
		int generatedKey = hasher.hash(s);
		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<HashNode> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).getValue().equals(s)) {
					return true;
				}
			}

		}
		return false;
	}
	

	/**
   * Takes a string and removes it from the hash table, if it
   * appears in the hash table.  If it doesn't, this method has no effect.
   *
   * @param s String to remove
  */
	public void remove(String s)
	{
		int generatedKey = hasher.hash(s);
		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<HashNode> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).getValue().equals(s)) {
					curentList.remove(listIndex);
				}
			}

		}
	}

	/*
	 *	 Need to be upgraded to use "better" hashing
	 */

	private void resizeIfNeeded () {
		int currentSize = this.size();
		int doubleHashMapSize = this.tableSize * 2;

		if (currentSize >= doubleHashMapSize) {
			recreateHashMapWithNewSize(doubleHashMapSize);
		}
	}

	private int size() {
		int currentSize = 0;

		for (SinglyLinkedList<HashNode> currentList : this.wordsTable) {
			if (currentList != null) {
				for (int index = 0; index < currentList.size(); index++) {
					currentSize++;
				}
			}
		}
		return currentSize;
	}

	private void recreateHashMapWithNewSize(int newHashMapSize){
		SinglyLinkedList<String> storedWords = new SinglyLinkedList<>();

		for (int index = 0; index < this.wordsTable.length; index++) {
			if (this.wordsTable[index] != null) {
				for (int innerIndex = 0; innerIndex < this.wordsTable[index].size(); innerIndex++) {
					storedWords.add((String) this.wordsTable[index].get(innerIndex).getValue());
				}
			}
		}

		this.wordsTable = (SinglyLinkedList<HashNode>[]) new SinglyLinkedList<?>[newHashMapSize];
		this.tableSize = newHashMapSize;

		for (int index = 0; index < storedWords.size(); index++) {
			this.add(storedWords.get(index));
		}
	}
}

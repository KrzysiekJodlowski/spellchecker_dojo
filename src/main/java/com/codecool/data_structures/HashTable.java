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
	private SinglyLinkedList<String>[] wordsTable;

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
		int generatedKey = getHash(s);

		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<String> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).equals(s)) {
					return;
				}
				curentList.add(s);
				return;
			}

		} else {
			wordsTable[generatedKey] = new SinglyLinkedList<>();
			wordsTable[generatedKey].add(s);
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
		int generatedKey = getHash(s);

		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<String> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).equals(s)) {
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
		int generatedKey = getHash(s);

		if (wordsTable[generatedKey] != null) {
			SinglyLinkedList<String> curentList = wordsTable[generatedKey];

			for (int listIndex = 0; listIndex < curentList.size(); listIndex++) {

				if (curentList.get(listIndex).equals(s)) {
					curentList.remove(listIndex);
				}
			}

		}
	}

	/*
	 *	 Need to be upgraded -> degenerate works too slow and better throws exception
	 */

	private int getHash(String s) {
		return Math.floorMod(hasher.hash(s), this.tableSize);

	}
}

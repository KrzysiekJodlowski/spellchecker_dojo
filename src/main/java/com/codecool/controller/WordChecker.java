package com.codecool.controller;

import java.util.ArrayList;

/**
 *
 * ICS 23 Summer 2004
 * Project #5: Lost for Words
 *
 * Implement your word checker here.  A word checker has two responsibilities:
 * given a word list, answer the questions "Is the word 'x' in the wordlist?"
 * and "What are some suggestions for the misspelled word 'x'?"
 *
 * WordChecker uses a class called WordList that I haven't provided the source
 * code for.  WordList has only one method that you'll ever need to call:
 *
 *     public boolean lookup(String word)
 *
 * which returns true if the given word is in the WordList and false if not.
 */

public class WordChecker
{
	private final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUWYZ".toCharArray();
	private final int INDEX_ZERO = 0;
	private final int INDEX_ONE = 1;
	private WordList wordList;
	/**
   * Constructor that initializes a new WordChecker with a given WordList.
   *
   * @param wordList Initial word list to check against.
   * @see WordList
   */
	public WordChecker(WordList wordList)
	{
		this.wordList = wordList;
	}
	

	/**
   * Returns true if the given word is in the WordList passed to the
   * constructor, false otherwise. 
   *
   * @param word Word to chack against the internal word list
   * @return bollean indicating if the word was found or not.
   */
	public boolean wordExists(String word)
	{
		return this.wordList.lookup(word.toUpperCase());
	}


	/**
   * Returns an ArrayList of Strings containing the suggestions for the
   * given word.  If there are no suggestions for the given word, an empty
   * ArrayList of Strings (not null!) should be returned.
   *
   * @param word String to check against
   * @return A list of plausible matches
   */
	public ArrayList getSuggestions(String word)
	{
		ArrayList suggestions = new ArrayList();
		word = word.toUpperCase().replaceAll("[^a-zA-Z0-9]", "");
		char[] wordArray = word.toCharArray();

		checkWordWhenSwappingLetters(wordArray, suggestions);
		checkWordWhenInsertingLetters(wordArray, suggestions);
		checkWordWhenDeletingLetters(wordArray, suggestions);
		checkWordWhenReplacingLetters(wordArray, suggestions);
		checkWordWhenSplittingInTwo(wordArray, suggestions);

		return suggestions;
	}

	private void checkWordWhenSwappingLetters(char[] wordArray, ArrayList suggestions) {
		int wordArrayLength = wordArray.length;

		for (int index = this.INDEX_ZERO; index < wordArrayLength - this.INDEX_ONE; index++) {

			char[] tempWordArray = wordArray.clone();

			char tempChar = tempWordArray[index + this.INDEX_ONE];
			tempWordArray[index + this.INDEX_ONE] = tempWordArray[index];
			tempWordArray[index] = tempChar;

			String tempWord = String.valueOf(tempWordArray);
			addWordToSuggestionsWhenNotPresent(suggestions, tempWord);
		}
	}

	private void checkWordWhenInsertingLetters(char[] wordArray, ArrayList suggestions) {
		int wordArrayLength = wordArray.length;
		int extendedWordArrayLength = wordArrayLength + this.INDEX_ONE;

		for (int index = this.INDEX_ZERO; index < extendedWordArrayLength; index++) {
			for (int letterindex = this.INDEX_ZERO; letterindex < this.ALPHABET.length; letterindex++) {

				char[] tempWordArray = new char[extendedWordArrayLength];

				if (index == this.INDEX_ZERO) {
					tempWordArray[this.INDEX_ZERO] = this.ALPHABET[letterindex];
					System.arraycopy(wordArray, this.INDEX_ZERO, tempWordArray, this.INDEX_ONE, wordArrayLength);

				} else if (index == extendedWordArrayLength - this.INDEX_ONE) {
					tempWordArray[index] = this.ALPHABET[letterindex];
					System.arraycopy(wordArray, this.INDEX_ZERO, tempWordArray, this.INDEX_ZERO, wordArrayLength);

				} else {
					System.arraycopy(wordArray, this.INDEX_ZERO, tempWordArray, this.INDEX_ZERO, index);
					System.arraycopy(wordArray, index, tempWordArray, index + this.INDEX_ONE, wordArrayLength - index);
					tempWordArray[index] = this.ALPHABET[letterindex];
				}

				String tempWord = String.valueOf(tempWordArray);
				addWordToSuggestionsWhenNotPresent(suggestions, tempWord);
			}
		}
	}

	private void checkWordWhenDeletingLetters(char[] wordArray, ArrayList suggestions) {
		int wordArrayLength = wordArray.length;
		int narrowedWordArrayLength = wordArrayLength - this.INDEX_ONE;

		for (int index = this.INDEX_ZERO; index < wordArrayLength; index++) {

			char[] tempWordArray = new char[narrowedWordArrayLength];
			tempWordArray = copyWordArrayWithoutChar(wordArray, tempWordArray, wordArrayLength, index);

			String tempWord = String.valueOf(tempWordArray);
			addWordToSuggestionsWhenNotPresent(suggestions, tempWord);
		}
	}

	private void checkWordWhenReplacingLetters(char[] wordArray, ArrayList suggestions) {
		int wordArrayLength = wordArray.length;

		for (int index = this.INDEX_ZERO; index < wordArrayLength; index++) {
			for (int letterindex = this.INDEX_ZERO; letterindex < this.ALPHABET.length; letterindex++) {

				char[] tempWordArray = new char[wordArrayLength];
				tempWordArray = copyWordArrayWithoutChar(wordArray, tempWordArray, wordArrayLength, index);
				tempWordArray[index] = this.ALPHABET[letterindex];

				String tempWord = String.valueOf(tempWordArray);
				addWordToSuggestionsWhenNotPresent(suggestions, tempWord);
			}
		}
	}

	private void checkWordWhenSplittingInTwo(char[] wordArray, ArrayList suggestions) {
		int narrowedWordArrayLength = wordArray.length - this.INDEX_ONE;

		for (int index = this.INDEX_ZERO; index < narrowedWordArrayLength; index++) {

			char[] tempFirstWordArray = new char[index + this.INDEX_ONE];
			char[] tempSecondWordArray = new char[narrowedWordArrayLength - index];

			System.arraycopy(wordArray, this.INDEX_ZERO, tempFirstWordArray, this.INDEX_ZERO, index + this.INDEX_ONE);
			System.arraycopy(wordArray, index + this.INDEX_ONE, tempSecondWordArray, this.INDEX_ZERO, narrowedWordArrayLength - index);

			String tempFirstWord = String.valueOf(tempFirstWordArray);
			String tempSecondWord = String.valueOf(tempSecondWordArray);

			if(this.wordList.lookup(tempFirstWord) && this.wordList.lookup(tempSecondWord)) {
				String likelyASuggestion = tempFirstWord + " " + tempSecondWord;
				suggestions.add(likelyASuggestion);
			}
		}
	}

	private void addWordToSuggestionsWhenNotPresent(ArrayList suggestions, String word) {
		if (this.wordList.lookup(word)) {
			if (!suggestions.contains(word)) {
				suggestions.add(word);
			}
		}
	}

	private char[] copyWordArrayWithoutChar(char[] wordArray, char[] tempWordArray, int wordArrayLength, int index) {
		int narrowedWordArrayLength = wordArrayLength - this.INDEX_ONE;

		if (index == this.INDEX_ZERO) {
			System.arraycopy(wordArray, this.INDEX_ONE, tempWordArray, this.INDEX_ZERO, narrowedWordArrayLength);

		} else if (index == wordArrayLength) {
			System.arraycopy(wordArray, this.INDEX_ZERO, tempWordArray, this.INDEX_ZERO, narrowedWordArrayLength);

		} else {
			System.arraycopy(wordArray, this.INDEX_ZERO, tempWordArray, this.INDEX_ZERO, index);
			System.arraycopy(wordArray, index, tempWordArray, index - this.INDEX_ONE, wordArrayLength - index);
		}

		return tempWordArray;
	}
}

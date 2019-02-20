package com.codecool.controller;

/*
 * Decompiled with CFR 0.139.
 */
public class WordLineReader {
    private final String line;
    private int currentChar;
    private String nextWord;

    public WordLineReader(String string) {
        this.line = string;
        if (string == null) {
            this.nextWord = null;
        } else {
            this.currentChar = 0;
            this.readNextWord();
        }
    }

    public boolean hasNextWord() {
        return this.nextWord != null;
    }

    public String nextWord() {
        String string = this.nextWord;
        if (this.nextWord != null) {
            this.readNextWord();
        }
        return string;
    }

    private void readNextWord() {
        this.nextWord = "";
        while (this.currentChar < this.line.length() && !this.isWordStartingOrEndingLetter(this.line.charAt(this.currentChar))) {
            ++this.currentChar;
        }
        while (this.currentChar < this.line.length() && this.isWordLetter(this.line.charAt(this.currentChar))) {
            this.nextWord = this.nextWord + this.line.charAt(this.currentChar);
            ++this.currentChar;
        }
        if (this.nextWord.length() == 0) {
            this.nextWord = null;
            return;
        }
        if (!this.isWordStartingOrEndingLetter(this.nextWord.charAt(this.nextWord.length() - 1))) {
            this.nextWord = this.nextWord.substring(0, this.nextWord.length() - 1);
        }
    }

    private boolean isWordStartingOrEndingLetter(char c) {
        return c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9';
    }

    private boolean isWordLetter(char c) {
        return this.isWordStartingOrEndingLetter(c) || c == '-' || c == '\'';
    }
}

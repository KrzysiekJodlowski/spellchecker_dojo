package com.codecool.controller;/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  StringHasher
 *  WordChecker
 */

import com.codecool.controller.tools.StringHasher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Checker {
    public void check(String string, String string2, StringHasher stringHasher, PrintStream printStream) throws IOException {
        WordList wordList = new WordList(string2, stringHasher);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(string));
        String string3 = bufferedReader.readLine();
        WordLineReader wordLineReader = new WordLineReader(string3);
        WordChecker wordChecker = new WordChecker(wordList);
        while (string3 != null) {
            while (wordLineReader.hasNextWord()) {
                String string4 = wordLineReader.nextWord().toUpperCase();
                if (wordChecker.wordExists(string4)) continue;
                ArrayList arrayList = wordChecker.getSuggestions(string4);
                printStream.println();
                printStream.println(string3);
                printStream.println("     word not found: " + string4);
                if (arrayList.size() <= 0) continue;
                Collections.sort(arrayList);
                printStream.println("  perhaps you meant: ");
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    String string5 = (String)iterator.next();
                    printStream.println("          " + string5 + " ");
                }
            }
            string3 = bufferedReader.readLine();
            wordLineReader = new WordLineReader(string3);
        }
        bufferedReader.close();
    }
}

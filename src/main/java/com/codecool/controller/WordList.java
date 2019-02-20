package com.codecool.controller;/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  HashTable
 *  StringHasher
 */

import com.codecool.controller.tools.StringHasher;
import com.codecool.data_structures.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class WordList {
    private final HashTable hashTable;

    public WordList(String string, StringHasher stringHasher) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(string));
        int n = Integer.parseInt(bufferedReader.readLine());
        this.hashTable = new HashTable((int)((double)n * 1.2), stringHasher);
        for (int i = 0; i < n; ++i) {
            this.hashTable.add(bufferedReader.readLine().trim().toUpperCase());
        }
        bufferedReader.close();
    }

    public boolean lookup(String string) {
        return this.hashTable.lookup(string.toUpperCase());
    }
}

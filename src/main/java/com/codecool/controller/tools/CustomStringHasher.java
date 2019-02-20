package com.codecool.controller.tools;


public class CustomStringHasher implements StringHasher {

    public int hash(String s) {
        int h = 0;

        for (int i = 0; i < s.length(); i++) {

            h *= 2;
            h += s.charAt(i);
        }

        return h;
    }
}

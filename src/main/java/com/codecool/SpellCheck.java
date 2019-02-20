package com.codecool;/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  BetterStringHasher
 *  DegenerateStringHasher
 *  LousyStringHasher
 *  StringHasher
 */
import com.codecool.controller.*;
import com.codecool.controller.tools.*;

import java.io.IOException;
import java.io.PrintStream;


public class SpellCheck {
    public static void main(String[] arrstring) {
        if (arrstring.length == 0) {
            SpellCheck.showUsageMessage();
            return;
        }
        String string = "src/main/Resources/" + arrstring[arrstring.length - 1];
        String string2 = "src/main/Resources/wordlist.txt";
        StringHasher lousyStringHasher = new LousyStringHasher();   // LousyStringHasher -> StringHasher
        PrintStream printStream = System.out;
        boolean bl = false;
        for (int i = 0; i < arrstring.length - 1; ++i) {
            if (arrstring[i].equals("-degenerate")) {
                lousyStringHasher = new DegenerateStringHasher();
                continue;
            }
            if (arrstring[i].equals("-lousy")) {
                lousyStringHasher = new LousyStringHasher();
                continue;
            }
            if (arrstring[i].equals("-better")) {
                lousyStringHasher = new BetterStringHasher();
                continue;
            }
            if (arrstring[i].equals("-quiet")) {
                printStream = new PrintStream(new NullOutputStream());
                bl = true;
                continue;
            }
            if (!arrstring[i].equals("-wordlist")) continue;
            if (++i >= arrstring.length - 1) {
                SpellCheck.showUsageMessage();
                return;
            }
            string2 = arrstring[i];
        }
        if (arrstring[arrstring.length - 1].charAt(0) == '-') {
            SpellCheck.showUsageMessage();
            return;
        }
        try {
            long l = System.currentTimeMillis();
            new Checker().check(string, string2, (StringHasher)lousyStringHasher, printStream);
            long l2 = System.currentTimeMillis();
            if (bl) {
                System.out.println("Checker ran in " + (l2 - l) + "ms");
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private static void showUsageMessage() {
        System.out.println("Usage: java SpellCheck [options] inputFilename");
        System.out.println();
        System.out.println("    options");
        System.out.println("    -------");
        System.out.println("    -degenerate");
        System.out.println("        runs the spell checker with the degenerate word hashing algorithm");
        System.out.println();
        System.out.println("    -lousy");
        System.out.println("        runs the spell checker with a lousy word hashing algorithm (default)");
        System.out.println();
        System.out.println("    -better");
        System.out.println("        runs the spell checker with a better word hashing algorithm");
        System.out.println();
        System.out.println("    -quiet");
        System.out.println("        runs the spell checker without any output, reporting the total time");
        System.out.println("        taken to load the dictionary and perform the spell check");
        System.out.println();
        System.out.println("    -wordlist wordlistFilename");
        System.out.println("        runs the spell checker using the wordlist specified, rather than");
        System.out.println("        the default (wordlist.txt)");
        System.out.println();
        System.out.println("    example");
        System.out.println("    -------");
        System.out.println("    java SpellCheck -wordlist biglist.txt -better -quiet big-input.txt");
        System.out.println("        executes the spell checker using the wordlist 'biglist.txt', the");
        System.out.println("        better word hashing algorithm, in quiet mode (i.e. no output),");
        System.out.println("        on the input file 'big-input.txt'");
    }
}

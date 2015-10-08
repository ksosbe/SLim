package com.team11.slim;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Written by Nick Houser
 * Last modified 9/19/14
 * Class to filter out profane words
 * @author Nick Houser
 */
public class ProfanityFilter {
    // Node serving as the head of the tree defining blacklisted words
    private LetterNode headLetter;
    
    /**
     * Constructor for the ProfanityFilter class
     * Creates a tree data structure for words in the blacklist
     * Assumes a file "blacklist.txt" in the source directory
     * File contains blacklisted words in all lower case
     * With no spaces and a new word on each line
     */
    public ProfanityFilter() {
        headLetter = new LetterNode();
        try {
            BufferedReader fileStream = new BufferedReader(new FileReader("blacklist.txt"));
            String textLine = fileStream.readLine();
            LetterNode currentLetter;
            int currentLetterInt;
            while (textLine != null) {
                currentLetter = headLetter;
                for (int strIndex = 0; strIndex < textLine.length(); strIndex++) {
                    currentLetterInt = headLetter.letterToInt(textLine.charAt(strIndex));
                    if (currentLetter.nextLetters[currentLetterInt] == null) {
                        currentLetter.nextLetters[currentLetterInt] = new LetterNode();
                    }
                    currentLetter = currentLetter.nextLetters[currentLetterInt];
                }
                currentLetter.lastLetter = true;
                textLine = fileStream.readLine();
            }
            fileStream.close();
        }catch (Exception e) {
            // do nothing
        }
    }
    
    /**
     * Method which takes a string and removes profanity from it by
     * replacing any profane words with stars
     * @param dirty The string to clean up
     * @return A string identical to the input but without the profanity
     */
    public String cleanText(String dirty) {        
        char[] toRet = dirty.toCharArray();
        dirty = dirty.toLowerCase();
        String clean = "";
        
        int searchIndex;
        LetterNode searchNode;
        LetterNode nextNode;
        for (int strIndex = 0; strIndex < dirty.length(); strIndex++) {
            if (headLetter.letterToInt(dirty.charAt(strIndex)) == -1) {
                continue;
            }
            
            searchIndex = 0;
            searchNode = headLetter;
            nextNode = searchNode.nextLetters[headLetter.letterToInt(dirty.charAt(searchIndex + strIndex))];
            while (dirty.length() > (searchIndex + strIndex) && nextNode != null) {
                if (nextNode.lastLetter == true)
                {
                    for (int indexToStar = 0; indexToStar <= searchIndex; indexToStar++) {
                        toRet[strIndex + indexToStar] = '*';
                    }
                }
                
                searchNode = nextNode;
                searchIndex = searchIndex + 1;
                while (dirty.length() > (searchIndex + strIndex) && headLetter.letterToInt(dirty.charAt(searchIndex + strIndex)) == -1) {
                    searchIndex = searchIndex + 1;
                }
                if (dirty.length() > (searchIndex + strIndex)) {
                    nextNode = searchNode.nextLetters[headLetter.letterToInt(dirty.charAt(searchIndex + strIndex))];
                }
            }
        }
        
        for (int strIndex = 0; strIndex < dirty.length(); strIndex++) {
            clean = clean + toRet[strIndex];
        }
        //System.out.println(clean);
        return clean;
    }

    /**
     * Class to represent blacklisted words as a tree
     * This should optimize searching through the blacklist
     */
    private class LetterNode {
        // array representing which letters following this one form blacklisted words
        private LetterNode[] nextLetters;
        // boolean representing whether this letter is the end of a blacklisted word
        private boolean lastLetter;
        
        /**
         * Constructor which initializes a new letter node
         * It will have no next letters and will not be the last letter of a word
         * These properties must be set later during the reading of the dictionary
         * @param depth The distance of the node from the head node
         */
        private LetterNode() {
            nextLetters = new LetterNode[26];
            lastLetter = false;
            for (int letter = 0; letter < 26; letter++) {
                nextLetters[letter] = null;
            }
        }
        
        /**
         * Method which converts a letter to its position in the alphabet
         * @param toConvert The letter as a lower-case
         * @return An integer representing the letter's position in the alphabet
         */
        private int letterToInt(char toConvert) {
            switch (toConvert) {
                case 'a':
                    return 0;
                case 'b':
                    return 1;
                case 'c':
                    return 2;
                case 'd':
                    return 3;
                case 'e':
                    return 4;
                case 'f':
                    return 5;
                case 'g':
                    return 6;
                case 'h':
                    return 7;
                case 'i':
                    return 8;
                case 'j':
                    return 9;
                case 'k':
                    return 10;
                case 'l':
                    return 11;
                case 'm':
                    return 12;
                case 'n':
                    return 13;
                case 'o':
                    return 14;
                case 'p':
                    return 15;
                case 'q':
                    return 16;
                case 'r':
                    return 17;
                case 's':
                    return 18;
                case 't':
                    return 19;
                case 'u':
                    return 20;
                case 'v':
                    return 21;
                case 'w':
                    return 22;
                case 'x':
                    return 23;
                case 'y':
                    return 24;
                case 'z':
                    return 25;
            }
            return -1;
        }
    }
}
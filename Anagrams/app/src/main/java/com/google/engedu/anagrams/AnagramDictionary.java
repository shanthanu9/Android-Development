/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.HashMap;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 3;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 8;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String> > lettersToWord = new HashMap< >();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap< >();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordList.add(word);

            wordSet.add(word);

            ArrayList<String> values = new ArrayList< >();
            if(sizeToWords.containsKey(word.length())) {
                values = sizeToWords.get(word.length());
            }
            values.add(word);
            sizeToWords.put(word.length(), values);

            String sorted = sortLetters(word);
            ArrayList<String> anagrams = new ArrayList< >();
            if (lettersToWord.containsKey(sorted)) {
                anagrams = lettersToWord.get(sorted);
            }
            anagrams.add(word);
            lettersToWord.put(sorted, anagrams);
        }
    }

    public String sortLetters(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        return sorted;
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.contains(base)) {
            return true;
        }
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList< >();
        targetWord = sortLetters(targetWord);
        for(int i = 0; i != wordList.size(); i++) {
            if(targetWord.length() == (wordList.get(i)).length()) {
                String sorted = sortLetters(wordList.get(i));
                if(sorted.equals(targetWord)) {
                    result.add(wordList.get(i));
                }
            }
        }
        return result;
    }

    public List<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> result = new ArrayList< >();
        for(char k = 'a'; k <= 'z'; k++) {
            for (char i = 'a'; i <= 'z'; i++) {
                String temp = word + i + k;
                String s = sortLetters(temp);
                if (lettersToWord.containsKey(s)) {
                    ArrayList<String> value = lettersToWord.get(s);
                    for (int j = 0; j != value.size(); j++) {
                        if (isGoodWord(value.get(j), word)) {
                            result.add(value.get(j));
                        }
                    }
                }
            }
        }
        return result;
    }

    /*public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList< >();
        for(char i = 'a'; i <= 'z'; i++) {
            String temp = word + i;
            String s = sortLetters(temp);
            if (lettersToWord.containsKey(s)) {
                ArrayList<String> value = lettersToWord.get(s);
                for (int j = 0; j != value.size(); j++) {
                    if (isGoodWord(value.get(j), word)) {
                        result.add(value.get(j));
                    }
                }
            }
        }
        return result;
    }*/

    public String pickGoodStarterWord() {
        String str;
        int count;
        Integer b = new Integer(wordLength);
        ArrayList<String> refWordList;
        refWordList = sizeToWords.get(b);
        do {
            int rnd = random.nextInt(refWordList.size());
            str = refWordList.get(rnd);
            count = (lettersToWord.get(sortLetters(str))).size();
        } while (count < MIN_NUM_ANAGRAMS);
        if(wordLength < MAX_WORD_LENGTH) {
            wordLength += 1;
        }
        return str;
    }
}

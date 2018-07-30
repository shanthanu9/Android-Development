package com.example.admin.ghost;

/**
 * Created by ADMIN on 31-03-2018.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;


public class WordDictionary {

    private ArrayList<String> wordList = new ArrayList<>();
    private Random random = new Random();

    public WordDictionary(Reader reader) throws IOException{
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
        }
    }

    //returns up and down in an array pair
    private int[] searchWords(String key) {
        int depth = key.length(), i = 0;
        int up = 0, down = wordList.size()-1;
        while(i < depth) {
            char currentKey = key.charAt(i);
            int left = up, right = down, mid;
            while(right - left > 1) {
                mid = (left + right)/2;
                String val = wordList.get(mid);
                if(val.length() <= i) {
                    left = mid+1;
                    continue;
                }
                char value = val.charAt(i);
                if(value < currentKey) {
                    left = mid+1;
                }
                else if(value >= currentKey){
                    right = mid;
                }
            }
            up = left;
            right = down;
            while(right - left > 1) {
                mid = (left + right)/2;
                String val = wordList.get(mid);
                if(val.length() <= i) {
                    left = mid + 1;
                    continue;
                }
                char value = val.charAt(i);
                if(value <= currentKey) {
                    left = mid;
                }
                else if(value > currentKey){
                    right = mid - 1;
                }
            }
            down = right;
            i++;
        }
        int[] pair = {up, down};
        return pair;
    }


    //assuming there are words that begin with key
    //it's done randomly for now
    public String bestLetterChoice(String key) {
        int i = random.nextInt(26);
        char ch = (char) ('a' + i);
        return String.valueOf(ch);
    }

    public boolean isWord(String key) {
        int[] pair = searchWords(key);
        int up = pair[0];
        String compareString = wordList.get(up);
        Log.d("isWord",compareString + " " + key);
        if(key.equals(compareString)) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isBeginOFSomeWord(String key) {
        int[] pair = searchWords(key);
        int up = pair[0], down = pair[1];
        if(up == down) {
            return false;
        }
        else {
            return true;
        }
    }

    //assuming that the word exists
    public String someWordBeginWith(String key) {
        int[] pair = searchWords(key);
        int up = pair[0], down = pair[1];
        int i = random.nextInt(down - up) + up;
        return wordList.get(i);
    }
}

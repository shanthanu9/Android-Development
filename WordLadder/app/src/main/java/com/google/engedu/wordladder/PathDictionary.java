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

package com.google.engedu.wordladder;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class PathDictionary {
    private static final int MAX_WORD_LENGTH = 4;
    private static HashSet<String> words = new HashSet<>();
    private static HashMap<String, ArrayList<String>> wordMap = new HashMap<>();
    private static HashSet<String> searchedWords = new HashSet<>();

    public PathDictionary(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return;
        }
        Log.i("Word ladder", "Loading dict");
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        Log.i("Word ladder", "Loading dict");
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() > MAX_WORD_LENGTH) {
                continue;
            }
            words.add(word);
        }
        for(String word: words) {
            wordMap.put(word, neighbours(word));
        }
    }

    private boolean compareStrings(String a, String b) {
        if(a.length() != b.length()) {
            return false;
        }
        int misMatch = 0;
        for(int i = 0; i != a.length(); i++) {
            if(a.charAt(i) != b.charAt(i)) {
                misMatch++;
            }
        }
        if(misMatch == 1) {
            return true;
        }
        return false;
    }

    public boolean isWord(String word) {
        return words.contains(word.toLowerCase());
    }

    private ArrayList<String> neighbours(String word) {
        ArrayList<String> differByOneLetter = new ArrayList<>();
        for(String check: words) {
            if(compareStrings(word, check)) {
                differByOneLetter.add(check);
            }
        }
        return differByOneLetter;
    }

    public String[] findPath(String start, String end) {
        if(!isWord(start) || !isWord(end)) {
            return null;
        }
        ArrayDeque<ArrayList<String>> queue = new ArrayDeque<>();
        ArrayList<String> path = new ArrayList<>();
        path.add(start);
        searchedWords.add(start);
        queue.addLast(path);
        int depth = 10;
        while(!queue.isEmpty()) {
            ArrayList<String> currentPath = queue.removeFirst();
            if(currentPath.size() > depth) {
                continue;
            }
            String currentWord = currentPath.get(currentPath.size() - 1);
            if(wordMap.get(currentWord).contains(end)) {
                path = currentPath;
                path.add(end);
                Log.d("lets", path.toString());
                break;
            }
            for(String s: wordMap.get(currentWord)) {
                if(!searchedWords.contains(s)) {
                    searchedWords.add(s);
                    ArrayList<String> search = new ArrayList<>(currentPath);
                    search.add(s);
                    queue.addLast(search);
                    Log.d("key", queue.toString());
                }
            }
        }
        String[] path1 = path.toArray(new String[path.size()]);
        return path1;
    }
}

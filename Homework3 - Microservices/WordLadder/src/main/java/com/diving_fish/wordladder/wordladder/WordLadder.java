package com.diving_fish.wordladder.wordladder;

import java.io.*;
import java.util.*;

class WordLink {
    public String first;
    public String second;
    WordLink(String a, String b) {
        first = a;
        second = b;
    }
}

public class WordLadder {
    public Set<String> dictionary = new TreeSet<String>();

    public WordLadder(String path) {
        File f = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                dictionary.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generate_string(String start, String end) {
        ArrayList<String> result = generate(start, end);
        if (result.size() == 0) {
            return "No Ladder.";
        } else {
            StringBuffer s = new StringBuffer();
            for (String s2 : result) {
                s.append(s2);
                if (!s2.equals(end)) s.append("->");
            }
            return s.toString();
        }
    }
    public ArrayList<String> generate(String start, String end) {
        Queue<String> q = new LinkedList<String>();
        Set<String> searched = new TreeSet<String>();
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<WordLink> wordLinks = new ArrayList<WordLink>();
        q.offer(start);
        while (!q.isEmpty()) {
            String current = q.poll();
            if (current.equals(end)) {
                while (!current.equals(start)) {
                    for (WordLink w : wordLinks) {
                        if (w.second.equals(current)) {
                            result.add(0, current);
                            current = w.first;
                            break;
                        }
                    }
                }
                result.add(0, current);
                break;
            }
            ArrayList<String> neighbor = find_neighbor(current);
            for (String word : neighbor) {
                if (!searched.contains(word)) {
                    searched.add(word);
                    wordLinks.add(new WordLink(current, word));
                    q.offer(word);
                }
            }
        }
        return result;
    }
    public boolean in_dict(String word) {
        return dictionary.contains(word);
    }
    private String replace_word(String word, char c, int index) {
        return word.substring(0, index) + c + word.substring(index+1);
    }
    private ArrayList<String> find_neighbor(String word) {
        ArrayList<String> a = new ArrayList<String>();
        for (int i = 0; i < word.length(); i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                String s = replace_word(word, c, i);
                if (in_dict(s) && !s.equals(word)) {
                    a.add(s);
                }
            }
        }
        return a;
    }
}

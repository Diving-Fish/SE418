import java.io.*;
import java.util.*;

class NotInDictException extends Exception {

}

class WordLink {
    public String first;
    public String second;
    WordLink(String a, String b) {
        first = a;
        second = b;
    }
}

public class WordLadder {
    public static Set<String> dictionary = new TreeSet<String>();
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter the start word:");
        String start = s.nextLine();
        System.out.print("Please enter the end word:");
        String end = s.nextLine();
        System.out.println(generate_string(start, end));
    }

    public static String generate_string(String start, String end) {
        initialize_dict();
        try {
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
        } catch (NotInDictException e) {
            return "Word is not in dictionary.";
        }
    }
    public static ArrayList<String> generate(String start, String end) throws NotInDictException {
        Queue<String> q = new LinkedList<String>();
        Set<String> searched = new TreeSet<String>();
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<WordLink> wordLinks = new ArrayList<WordLink>();
        q.offer(start);
        if (!in_dict(start) || !in_dict(end)) {
            throw new NotInDictException();
        }
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
    private static void initialize_dict() {
        File f = new File("src/main/java/dictionary.txt");
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
    private static boolean in_dict(String word) {
        return dictionary.contains(word);
    }
    private static String replace_word(String word, char c, int index) {
        return word.substring(0, index) + c + word.substring(index+1);
    }
    private static ArrayList<String> find_neighbor(String word) {
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

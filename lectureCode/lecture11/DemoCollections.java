/**
 * Created by 11519 on 2018/7/8.
 */
import java.util.*;

public class DemoCollections {

    /** Returns a lower case version of the string with
     * all characters except letters removed. */
    public static String cleanString(String s) {
        return s.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
    }

    /** Gets a list of all words in the file. */
    public static List<String> getWords(String inputFilename) {
        In in = new In(inputFilename);

        List<String> w = new ArrayList<>();
        while (!in.isEmpty()) {
            String rawString = in.readString();
            w.add(cleanString(rawString));
        }
        return w;
    }

    /** Returns the count of the number of unique words in words. */
    public static int countUniqueWords(List<String> words) {
        Set<String> ss = new HashSet<>();
        for (String s: words) {
            ss.add(s);
        }
        return ss.size();
    }

    /** Returns a map (a.k.a. dictionary) that tracks the count of all specified
     * target words in words. */
    public static Map<String, Integer> collectWordCount(List<String> words, List<String> targets) {
        Map<String, Integer> counts = new HashMap<>();
        for (String t: targets) {
            counts.put(t, 0);
        }

        for (String s: words) {
            if (counts.containsKey(s)) {
                counts.put(s, counts.get(s)+1);
            }
        }
        return counts;
    }

    public static void main(String[] args) {
        List<String> w = getWords("libraryOfBabylon.txt");
        System.out.println(w);
		System.out.println(countUniqueWords(w));
		List<String> targets = new ArrayList<String>();
		targets.add("lottery");
		targets.add("the");
		targets.add("babylon");
		System.out.println(collectWordCount(w, targets));
    }
}

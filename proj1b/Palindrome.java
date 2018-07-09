/**
 * Created by 11519 on 2018/7/9.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word) {
        Deque<Character> wq = new ArrayDeque<>();
        if (word == null || word.length() == 0) {
            return wq;
        }
        for (char ch: word.toCharArray()) {
            wq.addLast(ch);
        }

        return wq;
    }

    public static boolean isPalindrome(String word) {
        return isPalindrome(wordToDeque(word));
    }

    public static boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindrome(wordToDeque(word), cc);
    }

    private static boolean isPalindrome(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        }

        char first = wordDeque.removeFirst();
        char last = wordDeque.removeLast();

        return cc.equalChars(first, last) && isPalindrome(wordDeque, cc);
    }

    private static boolean isPalindrome(Deque<Character> wordDeque) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        }

        char first = wordDeque.removeFirst();
        char last = wordDeque.removeLast();

        return first == last && isPalindrome(wordDeque);
    }
}

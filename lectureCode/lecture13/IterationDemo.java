/**
 * Created by 11519 on 2018/7/9.
 */
public class IterationDemo {
    public static void main(String[] args) {
        ArrayMap<String, Integer> am = new ArrayMap<>();
        am.put("hello", 5);
        am.put("syrups", 10);

        ArrayMap.KeyIterator ami = am.new KeyIterator();
        while (ami.hasNext()) {
            System.out.println(ami.next());
        }

        for (String s: am) {
            System.out.println(s);
        }
    }
}

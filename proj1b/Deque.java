/**
 * Created by 11519 on 2018/7/9.
 */
public interface Deque<Item> {
    void addFirst(Item item);

    void addLast(Item item);

    boolean isEmpty();

    int size();

    void printDeque();

    Item removeFirst();

    Item removeLast();

    Item get(int index);
}

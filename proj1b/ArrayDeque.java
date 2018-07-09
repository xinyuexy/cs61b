/**
 * Created by 11519 on 2018/7/7.
 * Circle array deque
 */
public class ArrayDeque<Item> implements Deque<Item> {
    private Item[] items;
    private int nextFirst;
    private int nextLast;
    private int size;
    private final static int START_SIZE = 8;

    public ArrayDeque() {
        items = (Item[]) new Object[START_SIZE];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    @Override
    /** Adds an item to the front of the Deque */
    public void addFirst(Item item) {
        if (isFull()) {
            extend();
        }

        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    @Override
    /** Adds an item to the back of the Deque */
    public void addLast(Item item) {
        if (isFull()) {
            extend();
        }

        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size++;
    }

    private void extend() {
        resize(2 * size());
    }

    @Override
    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    /** Returns the number of items in the Deque */
    public int size() {
        return size;
    }

    /** return real length of array */
    public int capacity() {
        return items.length;
    }

    @Override
    /** Prints the items in the Deque from first to last, separated by a space */
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("LinkedListDeque is empty");
            return;
        }

        System.out.println("print ArrayDeque item...");
        for (int i=plusOne(nextFirst); i != nextLast; i=plusOne(i)) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }

    @Override
    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null.*/
    public Item removeFirst() {
        if (isSparse()) {
            shrink();
        }

        nextFirst = plusOne(nextFirst);
        Item toRemove = items[nextFirst];
        items[nextFirst] = null;
        size--;
        return toRemove;
    }

    @Override
    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null.*/
    public Item removeLast() {
        if (isSparse()) {
            shrink();
        }

        nextLast = minusOne(nextLast);
        Item toRemove = items[nextLast];
        items[nextLast] = null;
        size--;
        return toRemove;
    }

    private void shrink() {
        resize(items.length / 2);
    }

    @Override
    /** Gets the item at the given index (start 0)*/
    public Item get(int index) {
        return items[plusOne(nextFirst + index)];
    }

    private int minusOne(int index) {
        //floorMod取模运算(取模和取余%不同)
        //如nextFirst-1后为-1, items.length为5,
        //则 -1 mod 5 = 4; 刚好回到数组末端(循环)
        return Math.floorMod(index-1, items.length);
    }

    private int plusOne(int index) {
        return Math.floorMod(index+1, items.length);
    }

    private int plusOne(int index, int size) {
        return Math.floorMod(index+1, size);
    }

    private boolean isFull() {
        return size == items.length;
    }

    private boolean isSparse() {
        return items.length >= 16 && size() < items.length / 4;
    }

    private void resize(int capacity) {
        //System.out.printf("Resizing from %5d to %5d\n", items.length, capacity);
        int oldFirst = plusOne(nextFirst);
        int oldLast = minusOne(nextLast);

        Item[] newArray = (Item[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        for (int i=oldFirst; i != oldLast; i = plusOne(i)) {
            newArray[nextLast] = items[i];
            nextLast = plusOne(nextLast, newArray.length);
        }
        newArray[nextLast] = items[oldLast];
        nextLast = plusOne(nextLast, newArray.length);
        items = newArray;
    }
}

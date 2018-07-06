/**
 * Created by 11519 on 2018/7/6.
 * Doubly Linked List Implementation.
 * circular sentinel approaches
 * refer: [https://joshhug.gitbooks.io/hug61b/content/chap2/chap23.html]
 */
public class LinkedListDeque<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node() {
            this.item = null;
            this.next = null;
            this.prev = null;
        }

        public Node(Item i) {
            this.item = i;
            this.next = null;
            this.prev = null;
        }

        public Node(Item i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds an item to the front of the Deque */
    public void addFirst(Item item) {
        Node node = new Node(item);
        node.prev = sentinel;
        node.next = sentinel.next;
        sentinel.next.prev = node;
        sentinel.next = node;
        size++;
    }

    /** Adds an item to the back of the Deque */
    public void addLast(Item item) {
        Node node = new Node(item);
        node.prev = sentinel.prev;
        node.next = sentinel;
        sentinel.prev.next = node;
        sentinel.prev = node;
        size++;
    }

    /** Returns true if deque is empty, false otherwise */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /** Returns the number of items in the Deque */
    public int size() {
        return size;
    }

    /** Prints the items in the Deque from first to last, separated by a space */
    public void printDeque() {
        if (isEmpty()) {
            System.out.println("LinkedListDeque is empty");
            return;
        }
        Node p = sentinel.next;
        System.out.println("print LinkedListDeque item...");
        while (p != sentinel) {     //不能用p!=null来判断,链表是循环的(永远不会为null)
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the Deque. If no such item exists, returns null.*/
    public Item removeFirst() {
        Item item = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return item;
    }

    /** Removes and returns the item at the back of the Deque. If no such item exists, returns null.*/
    public Item removeLast() {
        Item item = sentinel.prev.item;
        Node sl = sentinel.prev.prev;
        sl.next = sl.next.next;
        sl.next.prev = sl;
        size--;
        return item;
    }

    /** Gets the item at the given index (start 0)*/
    public Item get(int index) {
        if (size == 0 || index >= size) {
            return null;
        }

        Node p = sentinel.next;
        for (int i=0; i<index; i++) {
            p = p.next;
        }
        return p.item;
    }

    /** Gets the item at the given index (start 0) Recursive*/
    public Item getRecursive(int index) {
        if (size == 0 || index >= size) {
            return null;
        }
        return getRecHelp(sentinel.next, index);
    }

    /** Gets help method */
    private Item getRecHelp(Node p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getRecHelp(p.next, index-1);
    }
}

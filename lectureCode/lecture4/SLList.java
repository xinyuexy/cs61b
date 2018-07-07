/** An SLList is a list of integers, which hides the terrible truth
   * of the nakedness within. */
/** refer: Reading 2.2 */

public class SLList {
    /** Nested Classes */
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }
    //private IntNode first;
    private IntNode sentinel;
    private int size;   //Caching size

    /** Construct empty list */
    //允许构建空链表后会让addLast方法出现异常,可以设置一个虚拟头节点(哨兵)
    public SLList() {
        //first = null;
        sentinel = new IntNode(0, null);
        size = 0;
    }

    public SLList(int x) {
        //first = new IntNode(x, null);
        sentinel = new IntNode(0, null);
        sentinel.next = new IntNode(x, null);
        size = 1;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(int x) {
        //first = new IntNode(x, first);
        sentinel.next = new IntNode(x, sentinel.next);
        size += 1;
    }

    /** Adds an item to the end of the list. */
    public void addLast(int x) {
        IntNode p = sentinel;

        while (p.next != null) {
            p = p.next;
        }

        p.next = new IntNode(x, null);
        size += 1;
    }

    /** Returns the size of the list starting at IntNode p. */
    private static int size(IntNode p) {
        if (p.next == null) {
            return 1;
        }

        return 1 + size(p.next);
    }

    public int size() {
        //return size(first);
        //使用缓存的size变量更快,不用每次重新计算
        return size;
    }

    public static void main(String[] args) {
        /* Creates a list of one integer, namely 10 */
        SLList L = new SLList();
        L.addLast(20);
        System.out.println(L.size());
    }
}

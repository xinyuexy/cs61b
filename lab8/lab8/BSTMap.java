package lab8;

import edu.princeton.cs.algs4.StdOut;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.val;
        }
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node x) {
        if (x == null) {
            this.size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(key, value, x.left);
        } else if (cmp > 0) {
            x.right = put(key, value, x.right);
        } else {
            x.val = value;  //覆盖原来的值
        }
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    /**
     * BST Iterator
     * refer: https://blog.csdn.net/mine_song/article/details/70903829
     */
    private class BSTMapIterator implements Iterator<K> {
        private Stack<Node> st;
        public BSTMapIterator() {
            st = new Stack<>();
            // 先找到左边第一个节点，并把路径入栈
            while (root != null) {
                st.push(root);
                root = root.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        @Override
        public K next() {
            Node cur = st.pop();
            K res = cur.key;
            // 如果该元素有右节点，把它的右节点及右节点的所有左边节点都压入栈中
            cur = cur.right;
            while (cur != null) {
                st.push(cur);
                cur = cur.left;
            }
            return res;
        }
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node x) {
        if (x == null) {
            return;
        }
        printInOrder(x.left);
        StdOut.print(x.key + " ");
        printInOrder(x.right);
    }

    @Override
    public V remove(K key) {
        V res = this.get(key);
        root = remove(root, key);
        return res;
    }

    private Node remove(Node x, K key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = remove(x.left, key);
        } else if (cmp > 0) {
            x.right = remove(x.right, key);
        } else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;

            Node cur = x.right;
            while (cur.left != null) {
                cur = cur.left;
            }
            x.key = cur.key;
            x.val = cur.val;
            x.right = remove(x.right, cur.key);
        }
        return x;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node x = queue.poll();
            if (x == null) continue;
            keys.add(x.key);
            queue.offer(x.left);
            queue.offer(x.right);
        }
        return keys;
       // throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap<Integer, String> bm = new BSTMap<>();
        bm.put(2, "aa");
        bm.put(1, "bb");
        bm.put(5, "cc");
        bm.printInOrder();
        System.out.println();
        //Iterator Test
        for (int i: bm) {
            System.out.print(i+" ");
        }
    }

}

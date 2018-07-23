package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;

    private int n;  //number of key-value pairs
    private int m;  // hash table size
    private LinkedList<Node>[] st;

    private class Node {
        K key;
        V val;
        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        this.n = 0;
        this.m = initialSize;
        this.st = new LinkedList[initialSize];
        for (int i=0; i<m; i++) {
            st[i] = new LinkedList<Node>();
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        for (int i=0; i<st.length; i++) {
            st[i] = new LinkedList<>();
        }
        this.n = 0;
    }


    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) !=  null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        int i = hash(key);
        return get(key, i);
    }

    private V get(K key, int i) {
        for (Node x: st[i]) {
            if (x.key.equals(key)) {    //注意不能用x.key==key
                return x.val;
            }
        }
        return null;
    }

    /** hash value between 0 and m-1 */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    @Override
    public int size() {
        return this.n;
    }

    @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        if (n >= loadFactor * m) {
            resize(2 * m);
        }

        int i = hash(key);
        put(key, val, i);
    }

    private void put(K key, V val, int i) {
        for (Node x: st[i]) {
            if (x.key.equals(key)) {
                x.val = val;
                return;
            }
        }
        st[i].addFirst(new Node(key, val));
        this.n++;
    }

    /** resize the hash table to have the given number of chains,
     * rehashing all of the keys
     */
    private void resize(int chains) {
        MyHashMap<K, V> temp = new MyHashMap<>(chains);
        for (int i=0; i<m; i++) {
            for (Node x: st[i]) {
                temp.put(x.key, x.val);
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (int i=0; i<m; i++) {
            for (Node x: st[i]) {
                keys.add(x.key);
            }
        }
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIter();
    }

    private class MyHashMapIter implements Iterator<K> {
        Iterator<K> it;
        public MyHashMapIter() {
            it = keySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public K next() {
            return it.next();
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        if (containsKey(key)) {
            int i = hash(key);
            for (Node x: st[i]) {
                if (x.key == key) {
                    V val = x.val;
                    st[i].remove(x);
                    this.n--;
                    return val;
                }
            }
        }
        return null;
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

 }

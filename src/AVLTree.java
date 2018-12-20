import javafx.collections.transformation.SortedList;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import sun.awt.SunHints;

import java.util.*;


public class AVLTree <Key extends Comparable <? super Key>, Value extends Comparable> implements SortedMap<Key, Value> {
    private Node root;

    private Node insert(Node place, Node node) {
        if (place == null) {
            return node;
        }

        int comparison = place.key.compareTo(node.key);
        if (comparison == 0) {
            place.value = node.value;
        } else if (comparison < 0) {
            place.leftChild = insert(place.leftChild, node);
        } else {
            place.rightChild = insert(place.rightChild, node);
        }
        return place.balanceNode();
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            throw new IllegalArgumentException();
        }

        int comp = node.key.compareTo(key);
        if (comp == 0) {
            if (node.leftChild == null && node.rightChild == null) {
                return null;
            } else if (node.leftChild == null) {
                return node.rightChild;
            } else if (node.rightChild == null) {
                return node.leftChild;
            } else {
                Node left = node.rightChild;
                while (left.leftChild != null) {
                    left = left.leftChild;
                }

                left.rightChild = deleteLeftMost(node.rightChild);
                left.leftChild = node.leftChild;

                return left.balanceNode();
            }
        } else if (comp < 0) {
            node.leftChild = delete(node.leftChild, key);
        } else {
            node.rightChild = delete(node.rightChild, key);
        }

        return node.balanceNode();
    }


    private Node deleteLeftMost(Node node) {
        if (node.leftChild == null) {
            return node.rightChild;
        } else {
            node.leftChild = deleteLeftMost(node.leftChild);
            return node.balanceNode();
        }
    }


    public void insert(Key key, Value value) {
        Node node = new Node(key, value);
        root = insert(root, node);
        keys.add(key);
        entries.add(node);
    }

    public void delete(Key key) {
        root = delete(root, key);
        keys.remove(key);
        entries.remove(get(key));
    }


    List<Key> keys = new ArrayList<>();                                 //lists with related indexes
    List<Node<Key, Value>> entries = new ArrayList<>();


    @Override
    public Comparator<? super Key> comparator() {
        return (Comparator<Key>) Comparator.naturalOrder();
    }

    @Override
    public SortedMap<Key, Value> subMap(Key fromKey, Key toKey) {
        return null;
    }

    @Override
    public SortedMap<Key, Value> headMap(Key toKey) {
        return null;
    }

    @Override
    public SortedMap<Key, Value> tailMap(Key fromKey) {
        return null;
    }


    @Override
    public Key firstKey() {
        return keys.get(0);
    }

    @Override
    public Key lastKey() {
        return keys.get(keys.size()-1);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node entry: entries) {
            if (entry.compareValues(value)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public Value get(Object key) {
        int index = keys.indexOf(key);
        return entries.get(index).getValue();
    }



    @Override
    public Value put(Key key, Value value) {
        insert(key, value);
        return entries.get(entries.size()-1).value;
    }



    @Override
    public Value remove(Object key) {
        int index = keys.indexOf(key);
        Node node = entries.get(index);
        delete((Key) key);
        return (Value) node.value;
    }



    @Override
    public void putAll(Map<? extends Key, ? extends Value> m) {
        for(Key element: m.keySet()) {
           Key key = element;
           Value value = m.get(element);
            put(key, value);
        }
    }



    @Override
    public void clear() {
        keys.clear();
        entries.clear();
        root = null;
    }



    @Override
    public Set<Key> keySet() {
        Set<Key> set = new TreeSet<>();
        for(Key key: keys) {
            set.add(key);
        }
        return set;
    }



    @Override
    public Collection<Value> values() {
        return null;
    }



    @Override
    public Set<Entry<Key, Value>> entrySet() {
        Set<Entry<Key, Value>> set = new TreeSet<>();
        for(Node node: entries) {
            set.add(node);
        }
        return set;
    }


}

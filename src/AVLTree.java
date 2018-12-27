import java.util.*;


public class AVLTree <Key extends Comparable <Key>, Value extends Comparable> implements Map<Key, Value> {
    private Node root;

    private Node<Key, Value> insert(Node<Key, Value> place, Node<Key, Value> node) {
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


    private void insert(Key key, Value value) {
        Node<Key, Value> node = new Node<>(key, value);
        root = insert(root, node);
        keys.add(key);
        values.add(value);
        entries.add(node);
    }

    private void delete(Key key) {
        values.remove(get(key));
        keys.remove(key);
        entries.remove(root.findByKey(key));
        root = delete(root, key);
    }


    private Set<Key> keys = new HashSet<>();
    private Set<Value> values = new HashSet<>();
    private  Set<Entry<Key, Value>> entries = new HashSet<>();


    private boolean equals(Map<Key, Value> map){
        return this.entrySet().equals(map.entrySet());
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Map) {
            return this.equals((Map<Key, Value>) o);
        }
        else return false;
    }

    public int hashCode() {
        int hashCode = 0;
        for(Entry node: entries) {
            hashCode+=node.hashCode();
        }
        return hashCode;
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
        if(root!= null) {
            return root.findByKey((Key) key) == null;
        }
        else return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains(value);
    }



    @Override
    public Value get(Object key) {
        return (Value) root.findByKey((Key) key).getValue();
    }



    @Override
    public Value put(Key key, Value value) {
        insert(key, value);
        return  (Value) root.findByKey(key).getValue();
    }



    @Override
    public Value remove(Object key) {
        Node<Key, Value> node = root.findByKey((Key) key );
        delete((Key) key);
        return node.getValue();
    }



    @Override
    public void putAll(Map<? extends Key, ? extends Value> m) {
        for(Key element: m.keySet()) {
           Value value = m.get(element);
           put(element, value);
        }
    }



    @Override
    public void clear() {
        keys.clear();
        values.clear();
        entries.clear();
        root = null;
    }



    @Override
    public Set<Key> keySet() {
        return keys;
    }



    @Override
    public Collection<Value> values() {
        return values;
    }



    @Override
    public Set<Entry<Key, Value>> entrySet() {
        return entries;
    }

























    abstract public class AVLSet<O extends Comparable> implements Set<O> {

        private class Element<O extends Comparable> implements Comparable<O> {

            private O value;

            O previous;
            O next;

            Element(O value, O previous, O next) {
                this.value = value;
                this.previous = previous;
                this.next = next;
            }

            @Override
            public int compareTo(O o) {
                return this.compareTo(o);
            }

        }





        private O first;
        private O last;
        private int size = 0;

        AVLSet(O o) {
            this.add(o);
            this.first = o;
            this.size = 1;
        }

        @Override
        public int size() {
            return size;
        }


        private boolean addElement(O o) {
            Element<O> element = new Element<>(o, last, null);
            last = o;
            return true;
        }

        @Override
        public boolean contains(Object o) {
            AVLIterator iterator = new AVLIterator(this);
            while (iterator.hasNext()) {
                if(iterator.next().equals(o)) {
                    return true;
                }
            }
            return false;
        }


        @Override
        public boolean remove(Object o) {
            AVLIterator iterator = new AVLIterator(this);
            while (iterator.hasNext()) {
                if(iterator.next().equals(o)) {
                    iterator.remove();
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }



        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public void clear() { }







        @Override
        public Iterator<O> iterator() {
            return new AVLIterator(this);
        }

        private class AVLIterator implements Iterator<O> {



            private O current;
            private int quantity;
            private AVLSet<O> set;
            List<O> cache = new ArrayList<>();


            AVLIterator(AVLSet<O> set){
                this.set = set;
                cache.addAll(set);
                quantity = cache.size();
            }



            @Override
            public boolean hasNext() { return quantity >= 0; }


            @Override
            public O next() throws NoSuchElementException {
                if (quantity == 0) {
                    throw new NoSuchElementException("Set is empty.");
                }
                current = cache.get(0);
                cache.remove(0);
                quantity--;
                return current;
            }


            @Override
            public int hashCode() {
                int hashCode = 0;
                for(O o: set) {
                    hashCode += o.hashCode();
                }
                return hashCode;
            }


            @Override
            public void remove() {
                set.remove(current);
            }
        }
    }




}

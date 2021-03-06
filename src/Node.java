import java.util.Comparator;
import java.util.Map;

public class Node<Key extends Comparable<Key>, Value extends Comparable> implements Map.Entry<Key, Value> {

    Value value;
    Key key;

    Node<Key, Value> leftChild;
    Node<Key, Value> rightChild;
    Node<Key, Value> parent;


    private int height;                            //subtree height


    Node(Key key, Value value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }

    private boolean compareValues(Value v) {
        return this.value.equals(v);
    }

    private boolean compareKeys(Key k) {
        return this.key.equals(k);
    }

    Node<Key, Value> findByKey(Key key) {
        Node<Key, Value> node = this;
        while(!node.key.equals(key)) {
            int comparison = node.key.compareTo(key);
            if(comparison==0)
                return node;
            if(comparison<0)
                node = node.leftChild;
            if(comparison>0)
                node = node.rightChild;

            if(node == null)
                return null;
        }
        return node;
    }

    private Node rotateRight() {
        Node node = this.leftChild;

        this.leftChild = node.rightChild;
        this.subtreeHeight();

        node.rightChild = this;
        node.subtreeHeight();

        node.parent = this.parent;
        return node;
    }

    private Node rotateLeft() {
        Node node = this.rightChild;

        this.rightChild = node.leftChild;
        this.subtreeHeight();

        node.leftChild = this;
        node.subtreeHeight();

        return node;
    }




    private void subtreeHeight() {
        height = 1;
        if (leftChild != null) {
            height += leftChild.height;
        }
        if (rightChild != null) {
            height += rightChild.height;
        }
    }


    private int getBalance()                      //balancing factor
    {
        int balance = 0;
        if (leftChild != null) {
            balance += leftChild.height;
        }
        if (rightChild != null) {
            balance -= rightChild.height;
        }

        return balance;
    }


     Node balanceNode() {
        subtreeHeight();
        int balance = getBalance();
        if (balance >= 2) {
            if (leftChild.getBalance() < 0) {
                leftChild = leftChild.rotateLeft();
            }
            return rotateRight();
        }
        else if (balance <= -2) {
            if (rightChild.getBalance() > 0) {
                rightChild = rightChild.rotateRight();
            }
            return rotateLeft();
        }

        return this;
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public Value getValue() {
        return value;
    }

    @Override
    public Value setValue(Value value) {
        this.value = value;
        return value;
    }


    public Comparator<? super Value> comparator() {
        return (Comparator<Value>) Comparator.naturalOrder();
    }


    public int hashCode() {
        return (this.getKey()==null   ? 0 : this.getKey().hashCode()) ^
              (this.getValue()==null ? 0 : this.getValue().hashCode());
    }



    private boolean equals(Map.Entry<Key, Value> entry) {
        return (this.getKey()==null ? entry.getKey()==null :
                this.getKey().equals(entry.getKey()))  &&
              (this.getValue()==null ? entry.getValue()==null :
                      this.getValue().equals(entry.getValue()));
    }


    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Map.Entry) {
           return equals((Map.Entry<Key, Value>) obj);
       }
       else return false;
    }
}


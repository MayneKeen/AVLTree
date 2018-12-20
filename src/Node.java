import java.util.Map;

public class Node<Key extends Comparable, Value extends Comparable> implements Map.Entry<Key, Value> {

    Value value;
    Key key;

    public Node leftChild;
    public Node rightChild;
    public Node parent;


    public int height;                            //subtree height


    public Node(Key key, Value value) {
        this.key = key;
        this.value = value;
        this.height = 1;
    }

    boolean compareValues(Object o) {
        return this.value.equals(o);
    }

    boolean compareKeys(Object o) {
        return this.key.equals(o);
    }


    public Node rotateRight() {
        Node node = this.leftChild;

        this.leftChild = node.rightChild;
        this.subtreeHeight();

        node.rightChild = this;
        node.subtreeHeight();

        node.parent = this.parent;
        return node;
    }

    public Node rotateLeft() {
        Node node = this.rightChild;

        this.rightChild = node.leftChild;
        this.subtreeHeight();

        node.leftChild = this;
        node.subtreeHeight();

        return node;
    }




    public void subtreeHeight() {
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


}


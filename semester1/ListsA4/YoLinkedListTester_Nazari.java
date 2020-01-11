import java.util.NoSuchElementException;
import java.util.ListIterator;

/**
 * Tester for all methods of linked list
 */
public class YoLinkedListTester_Nazari {
    public static void main(String[] args) {
        System.out.println("im so gassed part 2");

        YoLinkedList<String> list = new YoLinkedList<String>();

        list.addLast("please");
        list.addFirst("sleep");
        list.addFirst("lemme");
        list.addLast("!!!");
        System.out.println("To string       => " + list.toString());

        System.out.println("First element   => " + list.getFirst());

        list.removeFirst();
        System.out.println("Remove first    => " + list.toString());

        list.removeLast();
        System.out.println("Remove last     => " + list.toString());

        System.out.println("Get index 1     => " + list.get(1));

        list.add(2, "!");
        System.out.println("Add '!' index 2 => " + list.toString());

        list.remove(2);
        System.out.println("Remove index 2  => " + list.toString());

        System.out.println("Size            => " + list.size());

        list.addLast("1");
        list.addLast("2");
        list.addLast("3");
        System.out.println("Adding to last  => " + list.toString());

        list.reverse();
        System.out.println("Reversed order  => " + list.toString());
    }
}

/**
 * Custom data structure mimicking a linked list
 * super sexy ngl
 * @param <E> class type of linked list
 */
class YoLinkedList<E> {

    private Node first;
    private Node last;
    
    private class Node 	{ 
        public E data;
        public Node next;
        public Node previous;
    }
    
    public YoLinkedList() {
        first = null;
        last = null;
    }
    
    /**
     * @return the first element in the linked list
     */	
    public E getFirst() {
        if (first == null) throw new NoSuchElementException();
        return first.data;
    }
    
    /**
     * Removes the first element in the linked list.
     * @return the removed element
     */
    public E removeFirst() {
        if (first == null) throw new NoSuchElementException();
        E element = first.data;
        first = first.next;
        first.previous = null;
        
        return element;
    }

    /**
     * Adds an element to the front of the linked list.
     * @param element the data to store in the linked list
     */
    public void addFirst(E element) {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = first;
        newNode.previous = null;
        first = newNode;
        if (first.next != null) first.next.previous = first;
        if (last == null) last = first;
    }

    /**
     * Adds element to the end of the linked list
     * @param element the data to store
     */
    public void addLast(E element) {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = null;
        newNode.previous = last;
        last = newNode;
        if (last.previous != null) last.previous.next = last;
        if (first == null) first = newNode;
    }

    /**
     * Removes the last element of the list
     * @return data removed
     */
    public E removeLast() {
        if (last == null) throw new NoSuchElementException();
        E element = last.data;
        last = last.previous;
        last.next = null;

        return element;
    }

    /**
     * Private getter for node at index
     * @param index index of Node
     * @return node at index
     */
    private Node getNode(int index) {
        int size = size();
        if (index < 0 || index > size-1) throw new NoSuchElementException();
        Node currentNode = first;
        for (int i = 0; i < index; i++) currentNode = currentNode.next;
        
        return currentNode;
    }

    /**
     * Public getter for data of node at index
     * @param index index of node
     * @return Node at index
     */
    public E get(int index) {
        return getNode(index).data;
    }

    /**
     * Adds node with data at index
     * @param index index to add node
     * @param element data for node
     */
    public void add(int index, E element) {
        int size = size();
        if (index < 0 || index > size) throw new NoSuchElementException();
        else if (index == 0) addFirst(element);
        else if (index == size) addLast(element);
        else {
            Node nodeBefore = getNode(index-1);
            Node nodeAt = getNode(index);
            Node newNode = new Node();
            newNode.data = element;
            newNode.next = nodeAt;
            newNode.previous = nodeBefore;
            nodeBefore.next = newNode;
            nodeAt.previous = newNode;
        }
    }

    /**
     * Removes node at index
     * @param index index to remove node
     * @return data of node removed
     */
    public E remove(int index) {
        int size = size();
        if (index < 0 || index > size-1) throw new NoSuchElementException();
        if (index == 0) return removeFirst();
        if (index == size-1) return removeLast();
        Node nodeBefore = getNode(index-1);
        Node nodeAfter = getNode(index+1);
        E element = getNode(index).data;
        nodeBefore.next = nodeAfter;
        nodeAfter.previous = nodeBefore;

        return element;
    }

    /**
     * Getter for size of linked list
     * @return size of linked list
     */
    public int size() {
        int size = 0;
        Node currentNode = first;
        while (currentNode != null) {
            currentNode = currentNode.next;
            size++;
        }

        return size;
    }

    /**
     * Reverses order of linked list
     */
    public void reverse() {
        Node left = first;
        Node right = last;
        for (int i = 0; i < size()/2; i++) {
            E temp = left.data;
            left.data = right.data;
            right.data = temp;
            left = left.next;
            right = right.previous;
        }
    }

    /**
     * Stringify method
     * @return linked list as string
     */
    public String toString() {
        String string = "[  ";
        for (int i = 0; i < size(); i++) string += "\"" + get(i).toString() + "\"  ";
        string += "]";

        return string;
    }
}
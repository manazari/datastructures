import java.util.NoSuchElementException;
import java.util.ListIterator;

/**
 * Tester for all methods of linked list
 */
public class SingledLinkListTester_Nazari {
    public static void main(String[] args) {
        System.out.println("im so gassed rn");

        SingleLinkList<String> list = new SingleLinkList<String>();

        list.addFirst("please");
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
    }
}

/**
 * Custom data structure mimicking a linked list
 * super sexy ngl
 * @param <E> class type of linked list
 */
class SingleLinkList<E> {

	private Node first;
   
	private class Node 	{ 
		public E data;
		public Node next;
	}
	
	public SingleLinkList() {
		first = null;
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
		first = newNode;
    }

    /**
     * Adds element to the end of the linked list
     * @param element the data to store
     */
    public void addLast(E element) {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = null;
        Node lastNode = getNode(size()-1);
        lastNode.next = newNode;
    }

    /**
     * Removes the last element of the list
     * @return data removed
     */
    public E removeLast() {
        int size = size();
        if (size == 0) throw new NoSuchElementException();
        if (size == 1) first = null;
        Node secondLastNode = getNode(size-2);
        E element = secondLastNode.next.data;
        secondLastNode.next = null;

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
        if (index == 0) addFirst(element);
        else {
            Node nodeBefore = getNode(index-1);
            Node newNode = new Node();
            newNode.data = element;
            newNode.next = nodeBefore.next;
            nodeBefore.next = newNode;
        }
    }

    /**
     * Removes node at index
     * @param index index to remove node
     * @return data of node removed
     */
    public E remove(int index) {
        if (index < 0 || index > size()-1) throw new NoSuchElementException();
        if (index == 0) return removeFirst();
        Node nodeBefore = getNode(index-1);
        E element = nodeBefore.next.data;
        nodeBefore.next = nodeBefore.next.next;

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
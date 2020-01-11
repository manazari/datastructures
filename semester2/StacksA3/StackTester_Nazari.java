import java.util.EmptyStackException;

/**
 * This class implements an program
 * which tests the LinkedStack class
 * by creating an instance of it and
 * calling multiple of its methods.
 * The terminal arguments are unused.
 * 
 * Assignment StacksA3
 * @author Matthew Nazari
 * @since 9 Jan 2020
 */
public class StackTester_Nazari {
    /**
     * This method implements the test
     * of the LinkedStack class by
     * calling its methods and producing
     * a clear output
     * @param args unused
     */
    public static void main(String[] args) {
        LinkedStack<String> dishes = new LinkedStack<>();

        // Adding then removing letters of "HELLO" to stack
        System.out.println("Is it empty: " + dishes.isEmpty());
        System.out.println("Now pushing H, E, L, L, O");
        dishes.push("H");
        dishes.push("E");
        dishes.push("L");
        dishes.push("L");
        dishes.push("O");
        System.out.println("The top element is: " + dishes.peek());
        while (!dishes.isEmpty()) System.out.println("Popping: " + dishes.pop());

        // Adding then removing numbers 1 through 5 inclusive
        System.out.println("Is it empty: " + dishes.isEmpty());
        System.out.println("Now pushing 1");
        dishes.push("1");
        System.out.println("Is it empty: " + dishes.isEmpty());
        System.out.println("Now pushing 2, 3, 4, 5");
        dishes.push("2");
        dishes.push("3");
        dishes.push("4");
        dishes.push("5");
        System.out.println("The top element is: " + dishes.peek());
        System.out.println("Removing " + dishes.pop());
        System.out.println("Removing " + dishes.pop());
        System.out.println("Now pushing Last");
        dishes.push("Last");
        System.out.println("The top element is: " + dishes.peek());
        while (!dishes.isEmpty()) System.out.println("Popping: " + dishes.pop());
    }
}

/**
 * This interface holds the
 * basic operations of any
 * Stack data structure and
 * has a generic so that it
 * can hold any type of class
 */
interface YoStack<E> {
    public boolean isEmpty();
    public E push(E e);
    public E pop() throws EmptyStackException;
    public E peek() throws EmptyStackException;
}

/**
 * This class is a linked stack which
 * implements basic stack operations
 * from YoStack. LinkedStack is also
 * generic, meaning it will be able
 * to hold any type of class. It behaves
 * as a linked list in how it holds
 * a reference to a first Node which
 * holds data of generic type and the
 * reference to the "next" Node in the
 * stack
 */
class LinkedStack<E> implements YoStack<E> {
    private Node top;

    /**
     * Default constructor just sets
     * the top Node to null
     */
    public LinkedStack() {
        top = null;
    }

    /**
     * This class is a super simple
     * wrapper for data of generic
     * type E and a reference to
     * the next node in the list
     */
    class Node {
        E data;
        Node next;
    }

    /**
     * The stack will be considered
     * empty if the first Node does
     * not exist, meaning the top
     * reference would equal null
     * 
     * @return boolean  if stack is empty
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * This method accepts data to which
     * it adds onto the stack by storing
     * the data in a new Node. This new
     * Node will then become the new
     * top of the stack
     * 
     * @param e  data to add to stack
     * @return E  parameter e
     */
    public E push(E e) {
        Node node = new Node();
        node.data = e;
        node.next = isEmpty() ? null : top;
        top = node;
        return e;
    }

    /**
     * This method will remove the
     * data stored at the first spot
     * on top of the stack
     * 
     * @return E  data just popped
     * @exception ; if stack is empty
     */
    public E pop() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        E topData = top.data;
        top = top.next;
        return topData;
    }

    /**
     * This method is useful for
     * checking what is on the top
     * of the stack without actually
     * modifying the stack in any way
     * 
     * @return E  data at top of stack
     * @exception ; if stack is empty
     */
    public E peek() throws EmptyStackException {
        if (isEmpty()) throw new EmptyStackException();
        return top.data;
    }
}
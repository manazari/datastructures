/**
 * The ZoArrayListTester_Nazari program implements
 * an application that creates an instance of
 * ZoArrayList and tests their methods
 * 
 * @author  Matthew Nazari
 * @since   2019-09-24
 */
public class ZoArrayListTester_Nazari {
    /**
     * This is the main method which explores the
     * ZoArrayList class through creating an 
     * instance `zo`, adding elements, then 
     * triggering the custom out of bounds error
     * exception by attempting to add at index -1
     * @param termArgs  unused
     */
    public static void main(String[] termArgs) throws Exception {
        ZoArrayList<Integer> zo = new ZoArrayList<Integer>();

        System.out.println("\nPOPULATING");
        for (int i = 0; i < 5; i++) zo.add(i);
        zo.details();

        System.out.println("\nRELEASING");
        zo.release();
        zo.details();

        System.out.println("\nREMOVING INDEX 2");
        zo.remove(2);
        zo.details();

        System.out.println("\nADDING 99 AT INDEX 4 TWICE");
        zo.add(4, 99);
        zo.add(4, 99);
        zo.details();

        System.out.println("\nINDEX OF");
        System.out.println("# Index of 99: " + zo.indexOf(99));
        System.out.println("# Index of 69: " + zo.indexOf(69));

        System.out.println("\nSETTING INDEX 3 to -10");
        zo.set(-10, 3);
        zo.details();

        System.out.println("\nGETTING INDEX 1000");
        zo.get(1000);
        zo.details();
    }
}

/**
 * ZoArrayList class is a wrapper class for
 * a private int array with methods for appending
 * elements, getting elements, and printing details
 * like size of the array. For efficiency, the array
 * will be initialized with length 10 by default
 * unless specified otherwise.
 */
class ZoArrayList<T> {
    /**
     * The private array to hold all elements
     * and the simulated size of the list
     */
    private T[] array;
    private int size;
    
    /**
     * Constructor initializes the array
     * property.
     * @param size int   size to initialize
     * the array
     */
    public ZoArrayList(int size) {
        this.size = 0;
        this.array = (T[]) new Object[size];
    }

    /**
     * Empty constructor Initializes array
     * to an int array of length 10
     */
    public ZoArrayList() {
        this.size = 0;
        this.array = (T[]) new Object[10];
    }

    /**
     * This method inserts an element at a specific
     * index to the array. This is done through
     * creating a new int[] array, copying all
     * elements up to index, inserting element at
     * index, then copying all elements after index
     * from old array to new array. If the array 
     * property is larger than the simulated
     * ZoArrayList size represented by property
     * size, a new array won't be created.
     * 
     * @param element int   element to be added
     * @param index   int   index to add element
     * @throws Exception    epic out of bounds error
     */
    public void add(int index, T element) throws Exception {
        checkIndexError(index, true);
        T[] newArray = size < array.length ? array : (T[]) new Object[array.length * 2];
        for (int i = 0; i < index; i++) newArray[i] = array[i];
        for (int i = size; i > index; i--) newArray[i] = array[i-1];
        newArray[index] = element;
        array = newArray;
        size++;
    }

    /**
     * This method calls the add method passing
     * the array size as the index parameter in
     * order to add the element at the end of the
     * array
     * @param element int   element to be added
     * @throws Exception    epic out of bounds error
     */
    public void add(T element) throws Exception {
        add(size, element);
    }

    /**
     * This method returns the index of the first
     * element to match element
     * @param element   element to find index of
     * @return          index of element
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) if (array[i] == element) return i;
        return -1;
    }

    /**
     * Removes the element at index by shifting
     * over the rest of the array left
     * @param index         index of elem to remove
     * @throws Exception    epic out of bounds error
     */
    public void remove(int index) throws Exception {
        checkIndexError(index, false);
        for (int i = index; i < size - 1; i++) array[i] = array[i+1];
        size--;
    }

    /**
     * This method removes the first element
     * in the array
     * @param element       element to remove
     * @throws Exception    epic out of bounds error
     */
    public void remove(T element) throws Exception {
        remove(indexOf(element));
    }

    /**
     * This method gets the element at index
     * @param index int     index of saught element
     * @return int          element at index
     * @throws Exception    epic out of bounds error
     */
    public T get(int index) throws Exception {
        checkIndexError(index, false);
        return array[index];
    }

    /**
     * This method sets the element at index
     * to the parameter element
     * @param element       element to insert
     * @param index         index of element to set
     * @throws Exception    epic out of bounds error
     */
    public void set(T element, int index) throws Exception {
        checkIndexError(index, false);
        array[index] = element;
    }

    /**
     * This method tightens the ZoArrayList,
     * meaning that it will copy all elements over
     * to a new array of length size. This way
     * memory is saved and all unused space is
     * eliminated
     */
    public void release() {
        T[] newArray = (T[]) new Object[size];
        for (int i = 0; i < size; i++) newArray[i] = array[i];
        array = newArray;
    }
        
    /**
     * This method is a getter for the size
     * property which simulates the ZoArrayList's
     * length
     * @return int  {@link ZoArrayList#size}
     */
    public int size() {
        return size;
    }

    /**
     * This method prints the size of the array
     * and then all elements of the array in
     * order to visually check the status of the
     * array property
     */
    public void details() {
        System.out.println("Size: " + size + " | Length: " + array.length);
        for (int i = 0; i < size; i++) System.out.println("# " + i + ": " + array[i]);
    }

    /**
     * This method will throw an error if the
     * index is an invalid indext for the
     * ZoArrayList in respect to its simulated
     * size
     * @param index         index to check if valid
     * @param inclusive     will allow index 1 above last
     * @throws Exception    epic out of bounds error
     */
    private void checkIndexError(int index, boolean inclusive) throws Exception {
        if (index > (inclusive ? size : size - 1) || index < 0)
            throw new Exception("\n\n## THAT'S ILLEGAL!\n   index " + index + " out of bounds (size " + size + ")");
    }
}
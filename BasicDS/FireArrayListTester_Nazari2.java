/**
 * he FireArrayListTester_Nazari program implements
 * an application that creates two instance of
 * FireArrayList and tests their methods
 * 
 * @author  Matthew Nazari
 * @since   2019-09-11
 */
public class FireArrayListTester_Nazari2 {
    /**
     * This is the main method which explores the
     * FireArrayList class through creating two
     * instances `fire` and `water`, adding
     * elements to either, then printing their
     * details
     * @param termArgs  unused
     */
    public static void main(String[] termArgs) throws Exception {
        FireArrayList fire = new FireArrayList();
        fire.add(1);
        fire.add(2);
        fire.add(3);
        fire.add(4);
        fire.add(5);
        fire.add(6);
        fire.add(7);
        fire.add(8);
        fire.add(9);
        fire.add(10);
        fire.add(11);
        fire.add(99, 4);
        fire.details();

        fire.add(13, -1);
    }
}

/**
 * FireArrayList class is a wrapper class for
 * a private int array with methods for appending
 * elements, getting elements, and printing details
 * like size of the array. For efficiency, the array
 * will be initialized with length 10 by default
 * unless specified otherwise.
 */
class FireArrayList {
    /**
     * The private array to hold all elements
     */
    private int[] array;
    private int length;
    
    /**
     * Constructor initializes the array
     * property.
     * @param length int   length to initialize
     * the array
     */
    public FireArrayList(int length) {
        this.length = 0;
        this.array = new int[length];
    }

    /**
     * Empty constructor Initializes array
     * to an int array of length 10
     */
    public FireArrayList() {
        this.length = 0;
        this.array = new int[10];
    }

    /**
     * This method inserts an element at a specific
     * index to the array. This is done through
     * creating a new int[] array, copying all
     * elements up to index, inserting element at
     * index, then copying all elements after index
     * from old array to new array. If the array 
     * property is 
     * @param element int   element to be added
     * @param index   int   index to add element
     */
    public void add(int element, int index) throws Exception {
        if (index > length || index < 0)
            throw new Exception("\n\n## THAT'S ILLEGAL!\n   index " + index + " out of bounds (length " + length + ")");
        int[] newArray = index < array.length ? array : new int[array.length + 10];
        for (int i = 0; i < index; i++) newArray[i] = array[i];
        for (int i = index; i < length; i++) newArray[i+1] = array[i];
        newArray[index] = element;
        array = newArray;
        length++;
    }

    /**
     * This method calls the add method passing
     * the array length as the index parameter in
     * order to add the element at the end of the
     * array
     * @param element int   element to be added
     */
    public void add(int element) throws Exception {
        add(element, size());
    }

    /**
     * This method is a getter for the length
     * property which simulates the FireArrayList's
     * length.  
     * @return int  {@link FireArrayList#length}
     */
    public int size() {
        return length;
    }

    /**
     * This method gets the element at index
     * @param index int     index of saught element
     * @return int          element at index
     */
    public int get(int index) {
        return array[index];
    }

    /**
     * This method prints the size of the array
     * and then all elements of the array in
     * order to visually check the status of the
     * array property
     */
    public void details() {
        System.out.println("\nSize: " + size());
        for (int i = 0; i < size(); i++)
            System.out.println("# " + i + ": " + get(i));
    }
}
/**
 * he FireArrayListTester_Nazari program implements
 * an application that creates two instance of
 * FireArrayList and tests their methods
 * 
 * @author  Matthew Nazari
 * @since   2019-09-11
 */
public class FireArrayListTester_Nazari {
    /**
     * This is the main method which explores the
     * FireArrayList class through creating two
     * instances `fire` and `water`, adding
     * elements to either, then printing their
     * details
     * @param termArgs  unused
     */
    public static void main(String[] termArgs) {
        FireArrayList fire = new FireArrayList();
        fire.add(10);
        fire.add(15);
        fire.add(50, 1);
        fire.details();

        FireArrayList water = new FireArrayList(new int[]{1, 2, 3, 4, 5});
        water.add(99, 2);
        water.details();
    }
}

/**
 * FireArrayList class is a wrapper class for
 * a private int array with methods for appending
 * elements, getting elements, and printing details
 * like size of the array
 */
class FireArrayList {
    /**
     * The private array to hold all elements
     */
    private int[] array;
    
    /**
     * Constructor initializes the array
     * field.
     * @param array int[]   the array used
     * to initialize the array property
     */
    public FireArrayList(int[] array) {
        this.array = array;
    }

    /**
     * Empty constructor Initializes array
     * to an empty int array
     */
    public FireArrayList() {
        this.array = new int[]{};
    }

    /**
     * This method inserts an element at a specific
     * index to the array. This is done through
     * creating a new int[] array, copying all
     * elements up to index, inserting element at
     * index, then copying all elements after index
     * from old array to new array
     * @param element int   element to be added
     * @param index   int   index to add element
     */
    public void add(int element, int index) {
        int[] newArray = new int[array.length + 1];
        for (int i = 0; i < index; i++) newArray[i] = array[i];
        newArray[index] = element;
        for (int i = index; i < array.length; i++) newArray[i+1] = array[i];
        array = newArray;
    }

    /**
     * This method calls the add method passing
     * the array length as the index parameter in
     * order to add the element at the end of the
     * array
     * @param element int   element to be added
     */
    public void add(int element) {
        add(element, array.length);
    }

    /**
     * This method is a getter for the array
     * property's length
     * @return int  length of array property
     */
    public int size() {
        return array.length;
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
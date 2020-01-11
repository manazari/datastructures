/**
* The MergeTester_Nazari program implements
* a program that will test the merge
* an genArray methods by generating 2 arrays
* then merging them
*
* @author  Matthew Nazari
* @since   2019-11-03
*/
public class MergeTester_Nazari {
    /**
     * main method implements program which
     * creates 2 randomly sized, sorted arrays
     * from the genArray method and then
     * printing them alongside their merged
     * array found with the merge method
     * @param args not used
     */
    public static void main(String [] args)
    {
        int[] arr1 = genArray();
        int[] arr2 = genArray();
        int[] mergedArr = merge(arr1, arr2);

        System.out.print("\nARRAY 1 => ");
        printArray(arr1);
        System.out.print("\nARRAY 2 => ");
        printArray(arr2);

        System.out.print("\n\nMERGED ARRAY => ");
        printArray(mergedArr);
    }

    /**
     * This method will create and return
     * an array of increasing order
     * between MIN_VALUE and MAX_VALUE and
     * random size MIN_SIZE to MAX_SIZE. To
     * ensure an appropriately sized spread
     * between each of the elements, the randomized
     * step has the exact step as a factor
     * 
     * @return int[] sorted array of random size
     */
    public static int[] genArray() {
        final int MIN_SIZE = 900;
        final int MAX_SIZE = 1100;
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 10000;

        final int ARR_SIZE = (int)(Math.random()*(MAX_SIZE - MIN_SIZE) + MIN_SIZE);
        final int STEP_SIZE = (MAX_VALUE - MIN_VALUE) / ARR_SIZE;

        int[] arr = new int[ARR_SIZE];
        for (int i = 0; i < arr.length; i++) {
            int step = (int)(Math.random()*STEP_SIZE*2); // double so it'll average out to STEP_SIZE
            arr[i] = Math.min((i == 0 ? MIN_VALUE : arr[i-1]) + step, MAX_VALUE);
        }
        return arr;
    }

    /**
     * this method will print an arrays
     * length and all elements of it
     * 
     * @param arr array to print
     */
    public static void printArray(int[] arr) {
        System.out.print("length of " + arr.length + " [");
        for (int x : arr) System.out.print(x + ", ");
        System.out.println("]");
    }

    /**
     * this method will merge two arrays
     * of increasing order into a single
     * array also sorted by increasing
     * order
     * 
     * @param arr1 first array to merge
     * @param arr2 second array to merge
     * @return int[] merged array
     */
    public static int[] merge(int[] arr1, int[] arr2) {
        int[] newArr = new int[arr1.length + arr2.length];
        for (int i = 0, j = 0, k = 0; i < newArr.length; i++) {
            if (j >= arr1.length) {
                newArr[i] = arr2[k];
                k++;
                continue;
            }
            if (k >= arr2.length) {
                newArr[i] = arr1[j];
                j++;
                continue;
            }
            
            int nextArr1Elem = arr1[j];
            int nextArr2Elem = arr2[k];
            if (nextArr1Elem <= nextArr2Elem) {
                j++;
                newArr[i] = nextArr1Elem;
            } else {
                k++;
                newArr[i] = nextArr2Elem;
            }
        }
        return newArr;
    }
}
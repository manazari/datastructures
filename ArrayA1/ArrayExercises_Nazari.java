import java.lang.Math;
import java.util.List;
import java.util.Arrays;

/**
* The ArrayExercises class contains two methods corresponding
* to book problems R7.23 and E7.19 respectively. The comment
* between both methods is the solution to problem R7.24.
* This class contains these methods as public and static as
* to allow a tester class to effectively call these methods.
*
* @author  Matthew Nazari
* @since   2019-09-04
*/
public class ArrayExercises_Nazari {
    /**
     * This method searches through the passed array
     * and returns the length of the length of the
     * largest subarray of equivalent integers (the 
     * length of the longest "run"). This is achieved
     * by keeping a count every time the current index
     * is the same as the previous and resetting this
     * count once theres a change.
     * 
     * @param nums int[]    an array of integers
     * @return int          length of longest  sequencial
     *                      sequence of integers
     */
    public static int longestRun(int []nums) {
        int longestRunLength = 0;
        for (int i = 1, currentRunLength = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) currentRunLength ++;
            else currentRunLength = 1;
            longestRunLength = Math.max(currentRunLength, longestRunLength);
        }
        return longestRunLength;
    }

    /*
    R7.24
    In Java, when an array is passed into a function,
    the elements of the array object can be
    manipulated. However, the array variable
    cannot be assigned to a new object which the
    method is trying to do. So this function will
    NOT change the passed array object.

    To fix this, the method should update the
    passed method by changing the elements in
    the array and not try to assign a new
    array into the reference.
    */


    /** 
     * This method takes an Integer List and
     * creates a vertical bar graph in the
     * terminal of each value with a max height
     * of 20. This is achieved by first scaling every
     * element in the passed List by a factor which
     * leaves the maximum entry at 20. This way, the
     * numerical range of nums is 20. Then print the
     * chart starting from the top row and only put
     * an "*" if the row is less or equal to the
     * element.
     * 
     * @param nums List<Integer>    list of integers
     */
    public static void verticalChart(List<Integer> nums) {
        if (nums.size() == 0) return;
        int max = nums.get(1);
        for (int num : nums) max = num > max ? num : max;
        final int MAX = max;

        System.out.println("\n###### CHART OF " + nums);
        nums.replaceAll(num -> Math.round(20 * num/MAX));
        for (int row = 20; row > 0; row--) {
            String rowString = "";
            for (int num : nums) {
                rowString += row <= num ? "*" : " ";
            }
            System.out.println(rowString);
        }
    }
}




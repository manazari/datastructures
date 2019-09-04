import java.util.List;
import java.util.Arrays;

/**
* The ArrayA1Tester_Nazari program implements an application
* that tests the ArrayExercises_Nazari class methods.
* Arrays of ints are created with certain "runs" then
* multiple Integer Lists are created to be printed as
* vertical bar charts.
*
* @author  Matthew Nazari
* @since   2019-08-25
*/
public class ArrayA1Tester_Nazari {
    /**
     * This is the main method which explores the
     * two methods to ArrayEcercises_Nazari class.
     * 
     * @param args  unused
     */
    public static void main(String []terminalArgs) {
        int []sevenLongRun = {1, 7, 7, 7, 7, 7, 7, 7, 5, 1};
        int []fourLongRun = {4, 4, 4, 4, 2, 1, 1};
        int []threeLongRun = {5, 2, 5, 5, 8, 8, 8,};
        int []oneLongRun = {1, 3, 6, 2, 4, 9, 1, 2};
        int []twoLongRun = {4, 1, 3, 4, 7, 7};


        System.out.println("Should be 7: " +
            ArrayExercises_Nazari.longestRun(sevenLongRun));

        System.out.println("Should be 4: " + 
            ArrayExercises_Nazari.longestRun(fourLongRun));

        System.out.println("Should be 3: " + 
            ArrayExercises_Nazari.longestRun(threeLongRun));
        
        System.out.println("Should be 1: " + 
            ArrayExercises_Nazari.longestRun(oneLongRun));
        
            System.out.println("Should be 2: " + 
            ArrayExercises_Nazari.longestRun(twoLongRun));

        // E7.19
        List<Integer> ladderChart = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        ArrayExercises_Nazari.verticalChart(ladderChart);

        List<Integer> smallChart = Arrays.asList(1, 0, 2, 4, 5, 0, 0, 2, 0, 1, 2, 4, 3, 5, 3, 4, 5, 5, 3, 1, 0, 1, 1, 2);
        ArrayExercises_Nazari.verticalChart(smallChart);

        List<Integer> bigChart = Arrays.asList(1000, 2530, 9230, 5820, 7930, 9210, 3920, 910, 950, 1220, 5430, 4920, 8420);
        ArrayExercises_Nazari.verticalChart(bigChart);
    }
}
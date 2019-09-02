import java.util.Scanner;

/**
* The Tester_Nazari program implements an application that
* simply calls the {@link PrimePrinter#main} method to print
* primes to a certain inputted number.
*
* @author  Matthew Nazari
* @since   2019-09-01
*/
public class Tester_Nazari {
    /**
     * This is the main method which calls the main()
     * method of PrimePrinter
     * @param args  unused
     */
    public static void main(String[] args) {
        PrimePrinter.main();
    }
}

/**
 * PrimeGenerator class has two private properties
 * to help loop through primes in the most efficient
 * manner possible (checking 1 below and above multiples
 * of 6 rather than every single number). The nextPrime()
 * method will utilize the static isPrime() method to
 * determine the next prime from the last generated
 */
class PrimeGenerator {
    private int index = 2, lastPrime = -1;

    /**
     * This method determines whether a number is 
     * prime through an effective loop dependant on
     * factors around 6.
     * @param number    number to undergo primality test
     * @return boolean  whether @param number is prime
     */
    public static boolean isPrime(int number) {
        /**
         * Handles special cases ie [-∞, 3] as well
         * as all multiples of 2 and 3.
         */
        if (number <= 3) return number > 1;
        else if (number%2 == 0 || number%3 == 0) return false;
        
        /** 
         * Efficient manner of checking if possible prime
         * is prime: only check factors up to the square
         * root of the possible prime and only check
         * factors of 6 ±1.
         */
        for (int i = 6; i*i <= number; i += 6)
            if (number%(i-1) == 0 || number%(i+1) == 0 ) return false;
        
        return true;
    }

    /**
     * This method returns the prime immediately following
     * the previous prime returned by this method
     * starting from 2. The previous prime and index
     * for looping to find the next prime are stored
     * as private properties.
     * @return int  prime after {@link PrimeGenerator#lastPrime}
     */
    public int nextPrime() {
        /**
         * The loop dependant on factors of 6 requires
         * the index/iterator to be a factor of 6;
         * to catch cases of primes 2 and 3, index will
         * be checked to start at 2 and once it surpasses
         * 3, set it to 6 and proceed with the loop.
         */
        if (index <= 1) index = 2;
        if (index <= 3) return index++;
        if (index <= 5) index = 6;

        /**
         * Checks if factors of 6 ±1 are prime, and if not,
         * increment {@link PrimeGenerator#index} by 6.
         */
        while (true) {
            if (index - 1 > lastPrime && isPrime(index - 1)) {
                lastPrime = index - 1;
                return index - 1;
            }
            if (index + 1 > lastPrime && isPrime(index + 1)) {
                lastPrime = index + 1;
                return index + 1;
            }
            index += 6;    
        }
    }
}

/**
 * PrimePrinter class is used to house a method
 * which tests the PrimeGenerator class by scanning
 * for an integer and printing all primes up to
 * that integer using a PrimeGenerator instance.
 */
class PrimePrinter {
    public static void main() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Ready to print all primes up to... : ");
        int cap = scan.nextInt();
        scan.close();

        PrimeGenerator gen = new PrimeGenerator();

        for (int prime = gen.nextPrime(); prime < cap; prime = gen.nextPrime())
             System.out.printf("%4d\n", prime);
    }
}
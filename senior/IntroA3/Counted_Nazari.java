import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
* The Counted_Nazari program implements an application that
* creates a reference to a Singleton instance, creates 100
* Counted instances, prints the number of Counted instances
* then pops open a window with the BCP Logo and some dialog
* dependant on terminal flags passed via `args`
*
* @author  Matthew Nazari
* @since   2019-08-25
*/
public class Counted_Nazari {
    /**
     * This is the main method which explores the Singleton
     * and Counted classes as well as creates the BCP Logo
     * popup with dialog.
     * @param args  The flags are treated  as a sentence to
     *              be displayed as dialog in the popup
     */
    public static void main(String[] args) throws Exception{
        Singleton s = Singleton.getInstance();

        for (int i = 0; i < 100; i++) {
            Counted counted = new Counted();
        }
        System.out.printf("There are %d Counted instances.\n", Counted.getNumberOfInstances());

        //String name = JOptionPane.showInputDialog("WHat is your name?");
        String dialog = "Java is the language equivalent of Joe Biden.\n";
        for (String word : args) dialog += word + " ";
        URL imageLocation = new URL("http://greco.bcp.org/webs/bcp/bellSeal.jpg");
        JOptionPane.showMessageDialog(null, dialog, "IntroA3",
            JOptionPane.PLAIN_MESSAGE, new ImageIcon(imageLocation));
    }
}

/**
 * Singleton class has a private constructor to keep
 * its static instance of itself the only instance of 
 * Singleton and only retrieveable through a getter
 */
class Singleton {
    /**
     * The only instance of Singleton
     */
    private static Singleton instance = new Singleton();

    /**
     * This method is used to get reference to a single Singleton
     * instance without need of a constructor to inhibit multiple
     * instances.
     * @return Singleton    This returns {@link Singleton#instance}
     */
    public static Singleton getInstance() {
        return instance;
    }

    /**
     * Constructor for Singleton is private to inhibit any
     * additional instances other than the single static one
     */
    private Singleton() {
    }
}

/**
 * Counted class keeps a tally of how many
 * instances of itself are created by incrementing
 * a staic variable in the constructor
 */
class Counted {
    /**
     * The number of Counted instances
     */
    private static int numberOfInstances = 0;

    /**
     * This method is a getter for {@link Counted#numberOfInstances}
     * @return int  This returns the number of Counted instances
     */
    public static int getNumberOfInstances() {
        return numberOfInstances;
    }

    /**
     * Constructor for Counted increments the static
     * {@link Counted#numberOfInstances} variable to record the
     * number of Counted instances
     */
    Counted() {
        numberOfInstances++;
    }
}
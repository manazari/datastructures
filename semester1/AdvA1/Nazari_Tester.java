import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

/**

R11.1
When you try to open a file for reading, an
error will be thrown. However, when writing to
a file that does not exist, it will create a
new file at that directory.

R11.2
An exception will be thrown if ever a program
attempts to write to a read only file.
After a quick test proram I know this to
be proven correct.

R11.8
Every single method has the potential to
contain an instance of an array, and
whever there is an initialized array one
can attempt to access an index < 0 or
index >= array.size. This, of course throws
an IndexOutOfBoundsException. Since every
method throws an IndexOutOfBoundException,
you do not need to declare it when you
throw your own.

R11.11
When you catch an exception in the catch
clause, you can use that exception in
many ways including printing, tracing,
or assessing the error and performing
a method based off the assessment.

R11.13
One might choose to use try-with-resource
to ensure that every resource at the end
of the statement is closed. An example
is an implementation that has two
resources that must be closed with the
addition of the BufferedReader to the
FileReader.

static String firstLineFromFile(String path) throws IOException {
    try (BufferedReader br = 
        new BufferedReader(new FileReeader(Path))) {
        return br.readLine();
    }
}
// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

 */

public class Nazari_Tester {
    public static void main(String[] bruhMoment) throws Exception {
        BufferedReader bf = new BufferedReader(new FileReader("params.txt"));
        double batteryVoltage   = Double.parseDouble(bf.readLine().trim());
        double resistance       = Double.parseDouble(bf.readLine().trim());
        double charge           = Double.parseDouble(bf.readLine().trim());
        double timeInitial      = Double.parseDouble(bf.readLine().trim());
        double timeFinal        = Double.parseDouble(bf.readLine().trim());
        bf.close();

        PrintWriter writer = new PrintWriter("rc.txt", "UTF-8");
        double time = timeInitial;
        double step = (timeFinal - timeInitial)/100;
        while (time < timeFinal) {
            writer.println(time + "\t" + voltage(
                    batteryVoltage,
                    resistance,
                    charge,
                    time
                ));
            time += step;
        }
        writer.close();
    }

    /**
     * @param batteryVoltage volts
     * @param resistance     ohms
     * @param charge         micro farads
     * @param time           micro seconds
     * @return voltage
     */
    public static double voltage(double batteryVoltage, double resistance, double charge, double time) {
        double voltage = batteryVoltage * (1 - Math.pow(Math.E, -time/(resistance*charge)));
        return voltage;
    }
}
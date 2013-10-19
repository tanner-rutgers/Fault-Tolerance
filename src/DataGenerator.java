import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * DataGenerator generates a list of a specified size of
 * random integers and writes these integers to a specified
 * file, each value separated by a space.
 * The program can be run from the command line as follows:
 *
 *      java DataGenerator <filename> <datasize>
 *
 * Where <filename> is the name of the file you wish to write to (will
 * be created if doesn't already exist) and <datasize> is the number of
 * random integers you wish to write to that file.
 *
 * @author Tanner Rutgers (trutgers)
 */
public class DataGenerator {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid Syntax. Please use:\n\n"
                    + "\tjava DataGenerator <filename> <datasize>\n\n"
                    + "Where <filename> is the name of the file in the current directory \n"
                    + "you wish to write to and <datasize> is the number of random integers \n"
                    + "you wish to write.");
        } else {
            try {
                writeToFile(args[0], getRandomInts(Integer.valueOf(args[1])));
            } catch (IOException ex) {
                System.out.println("Could not write to file " + args[0]
                    + "\nTry a different filename");
            }
        }
    }


    /**
     * Return an ArrayList containing <code>size</code> random integers
     * from 0 (inclusive) to 256 (exclusive)
     * @param size Number of random integers to include
     * @return the ArrayList of random integers, empty ArrayList if
     *         <code>size</code> is 0.
     */
    private static ArrayList<Integer> getRandomInts(int size) {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        Random random = new Random();

        for (int i = 0; i<size; i++) {
            randoms.add(random.nextInt(255));
        }

        return randoms;
    }

    /**
     * Writes the given <code>values</code> to the file specified by the
     * <code>String</code> <code>filename</code>, where each value will be
     * separated by a space.
     * @param filename Name of file to write to - will be created if needed
     * @param values Values to write to file
     * @throws IOException if cannot write to given file
     */
    private static void writeToFile(String filename, ArrayList<Integer> values) throws IOException {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"));
            for (Integer value : values) {
                writer.write(value.toString() + " ");
            }
        } finally {
            try { writer.close(); } catch (Exception ex) {}
        }
    }
}

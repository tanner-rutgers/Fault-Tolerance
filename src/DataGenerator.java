import java.io.*;
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
    	if (args.length == 1 && args[0].equals("--help")) {
    		System.out.println("DataGenerator generates random integers and writes them to a file.");
    		System.out.println("Syntax for running DataGenerator is as follows:\n");
    		printSyntax();
    	} else if (args.length != 2) {
        	System.out.println("Invalid Syntax. Please use:\n");
        	printSyntax();
        } else {
            try {
                FileHelper.writeToFile(args[0], getRandomInts(Integer.parseInt(args[1])));
            } catch (IOException ex) {
                System.out.println("Could not write to file " + args[0]
                    + "\nTry a different filename");
            }
        }
    }


    /**
     * Return an ArrayList containing <code>size</code> random integers
     * from 0 (inclusive) to 1000 (exclusive)
     * @param size Number of random integers to include
     * @return the ArrayList of random integers, empty ArrayList if
     *         <code>size</code> is 0.
     */
    private static Integer[] getRandomInts(int size) {
        Integer[] randoms = new Integer[size];
        Random random = new Random();

        for (int i = 0; i<size; i++) {
            randoms[i] = random.nextInt(1000);
        }

        return randoms;
    }

    /**
     * Prints the proper syntax for running DataGenerator
     */
    private static void printSyntax() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tjava DataGenerator <filename> <datasize>\n\n")
                .append("Where <filename> is the name of the file in the current directory \n")
                .append("you wish to write to and <datasize> is the number of random integers \n")
                .append("you wish to write.");
        System.out.println(sb.toString());
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "Class Object";
    }
}

import java.io.File;
import java.io.IOException;
import java.util.Timer;

/**
 * Executive class for running various sorting Threads
 * implementing the Recovery Block reliable systems scheme.
 * Class will read a file containing a list of values to be
 * sorted (filename provided by command line) and attempt to sort
 * these values using a primary sorting method, with backup method(s)
 * in the case the primary method fails.  Sorted values will be written
 * to a new file specified on the command line.
 *
 * @author Tanner Rutgers (trutgers)
 */
public class DataSorter {

    static Integer[] originalValues;
    static final Sorter primarySort = new HeapSort();
    static final Sorter[] backupSorts = new Sorter[] { new CInsertionSort() };

    public static void main(String[] args) {
    	if (args.length == 1 && args[0].equals("--help")) {
    		System.out.println("DataSorter is a fault-tolerant sorting program.");
    		System.out.println("Syntax for running DataSorter is as follows:\n");
    		printSyntax();
    	}
        if (args.length != 5) {
            System.out.println("Invalid Syntax. Please use:\n");
            printSyntax();
        } else {
            // Collect command line arguments
            String inFile = args[0];
            String outFile = args[1];
            Double primFail = Double.parseDouble(args[2]);
            Double backFail = Double.parseDouble(args[3]);
            Integer timeout = Integer.parseInt(args[4]);

            try {
                // Collect values to be sorted from specified file
                String[] values = FileHelper.readFromFile(inFile);
                originalValues = new Integer[values.length];
                for (int i = 0; i<values.length; i++) {
                    originalValues[i] = Integer.parseInt(values[i]);
                }

                // Run primary sorting algorithm
                runSort(primarySort, timeout, primFail);
                try {
                    // Check if primary sorter finished and run adjudicator results, exit if successful
                    if (primarySort.didFinish() && SortedCheck.checkSorted(originalValues, primarySort.getSortedValues(), true)) {
                        FileHelper.writeToFile(outFile, primarySort.getSortedValues());
                        System.exit(0);
                    } else {
                        // Primary failed, print message indicating so
                        System.out.println("Primary sorter '" + primarySort.getClass().getName() + "' failed.");
                        // Run all backups one by one
                        for (Sorter backupSort : backupSorts) {
                            runSort(backupSort, timeout, backFail);
                            // Check if backup sorter finished and run adjudicator results, exit if successful
                            if (backupSort.didFinish() && SortedCheck.checkSorted(originalValues, backupSort.getSortedValues(), true)) {
                                FileHelper.writeToFile(outFile, backupSort.getSortedValues());
                                System.exit(0);
                            }
                        }
                        // All backups failed. Print failure message and delete output file.
                        System.out.println("All backup sorters failed.");
                        (new File(outFile)).delete();
                    }
                } catch (IOException ex) {
                    System.out.println("Could not write to file " + outFile);
                }
            } catch (IOException ex) {
                System.out.println("Could not read from file " + inFile);
            }
        }
    }

    /**
     * Runs the given <code>Sorter</code> on the global array
     * <code>originalValues</code> with a timeout of <code>timeout</code>
     * and a probability of memory access error of <code>failureProb</code>.
     * @param sorter <code>Sorter</code> to sort <code>originalValues</code> with
     * @param timeout Integer representing max time to allow for sorting
     * @param failureProb Probability of each memory access failing
     */
    private static void runSort(Sorter sorter, Integer timeout, Double failureProb) {
        Timer t = new Timer();
        Watchdog w = new Watchdog(sorter);

        sorter.setValues(originalValues);
        sorter.setFailureProbability(failureProb);
        t.schedule(w, timeout);
        sorter.start();
        try {
            sorter.join();
            t.cancel();
        } catch (InterruptedException e) {}
    }
    
    /**
     * Prints the proper syntax for running DataSorter
     */
    private static void printSyntax() {
    	StringBuilder sb = new StringBuilder();
        sb.append("\tjava DataSorter <inFile> <outFile> <primFail> <backFail> <timeout> \n\n")
                .append("Where:\n\n")
                .append("\tinFile = input file with values to sort\n")
                .append("\toutFile = output file to write sorted values to\n")
                .append("\tprimFail = failure probability of primary sorting routine\n")
                .append("\tbackFail = failure probability of backup sorting routine\n")
                .append("\ttimeout = number of seconds to wait for each sorting routine\n");
        System.out.println(sb.toString());	
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("Original Values size: ");
        result.append((originalValues != null ? originalValues.length : "0") + NEW_LINE);
        result.append("Primary sorting routine: " + primarySort.getClass().getName() + NEW_LINE);
        for (int i = 0; i < backupSorts.length; i++) {
            result.append("Backup sorting routine " + i + ": ");
            result.append(backupSorts[i].getClass().getName() + NEW_LINE);
        }
        result.append("}");

        return result.toString();
    }

}

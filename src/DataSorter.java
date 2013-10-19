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
 */
public class DataSorter {

    static Integer[] originalValues;
    static final Sorter primarySort = new HeapSort();
    static final Sorter[] backupSorts = new Sorter[] { new InsertionSort() };

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Invalid Syntax. Please use:\n\n"
                    + "\tjava DataSorter <inFile> <outFile> <primFail> <backFail> <timeout> \n\n"
                    + "Where:\n\n"
                    + "\tinFile = input file with values to sort\n"
                    + "\toutFile = output file to write sorted values to\n"
                    + "primFail = failure probability of primary sorting routine\n"
                    + "backFail = failure probability of backup sorting routine\n"
                    + "timeout = number of seconds to wait for each sorting routine\n");
        } else {
            String inFile = args[0];
            String outFile = args[1];
            Double primFail = Double.parseDouble(args[2]);
            Double backFail = Double.parseDouble(args[3]);
            Integer timeout = Integer.parseInt(args[4]);

            try {
                String[] values = FileHelper.readFile(inFile);
                for (int i = 0; i<values.length; i++) {
                    originalValues[i] = Integer.parseInt(values[i]);
                }
                runSort(primarySort, timeout, primFail);
                try {
                    if (SortedCheck.checkSorted(originalValues, primarySort.getSortedValues(), true)) {
                        FileHelper.writeToFile(outFile, primarySort.getSortedValues());
                        System.exit(0);
                    } else {
                        System.out.println("Primary sorter '" + primarySort + "' failed.");
                        for (Sorter backupSort : backupSorts) {
                            runSort(backupSort, timeout, backFail);
                            if (SortedCheck.checkSorted(originalValues, backupSort.getSortedValues(), true)) {
                                FileHelper.writeToFile(outFile, backupSort.getSortedValues());
                                System.exit(0);
                            }
                        }
                        System.out.println("All backup sorters failed.");
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

}

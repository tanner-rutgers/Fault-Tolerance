import java.io.*;
import java.util.Collection;

/**
 * A helper class for reading from and writing to files
 */
public class FileHelper {

    /**
     * Reads a single line from a file with the given
     * filename and returns an array of all space separated
     * values found
     * @param filename Name of file to read
     * @return String[] of all values found
     * @throws IOException if cannot read from file
     */
    public static String[] readFile(String filename) throws IOException{
        BufferedReader reader = null;
        String[] values = null;

        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(filename), "utf-8"));
            values = reader.readLine().split(" ");
        } finally {
            try { reader.close(); } catch (Exception ex) {}
        }

        return values;
    }

    /**
     * Writes all values found in <code>values</code> to the file
     * specified by <code>filename</code>. Values will be written
     * to one single line and space separated
     * @param filename File you wish to write to
     * @param values Collection of values to write to file
     * @throws IOException if cannot write to file
     */
    public static <E> void writeToFile(String filename, Collection<E> values) throws IOException {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"));
            for (E value : values) {
                writer.write(value.toString() + " ");
            }
        } finally {
            try { writer.close(); } catch (Exception ex) {}
        }
    }
}

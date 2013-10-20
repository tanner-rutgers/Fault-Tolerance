/**
 * Class that uses various methods to evaluate whether or not
 * one Array is a sorted version of another.
 *
 * @author Tanner Rutgers (trutgers)
*/
public class SortedCheck {

    /**
     * Determines whether or not <code>sorted</code> is a sorted
     * version of <code>original</code> based on size, sum, and
     * monotonicity of <code>sorted</code>
     * @param original Array of Comparable values before sorted
     * @param sorted Array of Comparable values potentially sorted
     * @param increasing true if values sorted in increasing order,
     *                   false otherwise
     * @return true if <code>sorted</code> can be considered the
     *          sorted version of <code>original</code> based on
     *          mentioned conditions.
     */
    public static <T extends Comparable<? super T>> boolean checkSorted(final T[] original, final T[] sorted, Boolean increasing) {
        if (sorted == null || increasing == null) return false;

        if (original != null) {
            return checkMonotonic(sorted, increasing) && checkSum(original, sorted) && sorted.length == original.length;
        }

        return checkMonotonic(sorted, increasing);
    }

    /**
     * Evaluates the passed in array for monotonicity assuming
     * the Objects in the array are comparable
     * @param sorted Array of potentially sorted Objects
     * @param increasing set to true if checking for increasing order,
     *                   false for decreasing order
     * @return true if monotonic, false otherwise
     */
    private static <T extends Comparable<? super T>> boolean checkMonotonic(final T[] sorted, boolean increasing) {
        if (sorted.length <= 0) return false;

        if (increasing) {
            for (int i = 1; i < sorted.length; i++) {
                if (sorted[i].compareTo(sorted[i-1]) < 0) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < sorted.length; i++) {
                if (sorted[i].compareTo(sorted[i-1]) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Evaluates equality of passed in arrays based on the sums
     * of their values' String representations
     * @param original First array of objects to use in comparison
     * @param sorted Second array of objects to use in comparison
     * @return true if equal based on above conditions, false otherwise
     */
    private static boolean checkSum(Object[] original, Object[] sorted) {
        int sum1 = 0;
        for (Object o : original) {
            sum1 += Integer.parseInt(o.toString());
        }

        int sum2 = 0;
        for (Object o : sorted) {
            sum2 += Integer.parseInt(o.toString());
        }

        return sum1 == sum2;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " Class Object";
    }
}

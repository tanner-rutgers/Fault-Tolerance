/**
 * An implementation of Thread that is used for
 * sorting algorithms
 *
 * @author Tanner Rutgers (trutgers)
 */
public abstract class Sorter extends Thread{

    protected Integer[] sortedValues;               // Potentially sorted values
    protected Boolean ascendingOrder = true;        // Ascending order flag
    protected Double failureProbability;            // Probability of memory access failure

    protected Boolean sortComplete = false;         // Sort completed flag
    protected int memHits = 0;                      // Number of memory accesses

    public Integer[] getSortedValues() { return sortedValues; }
    public Boolean didFinish() { return sortComplete; }

    public void setValues(Integer[] values) { this.sortedValues = values.clone(); }
    public void setAscendingOrder(Boolean asc) { this.ascendingOrder = asc; }
    public void setFailureProbability(Double prob) { this.failureProbability = prob; }

    /**
     * Called by Thread.start()
     * Will call concrete implementation's sort method
     */
    @Override
    public void run() {
        try {
            this.sort();
        } catch (ThreadDeath td) {
            System.out.println("Timeout occured");
            throw new ThreadDeath();
        }
    }

    /**
     * Determines whether or not a virtual hardware failure occurred.
     * This is a theoretical calculation based on
     * <code>failureProbability</code> and the number of memory
     * accesses during the sorting routines execution.
     * @return true if failure occured, false otherwise
     */
    protected Boolean virtualHardwareFailure() {
        if (failureProbability == null) return false;

        double hazard = memHits*failureProbability;
        double random = Math.random();
        if (random >= 0.5 && random <= 0.5+hazard) return true;
        return false;
    }

    /**
     * Abstract method which should be overridden to
     * sort the given values.  If values are not given,
     * method should sort the global values in
     * <code>originalValues</code>.  Sorted values should be
     * stored in the global variable <code>sortedValues</code>
     */
    protected abstract void sort(Integer[]... values);

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");

        result.append(this.getClass().getName() + " Object {" + NEW_LINE);
        result.append("Values size: ");
        result.append((sortedValues != null ? sortedValues.length : "0") + NEW_LINE);
        result.append("Failure probability: " + failureProbability + NEW_LINE);
        result.append("Sort completed: " + sortComplete + NEW_LINE);
        result.append("}");

        return result.toString();
    }
}

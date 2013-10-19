/**
 * An implementation of Thread that is used for
 * sorting algorithms
 */
public abstract class Sorter extends Thread{

    protected Integer[] originalValues;
    protected Integer[] sortedValues;
    protected double failureProbability;

    public Integer[] getSortedValues() {
        return sortedValues;
    }

    public void setFailureProbability(Double failureProb) {
        this.failureProbability = failureProb;
    }

    /**
     * Called by Thread.start()
     * Will call concrete implementation's sort method
     */
    public void run() {
        this.sort();
    }


    public void setValues(Integer[] values) {
        this.originalValues = values.clone();
    }

    /**
     * Abstract method which should be overridden to
     * sort the given values.  If values are not given,
     * method should sort the global values in
     * <code>originalValues</code>.  Sorted values should be
     * stored in the global variable <code>sortedValues</code>
     * @param values Optional list of objects to sort
     */
    public abstract void sort(Object... values);
}

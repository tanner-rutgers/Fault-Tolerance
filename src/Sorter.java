/**
 * An implementation of Thread that is used for
 * sorting algorithms
 */
public abstract class Sorter<T extends Comparable<? super T>> extends Thread{

    protected T[] originalValues;
    protected T[] sortedValues;
    protected Boolean ascendingOrder = true;
    protected Double failureProbability;

    public T[] getSortedValues() { return sortedValues; }

    public void setValues(T[] values) {
        this.originalValues = values;
        this.sortedValues = values;
    }

    public void setAscendingOrder(Boolean asc) { this.ascendingOrder = asc; }
    public void setFailureProbability(Double prob) { this.failureProbability = prob; }

    /**
     * Called by Thread.start()
     * Will call concrete implementation's sort method
     */
    public void run() {
        this.sort();
    }

    /**
     * Abstract method which should be overridden to
     * sort the given values.  If values are not given,
     * method should sort the global values in
     * <code>originalValues</code>.  Sorted values should be
     * stored in the global variable <code>sortedValues</code>
     * @param values Optional list of objects to sort
     */
    public abstract void sort(T[]... values);
}

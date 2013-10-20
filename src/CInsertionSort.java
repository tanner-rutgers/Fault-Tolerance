public class CInsertionSort<T extends Comparable<? super T>> extends Sorter<T>{

    @Override
    public void sort(T[]... values) {
        if (values.length == 1) sortedValues = values[0];
        if (this.sortedValues == null) return;

        memHits = insertSort(sortedValues);
        sortComplete = !virtualHardwareFailure();
    }

    private native int insertSort(T[] values);

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

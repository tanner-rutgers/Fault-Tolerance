public class CInsertionSort extends Sorter{

    @Override
    protected void sort(Integer[]... values) {
        if (values.length == 1) sortedValues = values[0];                               memHits+=3;
        if (this.sortedValues == null) return;                                          memHits++;

        // Create copy of sortedValues as a primitive int array
        int[] sortedInts = new int[sortedValues.length];                                memHits+=2;
        for (int i = 0; i < sortedInts.length; i++) {                                   memHits+=2;
            sortedInts[i] = sortedValues[i];                                            memHits+=2;
        }

        // Load dynamic library and call C sorting method
        System.loadLibrary("insertSort");
        memHits += insertSort(sortedInts);

        // Convert primitive int array back to Integers
        for (int i = 0; i < sortedInts.length; i++) {                                   memHits+=2;
            sortedValues[i] = sortedInts[i];                                            memHits+=2;
        }

        // Sort complete. Set flag to true unless hardware failure
        memHits++;
        sortComplete = !virtualHardwareFailure();
    }

    private native int insertSort(int[] values);

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

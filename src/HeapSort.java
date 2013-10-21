/**
 * Subclass of Sorter that sorts values based on the
 * well-known Heap Sort method
 *
 * @author Tanner Rutgers (trutgers)
 */
public class HeapSort extends Sorter{

    private int heapSize;

    /**
     * Sort objects using a Heap Sort alogrithm.
     * Details about arguments can be found in <code>Sorter</code>
     */
    @Override
    protected void sort(Integer[]... values) {
        if (values.length == 1) sortedValues = values[0];                                   memHits+=3;
        if (sortedValues == null) return;                                                   memHits++;

        // Build max heap and run a heap sort on it
        buildHeap();
        for (int i = sortedValues.length-1; i>=1; i--) {                                    memHits+=2;
            swapElements(0, i);
            heapSize--;                                                                     memHits++;
            maxHeapify(0);
        }

        // Sort complete. Set flag to true unless hardware failure
        memHits++;
        sortComplete = !virtualHardwareFailure();
    }

    /**
     * Turns the global array of values <code>sortedValues</code>
     * into a max heap
     */
    private void buildHeap() {
        heapSize = sortedValues.length;                                                     memHits+=2;
        for (int i = (sortedValues.length-1)/2; i >= 0; i--) {                              memHits+=2;
            maxHeapify(i);
        }
    }

    /**
     * Turns the partial max heap with root at index <code>i</code>
     * into a max heap
     * @param i Index of root node in partial max heap
     */
    private void maxHeapify(int i) {
        int lc = leftChild(i);                                                              memHits++;
        int rc = rightChild(i);                                                             memHits++;

        int largest = i;                                                                    memHits+=2;
        if (lc < heapSize && sortedValues[lc] > sortedValues[i]) {                          memHits+=4;
            largest = lc;                                                                   memHits+=2;
        }
        if (rc < heapSize && sortedValues[rc] > sortedValues[largest]) {                    memHits+=4;
            largest = rc;                                                                   memHits+=2;
        }
        if (largest != i) {                                                                 memHits+=2;
            swapElements(i, largest);
            maxHeapify(largest);
        }
    }

    /**
     * Returns index of the left child of the node located at <code>index</code>
     * @param index index of parent node
     * @return index of left child node
     */
    private int leftChild(int index) {
        memHits++;
        return index*2;
    }

    /**
     * Returns index of the right child of the node located at <code>index</code>
     * @param index index of parent node
     * @return index of right child node
     */
    private int rightChild(int index) {
        memHits++;
        return index*2+1;
    }

    /**
     * Swaps two elements in the <code>sortedValues</code> array
     * each with <code>index1</code> and <code>index2</code>
     * respectively
     * @param index1 index of first element
     * @param index2 index of second element
     */
    private void swapElements(int index1, int index2) {
        if (index1 < 0 || index1 >= sortedValues.length) {
            memHits+=3;
            return;
        }
        if (index2 < 0 || index2 >= sortedValues.length) {
            memHits += 3;
            return;
        }

        Integer temp = sortedValues[index1];                                                memHits++;
        sortedValues[index1] = sortedValues[index2];                                        memHits++;
        sortedValues[index2] = temp;                                                        memHits++;
    }

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

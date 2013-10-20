/**
 * Subclass of Sorter that sorts values based on the
 * well-known Heap Sort method
 */
public class HeapSort<T extends Comparable<? super T>> extends Sorter<T>{

    private static int heapSize;

    /**
     * Sort objects using a Heap Sort alogrithm.
     * Details about arguments can be found in <code>Sorter</code>
     */
    public void sort(T[]... values) {
        if (values.length == 1) setValues(values[0]);
        if (originalValues == null) return;

        buildHeap();
        for (int i = sortedValues.length-1; i>=1; i--) {
            swapElements(0, i);
            heapSize--;
            maxHeapify(0);
        }
    }

    private void buildHeap() {
        heapSize = sortedValues.length;
        for (int i = (sortedValues.length-1)/2; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    private void maxHeapify(int i) {
        int lc = leftChild(i);
        int rc = rightChild(i);

        int largest = i;
        if (lc < heapSize && sortedValues[lc].compareTo(sortedValues[i]) > 0) {
            largest = lc;
        }
        if (rc < heapSize && sortedValues[rc].compareTo(sortedValues[largest]) > 0) {
            largest = rc;
        }
        if (largest != i) {
            swapElements(i, largest);
            maxHeapify(largest);
        }
    }

    private int leftChild(int index) {
        return index*2;
    }

    private int rightChild(int index) {
        return index*2+1;
    }

    private void swapElements(int index1, int index2) {
        if (index1 < 0 || index1 >= sortedValues.length) return;
        if (index2 < 0 || index2 >= sortedValues.length) return;

        T temp = sortedValues[index1];
        sortedValues[index1] = sortedValues[index2];
        sortedValues[index2] = temp;
    }
}

public class HeapSort extends Sorter{

    public void sort(Object... values) {
        if (values.length == 1) setValues((Integer [])values[0]);
        if (this.originalValues == null) return;

        this.sortedValues = new Integer[this.originalValues.length];
    }
}

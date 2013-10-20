public class InsertionSort<T extends Comparable<? super T>> extends Sorter<T>{


    public void sort(T[]... values) {
        if (values.length == 1) setValues(values[0]);
        if (this.originalValues == null) return;


    }
}

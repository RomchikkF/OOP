import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    static void addToHeap(ArrayList<Integer> heap, int x) {
        int i = heap.size();
        heap.add(x);
        while (i > 0 && heap.get(i) < heap.get((i  - 1) / 2)) {
            Collections.swap(heap, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    static int takeFromHeap(ArrayList<Integer> heap) {
        int result = heap.get(0);
        int n = heap.size() - 1;
        heap.set(0, heap.get(n));
        heap.remove(n);
        int i = 0;
        while ((2 * i + 1 < n && heap.get(i) > heap.get(2 * i + 1)) || (2 * i + 2 < n && heap.get(i) > heap.get(2 * i + 2))){
            if (2 * i + 2 >= n || heap.get(2 * i + 2) > heap.get(2 * i + 1)) {
                Collections.swap(heap, i, 2 * i + 1);
                i = 2 * i + 1;
            } else {
                Collections.swap(heap, i, 2 * i + 2);
                i = 2 * i + 2;
            }
        }
        return result;
    }

    static int[] heapsort(int[] list) {
        ArrayList<Integer> heap = new ArrayList<>();
        for (int j : list) {
            addToHeap(heap, j);
        }
        int n = heap.size();
        int[] sortedList = new int[n];
        for (int i = 0; i < n; ++i) {
            sortedList[i] = takeFromHeap(heap);
        }
        return sortedList;
    }
}
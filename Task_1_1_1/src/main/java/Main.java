import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class Main
{
    static void add_to_heap(ArrayList<Integer> heap, int x){
        int i = heap.size();
        heap.add(x);
        while (i > 0 && heap.get(i) < heap.get((i  - 1) / 2)) {
            Collections.swap(heap, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    static int take_from_heap(ArrayList<Integer> heap){
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

    static int[] heapsort(int[] list){
        ArrayList<Integer> heap = new ArrayList<>();
        for (int j : list) {
            add_to_heap(heap, j);
        }
        int n = heap.size();
        int[] sorted_list = new int[n];
        for (int i = 0; i < n; ++i){
            sorted_list[i] = take_from_heap(heap);
        }
        return sorted_list;
    }
    public static void main(String[] Args)
    {
        System.out.println(Arrays.toString(heapsort(new int[] {5, 4, 3, 2, 1})));
        System.out.println(Arrays.toString(heapsort(new int[] {5, 6, 7, 9, 8, 4, 10, 3, 2, 1})));
    }
}

import java.io.*;
import java.util.Arrays;

public class Main {
    private static long comparison = 0;

    public static void main(String[] args) {
        int[] array = new int[10000];
        String path = "QuickSort.txt";

        try {
            int index = 0;
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = "";

            while ((str = reader.readLine()) != null)
                array[index++] = Integer.parseInt(str);

            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        quickSort(array, 0, array.length - 1);

        // Answer: 162085
        // Answer: 164123
        // Answer: 138382
        System.out.println(comparison);
    }

    private static void quickSort(int[] array, int start, int end){
        if (start >= end)
            return;

        //firstEleAsPivot(array);
        //lastEleAsPivot(array, start, end);
        medianOfThreeAsPivot(array, start, end);

        int pos = partition(array, start, end);
        quickSort(array, start, pos - 1);
        quickSort(array, pos + 1, end);
    }

    private static void firstEleAsPivot(int[] array){
        return;
    }

    private static void lastEleAsPivot(int[] array, int start, int end){
        swap(array, start, end);
    }

    private static void medianOfThreeAsPivot(int[] array, int start, int end){
        int middle = (start + end) / 2;
        int[] tmp = {array[start], array[middle], array[end]};

        Arrays.sort(tmp);

        if (tmp[1] == array[start])
            return;
        else if (tmp[1] == array[middle])
            swap(array, start, middle);
        else
            swap(array, start, end);
    }

    private static void swap(int[] array, int i, int j){
        if (i == j)
            return;

        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[j] ^ array[i];
    }

    private static int partition(int[] array, int start, int end){
        comparison += end - start;
        int i = start + 1;

        for (int j = start + 1; j <= end; j++){
            if (array[j] < array[start]) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, start, --i);

        return i;
    }
}
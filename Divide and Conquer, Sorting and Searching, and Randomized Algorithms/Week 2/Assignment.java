import java.io.*;

public class Main {

    public static void main(String[] args) {
        int[] array = new int[100000];
        String path = "IntegerArray.txt";

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

        // Answer: 2407905288
        System.out.println(sortAndCount(array, 0, array.length - 1));
    }

    private static long sortAndCount(int[] array, int start, int end){
        if (start == end)
            return 0;

        int mid = (start + end) / 2;

        long leftCount = sortAndCount(array, start, mid);
        long rightCount = sortAndCount(array, mid + 1, end);
        long splitCount = countSplitInv(array, start, mid, end);

        return leftCount + rightCount + splitCount;
    }

    private static long countSplitInv(int[] array, int start, int mid, int end) {
        int[] aux = new int[array.length];
        long count = 0;
        int j = start;
        int k = mid + 1;

        for (int i = start; i <= end; i++)
            aux[i] = array[i];

        for (int i = start; i <= end; i++) {
            if (j > mid)
                array[i] = aux[k++];
            else if (k > end)
                array[i] = aux[j++];
            else if (aux[j] < aux[k])
                array[i] = aux[j++];
            else {
                count += mid - j + 1;
                array[i] = aux[k++];
            }
        }

        return count;
    }
}
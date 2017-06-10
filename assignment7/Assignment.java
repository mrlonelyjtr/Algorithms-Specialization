import java.io.*;
import java.util.*;

public class Main {
    private static MaxPQ<Integer> low = new MaxPQ<>();
    private static MinPQ<Integer> high = new MinPQ<>();

    public static void main(String[] args) {
        String path = "Median.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = "";
            int sum = 0;

            while ((str = reader.readLine()) != null) {
                Scanner sc = new Scanner(str);
                int median = calMedian(sc.nextInt());
                sum = (sum + median) % 10000;
            }

            // Answer: 1213
            System.out.println(sum);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int calMedian(int k){
        if (!high.isEmpty() && k > high.min())
            high.insert(k);
        else
            low.insert(k);

        if (low.size() > high.size() + 1)
            high.insert(low.delMax());
        else if (high.size() > low.size() + 1)
            low.insert(high.delMin());

        if (high.size() > low.size())
            return high.min();
        else
            return low.max();
    }
}

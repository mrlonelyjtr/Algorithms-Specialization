import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "knapsack_big.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int W = sc.nextInt();
            int N = sc.nextInt();
            int[] values = new int[N+1];
            int[] weights = new int[N+1];
            long[] dp = new long[W+1];

            for (int i = 1; i <= N; i++) {
                str = reader.readLine();
                sc = new Scanner(str);
                values[i] = sc.nextInt();
                weights[i] = sc.nextInt();
            }

            for (int i = 1; i <= N; i++){
                for (int x = W; x >= 1; x--) {
                    if (x >= weights[i])
                        dp[x] = Math.max(dp[x], dp[x-weights[i]] + values[i]);
                }
            }

            // Answer: 4243395
            System.out.println(dp[W]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
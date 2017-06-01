import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "knapsack1.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int W = sc.nextInt();
            int N = sc.nextInt();
            int[] values = new int[N+1];
            int[] weights = new int[N+1];
            long[][] dp = new long[N+1][W+1];

            for (int i = 1; i <= N; i++) {
                str = reader.readLine();
                sc = new Scanner(str);
                values[i] = sc.nextInt();
                weights[i] = sc.nextInt();
            }

            for (int i = 1; i <= N; i++){
                for (int x = 1; x <= W; x++) {
                    if (x >= weights[i])
                        dp[i][x] = Math.max(dp[i-1][x], dp[i-1][x-weights[i]] + values[i]);
                    else
                        dp[i][x] = dp[i-1][x];
                }
            }

            // Answer: 2493893
            System.out.println(dp[N][W]);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
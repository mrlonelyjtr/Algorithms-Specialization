import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "mwis.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int n = sc.nextInt();
            int[] graph = new int[n+1];
            long[] dp = new long[n+1];
            HashMap<Integer, String> result = new HashMap<>();

            for (int i = 1; i <= n; i++) {
                str = reader.readLine();
                sc = new Scanner(str);
                graph[i] = sc.nextInt();
            }

            reader.close();

            long weight = calMaxWeight(dp, graph);
            reconstruction(result, dp);

            StringBuilder sb = new StringBuilder();
            sb.append(result.get(1));
            sb.append(result.get(2));
            sb.append(result.get(3));
            sb.append(result.get(4));
            sb.append(result.get(17));
            sb.append(result.get(117));
            sb.append(result.get(517));
            sb.append(result.get(997));

            // Answer: 10100110
            System.out.println(sb.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static long calMaxWeight(long[] dp, int[] graph){
        dp[1] = graph[1];

        for (int i = 2; i < dp.length; i++)
            dp[i] = Math.max(dp[i-1], dp[i-2] + graph[i]);

        return dp[dp.length-1];
    }

    private static void reconstruction(HashMap<Integer, String> result, long[] dp){
        for (int idx = dp.length - 1; idx >= 1; idx--){
            if (dp[idx] > dp[idx-1]) {
                result.put(idx, "1");
                result.put(idx-1, "0");
                idx--;
            }
            else
                result.put(idx, "0");
        }
    }
}
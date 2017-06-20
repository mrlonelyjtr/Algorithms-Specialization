import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "2sum.txt";
        int minT = -10000;
        int maxT = 10000;
        int n = 1000000;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = "";
            long count = 0;
            HashMap<Long, Boolean> map = new HashMap<>();

            while ((str = reader.readLine()) != null)
                map.put(Long.parseLong(str), true);

            for (int t = minT; t <= maxT; t++){
                for (long x : map.keySet()){
                    if (map.containsKey(t - x) && t - x != x){
                        count++;
                        break;
                    }
                }
            }

            // Answer: 427
            System.out.println(count);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

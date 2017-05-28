import java.io.*;
import java.util.*;

public class Main {
    private static HashMap<String, Integer> cluster = new HashMap<>();

    public static void main(String[] args) {
        String path = "clustering_big.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int nodes = sc.nextInt();
            int bits = sc.nextInt();

            for (int i = 1; i <= nodes; i++) {
                str = reader.readLine();
                sc = new Scanner(str);
                String node = sc.nextLine().replaceAll(" ", "");
                cluster.put(node, i);
            }

            int value = 1;
            for (String key : cluster.keySet()){
                cluster.put(key, value++);
            }

            reader.close();

            // Answer: 6118
            System.out.println(calClusters(cluster.size()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int calClusters(int size){
        UnionFind uf = new UnionFind(size);

        for (String node : cluster.keySet()) {
            ArrayList<String> neighbors = new ArrayList<>();

            findNeighbors(neighbors, node);

            for (String neighbor : neighbors){
                if (cluster.containsKey(neighbor))
                    uf.union(cluster.get(neighbor), cluster.get(node));
            }
        }

        return uf.count();
    }

    private static void findNeighbors(ArrayList<String> neighbors, String str){
        char[] node = str.toCharArray();
        flipOneBit(neighbors, node);
        flipTwoBit(neighbors, node);
    }

    private static void flipOneBit(ArrayList<String> neighbors, char[] node){
        for (int i = 0; i < node.length; i++){
            flip(node, i);
            neighbors.add(String.valueOf(node));
            flip(node, i);
        }
    }

    private static void flipTwoBit(ArrayList<String> neighbors, char[] node) {
        for (int i = 0; i < node.length - 1; i++){
            flip(node, i);

            for (int j = i + 1; j < node.length; j++){
                flip(node, j);
                neighbors.add(String.valueOf(node));
                flip(node, j);
            }

            flip(node, i);
        }
    }

    private static void flip(char[] node, int idx){
        char bit = node[idx];

        if (bit == '0')
            node[idx] = '1';
        else
            node[idx] = '0';
    }

    static class UnionFind {
        private int[] id;
        private int[] size;
        private int count;

        public UnionFind(int n){
            count = n;
            id = new int[n+1];
            size = new int[n+1];

            for (int i = 1; i <= n; i++){
                id[i] = i;
                size[i] = 1;
            }
        }

        public int count(){
            return count;
        }

        public void union(int p, int q){
            int pRoot = find(p);
            int qRoot = find(q);

            if (pRoot == qRoot)
                return;

            if (size[pRoot] < size[qRoot]){
                id[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            }
            else{
                id[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
            }

            count--;
        }

        public int find(int p){
            while (id[p] != p)
                p = id[p];

            return id[p];
        }
    }
}
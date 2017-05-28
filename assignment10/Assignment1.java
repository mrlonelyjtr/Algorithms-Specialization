import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "clustering1.txt";
        int k = 4;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int vertex = sc.nextInt();
            ArrayList<Edge> edges = new ArrayList<>();

            while ((str = reader.readLine()) != null) {
                sc = new Scanner(str);
                edges.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));
            }

            reader.close();

            Collections.sort(edges);

            // Answer: 106
            System.out.println(calMaxSpacing(edges, k, vertex));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static int calMaxSpacing(ArrayList<Edge> edges, int k, int cluster){
        int idx = 0;
        int result = Integer.MAX_VALUE;
        UnionFind uf = new UnionFind(cluster);

        while (uf.count() > k && idx < edges.size()){
            Edge edge = edges.get(idx++);
            uf.union(edge.either(), edge.other(edge.either()));
        }

        while (idx < edges.size()){
            Edge edge = edges.get(idx++);

            if (uf.connected(edge.either(), edge.other(edge.either())))
                continue;

            result = Math.min(result, edge.weight());
        }

        return result;
    }
}

class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final int weight;

    public Edge(int v, int w, int weight){
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int weight(){
        return weight;
    }

    public int either(){
        return v;
    }

    public int other(int vertex){
        if (vertex == v)
            return w;
        else
            return v;
    }

    public int compareTo(Edge that){
        return Integer.compare(this.weight, that.weight);
    }
}

class UnionFind {
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

    public boolean connected(int p, int q){
        return find(p) == find(q);
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
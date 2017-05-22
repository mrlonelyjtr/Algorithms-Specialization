import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int vertexSize = 200;

    public static void main(String[] args) {
        int minCut = Integer.MAX_VALUE;

        for (int i = 1; i <= vertexSize; i++) {
            Random rand = new Random(i);
            Graph graph = new Graph(vertexSize);
            String path = "kargerMinCut.txt";

            try {
                BufferedReader reader = new BufferedReader(new FileReader(path));
                String str = "";

                while ((str = reader.readLine()) != null){
                    Scanner sc = new Scanner(str);
                    int vertex = sc.nextInt();

                    while (sc.hasNextInt())
                        graph.addEdge(vertex, sc.nextInt());
                }

                reader.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

            minCut = Math.min(kargerMinCut(graph, rand), minCut);
        }

        // Answer: 17
        System.out.println(minCut);
    }

    private static int kargerMinCut(Graph graph, Random rand){
        while (graph.V() > 2){
            int v = rand.nextInt(vertexSize + 1);

            if (graph.adjSize(v) == 0)
                continue;

            int w = graph.adj(v, rand.nextInt(graph.adjSize(v)));

            graph.contractVertex(v, w);
        }

        return graph.E();
    }
}

class Graph {
    private int V;
    private int E;
    private ArrayList<Integer>[] adj;

    public Graph(int V){
        this.V = V;
        this.E = 0;
        adj = (ArrayList<Integer>[]) new ArrayList[V+1];

        for (int v = 1; v <= V; v++)
            adj[v] = new ArrayList<>();
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(int v, int w){
        adj[v].add(w);

        if (!adj[w].contains(v))
            E++;
    }

    public void contractVertex(int v, int w){
        for (int node : adj[w]){
            if (node != v) {
                adj[node].set(adj[node].indexOf(w), v);
                addEdge(v, node);
            }
        }

        while (adj[v].contains(w)){
            adj[v].remove(adj[v].indexOf(w));
            E--;
        }

        adj[w] = null;
        V--;
    }

    public int adj(int v, int index){
        return adj[v].get(index);
    }

    public int adjSize(int v){
        if (adj[v] == null)
            return 0;
        else
            return adj[v].size();
    }
}
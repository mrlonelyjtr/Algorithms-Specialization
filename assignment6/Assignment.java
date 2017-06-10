import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "dijkstraData.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = "";
            EdgeWeightedGraph graph = new EdgeWeightedGraph(200);

            while ((str = reader.readLine()) != null) {
                Scanner sc = new Scanner(str);
                int v = sc.nextInt();

                while (sc.hasNext()){
                    String[] entry = sc.next().split(",");
                    graph.addEdge(new Edge(v, Integer.parseInt(entry[0]), Integer.parseInt(entry[1])));
                }
            }

            DijkstraSPT dijkstraSPT = new DijkstraSPT(graph, 1);

            int[] vertex = new int[]{7, 37, 59, 82, 99, 115, 133, 165, 188, 197};
            int[] result = new int[10];

            for (int i = 0; i < 10; i++){
                if (dijkstraSPT.hasPathTo(vertex[i]))
                    result[i] = dijkstraSPT.distTo(vertex[i]);
                else
                    result[i] = 1000000;
            }

            // Answer: 2599, 2610, 2947, 2052, 2367, 2399, 2029, 2442, 2505, 3068
            System.out.println(Arrays.toString(result));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

class DijkstraSPT {
    private int[] distTo;
    private IndexMinPQ<Integer> pq;
    private int s;

    public DijkstraSPT(EdgeWeightedGraph g, int s){
        distTo = new int[g.V()+1];
        pq = new IndexMinPQ<>(g.V());
        this.s = s;

        for (int v = 0; v < g.V(); v++)
            distTo[v] = Integer.MAX_VALUE;
        distTo[s] = 0;

        pq.insert(s, 0);

        while (!pq.isEmpty())
            relax(g, pq.delMin());
    }

    private void relax(EdgeWeightedGraph g, int v){
        for (Edge e : g.adj(v)){
            int w = e.other(v);

            if (distTo[v] + e.weight() < distTo[w]){
                distTo[w] = distTo[v] + e.weight();

                if (pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    public int distTo(int v){
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
}

class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Stack<Edge>[] adj;

    public EdgeWeightedGraph(int V){
        this.V = V;
        this.E = 0;
        adj = (Stack<Edge>[]) new Stack[V+1];

        for (int v = 1; v <= V; v++)
            adj[v] = new Stack<>();
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(Edge e){
        int v = e.either();
        int w = e.other(v);
        adj[v].push(e);
        adj[w].push(e);
        E++;
    }

    public Iterable<Edge> adj(int v){
        return adj[v];
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
        return Double.compare(this.weight, that.weight);
    }
}
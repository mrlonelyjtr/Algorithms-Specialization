import java.io.*;
import java.util.Stack;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "edges.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int vertex = sc.nextInt();
            int edge = sc.nextInt();
            EdgeWeightedGraph graph = new EdgeWeightedGraph(vertex);

            while ((str = reader.readLine()) != null) {
                sc = new Scanner(str);
                graph.addEdge(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));
            }

            reader.close();

            PrimMST mst = new PrimMST(graph);

            // Answer: -3612829
            System.out.println(mst.calCost());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

class PrimMST {
    private boolean[] marked;
    private Edge[] edgeTo;
    private int[] distTo;
    private IndexMinPQ<Integer> pq;

    public PrimMST(EdgeWeightedGraph g){
        marked = new boolean[g.V()+1];
        edgeTo = new Edge[g.V()+1];
        distTo = new int[g.V()+1];
        pq = new IndexMinPQ<>(g.V()+1);

        for (int v = 2; v <= g.V(); v++)
            distTo[v] = Integer.MAX_VALUE;

        prim(g);
    }

    private void prim(EdgeWeightedGraph g){
        pq.insert(1, 0);

        while (!pq.isEmpty())
            visit(g, pq.delMin());
    }

    private void visit(EdgeWeightedGraph g, int v){
        marked[v] = true;

        for (Edge e : g.adj(v)){
            int w = e.other(v);

            if (marked[w] == true)
                continue;

            if (e.weight() < distTo[w]){
                distTo[w] = e.weight();
                edgeTo[w] = e;

                if (pq.contains(w))
                    pq.change(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    public int calCost(){
        int weight = 0;

        for (int v = 1; v < distTo.length; v++)
            weight += distTo[v];

        return weight;
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
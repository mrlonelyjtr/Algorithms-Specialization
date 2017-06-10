import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "SCC.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = "";
            Digraph graph = new Digraph(875714);

            while ((str = reader.readLine()) != null) {
                Scanner sc = new Scanner(str);
                graph.addEdge(sc.nextInt(), sc.nextInt());
            }
            
            KosarajuSCC scc = new KosarajuSCC(graph);
            int[] result = scc.count();

            // Answer: 434821, 968, 459, 313, 211
            System.out.println(Arrays.toString(result));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

class KosarajuSCC {
    private int t;
    private int s;
    boolean[] marked;
    private int[] finishingTime;
    private Map<Integer, Integer> leader = new HashMap<>();

    public KosarajuSCC(Digraph g){
        finishingTime = new int[g.V()+1];
        marked = new boolean[g.V()+1];

        t = 0;
        s = 0;

        dfsLoopRev(g.reverse());
        dfsLoop(g);
    }

    private void dfsLoopRev(Digraph g){
        for (int i = 0; i <= g.V(); i++)
            marked[i] = false;

        for (int i = g.V(); i >= 1; i--){
            if (marked[i] == false)
                dfsRev(g, i);
        }
    }

    private void dfsRev(Digraph g, int i){
        Stack<Integer> stack = new Stack<>();

        marked[i] = true;
        stack.push(i);

        while (!stack.isEmpty()){
            int v = stack.peek();

            for (int w : g.adj(v)){
                if (marked[w] == false){
                    marked[w] = true;
                    stack.push(w);
                    break;
                }
            }

            if (stack.peek() == v){
                t++;
                finishingTime[t] = v;
                stack.pop();
            }
        }
    }

    private void dfsLoop(Digraph g){
        for (int i = 0; i <= g.V(); i++)
            marked[i] = false;

        for (int i = g.V(); i >= 1; i--){
            int v = finishingTime[i];

            if (marked[v] == false) {
                s = v;
                dfs(g, v);
            }
        }
    }

    private void dfs(Digraph g, int i){
        Stack<Integer> stack = new Stack<>();

        marked[i] = true;
        stack.push(i);

        while (!stack.isEmpty()){
            int v = stack.peek();

            for (int w : g.adj(v)){
                if (marked[w] == false){
                    marked[w] = true;
                    stack.push(w);
                    break;
                }
            }

            if (stack.peek() == v) {
                if (leader.containsKey(s))
                    leader.put(s, leader.get(s) + 1);
                else
                    leader.put(s, 1);

                stack.pop();
            }
        }
    }

    public int[] count(){
        int[] result = new int[5];
        MaxPQ pq = new MaxPQ(5);

        for (int val : leader.values())
            pq.insert(val);

        for (int i = 0; i < 5; i++){
            if (!pq.isEmpty())
                result[i] = (int) pq.delMax();
            else
                result[i] = 0;
        }

        return result;
    }
}

class Digraph {
    private final int V;
    private int E;
    private Stack<Integer>[] adj;

    public Digraph(int V){
        this.V = V;
        this.E = 0;
        adj = (Stack<Integer>[]) new Stack[V+1];

        for (int v = 1; v <= V; v++){
            adj[v] = new Stack<>();
        }
    }

    public int V(){
        return V;
    }

    public int E(){
        return E;
    }

    public void addEdge(int v, int w){
        adj[v].push(w);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public Digraph reverse(){
        Digraph tmp = new Digraph(V);

        for (int v = 1; v <= V; v++){
            for (int w : adj[v])
                tmp.addEdge(w, v);
        }

        return tmp;
    }
}
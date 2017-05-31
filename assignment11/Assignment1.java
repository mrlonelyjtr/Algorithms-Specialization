import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String path = "huffman.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String str = reader.readLine();
            Scanner sc = new Scanner(str);
            int n = sc.nextInt();
            int[] freq = new int[n];

            for (int i = 0; i < n; i++) {
                str = reader.readLine();
                sc = new Scanner(str);
                freq[i] = sc.nextInt();
            }


            Huffman huffman = new Huffman();
            huffman.buildTree(freq);

            // Answer: 19
            // Answer: 9
            System.out.println(huffman.getMaxLength());
            System.out.println(huffman.getMinLength());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Huffman {
    private Node root;

    private class Node implements Comparable<Node> {
        private long val;
        private Node left;
        private Node right;
        private int minLength;
        private int maxLength;

        public Node(long val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
            this.maxLength = 0;
            this.minLength = 0;
        }

        public int compareTo(Node that) {
            return Long.compare(this.val, that.val);
        }
    }

    public void buildTree(int[] freq){
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (int i = 0; i < freq.length; i++)
            pq.offer(new Node(freq[i], null, null));

        while (pq.size() > 1){
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node(left.val + right.val, left, right);
            pq.offer(parent);

            parent.minLength = Math.min(left.minLength, right.minLength) + 1;
            parent.maxLength = Math.max(left.maxLength, right.maxLength) + 1;
        }

        root = pq.poll();
    }

    public int getMaxLength(){
        return root.maxLength;
    }

    public int getMinLength(){
        return root.minLength;
    }
}
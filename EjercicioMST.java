"EjercicioMST.java"
import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    
    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }

    @Override
    public String toString() {
        return String.format("%d--%d (%d)", src, dest, weight);
    }
}


class Graph {
    int V;
    List<List<Edge>> adj;

    public Graph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        
        adj.get(src).add(new Edge(src, dest, weight));
        adj.get(dest).add(new Edge(dest, src, weight));
    }
}

"Algoritmo de Prim" 
class PrimAlgorithm {
    public static void findMST(Graph graph, int startVertex) {
        System.out.println("\n--- Algoritmo de Prim (iniciando desde el vértice " + startVertex + ") ---");
        boolean[] inMST = new boolean[graph.V];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        int mstCost = 0;
        List<Edge> mstEdges = new ArrayList<>();

        inMST[startVertex] = true;
        pq.addAll(graph.adj.get(startVertex));

        while (!pq.isEmpty() && mstEdges.size() < graph.V - 1) {
            Edge edge = pq.poll();
            if (inMST[edge.dest]) {
                continue; 
            }

            
            mstEdges.add(edge);
            mstCost += edge.weight;
            inMST[edge.dest] = true;

            
            for (Edge e : graph.adj.get(edge.dest)) {
                if (!inMST[e.dest]) {
                    pq.add(e);
                }
            }
        }

        System.out.println("1. Orden en que se seleccionan las aristas:");
        mstEdges.forEach(System.out::println);
        System.out.println("2. Costo total del MST: " + mstCost);
    }
}

 "Algoritmo de Kruskal"
class KruskalAlgorithm {
    
    static class UnionFind {
        int[] parent, rank;
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        public int find(int i) {
            if (parent[i] != i) parent[i] = find(parent[i]);
            return parent[i];
        }
        public void union(int x, int y) {
            int xRoot = find(x), yRoot = find(y);
            if (xRoot == yRoot) return;
            if (rank[xRoot] < rank[yRoot]) parent[xRoot] = yRoot;
            else if (rank[xRoot] > rank[yRoot]) parent[yRoot] = xRoot;
            else { parent[yRoot] = xRoot; rank[xRoot]++; }
        }
    }

    public static void findMST(Graph graph) {
        System.out.println("\n--- Algoritmo de Kruskal ---");
        List<Edge> allEdges = new ArrayList<>();
        for (int i = 0; i < graph.V; i++) {
            for (Edge edge : graph.adj.get(i)) {
                if (edge.src < edge.dest) { 
                    allEdges.add(edge);
                }
            }
        }
        Collections.sort(allEdges);
        UnionFind uf = new UnionFind(graph.V);
        List<Edge> mstEdges = new ArrayList<>();
        int mstCost = 0;
        int discardedEdges = 0;

        for (Edge edge : allEdges) {
            int rootSrc = uf.find(edge.src);
            int rootDest = uf.find(edge.dest);

            if (rootSrc != rootDest) {
                mstEdges.add(edge);
                mstCost += edge.weight;
                uf.union(rootSrc, rootDest);
            } else {
                discardedEdges++;
            }
        }

        System.out.println("1. Aristas seleccionadas:");
        mstEdges.forEach(System.out::println);
        System.out.println("2. Número de aristas descartadas por formar ciclo: " + discardedEdges);
        System.out.println("Costo total del MST: " + mstCost);
    }
}


public class EjercicioMST {
    public static void main(String[] args) {
        
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 6);
        graph.addEdge(0, 2, 1);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 2, 2);
        graph.addEdge(1, 4, 5);
        graph.addEdge(2, 3, 2);
        graph.addEdge(2, 4, 6);
        graph.addEdge(3, 5, 4);
        graph.addEdge(4, 5, 3);

        
        PrimAlgorithm.findMST(graph, 0);

        
        PrimAlgorithm.findMST(graph, 2);

        
        KruskalAlgorithm.findMST(graph);
    }
}
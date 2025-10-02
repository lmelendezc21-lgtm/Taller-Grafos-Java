"EjercicioDijkstra.java"

import java.util.*;

class DijkstraGraph {
    private int V;
    private List<List<int[]>> adj; 

    public DijkstraGraph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new int[]{v, weight}); 
    }

    public void dijkstra(int src) {
        int[] dist = new int[V];
        int[] parent = new int[V];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // [nodo, distancia]

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;
        pq.add(new int[]{src, 0});

        while (!pq.isEmpty()) {
            int[] u = pq.poll();
            int node = u[0];

            for (int[] neighbor : adj.get(node)) {
                int v = neighbor[0];
                int weight = neighbor[1];

                if (dist[node] + weight < dist[v]) {
                    dist[v] = dist[node] + weight;
                    parent[v] = node;
                    pq.add(new int[]{v, dist[v]});
                }
            }
        }

        printSolution(src, dist, parent);
    }

    private void printSolution(int src, int[] dist, int[] parent) {
        System.out.println("--- Algoritmo de Dijkstra desde el vértice " + src + " ---");
        for (int i = 0; i < V; i++) {
            if (i != src) {
                System.out.print("Camino más corto de " + src + " a " + i + ": ");
                if (dist[i] == Integer.MAX_VALUE) {
                    System.out.println("Inalcanzable");
                } else {
                    System.out.print("Distancia = " + dist[i] + ", Camino = ");
                    printPath(parent, i);
                    System.out.println();
                }
            }
        }
    }

    private void printPath(int[] parent, int j) {
        if (parent[j] == -1) {
            System.out.print(j);
            return;
        }
        printPath(parent, parent[j]);
        System.out.print(" → " + j);
    }
}

public class EjercicioDijkstra {
    public static void main(String[] args) {
        
        DijkstraGraph g = new DijkstraGraph(5);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 4, 3);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 4, 4);
        g.addEdge(2, 3, 9);
        g.addEdge(3, 2, 7);
        g.addEdge(4, 1, 1);
        g.addEdge(4, 2, 8);
        g.addEdge(4, 3, 2);

        g.dijkstra(0);
    }
}
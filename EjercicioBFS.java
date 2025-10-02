" EjercicioBFS.java"

import java.util.*;

public class EjercicioBFS {
    private int V;
    private LinkedList<Integer>[] adj;

    public EjercicioBFS(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);     }

    public List<Integer> findShortestPath(int src, int dest) {
        boolean[] visited = new boolean[V];
        int[] parent = new int[V];
        Queue<Integer> queue = new LinkedList<>();

        visited[src] = true;
        queue.add(src);
        parent[src] = -1; 
        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int neighbor : adj[u]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = u;
                    queue.add(neighbor);

                    if (neighbor == dest) {
                        return reconstructPath(parent, src, dest);
                    }
                }
            }
        }
        return null; 
    }

    private List<Integer> reconstructPath(int[] parent, int src, int dest) {
        List<Integer> path = new LinkedList<>();
        int current = dest;
        while (current != -1) {
            path.add(0, current);
            current = parent[current];
        }
        return path;
    }

    public static void main(String[] args) {
        // Grafo del enunciado: 0–1, 0–2, 1–3, 2–3, 3–4, 4–5
        EjercicioBFS g = new EjercicioBFS(6);
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(4, 5);

        int origen = 0;
        int destino = 5;
        List<Integer> path = g.findShortestPath(origen, destino);

        System.out.println("Camino más corto en número de aristas (BFS):");
        if (path != null) {
            System.out.println("Salida esperada: " + String.join(" → ", path.stream().map(String::valueOf).toArray(String[]::new)));
        } else {
            System.out.println("No se encontró un camino entre " + origen + " y " + destino);
        }
    }
}

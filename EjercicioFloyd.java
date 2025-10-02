 "EjercicioFloyd.java"

public class EjercicioFloyd {
    private static final int INF = Integer.MAX_VALUE / 2; 
    public void floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];
        int[][] next = new int[V][V]; 

        
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
                if (graph[i][j] != INF && i != j) {
                    next[i][j] = j;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }

        printSolution(dist, next);
    }

    private void printSolution(int[][] dist, int[][] next) {
        int V = dist.length;
        System.out.println("--- Matriz de distancias más cortas (Floyd-Warshall) ---");
        for (int i = 0; i < V; ++i) {
            for (int j = 0; j < V; ++j) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(dist[i][j] + "   ");
                }
            }
            System.out.println();
        }

        System.out.println("\n--- Camino mínimo de 0 → 3 ---");
        printPath(next, 0, 3);
    }

    private void printPath(int[][] next, int u, int v) {
        if (next[u][v] == -1) {
            System.out.println("No hay camino de " + u + " a " + v);
            return;
        }
        List<Integer> path = new ArrayList<>();
        path.add(u);
        while (u != v) {
            u = next[u][v];
            path.add(u);
        }
        System.out.println(String.join(" → ", path.stream().map(String::valueOf).toArray(String[]::new)));
    }

    public static void main(String[] args) {
        
        int V = 5;
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) graph[i][j] = 0;
                else graph[i][j] = INF;
            }
        }
        graph[0][1] = 10; graph[0][4] = 3;
        graph[1][2] = 2;  graph[1][4] = 4;
        graph[2][3] = 9;
        graph[3][2] = 7;
        graph[4][1] = 1;  graph[4][2] = 8; graph[4][3] = 2;

        EjercicioFloyd fw = new EjercicioFloyd();
        fw.floydWarshall(graph);
    }
}

"EjercicioWarshall.java"

public class EjercicioWarshall {
    public void findTransitiveClosure(int[][] graph) {
        int V = graph.length;
        boolean[][] reach = new boolean[V][V];

        
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                reach[i][j] = (graph[i][j] == 1);
            }
        }

        
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
                }
            }
        }

        printSolution(reach);
    }

    private void printSolution(boolean[][] reach) {
        int V = reach.length;
        System.out.println("--- Matriz de Alcanzabilidad (Warshall) ---");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print((reach[i][j] ? 1 : 0) + " ");
            }
            System.out.println();
        }

        
        boolean isStronglyConnected = true;
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (!reach[i][j]) {
                    isStronglyConnected = false;
                    break;
                }
            }
            if (!isStronglyConnected) break;
        }

        System.out.println("\n¿El grafo es fuertemente conexo? " + (isStronglyConnected ? "Sí" : "No"));
    }

    public static void main(String[] args) {
        
        int V = 4;
        int[][] graph = new int[V][V];
        graph[0][1] = 1;
        graph[1][2] = 1;
        graph[2][0] = 1;
        graph[2][3] = 1;

        EjercicioWarshall warshall = new EjercicioWarshall();
        warshall.findTransitiveClosure(graph);
    }
}
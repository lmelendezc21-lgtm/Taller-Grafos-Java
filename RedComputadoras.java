"RedComputadoras.java"

import java.util.*;

public class RedComputadoras {
    private int numNodos;
    private LinkedList<Integer>[] adj;

    public RedComputadoras(int numNodos) {
        this.numNodos = numNodos;
        adj = new LinkedList[numNodos];
        for (int i = 0; i < numNodos; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    
    public void agregarConexion(int u, int v) {
        System.out.println("Agregando conexión entre " + u + " y " + v);
        adj[u].add(v);
        adj[v].add(u); 
    }

    
    public boolean puedenComunicarse(int u, int v) {
        if (u == v) return true;
        boolean[] visited = new boolean[numNodos];
        Queue<Integer> queue = new LinkedList<>();
        visited[u] = true;
        queue.add(u);

        while (!queue.isEmpty()) {
            int nodo = queue.poll();
            for (int vecino : adj[nodo]) {
                if (vecino == v) {
                    return true;
                }
                if (!visited[vecino]) {
                    visited[vecino] = true;
                    queue.add(vecino);
                }
            }
        }
        return false;
    }

    
    public List<Integer> encontrarCaminoMasCorto(int u, int v) {
        if (u == v) {
            return Arrays.asList(u);
        }
        boolean[] visited = new boolean[numNodos];
        int[] parent = new int[numNodos];
        Queue<Integer> queue = new LinkedList<>();
        
        visited[u] = true;
        queue.add(u);
        parent[u] = -1;

        while (!queue.isEmpty()) {
            int nodo = queue.poll();
            for (int vecino : adj[nodo]) {
                if (!visited[vecino]) {
                    visited[vecino] = true;
                    parent[vecino] = nodo;
                    queue.add(vecino);
                    if (vecino == v) {
                        return reconstruirCamino(parent, u, v);
                    }
                }
            }
        }
        return null; 
    }

    private List<Integer> reconstruirCamino(int[] parent, int src, int dest) {
        List<Integer> path = new LinkedList<>();
        int current = dest;
        while (current != -1) {
            path.add(0, current);
            current = parent[current];
        }
        return path;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RedComputadoras red = new RedComputadoras(6);

        red.agregarConexion(0, 1);
        red.agregarConexion(0, 2);
        red.agregarConexion(1, 3);
        red.agregarConexion(2, 4);
        red.agregarConexion(3, 5);
        red.agregarConexion(4, 5);

        while (true) {
            System.out.println("\n--- Menú de la Red de Computadoras ---");
            System.out.println("1. Agregar conexión");
            System.out.println("2. Verificar comunicación entre dos nodos");
            System.out.println("3. Encontrar camino más corto entre dos nodos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nodo de origen (0-5): ");
                    int u = scanner.nextInt();
                    System.out.print("Ingrese nodo de destino (0-5): ");
                    int v = scanner.nextInt();
                    if (u >= 0 && u < 6 && v >= 0 && v < 6) {
                        red.agregarConexion(u, v);
                    } else {
                        System.out.println("Nodos inválidos.");
                    }
                    break;
                case 2:
                    System.out.print("Ingrese primer nodo: ");
                    int n1 = scanner.nextInt();
                    System.out.print("Ingrese segundo nodo: ");
                    int n2 = scanner.nextInt();
                    if (n1 >= 0 && n1 < 6 && n2 >= 0 && n2 < 6) {
                        boolean pueden = red.puedenComunicarse(n1, n2);
                        System.out.println("¿Pueden comunicarse " + n1 + " y " + n2 + "? " + (pueden ? "Sí" : "No"));
                    } else {
                        System.out.println("Nodos inválidos.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese nodo de origen: ");
                    int o = scanner.nextInt();
                    System.out.print("Ingrese nodo de destino: ");
                    int d = scanner.nextInt();
                    if (o >= 0 && o < 6 && d >= 0 && d < 6) {
                        List<Integer> camino = red.encontrarCaminoMasCorto(o, d);
                        if (camino != null) {
                            System.out.println("Camino más corto: " + String.join(" → ", camino.stream().map(String::valueOf).toArray(String[]::new)));
                        } else {
                            System.out.println("No hay camino entre " + o + " y " + d);
                        }
                    } else {
                        System.out.println("Nodos inválidos.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
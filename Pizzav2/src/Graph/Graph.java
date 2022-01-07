package Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph<T> {
    int[][] adjacencyMatrix = new int[0][0]; //матрица смежности
    List<Vertex<T>> vertexList = new ArrayList<>(); //список вершин
    List<Edge<T>> edgeList = new ArrayList<>(); //список дуг

    public void addVertex(Vertex<T> vertex) {
        if (vertexList.contains(vertex)) {
            System.out.println("Данная вершина уже существует!");
            return;
        }
        vertexList.add(vertex);
        resizeMatrix();
    }

    public void removeVertex(Vertex<T> vertex) {
        if (!vertexList.contains(vertex)) {
            System.out.println("Данной вершины в списке нет!");
            return;
        }
        removeVertexFromMatrix(vertex);
        vertexList.remove(vertex);
    }

    public void addEdge(Edge<T> edge) {
        if (!vertexList.contains(edge.source)) {
            System.out.println("Стартовой вершины в списке нет!");
            return;
        }

        if (!vertexList.contains(edge.destination)) {
            System.out.println("Конечной вершины в списке нет!");
            return;
        }

        if (edgeList.contains(edge)) {
            System.out.println("Данная дуга уже существует!");
            return;
        }

        edgeList.add(edge);
        changeMatrix(edge, edge.weight);
    }

    public void removeEdge(Edge<T> edge) {
        if (!edgeList.contains(edge)) {
            System.out.println("Данной дуги не существует!");
            return;
        }

        edgeList.remove(edge);
        changeMatrix(edge, 0);
    }

    public int getIndex(Vertex<T> vertex){
        return vertexList.indexOf(vertex);
    }

    private void changeMatrix(Edge<T> edge, int weight) {
        int indexSource = vertexList.indexOf(edge.source);
        int indexDestination = vertexList.indexOf(edge.destination);
        adjacencyMatrix[indexSource][indexDestination] = weight;
        adjacencyMatrix[indexDestination][indexSource] = weight;
    }

    private void resizeMatrix() {
        if (adjacencyMatrix == null) {
            adjacencyMatrix = new int[0][0];
            return;
        }

        int size = adjacencyMatrix.length;
        int[][] newMatrix = new int[size + 1][size + 1];

        for (int i = 0; i < size; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, size);
        }

        adjacencyMatrix = newMatrix;
    }

    private void removeVertexFromMatrix(Vertex<T> vertex) {
        if (adjacencyMatrix.length == 0) {
            return;
        }

        int size = adjacencyMatrix.length;
        int removeIndex = vertexList.indexOf(vertex);

        int[][] newMatrix = new int[size - 1][size - 1];
        for (int i = 0, realCounterI = 0; i < size; i++) {
            for (int j = 0, realCounterJ = 0; j < size; j++) {
                if (j == removeIndex) continue;

                newMatrix[realCounterI][realCounterJ] = adjacencyMatrix[i][j];
                realCounterJ++;

            }
            if (i == removeIndex) continue;
            realCounterI++;
        }
        adjacencyMatrix = newMatrix;
    }

    public void print() {
        if (adjacencyMatrix == null) {
            System.out.println("Массив пустой");
            return;
        }

        for (int[] matrix : adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(matrix[j] + "   ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void fromMatrixToGraph(int [][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j] != 0){
                    addEdge(new Edge<T>(
                            new Vertex<>(vertexList.get(i).getName()),
                            new Vertex<>(vertexList.get(j).getName()),
                            matrix[i][j]
                    ));
                }
            }
        }
    }

    public void getShortestPath(Vertex<T> startVertex, Vertex<T> endVertex) {
        restorePath(startVertex, endVertex, dexter(startVertex)[1]);
    }

    public int[][] dexter(Vertex<T> startVertex) {
        int SIZE_GRAPH = adjacencyMatrix.length;
        int[] distance = new int[SIZE_GRAPH];
        boolean[] isVisited = new boolean[SIZE_GRAPH];
        int[] prev = new int[SIZE_GRAPH];

        for (int i = 0; i < SIZE_GRAPH; i++) {
            distance[i] = Integer.MAX_VALUE;
            isVisited[i] = false;
        }
        distance[vertexList.indexOf(startVertex)] = 0;

        int indexMin = 0;
        int u; //обратываемая вершина

        for (int i = 0; i < SIZE_GRAPH; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < SIZE_GRAPH; j++) {   //ищем вершину с минимальным расстоянием
                if (!isVisited[j] && distance[j] < min) {
                    min = distance[j];
                    indexMin = j;
                }
            }

            u = indexMin;
            isVisited[u] = true;

            for (int j = 0; j < SIZE_GRAPH; j++) {
                if (!isVisited[j] && adjacencyMatrix[u][j] != 0 && distance[u] != Integer.MAX_VALUE) {
                    if (distance[u] + adjacencyMatrix[u][j] < distance[j]) {
                        distance[j] = distance[u] + adjacencyMatrix[u][j];
                        prev[j] = u;
                    }
                }
            }
        }
        /*
        System.out.println("Кратчайшие пути:");
        for (int i = 0; i < SIZE_GRAPH; i++) {
            if (distance[i] != Integer.MAX_VALUE) {
                System.out.println(startVertex + " -> " + i + " = " + distance[i]);
            } else {
                System.out.println(startVertex + " -> " + i + " = " + "error");
            }
        }
        for (int i = 0; i < SIZE_GRAPH; ++i) {
            System.out.println("Предок " + i + ": " + prev[i]);
        }
        */
        return new int[][]{distance, prev};
    }

    private void restorePath(Vertex<T> startVertex, Vertex<T> endVertex, int[] prevPath) {
        if (startVertex.equals(endVertex)) {
            System.out.println("Курьер в этом районе!");
            return;
        }

        int startVertexIndex = vertexList.indexOf(startVertex);
        int endVertexIndex = vertexList.indexOf(endVertex);

        Queue<Integer> path = new LinkedList<>();
        int nextVertex = prevPath[endVertexIndex];
        path.offer(nextVertex);
        while (nextVertex != startVertexIndex) {
            nextVertex = prevPath[nextVertex];
            path.offer(nextVertex);
        }

        System.out.print(endVertex.toString());
        while (!path.isEmpty()) {
            System.out.print(" -> " + vertexList.get(path.remove()).toString());
        }
        System.out.println();
    }

}

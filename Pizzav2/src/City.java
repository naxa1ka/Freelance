import Graph.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class City<T> {
    private final Graph<T> graph = new Graph<T>();
    private final List<Vertex<T>> courierList = new ArrayList<>();

    public Graph<T> getGraph() {
        return graph;
    }

    public void addCourier(Vertex<T> vertex){
        if (courierList.contains(vertex)){
            System.out.println("Курьер уже существует там!");
            return;
        }
        courierList.add(vertex);
    }

    public void removeCourier(Vertex<T> vertex){
        if (!courierList.contains(vertex)){
            System.out.println("Курьера не существует там!");
            return;
        }
        courierList.remove(vertex);
    }

    public void getShortestPath(Vertex<T> destinationVertex) {
        int minPath = Integer.MAX_VALUE;
        Vertex<T> minVertex = destinationVertex;
        int indexDestVertex = graph.getIndex(destinationVertex);
        for (Vertex<T> courierVertex:
             courierList) {
            int dist = graph.dexter(courierVertex)[0][indexDestVertex];
             if (minPath > dist){
                 minPath = dist;
                 minVertex = courierVertex;
             }
        }
        System.out.println("Курьер уже в пути!");
        System.out.println("Ему идти: " + minPath + " у.е.");
        graph.getShortestPath(minVertex, destinationVertex);
    }

}

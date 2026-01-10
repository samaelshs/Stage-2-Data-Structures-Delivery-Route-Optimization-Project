// File: CityGraph.java
import java.util.*;

public class CityGraph implements GraphADT {
    private class Node {
        int id;
        Coordinate coords;
        List<Edge> outgoing;

        Node(int id, Coordinate coords) {
            this.id = id;
            this.coords = coords;
            this.outgoing = new ArrayList<>();
        }
    }

    private class Edge {
        int to;
        double distance;
        double timeCost;

        Edge(int to, double distance, double timeCost) {
            this.to = to;
            this.distance = distance;
            this.timeCost = timeCost;
        }
    }



    private Map<Integer, Node> nodes;

    public CityGraph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void addNode(int id, Coordinate coords) throws RouteException {
        if (nodes.containsKey(id)) {
            throw new RouteException("Node ID " + id + " already exists.");
        }
        nodes.put(id, new Node(id, coords));
    }

    @Override
    public void addEdge(int u, int v, double distance, double speedLimit) throws RouteException {
        if (!nodes.containsKey(u)) throw new RouteException("Source node " + u + " does not exist.");
        if (!nodes.containsKey(v)) throw new RouteException("Target node " + v + " does not exist.");
        if (speedLimit <= 0) throw new RouteException("Speed limit must be positive.");

        double initialTime = distance / speedLimit;
        nodes.get(u).outgoing.add(new Edge(v, distance, initialTime));
    }

    @Override
    public void updateEdgeTime(int u, int v, double trafficFactor) throws RouteException {
        if (!nodes.containsKey(u)) throw new RouteException("Node " + u + " not found.");

        Node node = nodes.get(u);
        boolean found = false;
        for (Edge e : node.outgoing) {
            if (e.to == v) {
                e.timeCost = e.timeCost * trafficFactor;
                found = true;
                break;
            }
        }
        if (!found) throw new RouteException("Edge " + u + "->" + v + " does not exist.");
    }

    @Override
    public boolean isReachable(int u, int v) {
        return false;
    }

    @Override
    public double getShortestTime(int sourceId, int targetId) throws RouteException {
        if (!nodes.containsKey(sourceId)) throw new RouteException("Source node " + sourceId + " missing.");
        if (!nodes.containsKey(targetId)) throw new RouteException("Target node " + targetId + " missing.");

        PriorityQueue<double[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
        Map<Integer, Double> dist = new HashMap<>();

        for (int id : nodes.keySet()) dist.put(id, Double.MAX_VALUE);

        dist.put(sourceId, 0.0);
        pq.offer(new double[]{sourceId, 0.0});

        while (!pq.isEmpty()) {
            double[] current = pq.poll();
            int u = (int) current[0];
            double timeSoFar = current[1];

            if (timeSoFar > dist.get(u)) continue;
            if (u == targetId) return timeSoFar;

            Node node = nodes.get(u);
            if (node != null) {
                for (Edge e : node.outgoing) {
                    double newTime = timeSoFar + e.timeCost;
                    if (newTime < dist.get(e.to)) {
                        dist.put(e.to, newTime);
                        pq.offer(new double[]{e.to, newTime});
                    }
                }
            }
        }
        return Double.MAX_VALUE;
    }

    public void printGraphStatus() {
        if (nodes.isEmpty()) {
            System.out.println("Map is empty.");
            return;
        }
        System.out.println("\n--- Current City Map ---");
        for (Integer id : nodes.keySet()) {
            Node n = nodes.get(id);
            System.out.printf("Node %d at %s connects to:%n", id, n.coords);
            if (n.outgoing.isEmpty()) {
                System.out.println("   (No outgoing roads)");
            } else {
                for (Edge e : n.outgoing) {
                    System.out.printf("   -> Node %d [Dist: %.2f km, Speed: %.1f km/h]%n",
                            e.to, e.distance, (e.distance / e.timeCost));
                }
            }
        }
    }
}
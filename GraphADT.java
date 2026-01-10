import java.util.List;

public interface GraphADT {
    // Add 'throws RouteException' to these lines:
    void addNode(int id, Coordinate coords) throws RouteException;
    void addEdge(int u, int v, double distance, double speedLimit) throws RouteException;
    void updateEdgeTime(int u, int v, double trafficFactor) throws RouteException;
    double getShortestTime(int sourceId, int targetId) throws RouteException;

    // This one usually doesn't need it, but check your own code:
    boolean isReachable(int u, int v);
}
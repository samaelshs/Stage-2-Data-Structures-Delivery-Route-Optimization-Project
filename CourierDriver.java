import java.util.ArrayList;
import java.util.List;

public class CourierDriver implements DriverADT {
    private int id;
    private double capacity;
    private double currentLoad;
    private List<TaskADT> route;
    private Coordinate startLocation;

    public CourierDriver(int id, double capacity, Coordinate startLocation) {
        this.id = id;
        this.capacity = capacity;
        this.startLocation = startLocation;
        this.currentLoad = 0;
        this.route = new ArrayList<>();
    }

    @Override
    public void assignTask(TaskADT task) throws RouteException {
        if (!checkCapacity(task.getLoad())) {
            throw new RouteException("Driver capacity exceeded. Cannot assign Task " + task.getId());
        }
        route.add(task);
        currentLoad += task.getLoad();
    }

    @Override
    public boolean checkCapacity(double taskLoad) {
        return (currentLoad + taskLoad) <= capacity;
    }

    @Override
    public double calculateTotalRouteDistance() {
        if (route.isEmpty()) return 0.0;
        double totalDist = startLocation.distanceTo(route.get(0).getCoordinate());
        for (int i = 0; i < route.size() - 1; i++) {
            totalDist += route.get(i).getCoordinate().distanceTo(route.get(i+1).getCoordinate());
        }
        return totalDist;
    }

    @Override
    public void printRoute() {
        System.out.println("Driver " + id + " Route (" + currentLoad + "/" + capacity + "kg):");
        for(TaskADT t : route) System.out.println(" - " + t);
    }
}
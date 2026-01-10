// File: DeliveryTask.java
public class DeliveryTask implements TaskADT {
    private int id;
    private Coordinate location;
    private double startTime;
    private double endTime;
    private double load;
    private int priority;

    public DeliveryTask(int id, Coordinate location, double startTime, double endTime, double load, int priority) {
        if (startTime > endTime) throw new IllegalArgumentException("Start time cannot be after end time");
        if (load < 0) throw new IllegalArgumentException("Load cannot be negative");

        this.id = id;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.load = load;
        this.priority = priority;
    }

    @Override
    public Coordinate getCoordinate() { return location; }

    @Override
    public boolean isFeasible(double arrivalTime) { return arrivalTime <= endTime; }

    @Override
    public double getWaitTime(double arrivalTime) {
        return (arrivalTime < startTime) ? (startTime - arrivalTime) : 0.0;
    }

    @Override
    public int getId() { return id; }
    @Override
    public double getLoad() { return load; }

    @Override
    public String toString() {
        return String.format("Task %d [Window: %.1f-%.1f, Load: %.1f] @ %s", id, startTime, endTime, load, location);
    }
}
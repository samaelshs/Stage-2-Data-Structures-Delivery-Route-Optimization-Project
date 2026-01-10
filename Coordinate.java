// File: Coordinate.java
public class Coordinate {
    private final double x;
    private final double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public double distanceTo(Coordinate other) {
        if (other == null) throw new IllegalArgumentException("Target coordinate cannot be null");
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() { return String.format("(%.2f, %.2f)", x, y); }
}
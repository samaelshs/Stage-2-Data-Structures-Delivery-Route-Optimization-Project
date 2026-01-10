import java.util.List;

public interface SpatialADT {
    // [AI Journal Reference: Based on Design Document 1.3]

    /**
     * Inserts task into spatial index.
     * Precondition: Task is not null.
     * Postcondition: Task is added to the spatial structure.
     */
    void insertTask(TaskADT task);

    /**
     * Core clustering operation.
     * Precondition: Radius > 0.
     * Postcondition: Returns list of tasks within radius of coord.
     */
    List<TaskADT> findNearestNeighbors(Coordinate coord, double radius);
}
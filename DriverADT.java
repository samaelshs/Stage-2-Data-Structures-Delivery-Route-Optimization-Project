public interface DriverADT {
    // [AI Journal Reference: Based on Design Document 1.4]

    /**
     * Appends task to route.
     * Precondition: Capacity check passed.
     * Postcondition: Task added to route sequence.
     */
    void assignTask(TaskADT task) throws RouteException;

    /**
     * Computes distance of full route.
     * Precondition: None.
     * Postcondition: Returns total static distance.
     */
    double calculateTotalRouteDistance();

    /**
     * Ensures vehicle can carry load.
     * Precondition: task_load >= 0.
     * Postcondition: Returns true if adding load doesn't exceed capacity.
     */
    boolean checkCapacity(double taskLoad);

    void printRoute();
}
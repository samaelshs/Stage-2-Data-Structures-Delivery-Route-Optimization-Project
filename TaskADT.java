public interface TaskADT {
    // [AI Journal Reference: Methods generated based on Design Document 1.2 Essential Operations]

    /**
     * Access delivery point coordinates.
     * Precondition: Task must be initialized.
     * Postcondition: Returns the Coordinate object of the task.
     */
    Coordinate getCoordinate();

    /**
     * Validates time window feasibility.
     * Precondition: driver arrival time and current global time are non-negative.
     * Postcondition: Returns true if arrival is within [start, end], false otherwise.
     */
    boolean isFeasible(double arrivalTime);

    /**
     * Calculates required waiting time for early arrivals.
     * Precondition: arrivalTime is non-negative.
     * Postcondition: Returns 0 if arrival >= start, otherwise returns start - arrival.
     */
    double getWaitTime(double arrivalTime);

    int getId();
    double getLoad();
}
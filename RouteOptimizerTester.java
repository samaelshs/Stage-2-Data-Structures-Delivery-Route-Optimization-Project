import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Random;

// [cite: 223] "A Tester Class... This will contains the main() method"
public class RouteOptimizerTester {

    private static Scanner scanner = new Scanner(System.in);

    // Global instances for Interactive Mode
    private static CityGraph city = new CityGraph();
    private static SpatialADT spatialIndex = new SpatialIndex();
    private static DriverADT driver = new CourierDriver(1, 100.0, new Coordinate(0,0));

    // [cite: 226] "main() method"
    public static void main(String[] args) {
        System.out.println("=== CCS2110 Coursework: Courier Delivery Route Optimizer ===");

        // Safety Check: Print this to prove you are running the new code
        System.out.println("!!! SYSTEM READY: MENU LOADED !!!");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Run Assessment Demo (Hardcoded Scenario + Performance)");
            System.out.println("2. Interactive Mode (Build & Test Manually)");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    // [cite: 142] "main() method should contain adequate hardcoded initializations"
                    runHardcodedScenario();
                    // [cite: 229] "measure the time each run takes"
                    measureDijkstraPerformance();
                    System.out.println("\n[Info] Assessment Demo Complete. Returning to menu...");
                    break;
                case 2:
                    runInteractiveMode();
                    break;
                case 3:
                    System.out.println("Exiting application.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // --- 1. HARDCODED SCENARIO (Assessment Requirement) ---
    private static void runHardcodedScenario() {
        System.out.println("\n[System] Running Hardcoded Sample Scenario...");
        try {
            CityGraph demoCity = new CityGraph();
            demoCity.addNode(0, new Coordinate(0, 0));   // Depot
            demoCity.addNode(1, new Coordinate(2, 5));   // North
            demoCity.addNode(2, new Coordinate(5, 5));   // East

            demoCity.addEdge(0, 1, 5.5, 50.0);
            demoCity.addEdge(1, 2, 3.0, 40.0);
            demoCity.addEdge(0, 2, 8.0, 60.0);

            // [cite: 147] "Most Significant Operation" (Dijkstra)
            System.out.println("Calculating shortest time from Depot(0) to East(2)...");
            double time = demoCity.getShortestTime(0, 2);
            System.out.printf("Shortest Time: %.4f hours%n", time);

            SpatialADT demoSpatial = new SpatialIndex();
            // [cite: 142] "showcasing the ADTs"
            demoSpatial.insertTask(new DeliveryTask(101, new Coordinate(1.9, 5.1), 9, 12, 10, 1));

        } catch (Exception e) {
            System.out.println("Scenario Error: " + e.getMessage());
        }
    }

    // --- 2. PERFORMANCE MEASUREMENT (Assessment Requirement) ---
    private static void measureDijkstraPerformance() {
        System.out.println("\n[System] Measuring Algorithm Performance...");
        // [cite: 229] "test this operation with 3 different data sets of different sizes"
        int[] dataSizes = {100, 1000, 5000};
        Random rand = new Random();

        for (int size : dataSizes) {
            CityGraph testGraph = new CityGraph();
            try {
                // [cite: 230] "You can use an AI tool to generate these data sets" (Simulated here with Random)
                for (int i = 0; i < size; i++) {
                    try { testGraph.addNode(i, new Coordinate(rand.nextDouble() * 100, rand.nextDouble() * 100)); }
                    catch (Exception ignored) {}
                }
                for (int i = 0; i < size; i++) {
                    int u = rand.nextInt(size);
                    int v = rand.nextInt(size);
                    if (u != v) {
                        try { testGraph.addEdge(u, v, rand.nextDouble() * 10 + 1, 50.0); }
                        catch (Exception ignored) {}
                    }
                }

                long startTime = System.nanoTime();
                testGraph.getShortestTime(0, size - 1);
                long endTime = System.nanoTime();

                System.out.printf("Size: %-5d nodes | Time: %.4f ms%n", size, (endTime - startTime) / 1e6);

            } catch (Exception e) {
                System.out.println("Performance test failed: " + e.getMessage());
            }
        }
    }

    // --- 3. INTERACTIVE MODE (Your Custom Feature) ---
    private static void runInteractiveMode() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- INTERACTIVE MODE ---");
            System.out.println("1. Add Map Node");
            System.out.println("2. Add Map Edge");
            System.out.println("3. Create Delivery Task");
            System.out.println("4. Assign Tasks (Spatial Search)");
            System.out.println("5. Calculate Path (Dijkstra)");
            System.out.println("6. Return to Main Menu");
            System.out.print("Select action: ");

            int choice = getIntInput();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Node ID: "); int id = getIntInput();
                        System.out.print("X: "); double x = getDoubleInput();
                        System.out.print("Y: "); double y = getDoubleInput();
                        city.addNode(id, new Coordinate(x, y));
                        System.out.println("Node added.");
                        break;
                    case 2:
                        System.out.print("From ID: "); int u = getIntInput();
                        System.out.print("To ID: "); int v = getIntInput();
                        System.out.print("Distance: "); double dist = getDoubleInput();
                        System.out.print("Speed Limit: "); double speed = getDoubleInput();
                        city.addEdge(u, v, dist, speed);
                        System.out.println("Edge added.");
                        break;
                    case 3:
                        System.out.print("Task ID: "); int tid = getIntInput();
                        System.out.print("X: "); double tx = getDoubleInput();
                        System.out.print("Y: "); double ty = getDoubleInput();
                        System.out.print("Load: "); double load = getDoubleInput();
                        spatialIndex.insertTask(new DeliveryTask(tid, new Coordinate(tx, ty), 9, 17, load, 1));
                        System.out.println("Task added.");
                        break;
                    case 4:
                        System.out.print("Search Center X: "); double cx = getDoubleInput();
                        System.out.print("Search Center Y: "); double cy = getDoubleInput();
                        System.out.print("Radius: "); double rad = getDoubleInput();
                        List<TaskADT> tasks = spatialIndex.findNearestNeighbors(new Coordinate(cx, cy), rad);
                        System.out.println("Found " + tasks.size() + " tasks.");
                        for (TaskADT t : tasks) {
                            try { driver.assignTask(t); System.out.println("Assigned Task " + t.getId()); }
                            catch (Exception e) { System.out.println("Skipped Task " + t.getId() + ": " + e.getMessage()); }
                        }
                        driver.printRoute();
                        break;
                    case 5:
                        System.out.print("Start Node: "); int start = getIntInput();
                        System.out.print("End Node: "); int end = getIntInput();
                        double result = city.getShortestTime(start, end);
                        if (result == Double.MAX_VALUE) System.out.println("Unreachable.");
                        else System.out.printf("Travel Time: %.4f hours%n", result);
                        break;
                    case 6:
                        inMenu = false;
                        break;
                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                // Catches RouteException and any other issues
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // --- INPUT HELPERS ---
    private static int getIntInput() {
        while (true) {
            try { return scanner.nextInt(); }
            catch (InputMismatchException e) {
                scanner.next();
                System.out.print("Invalid integer. Try again: ");
            } catch (Exception e) {
                System.out.println("Input stream error. Exiting.");
                System.exit(1);
                return -1;
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try { return scanner.nextDouble(); }
            catch (InputMismatchException e) {
                scanner.next();
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
// Add this method inside your main class
private static void populateRandomCity() {
    System.out.print("Enter number of nodes to generate (e.g., 20): ");
    int size = getIntInput();
    
    Random rand = new Random();
    int edgesCount = 0;
    
    // 1. Clear existing data (Optional: remove this if you want to add to existing map)
    city = new CityGraph();
    spatialIndex = new SpatialIndex();
    driver = new CourierDriver(1, 100.0, new Coordinate(0,0));
    
    System.out.println("Generating " + size + " random locations...");
    
    try {
        // 2. Generate Nodes
        for (int i = 0; i < size; i++) {
            double x = rand.nextDouble() * 100; // Map size 100x100
            double y = rand.nextDouble() * 100;
            try {
                city.addNode(i, new Coordinate(x, y));
            } catch (RouteException ignored) {} 
        }

        // 3. Generate Edges (Connect them randomly)
        // Try to connect each node to 2-3 other nodes on average
        for (int i = 0; i < size; i++) {
            int connections = rand.nextInt(3) + 1; // 1 to 3 roads per node
            for (int k = 0; k < connections; k++) {
                int target = rand.nextInt(size);
                if (i != target) {
                    double dist = rand.nextDouble() * 15 + 1; // 1km to 16km
                    double speed = rand.nextDouble() * 60 + 30; // 30km/h to 90km/h
                    try {
                        city.addEdge(i, target, dist, speed);
                        edgesCount++;
                    } catch (RouteException ignored) {}
                }
            }
        }

        // 4. Generate Random Tasks
        int tasksCount = size / 2; // Create half as many tasks as nodes
        for (int i = 0; i < tasksCount; i++) {
            double tx = rand.nextDouble() * 100;
            double ty = rand.nextDouble() * 100;
            double load = rand.nextDouble() * 15 + 1; // 1kg to 16kg
            spatialIndex.insertTask(new DeliveryTask(1000 + i, new Coordinate(tx, ty), 9, 17, load, 1));
        }

        System.out.println("Success! Created city with " + size + " nodes, " + edgesCount + " edges, and " + tasksCount + " tasks.");

    } catch (Exception e) {
        System.out.println("Error generating data: " + e.getMessage());
    }
}
    
}

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;
import java.util.Random;

public class RouteOptimizerApp {

    private static Scanner scanner = new Scanner(System.in);

    // Global instances for Interactive Mode
    private static CityGraph city = new CityGraph();
    private static SpatialADT spatialIndex = new SpatialIndex();
    private static DriverADT driver = new CourierDriver(1, 100.0, new Coordinate(0,0));

    public static void main(String[] args) {
        System.out.println("=== CCS2110 Coursework: Courier Delivery Route Optimizer ===");
        System.out.println("!!! I AM THE NEW VERSION WITH MAP VIEW !!!");

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
                    runHardcodedScenario();
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

            System.out.println("Calculating shortest time from Depot(0) to East(2)...");
            double time = demoCity.getShortestTime(0, 2);
            System.out.printf("Shortest Time: %.4f hours%n", time);

            SpatialADT demoSpatial = new SpatialIndex();
            demoSpatial.insertTask(new DeliveryTask(101, new Coordinate(1.9, 5.1), 9, 12, 10, 1));

        } catch (Exception e) {
            System.out.println("Scenario Error: " + e.getMessage());
        }
    }

    // --- 2. PERFORMANCE MEASUREMENT (Assessment Requirement) ---
    private static void measureDijkstraPerformance() {
        System.out.println("\n[System] Measuring Algorithm Performance...");
        int[] dataSizes = {100, 1000, 5000};
        Random rand = new Random();

        for (int size : dataSizes) {
            CityGraph testGraph = new CityGraph();
            try {
                for (int i = 0; i < size; i++) {
                    try { testGraph.addNode(i, new Coordinate(rand.nextDouble() * 100, rand.nextDouble() * 100)); }
                    catch (RouteException ignored) {}
                }
                for (int i = 0; i < size; i++) {
                    int u = rand.nextInt(size);
                    int v = rand.nextInt(size);
                    if (u != v) {
                        try { testGraph.addEdge(u, v, rand.nextDouble() * 10 + 1, 50.0); }
                        catch (RouteException ignored) {}
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

    // --- 3. INTERACTIVE MODE ---
    private static void runInteractiveMode() {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println("\n--- INTERACTIVE MODE ---");
            System.out.println("1. Add Map Node");
            System.out.println("2. Add Map Edge");
            System.out.println("3. Create Delivery Task");
            System.out.println("4. Assign Tasks (Spatial Search)");
            System.out.println("5. Calculate Path (Dijkstra)");
            System.out.println("6. VIEW MAP STATUS"); // <--- NEW OPTION
            System.out.println("7. Return to Main Menu");
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
                            catch (RouteException e) { System.out.println("Skipped Task " + t.getId() + ": " + e.getMessage()); }
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
                        // Call the new method we just made
                        city.printGraphStatus();
                        break;

                    case 7:
                        inMenu = false;
                        break;

                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
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
                // Catches stream errors if run in non-interactive environments
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
}
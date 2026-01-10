User Guide: Courier Delivery Route Optimizer
1. Introduction Welcome to the Courier Route Optimizer! This application acts as a "digital dispatcher" for a delivery company. It helps you build a virtual map of a city, track delivery packages, and calculate the fastest routes for drivers using smart algorithms. It is designed to demonstrate Graph Data Structures and Pathfinding Algorithms in action.


2. How to Start the App

Open your Java environment (e.g., IntelliJ, Eclipse, or Command Prompt).

Locate and run the file named RouteOptimizerTester.

You will see a menu appear on the screen:

Plaintext

--- MAIN MENU ---
1. Run Assessment Demo (Hardcoded Scenario + Performance)
2. Interactive Mode (Build & Test Manually)
3. Exit
3. Main Menu Options

Option 1: Run Assessment Demo

Best for: Seeing a quick, automatic example of the system working without typing anything.


What happens: The app runs a pre-scripted scenario required for the coursework assessment. It builds a tiny test city, assigns a package, and calculates one route automatically.


Performance Test: It also runs a "speed test" to show how fast the algorithm can handle 100, 1,000, and 5,000 locations.

Option 2: Interactive Mode (Build Your Own City)

Best for: Testing specific routes, playing "city architect," or manually verifying your design.

How to use it: When you select Option 2, you enter the Interactive Menu. Follow these steps in order to build a working simulation:

Action 1: Add Map Node (Build Locations)

Node ID: Give your location a unique number (e.g., 0 for Depot, 1 for Customer).

X / Y: Enter coordinates (e.g., 10 and 20). Think of this like points on graph paper.

Action 2: Add Map Edge (Build Roads)

From ID / To ID: Enter the numbers of the two locations you want to connect (e.g., 0 to 1).

Distance: How long is the road? (e.g., 5.5 km).

Speed Limit: How fast can cars go? (e.g., 50 km/h).

Note: Roads are one-way! To make a two-way street, add another edge from 1 back to 0.

Action 3: Create Delivery Task (Add Packages)

Task ID: Give the package a unique number (e.g., 101).

Load: How heavy is it? (e.g., 10.5 kg). Note: The driver has a maximum capacity of 100kg.

Action 4: Assign Tasks (Dispatch Driver)

Search Center: Enter X and Y coordinates to tell the driver where to look.

Radius: Enter a distance (e.g., 5.0). The driver will pick up all packages within this circle, provided they fit in the van.

Action 5: Calculate Path (Run AI)

Start Node / End Node: Enter the IDs of where the driver starts and ends.


Result: The app uses Dijkstra’s Algorithm to calculate the fastest time to travel, considering the speed limits of the roads you built.

Action 6: VIEW MAP STATUS

What it does: Prints a text summary of your entire city. It lists every location (Node) and every road (Edge) connected to it. Use this if you forget which IDs you have created.

4. Troubleshooting / FAQ

"Target Unreachable": This means there is no road connecting your Start Node to your End Node. You might need to add more Edges (Action 2).

"Input Stream Error" / Program Crash: You likely typed a letter (like "A") when the app expected a number. Restart the app and try again.

"Driver Capacity Exceeded": You tried to assign a package that made the total weight go over 100kg. The driver refused it to stay safe.

"Map is empty": You tried to View Map or Calculate Path before using Action 1 to build any nodes.

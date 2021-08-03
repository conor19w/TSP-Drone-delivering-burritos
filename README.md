# TSP-Drone-delivering-burritos
Genetic Algorithm to solve a TSP problem involving delivering burritos by drone, with time constraints.

Problem:
-Deliver 100 burritos while trying to minimise customer dis-satisfaction.
-We are given the time[] array which contains the length of time each customer has been waiting for their burrito.
-The goal is to minimize the sum of this array while delivering the burritos to the specified locations, given as longitudes and latitudes.
-The Drone moves at a constant speed.
-To calculate the distance between points on the path we can use the haversine distance formula for calculating distances along great circles.

Solution:
-Generate random paths
-calculate the customer dis-satifaction for each path and track the fitness of a path.
-implement genetic algorithm which uses crossover & mutation to combine paths without repetition of locations.
-new populations will be bred from the old population with the fitness playing a role in the probability of a path being chosen.
-New populations should have improved fitness.

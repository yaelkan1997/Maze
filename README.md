# Maze Solver

This project implements a maze generator and solver in Java, offering various algorithms for both maze generation and solving. The project structure includes client-server communication, compression and decompression of maze data, and a graphical user interface for visualization.

## Client

The Client class handles the communication with the server, utilizing a strategy pattern through the IClientStrategy interface. The client can execute various strategies for interacting with the server.

## Maze Generation

### The project includes different maze generation algorithms, such as:

- EmptyMazeGenerator: Generates an empty maze.
- SimpleMazeGenerator: Generates a simple maze with random walls.
- MyMazeGenerator: Implements a randomized depth-first search algorithm for maze generation.

## Maze Solving

### The Solver class provides implementations for solving mazes using algorithms like:

- Depth-First Search (DFS)
- Breadth-First Search (BFS)

## IO Operations

### The IO package includes classes for compressing and decompressing maze data:

- MyCompressorOutputStream: Compresses maze data.
- MyDecompressorInputStream: Decompresses maze data.



# -------------------------------------------------------------------
# DON'T CHANGE THIS FILE.
# Prim's maze generator.
#
# __author__ = 'Jeffrey Chan'
# __copyright__ = 'Copyright 2024, RMIT University'
# -------------------------------------------------------------------

from maze.maze3D import Maze3D
from maze.util import Coordinates3D
from generation.mazeGenerator import MazeGenerator



import random
from collections import deque

# Code Implemented By SHIVANSH SINGHAL S3957577

class PrimMazeGenerator(MazeGenerator):
    """
    Prim's algorithm maze generator.  
    TODO: Complete the implementation (Task A)
    """

    def generateMaze(self, maze: Maze3D):
        # Initialize the maze with all walls
        maze.initCells(True)

        # Choose a random starting cell
        start_level = random.randint(0, maze.levelNum() - 1)
        start_row = random.randint(0, maze.rowNum(start_level) - 1)
        start_col = random.randint(0, maze.colNum(start_level) - 1)
        start_cell = Coordinates3D(start_level, start_row, start_col)
        print(start_cell)

        # visited set and frontier deque
        visited = set()
        visited.add(start_cell)
        frontier = deque(maze.neighbours(start_cell))

        while frontier:
            # Choose a random cell from the frontier
            current_cell = random.choice(frontier)

            # Find a neighbor of the current cell that is in the visited set and not on the boundary
            neighbors = maze.neighbours(current_cell)
            valid_neighbors = [n for n in neighbors if n in visited and not maze.isBoundary(n)]

            if valid_neighbors:
                neighbor = random.choice(valid_neighbors)
                
                # Remove the wall between the current cell and its neighbor
                maze.removeWall(current_cell, neighbor)

                # Add the current cell to the visited set
                visited.add(current_cell)

                # Remove the current cell from the frontier
                frontier.remove(current_cell)

                # Add unvisited neighbors of the current cell to the frontier
                new_neighbors = [n for n in neighbors if n not in visited and n not in frontier and not maze.isBoundary(n)]

                frontier.extend(new_neighbors)
            else:
                # Remove the current cell from the frontier if no valid neighbors
                frontier.remove(current_cell)

        # Update maze generated flag
        self.m_mazeGenerated = True

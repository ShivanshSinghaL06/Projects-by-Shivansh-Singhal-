# -------------------------------------------------------------------
# PLEASE UPDATE THIS FILE.
# Implementation of Task D maze generator.

# __author__ = 'Jeffrey Chan'
# __copyright__ = 'Copyright 2024, RMIT University'
# -------------------------------------------------------------------


# from maze.maze3D import Maze3D
# from maze.util import Coordinates3D
# from generation.mazeGenerator import MazeGenerator


# class TaskDMazeGenerator(MazeGenerator):
#     """
#     TODO: Complete the implementation (Task D)
#     """
	
#     def generateMaze(self, maze:Maze3D):
#         # TODO: Implement this method for task A.
#         pass
		
# Code Implemented By SHIVANSH SINGHAL S3957577

from maze.maze3D import Maze3D
from maze.util import Coordinates3D
from generation.mazeGenerator import MazeGenerator
import random

class TaskDMazeGenerator(MazeGenerator):
    """
    Maze generator for Task D: Maximising Solver Exploration of Cells.
    """
    
    def generateMaze(self, maze: Maze3D):
        # Initialize the cells and walls
        maze.initCells(addWallFlag=True)
        
        # Get the number of levels
        levels = maze.levelNum()
        
        # Initialize walls and visited sets
        walls = []
        visited = set()
        
        # Starting point for the maze generation
        start_level = random.randint(0, levels - 1)
        start_row = random.randint(0, maze.rowNum(start_level) - 1)
        start_col = random.randint(0, maze.colNum(start_level) - 1)
        
        start = Coordinates3D(start_level, start_row, start_col)
        
        # Mark the start point as visited
        visited.add(start)
        
        # Add walls of the starting cell to the walls list
        walls.extend(maze.neighbourWalls(start))
        
        while walls:
            # Pick a random wall
            wall = random.choice(walls)
            walls.remove(wall)
            
            # Get the cells divided by this wall
            cell1, cell2 = wall
            
            # Check if both cells are valid coordinates in the maze
            if maze.checkCoordinates(cell1) and maze.checkCoordinates(cell2):
                # If one of the cells hasn't been visited
                if (cell1 in visited) != (cell2 in visited):
                    # Determine the unvisited cell
                    unvisited = cell1 if cell1 not in visited else cell2
                    
                    # Mark the unvisited cell as visited
                    visited.add(unvisited)
                    
                    # Carve a path between the two cells
                    if not maze.isBoundary(cell1) and not maze.isBoundary(cell2):
                        maze.removeWall(cell1, cell2)
                    
                    # Add the neighboring walls of the unvisited cell to the walls list
                    for neighbor_wall in maze.neighbourWalls(unvisited):
                        n_cell1, n_cell2 = neighbor_wall
                        if (n_cell1 not in visited) or (n_cell2 not in visited):
                            walls.append(neighbor_wall)
        
        # Indicate that the maze has been generated
        self.m_mazeGenerated = True

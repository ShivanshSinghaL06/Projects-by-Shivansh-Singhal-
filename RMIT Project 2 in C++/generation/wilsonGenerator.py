# -------------------------------------------------------------------
# PLEASE UPDATE THIS FILE.
# Wilson's algorithm maze generator.
#
# __author__ = 'Jeffrey Chan'
# __copyright__ = 'Copyright 2024, RMIT University'
# -------------------------------------------------------------------


# from maze.maze3D import Maze3D
# from maze.util import Coordinates3D
# from generation.mazeGenerator import MazeGenerator
# import random

# class WilsonMazeGenerator(MazeGenerator):
#     """
#     Wilson algorithm maze generator.
#     TODO: Complete the implementation (Task A)
#     """
	

#     def generateMaze(self, maze:Maze3D):
#         # TODO: Implement this method for task A.
#          pass


# Code Implemented By SHIVANSH SINGHAL S3957577

from maze.maze3D import Maze3D
from maze.util import Coordinates3D
from generation.mazeGenerator import MazeGenerator
import random

class WilsonMazeGenerator(MazeGenerator):
    """ Wilson's algorithm maze generator. """

    def __init__(self):
        self.unvisited_cells = set()

    def generateMaze(self, maze: Maze3D):
        maze.initCells(True)

        # Select a random starting cell and mark it as finalized
        start_level = random.randint(0, maze.levelNum() - 1)
        start_row = random.randint(0, maze.rowNum(start_level) - 1)
        start_col = random.randint(0, maze.colNum(start_level) - 1)
        start_cell = Coordinates3D(start_level, start_row, start_col)

        finalized = set([start_cell])
        self.unvisited_cells = set(maze.allCells()) - finalized

        while self.unvisited_cells:
            walk_start = random.choice(list(self.unvisited_cells))
            self.unvisited_cells.remove(walk_start)

            path = []
            curr_cell = walk_start
            visited = set([curr_cell])
            
            while curr_cell not in finalized:
                neighbors = [n for n in maze.neighbours(curr_cell) if n not in visited and not maze.isBoundary(n)]
                if neighbors:
                    next_cell = random.choice(neighbors)
                    path.append((curr_cell, next_cell))
                    visited.add(next_cell)
                    curr_cell = next_cell
                else:
                    path = [(curr_cell, path[0][0])]  # Backtrack to the start of path
                    break

            # Carve the path and mark cells as finalized
            for cell1, cell2 in path:
                if not maze.isBoundary(cell1) and not maze.isBoundary(cell2):
                    maze.removeWall(cell1, cell2)
                    finalized.add(cell1)
            finalized.add(curr_cell)

        self.m_mazeGenerated = True
        
 
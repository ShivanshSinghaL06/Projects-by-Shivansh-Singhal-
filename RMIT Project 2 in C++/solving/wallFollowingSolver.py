# # -------------------------------------------------------------------
# # PLEASE UPDATE THIS FILE.
# # Wall following maze solver.

# # __author__ = 'Jeffrey Chan'
# # __copyright__ = 'Copyright 2024, RMIT University'
# # -------------------------------------------------------------------


# Code Implemented By SHIVANSH SINGHAL S3957577

from maze.maze3D import Maze3D
from solving.mazeSolver import MazeSolver
from maze.util import Coordinates3D
class WallFollowingSolver(MazeSolver):
    def __init__(self):
        super().__init__()
        self.m_name = "wall"
        self.directions = {
            'N': Coordinates3D(0, 1, 0),   # NORTH
            'U': Coordinates3D(1, 0, 0),   # UP
            'E': Coordinates3D(0, 0, 1),   # EAST
            'S': Coordinates3D(0, -1, 0),  # SOUTH
            'D': Coordinates3D(-1, 0, 0),  # DOWN
            'W': Coordinates3D(0, 0, -1)  # WEST
        }
        self.current_direction = 'N'  # Start facing NORTH
        self.opposite_direction = None
        self.directions_order = {
            'N': ['E', 'U','N','W', 'D', 'S'],  # NORTH
            'U': ['S', 'E','U','N','W', 'D'],  # UP
            'E': ['D','S','E','U','N','W'],  # EAST
            'S': ['W','D','S','E','U','N'],  # SOUTH
            'W': ['U','N','W','D','S','E'],  # WEST
            'D': ['N','W','D','S','E','U']}  # DOWN

    def get_opposite_direction(self, direction: str):
        """
        Get the opposite direction of the given direction.
        
        Parameters:
        - direction: The current direction.

        Returns:
        - The opposite direction.
        """
        opposites = {
            'N': 'S',
            'S': 'N',
            'E': 'W',
            'W': 'E',
            'U': 'D',
            'D': 'U'
        }
        self.opposite_direction = opposites[direction]
        print("setting self.opposite_direction ", opposites[direction])

    def rotate_left(self, previous_direction: str):
        if previous_direction in self.directions_order:
            return self.directions_order[previous_direction]
        else:
            print("Invalid previous direction")
            return None


    def checking_wall_direction(self, maze: Maze3D, current_cell: Coordinates3D):
        array_direction = self.directions_order.get(self.current_direction)

        for dir in array_direction:
            next_cell = current_cell + self.directions[dir]
            # Check if the next cell is within the maze boundaries and valid
            if maze.hasCell(next_cell) and maze.checkCoordinates(next_cell) and not maze.hasWall(current_cell, next_cell):
                print("Not having right wall, changing direction")
                print("Coordinate --> ", next_cell)
                self.current_direction = dir
                print("Setting new Direction --> ", self.current_direction)
                return next_cell

        print("No change in direction, moving forward")
        return current_cell + self.directions[self.current_direction]                

    def solveMaze(self, maze: Maze3D, entrance: Coordinates3D):
        self.m_solved = False
        self.m_solution = []

        current_cell = entrance
        print("entrance current_cell ", current_cell)

        self.solverPathAppend(current_cell, False)

        current_cell = current_cell + self.directions[self.current_direction]
        # print(f"Starting at current_cell: ", current_cell)

        # print("current_direction -->    ", self.current_direction)

        # self.get_opposite_direction(self.current_direction)

        while current_cell not in maze.getExits():
            print("now calling --->     checking_wall_direction")
            next_cell = self.checking_wall_direction(maze, current_cell)
            if maze.hasCell(next_cell) and maze.checkCoordinates(next_cell):
                self.solverPathAppend(next_cell, False)
                current_cell = next_cell
                print(f"Moved to: {current_cell}")
                # print("Current opposite direction--> ", self.opposite_direction)
            else:
                break

        if current_cell in maze.getExits():
            self.solved(entrance, current_cell)
            print(f"Solved! Exit found at: {current_cell}")

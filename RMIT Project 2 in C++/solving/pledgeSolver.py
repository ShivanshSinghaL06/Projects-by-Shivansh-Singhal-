from maze.maze3D import Maze3D
from solving.mazeSolver import MazeSolver
from maze.util import Coordinates3D


# Code Implemented By SHIVANSH SINGHAL S3957577

class PledgeMazeSolver(MazeSolver):
    def __init__(self):
        super().__init__()
        self.m_name = "pledge"
        self.directions = {
            'N': Coordinates3D(0, 1, 0),   # NORTH
            'U': Coordinates3D(1, 0, 0),   # UP
            'E': Coordinates3D(0, 0, 1),   # EAST
            'S': Coordinates3D(0, -1, 0),  # SOUTH
            'D': Coordinates3D(-1, 0, 0),  # DOWN
            'W': Coordinates3D(0, 0, -1)   # WEST
        }
        self.current_direction = 'N'  # Start facing NORTH
        self.total_turning = 0

    def get_opposite_direction(self, direction: str):
        opposites = {
            'N': 'S',
            'S': 'N',
            'E': 'W',
            'W': 'E',
            'U': 'D',
            'D': 'U'
        }
        return opposites[direction]


    def rotate_left(self):
        directions = ['N', 'W', 'S', 'E', 'U', 'D']
        index = directions.index(self.current_direction)
        new_index = (index + 1) % len(directions)
        self.current_direction = directions[new_index]
        self.total_turning += 60  # Increment angle for left rotation
        print(f"Rotated left, new direction: {self.current_direction}, total turning: {self.total_turning}")

    def rotate_right(self):
        directions = ['N', 'E', 'S', 'W', 'U', 'D']
        index = directions.index(self.current_direction)
        new_index = (index - 1) % len(directions)  # Adjusted index calculation for right rotation
        self.current_direction = directions[new_index]
        self.total_turning -= 60  # Decrement angle for right rotation
        print(f"Rotated right, new direction: {self.current_direction}, total turning: {self.total_turning}")

def solveMaze(self, maze: Maze3D, entrance: Coordinates3D):
    self.m_solved = False
    self.m_solution = []

    current_cell = entrance
    print("Entrance current_cell ", current_cell)

    self.solverPathAppend(current_cell, False)

    while current_cell not in maze.getExits():
        if not maze.hasWall(current_cell, current_cell + self.directions[self.current_direction]):
            next_cell = current_cell + self.directions[self.current_direction]
            self.solverPathAppend(next_cell, False)
            current_cell = next_cell
            print(f"Moved to: {current_cell}")
        else:
            # Wall detected, perform wall following
            while True:  # Infinite loop for wall following
                if not maze.hasWall(current_cell, current_cell + self.directions[self.current_direction]):
                    # Found an opening in the wall, break the loop
                    break
                self.rotate_left()  # Rotate left until no wall on the left side

            next_cell = current_cell + self.directions[self.current_direction]
            self.solverPathAppend(next_cell, False)
            current_cell = next_cell
            print(f"Moved to: {current_cell}")

    # Maze exit found
    self.solved(entrance, current_cell)
    print(f"Solved! Exit found at: {current_cell}")

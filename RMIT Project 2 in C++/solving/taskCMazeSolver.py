# -------------------------------------------------------------------
# PLEASE UPDATE THIS FILE.
# Task C solver.
#
# __author__ = 'Jeffrey Chan'
# __copyright__ = 'Copyright 2024, RMIT University'
# -------------------------------------------------------------------

from maze.maze3D import Maze3D
from solving.mazeSolver import MazeSolver
from maze.util import Coordinates3D
from collections import deque
from queue import PriorityQueue
from math import sqrt


# Code Implemented By SHIVANSH SINGHAL S3957577

def dfs_with_priority(maze, entrance):
    stack = deque()
    visited = set()
    exits_found = []  # List to store exits found during DFS

    stack.append(entrance)


    while stack:
        cell = stack.pop()
        if cell not in visited:
            visited.add(cell)

            # Explore neighbors
            for neighbor in maze.neighbours(cell):
                if neighbor not in visited:
                    # If neighbor is a boundary cell, prioritize it
                    if maze.isBoundary(neighbor) and neighbor not in maze.getExits():
                        stack.append(neighbor)
                    else:
                        # Push non-boundary cells to the end of the stack
                        stack.appendleft(neighbor)

                    # If we've reached a boundary cell that is an exit, add it to exits_found
                    if maze.isBoundary(neighbor) and neighbor in maze.getExits():
                        exits_found.append(neighbor)

    return exits_found

def heuristic(cell1, cell2):
    return sqrt((cell1.getRow() - cell2.getRow())**2 +
                (cell1.getCol() - cell2.getCol())**2 +
                (cell1.getLevel() - cell2.getLevel())**2)

def a_star_search(maze, start, goal):
    frontier = PriorityQueue()
    frontier.put((0, start))
    came_from = {}
    cost_so_far = {}
    came_from[start] = None
    cost_so_far[start] = 0

    while not frontier.empty():
        current_cost, current = frontier.get()

        if current == goal:
            break

        for next_cell in maze.neighbours(current):
            # Check if there's a wall between current and next_cell
            if not maze.hasWall(current, next_cell):
                new_cost = cost_so_far[current] + 1
                if next_cell not in cost_so_far or new_cost < cost_so_far[next_cell]:
                    cost_so_far[next_cell] = new_cost
                    priority = new_cost + heuristic(goal, next_cell)
                    frontier.put((priority, next_cell))
                    came_from[next_cell] = current

    path = []
    current = goal
    while current != start:
        path.append(current)
        current = came_from[current]
    path.append(start)
    path.reverse()
    return path


class TaskCMazeSolver(MazeSolver):
    """
    Task C solver implementation. You'll need to complete its implementation for task C.
    Recursive back-tracking solver (select next unvisited neighbour to visit, with a
    preference towards a certain direction)
    """

    def __init__(self):
        super().__init__()
        self.m_name = "taskC"

    def solveMaze(self, maze: Maze3D, entrance: Coordinates3D = None) :
        # we call the the solve maze call without the entrance.
        # DO NOT CHANGE THIS METHOD
        self.solveMazeTaskC(maze)

    # def solveMaze(self, maze: Maze3D, entrance: Coordinates3D = None):
    #     # Call the solveMazeTaskC method without the entrance.
    #     self.solveMazeTaskC(maze)

    def solveMazeTaskC(self, maze: Maze3D):
        # Use A* search to find the shortest pair of entrance and exit
        shortest_pair = self.find_shortest_pair(maze)
        if shortest_pair:
            print("Shortest pair of entrance and exit:", shortest_pair)

    def find_shortest_pair(self, maze):
        shortest_distance = float('inf')
        shortest_pair = None
        shortest_path = None
        for entrance in maze.getEntrances():
            exits_found = dfs_with_priority(maze, entrance)
            for exit_cell in exits_found:
                # Use A* search to find the shortest path
                path = a_star_search(maze, entrance, exit_cell)
                path_length = len(path)
                if path_length < shortest_distance:
                    shortest_distance = path_length
                    shortest_pair = (entrance, exit_cell)
                    shortest_path = path
        for i in shortest_pair:
            print(i)
        
        # Append startCoord to the path for the shortest path
        if shortest_path:
            for i in shortest_path:
                self.solverPathAppend(i, False)
        
        return shortest_pair

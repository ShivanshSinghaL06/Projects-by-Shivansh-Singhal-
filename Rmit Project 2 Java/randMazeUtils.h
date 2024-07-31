#include "Maze.h"

void CreateTerrainMaze(std::vector<std::vector<int>> heightMap, Maze& maze);
void CreateTerrainMazePaths(std::vector<int> pos,std::vector<std::vector<char>>* mazePoint, Maze& maze);

void CreateTerrainMaze(std::vector<std::vector<int>> heightMap, Maze& maze) {
    std::vector<std::vector<char>> mazeVector(maze.getXlen(), std::vector<char>(maze.getZlen()));
    int numBadNeighbours = 0;
    //Creates the basis for the maze by checking all squares
    //  if a square has a neighbour that has a height that
    //  differs by 2 or more, then that square is 'y'.
    //  All edges are 'z' and the rest are 'x'
    for (unsigned int i = 0; i < maze.getXlen(); ++i) {
        for (unsigned int j = 0; j < maze.getZlen(); ++j) {
            if(i != 0 && (heightMap[i][j] - heightMap[i - 1][j] > 1 || heightMap[i][j] - heightMap[i - 1][j] < -1)) {
                ++numBadNeighbours;
            }
            if(i + 1 != maze.getXlen() && (heightMap[i][j] - heightMap[i + 1][j] > 1 || heightMap[i][j] - heightMap[i + 1][j] < -1)) {
                ++numBadNeighbours;
            } 
            if(j != 0 && (heightMap[i][j] - heightMap[i][j - 1] > 1 || heightMap[i][j] - heightMap[i][j - 1] < -1)) {
                ++numBadNeighbours;
            }
            if(j + 1 != maze.getZlen() && (heightMap[i][j] - heightMap[i][j + 1] > 1 || heightMap[i][j] - heightMap[i][j + 1] < -1)) {
                ++numBadNeighbours;
            }

            if(i == 0 || i == maze.getXlen() - 1 || j == 0 || j == maze.getZlen() - 1) {
                mazeVector[i][j] = 'z';
            } else if(numBadNeighbours > 0) {
                mazeVector[i][j] = 'y';
            } else {
                mazeVector[i][j] = 'x';
            }

            numBadNeighbours = 0;
        }
    }

    //Determine Starting Position
    bool cantContinue = true;
    std::vector<int> pos(2);
    int Xlen = maze.getXlen();
    int Zlen = maze.getZlen();
    while (cantContinue) {
        std::srand(time(0));
        //Coin flips to decide if it will be on an x or
        //  z edge
        if (std::rand() % 2 == 0) {
            //Coin flips to decide on either +z or -z
            pos[0] = (std::rand() % (Xlen - 2) + 1);
            pos[1] = (std::rand() % 2) * (Zlen - 1);        
        } else {
            //Coin flips to decide on either +x or -x
            pos[0] = ((std::rand() % 2) * (Xlen - 1));
            pos[1] = (std::rand() % (Zlen - 2) + 1);
        }
        if(mazeVector[pos[0]][pos[1]] == 'z') {
            cantContinue = false;
            mazeVector[pos[0]][pos[1]] = '.';
        }
    }

    //Shift starting position from edge into maze to prevent
    //  potential out of bounds checking
    if(pos[0] == 0) {
        ++pos[0];
    } else if(pos[0] == Xlen - 1) {
        --pos[0];
    } else if(pos[1] == 0) {
        ++pos[1];
    } else {
        --pos[1];
    }

    mazeVector[pos[0]][pos[1]] = '.';

    std::vector<std::vector<char>>* mazePoint = &mazeVector;
    CreateTerrainMazePaths(pos, mazePoint, maze);

    //Has its own construction function since it works off slightly different rules to other mazes. Also doesn't save the maze for
    //  the same reason.
    mcpp::MinecraftConnection mc;
    for (unsigned int i = 0; i < maze.getXlen(); ++i) {
        for (unsigned int j = 0; j < maze.getZlen(); ++j) {
            if(mazeVector[i][j] == 'x') {
                for(int k = 1; k <= 3; ++k) {
                    mc.setBlock((*maze.getBasePoint()) + mcpp::Coordinate(i, mc.getHeight((*maze.getBasePoint()).x + i, (*maze.getBasePoint()).z + j) - (*maze.getBasePoint()).y + 1, j), mcpp::Blocks::ACACIA_WOOD_PLANK);
                }
            } else if (mazeVector[i][j] == 'z') {
                for(int k = 1; k <= 3; ++k) {
                    mc.setBlock((*maze.getBasePoint()) + mcpp::Coordinate(i, mc.getHeight((*maze.getBasePoint()).x + i, (*maze.getBasePoint()).z + j) - (*maze.getBasePoint()).y + 1, j), mcpp::Blocks::ACACIA_WOOD_PLANK);
                }
            } else if (mazeVector[i][j] == '.') {
                for(int k = 1; k <= 3; ++k) {
                    mc.setBlock((*maze.getBasePoint()) + mcpp::Coordinate(i, mc.getHeight((*maze.getBasePoint()).x + i, (*maze.getBasePoint()).z + j) - (*maze.getBasePoint()).y + 1, j), mcpp::Blocks::AIR);
                }
            } else {
                for(int k = 1; k <= 3; ++k) {
                    mc.setBlock((*maze.getBasePoint()) + mcpp::Coordinate(i, mc.getHeight((*maze.getBasePoint()).x + i, (*maze.getBasePoint()).z + j) - (*maze.getBasePoint()).y + 1, j), mcpp::Blocks::ACACIA_WOOD_PLANK);
                }
            }
        }
    }
}

void CreateTerrainMazePaths(std::vector<int> pos,std::vector<std::vector<char>>* mazePoint, Maze& maze) {
    // Vector for storing possible directions, 
    //  stops looping if all directions have been checked
    std::vector<std::vector<int>> dirVector{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    while(!dirVector.empty()) {
        int randNum = rand() % dirVector.size();
        //Creates a new position in the direction of
        // the randomly decided direction
        std::vector<int> nextPos{dirVector[randNum][0] + pos[0], dirVector[randNum][1] + pos[1]};

        //Multiple conditions must be met for it to be a viable square
        // - must be an 'x' (eliminating y's and edges)
        // - all neighbouring values must not be a '.' (besides the incoming one)
        if((*mazePoint)[nextPos[0]][nextPos[1]] == 'x') {
            if((*mazePoint)[dirVector[randNum][0] + nextPos[0]][ dirVector[randNum][1] + nextPos[1]] != '.') {
                if(dirVector[randNum][0] == 0) {
                    if((*mazePoint)[nextPos[0] + 1][nextPos[1]] != '.' && (*mazePoint)[nextPos[0] - 1][nextPos[1]] != '.') {
                        (*mazePoint)[nextPos[0]][nextPos[1]] = '.';                            
                        CreateTerrainMazePaths(nextPos, mazePoint, maze);
                    }
                } else {
                    if((*mazePoint)[nextPos[0]][nextPos[1] + 1] != '.' && (*mazePoint)[nextPos[0]][nextPos[1] - 1] != '.') {
                        (*mazePoint)[nextPos[0]][nextPos[1]] = '.';
                        CreateTerrainMazePaths(nextPos, mazePoint, maze);
                    }
                }
            }
        }
        //Erases that direction from the vector so that it
        //  cannot be chosen again
        dirVector.erase(dirVector.begin() + randNum);
    }
}
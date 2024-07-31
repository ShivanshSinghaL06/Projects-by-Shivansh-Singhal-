#include "Maze.h"

constexpr long int max_size = std::numeric_limits<std::streamsize>::max();

enum States{
    ST_Main,
    ST_GetMaze,
    ST_SolveMaze,
    ST_Creators,
    ST_Exit
};

void ReadMazeSize(unsigned int&, unsigned int&, States&);
void ReadBasePoint(mcpp::Coordinate**, States&);
void ReadMazeInput(Maze&, States&);
bool ResetIntegerInput(States&);
int find_pos(std::vector<std::vector<int>> bigVect, std::vector<int> posVect);
void CreateRandomMaze(Maze& inputMaze, States& curState);
void CreateMazePaths(std::vector<int> pos, std::vector<std::vector<int>>* spotsLeft, std::vector<std::vector<char>>* maze, Maze& inputMaze);



void ReadMazeSize(unsigned int& mazeLength, unsigned int& mazeWidth, States& curState) {
    std::cout << "Enter the length and width of maze (1 - 256):" << std::endl;
    bool gettingInput = true;
    int tempLength, tempWidth;
    while (gettingInput) {
        std::cin >> tempLength;
        
        std::cin >> tempWidth;
        
        if (std::cin.good() && tempLength > 0 && tempLength < 257 && tempWidth > 0 && tempWidth < 257 && tempLength % 2 != 0 && tempWidth % 2 != 0) {
            gettingInput = false;
            mazeLength = tempLength;
            mazeWidth = tempWidth;
        }
        else {
            std::cout << "Sorry that wasn't correct please enter the length and width of maze again (1 - 256):" << std::endl;
            gettingInput = ResetIntegerInput(curState);
        }
    }
}

void ReadBasePoint(mcpp::Coordinate** mazeStart, States& curState) {
    mcpp::MinecraftConnection mc;
    std::cout << "Enter the basePoint of maze (x, y, z):" << std::endl;
    int x_input, y_input, z_input;
    bool gettingInput = ResetIntegerInput(curState);
    while (gettingInput) {
        std::cin >> x_input >> y_input >> z_input;
        if (std::cin.good()) {
            gettingInput = false;
        }
        else {
            gettingInput = ResetIntegerInput(curState);
            std::cout << "Sorry that wasn't valid, please enter the basePoint of maze again (x, y, z):" << std::endl;
        }
        
    }
    if (ResetIntegerInput(curState)) {
        y_input = mc.getHeight(x_input, z_input) + 1;
        *mazeStart = new mcpp::Coordinate(x_input, y_input, z_input);
    }
}

void ReadMazeInput(Maze& inputMaze, States& curState) {
    std::vector<std::vector<char>> mazeStructure;
    char input_char;
    std::cout << "Please enter the maze shape: " << std::endl;
    if (ResetIntegerInput(curState)) {
        for (unsigned int row = 0; row < inputMaze.getXlen(); row++) {
            std::vector<char> curVec;
            for (unsigned int col = 0; col < inputMaze.getZlen(); col++) {
                if (std::cin.good() && ResetIntegerInput(curState)) {
                    std::cin >> input_char;
                    curVec.push_back(input_char);
                }
                else {
                    col = inputMaze.getZlen();
                    row = inputMaze.getXlen();
                }
            }
            if (!std::cin.good()) {
                ResetIntegerInput(curState);
                row--;
                std::cout << "Please enter that row again" << std::endl;
            }
            else {
                mazeStructure.push_back(curVec);
            }
        }
        inputMaze.setMazeStructure(mazeStructure);
    }
}

bool ResetIntegerInput(States& curState) {
    bool returnVal = true;
    if (std::cin.eof()==1) {
        curState = ST_Exit;
        returnVal = false;
    }
    else if (std::cin.fail()) {
        std::cin.clear();
        std::cin.ignore(max_size, '\n');
    } 
    return returnVal; 
}

int find_pos(std::vector<std::vector<int>> bigVect, std::vector<int> posVect) {
    //Finds the position of a vector within another vector - used to find the position of 'pos' or 'nextPos' within 'spotsLeft'
    int pos = -1;
    for (unsigned int i = 0; i < bigVect.size(); ++i) {
        if (bigVect[i] == posVect) {
            pos = i;
        }
    }
    return pos;
}

void CreateRandomMaze(Maze& inputMaze, States& curState) {
    // Creates a basic environment full of 'x' characters
    std::vector<std::vector<char>> mazeVector(inputMaze.getXlen(), std::vector<char>(inputMaze.getZlen()));
    for (unsigned int i = 0; i < inputMaze.getXlen(); ++i) {
        for (unsigned int j = 0; j < inputMaze.getZlen(); ++j) {
            mazeVector[i][j] = 'x';
        }
    }

    // Punches a bunch of "holes" into the maze, storing each location into 'blankSpots'
    std::vector<std::vector<int>> blankSpots;
    for (unsigned int i = 1; i < inputMaze.getXlen() - 1; i += 2) {
        for (unsigned int j = 1; j < inputMaze.getZlen() - 1; j += 2) {
            std::vector<int> loc(2);
            mazeVector.at(i).at(j) = '.';
            loc[0] = i;
            loc[1] = j;
            blankSpots.push_back(loc);
        }
    }

    // Determine starting position of the maze
    std::srand(time(0));
    std::vector<int> pos(2);
    int Xlen = inputMaze.getXlen();
    int Zlen = inputMaze.getZlen();
    if(inputMaze.getMode() == 0) {
        //Coin flips to decide if it will be on an x or z edge
        if (std::rand() % 2 == 0) {
            //Coin flips to decide on either +z or -z
            pos[0] = (((std::rand() % (Xlen / 2)) + 1) * 2 - 1);
            pos[1] = ((std::rand() % 2) * (Zlen - 1));
            mazeVector[pos[0]][pos[1]] = '.';
            // Checks to see which side the starting position is on, moving it towards the centre
            pos[1] = (pos[1] < Zlen / 2) ? 1 : Zlen - 2;
        
        } else {
            //Coin flips to decide on either +x or -x
            pos[0] = ((std::rand() % 2) * (Xlen - 1));
            pos[1] = (((std::rand() % (Zlen / 2)) + 1) * 2 - 1);
            mazeVector[pos[0]][pos[1]] = '.';
            // Checks to see which side the starting position is on, moving it towards the centre
            pos[0] = (pos[0] < Xlen / 2) ? 1 : Xlen - 2;
        }
    } else {
        mazeVector[1][0] = '.';
        pos[0] = 1;
        pos[1] = 1;
    }
    
    int posOf = find_pos(blankSpots, pos);
    blankSpots.erase(blankSpots.begin() + posOf);

    // Create maze Paths
    std::vector<std::vector<char>>* mazePoint = &mazeVector;
    std::vector<std::vector<int>>* blankPoint = &blankSpots;
    CreateMazePaths(pos, blankPoint, mazePoint, inputMaze);

    // Set inputMaze's structure to be equal to the recently created structure
    inputMaze.setMazeStructure(mazeVector);
}

void CreateMazePaths(std::vector<int> pos, std::vector<std::vector<int>>* spotsLeft, std::vector<std::vector<char>>* maze, Maze& inputMaze) {
    // Direction vector to store the different directions - uses '2' instead of '1' since that is the distance between each blank spot
    std::vector<std::vector<int>> dirVector{{-2, 0}, {0, 2}, {2, 0}, {0, -2}};
    int Xlen = inputMaze.getXlen();
    int Zlen = inputMaze.getZlen();

    while (!dirVector.empty() && !(*spotsLeft).empty()) {
        int randNum = 0;
        if(inputMaze.getMode() == 0) {
            randNum = rand() % dirVector.size();
        }
        std::vector<int> nextPos{dirVector[randNum][0] + pos[0], dirVector[randNum][1] + pos[1]};
        int indexOf = find_pos(*spotsLeft, nextPos);
        //Checks for the following:
        // - is NextPos within the bounds of the Vector
        // - has NextPos been visited before
        if(nextPos[0] > -1 && nextPos[1] > -1 && nextPos[0] < Xlen && nextPos[1] < Zlen && indexOf != -1) {
            //If true, set the 'x' in between the two spots to a '.', then loop again from NextPos
            (*maze)[pos[0] + (dirVector[randNum][0] / 2)][pos[1] + (dirVector[randNum][1] / 2)] = '.';
            (*spotsLeft).erase((*spotsLeft).begin() + indexOf);
            CreateMazePaths(nextPos, spotsLeft, maze, inputMaze);
        }

        dirVector.erase(dirVector.begin() + randNum);
    }
}
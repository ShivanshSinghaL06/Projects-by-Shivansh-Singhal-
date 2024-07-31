#ifndef ASSIGN_MAZE_H
#define ASSIGN_MAZE_H

#include <vector>
#include <random>
#include <chrono>
#include <thread>
#include <mcpp/mcpp.h>

class Maze
{

public:
    Maze(mcpp::Coordinate* basePoint, unsigned int xlen, unsigned int zlen, bool mode);
    ~Maze();

    // maze class methods
    unsigned int getXlen(void);
    unsigned int getZlen(void);
    bool getMode(void);

    void setXlen(unsigned int xlen);
    void setZlen(unsigned int zlen);

    mcpp::Coordinate* getBasePoint(void);
    void setBasePoint(mcpp::Coordinate* basePoint);


    // potentially change mazeStructure to vector if implementation of 2D char array becomes tedious
    void setMazeStructure(std::vector<std::vector<char>>);
    std::vector<std::vector<char>> getMazeStructure(void);

    void PrintMaze(void);

    void getOriginalLandscape(void);
    void setOriginalLandscape(void);
    void Flattening(void);
    void reduce(mcpp::Coordinate* currPoint, int baseheight);
    void increase(mcpp::Coordinate* currPoint, int baseheight, mcpp::BlockType baseBlockType);
    bool mazeExistsInMc();
    void BuildMazeInMc(void);
    void DestroyMazeInMc(void);
    void PlacePlayerInRandomLocation();

private:
    /* data */
    unsigned int xlen;
    unsigned int zlen;
    unsigned int prevXLen;
    unsigned int prevZLen;
    int minHeight;

    mcpp::Coordinate* basePoint;
    mcpp::Coordinate* previousBasePoint;
    mcpp::MinecraftConnection mc;
    std::vector<std::vector<char>> mazeStructure;
    std::vector<std::vector<std::vector<mcpp::BlockType>>> orginalLandscape;
    std::vector<mcpp::Coordinate> FindEmpty();
    
    bool mode;
};

#endif //ASSIGN_MAZE_H

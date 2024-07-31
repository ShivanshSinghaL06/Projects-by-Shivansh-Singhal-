#ifndef ASSIGN3_AGENT_H
#define ASSIGN3_AGENT_H

#include <iostream>
#include <mcpp/mcpp.h>
#include <cmath>
#define MOVE_XPLUS mcpp::Coordinate(1,0,0)
#define MOVE_XMINUS mcpp::Coordinate(-1,0,0)
#define MOVE_ZPLUS mcpp::Coordinate(0,0,1)
#define MOVE_ZMINUS mcpp::Coordinate(0,0,-1)

enum solveAlgorithm{
        RIGHT_HAND_FOLLOW,
        BREATH_FIRST_SEARCH,
};

enum AgentOrientation{
    X_PLUS,
    Z_PLUS,
    X_MINUS,
    Z_MINUS
};

enum AgentDirection{
    west, north , south , east
};

class Agent
{

public:
    Agent();
    Agent(mcpp::Coordinate location);
    ~Agent();
    bool insidemaze(mcpp::Coordinate coord);
    void showEscapeRoute();
    void shortestPath();    
    mcpp::Coordinate getLocation();
    // Added by Shivansh for enhancement solve 
    //provides the next possible moves in form of an vector when position is input
    
    
private:
    /* data */
    mcpp::Coordinate location;
    mcpp::MinecraftConnection mc;
    std::vector<mcpp::Coordinate> path;
    AgentDirection direction;
    bool iswall(mcpp::Coordinate prev, mcpp::Coordinate next);
    bool checkFourWalls(mcpp::Coordinate); 
    void setDirection(AgentDirection newdirection) ;
    std::vector<std::vector<std::vector<mcpp::BlockType>>> blocksAroundCoord;
    std::array<mcpp::Coordinate, 4> DIR_OFFSETS{mcpp::Coordinate(0,0,-1),mcpp::Coordinate(1,0,0),mcpp::Coordinate(0,0,1),mcpp::Coordinate(-1,0,0)};
    std::vector<mcpp::Coordinate> givenextcords(const mcpp::Coordinate& currentpos);
};



#endif //ASSIGN3_AGENT_H
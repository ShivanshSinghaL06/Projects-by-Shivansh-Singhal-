#include "Agent.h"
#include <chrono>
#include <thread>
#include <queue>
#include <set>
#include <map>
#include <algorithm>    // std::reverse
#include <vector>

Agent::Agent()
{
    //if no location is given then location is set to player position
    this->location = this->mc.getPlayerPosition();
}

Agent::Agent(mcpp::Coordinate location)
{
    this->location = location;
}

Agent::~Agent()
{
}

mcpp::Coordinate Agent::getLocation() {
    return this->location;
}

bool Agent::iswall(mcpp::Coordinate prev, mcpp::Coordinate next){
    bool valid = false;
    //check if the next location is reachable
    int newheight = this->mc.getHeight(next.x, next.z);
    int heightDiff = (newheight) - (prev.y);
    if(std::abs(heightDiff) > 1){
        valid = true;
    }

    return valid;
}// TO DETECT THE NEXT BLOCK IS WALL OR NOT 

bool Agent::checkFourWalls(mcpp::Coordinate coord) {
    std::vector<std::vector<int>> surroundingHeights = this->mc.getHeights(coord+ MOVE_XMINUS + MOVE_ZMINUS, coord + MOVE_XPLUS + MOVE_ZPLUS);
    return (std::abs(surroundingHeights.at(0).at(1) - surroundingHeights.at(1).at(1))  > 1 ||
            std::abs(surroundingHeights.at(1).at(0) - surroundingHeights.at(1).at(1))  > 1 ||
            std::abs(surroundingHeights.at(1).at(2) - surroundingHeights.at(1).at(1))  > 1 ||
            std::abs(surroundingHeights.at(2).at(1) - surroundingHeights.at(1).at(1))  > 1);
}

bool Agent::insidemaze(mcpp::Coordinate coord){
    // get block vec in orientation y, x, z
    std::vector<std::vector<std::vector<mcpp::BlockType>>> blocksAroundCoord = this->mc.getBlocks(coord + MOVE_XMINUS + MOVE_ZMINUS, coord + MOVE_XPLUS + MOVE_ZPLUS);
    return (this->checkFourWalls(this->location) &&  
        ((blocksAroundCoord.at(0).at(0).at(1) == mcpp::Blocks::ACACIA_WOOD_PLANK) || 
         (blocksAroundCoord.at(0).at(1).at(0) == mcpp::Blocks::ACACIA_WOOD_PLANK) || 
         (blocksAroundCoord.at(0).at(1).at(2) == mcpp::Blocks::ACACIA_WOOD_PLANK) || 
         (blocksAroundCoord.at(0).at(2).at(1) == mcpp::Blocks::ACACIA_WOOD_PLANK)));
}

void Agent::setDirection(AgentDirection newdirection) {
    this->direction = newdirection;
    this->path.push_back(this->location);
    this->mc.setBlock(this->location, mcpp::Blocks::LIME_CARPET);
    std::this_thread::sleep_for(std::chrono::milliseconds(1000));
    this->mc.setBlock(this->location, mcpp::Blocks::AIR);
}

void Agent::showEscapeRoute() {
    this->direction = east; // Assuming  right hand is facing the positive x-axis this->direction
    int count = 0;
    while (this->insidemaze(this->location)) {
        if (count == 0) {
            std::cout << "Inside -> 2) Show Escape Route" << std::endl;
            std::cout << "Showing escape route..." << std::endl;
        }
        if (this->direction == east){
            if(!this->iswall(this->location,this->location + MOVE_XPLUS) && this->iswall(this->location,this->location + MOVE_ZPLUS)){
                this->location = this->location + MOVE_XPLUS;
                setDirection(east);
            }
            else if( !this->iswall(this->location, this->location + MOVE_ZPLUS)){
                this->location = this->location + MOVE_ZPLUS;
                setDirection(south);
                
            }
            else if(this->iswall(this->location, this->location + MOVE_XPLUS) && this->iswall(this->location, this->location + MOVE_ZPLUS) && !this->iswall(this->location, this->location + MOVE_ZMINUS)){
                this->location = this->location + MOVE_ZMINUS;
                setDirection(north);
                
            }
            else if(this->iswall(this->location, this->location + MOVE_XPLUS) && this->iswall(this->location, this->location + MOVE_ZPLUS) && this->iswall(this->location, this->location + MOVE_ZMINUS) && !this->iswall(this->location, this->location + MOVE_XMINUS)){
                this->location = this->location + MOVE_XMINUS;
                setDirection(west);
            }

        }
        else if(this->direction == south){
            if(!this->iswall(this->location, this->location + MOVE_ZPLUS) && this->iswall(this->location, this->location + MOVE_XMINUS)){
                this->location = this->location + MOVE_ZPLUS;
                setDirection(south);
            }
            else if( !this->iswall(this->location, this->location + MOVE_XMINUS)){
                this->location = this->location + MOVE_XMINUS;
                setDirection(west);
                 
            }
            else if(this->iswall(this->location, this->location + MOVE_ZPLUS) && this->iswall(this->location, this->location + MOVE_XMINUS) && !this->iswall(this->location, this->location + MOVE_XPLUS)){
                this->location = this->location + MOVE_XPLUS;
                setDirection(east);
                
            }
            else if(this->iswall(this->location, this->location + MOVE_ZPLUS) && this->iswall(this->location, this->location + MOVE_XMINUS) && this->iswall(this->location, this->location + MOVE_XPLUS) && !this->iswall(this->location, this->location + MOVE_ZMINUS)){
                this->location = this->location + MOVE_ZMINUS;
                setDirection(north);

            }

        }
        else if(this->direction == north){
            if(!this->iswall(this->location, this->location + MOVE_ZMINUS) && this->iswall(this->location, this->location + MOVE_XPLUS)){
                this->location = this->location + MOVE_ZMINUS;
                setDirection(north);
            }
            else if( !this->iswall(this->location, this->location + MOVE_XPLUS)){
                this->location = this->location + MOVE_XPLUS;
                setDirection(east);
            }
            else if(this->iswall(this->location, this->location + MOVE_ZMINUS) && this->iswall(this->location, this->location + MOVE_XPLUS) && !this->iswall(this->location, this->location + MOVE_XMINUS)){
                this->location = this->location + MOVE_XMINUS;
                setDirection(west);                
            }
            else if(this->iswall(this->location, this->location + MOVE_ZMINUS) && this->iswall(this->location, this->location + MOVE_XPLUS) && this->iswall(this->location, this->location + MOVE_XMINUS) && !this->iswall(this->location, this->location + MOVE_ZPLUS)){
                this->location = this->location + MOVE_ZPLUS;
                setDirection(south);
            }

        }
        else if(this->direction == west){
            if(!this->iswall(this->location, this->location + MOVE_XMINUS) && this->iswall(this->location, this->location + MOVE_ZMINUS)){
                this->location = this->location + MOVE_XMINUS;
                setDirection(west);      
            }
            else if( !this->iswall(this->location, this->location + MOVE_ZMINUS)){
                this->location = this->location + MOVE_ZMINUS;
                setDirection(north);
            }
            else if(this->iswall(this->location, this->location + MOVE_XMINUS) && this->iswall(this->location, this->location + MOVE_ZMINUS) && !this->iswall(this->location, this->location + MOVE_ZPLUS)){
                this->location = this->location + MOVE_ZPLUS;
                setDirection(south);
            }
            else if(this->iswall(this->location, this->location + MOVE_XMINUS) && this->iswall(this->location, this->location + MOVE_ZMINUS) && this->iswall(this->location, this->location + MOVE_ZPLUS) && !this->iswall(this->location, this->location + MOVE_XPLUS)){
                this->location = this->location + MOVE_XPLUS;
                setDirection(east);
            }
            else if(!this->iswall(this->location, this->location + MOVE_XMINUS) && !this->iswall(this->location, this->location + MOVE_ZMINUS) && !this->iswall(this->location, this->location + MOVE_ZPLUS) && !this->iswall(this->location, this->location + MOVE_XPLUS)){
                this->location = this->location + MOVE_ZMINUS;
                setDirection(north);
            }
        }
        else{
            if (!path.empty()) {
                this->location = path.back();
                path.pop_back();
            } else {
                // This means we have reached a dead-end, and there's no way out
                std::cout << "Unable to find an escape route." << std::endl;
                
            }

        }

    
    std::cout << "Step[" << count + 1 << "]: (" << path[count].x << ", " << path[count].y << ", " << path[count].z << ")\n";//Printing the path to Terinal 
    count += 1;
    }
    if (count == 0) {
        std::cout<<"Player not inside the maze!! SOLVE MANUALLY first"<<std::endl;
        this->mc.postToChat("Player not inside the maze!! SOLVE MANUALLY first");
    }
    else {
        std::cout << "Escape route displayed in Minecraft and path coordinates printed to the terminal." << std::endl;
    }
}

std::vector<mcpp::Coordinate> Agent::givenextcords(const mcpp::Coordinate& currentpos){
    std::vector<mcpp::Coordinate> possible_cords;
    for (mcpp::Coordinate& offset : this->DIR_OFFSETS){
        mcpp::Coordinate curr_offset = currentpos + offset;
        if (!this->iswall(currentpos, curr_offset)){
            possible_cords.push_back(curr_offset);
        }
    }
    
    return possible_cords;

}

struct CoordinateCompare {
    bool operator()(const mcpp::Coordinate& a, const mcpp::Coordinate& b) const {
        bool returnVal;
        if (a.x != b.x) {
            returnVal = a.x < b.x;
        }
        else if (a.y != b.y) {
            returnVal = a.y < b.y;
        }
        else {
            returnVal = a.z < b.z;
        }
        return returnVal;
    }
};

void Agent::shortestPath() {
    mcpp::Coordinate playerpos = this->mc.getPlayerPosition();
    mcpp::Coordinate exitCoord;  
    bool exitFound = false;     

    std::queue<mcpp::Coordinate> coord_queue;
    coord_queue.push(playerpos);

    std::map<mcpp::Coordinate, mcpp::Coordinate, CoordinateCompare> previous; 
    Agent agent(playerpos);

    if(!this->insidemaze(playerpos)){
        std::cout<<"Not inside maze !!"<<std::endl;
        return ;
        
    }
    while (!coord_queue.empty() && !exitFound) {
        mcpp::Coordinate currentPos = coord_queue.front();
        coord_queue.pop();

        
        std::vector<mcpp::Coordinate> nextCoords = agent.givenextcords(currentPos);
        

        for (const mcpp::Coordinate& coord : nextCoords) {
          
            if (previous.find(coord) == previous.end() && agent.insidemaze(coord)) {
              
                coord_queue.push(coord);
                previous[coord] = currentPos; 
            } else if (!agent.insidemaze(coord) && !exitFound) {
                exitCoord = coord;
                exitFound = true;
                previous[coord] = currentPos; 
            }
        }

        agent.location = currentPos;
    }

    if (exitFound) {
        std::vector<mcpp::Coordinate> shortestPath;
        mcpp::Coordinate current = exitCoord;
        int count = 1;
        while (!(current == playerpos)) {
            shortestPath.push_back(current);
            current = previous[current];
        }

        shortestPath.push_back(playerpos);
        std::reverse(shortestPath.begin(), shortestPath.end());

       
        for (const mcpp::Coordinate& coord : shortestPath) {
            this->mc.setBlock(coord, mcpp::Blocks::LIME_CARPET);
            std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            this->mc.setBlock(coord, mcpp::Blocks::AIR);
            std::cout << "Step[" <<count  << "]: " << coord << "\n";
            count = count + 1;
        }
        std::cout << "Exit" << std::endl;
    } else {
        std::cout << "No exit found." << std::endl;
    }
}   

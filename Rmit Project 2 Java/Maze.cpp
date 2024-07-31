#include "Maze.h"

Maze::Maze(mcpp::Coordinate *basePoint, unsigned int xlen, unsigned int zlen, bool mode)
{
  this->basePoint = basePoint;
  this->previousBasePoint = nullptr;
  this->xlen = xlen;
  this->zlen = zlen;
  this->mode = mode;
}

Maze::~Maze()
{
  // implement destructor
  if (this->previousBasePoint != nullptr) {
    delete this->previousBasePoint;
  }

  if (this->basePoint != nullptr) {
    delete this->basePoint;
  }
}

unsigned int Maze::getXlen(void)
{
  return this->xlen;
}

unsigned int Maze::getZlen(void)
{
  return this->zlen;
}

bool Maze::getMode(void) 
{
  return this->mode;
}

void Maze::setXlen(unsigned int xlen)
{
  this->prevXLen = this->xlen;
  this->xlen = xlen;
}

void Maze::setZlen(unsigned int zlen)
{
  this->prevZLen = this->zlen;
  this->zlen = zlen;
}

mcpp::Coordinate *Maze::getBasePoint(void)
{
  return this->basePoint;
}

void Maze::setBasePoint(mcpp::Coordinate *basePoint)
{
  //check for existing basepoint, set previous basepoint if basepoint already exists (used to destroy maze when secondary maze is built)
  if (this->basePoint != nullptr) {
    if (this->previousBasePoint != nullptr) {
      delete this->previousBasePoint;
    }
    this->previousBasePoint = new mcpp::Coordinate(*this->basePoint);
  }
  this->basePoint = basePoint;
}


void Maze::setMazeStructure(std::vector<std::vector<char>> mazeStructure)
{
  this->mazeStructure = mazeStructure;
}

std::vector<std::vector<char>> Maze::getMazeStructure(void)
{
  return this->mazeStructure;
}

void Maze::PrintMaze(void)
{

  for (unsigned int i = 0; i < this->mazeStructure.size(); i++)
  {
    for (unsigned int j = 0; j < this->mazeStructure.at(i).size(); j++)
    {
      std::cout << this->mazeStructure.at(i).at(j);
    }
    std::cout << std::endl;
  }
}

bool Maze::mazeExistsInMc(){
  //returns true if a maze has been built in mc, false otherwise
  return this->orginalLandscape.size() > 0;
}

// // Land Flattening - Shivansh and Ben

void Maze::reduce(mcpp::Coordinate* currPoint, int baseheight) {
  // used in flattening to reduce blocks higher than basepoint to bp height (replace with air)
  while (currPoint->y > baseheight) {
    this->mc.setBlock(*currPoint, 0);
    currPoint->y = currPoint->y - 1;
  }
}

void Maze::increase(mcpp::Coordinate* currPoint, int baseheight, mcpp::BlockType baseBlockType){
  // used in flattening to build blocks up to the bp height, uses the blocktype of the bp to build
  while (currPoint->y < baseheight) {
    currPoint->y = currPoint->y + 1;
    this->mc.setBlock(*currPoint, baseBlockType);
  }
}

void Maze::getOriginalLandscape() {
  // Function gets a 3D vector of blocks from the maze area prior to flattening
  mcpp::Coordinate* bottomCorner = new mcpp::Coordinate(this->basePoint->x, this->basePoint->y, this->basePoint->z);
  mcpp::Coordinate* topCorner = new mcpp::Coordinate(this->basePoint->x + this->xlen, this->basePoint->y, this->basePoint->z + this->zlen);
  // getting the lowest height and heighest height within the maze area (creates bounds for cuboid)
  std::vector<std::vector<int>> mazeHeights = this->mc.getHeights(*bottomCorner, *topCorner);
  int minHeight = mazeHeights.at(0).at(0);
  int maxHeight = minHeight;
  for (unsigned int i = 0; i < mazeHeights.size(); i++) {
    for (unsigned int j = 0; j < mazeHeights.at(i).size(); j++) {
      if (mazeHeights.at(i).at(j) > maxHeight) {
        maxHeight = mazeHeights.at(i).at(j);
      }
      else if (mazeHeights.at(i).at(j) < minHeight) {
        minHeight = mazeHeights.at(i).at(j);
      }
    }
  }
  //respective values are assigned before getting all blocks in 3D space, private vector originallandscape is set to the value to be used later
  bottomCorner->y = minHeight;
  topCorner->y = maxHeight;
  this->orginalLandscape = this->mc.getBlocks(*bottomCorner, *topCorner);
  // min height saved for later use in setOriginalLandscape
  this->minHeight = minHeight;
  delete bottomCorner;
  delete topCorner;
}

void Maze::setOriginalLandscape() {
  // uses the landscape gathered prior to build maze and sets all blocks in the maze area back to its original
  mcpp::Coordinate* currCoord = new mcpp::Coordinate(this->previousBasePoint->x, this->minHeight, this->previousBasePoint->z);
  for (unsigned int i = 0; i < this->orginalLandscape.size(); i++) {
    currCoord->y = this->minHeight + i;
    for (unsigned int j = 0; j < this->orginalLandscape.at(i).size(); j++) {
      currCoord->x = this->previousBasePoint->x + j;
      for (unsigned int k = 0; k < this->orginalLandscape.at(i).at(j).size(); k++) {
        currCoord->z = this->previousBasePoint->z + k;
        this->mc.setBlock(*currCoord, this->orginalLandscape.at(i).at(j).at(k));
      }
    }
  }
  delete currCoord;
}

void Maze::Flattening() {
    this->getOriginalLandscape();
    int baseheight = this->basePoint->y - 1;
    //get blocktype of basepoint
    mcpp::BlockType baseBlockType = this->mc.getBlock(mcpp::Coordinate(this->basePoint->x, this->basePoint->y -1, this->basePoint->z));
    // diagonal is the opposite corner of maze square from basepoint
    mcpp::Coordinate* diagonal = new mcpp::Coordinate(this->basePoint->x + this->xlen, this->basePoint->y, this->basePoint->z + this->zlen);
    // Vector with all heights of terrain prior to flattening
    std::vector<std::vector<int>> mazeHeights = this->mc.getHeights(*this->basePoint, *diagonal);
    // for each height in mazeheights, check if higher or lower than bp and call respective reduce/increase function
    for (unsigned int i = 0; i < this->xlen; ++i) {
        diagonal->x = this->basePoint->x + i;
        for (unsigned int j = 0; j < this->zlen; ++j) {
            diagonal->z = this->basePoint->z + j;
            diagonal->y = mazeHeights.at(i).at(j);
            if (diagonal->y > baseheight) {
              reduce(diagonal, baseheight);
            }
            else if (diagonal->y < baseheight) {
              increase(diagonal, baseheight, baseBlockType);
            }
        }
    }
    delete diagonal;
}

void Maze::BuildMazeInMc(void) {
    // set player to 10 blocks above bp
    this->mc.setPlayerPosition(*(this->basePoint) + mcpp::Coordinate(0, 10, 0));
    // go through mazestructure and place wall at coords marked with x
    for(unsigned int length = 0; length < this->xlen; length++){
        for(unsigned int width = 0; width < this->zlen; width++){
            if((this->getMazeStructure()).at(length).at(width) == 'x') {
                this->mc.setBlock(*(this->basePoint) + mcpp::Coordinate(length, 0, width), mcpp::Blocks::ACACIA_WOOD_PLANK);
                this->mc.setBlock(*(this->basePoint) + mcpp::Coordinate(length, 1, width), mcpp::Blocks::ACACIA_WOOD_PLANK);
                this->mc.setBlock(*(this->basePoint) + mcpp::Coordinate(length, 2, width), mcpp::Blocks::ACACIA_WOOD_PLANK);
                std::this_thread::sleep_for(std::chrono::milliseconds(1000));
            }
        }
    }
}

void Maze::DestroyMazeInMc(void) {
    // go through maze area and set all blocks to air
    for(unsigned int length = 0; length < this->prevXLen; length++){
        for(unsigned int width = 0; width < this->prevZLen; width++){
          this->mc.setBlock(*(this->previousBasePoint) + mcpp::Coordinate(length, 0, width), mcpp::Blocks::AIR);
          this->mc.setBlock(*(this->previousBasePoint) + mcpp::Coordinate(length, 1, width), mcpp::Blocks::AIR);
          this->mc.setBlock(*(this->previousBasePoint) + mcpp::Coordinate(length, 2, width), mcpp::Blocks::AIR);
        }
    }
}

// // Solve maze manually 

std::vector<mcpp::Coordinate> Maze::FindEmpty() {
    std::vector<mcpp::Coordinate> emptyCells;
    
    for (unsigned int x = 0; x < this->getXlen(); x++) {
        for (unsigned int z = 0; z < this->getZlen(); z++) {
            if (this->mazeStructure[z][x] != 'x') {
                mcpp::Coordinate cellCoord = *(this->getBasePoint()) + mcpp::Coordinate(x, 0, z);
                emptyCells.push_back(cellCoord);
            }
        }
    }

    return emptyCells;
}

void Maze::PlacePlayerInRandomLocation() {
    std::cout << "Inside -> 1) Solve Manually" << std::endl;
    if(this->getMode() == 0) {
        std::vector<mcpp::Coordinate> emptyCells = this->FindEmpty();
        std::random_device rd;  
        std::uniform_int_distribution<int> dist(0, emptyCells.size() - 1);
        int randomIndex = dist(rd);

        this->mc.setPlayerPosition(emptyCells[randomIndex]);
    } else {
        mcpp::Coordinate bottomRight = *(this->getBasePoint()) + mcpp::Coordinate(this->getXlen() - 1, 0, this->getZlen() - 1);
        this->mc.setPlayerPosition(bottomRight);
    }
}

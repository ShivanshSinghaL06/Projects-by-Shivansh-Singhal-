
#include <random>

#include "menuUtils.h"
#include "mazeGenUtils.h"
#include "randMazeUtils.h"
#include "Maze.h"
#include "Agent.h"

#define NORMAL_MODE 0
#define TESTING_MODE 1

#define MAX_ITER 1000

int main(int argc, char *argv[]){
    //initialise all data 
    bool mode = NORMAL_MODE;
    mcpp::Coordinate* basePoint = nullptr;
    unsigned int xlen = 0, zlen = 0;
    Maze inputMaze(basePoint, xlen, zlen, mode);
    mcpp::MinecraftConnection mc; 
    States curState = ST_Main;

    mc.doCommand("time set day");
    if(argc == 2 && std::string(argv[1]) == "-T") {
        mode = TESTING_MODE;
        std::cout << "--- TESTING MODE ---\n";
    }
    else if (argc == 2 && std::string(argv[1]) != "-ES" && std::string(argv[1]) != "-EB") {
        std::cout << "Sorry that wasn't a valid flag, valid flags are:\n\t-T for testing mode\n\t-ES for enhanced solving\n\t-EB for enhanced building" << std::endl;
        curState = ST_Exit;
    }
    else {
        printStartText();
    }
        while (curState != ST_Exit)
        {
            printMainMenu();
            // Get Main menu input default to exit if bad value is given
            int main_menu_selection = 5;
            std::cin >> main_menu_selection;      
            // go to generate maze menu
            if(main_menu_selection == 1){
                curState = ST_GetMaze;
                while(curState == ST_GetMaze){

                    printGenerateMazeMenu();
                    int menu_one_selection = 3;
                    std::cin >> menu_one_selection;
                    if (menu_one_selection == 1 ) {
                        std::cout << "Inside -> Read Maze from terminal" << std::endl;
                        ReadMazeSize(xlen, zlen, curState);
                        ReadBasePoint(&basePoint, curState);
                        if (ResetIntegerInput(curState)) {
                            inputMaze.setBasePoint(basePoint);
                            inputMaze.setXlen(xlen);
                            inputMaze.setZlen(zlen);
                            ReadMazeInput(inputMaze, curState);
                            inputMaze.PrintMaze();
                            std::cout << "Your maze has successfully been input, go to build maze to create this maze in Minecraft" << std::endl;
                        }
                        
                    }
                    else if (menu_one_selection == 2 ){

                        std::cout <<"Inside -> Generate Random Maze" << std::endl;
                        ReadMazeSize(xlen, zlen, curState);
                        ReadBasePoint(&basePoint, curState);
                        if (ResetIntegerInput(curState)) {
                            inputMaze.setBasePoint(basePoint);
                            inputMaze.setXlen(xlen);
                            inputMaze.setZlen(zlen);
                            CreateRandomMaze(inputMaze, curState);
                            inputMaze.PrintMaze();
                        }
                    }
                    else if (menu_one_selection == 3 ){
                        // Goes back to the menu 3) Back
                        curState = ST_Main;
                    }
                    else{
                        // output invalid input and loop through above again
                        // will loop until valid result is entered
                        ResetIntegerInput(curState);
                        std::cout << "Input Error: Enter a number between 1 and 3 ...." << std::endl;
                    }
                }
            
            }
            // builds maze in minecraft
            else if(main_menu_selection == 2){
                // Build maze always checks for maze initialisation by checking that bp has been set to a value other than nullptr
                if (inputMaze.mazeExistsInMc()) {
                    // destroy maze if one already exists
                    inputMaze.DestroyMazeInMc();
                    inputMaze.setOriginalLandscape();
                }
                // check for Enhanced build flag and use EB algorithm if set
                if (inputMaze.getBasePoint() != nullptr && argc == 2 && std::string(argv[1]) == "-EB") {
                    std::cout << "Building maze now, please wait..." << std::endl;
                    std::vector<std::vector<int>> heightMap = mc.getHeights((*inputMaze.getBasePoint()), (*inputMaze.getBasePoint()) + mcpp::Coordinate(inputMaze.getXlen(), 0, inputMaze.getZlen()));
                    CreateTerrainMaze(heightMap, inputMaze);
                    std::cout << "Maze build complete." << std::endl;
                } 
                else if (inputMaze.getBasePoint() != nullptr) {
                    std::cout << "Building maze now, please wait..." << std::endl;
                    inputMaze.Flattening();
                    inputMaze.BuildMazeInMc();
                    std::cout << "Maze build complete." << std::endl;
                }
                else {
                    std::cout << "Please generate a maze first" << std::endl;
                }
            }
            //Maze solving 
            else if(main_menu_selection == 3){
                // exit if no bp has been set
                if(inputMaze.getBasePoint() == nullptr){
                    std::cout<<"Please generate a maze first"<<std::endl;
                    mc.postToChat("Please generate a maze first");
                    curState = ST_Main; 
                }
                else {
                    curState = ST_SolveMaze;
                    
                }
                while(curState == ST_SolveMaze){
                    // prints respective menu if in normal or enhanced mode
                    printSolveMazeMenu(argc == 2 && std::string(argv[1]) == "-ES");
                    int menu_three_selection = 3;
                    std::cin >> menu_three_selection;
                    Agent solver;
                    if (menu_three_selection == 1 ){
                        inputMaze.PlacePlayerInRandomLocation();
                    }
                    else if (menu_three_selection == 2 ){
                        
                        solver.showEscapeRoute();            
                    }
                    else if (menu_three_selection == 3 ){
                        //Goes back to the menu 3) Back
                        curState = ST_Main;
                    }
                    else if (menu_three_selection == 4 && argc == 2 && std::string(argv[1]) == "-ES") {
                        solver.shortestPath();
                    }
                    else{
                        ResetIntegerInput(curState);
                        std::cout << "Input Error: Enter a number between 1 and 3 ...." << std::endl;
                    }
                }
            }
            else if(main_menu_selection == 4){
                curState = ST_Creators;
                printTeamInfo();
                curState = ST_Main;
            }
            else if(main_menu_selection == 5){
                curState = ST_Exit;
            }
            else{
                ResetIntegerInput(curState);
                std::cout << "Input Error: Enter a number between 1 and 5 ...." << std::endl;
            }
        }
        // before exiting, check if maze has been built and destroy if so
        //destroy always uses the previous values, therefore they must be reset prior to setOriginalLandscape call
        if (inputMaze.mazeExistsInMc()) {
            inputMaze.setBasePoint(inputMaze.getBasePoint());
            inputMaze.setXlen(inputMaze.getXlen());
            inputMaze.setZlen(inputMaze.getZlen());
            inputMaze.DestroyMazeInMc();
            inputMaze.setOriginalLandscape();
        }
    printExitMassage();
    return EXIT_SUCCESS;
}
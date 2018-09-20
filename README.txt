       ___________  
      |_READ__ME__|
     
CS 420-01 Artificial Intelligence: Project #2: N-Queen Problem (N = 21)

Amanda Garcia

This is an N-Queen Problem Solver where N = 21, using two different local search methods to solve:
    (1) Steepest-Ascent Hill Climbing
    (2) Genetic Algorithm
    

Compilation:
    javac Project2.java    
    
    
Execution:
    java Project2
      
When using Solver:     
     Once executed, a menu will pop up giving you the following options:
        (1) Solve the N-Queen (N = 21) Problem using Steepest-Ascent Hill Climbing
        (2) Solve the N-Queen (N = 21) Problem using Genetic Algorithm
        (3) Exit out of the Program
        
     If (1) is selected:
       - The program will generate a random board with queens already positioned, one per column
       - Then the program will attempt to solve the problem using the hill climbing described
       - The program will output the state of the board when the program finished, whether it is the
         solution or not. It will also output the heuristic of the board, if it failed, the moves it took
         to either determine it was a fail or moves it took to solve, the time it took to execute, and 
         the outcome(SUCCESS or FAIL)
         
      If (2) is selected:
      	-The program will run the genetic algorithm
      	-This option will then output the solution board, if it finds a solution. It will also output the 
      	 search cost of the solution, the time it took to solve, and the outcome(SUCCESS or FAIL)
      	-If the algorithm cannot solve the problem in a reasonable amount of time, 
      	 it will print out that it FAILED
      	 
      If (3) is selected:
        -The program will terminate
        
     The menu will recursively output, giving the user the ability to do multiple options in one session
     and also do multiple problems per option.         
         
         
         
         
         
         
         
            
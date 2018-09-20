package cs_420_project2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class Project2 {
	
	private static Comparator<Successors> sort = new SortQueue();          //sorting for the hill climbing, by number of attacking queen pairs
	private static PriorityQueue<Successors> successors = new PriorityQueue<Successors>(1000, sort);   //queue for hill climbing. It is the successors

	private static Comparator<Individual> sorter = new SortFitness();     //sorting for the genetic algorithm, by fitness function
	private static PriorityQueue<Individual> popFit = new PriorityQueue<Individual>(10000, sorter);    //queue for genetic. To order population by fitness
	private static int sumOfFitnesses;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int flag = 0;
		
		while(flag != 1){
		menu();
		
		int option = in.nextInt();
		switch(option){
		case 1:{
			ArrayList<Integer> array = generateRandomBoard(); 
			int result = steepest_Hill(array);
			if(result == 0){
				System.out.println("Success!");
			}else if(result == 1){
				System.out.println("Failure!");
			}
			System.out.println();
		}
			break;
		case 2:{
		
			int outcome = genetic();
			if(outcome == 0){
				System.out.println("Success!");
			}
			if(outcome == 1){
				System.out.println("Failure!");
			}
			System.out.println();
		}
			break;
		case 3:{
			System.out.println("GOODBYE!");
			flag = 1;
			System.exit(0);
		}
		
		}
	}
		in.close();
	}
	
	public static void menu(){
		System.out.println();
		System.out.println("************************ N-Queen (N = 21) ***********************************");
		System.out.println("*                       ------------------                                  *");
		System.out.println("*               Choose an Option by Entering either 1 or 2:                 *");
		System.out.println("* (1) Steepest-Ascent Hill Climbing                                         *");
		System.out.println("*                               OR                                          *");
		System.out.println("* (2) Genetic Algorithm                                                     *");
		System.out.println("*                               OR                                          *");
		System.out.println("* (3) Exit the Program                                                      *");
		System.out.println("*****************************************************************************");
		
	}
	
	//This function is the actual steepest hill climbing algorithm. It takes in the initial state of the board with queens already placed on board.
	//The function will keep moving queens to the successor with the least amount of attacking queen pairs until the heuristic if zero, or there is no better move
	public static int steepest_Hill(ArrayList<Integer> board){
		double totalTime=0;
		double startTime = System.nanoTime();
		Successors current = new Successors(board);
		current.setHeur(setHeuristic(board));
		
		int searchCostHill = 0;      //keep track of search cost, meaning the number of times you move the queens
		
		if(current.getHeur() == 0){       //first, check if the initial state of the board is a valid solution with no attacking queen pairs
			printArray(current.getBoard());    //if so, return success and print board state
			return 0;    //return 0 if the solution is found
		}
		
		while(current.getHeur() != 0){          //continue moving queens to successor with lowest successor heuristic until the solution is found, or no more moves can be made
		generateSuccessors(current.getBoard());      //get all the successors of the current board state
		Successors lowest = successors.peek();       //the priority queue will peek the successor with the lowest number of attacking queen pairs
			if(lowest.getHeur() < current.getHeur()){
				current = lowest;        //if the lowest heuristic of all the successors is lower than the heuristic of the current board state, move that successor     
				searchCostHill++;      
				successors.clear();     //remove all the successors, so that you can only have the successors of the new board after a queen moved
			}else if(lowest.getHeur() >= current.getHeur()){   //if there are no better moves, return FAILURE
				double endTime = System.nanoTime();
				totalTime = endTime - startTime;
				System.out.print("Last Board State: "); 
				printArray(current.getBoard());
				System.out.println("Heuristic of the state of the board where FAILURE occurred: " + current.getHeur());
				System.out.println("Moves it took to determine board FAILED: " + searchCostHill + " moves");
				System.out.println("Time it took to Determine it FAILED: " + totalTime/1000000 + " milliseconds");
				return 1;   //return 1 if the solution cannot be found (Hill Climbing gets STUCK)
			}
		}
		double endTime = System.nanoTime();
		totalTime = endTime - startTime;
		System.out.print("Solution Board: "); 
		printArray(current.getBoard());
		System.out.println("Search Cost for Hill Climbing: " + searchCostHill + " moves");
		System.out.println("Time it took to solve: " + totalTime/1000000 + " milliseconds");
		return 0;   //return 0 if the solution is found
	}

	//This function generates all the successors for every queen
	public static void generateSuccessors(ArrayList<Integer> board){
		for(int i = 0; i < board.size(); i++){
			int original = board.get(i);          
			for(int j = 0; j < board.size(); j++){
				if(j == original){    //do not consider the move a successor if the queen does not move for original position
					continue;
				}
				moveQueen(board, i, j);     //calculate the heuristic for current successor with the moveQueen function using the new position j
				
			}
		}
	}
	
	//This method allows you to create a successor without permanently moving the queen to that position for Hill Climbing
	public static void moveQueen(ArrayList<Integer> board, int i, int j){
			Successors current = new Successors();
			@SuppressWarnings("unchecked")
			ArrayList<Integer> updated = (ArrayList<Integer>) board.clone(); //this is so you do not change the original board set up
		
			updated.set(i, j);  //move the queen temporarily for successor
			current.setBoard(updated);    
			current.setHeur(setHeuristic(updated));  //get the heuristic of the new board after queen was moved
			successors.add(current);   //add the state of the board into the priority queue
		
	}
	
	//This function calculates the heuristic for the Hill Climbing, which is the number of attacking queen pairs
	public static int setHeuristic(ArrayList<Integer> board){ 
		int setHeur = 0;   
		
		for(int i = 0; i < board.size(); i++){
			for(int j = 1; i+j < board.size(); j++){
				if(board.get(i) == board.get(j+i)){   //add one to heuristic if two queens are in the same row
					setHeur++;
				}
				if(board.get(i+j) == (board.get(i) + j)){  //add one to heuristic if two queens are in the same diagonal going right of index
					setHeur++;
				}
				if(board.get(i+j) == (board.get(i) - j)){  //add one to heuristic if two queens are in the same diagonal going left of index
					setHeur++;
				}
			}
		}
		
		return setHeur;
	}
	
	
	
	public static int genetic(){
		double totalTime=0;
		
		double startTime = System.nanoTime();
	
		ArrayList<Individual> population = makePopulation();
		
		int searchCostGenetic = 0;
		
		
		for(int i = 0; i < population.size(); i++){
			if(population.get(i).getFitness() == 210){
				double endTime = System.nanoTime();
				totalTime = endTime - startTime;
				System.out.print("Solution Board :"); 
				printArray(population.get(i).getBoard());
				System.out.println("Search Cost for Genetic Algorithm: " + searchCostGenetic + " iterations of generating successors");
				System.out.println("Time it took to solve: " + totalTime/1000000 + " milliseconds");
				return 0; 
			}
		}
		
		while(true){
			
			if(popFit.size() > 32){                //when the population exceeds 32 individuals, cut it down by half, leaving the 16 individuals with the best fitness
				population.clear();               
				while(popFit.size() != 16){
					Individual indiv = popFit.peek();    //the priority queue allows you to easily get the individuals with the best fitness since it is sorted by fitness
					popFit.remove();
					population.add(indiv);
				}
				popFit.clear();                 
				for(int i = 0; i < population.size(); i++){
					popFit.add(population.get(i));     //add the new population to the emptied queue 
				}	
				
				searchCostGenetic++;      //keep track of how many iterations of generating successors there are
			}
	
			//choose parents for the child based on a probability of likeliness of a parent being chosen
			Individual parent1 = population.get(chooseParent(probabilityOfParent(population))); 
			Individual parent2 = population.get(chooseParent(probabilityOfParent(population))); 
			
			Individual child = makeChild(parent1, parent2);      //create a child
	
			
			if(checkIfDuplicate(population, child.getBoard()) == 0){    //prevents from children already in the population from being added to population
			popFit.add(child);
			population.add(child);
			
			}
			if(child.getFitness() == 210){      //check if child is the solution
				double endTime = System.nanoTime();
				totalTime = endTime - startTime;
				System.out.print("Solution Board :"); 
				printArray(child.getBoard());
				System.out.println("Search Cost for Genetic Algorithm: " + searchCostGenetic + " iterations of generating successors");
				System.out.println("Time it took to solve: " + totalTime/1000000 + " milliseconds");
				return 0;  
			}
			
			if(searchCostGenetic > 100000){        //this will return fail if algorithm could not find solution fast enough, meaning it is having to go through too many sucessor iterations
				System.out.println("Could not find solution fast enough, so it is considered a Failure");
				return 1;
			}
			
			}
		
	}

	//This function checks to see if the child being added to the population is already in the population. This helps to not add any children you know are not solutions
	public static int checkIfDuplicate(ArrayList<Individual> pop, ArrayList<Integer> check){
		ArrayList<ArrayList<Integer>> boards= new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < pop.size(); i++){
			if(boards.contains(check)){
				return 1;
			}else{
				boards.add(pop.get(i).getBoard());
			}
		}
		return 0;
	}
	
	//This function creates the initial population of 16 individuals to be used as possible parents
	public static ArrayList<Individual> makePopulation(){
		
		ArrayList<Individual> population = new ArrayList<Individual>();
		for(int i = 0; i < 17; i++){
			Individual indiv = new Individual(generateRandomBoard());    //generate a random board state
			indiv.setFitness(fitnessFunction(indiv.getBoard()));
			if(!population.contains(indiv)){
			population.add(indiv);
			popFit.add(indiv);
			
			}
		}
		return population;
	}
	
	//This function returns the list of probabilities of the likelihood of each parent being selected to mate 
	public static ArrayList<Double> probabilityOfParent(ArrayList<Individual> parents){
		ArrayList<Double> listOfProb = new ArrayList<Double>();
		double prob = 0;
		for(int i = 0; i < parents.size(); i++){
			if(sumOfFitnesses != 0){
			 prob = parents.get(i).getFitness() / sumOfFitnesses;
			}
			if(sumOfFitnesses == 0){
				prob = 1;
			}
			listOfProb.add(i, prob);
		}
		return listOfProb;
	}
	
	//This function returns the index of the chosen parent based off of the probability of the parents. This helps choose parents that have higher fitness values 
	public static int chooseParent(ArrayList<Double> probOfParents){
		int parentChoice = 0;
		
		Random random = new Random();
		double rand = random.nextInt(101);      
		double prob = rand / 100;               //generate a random number between 0 and 100 to be used like a dice to determine which parent gets chosen
		
		double prob1 = (probOfParents.get(0) + probOfParents.get(1));
		double prob2 = prob1 + probOfParents.get(2);
	
		//This following are ranges of the probabilities to determine where the random number lies to get the parent
		if(prob >= 0 || prob <= probOfParents.get(0)){
			parentChoice = 0;
		}
		if(prob > probOfParents.get(0)  || prob <= prob1){
			parentChoice = 1;
		}
		if(prob > prob1  || prob <= prob2){
			parentChoice = 2;
		}
		if(prob > prob2  || prob <= 1){
			parentChoice = 3;
		}
		
		return parentChoice;
	}
	
	//This function creates the child when given two parents
	public static Individual makeChild(Individual parent1, Individual parent2){
		
		ArrayList<Integer> child1Board = new ArrayList<Integer>();
		
		Random random = new Random();
		int cutoff = random.nextInt(20) + 1;     //get a number from 1 through 20 for cutoff
		
		for(int i = 0; i < parent1.getBoard().size(); i++){
			if(i < cutoff){
				child1Board.add(parent1.getBoard().get(i));
			}
		}
		for(int j = 0; j < parent2.getBoard().size(); j++){
			
			if(j >= cutoff){
				child1Board.add(parent2.getBoard().get(j));
			}			
		}

		Individual child1 = new Individual(child1Board);

		
		child1 = mutate(child1);      //check if the child gets mutated with the mutate function
		
		child1.setFitness(fitnessFunction(child1.getBoard()));
		
		
		return child1;
	}
	
	//This function determines if the child gets mutated or not, and if it does get mutated, it mutates it
	public static Individual mutate(Individual child){
		Random random = new Random();
		int mutate = random.nextInt(101);             //generate random number between 0 and 100 for mutation rate
		int position = random.nextInt(21);            //random queen that will get mutated if chosen to be mutated
		int newPosition = random.nextInt(21);         //random position to move the selected queen
		
		if(mutate <= 20){             //20% mutation rate
			child.getBoard().set(position, newPosition);      //mutate child
		}
		return child;
	}
		
	//This function gets the fitness for the Genetic Algorithm which is the number of non-attacking queens
	public static int fitnessFunction(ArrayList<Integer> board){
		int numberOfAttacking = setHeuristic(board);
		int fitness = 210 - numberOfAttacking;       //MAX fitness function is 210, so subtract number of attacking queen pairs, if any
		sumOfFitnesses += fitness;                //this lets you keep track of the sum of all the fitnesses for a population to be used for the probability
		return fitness;
	}
	 
	//This function generates a random board with queens positioned randomly
	public static ArrayList<Integer> generateRandomBoard(){
		int count = 0;
		ArrayList<Integer> board = new ArrayList<Integer>();
		Random random = new Random();

		while(count != 21){
			int randSeq = random.nextInt(21);   //give me a number from  0 through 20 (for N=21)
			board.add(randSeq);
			count++;
		  }
	   
		return board;
	}
	
	//This function prints out the board as a one dimensional array
	public static void printArray(ArrayList<Integer> anArray) {
	      StringBuilder sb = new StringBuilder();
	      for (int i = 0; i < anArray.size(); i++) {
	    	  if (i == 0) {
		            sb.append("{ ");
		         }
	         if (i > 0) {
	            sb.append("  ");
	         }
	         sb.append(anArray.get(i));
	      }
	      	 sb.append(" }");
	      System.out.println(sb.toString());
	   }
}






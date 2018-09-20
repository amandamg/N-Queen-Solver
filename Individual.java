package cs_420_project2;

import java.util.ArrayList;

public class Individual {
	private ArrayList<Integer> board;
	private int fitness;
	
	public Individual(){
		
	}
	
	public Individual (ArrayList<Integer> board) { 
 		this.board = board;
 	} 
	
	public int getFitness(){
		return fitness;
	}
	
	public void setFitness(int fitness){
		this.fitness = fitness;
	}	
	
	public ArrayList<Integer> getBoard(){
		return board;
	}
 	
	public void setBoard(ArrayList<Integer> board){
		this.board = board;
	}
}

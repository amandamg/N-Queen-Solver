package cs_420_project2;

import java.util.ArrayList;

public class Successors  {
	private int heuristic; 
	private ArrayList<Integer> board;
	
	public Successors(){
		
	}
	
	public Successors(ArrayList<Integer> board) { 
 		this.board = board;
 	} 
	
	public int getHeur(){
		return heuristic;
	}
	
	public void setHeur(int value){
		this.heuristic = value;
	}	
	
	public ArrayList<Integer> getBoard(){
		return board;
	}
 	
	public void setBoard(ArrayList<Integer> board){
		this.board = board;
	}
	
}


package cs_420_project2;

import java.util.Comparator;

public class SortQueue implements Comparator<Successors>{

	//This sorts the hill climbing by successor with the lowest heuristic, meaning the least amount of attacking queens
    public int compare(Successors one, Successors two) {
    	if(one.getHeur() < two.getHeur()){
    		return -1;
    	}
    	if(one.getHeur() > two.getHeur()){
    		return 1;
    	}
        return 0;
    }

}

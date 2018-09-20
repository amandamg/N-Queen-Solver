package cs_420_project2;

import java.util.Comparator;

public class SortFitness implements Comparator<Individual> {

	//This is sorting for the genetic. Subtract 210 from the fitness function, so that the queue sorts the individuals by best fitness, which means that individual
	//has the lowest number of attacking queens (210 - fitness).
	public int compare(Individual one, Individual two) {
		if((210 - one.getFitness()) < (210 - two.getFitness())){
    		return -1;
    	}
    	if((210 - one.getFitness()) > (210 - two.getFitness())){
    		return 1;
    	}
        return 0;
    }
}

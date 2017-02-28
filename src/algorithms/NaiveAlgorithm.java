package algorithms;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */

/**
 * Implementation of exponential algorithm with code gray for comparisons of approximate
 * algorithms to accurate results.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import structures.CNF;
import structures.Literal;

public class NaiveAlgorithm {

	public NaiveAlgorithm() {

	}

	public int ExponentialAlgorithm(CNF myCNF) {
		ArrayList<Literal> variables = myCNF.getMyLiterals();	
		List<Integer> grayCodeNumers = createGrayCode(variables.size()/2);
		int currentMax = 0;
		for(int i=0; i < grayCodeNumers.size(); i++) {
			int number = grayCodeNumers.get(i);
			int binary[] = new int[variables.size()/2];
			int index = 0;
			while(number > 0){
				binary[index++] = number%2;
				number = number/2;
			}
			int curr = 0;
			for(int k = binary.length-1;k >= 0;k--){
				if(binary[k] == 1) {
					//System.out.println("Assigning "+variables.get(curr).toString() + " true.");
					//System.out.println("Assigning "+variables.get(curr+1).toString() + " true.");
					variables.get(curr).setAssigning(true);
					variables.get(curr+1).setAssigning(true);
					variables.get(curr).setOccurrence(true);
					variables.get(curr+1).setOccurrence(true);
				} else { 
					//System.out.println("Assigning "+variables.get(curr).toString() + " false.");
					//System.out.println("Assigning "+variables.get(curr+1).toString() + " false.");
					variables.get(curr).setAssigning(false);
					variables.get(curr+1).setAssigning(false);
					variables.get(curr).setOccurrence(true);
					variables.get(curr+1).setOccurrence(true);
				}
				curr = curr + 2;
			}

			int satisfied = myCNF.countSatisfied();

			if(satisfied >= currentMax) {
				currentMax = satisfied;
				if(currentMax == myCNF.getClauses().size()) break;
			}
		}

		// System.out.println("Exponential Algorithm:");
		//	 System.out.println("Accurate Number of Maximum Satisfied Clauses: " + currentMax);
		return currentMax;
	}

	public static List<Integer> createGrayCode(int n) {
		List<Integer> ret = new LinkedList<>();
		ret.add(0);
		for (int i = 0; i < n; i++) {
			int size = ret.size();
			for (int j = size - 1; j >= 0; j--) {
				Integer n1 = (Integer)ret.get(j) + size;
				ret.add(n1);
			}
		}
		return ret;
	}

}

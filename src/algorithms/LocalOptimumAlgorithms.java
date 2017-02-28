package algorithms;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */

/**
 * Represents a CNF, which is consists of clauses and literals.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import structures.CNF;
import structures.Literal;

/**
 * Implementation of the 2 algorithms for finding local optimum.
 */
public class LocalOptimumAlgorithms {
	private  ArrayList<Literal> variables;
	private CNF myCNF;  
	List<int[]> localOptimumFound= new LinkedList<>();
	List<Integer> grayCodeNumbers;


	public LocalOptimumAlgorithms() {}	

	public double[] FirstLocalOptimumAlgorithm(CNF myCNF,int l) {
		this.myCNF = myCNF;
		this.variables = myCNF.getMyLiterals();	
		
		//this represents all assigning possible!
		grayCodeNumbers = createGrayCode(variables.size()/2); //we created the gray code by the number of variables.
		Random rand = new Random();  
		
		//we randomize l points, and check if each one is an local optimum point
		for(int i = 0; i < l; i++) {
				int grayCodeResult = rand.nextInt(grayCodeNumbers.size());	 //we pick one assigning = one potential point
				int point[] = new int[variables.size()/2]; //we build the specific assigning x1 = true, x2 =false, x3 = true, etc...
				int index = 0;
				
				while(grayCodeResult > 0) { //running over the gray code, building binary by its assingings
					point[index++] = grayCodeResult % 2;
					grayCodeResult = grayCodeResult / 2;
				}
	
				if(isLocalOptimumPoint(point)) { //check if this one is local optimum?
					int k;
					for(k = 0; k < localOptimumFound.size(); k++) {
						if(Arrays.equals(point,localOptimumFound.get(k))) { //have we found it already? we dont want duplications!!!
							break;
						}
					}
					if(k == localOptimumFound.size()) { //if we have not found it, let`s add it - a new fresh local optimum point!
						localOptimumFound.add(point);
					}
				}
		}
		
		
		//calculating the mean
		int amountOfSatisfiedClausesByLocalOptimumPoints[] = new int [localOptimumFound.size()];
		
		double mean = 0;
		
		for(int i = 0; i < amountOfSatisfiedClausesByLocalOptimumPoints.length; i++) {
			amountOfSatisfiedClausesByLocalOptimumPoints[i] = countAmountOfSatisfiedClauses(localOptimumFound.get(i));
			mean += amountOfSatisfiedClausesByLocalOptimumPoints[i];
		}
		mean = (double) mean/amountOfSatisfiedClausesByLocalOptimumPoints.length;
		
		
		//calculate the deviation, by definition
		double standardDeviation = 0 , num;
		
		for(int i=0; i< amountOfSatisfiedClausesByLocalOptimumPoints.length; i++) {
			num = amountOfSatisfiedClausesByLocalOptimumPoints[i]-mean;
			num = num*num;
			standardDeviation += num;
		}
		
		standardDeviation = standardDeviation/amountOfSatisfiedClausesByLocalOptimumPoints.length;
		standardDeviation = Math.sqrt(standardDeviation);
		
		//calculate the "similarity" of points
		double numberOfDifferentAssingings = 0;
		int counter = 0;
		
		//for each pair of different local optimum points
		for(int i = 0; i < localOptimumFound.size()-1 ; i++) {
			for(int j = i+1; j < localOptimumFound.size(); j++) {
				numberOfDifferentAssingings += amountOfSimilarAssigningsInLocalOptimumPoint(localOptimumFound.get(i),localOptimumFound.get(j));
				counter++;
			}
		}
		
		double similarity = numberOfDifferentAssingings/(variables.size()/2)/counter;

		double [] localOptimumAlgorithmResults = new double[4];
		
		localOptimumAlgorithmResults[0] = mean;
		localOptimumAlgorithmResults[1] = standardDeviation;
		localOptimumAlgorithmResults[2] = similarity;
		localOptimumAlgorithmResults[3] = localOptimumFound.size();
		return localOptimumAlgorithmResults;
	}


	public double[] SecondLocalOptimumAlgorithm(CNF myCNF,int l) {	
		this.myCNF = myCNF;
		this.variables = myCNF.getMyLiterals();	
		
		//this represents all assigning possible!
		grayCodeNumbers = createGrayCode(variables.size()/2); 
		
		Random rand = new Random();
		
		int amountOfSatisfiedClauses,random_number;
		
		//we randomize l points, and "climb" our way for each one to it`s local optimum point
		for(int i=0; i < l; i++) {
			int grayCodeResult = rand.nextInt(grayCodeNumbers.size());	 
			int point[] = new int[variables.size()/2];
			int index = 0;
			
			while(grayCodeResult > 0) { //running over the gray code, building binary by its assingings
				point[index++] = grayCodeResult % 2;
				grayCodeResult = grayCodeResult / 2;
			}
			
			//that`s the catch -> we randomize one assiging, and change it if it`s good for us! meaning the
			//number of satisfied clauses is reaching up!!! :)
			while(isLocalOptimumPoint(point) == false) { 
				amountOfSatisfiedClauses = countAmountOfSatisfiedClauses(point);
				random_number = rand.nextInt(variables.size()/2);
				if(point[random_number] == 0) {
						point[random_number] = 1;
						if(countAmountOfSatisfiedClauses(point) < amountOfSatisfiedClauses) {
							point[random_number] = 0; //stay, not good for us
						}
				} else {
						point[random_number] = 0;
						if(countAmountOfSatisfiedClauses(point) < amountOfSatisfiedClauses) {
							point[random_number] = 1;
						}
				}
			} 	
			//if we are here - we have a local optimum point!
			
			int k;
			for(k = 0; k < localOptimumFound.size(); k++) {
				if(Arrays.equals(point,localOptimumFound.get(k))) {
					break;
				}
			}
			
			if(k == localOptimumFound.size()) {
				localOptimumFound.add(point);
			}
		}	 
		//calculating the mean
		int amountOfSatisfiedClausesByLocalOptimumPoints[] = new int [localOptimumFound.size()];
		
		double mean = 0;
		
		for(int i = 0; i < amountOfSatisfiedClausesByLocalOptimumPoints.length; i++) {
			amountOfSatisfiedClausesByLocalOptimumPoints[i] = countAmountOfSatisfiedClauses(localOptimumFound.get(i));
			mean += amountOfSatisfiedClausesByLocalOptimumPoints[i];
		}
		mean = (double) mean/amountOfSatisfiedClausesByLocalOptimumPoints.length;
		
		
		//calculate the deviation, by definition
		double standardDeviation = 0 , num;
		
		for(int i=0; i< amountOfSatisfiedClausesByLocalOptimumPoints.length; i++) {
			num = amountOfSatisfiedClausesByLocalOptimumPoints[i]-mean;
			num = num*num;
			standardDeviation += num;
		}
		
		standardDeviation = standardDeviation/amountOfSatisfiedClausesByLocalOptimumPoints.length;
		standardDeviation = Math.sqrt(standardDeviation);
		
		//calculate the "similarity" of points
		double numberOfDifferentAssingings = 0;
		int counter = 0;
		
		//for each pair of different local optimum points
		for(int i = 0; i < localOptimumFound.size()-1 ; i++) {
			for(int j = i+1; j < localOptimumFound.size(); j++) {
				numberOfDifferentAssingings += amountOfSimilarAssigningsInLocalOptimumPoint(localOptimumFound.get(i),localOptimumFound.get(j));
				counter++;
			}
		}
		
		double similarity = numberOfDifferentAssingings/(variables.size()/2)/counter;

		double [] localOptimumAlgorithmResults = new double[4];
		
		localOptimumAlgorithmResults[0] = mean;
		localOptimumAlgorithmResults[1] = standardDeviation;
		localOptimumAlgorithmResults[2] = similarity;
		localOptimumAlgorithmResults[3] = localOptimumFound.size();
		return localOptimumAlgorithmResults;
	}	

	public int amountOfSimilarAssigningsInLocalOptimumPoint(int []a, int[]b)
	{
		int sum = 0;
		for(int i=0; i<a.length; i++) {
			if(a[i] == b[i]) {
				sum++;
			}
		}
		return sum;
	}
	
	public boolean isLocalOptimumPoint(int [] binary)
	{ 
		int current_satsifced = 0;
		int counter;
		current_satsifced=countAmountOfSatisfiedClauses(binary);
		counter=0;
		for(int j=0; j < binary.length; j++)
		{
			if(binary[j]==0)
			{
				binary[j]=1;
				if(countAmountOfSatisfiedClauses(binary)>current_satsifced)
				{
					break;
				}
				binary[j]=0;
			}
			else
			{
				binary[j]=0;
				if(countAmountOfSatisfiedClauses(binary)>current_satsifced)
				{
					break;
				}
				binary[j]=1;
			}
			counter++;

		}
		if(counter==binary.length)
		{
			//global_max.add(binary);
			return true;
		}
		return false;

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

	public int countAmountOfSatisfiedClauses( int [] binary)
	{
		int curr = 0;
		for(int k=0;k<binary.length;k++){
			if(binary[k] == 1) {
				variables.get(curr).setAssigning(true);
				variables.get(curr+1).setAssigning(true);
				variables.get(curr).setOccurrence(true);
				variables.get(curr+1).setOccurrence(true);
			} else { 
				variables.get(curr).setAssigning(false);
				variables.get(curr+1).setAssigning(false);
				variables.get(curr).setOccurrence(true);
				variables.get(curr+1).setOccurrence(true);
			}
			curr = curr + 2;
		}
		return this.myCNF.countSatisfied();

	}



}



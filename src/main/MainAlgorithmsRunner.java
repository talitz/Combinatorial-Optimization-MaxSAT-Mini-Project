package main;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */

/**
 * This class is for our personal use to display the results...
 */
import java.io.IOException;

import algorithms.ApproximatelyAlgorithms;
import algorithms.NaiveAlgorithm;
import structures.CNF;
import structures.CNFGenerator;
import enums.Algorithm;
import algorithms.LocalOptimumAlgorithms;

public class MainAlgorithmsRunner {
	
	//0 - A Algorithm
	//1 - B Algorithm
	//2 - C Algorithm
	//3 - D Algorithm
	//4 - Naive Algorithm
	public static final int numOfTotalAlgorithms = 7;
	
	public static void runAAlgorithm(int literalsPerClause, int n, int m, int numberOfCNFS,boolean improved,int k) {
		ApproximatelyAlgorithms runApprox = new ApproximatelyAlgorithms();
		NaiveAlgorithm runMe = new NaiveAlgorithm();
		
		CNF [] testMe;
		int result_A = 0 ,result_naive = 0;

		for(int i=0; i < numberOfCNFS; i++) {
			testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
			if(improved) {
				result_A += runApprox.improvedAAlgorithm(testMe[Algorithm.A.getValue()],k);
			} else {
				result_A += runApprox.AAlgorithm(testMe[Algorithm.A.getValue()]);
			}
			
			result_naive += runMe.ExponentialAlgorithm(testMe[Algorithm.NAIVE.getValue()]);	
		}
		
		if(improved) {
			System.out.println("Improved A algorithm: ");
		} else {
			System.out.println("A algorithm: ");
		}
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("m/n ratio: " + (double)m/n);
		System.out.println("Number of CNF's: "+numberOfCNFS);
		System.out.println("Satisfied clauses (in percentage): "+((double)result_A/(m*numberOfCNFS))*100+"%");	
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}
	
	public static void runBAlgorithm(int literalsPerClause, int n, int m, int numberOfCNFS) {
		ApproximatelyAlgorithms runApprox = new ApproximatelyAlgorithms();
		NaiveAlgorithm runMe = new NaiveAlgorithm();
		
		CNF [] testMe;
		int result_B = 0 ,result_naive = 0;

		for(int i=0; i < numberOfCNFS; i++) {
			testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
			result_B += runApprox.BAlgorithm(testMe[Algorithm.B.getValue()]);
			result_naive += runMe.ExponentialAlgorithm(testMe[Algorithm.NAIVE.getValue()]);	
		}
		
		System.out.println("B algorithm: ");
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("m/n ratio: " + (double)m/n);
		System.out.println("Number of CNF's: "+numberOfCNFS);
		System.out.println("Satisfied clauses (in percentage): "+((double)result_B/(m*numberOfCNFS))*100+"%");	
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}

	public static void runCAlgorithm(int literalsPerClause, int n, int m, int numberOfCNFS) {
		ApproximatelyAlgorithms runApprox = new ApproximatelyAlgorithms();
		NaiveAlgorithm runMe = new NaiveAlgorithm();
		
		CNF [] testMe;
		int result_C = 0 ,result_naive = 0;

		for(int i=0; i < numberOfCNFS; i++) {
			testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
			result_C += runApprox.CAlgorithm(testMe[Algorithm.C.getValue()]);
			result_naive += runMe.ExponentialAlgorithm(testMe[Algorithm.NAIVE.getValue()]);	
		}
		
		System.out.println("C algorithm: ");
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("m/n ratio: " + (double)m/n);
		System.out.println("Number of CNF's: "+numberOfCNFS);
		System.out.println("Satisfied clauses (in percentage): "+((double)result_C/(m*numberOfCNFS))*100+"%");	
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}
	
	public static void runDAlgorithm(int literalsPerClause, int n, int m, int numberOfCNFS) {
		ApproximatelyAlgorithms runApprox = new ApproximatelyAlgorithms();
		NaiveAlgorithm runMe = new NaiveAlgorithm();
		
		CNF [] testMe;
		int result_D = 0 ,result_naive = 0;

		for(int i=0; i < numberOfCNFS; i++) {
			testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
			result_D += runApprox.DAlgorithm(testMe[Algorithm.D.getValue()]);
			result_naive += runMe.ExponentialAlgorithm(testMe[Algorithm.NAIVE.getValue()]);	
		}
		
		System.out.println("D algorithm: ");
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("m/n ratio: " + (double)m/n);
		System.out.println("Number of CNF's: "+numberOfCNFS);
		System.out.println("Satisfied clauses (in percentage): "+((double)result_D/(m*numberOfCNFS))*100+"%");	
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}
	
	public static void runFirstLocalOptimumAlgorithm(int literalsPerClause, int n, int m, int numOfPointsToCreate) {
		LocalOptimumAlgorithms runLocalOptimumAlgorithm = new LocalOptimumAlgorithms();
		
		//We save the results in the following cells:
		//0 - Local Optimum Points Average
		//1 - Local Optimum Points Standard Deviation
		//2 - Similarity of Local Optimum Points
		//3 - Number of Local Optimum Points
		double[] localOptimumAlgorithmResults = new double [4];
		
		CNF [] testMe;

		testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
		localOptimumAlgorithmResults = runLocalOptimumAlgorithm.FirstLocalOptimumAlgorithm(testMe[Algorithm.FirstLocalOptimumAlgorithm.getValue()],numOfPointsToCreate);
		
		System.out.println("First Local Optimum Algorithm:");
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+ n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("Number of CNF`s: 1");
		System.out.println("Number of Local Optimum Points: "+ (int)localOptimumAlgorithmResults[3]);
		System.out.println("Local Optimum Points Performance:");
		System.out.println("Satisfied Clauses (Average): "+ localOptimumAlgorithmResults[0]);
		System.out.println("Satisfied Clauses (Standard Deviation): "+localOptimumAlgorithmResults[1]);
		System.out.println("Similarity of Local Optimum Points (percentage): "+localOptimumAlgorithmResults[2]*100+"%");
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}
	
	public static void runSecondLocalOptimumAlgorithm(int literalsPerClause, int n, int m, int numOfPointsToCreate) {
		LocalOptimumAlgorithms runLocalOptimumAlgorithm = new LocalOptimumAlgorithms();
		
		//We save the results in the following cells:
		//0 - Local Optimum Points Average
		//1 - Local Optimum Points Standard Deviation
		//2 - Similarity of Local Optimum Points
		//3 - Number of Local Optimum Points
		double[] localOptimumAlgorithmResults = new double [4];
		
		CNF [] testMe;

		testMe = new CNFGenerator(literalsPerClause,n,m,numOfTotalAlgorithms).Generate();
		localOptimumAlgorithmResults = runLocalOptimumAlgorithm.SecondLocalOptimumAlgorithm(testMe[Algorithm.SecondLocalOptimumAlgorithm.getValue()],numOfPointsToCreate);
		
		System.out.println("Second Local Optimum Algorithm:");
		System.out.println("Number of literals per clause: " + literalsPerClause);
		System.out.println("Number of variables (n): "+ n);
		System.out.println("Number of clauses (m): " + m);
		System.out.println("Number of CNF`s: 1");
		System.out.println("Number of Local Optimum Points: "+ (int)localOptimumAlgorithmResults[3]);
		System.out.println("Local Optimum Points Performance:");
		System.out.println("Satisfied Clauses (Average): "+ localOptimumAlgorithmResults[0]);
		System.out.println("Satisfied Clauses (Standard Deviation): "+localOptimumAlgorithmResults[1]);
		System.out.println("Similarity of Local Optimum Points (percentage): "+localOptimumAlgorithmResults[2]*100+"%");
		System.out.println();System.out.println("---------------------------------------");System.out.println();
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		runAAlgorithm(3,10,42,100,false,0);
		runAAlgorithm(3,10,42,100,true,100); //improved version
		
		runBAlgorithm(3,10,42,100);
		
		runCAlgorithm(3,10,80,100);
		
		runDAlgorithm(3,10,80,100);
		
		runFirstLocalOptimumAlgorithm(3,10,42,1000); //1000 points to create and check if they are local optimum points
		
		runSecondLocalOptimumAlgorithm(3,10,42,1000); //1000 points to create and "climb" the way to local optimum points
	}
}

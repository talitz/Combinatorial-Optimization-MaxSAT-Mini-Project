package algorithms;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */


/**
 * Implementation of all the approximate algorithms we showed the results of, 
 * A B C D, at the same order they are showed in the documentation pdf doc.
 */
import java.util.ArrayList;
import java.util.Random;
import structures.Literal;
import structures.CNF;
import structures.LiteralWithOccurence;

public class ApproximatelyAlgorithms {
	ArrayList<Literal> variables;
	CNF CNFparse;
	
	public ApproximatelyAlgorithms() {}

	public int AAlgorithm(CNF CNFparse)
	{	
		this.CNFparse=CNFparse;
		variables = CNFparse.getMyLiterals();	
		Random rand = new Random();
		for(int i=0; i < variables.size() / 2; i++) {
				int random_number = rand.nextInt(2);
				variables.get(i*2).setAssigning(random_number!=0);      
				variables.get(i*2+1).setAssigning(random_number!=0);
				variables.get(i*2+1).setOccurrence(true);
				variables.get(i*2).setOccurrence(true);
		}

		return CNFparse.countSatisfied();
	}

	//Running A`s algorithm k times
	public int improvedAAlgorithm(CNF CNFparse, int k)
	{	
		this.CNFparse=CNFparse;
		variables = CNFparse.getMyLiterals();	
		Random rand = new Random();
		int countSatisfied=0,num;
		for(int j=0; j < k; j++)
		{
			for(int i=0; i < variables.size() / 2; i++) {//loop again
				int random_number = rand.nextInt(2);
				variables.get(i*2).setAssigning(random_number!=0);      
				variables.get(i*2+1).setAssigning(random_number!=0);
				variables.get(i*2+1).setOccurrence(true);
				variables.get(i*2).setOccurrence(true);
			}
			num=CNFparse.countSatisfied();
			if(num>countSatisfied)
				countSatisfied=num;
		}
		return countSatisfied;
	}

	public int BAlgorithm(CNF CNFparse)
	{
		this.CNFparse = CNFparse;
		variables = CNFparse.getMyLiterals();
		Random rand = new Random();
		int random_number, random, countSatisfied = 0;
		double [] addOccurrence = new double [CNFparse.getClauses().get(0).getNumOfLitrals()+1]; //array 
		int [] variablesarray = new int [variables.size()/2+1];
		int current_length = variablesarray.length - 1;
		
		for(int i = 0; i < variables.size()/2; i++) {
			variablesarray[i] = i;
		}
		
		for(int i = 1; i < addOccurrence.length; i++) {
			addOccurrence[i] = 1/(Math.pow(2, i));
		}	
		
		for(int i = 0; i < variables.size()/2; i++) {   
			random = rand.nextInt(current_length); //we randomize a new literal each iteration
			random_number = variablesarray[random];
			current_length--;
			variablesarray[random] = variablesarray[current_length];
			int numOfNotOccurrences;
			double sizea = 0,sizeb = 0;
			
			//run over all clauses
			for(int j= 0; j < CNFparse.getClauses().size(); j++) { 
				numOfNotOccurrences = CNFparse.getClauses().get(j).numOfNotOccurrences(); //the number of literals
				//run over all literals
				for(int k = 0; k < CNFparse.getClauses().get(j).getLiteral().size(); k++) { 
					if(CNFparse.getClauses().get(j).getLiteral().get(k) == variables.get(random_number*2)) { //is the literal Xi appears?
						sizea += addOccurrence[numOfNotOccurrences];
					}

					if(CNFparse.getClauses().get(j).getLiteral().get(k) == variables.get(random_number*2+1)) { //is the literal not(Xi) appears?
						sizeb += addOccurrence[numOfNotOccurrences];
					}
				}	
			}
			
			if(sizea >= sizeb) { 
				//Xi was better as Xi = true
				variables.get(random_number*2).setAssigning(true);	
				variables.get(random_number*2).setOccurrence(true);
				variables.get(random_number*2+1).setAssigning(true);
				variables.get(random_number*2+1).setOccurrence(true);
			} else {
				//not(Xi) was better as not(Xi) = false
				variables.get(random_number*2).setAssigning(false);
				variables.get(random_number*2+1).setAssigning(false);
				variables.get(random_number*2).setOccurrence(true);
				variables.get(random_number*2+1).setOccurrence(true);
			}
			countSatisfied = countSatisfied + deleteAndRetrieveSatisfiedClauses(CNFparse);	 
		}
		return countSatisfied;	 
	}

	public int CAlgorithm(CNF CNFparse)
	{
		this.CNFparse = CNFparse; 
		variables = CNFparse.getMyLiterals();
		int literalID;
		ArrayList<LiteralWithOccurence> arrayOfLiteralsWithOccurrences = new ArrayList<LiteralWithOccurence>();
		
		for(int i=0; i < variables.size(); i++) {
			arrayOfLiteralsWithOccurrences.add(new LiteralWithOccurence(i));
		}
		
		for(int i=0; i<CNFparse.NumOfClauses(); i++) {
			for(int j=0; j<CNFparse.getClauses().get(i).getNumOfLitrals(); j++) {
				literalID = CNFparse.getClauses().get(i).getLiteral().get(j).getId();
				literalID = literalID*2;
				if(CNFparse.getClauses().get(i).getLiteral().get(j).getNot()) {
					literalID++;
				}
				arrayOfLiteralsWithOccurrences.get(literalID).increaseOccurence();			
			}
		}
		for(int i = 0;i < arrayOfLiteralsWithOccurrences.size(); i = i+2) {
			//for each Xi and not(Xi), we check who has more occurrences and set up a relevant value.
			if(arrayOfLiteralsWithOccurrences.get(i).num_Of_Occuernce > arrayOfLiteralsWithOccurrences.get(i+1).num_Of_Occuernce) {
				variables.get(i).setAssigning(true);	
				variables.get(i).setOccurrence(true);
				variables.get(i+1).setAssigning(true);
				variables.get(i+1).setOccurrence(true);  
			} else {
				variables.get(i).setAssigning(false);	
				variables.get(i).setOccurrence(true);
				variables.get(i+1).setAssigning(false);
				variables.get(i+1).setOccurrence(true);
			}
		}
		return CNFparse.countSatisfied();
	}

	public int DAlgorithm(CNF CNFparse)
	{
		this.CNFparse = CNFparse; 
		variables = CNFparse.getMyLiterals();
		int largestLiteralID , countSatisfied = 0;
		int [] arrayOfLiteralOccurences = new int[variables.size()];
		
		for(int i=0; i< CNFparse.getMyLiterals().size()/2; i++) { 
			arrayOfLiteralOccurences  = retrieveArrayOfLiteralOccurences(CNFparse,arrayOfLiteralOccurences);
			largestLiteralID = findLargestLiteralID(arrayOfLiteralOccurences);
			
			if(largestLiteralID %2 == 0) {
				variables.get(largestLiteralID).setAssigning(true);	
				variables.get(largestLiteralID).setOccurrence(true);
				variables.get(largestLiteralID+1).setAssigning(true);
				variables.get(largestLiteralID+1).setOccurrence(true);
				arrayOfLiteralOccurences[largestLiteralID] = -1; //mark this one as we set an assigning already
				arrayOfLiteralOccurences[largestLiteralID+1] = -1; //mark this as well!
			} else {
				variables.get(largestLiteralID).setAssigning(false);	
				variables.get(largestLiteralID).setOccurrence(true);
				variables.get(largestLiteralID-1).setAssigning(false);
				variables.get(largestLiteralID-1).setOccurrence(true);
				arrayOfLiteralOccurences [largestLiteralID] = -1; //mark this one as we set an assigning already
				arrayOfLiteralOccurences [largestLiteralID-1] = -1;	//mark this as well!
			}
			
			for(int j=0; j < arrayOfLiteralOccurences.length; j++) {  
				if(arrayOfLiteralOccurences[j] < 0) { //if it is marked as it has an assigning
					arrayOfLiteralOccurences [j] =- (CNFparse.getClauses().size()); 
					//this is done because in the next iteration we are counting the number of occurrences of each literal.
					//this literal has already an assigning.
					//we don`t want, accidently to choose it as the one who appears the most times,
					//so we decrease as if it appears in all clauses, so when we increase it`s value it will never get chosen.
				}
				countSatisfied += deleteAndRetrieveSatisfiedClauses(CNFparse);
			}
		}
		return countSatisfied;
	}

	public int findLargestLiteralID(int []count_literal)
	{
		int largest=0, id_of_lagrest=0;
		for(int j=0; j< count_literal.length; j++)
		{
			if(count_literal[j] > largest)
			{
				largest = count_literal[j];
				id_of_lagrest=j;
			}
		}
		return id_of_lagrest;
	}

	public int deleteAndRetrieveSatisfiedClauses(CNF CNFparse)
	{ 		    
		int count_satsifid=0,n=0;
		int size=CNFparse.getClauses().size();
		for(int i=0;i<size;i++)	
		{ 
			if(CNFparse.getClauses().get(n).isSatisfied())
			{	
				CNFparse.getClauses().remove(n);
				count_satsifid++;
			}
			else
				n++;
			if(CNFparse.getClauses().size()==i)
				break;
		}
		return count_satsifid;
	}

	public int[] retrieveArrayOfLiteralOccurences(CNF CNFparse,int[] count_literal)
	{
		int num;
		for(int i=0;i<CNFparse.NumOfClauses();i++) {
			for(int j=0;j<CNFparse.getClauses().get(i).getNumOfLitrals();j++) {
				num = CNFparse.getClauses().get(i).getLiteral().get(j).getId();
				num = num*2;
				if(CNFparse.getClauses().get(i).getLiteral().get(j).getNot()) {
					num++;
				}
				count_literal[num]++;				
			}
		}
		return count_literal;
	}

}

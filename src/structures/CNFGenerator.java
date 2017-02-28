package structures;

/**
  * The Class is part of Combinatorics-Optimization-MaxSAT project,
  * and was written by Haim Saban & Tal Yitzhak.
  * For any question you can refer to: talyitzhak100@gmail.com, or either 
  * haimsa13@gmail.com.
  */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * CNF Generator implementation, for creating CNF`s by variables demand:
 * 1. number of literals per clause.
 * 2. number of variables.
 * 3. number of clauses.
 * 4. number of cnf`s.
 */
public class CNFGenerator {
	private int Literals_per_clause;
	private int Number_of_variables;
	private int Number_of_clauses;
	private int startedID = 0;
	private int num_of_cnfs;
	private Throwable e;

	public CNFGenerator(int Literals_per_clause, int Number_of_variables,int  Number_of_clauses,int number_of_cnf) {
		if(Literals_per_clause <= Number_of_variables) {
			this.Literals_per_clause = Literals_per_clause;
			this.Number_of_variables = Number_of_variables;
			this.Number_of_clauses = Number_of_clauses;
			this.num_of_cnfs=number_of_cnf;
		} else {
			try {
				throw new Exception("Literals_per_clause <= Number_of_variables -> Exception", e);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	}
	/**
	 * Generates the requested CNF by it`s initialized fields....
	 */
	public CNF[] Generate() {

		CNF [] Generated = new CNF[this.num_of_cnfs];

		//ArrayList<ArrayList<Literal>> variables = new ArrayList<ArrayList<Literal>>(this.num_of_cnfs);
		@SuppressWarnings("unchecked")
		ArrayList<Literal>[] variables = (ArrayList<Literal>[])new ArrayList[this.num_of_cnfs];
		for(int i=0; i<this.num_of_cnfs; i++) {
			Generated[i]=new CNF();	
			variables[i]=new ArrayList<Literal>() ;
			startedID=0;
			for(int j=0; j<this.Number_of_variables; j++) {			  
				variables[i].add(new Literal(startedID,false,false,false,Literals_per_clause));
				variables[i].add(new Literal(startedID,true,false,false,Literals_per_clause));
				startedID = startedID + 1;
			}
		}
		markAllVariablesAsUnUsed(variables[0]);
		for(int i=0; i<this.Number_of_clauses; i++) {
			Clause [] thisClause = new Clause[num_of_cnfs];
			for(int j=0; j<this.num_of_cnfs; j++)
				thisClause[j]=new Clause();
			for(int k=0; k<this.Literals_per_clause; k++) {
				int rand = randInt(0,this.Number_of_variables*2-1);
				Literal temp = variables[0].get(rand);

				while(temp.isUsed()) {
					rand = randInt(0,this.Number_of_variables*2-1);
					temp = variables[0].get(rand);
				}
				temp.markAsUsed();
				if(rand%2 == 0) {
					variables[0].get(rand+1).markAsUsed();
				} else {
					variables[0].get(rand-1).markAsUsed();
				}
				for(int j=0; j<this.num_of_cnfs; j++) 
					thisClause[j].addLiteral(variables[j].get(rand)); 
			}
			for(int j=0; j<this.num_of_cnfs; j++) 
			{ 
				Generated[j].addClause(thisClause[j]);
			}
			markAllVariablesAsUnUsed(variables[0]);
		}

		for(int j=0; j<this.num_of_cnfs; j++) 
			Generated[j].setMyLiterals(variables[j]);
		return Generated;
	}

	public void superShuffleVariables(ArrayList<Literal> variables) {
		for(int i=0; i< 300; i++) {
			shuffleVariables(variables); 
		}
	}

	public void shuffleVariables(ArrayList<Literal> variables) {
		Collections.shuffle(variables);

		for (int i = 0; i < variables.size(); i++) {
			if(randInt(0,1) == 1) {
				Literal temp = variables.get(i);
				temp.setNotValue();
			}
		}
	}

	public void markAllVariablesAsUnUsed(ArrayList<Literal> variables) {
		for(int j=0; j<this.Number_of_variables*2-1; j++) {
			variables.get(j).markAsUnUsed();  
		}
	}

	public void printVariables(ArrayList<Literal> variables) {
		for (int i = 0; i < variables.size(); i++) {
			System.out.print(variables.get(i).toString() + ",");
		}
		System.out.println();
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}

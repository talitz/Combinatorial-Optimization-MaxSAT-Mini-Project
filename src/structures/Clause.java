package structures;

/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */



import java.util.ArrayList;

/**
 * The Class represents a clause in a CNF. It had an id and a list of literals.
 */

public class Clause {
	private ArrayList<Literal> MyLiterals;
	public int id;


	public Clause()
	{
		MyLiterals=new ArrayList<Literal>();
	}


	public Clause(ArrayList<Literal> MyLiterals) {
		this.MyLiterals = MyLiterals;
	}


	public void addLiteral(Literal l)
	{
		this.MyLiterals.add(l);

	}

	public Boolean isSatisfied() { //remind haim to explain me why getOccurence is needded here
		for (int i = 0; i < this.MyLiterals.size(); i++) {
			if(this.MyLiterals.get(i).isLiteralSatisfied()&&
					this.MyLiterals.get(i).getOccurrence()) 
				return true;

		}
		return false;
	}

	public int getNumOfLitrals()
	{
		return MyLiterals.size(); 
	}

	public int numOfNotOccurrences()
	{
		int sum=0;
		for (int i = 0; i < this.MyLiterals.size(); i++)
		{
			if(!this.MyLiterals.get(i).getOccurrence())
				sum++;      				
		}
		return sum;


	}

	public ArrayList<Literal> getLiteral(){
		return this.MyLiterals;
	}


	public String toString() {
		String str = "(";

		for (int i = 0; i < this.MyLiterals.size(); i++) {
			if(i < this.MyLiterals.size()-1) {
				str += this.MyLiterals.get(i).toString()+ " | ";
			} else {
				str += this.MyLiterals.get(i).toString();
			}
		} 
		str += ")";
		return str;
	}

}

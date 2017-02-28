package structures;

/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */



import java.util.ArrayList;
/**
 * Represents a CNF, which is consists of clauses and literals.
 */
public class CNF { 
	private ArrayList<Clause> MyClauses;
	private ArrayList<Literal> MyLiterals;

	public CNF(ArrayList<Clause> MyClauses) {
		this.MyClauses = MyClauses;
	}

	public CNF()
	{
		MyClauses= new ArrayList<Clause>();
	}

	public void addClause(Clause c)
	{
		this.MyClauses.add(c);
	}
	public void setClause(ArrayList<Clause> MyClauses)
	{
		this.MyClauses=MyClauses;
	}

	public void setMyLiterals(ArrayList<Literal> MyLiterals) {
		this.MyLiterals = MyLiterals;
	}

	public ArrayList<Literal> getMyLiterals() {
		return this.MyLiterals;
	} 

	public Boolean isSatisfied() {
		for (int i = 0; i < this.MyClauses.size(); i++) {
			if(!this.MyClauses.get(i).isSatisfied()) return false;
		}
		return true;
	}

	public int countSatisfied() {
		int count = 0;

		for (int i = 0; i < this.MyClauses.size(); i++) {
			//System.out.println("Currently checking this Clause: " +this.MyClauses.get(i).toString());
			if(this.MyClauses.get(i).isSatisfied()) count++;
		}
		return count;
	}

	public ArrayList<Clause> getClauses() {
		return this.MyClauses;
	}

	public int NumOfClauses()
	{
		return MyClauses.size();
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < this.MyClauses.size(); i++) {

			if(i < this.MyClauses.size()-1) {
				str += this.MyClauses.get(i).toString()+ " ^ ";
			} else {
				str += this.MyClauses.get(i).toString();
			}
		} 
		return str;
	}

}

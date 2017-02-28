package structures;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */

	/**
	 * Represent literal with X occurences in a CNF.
	 */
public class LiteralWithOccurence implements Comparable<LiteralWithOccurence> {
	public int id;
	public int num_Of_Occuernce;

	public LiteralWithOccurence(int i)
	{
		this.id=i;
		this.num_Of_Occuernce=0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNum_Of_Occuernce() {
		return num_Of_Occuernce;
	}

	public void setNum_Of_Occuernce(int num_Of_Occuernce) {
		this.num_Of_Occuernce = num_Of_Occuernce;
	}

	public void increaseOccurence()
	{
		this.num_Of_Occuernce++;
	}

	@Override
	public int compareTo(LiteralWithOccurence o) {
		return this.num_Of_Occuernce-o.num_Of_Occuernce;
	}

}

package structures;
/**
 * The Class is part of Combinatorics-Optimization-MaxSAT project,
 * and was written by Haim Saban & Tal Yitzhak.
 * For any question you can refer to: talyitzhak100@gmail.com, or either 
 * haimsa13@gmail.com.
 */

	/**
	 * literal representation...
	 */
public class Literal {
	private int Id; //Every literal has it's own id
	private Boolean Not; //true for not, false for the value itself 
	private Boolean Value; //Literal's value declared by Not
	private Boolean Assigning; //X = T\F
	private Boolean Occurrence;
	private Boolean Used;

	public Literal(int Id, Boolean Not,int size) {
		this.Id = Id;
		this.Not = Not;
		this.Used = false;

	}

	public Literal(int Id, Boolean Not, Boolean Assigning, boolean Occurrence,int size) {
		this.Id = Id;
		this.Used = false;
		this.Not = Not;
		this.Assigning = Assigning;
		if(Not) {
			this.Value = !Assigning;
		} else {
			this.Value = Assigning;
		}
		this.Occurrence=Occurrence;
	}
	public Literal(Literal literal)
	{
		this.Id = literal.Id;
		this.Used = literal.Used;
		this.Not = literal.Not;
		this.Assigning = literal.Assigning;
		this.Value=literal.Value;
		this.Occurrence=literal.Occurrence;


	}


	public Literal(int Id, Boolean Not, Boolean Occurrence) {
		this.Id = Id;
		this.Used = false;
		this.Not = Not;
		this.Occurrence=Occurrence;
	}


	public Boolean getValue() {
		return this.Value;
	}

	public int getId() {
		return Id;
	}

	public Boolean getUsed() {
		return this.Used;
	}
	public Boolean getNot() {
		return this.Not;
	}
	public int getIntNot() {
		if (this.Not==false)
			return 0;
		return 1;		
	}


	public Boolean getAssigning() {
		return Assigning;
	}

	public Boolean isLiteralSatisfied() {
		return this.Value;
	}

	public void setNotValue() {
		if(this.Not) {
			this.Not = false;
		} else {
			this.Not = true;
		}
	}

	public void markAsUsed() {
		this.Used = true;
	}

	public void markAsUnUsed() {
		this.Used = false;
	}

	public Boolean isUsed() {
		return this.Used;
	}

	public void setAssigning(Boolean assigning) {
		Assigning = assigning;
		if(Not) {
			this.Value = !Assigning;
		} else {
			this.Value = Assigning;
		}
	}

	public Boolean getOccurrence() {
		return Occurrence;
	}

	public void setOccurrence(boolean Occurrence){
		this.Occurrence=Occurrence;
	}


	public String toString() {
		if(!Not){
			return "x"+this.Id;
		} else { 
			return "¬x"+this.Id;
		}
	}


}

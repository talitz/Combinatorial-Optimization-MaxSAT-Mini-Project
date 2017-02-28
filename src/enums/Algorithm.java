package enums;

public enum Algorithm {
	A(0), B(1), C(2), D(3), NAIVE(4), FirstLocalOptimumAlgorithm(5), SecondLocalOptimumAlgorithm(6);
	
	private final int value;
	
    private Algorithm (int value) {
        this.value = value;
    }
    
	public int getValue() {
	    return value;
	}
}


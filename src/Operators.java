import java.util.ArrayList;

public abstract class Operators extends Equation {
	
	public Operators(String testCase) {
		super(testCase);
		// TODO Auto-generated constructor stub
	}

	public abstract ArrayList<String> operation();
	
	public void createList() {
		super.convert();
	}
	
	public void calculate() {
		this.operation();
	}
	
}

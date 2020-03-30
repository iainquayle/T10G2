import java.util.ArrayList;

public abstract class Operators extends Equation {
	
	public Operators(String testCase) {
		super(testCase);
	}

	public abstract ArrayList<String> operation(ArrayList<String> m );
	
	public void calculate() {
		this.operation(super.getFormulaSplitAll());
	}
	
}

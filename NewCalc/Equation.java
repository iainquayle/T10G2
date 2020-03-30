import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
	private ArrayList<String> formulaSplitAll = new ArrayList<String>();
	private String[] ArrayForm;
	private String equation;
	
	protected int counter = 0;

	
	public Equation(String testCase) {
		setEquation(testCase);
		setArrayForm();
		setFormulaSplitAll();
		checkForBrack();
	
	}
	
	public String getEquation() {
		return this.equation;
	}

	public void setEquation(String equation) {
		this.equation = equation;
	}
	public String[] getArrayForm() {
		return ArrayForm;
	}
	
	public void setArrayForm() {
		ArrayForm = getEquation().split(" ");
	}

	public ArrayList<String> getFormulaSplitAll() {
		return formulaSplitAll;
	}

	public void setFormulaSplitAll() {
		for(int i = 0; i < getArrayForm().length ; i++) {
			formulaSplitAll.add(getArrayForm()[i]);
		}
		formulaSplitAll.removeAll(Arrays.asList("", null));
	}
		
	
	
	public int checkForBrack() {
		for (int i = 0; i < getArrayForm().length; i++) {
			if(getArrayForm()[i].equals("(")) {
				counter ++;
			}
				
		}
		return counter;
	}


	
}

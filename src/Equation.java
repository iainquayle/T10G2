import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
	private ArrayList<String> formulaSplitAll = new ArrayList<String>();
	private String[] ArrayForm;
	private ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
	private String equation;
	protected static ArrayList<String> copy = new ArrayList<String>();
	private int counter = 0;
	int indexOfOpen= 0;
	int indexOfClose = 0;
	
	public Equation(String testCase) {
		setEquation(testCase);
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
			counter ++;
		}
		return counter;
	}
	
	public ArrayList<String> getFormulaSplitInBrackets() {
		return formulaSplitInBrackets;
	}
	
	public void setFormulaSplitInBrackets() {
	
		while (counter != 0) {
			indexOfOpen = getFormulaSplitAll().indexOf("(");;
		    indexOfClose = getFormulaSplitAll().indexOf(")");
			counter --;
		}
	}
	
	public void addToCopy() {
		for (int i = 0 ; i < getArrayForm().length ; i ++) {
			copy.add(ArrayForm[i]);
		}
	}
	
	public ArrayList<String> getCopy(){
		return this.copy;
	}
	
	public void convert(){
		setArrayForm();
		setFormulaSplitAll();
		checkForBrack();
		setFormulaSplitInBrackets();
		addToCopy();
		getCopy();
		
	}

	

	
}

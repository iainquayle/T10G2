import java.util.ArrayList;
import java.util.Arrays;

public class Equation {
	private ArrayList<String> formulaSplitAll = new ArrayList<String>();
	private String[] ArrayForm;
	private String equation;
	
	protected int counter = 0;

	/**
	 * constructor initializes the instance variables through the setters in order to prevent privacy leaks
	 * @param testCase
	 */
	public Equation(String testCase) 
	{
		
		setEquation(testCase);
		setArrayForm();
		setFormulaSplitAll();
		checkForBrack();
	}
	
	
	
	/**
	 * gets equation
	 * @return
	 */
	public String getEquation() {
		return this.equation;
	}
	
	
	
	/**
	 * sets the value of equation
	 * @param equation - the equation
	 */
	public void setEquation(String equation) {
	
		this.equation = equation;
	}
	
	
	
	/**
	 * gets ArrayForm
	 * @return ArrayForm
	 */
	public String[] getArrayForm() {
		return ArrayForm;
	}
	
	
	
	/**
	 * Sets ArrayForm
	 * splits the original equation string and places it into an array
	 */
	public void setArrayForm() {
		ArrayForm = getEquation().split(" ");
	}
	
	
	
	/**
	 * gets formulaSplitAll
	 * @return
	 */
	public ArrayList<String> getFormulaSplitAll() {
		return formulaSplitAll;
	}
	
	
	
	/**
	 * Sets formulaSplitAll
	 * Splits every element in the formula into a seperate index
	 */
	public void setFormulaSplitAll() {
		
		for(int i = 0; i < getArrayForm().length ; i++) 
		{
			formulaSplitAll.add(getArrayForm()[i]);
		}
		
		formulaSplitAll.removeAll(Arrays.asList("", null));
	}
		
	
	
	/**
	 * Checks if the equations has a bracket
	 * Counts how many brackets there are
	 * @return the counts of how many brackets there are
	 */
	public int checkForBrack() {
		
		for (int i = 0; i < getArrayForm().length; i++)
		{
			
			if(getArrayForm()[i].equals("(")) 
			{
				counter ++;
			}
				
		}
		
		return counter;
	}


	
}

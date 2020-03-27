package engine;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CalculatorV5 {
	private ArrayList<String> formulaSplitAll = new ArrayList<String>();
	private String[] ArrayForm;
	private ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
//	private ArrayList<String> formulaSplitOutBrackets = new ArrayList<String>();
	public int counter = 0;
	
//	public CalculatorV4() {
//		super();
//		this.testCase = super.testCase;
//		
//	}
	
	public  ArrayList<String> converStrToListNoBrack(String testCase){
		// create an array of the equation that is in a string format and split by the spaces
		ArrayForm = testCase.split(" ");
		//add all elements from the array to an array list
		for (int i = 0; i < ArrayForm.length; i++ ) {
			formulaSplitAll.add(ArrayForm[i]);
			formulaSplitInBrackets.add(ArrayForm[i]);

			if (formulaSplitAll.get(i).equals("("))
			{
				counter = counter + 1;
			}
		}
		
		for (int i = 0 ; i < formulaSplitAll.size() ; i ++) {
			if (i + 1 < formulaSplitAll.size() ) {
				if (formulaSplitAll.get(i).equals(" ") && formulaSplitAll.get(i + 1).equals(" ")) {
					formulaSplitAll.remove(i);
				}
			}
		}
		
		for (int i = 0 ; i < formulaSplitInBrackets.size() ; i ++) {
			if (i + 1 < formulaSplitInBrackets.size() ) {
				if (formulaSplitInBrackets.get(i).equals(" ") && formulaSplitInBrackets.get(i + 1).equals(" ")) {
					formulaSplitInBrackets.remove(i);
				}
			}
		}
		
		formulaSplitAll.removeAll(Arrays.asList("", null));
		formulaSplitInBrackets.removeAll(Arrays.asList("", null));
		System.out.println("form-sp" + formulaSplitAll);
		return formulaSplitAll;
	}
	
public ArrayList<String> converStrToListWithBrack(ArrayList<String> formulaSplitInBrack) {
		
		int count = 0 ;
		int indexOfOpen = 0;
		int indexOfClose = 0;


		while(formulaSplitInBrack.contains("(")) {
			
			indexOfOpen = formulaSplitInBrack.indexOf("(");
			for(int i = 0 ; i < formulaSplitInBrack.size(); i ++) {
				if (formulaSplitInBrack.get(i).equals("(")) {
					count++;
				}
				if(formulaSplitInBrack.get(i).equals(")")) {
					count--;
					
					if (count == 0) {
						indexOfClose = i;
						System.out.println("indexOpen: " + indexOfOpen);
						System.out.println("indexClose: " + indexOfClose);
						


						ArrayList<String> sibling = new ArrayList<String>(formulaSplitInBrack.subList(indexOfOpen + 1 , indexOfClose));
						System.out.println("sibling-: " + sibling);
						System.out.println("splitIn: " + formulaSplitInBrack);
						if(sibling.contains("(")) {
							converStrToListWithBrack(sibling);	
						}
							System.out.println("sibling--: " + sibling);
							System.out.println("m: " + formulaSplitInBrack);
							System.out.println("indexOpen2: " + indexOfOpen);
							System.out.println("indexClose2: " + indexOfClose);
							division(sibling);
							multiplication(sibling);
							addition(sibling);
							subtraction(sibling);
							int indClose = indexOfClose;
							int indOpen = indexOfOpen;
							System.out.println("sibling--: " + sibling);
//							System.out.println("indOpen: " + indOpen);
//							System.out.println("indClose: " + indClose);
							
							formulaSplitInBrack.subList(indOpen + 1, indClose).clear();
							System.out.println("new formula: " + formulaSplitInBrack);
							formulaSplitInBrack.addAll(indOpen + 1, sibling);
							System.out.println("new formula2: " + formulaSplitInBrack);

							System.out.println("m: " + formulaSplitInBrack);
							System.out.println("sibling---: " + sibling);
					
							
							if (sibling.size() == 1 ) {
								if( indexOfOpen > 0) {
								if(!formulaSplitInBrack.get(indOpen - 1).equals("*") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("-") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("+") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("/") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("^")) {
									double numInBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen - 1));
									double numOutBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen + 1));
									numOutBrack = numInBrack * numOutBrack;
									String changes = Double.toString(numOutBrack);
									formulaSplitInBrack.subList(indOpen - 1, indOpen + 3).clear();
									formulaSplitInBrack.add(indexOfOpen - 1, changes);
								
									
								}
								
								}
								else {
									formulaSplitInBrack.remove("(");
									formulaSplitInBrack.remove(")");
								}

							}
							System.out.println("-------formslpit: " + formulaSplitInBrack);
							if(formulaSplitInBrack.contains("(")) {
								 converStrToListWithBrack(formulaSplitInBrack);
							}

						}
	
				}
				}
			
			System.out.println("this: " + formulaSplitInBrack);

				}
		

		if (!formulaSplitInBrack.contains("(")) {
			division(formulaSplitInBrack);
			multiplication(formulaSplitInBrack);
			addition(formulaSplitInBrack);
			subtraction(formulaSplitInBrack);
		}
		

		return formulaSplitInBrack;
	}
			
	
			
	
	// from the order BIDMAS, it looks for the division symbol and divides the numbers before and after the "/" symbol
	public void division(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while (m.get(i).equals("/")) {
				int indexOfThatSymbol = m.indexOf("/");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 / num2);
				String changed = Double.toString(num3);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
				}	
		}
	}
	//it looks for the division symbol and multiplies the numbers before and after the "*" symbol
	public void multiplication(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while (m.get(i).equals("*")) {
				int indexOfThatSymbol = m.indexOf("*");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 * num2);
				String changed = Double.toString(num3);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
				}	
		}
	}
	//it looks for the division symbol and adds the numbers before and after the "+" symbol
	public void addition(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while(m.get(i).equals("+")) {
				int indexOfThatSymbol = m.indexOf("+");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 + num2);
				String changed = Double.toString(num3);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
				}	
		}
	}
	//it looks for the division symbol and subtracts the numbers before and after the "-" symbol
	public  void subtraction(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while(m.get(i).equals("-")) {
				int indexOfThatSymbol = m.indexOf("-");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 - num2);
				String changed = Double.toString(num3);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
				}	
		}
	}
	public void power(ArrayList<String> m) {
		for (int i = 0; i <= m.size() - 1 ; i++) {
			while(m.get(i).contentEquals("^")) {
				int indexOfThatSymbol = m.indexOf("^");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = Math.pow(num1, num2);
				String changed = Double.toString(num3);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
			}
		}
	}
	
	public String run(String testCase) throws Exception{

		try {
		CalculatorV5 calc = new CalculatorV5();
		testCase = testCase.trim();
		calc.converStrToListNoBrack(testCase);
		

		if(calc.formulaSplitInBrackets.contains("(")) {
			calc.formulaSplitAll.clear();
			while (calc.formulaSplitInBrackets.contains("(")) {
				System.out.println("-------------------------------");
				calc.converStrToListWithBrack(calc.formulaSplitInBrackets);
//				System.out.println("calcForm: "+ calc.formulaSplitInBrackets);
				
				
			}
		}
		
		if(calc.formulaSplitInBrackets.size() == 1) {
			for(String b : calc.formulaSplitInBrackets)
			calc.formulaSplitAll.add(b);
		}
		System.out.println("formulaSplitAll: " +calc.formulaSplitAll);
		calc.power(calc.formulaSplitAll);
		calc.division(calc.formulaSplitAll);
		calc.multiplication(calc.formulaSplitAll);
		calc.addition(calc.formulaSplitAll);
		calc.subtraction(calc.formulaSplitAll);
		double num = 0;
		int num2 = 0;
		// gives a cleaner output with either an integer value or double 
		for (String b : calc.formulaSplitAll) {
			num = Double.parseDouble(b);
			
			if(num % 1 == 0) {
				num2 = (int) num;
				String num_dis = Integer.toString(num2);
				return num_dis;

				
			}
			else {
				String num_dat = String.format("%.5f", num);
				return num_dat;
				
			}
			
			
		}
		return"Invalid";

	}
		catch(Exception e){
			return "Invalid";
		}

} 
}

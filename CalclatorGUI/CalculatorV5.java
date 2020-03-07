package engine;

import java.util.ArrayList;

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
			if (formulaSplitAll.get(i).equals("(")) {
				counter = counter + 1;
			}
		}
		return formulaSplitAll;
	}
	
	public ArrayList<String> converStrToListWithBrack(ArrayList<String> formulaSplitAll) {
		
		
		while (counter != 0) {
			int indexOfOpen = formulaSplitAll.indexOf("(");;
			int indexOfClose = formulaSplitAll.indexOf(")");
			System.out.println(indexOfOpen);
			System.out.println(indexOfClose);
			formulaSplitInBrackets.clear();
			
			//add elements stored in the brackets from the "formulaSplitAll" array list into "formulaSplitInBrackets" 
			for (int i= indexOfOpen ; i< indexOfClose + 1; i++ ) {
				formulaSplitInBrackets.add(formulaSplitAll.get(i));
			}
			System.out.println(formulaSplitAll);
			System.out.println(formulaSplitInBrackets);
			// do the necessary calculations of the equations 
			division(formulaSplitInBrackets);
			multiplication(formulaSplitInBrackets);
			addition(formulaSplitInBrackets);
			subtraction(formulaSplitInBrackets);
			System.out.println(formulaSplitInBrackets);
		

			// clear out the equation stored in the brackets in the formulaSplitAll array list 
			formulaSplitAll.subList(indexOfOpen, indexOfClose +1 ).clear();

			// from the formulaSplitInBrackets, the calculated value from the brackets is added to the formulaSplitAll list

			formulaSplitInBrackets.remove("(");
			formulaSplitInBrackets.remove(")");
			System.out.println(formulaSplitInBrackets);
			System.out.println(formulaSplitAll);
			
			for (int j = 0 ; j <= formulaSplitInBrackets.size() - 1; j ++) {
				formulaSplitAll.add(indexOfOpen , formulaSplitInBrackets.get(j));;
			}
			
			System.out.println(formulaSplitAll);
			System.out.println(indexOfOpen);
			System.out.println("size" + formulaSplitAll.size());
			for (int j = 0; j <= formulaSplitAll.size() - 1 ; j++) {
				if (indexOfOpen > 0 ) {
					if (!formulaSplitAll.get(indexOfOpen - 1).equals("+") && 
							!formulaSplitAll.get(indexOfOpen - 1).equals("/") &&
							!formulaSplitAll.get(indexOfOpen - 1).equals("*") && 
							!formulaSplitAll.get(indexOfOpen - 1).equals("-") && 
							!formulaSplitAll.get((indexOfOpen) - 1).equals("^")){
						double numInBrack = Double.parseDouble(formulaSplitAll.get(indexOfOpen));
						System.out.println(numInBrack);
						double numOutBrack = Double.parseDouble(formulaSplitAll.get(indexOfOpen - 1));
						numOutBrack = numInBrack * numOutBrack;
						System.out.println(numOutBrack);
						String changes = Double.toString(numOutBrack);
						System.out.println(formulaSplitAll);
						formulaSplitAll.subList(indexOfOpen - 1, indexOfOpen + 1).clear();
						formulaSplitAll.add(indexOfOpen - 1, changes);
						System.out.println(formulaSplitAll);
						indexOfOpen = 0;
					}
				}
			}

			// if there is a number before the opening brackets, it multiplies the number with the calculated value in brackets 
			// e.g if equation is ; 2(2), output will be 4 ( multiples 2 by 2 )			
			// remove the opening and closing brackets symbol

			System.out.println(formulaSplitAll);
			counter = counter - 1;

			
		}
		return formulaSplitAll;
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
	
	public double run(String testCase){

		
		CalculatorV5 calc = new CalculatorV5();
		testCase = testCase.trim();
		calc.converStrToListNoBrack(testCase);
		
//		System.out.println(calc.counter);
		//look for if the formulaSplitAll array list has any brackets operator, and if it does, carry out the function with the brackets 
		for (int i = 0; i <= calc.formulaSplitAll.size() - 1 ; i ++ ) {
			while (calc.formulaSplitAll.contains("(")) {
				calc.converStrToListWithBrack(calc.formulaSplitAll);
			}
		}
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
				return num2;
//				System.out.println("Final answer: " + num2);
				
			}
			else {
//				System.out.println("Final answer: " + num);
				return num;
				
			}
			
			
		}
		return 0;
		
		
	}
	
}  

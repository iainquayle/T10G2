package engine;

import java.util.ArrayList;

public class CalculatorV2 {
	static ArrayList<String> formulaSplitAll = new ArrayList<String>();
	static String[] ArrayForm;
	static ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
	static ArrayList<String> formulaSplitOutBrackets = new ArrayList<String>();
	static String caldNum = null;
	
	public static ArrayList<String> converStrToList(String testCase){
		ArrayForm = testCase.split(" ");
//		ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
		for (int i = 0; i < ArrayForm.length; i++ ) {
			formulaSplitAll.add(ArrayForm[i]);
		}
		return formulaSplitAll;
	}

	
	public static void division(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while (m.get(i).equals("/")) {
				int indexOfThatSymbol = m.indexOf("/");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 / num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				System.out.println(m);
				i = 0;
				}	
		}
	}
	
	public static void multiplication(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while (m.get(i).equals("*")) {
				int indexOfThatSymbol = m.indexOf("*");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 * num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				System.out.println(m);
				i = 0;
				}	
		}
	}
	
	public static void addition(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while(m.get(i).equals("+")) {
				int indexOfThatSymbol = m.indexOf("+");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 + num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				System.out.println(m);
				i = 0;
				}	
		}
	}
	public static void subtraction(ArrayList<String> m) {
		for (int i = 0 ; i <= m.size() - 1; i ++) {
			while(m.get(i).equals("-")) {
				int indexOfThatSymbol = m.indexOf("-");
				double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
				double num3 = (num1 - num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				System.out.println(m);
				i = 0;
				}	
		}
	}
	
	public static void main(String[] args) {
		String testCase = "4 + 2 - 2 * 10 + 4 - 10 + 100 - 9 * 5";
		ArrayList<String> formulaSplit = converStrToList(testCase);
		System.out.println(formulaSplit);
		division(formulaSplit);
		multiplication(formulaSplit);
		addition(formulaSplit);
		subtraction(formulaSplit);
		System.out.println(formulaSplit);
		
		
	}
	
}

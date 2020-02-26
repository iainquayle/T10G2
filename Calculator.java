package engine;

import java.util.ArrayList;
import java.util.Scanner;

public class CalculatorV3 {
	static ArrayList<String> formulaSplitAll = new ArrayList<String>();
	static String[] ArrayForm;
	static ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
	static ArrayList<String> formulaSplitOutBrackets = new ArrayList<String>();
	
	public static ArrayList<String> converStrToListNoBrack(String testCase){
		ArrayForm = testCase.split(" ");
		for (int i = 0; i < ArrayForm.length; i++ ) {
			formulaSplitAll.add(ArrayForm[i]);
		}
		return formulaSplitAll;
	}
	
	public static ArrayList<String> converStrToListWithBrack(ArrayList<String> formulaSplitAll) {

		System.out.println(formulaSplitInBrackets);
		int indexOfOpen = formulaSplitAll.indexOf("(");;
		int indexOfClose = formulaSplitAll.indexOf(")");
		
		for (int i= indexOfOpen ; i<= indexOfClose; i++ ) {
			formulaSplitInBrackets.add(formulaSplitAll.get(i));
		}
		
		division(formulaSplitInBrackets);
		multiplication(formulaSplitInBrackets);
		addition(formulaSplitInBrackets);
		subtraction(formulaSplitInBrackets);

		
		formulaSplitAll.subList(indexOfOpen, indexOfClose + 1).clear();
		
		for(String g : formulaSplitInBrackets) {
			formulaSplitAll.add(g);
//			double num3 = Double.parseDouble(g);
		}
		System.out.println(formulaSplitAll);
		for (int j = 0; j <= formulaSplitAll.size() - 1; j++) {
			while(formulaSplitAll.get(j).equals("(")) {
				int indexOfThatSymbol = formulaSplitAll.indexOf("(");
				System.out.println(indexOfThatSymbol);
				double num3 = Double.parseDouble(formulaSplitAll.get(indexOfThatSymbol + 1));
//				double num2 = Double.parseDouble(formulaSplitAll.get(indexOfThatSymbol + 1));
				if(indexOfThatSymbol > 0) {
				if (formulaSplitAll.get(indexOfThatSymbol - 1).equals("+")) {
//					|| formulaSplitAll.get(indexOfThatSymbol - 1) == "*" || formulaSplitAll.get(indexOfThatSymbol - 1) == "/") {
					double num1 = Double.parseDouble(formulaSplitAll.get(indexOfThatSymbol - 2));
					System.out.println(num1);
//					if (formulaSplitAll.get(indexOfThatSymbol - 1) == "+") {
						num3 = num1 + num3;
					System.out.println(num3);
				}
			
				else{
					double num1 = Double.parseDouble(formulaSplitAll.get(indexOfThatSymbol - 1));
					num3 = (num1 * num3);
					}
				}
				System.out.println(num3);
				System.out.println(formulaSplitAll);
				formulaSplitAll.subList(indexOfThatSymbol , indexOfThatSymbol + 3).clear();
				String changed = Double.toString(num3);
				System.out.println(changed);
				System.out.println(formulaSplitAll);
				formulaSplitAll.subList((indexOfThatSymbol - 2), (indexOfThatSymbol)).clear();
				System.out.println(formulaSplitAll);
				formulaSplitAll.add(changed);
				System.out.println(formulaSplitAll);
				formulaSplitAll.remove(")");
				j = 0;
			}
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
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
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
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
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
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
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
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
				i = 0;
				}	
		}
	}
	
	public static void main(String[] args) {
		Scanner inputFromUser = new Scanner(System.in);
		System.out.print("Enter a string with spaces in between operators: ");
		String testCase = inputFromUser.nextLine();
		
		ArrayList<String> formulaSplitAll = converStrToListNoBrack(testCase);
		for (String g : formulaSplitAll) {
			if(formulaSplitAll.contains("(")) {
				ArrayList<String> formulaSplitInBrackets = converStrToListWithBrack(formulaSplitAll);
			}
		}
		division(formulaSplitAll);
		multiplication(formulaSplitAll);
		addition(formulaSplitAll);
		subtraction(formulaSplitAll);
		double num = 0;
		int num2 = 0;
		for (String b : formulaSplitAll) {
			num = Double.parseDouble(b);
			if(num % 1 == 0) {
				num2 = (int) num;
				System.out.println("Final answer: " + num2);
			}
			else {
				System.out.println("Final answer: " + num);
			}
			
		}
		
		
	}
	
}

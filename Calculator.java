

import java.util.ArrayList;

public class Calculator{
	public static void main(String[] args) {
		String testCase1 = "2 + 2 + 2 + 3";
		String[] ArrayForm = testCase1.split(" ");
		ArrayList<String> formulaSplit = new ArrayList<String>();
		for (int i = 0; i < ArrayForm.length; i++ ) {
			formulaSplit.add(ArrayForm[i]);
//			System.out.println(formulaSplit);
		}
		System.out.println(formulaSplit);

		for (int i = 0 ; i <= ArrayForm.length - 1; i ++) {
			if (ArrayForm[i].equals("/")) {
				int indexOfThatSymbol = formulaSplit.indexOf("/");
				double num1 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol + 1));
				double num3 = (num1 / num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				formulaSplit.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				formulaSplit.add(indexOfThatSymbol - 1, changed);
				}	
		}
		System.out.println(formulaSplit);
		for (int i = 0 ; i <= ArrayForm.length - 1; i ++) {
			if (ArrayForm[i].equals("*")) {
				int indexOfThatSymbol = formulaSplit.indexOf("*");
				double num1 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol - 1));
				double num2 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol + 1));
				double num3 = (num1 * num2);
				String changed = Double.toString(num3);
//				System.out.println(num3);
//				System.out.println(num1);
//				System.out.println(num2);
				formulaSplit.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				formulaSplit.add(indexOfThatSymbol - 1, changed);
				}	
		}
		System.out.println(formulaSplit);
		for (int i = 0 ; i <= ArrayForm.length - 1; i ++) {
			if (ArrayForm[i].equals("+")) {
				int indexOfThatSymbol = formulaSplit.indexOf("+");
				double num4 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol - 1));
				double num5 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol + 1));
				double num6 = (num4 + num5);
				String changed = Double.toString(num6);
//				System.out.println(num6);
//				System.out.println(num4);
//				System.out.println(num5);
				formulaSplit.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				formulaSplit.add(indexOfThatSymbol - 1, changed);
				}		
		}
		System.out.println(formulaSplit);
			
		for (int i = 0 ; i <= ArrayForm.length - 1; i ++) {
			if (ArrayForm[i].equals("-")) {
				int indexOfThatSymbol = formulaSplit.indexOf("-");
				double num4 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol - 1));
				double num5 = Double.parseDouble(formulaSplit.get(indexOfThatSymbol + 1));
				double num6 = (num4 -num5);
				String changed = Double.toString(num6);
//				System.out.println(num6);
//				System.out.println(num4);
//				System.out.println(num5);
				formulaSplit.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				formulaSplit.add(indexOfThatSymbol - 1, changed);
				}	
		}
				
		System.out.println(formulaSplit);

		
	}
}











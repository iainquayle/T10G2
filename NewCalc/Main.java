import java.util.Scanner;

public class Main {
	
	
	public String run(String testCase) {
		@SuppressWarnings("resource")
//		Scanner inputFromUser = new Scanner(System.in);
//		System.out.print("Enter a string with spaces in between operators: ");
//		String testCase = inputFromUser.nextLine();
		Equation A = new Equation(testCase);
		Brackets conv = new Brackets(testCase);
		
		conv.setFormulaSplitInBrackets();
		if (conv.getFormulaSplitInBrackets().contains("(")) {
			while (conv.getFormulaSplitInBrackets().contains("(")) {
				conv.converStrToListWithBrack(conv.getFormulaSplitInBrackets());	
			}
			for(String b : conv.getFormulaSplitInBrackets()) {
				return b;
			}
	
		}
		else {
		return Run.run(testCase, A.getFormulaSplitAll());
		
		
	}
		return "Inval";
	}
}

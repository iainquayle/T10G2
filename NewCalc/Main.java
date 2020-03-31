

public class Main {
	
	
	public String run(String testCase) throws Exception {
		try {
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
		}
		catch(Exception e) {
			return "Inval";
		}
		return null;
		
	}
}

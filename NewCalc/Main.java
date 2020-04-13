

public class Main {
	
	
	/**
	 * Calculator main process
	 * checks if the equation has a bracket or not and then deals with it accordingly
	 * @param testCase string containing the the equation
	 * @return the result
	 * @throws Exception
	 */
	public String run(String testCase) {
		try {
		Equation A = new Equation(testCase);
		Brackets conv = new Brackets(testCase);
		
		conv.setFormulaSplitInBrackets();
		if (conv.getFormulaSplitInBrackets().contains("(")) {
				conv.converStrToListWithBrack(conv.getFormulaSplitInBrackets());
			if (conv.getFormulaSplitInBrackets().size() == 1) {
			for(String b : conv.getFormulaSplitInBrackets()) {
				return b;
			}
			}
			else {
				return "Invalid";
			}
	
		}
		else {
		return Run.run(A.getFormulaSplitAll());
		
		
	}
		}
		catch(Exception e) {
			return "Invalid";
		}
		return null;
		
	}
}
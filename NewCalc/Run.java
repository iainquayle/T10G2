import java.util.ArrayList;

public class Run extends Operators {
	
	static double finalAnswer = 0;

	public Run(String testCase) {
		super(testCase);
		// TODO Auto-generated constructor stub
	}


	@Override
	public ArrayList<String> operation(ArrayList<String> m) {
		// TODO Auto-generated method stub
		return null;
	}
	
 
	public static String run(String test1, ArrayList<String> l ) {
		
		try {
		double finalAnswer = 0.0;
		
		Operators firstOperator = new Power(test1);
		firstOperator.calculate();
		
		
		Operators secondOperator = new Divide(test1);
		secondOperator.calculate();
		
		Operators thirdOperator = new Multiply(test1);
		thirdOperator.calculate();
		
		Operators fourthOperator = new Plus(test1);
		fourthOperator.calculate();

		
		
		Operators fifthOperator = new Minus(test1);
		fifthOperator.calculate();
		
		if(firstOperator.operation(l).size() == 1){
			for(String b : firstOperator.operation(l)) {
				finalAnswer = Double.parseDouble(b);

			}
	}
		
		else if(secondOperator.operation(l).size() == 1 ) {
			for(String b : secondOperator.operation(l)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(thirdOperator.operation(l).size() == 1 ) {
			for(String b : thirdOperator.operation(l)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(fourthOperator.operation(l).size() == 1 ) {
			for(String b : fourthOperator.operation(l)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(fifthOperator.operation(l).size() == 1 ) {
			for(String b : fifthOperator.operation(l)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		int finalAnswer_2 = 0 ;
		if (finalAnswer % 1 == 0) {
			finalAnswer_2 = (int) finalAnswer;
			String output = Integer.toString(finalAnswer_2);
			return output;
		}
		
		else {
			String output = String.format("%.3f", finalAnswer);
			return output;
		
		}
		}
		catch(Exception e) {
			return "Invalid";
		}
					
		
}

}

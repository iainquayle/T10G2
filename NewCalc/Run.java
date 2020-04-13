import java.util.ArrayList;

public class Run {
	
/**
 * runs the calculation processes
 * @param test1 string with equation
 * @param equation list containing the equation
 * @return the answer to the equation
 * @throws Exception
 */
 
public static String run( ArrayList<String> equation ) throws Exception {
		
		try {
		double finalAnswer = 0.0;
		
		if(equation == null)
		{
			System.out.println("pop");
			return "Invalid";
		
		}
		else {
		Operators firstOperator = new Power();
		firstOperator.operation(equation);
		
		Operators secondOperator = new Divide();
		secondOperator.operation(equation);
		
		Operators thirdOperator = new Multiply();
		thirdOperator.operation(equation);
		
		Operators fourthOperator = new Plus();
		fourthOperator.operation(equation);

		Operators fifthOperator = new Minus();
		fifthOperator.operation(equation);
		
		if(firstOperator.operation(equation).size() == 1){
			for(String b : firstOperator.operation(equation)) {
				finalAnswer = Double.parseDouble(b);

			}
	}
		
		else if(secondOperator.operation(equation).size() == 1 ) {
			for(String b : secondOperator.operation(equation)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(thirdOperator.operation(equation).size() == 1 ) {
			for(String b : thirdOperator.operation(equation)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(fourthOperator.operation(equation).size() == 1 ) {
			for(String b : fourthOperator.operation(equation)) {
				finalAnswer = Double.parseDouble(b);

			}
		}
		
		else if(fifthOperator.operation(equation).size() == 1 ) {
			for(String b : fifthOperator.operation(equation)) {
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
		}
		
		catch(Exception e) {
			throw new Exception("Invalid");
		}
					
		
}

}

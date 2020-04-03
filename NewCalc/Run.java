import java.util.ArrayList;

public class Run {
	
/**
 * runs the calculation processes
 * @param test1 string with equation
 * @param l list containing the equation
 * @return the answer to the equation
 * @throws Exception
 */
 
	public static String run(String test1, ArrayList<String> l ) throws Exception {
		
		try {
		double finalAnswer = 0.0;
		
		Operators firstOperator = new Power();
		firstOperator.operation(l);
		
		Operators secondOperator = new Divide();
		secondOperator.operation(l);
		
		Operators thirdOperator = new Multiply();
		thirdOperator.operation(l);
		
		Operators fourthOperator = new Plus();
		fourthOperator.operation(l);

		Operators fifthOperator = new Minus();
		fifthOperator.operation(l);
		
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
			return "Invalid_Run";
		}
					
		
}

}

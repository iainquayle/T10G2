import java.util.ArrayList;

public class Run extends Operators {
	
	static double finalAnswer = 0;

	public Run(String testCase) {
		super(testCase);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<String> operation() {
		// TODO Auto-generated method stub
		return null;
	}
	
 
	public static void main(String[] args) {
		String test1 = "4 ^ 2 + 5 - 8 + 6 / 3";
		test1.trim();
		
		Operators firstOperator = new Power(test1);
		firstOperator.createList();
		firstOperator.calculate();
		
		Operators secondOperator = new Divide(test1);
		secondOperator.calculate();
		
		Operators thirdOperator = new Multiply(test1);
		thirdOperator.calculate();
		
		Operators fourthOperator = new Plus(test1);
		fourthOperator.calculate();
		
		Operators fifthOperator = new Minus(test1);
		fifthOperator.calculate();
		
		if(firstOperator.operation().size() == 1 ) {
			for(String b : firstOperator.operation()) {
				finalAnswer = Double.parseDouble(b);
				System.out.println(finalAnswer);
			}
	}
		
		else if(secondOperator.operation().size() == 1 ) {
			for(String b : secondOperator.operation()) {
				finalAnswer = Double.parseDouble(b);
				System.out.println(finalAnswer);
			}
		}
		
	
		else if(thirdOperator.operation().size() == 1 ) {
			for(String b : thirdOperator.operation()) {
				finalAnswer = Double.parseDouble(b);
				System.out.println(finalAnswer);
			}
		}
		
		else if(fourthOperator.operation().size() == 1 ) {
			for(String b : fourthOperator.operation()) {
				finalAnswer = Double.parseDouble(b);
				System.out.println(finalAnswer);
			}
		}
		
		else if(fifthOperator.operation().size() == 1 ) {
			for(String b : fifthOperator.operation()) {
				finalAnswer = Double.parseDouble(b);
				System.out.println(finalAnswer);
			}
		}
		
		if (finalAnswer % 1 == 0) {
			finalAnswer = (int) finalAnswer;
			System.out.println(finalAnswer);
			
		}
		
		else {
		}
		
		
			
			
		
		
		
}
}

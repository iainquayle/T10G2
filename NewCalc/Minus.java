import java.util.ArrayList;

public class Minus extends Operators{

	/**
	 * This method checks if the formula has a minus symbol
	 * It subtracts what's before and after the minus symbol and replaces them with the result
	 * @return the updated arraylist
	 */
	public ArrayList<String> operation(ArrayList<String> m ) {

		while(m.contains("-")) {
			int indexOfThatSymbol = m.indexOf("-");
			double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
			double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
			double num3 = (num1 - num2);
			String changed = Double.toString(num3);
			m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
			m.add(indexOfThatSymbol - 1, changed);
		}
		return m;
		
	}

	
}

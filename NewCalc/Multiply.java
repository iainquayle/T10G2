import java.util.ArrayList;

public class Multiply extends Operators{


	/**
	 * This method checks if the formula has a multiplication symbol
	 * It multiplies what's before and after the multiplication symbol and replaces them with the product
	 * @return the updated arraylist
	 */
	
	
	public ArrayList<String> operation(ArrayList<String> m) {

		while(m.contains("*")) {
			int indexOfThatSymbol = m.indexOf("*");
			double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
			double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
			double num3 = (num1 * num2);
			String changed = Double.toString(num3);
			m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
			m.add(indexOfThatSymbol - 1, changed);
		}
		return m;

}
}

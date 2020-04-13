import java.util.ArrayList;

public class Power extends Operators{

	/**
	 * This method checks if the formula has an exponent symbol
	 * It exponentiates the number before the symbol to the number after and replaces that with the result.
	 * @return the updated arraylist
	 */
	public ArrayList<String> operation(ArrayList<String> m) {

		while(m.contains("^")) {
			
			int indexOfThatSymbol = m.indexOf("^");
			
			double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
			double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
			double num3 = (Math.pow(num1, num2));
			if(num3 % 1 == 0) 
			{
				String changed = Integer.toString((int) num3);
				
				m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
				m.add(indexOfThatSymbol - 1, changed);
			
			}
			
			else 
			{
			
			m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
			m.add(indexOfThatSymbol - 1, String.format("%.5f", num3));
			}
		}
		
		return m;

}
	
}
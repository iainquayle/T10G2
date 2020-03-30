import java.util.ArrayList;

public class Power extends Operators{

	public Power(String testCase) {
		super(testCase);
		// TODO Auto-generated constructor stub
	}

	
	public ArrayList<String> operation(ArrayList<String> m) {

		while(m.contains("^")) {
			int indexOfThatSymbol = m.indexOf("^");
			double num1 = Double.parseDouble(m.get(indexOfThatSymbol - 1));
			double num2 = Double.parseDouble(m.get(indexOfThatSymbol + 1));
			double num3 = (Math.pow(num1, num2));
			String changed = Double.toString(num3);
			m.subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
			m.add(indexOfThatSymbol - 1, changed);
		}
		return m;

}
}

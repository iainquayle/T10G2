import java.util.ArrayList;

public class Power extends Operators{

	public Power(String testCase) {
		super(testCase);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<String> operation() {

		while(super.getCopy().contains("^")) {
			int indexOfThatSymbol = super.getCopy().indexOf("^");
			double num1 = Double.parseDouble(super.getCopy().get(indexOfThatSymbol - 1));
			double num2 = Double.parseDouble(super.getCopy().get(indexOfThatSymbol + 1));
			double num3 = (Math.pow(num1, num2));
			String changed = Double.toString(num3);
			super.getCopy().subList((indexOfThatSymbol - 1), (indexOfThatSymbol + 2)).clear();
			super.getCopy().add(indexOfThatSymbol - 1, changed);
		}
		return super.getCopy();

}
}

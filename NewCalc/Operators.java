import java.util.ArrayList;
/**
 * Abstract class for use by the operator classes
 * @author Parth Patel
 * @documenter
 *
 */
public abstract class Operators{
	
	/**
	 * abstract method for use by the operators
	 * @param m the list containing the formulas
	 * @return usually would be the result of the operation
	 */
	public abstract ArrayList<String> operation(ArrayList<String> m );
	
}

package engine;

/**
 * Various methods and activation functions for the neurons
 * @author Iain Quayle
 * @documenter Osama Bamatraf
 *
 */
public class Functions 
{
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float sigmoid(float n)
	{
		n = clip((float)1.0E6, (float)-1.0E6, n);
		return n/(float)Math.sqrt(1 + n * n);
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float sigmoidZer(float n)
	{
		return (sigmoid(n) + (float)1) / (float)2;
	}
	
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float relu(float n)
	{
		if(n >= 0)
			return n;
		else
			return (float)0;
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float leakyRelu(float n)
	{
		if(n >= 0)
			return n;
		else
			return (float)0.001 * n;
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float leakyRelu6(float n)
	{
		if(n >= 1)
			return (float)0.001 * (n - 6) + 6;
		else if(n >= 0)
			return n;
		else
			return (float)0.001 * n;
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float relu6(float n)
	{
		return clip(6, 0, n);
	}
	/**
	 * 
	 * @param upper
	 * @param lower
	 * @param n
	 * @return
	 */
	final public static float clip(float upper, float lower, float n)
	{
		if(n >= upper)
		{
			return upper;
		}
		else if(n <= lower)
		{
			return lower;
		}
		else
		{
			return n;
		}
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float stepNeg(float n)
	{
		if(n >= 0)
			return (float)1;
		else
			return (float)-1;
	}
	/**
	 * 
	 * @param n
	 * @return
	 */
	final public static float stepZer(float n)
	{
		if(n >= 0)
			return (float)1;
		else
			return (float)0;
	}
}

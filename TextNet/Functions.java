package engine;

public class Functions 
{
	final public static int padNorm(int n)
	{
		if(n >= 0)
			return n;
		else
			return 0;
	}
	final public static float sigmoid(float n)
	{
		return (float)(n/Math.sqrt(1 + n * n));
	}
	final public static float sigmoidZer(float n)
	{
		return sigmoid(n) / 2 + 1;
	}
	final public static float relu(float n)
	{
		if(n >= 0)
			return n;
		else
			return (float)0;
	}
	final public static float leakyRelu(float n)
	{
		if(n >= 0)
			return n;
		else
			return (float)0.01 * n;
	}
	final public static float stepNeg(float n)
	{
		if(n >= 0)
			return (float)1;
		else
			return (float)-1;
	}
	final public static float stepZer(float n)
	{
		if(n >= 0)
			return (float)1;
		else
			return (float)0;
	}
}

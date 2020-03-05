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
		//n *= 8;
		return n/(float)Math.sqrt(1 + n * n);
	}
	final public static float sigmoidZer(float n)
	{
		return (sigmoid(n) + (float)1) / (float)2;
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
	final public static float leakyRelu6(float n)
	{
		if(n >= 6)
			return (float)6;
		else if(n >= 0)
			return n;
		else
			return (float)0.01 * n;
	}
	final public static float relu6(float n)
	{
		if(n >= 6)
			return (float)6;
		else if(n >= 0)
			return n;
		else
			return (float)0;
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

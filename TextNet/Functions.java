package engine;

public class Functions 
{
	final public static float sigmoid(float n)
	{
		n = clip(53822, -53822, n);
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
			return (float)0.001 * n;
	}
	final public static float leakyRelu6(float n)
	{
		if(n >= 1)
			return (float)0.001 * (n - 6) + 6;
		else if(n >= 0)
			return n;
		else
			return (float)0.001 * n;
	}
	final public static float relu6(float n)
	{
		return clip(6, 0, n);
	}
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

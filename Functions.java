package engine;

public class Functions 
{
	public float sigmoid(float n)
	{
		return (float)(n/Math.sqrt(1 + n * n));
	}
	public float relu(float n)
	{
		if(n >= 0)
			return n;
		else
			return (float)0;
	}
	public float step(float n)
	{
		if(n >= 0)
			return (float)1;
		else
			return (float)-1;
	}
}

package engine;

public class Layer 
{
	protected float[] valsAch;
	protected float[] valsReq;
	protected int lenVals;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected float[] valsVisAch;
	protected float[] valsVisReq;
	protected int lenVisVals;
	
	protected float temp;
	
	protected float learnRate;
	
	protected static int i;
	protected static int j;
	protected static int w;
	
	public Layer()
	{
	}
	
	public void eval()
	{
	}
	public void train()
	{
	}
	
	public void setValsRefs(float[] a, float[] r)
	{
	}
	public void setVisRefs(float[] a, float[] r)
	{
	}
	public void setWeightsRef(float[] w)
	{
	}
}

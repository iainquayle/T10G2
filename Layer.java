package engine;

public class Layer 
{
	protected float[] valsAch;
	protected float[] valsReq;
	protected int lenVals;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected float[] visValsAch;
	protected float[] visValsReq;
	protected int lenVisVals;
	
	protected static float[] threadSplits;
	
	protected float learnRate;
	
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

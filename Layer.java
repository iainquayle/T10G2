package engine;

public class Layer 
{
	protected static float[] valsAch;
	protected static float[] valsReq;
	protected int lenVals;
	protected int begVals;
	protected int endVals;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected static float[] visValsAch;
	protected static float[] visValsReq;
	protected int lenValsVis;
	protected int begValsVis;
	protected int endValsVis;
	
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

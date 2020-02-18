package engine;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Layer 
{
	protected static float[] valsAch;
	protected static float[] valsReq;
	protected int lenVals;
	protected int begVals; //beginning index of vals
	protected int endVals; //ending index of vals
	protected int layerNum;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected static float[] visValsAch; //this is now the same pointer as valsAch, but this has been kept for code readability, it may be done away with
	protected static float[] visValsReq; //this is now the same pointer as valsReq, but this has been kept for code readability, it may be done away with
	protected int lenValsVis;
	protected int begValsVis; //beginning index of valsVis
	protected int endValsVis; //ending index of valsVis
	protected int visLayerNum;
	
	protected static float threadSplits; 
	
	protected float learnRate;
	
	public Layer()
	{
	}
	public void init(String loc, Scanner in, int pos) throws IOException
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
		valsAch = a;
		valsReq = r;
		visValsAch = a;
		visValsReq = r;
	}
	public void setWeightsRef(float[] w)
	{
		weights = w;
	}
}

package engine;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Layer 
{
	protected static Integer rndIndex = null;
	
	protected static float[] valsAch = null;
	protected static float[] valsReq = null;
	protected int lenVals = 0;
	protected int begVals = 0; //beginning index of vals
	protected int endVals = 0; //ending index of vals
	protected int layerNum = 0;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected static float[] visValsAch = null; //this is now the same pointer as valsAch, but this has been kept for code readability, it may be done away with
	protected static float[] visValsReq = null; //this is now the same pointer as valsReq, but this has been kept for code readability, it may be done away with
	protected int lenValsVis = 0;
	protected int begValsVis = 0; //beginning index of valsVis
	protected int endValsVis = 0; //ending index of valsVis
	protected int visLayerNum = 0;
	
	protected static float threadSplits; //value for fraction of layer a thread has command of
	
	protected float learnRate;
	
	public Layer()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, int num) throws IOException
	{
	}
	public void save(String loc) throws IOException
	{
		if(weights != null)
		{
			FileWriter file = new FileWriter(loc + "weights" + layerNum);
			String str = null;
			for(int i = 0; i < lenWeis; i++)
			{
				str = str + weights[i];
			}
			file.write(str);
			file.close();
		}
	}
	
	public void eval()
	{
	}
	public void train()
	{
	}
	
	public void setStatRefs(Integer i, float[] a, float[] r)
	{
		rndIndex = i;
		valsAch = a;
		valsReq = r;
		visValsAch = a;
		visValsReq = r;
	}
	public void setWeightsRef(float[] w)
	{
		weights = w;
	}
	public void setIORefs(float[] n)
	{
		
	}
	public int getLayerType()
	{
		return -1;
	}
	public int getLenVals()
	{
		return lenVals;
	}
	public int getBegVals()
	{
		return begVals;
	}
}

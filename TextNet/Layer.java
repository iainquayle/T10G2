package engine;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Layer 
{
	protected static Integer rndIndex = null;
	
	protected static float[] valsAch = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected static float[] valsReq = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected int lenVals = 0;
	protected int begVals = 0; //beginning index of vals
	protected int endVals = 0; //ending index of vals
	protected int layerNum = 0;
	
	protected float[] weights;
	protected int lenWeis;
	
	protected int lenValsVis = 0;
	protected int begValsVis = 0; //beginning index of valsVis
	protected int endValsVis = 0; //ending index of valsVis
	protected int layerVisNum = 0;
	
	protected static float threadSplits; //value for fraction of layer a thread has command of
	
	protected float learnRate;
	
	public Layer()
	{
	}
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		System.out.println("Wrong layer");
	}
	public void save(String loc) throws IOException
	{
		if(weights != null)
		{
			FileWriter file = new FileWriter(loc + "Weights" + layerNum + ".csv", false);
			String str = "";
			for(int i = 0; i < lenWeis; i++)
			{
				str += weights[i] + ",";
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
	
	public void loadWeights(String loc) throws IOException
	{
		File file = new File(loc + "Weights" + layerNum + ".csv");
		if(file.exists())
		{
			InputData wFile = new InputData(loc + "Weights" + layerNum + ".csv");
			weights = wFile.toFloatArray();
			wFile.close();
		}
		else
		{
			Random rnd = new Random();
			weights = new float[lenWeis];
			for(int i = 0; i < lenWeis; i++)
			{
				weights[i] = rnd.nextFloat() * 2 - 1;
			}
		}
	}
	public void setStatRefs(Integer i, float[] a, float[] r)
	{
		rndIndex = i;
		valsAch = a;
		valsReq = r;
	}
	public void setWeightsRef(float[] w)
	{
		weights = w;
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
	public String toString()
	{
		return null;
	}
}

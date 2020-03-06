package engine;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Layer 
{	
	protected static float[] valsAch = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected static float[] valsErr = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected int lenVals = 0; //total length of values ach/Err
	protected int begVals = 0; //beginning index of vals
	protected int endVals = 0; //ending index of vals
	protected int layerNum = 0; //position in net
	
	protected float[] weights;
	protected int lenWeis; //total weights length
	
	protected int lenValsVis = 0; //total length of values ach/Err of vis layer
	protected int begValsVis = 0; //beginning index of valsVis
	protected int endValsVis = 0; //ending index of valsVis
	protected int layerVisNum = 0; //position of vis layer
	
	protected static float learnRate = (float)0.06; //learning adjustment rate
	
	protected static int rndIndex = 0; //random index for io layers picking data from set
	
	protected static float netErr = 0; //average error of the net at the outputs layer (weighted 0.01 to the newest value)
	protected static float netCorr = 0; //average amount of correct guesses (weighted 0.01 to the newest value)
	
	protected static float threadSplits = 1; //value for fraction of layer a thread has command of
	protected static int numThreads = 1; //number of threads working on layers
	
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
				weights[i] = rnd.nextFloat() - (float)(0.5);
			}
		}
	}
	
	public void eval() //virtual method
	{
	}
	public void train() //virtual method
	{
	}
	
	public void setStatRefs(float[] a, float[] r)
	{
		valsAch = a;
		valsErr = r;
	}
	public void setWeightsRef(float[] w)
	{
		weights = w;
	}
	public void setRndIndex(int i)
	{
		rndIndex = i;
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
	public String errString()
	{
		return "Error: " + netErr + "   Correct:" + netCorr; 
	}
}

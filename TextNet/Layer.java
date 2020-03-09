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
	protected int layerNum = 0; //position in sequence of layers
	
	protected float[] weights; //weights specific to the layer, not shared by all layers
	protected int lenWeis; //total weights length
	
	protected int lenValsVis = 0; //total length of values ach/err of vis layer
	protected int begValsVis = 0; //beginning index of valsVis
	protected int endValsVis = 0; //ending index of valsVis
	protected int layerVisNum = 0; //position of vis layer in sequence of layers
	
	protected float learnRate = (float)0.003; //learning adjustment rate, will be default to value here but can be tuned elsewhere
	
	protected static int rndIndex = 0; //random index for io layers to pick data from in the set
	
	protected static float netErr = 0; //average error of the net at the outputs layer (weighted 0.01 to the newest value)
	protected static float netAcc = 0; //average amount of correct guesses (weighted 0.01 to the newest value)
	
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
		if(file.exists()) //checking if weights for this file have been made already
		{
			InputData wFile = new InputData(loc + "Weights" + layerNum + ".csv");
			weights = wFile.toFloatArray();
			wFile.close();
		}
		else //creating new weights if they have not been made yet, may switch to a more stable method of generation later
		{
			Random rnd = new Random();
			weights = new float[lenWeis];
			for(int i = 0; i < lenWeis; i++)
			{
				weights[i] = (rnd.nextFloat() - (float)(0.55)); //slight negative bias seems to result in more stability at start of training for relu
			}
		}
	}
	
	public void eval() //virtual method
	{
	}
	public void train() //virtual method
	{
	}
	
	public void printLargest() //for the purpose of debugging stability of net
	{
		int larPos = 0;
		for(int i = 0; i < lenVals; i++)
		{
			if(valsAch[i] > valsAch[larPos])
			{
				larPos = i;
			}
		}
		System.out.println("(" + valsAch[larPos] + ", " + valsErr[larPos] + ")   ");
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
	public String errString() //prints a weighted average of the net error and accuracy calculated by output layers
	{
		return "Error: " + netErr + "   Accuracy:" + netAcc; 
	}
}

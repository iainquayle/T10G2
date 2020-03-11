package engine;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")

/**
 * This class manages all the layers and is used to make everything work
 * 
 * 
 * @author Iain Quayle
 * @Documenter Osama Bamatraf
 *
 */

public class Layer 
{	
	//Reference for variable naming scheme:
	//Variables are attempted to be named so that the broadest category they fit into comes first.
	//len: (length) prefix to all lengths and dimensionalities of arrays.
	//beg: (beginning) prefix to all starting indexes of layers in the two vals arrays. (also comes in temp form for multiple threads)
	//end: (ending) prefix to the final indexes +1 of layers in the two vals arrays. (also comes in temp form for multiple threads)
	//jump: (jump) prefix for any variable that precalculates a jump between locations that is not a standard length in any array.
	//stop: (stop) prefix for any variable that precalculates a stop index but not the  
	//vals: (values) title for anything to do with either the achieved or error values arrays.
	//ach: (achieved) title for anything to do with achieved values array.
	//err: (error) title for anything to do with error values array.
	//vis: (visible) title for anything to do with the visible layer, the one that the current layer looks at.
	//temp: (temporary) suffix for a variable that does the job of another, or holds a precaculated value for only the methods run time.
	//pos: (position) suffix for a variable that indexs where an algorithm is in an array.
	//num: (number) suffix used to denote the position in an array a layer or thread may hold.
	
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
	
	protected float learnRate = (float)0.00001; //learning adjustment rate, will be default to value here but can be tuned elsewhere
	
	protected static int rndIndex = 0; //random index for io layers to pick data from in the set
	
	protected static float netErr = 0; //average error of the net at the outputs layer (weighted 0.01 to the newest value)
	protected static float netAcc = 0; //average amount of correct guesses (weighted 0.01 to the newest value)
	
	public Layer()
	{
	}
	
	/**
	 * initialization method
	 */
	
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
		System.out.print("Weights - ");
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
				weights[i] = (rnd.nextFloat() - (float)(0.51)); //slight negative bias seems to result in more stability at start of training for relu
			}
		}
		System.out.println("loaded");
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

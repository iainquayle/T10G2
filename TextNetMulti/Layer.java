package engine;

import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public abstract class Layer 
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
	
	protected  static float[] valsAch = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected  static float[] valsErr = null; //vals ref acts for both the loc layer and vis layer, judge what it is being used for by the indexing var
	protected int lenVals = 0; //total length of values ach/Err
	protected int begVals = 0; //beginning index of vals
	protected int endVals = 0; //ending index of vals
	protected int layerNum = 0; //position in net
	
	protected  float[] weights;
	protected int lenWeis; //total weights length
	
	protected int lenValsVis = 0; //total length of values ach/Err of vis layer
	protected int begValsVis = 0; //beginning index of valsVis
	protected int endValsVis = 0; //ending index of valsVis
	protected int layerVisNum = 0; //position of vis layer
	
	protected static float learnRate = (float)0.0001; //learning adjustment rate
	
	protected static int rndIndex = 0; //random index for io layers picking data from set
	
	protected static float netErr = 0; //average error of the net at the outputs layer (weighted 0.01 to the newest value)
	protected static float netAcc = 0; //average amount of Accect guesses (weighted 0.01 to the newest value)
	
	protected static float threadSplits = 1; //value for fraction of layer a thread has command of
	protected static int lenThreads = 1; //number of threads working on layers
	
	public Layer()
	{
	}
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
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
				weights[i] = rnd.nextFloat() - (float)(0.52);
			}
		}
	}
	
	public void eval(int threadNum) //virtual method
	{
	}
	public void norm(int threadNum) //can be over ridden
	{
		
	}
	public void error(int threadNum) //can be over ridden
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenVals);
		
		for(int valsPos = begVals + (int)(threadSplits * threadNum * lenVals); valsPos < endValsTemp; valsPos++)
		{
			//valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]);
			valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]) - valsAch[valsPos]; //step applied to Err vals
		}
	}
	public void train(int threadNum) //virtual method
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
	
	public void setStatics(float[] a, float[] r, int n)
	{
		valsAch = a;
		valsErr = r;
		lenThreads = n;
		threadSplits = (float)1 / n;
	}
	public void setWeightsRef(float[] w)
	{
		weights = w;
	}
	public void setRndIndex(int i)
	{
		rndIndex = i;
	}
	public void setLearnRate(float n)
	{
		learnRate = n;
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
	public float getLearnRate()
	{
		return learnRate;
	}
	
	public String toString()
	{
		return null;
	}
	public String errString()
	{
		return "Error: " + netErr + "   Accuracy:" + netAcc; 
	}
}

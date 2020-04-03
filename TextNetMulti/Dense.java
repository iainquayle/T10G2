package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Dense extends Layer
{
	public Dense()
	{
	}
	
	/**
	 * Initializes the dense layer
	 * 
	 */
	
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num;          //(vis layer num, len vals, lenvisvals)
		layerVisNum = in.nextInt();
		lenVals = in.nextInt();
		lenValsVis = in.nextInt();
		lenWeis = lenVals * lenValsVis;
		super.loadWeights(loc);
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		endVals = begVals + lenVals;
		endValsVis = begValsVis + lenValsVis;
	}
	 /**
	  * This method goes through the visible layer and provides the dot products of the weight and values
	  * Then goes through the relu
	  */
	public void eval(int threadNum)
	{
		int begValsTemp = begVals + (int)(threadSplits * threadNum * lenVals + (float)0.5);
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenVals + (float)0.5);
		int weiPos = (begValsTemp - begVals) * lenValsVis;
		//float valTemp = 0;
		
		for(int valsPos = begValsTemp; valsPos < endValsTemp; valsPos++) //iterate through the vals
		{
			valsAch[valsPos] = 0; //clearing ach vals
			valsErr[valsPos] = 0; //clearing Err vals for the training pass
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]);  //sigmoid activation on value achieve
		}
	}
	
	/**
	 * This method trains the layer  and backpropagates the error
	 * Adjusts the weights
	 */
	public void train(int threadNum) 
	{
		int begValsVisTemp = begValsVis + (int)(threadSplits * threadNum * lenValsVis + (float)0.5);
		int endValsVisTemp = begValsVis + (int)(threadSplits * (threadNum + 1) * lenValsVis + (float)0.5);
		int weiPos = begValsVisTemp - begValsVis;
		int jumpWei = lenValsVis - (endValsVisTemp - begValsVisTemp);
		//float valTemp = 0;
		
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through vals
		{
			for(int valsVisPos = begValsVisTemp; valsVisPos < endValsVisTemp; valsVisPos++) //iterate through vis layer vals
			{
				valsErr[valsVisPos] += valsErr[valsPos] * weights[weiPos]; //backpropagation of error
				weights[weiPos] += valsErr[valsPos] * valsAch[valsVisPos] * learnRate; //adjusting weights based on ach/rew error
				weiPos++;
			}
			weiPos += jumpWei;
		}
	}
	/**
	 * gets the layer type
	 * @return 0 should not be changed
	 */
	public int getLayerType()
	{
		return 0;
	}
	
	/**
	 * converts all the important values to a string
	 * @return
	 */
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

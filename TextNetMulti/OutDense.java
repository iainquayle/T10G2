package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class OutDense extends Layer
{
	private float data[] = null;
	private int dataNum = 0;
	
	public OutDense()
	{
	}
	
	/**
	 * initializes the outDense layer
	 * @param l - layer array
	 * @param loc - location of data
	 * @param in - input data object
	 * @param io - float array of imaga data inputs and labels
	 * @param num - layer reference number
	 * @throws IOException
	 */
	
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num;    //(vis layernum, datanum, len vals, lenvisvals)
		layerVisNum = in.nextInt();
		dataNum = in.nextInt();
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
		data = io[dataNum];
	}
	 /**
	  * This method goes through the visible layer and provides the dot products of the weight and values
	  * Then goes through the relu
	  */
	public void eval(int threadNum)
	{
		int begValsTemp = begVals + (int)(threadSplits * threadNum * lenVals);
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenVals);
		int weiPos = (begValsTemp - begVals) * lenValsVis;
		
		for(int valsPos = begValsTemp; valsPos < endValsTemp; valsPos++) //iterate through the vals
		{
			valsAch[valsPos] = 0;
			valsErr[valsPos] = 0;
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.sigmoidZer(valsAch[valsPos]);  //sigmoid activation on value achieve
		}
	}
	
	/**
	 * this method does error calculations
	 * @param threadNum
	 */
	public void error(int threadNum)
	{
		if(threadNum == 0)
		{
			float aveErr = 0;
			float valsLar = valsAch[begVals];
			int larPos = begVals;
			for(int valsPos = begVals; valsPos < endVals; valsPos++)
			{
				if((int)(data[rndIndex] + 0.5) + begVals != valsPos)
				{
					valsErr[valsPos] = -valsAch[valsPos]; 
				}
				else
				{
					valsErr[valsPos] = (float)1 - valsAch[valsPos];
				}
				aveErr += Math.abs(valsErr[valsPos]);
				if(valsAch[valsPos] > valsLar)
				{
					valsLar = valsAch[valsPos];
					larPos = valsPos;
				}
				netErr = (netErr * (float)0.999) + (aveErr / lenVals * (float)0.001);
				if(larPos == (int)(data[rndIndex] + 0.5) + begVals)
				{
					netAcc = (netAcc * (float)0.999) + (float)0.001;
				}
				else
				{
					netAcc *= (float)0.999;
				}
			}
		}
	}
	
	/**
	 * This method trains the layer  and backpropagates the error
	 * Adjusts the weights
	 */
	
	public void train(int threadNum) 
	{
		int begValsVisTemp = begValsVis + (int)(threadSplits * threadNum * lenValsVis);
		int endValsVisTemp = begValsVis + (int)(threadSplits * (threadNum + 1) * lenValsVis);
		int weiPos = begValsVisTemp - begValsVis;
		int jumpWei = lenValsVis - (endValsVisTemp - begValsVisTemp);
		
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
	 * gets layer type
	 * @return 6 do not change
	 */
	public int getLayerType()
	{
		return 6;
	}
	
	/**
	 * converts the important numbers to string
	 * @return
	 */
	
	public String toString()
	{
		return dataNum + ", " + layerVisNum + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

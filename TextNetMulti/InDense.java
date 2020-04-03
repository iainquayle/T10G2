package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class InDense extends Layer
{
	private float data[] = null;
	private int dataNum = 0;
	
	public InDense()
	{
	}
	
	/**
	 * initializes the inDense layer
	 * @param l - layer array
	 * @param loc - location of data
	 * @param in - input data object
	 * @param io - float array of imaga data inputs and labels
	 * @param num - layer reference number
	 * @throws IOException
	 */
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num;
		dataNum = in.nextInt();
		lenVals = in.nextInt();
		lenValsVis = in.nextInt();
		lenWeis = lenVals * lenValsVis;
		super.loadWeights(loc);
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
		}
		data = io[dataNum];
	}
	 /**
	  * evalutated the begginging and ending values for the data
	  * then goes through the visible layer and provides the dot products of the weight and values
	  * Then goes through the relu
	  */
	public void eval(int threadNum)
	{
		begValsVis = rndIndex * lenValsVis;  //sets the beg and end vals for the data
		endValsVis = begValsVis + lenValsVis; //sets the beg and end vals for the data
		int begValsTemp = begVals + (int)(threadSplits * threadNum * lenVals);
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenVals);
		int weiPos = begValsTemp * lenValsVis;
		
		for(int valsPos = begValsTemp; valsPos < endValsTemp; valsPos++) //iterate through the vals
		{
			valsAch[valsPos] = 0;
			valsErr[valsPos] = 0; //clearing Err vals for the training pass
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += data[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]);  //sigmoid activation on value achieve
		}
	}
	/**
	 * This method trains the layer
	 * Adjusts the weights
	 * Does not backpropagate for error
	 * @param threadNum
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
				weights[weiPos] += valsErr[valsPos] * data[valsVisPos] * learnRate; //adjusting weights based on ach/rew error
				weiPos++;
			}
			weiPos += jumpWei;
		}
	}
	
	/**
	 * gets the layer type
	 * @return 3 do not change
	 */
	public int getLayerType()
	{
		return 3;
	}
	/**
	 * converts the important numbers to string
	 * @return
	 */
	public String toString()
	{
		return dataNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

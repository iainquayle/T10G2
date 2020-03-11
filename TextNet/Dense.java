package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

/**
 * The dense layer is the fully connected layer which  also trains and evaluates
 * In addition, the dense layer is the fully connected layer which would looks at the visible layer 
 * and adjusts weight depending on how close the achieved value and error are
 *
 * @author Iain Quayle
 * @Documenter Osama Bamatraf
 *
 */

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
		System.out.println("here");
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
	public void eval()
	{
		int weiPos = 0;
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through the vals
		{
			valsAch[valsPos] = 0; //clearing ach vals
			valsErr[valsPos] = 0; //clearing Err vals for the training pass
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]);  //relu activation on value achieve
		}
	}
	
		/**
	 * This method trains the layer  and backpropagates the error
	 * Adjusts the weights
	 */
	
	public void train() 
	{
		int weiPos = 0;
		//float errTemp;
		for(int valsPos = begVals; valsPos < endVals; valsPos++)
		{
			//valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]); //step applied to Err vals
			valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]) - valsAch[valsPos];
		}
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through vals
		{
			//errTemp = valsErr[valsPos] - valsAch[valsPos];
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through vis layer vals
			{
				valsErr[valsVisPos] += valsErr[valsPos] * weights[weiPos]; //backpropagation of error
				weights[weiPos] += valsErr[valsPos] * valsAch[valsVisPos] * learnRate; //adjusting weights based on ach/rew error
				weiPos++;
			}
		}
	}
	
	public int getLayerType()
	{
		return 0;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

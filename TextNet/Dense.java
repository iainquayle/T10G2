package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Dense extends Layer
{
	public Dense()
	{
	}
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
			valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]);  //sigmoid activation on value achieve
		}
	}
	public void train() //back pass and train
	{
		int weiPos = 0;
		for(int valsPos = begVals; valsPos < endVals; valsPos++)
		{
			valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]) - valsAch[valsPos]; //step applied to Err vals
		}
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through vals
		{
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through vis layer vals
			{
				valsErr[valsVisPos] += (valsErr[valsPos]) * weights[weiPos]; //backpropagation of error
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

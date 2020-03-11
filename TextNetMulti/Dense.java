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
	
	public void eval(int threadNum)
	{
		int begValsTemp = begVals + (int)(threadSplits * threadNum * lenVals + (float)0.5);
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenVals + (float)0.5);
		int weiPos = (begValsTemp - begVals) * lenValsVis;
		
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
	public void train(int threadNum) //back pass and train
	{
		int begValsVisTemp = begValsVis + (int)(threadSplits * threadNum * lenValsVis + (float)0.5);
		int endValsVisTemp = begValsVis + (int)(threadSplits * (threadNum + 1) * lenValsVis + (float)0.5);
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
	
	public int getLayerType()
	{
		return 0;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

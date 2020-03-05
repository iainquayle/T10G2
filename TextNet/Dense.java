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
			valsReq[valsPos] = 0; //clearing req vals for the training pass
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);  //sigmoid activation on value achieve
		}
	}
	@SuppressWarnings("unused")
	public void train() //back pass and train
	{
		int weiPos = 0;
		for(int valsPos = begVals; valsPos < endVals; valsPos++)
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]); //step applied to req vals
		}
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through vals
		{
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through vis layer vals
			{
				valsReq[valsVisPos] += valsReq[valsPos] * weights[weiPos];  //updating the vis layers requested values
				weights[weiPos] += ((valsReq[valsPos] - valsAch[valsPos]) * valsAch[valsVisPos]) * learnRate;   //updating the weights based on requested values and vis layer achieved values
				weiPos++;
			}
		}
		//Two ways of learning, believe the top will work better for accuracy in the long run
		//((valsReq[valsPos] - valsAch[valsPos]) * valsAch[valsVisPos]) * learnRate;
		//(valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;
	}
	
	public int getLayerType()
	{
		return 0;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + lenVals + ", " + lenValsVis + ", " + lenWeis + "\n";
	}
}

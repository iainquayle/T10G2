package engine;

import java.util.Scanner;
import java.io.File;
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
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num) throws IOException
	{
		layerNum = num;
		dataNum = in.nextInt();
		lenVals = in.nextInt();
		lenValsVis = in.nextInt();
		lenWeis = lenVals * lenValsVis;
		Scanner wFile = new Scanner(new File(loc + "weights" + layerNum));
		weights = new float[lenWeis];
		for(int i = 0; i < lenWeis; i++)
		{
			weights[i] = wFile.nextFloat();
		}
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
		}
		data = io[dataNum];
		wFile.close();
	}
	
	public void eval()
	{
		int weiPos = 0;
		begValsVis = rndIndex * lenValsVis;  //sets the beg and end vals for the data
		endValsVis = begValsVis + lenValsVis; //sets the beg and end vals for the data
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through the vals
		{
			valsAch[valsPos] = 0;
			valsReq[valsPos] = 0; //clearing req vals for the training pass
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through the vis layer vals
			{
				valsAch[valsPos] += data[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);  //sigmoid activation on value achieve
			weiPos++;
		}
	}
	@SuppressWarnings("unused")
	public void train()
	{
		int weiPos = 0;
		for(int valsPos = begVals; valsPos < endVals; valsPos++) //iterate through vals
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]); //step applied to req vals
			for(int valsVisPos = begValsVis; valsVisPos < endValsVis; valsVisPos++) //iterate through vis layer vals
			{
				weights[weiPos] += (valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;   //updating the weights based on requested values and vis layer achieved values
				weiPos++;
			}
		}
	}
	
	public int getLayerType()
	{
		return 3;
	}
	public String toString()
	{
		return null;
	}
}

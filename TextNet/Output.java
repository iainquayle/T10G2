package engine;

import java.util.Scanner;

import engine.Layer;

public class Output extends Layer
{
	float[] output = null;
	int lenOutputJump = 0;
	int outputNum = 0;
	
	public Output()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num)
	{
		layerNum = num;
		visLayerNum = in.nextInt();
		outputNum = in.nextInt();
		lenVals = l[visLayerNum].getLenVals();
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[visLayerNum].getBegVals();
		}
		output = io[outputNum];
	}
	
	public void eval()
	{
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[begVals + i] = visValsAch[begValsVis + i];
		}
	}
	public void train()
	{
		visValsReq[(int)output[rndIndex]] = 1;
	}
	
	public int getLayerType()
	{
		return 3;
	}
}

package engine;

import java.util.Scanner;
import engine.Layer;

public class Input extends Layer
{
	float[] data = null;
	int dataNum = 0;
	
	public Input()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num)
	{
		layerNum = num;
		layerVisNum = in.nextInt();
		lenVals = in.nextInt();
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
		}
		data = io[dataNum];
	}
	
	public void eval()
	{
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[begVals + i] = data[rndIndex * lenValsVis + i];
		}
	}
	public void train() //don't need anything here
	{
	}
	
	public int getLayerType()
	{
		return 2;
	}
}

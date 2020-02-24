package engine;

import java.util.Scanner;
import engine.Layer;

public class Input extends Layer
{
	float[] input = null;
	int lenInputJump = 0;
	
	public Input()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num)
	{
		layerNum = num;
		visLayerNum = in.nextInt();
		lenVals = in.nextInt();
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[visLayerNum].getBegVals();
		}
		input = io[visLayerNum];
	}
	
	public void eval()
	{
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[begVals + i] = input[rndIndex * lenInputJump + i];
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

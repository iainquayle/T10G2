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
	public void init(Layer l, String loc, Scanner in, int pos)
	{
		layerNum = pos;
		lenVals = in.nextInt();
	}
	
	public void eval()
	{
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[begVals + i] = input[rndIndex * lenInputJump + i];
		}
	}
	public void train()
	{
	}
	
	public int getLayerType()
	{
		return 2;
	}
}

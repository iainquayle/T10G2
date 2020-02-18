package engine;

import java.util.Scanner;

import engine.Layer;

public class Output extends Layer
{
	public Output()
	{
	}
	public void init(String loc, Scanner in, int pos)
	{
		layerNum = pos;
		lenVals = in.nextInt();
	}
	
	public void eval()
	{
	}
	public void train()
	{
	}
	
	public int getLayerType()
	{
		return 3;
	}
}

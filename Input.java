package engine;

import java.util.Scanner;
import engine.Layer;

public class Input extends Layer
{
	public Input()
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
		return 2;
	}
}

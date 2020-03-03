package engine;

import java.io.IOException;
import engine.Layer;

public class Output extends Layer
{
	private float[] data = null;
	private int dataNum = 0;
	
	public Output()
	{
	}
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num;
		layerVisNum = in.nextInt();
		dataNum = in.nextInt();
		lenVals = l[layerVisNum].getLenVals();
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		data = io[dataNum];
	}
	
	public void eval()
	{
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[begVals + i] = valsAch[begValsVis + i];
		}
	}
	public void train()
	{
		valsAch[(int)data[rndIndex]] = 1;
	}
	
	public int getLayerType()
	{
		return 5;
	}
}

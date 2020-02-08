package engine;
import engine.Layer;

public class Conv2D extends Layer
{
	int kerDim = 0; //individual kernel dimensions
	int kerNum = 0; //number of kernels/filters/layers
	int stride = 0;
	int lenValsWid = 0;
	int lenValsHei = 0;
	int lenValsImg = 0;
	int lenVisValsLayers = 0;
	int weiLen = 0;
	
	public Conv2D()
	{
	}
	
	public void eval()
	{
		for(int i = 0; i < kerNum; i++)
		{
			for(int j = 0; j < lenValsHei; j++)
			{
				for(int k = 0; k < lenValsWid; k++)
				{
					valsAch[i * lenValsWid + j] = 0;
					for(int l = 0; l < kerDim; k++)
					{
						
					}
				}
			}
		}
	}
	public void train()
	{
	}
	
	public void setValsRefs(float[] a, float[] r)
	{
	}
	public void setVisRefs(float[] a, float[] r)
	{
	}
	public void setWeightsRef(float[] w)
	{
	}
}

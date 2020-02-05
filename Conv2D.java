package engine;
import engine.Layer;

public class Conv2D extends Layer
{
	int kerWid = 0;
	int kerHei = 0;
	int kerNum = 0;
	int stride = 0;
	int lenValsWid = 0;
	int lenValsHei = 0;
	int weiLen = 0;
	
	public Conv2D()
	{
	}
	
	public void eval()
	{
		i = 0;
		w = 0;
		while(i < lenValsHei)
		{
			j = 0;
			while(j < lenValsWid)
			{
				valsAch[i * lenValsWid + j] = 0;
				
				j++;
			}
			i++;
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

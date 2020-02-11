package engine;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	int kerDim = 0; //individual kernel dimensions
	int kerNum = 0; //number of kernels/filters/imgs
	int stride = 0; //stride length between neurons in vis layer
	int jumpBack = 0;
	int lenValsWid = 0; //width of vals
	int lenValsHei = 0; //height of vals
	//int lenValsDep = 0; //depth of vals
	int lenValsLayer = 0; //total length of img, may not use and just go with wid x hei cache hit dependent 
	int lenVisValsWid = 0;
	int lenVisValsHei = 0;
	int lenVisValsDep = 0;
	int lenVisValsLayer = 0;
	int weiLen = 0;
	
	public Conv2D()
	{
	}
	
	public void eval()
	{
		int valPos = 0;
		int kerPos = 0;
		int weiPos = 0;
		for(int i = 0; i < kerNum; i++) //iterate through each weight set/kernel/img
		{
			for(int j = 0; j < lenValsHei; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsWid; k++) //iterate through each row
				{
					valsAch[valPos] = 0;
					weiPos = 0;
					kerPos = j * stride * lenVisValsWid + k * stride;
					for(int l = 0; l < lenVisValsDep; l++) //iterate through each img in the vis layer 
					{
						for(int m = 0; m < kerDim; m++) //iterate through kernel dim
						{
							for(int n = 0; n < kerDim; n++) //iterate through kernel dim
							{
								valsAch[valPos] += visValsAch[kerPos] * weights[weiPos];
								kerPos++;
								weiPos++;
							}
							kerPos += lenVisValsWid - kerDim;
							weiPos++;
						}
						kerPos += lenVisValsLayer - (kerDim * lenVisValsWid + kerDim);
						weiPos++;
					}
					valsAch[valPos] = Functions.sigmoid(valsAch[valPos]);
					valPos++;
				}
				valPos++;
			}
			valPos++;
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

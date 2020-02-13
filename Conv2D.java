package engine;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	//there is no current options built in for padding
	int stride = 0; //stride length between neurons in vis layer
	int lenKerDim = 0; //individual kernel dimensions, they are square so it acts for wid and hei
	int lenValsWid = 0; //width of vals
	int lenValsHei = 0; //height of vals
	int lenValsDep = 0; //depth of vals/number of kernels
	int lenValsLayer = 0; //total length of img, may not use and just go with wid x hei cache hit dependent 
	int lenVisValsWid = 0; //wid of vis layer
	int lenVisValsHei = 0; //hei of vis layer
	int lenVisValsDep = 0; //depth of vis layer/depth of each kernel
	int lenVisValsImg = 0;
	int weiLen = 0;
	
	public Conv2D()
	{
	}
	
	public void eval()
	{
		int valPos = 0;
		int weiPos = 0;
		int kerPos = 0;
		int kerDepStop = 0;
		int kerWidStop = 0;
		int kerHeiStop = 0;
		for(int i = 0; i < lenValsDep; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerDim * lenKerDim * lenVisValsDep;
			for(int j = 0; j < lenValsHei; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsWid; k++) //iterate through each col
				{
					valsAch[valPos] = 0;
					kerPos = j * stride * lenVisValsWid + k * stride;
					//kerDepStop = kerPos + lenVisValsImg * (lenVisValsDep - 1) + kerDim * lenVisValsWid + kerDim;
					for(int l = 0; l < lenVisValsDep; l++) //iterate through each img in the vis layer 
					{
						//kerHeiStop = kerPos + kerDim * lenVisValsWid + kerDim;
						for(int m = 0; m < lenKerDim; m++) //iterate through kernel dim
						{
							//kerWidStop = kerPos + kerDim;
							for(int n = 0; n < lenKerDim; n++) //iterate through kernel dim
							{
								valsAch[valPos] += visValsAch[kerPos] * weights[weiPos];
								kerPos++;
								weiPos++;
							}
							kerPos += lenVisValsWid - lenKerDim;
							weiPos++;
						}
						kerPos += lenVisValsImg - (lenKerDim * lenVisValsWid + lenKerDim);
						weiPos++;
					}
					valsAch[valPos] = Functions.sigmoid(valsAch[valPos]);
					weiPos -= lenKerDim * lenKerDim * lenVisValsDep;
					valPos++;
				}
				valPos++;
			}
			valPos++;
		}
	}
	public void train()
	{
		//look at iterating through the vis layer again 
		//add in step func that is thread safe
		
		for(int i = 0; i < lenVals; i++)
		{
			valsReq[i] = Functions.stepNeg(valsReq[i]);
		}
		int valPos = 0;
		int kerPos = 0;
		int weiPos = 0;
		for(int i = 0; i < lenValsDep; i++) //iterate through each weight set/kernel/img
		{
			for(int j = 0; j < lenValsHei; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsWid; k++) //iterate through each row
				{
					weiPos = 0;
					kerPos = j * stride * lenVisValsWid + k * stride;
					for(int l = 0; l < lenVisValsDep; l++) //iterate through each img in the vis layer 
					{
						for(int m = 0; m < lenKerDim; m++) //iterate through kernel dim
						{
							for(int n = 0; n < lenKerDim; n++) //iterate through kernel dim
							{
								visValsReq[kerPos] += valsReq[valPos] * weights[weiPos];
								kerPos++;
								weiPos++;
							}
							kerPos += lenVisValsWid - lenKerDim;
							weiPos++;
						}
						kerPos += lenVisValsImg - (lenKerDim * lenVisValsWid + lenKerDim);
						weiPos++;
					}
					valPos++;
				}
				valPos++;
			}
			valPos++;
		}
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

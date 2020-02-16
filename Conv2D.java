package engine;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	//there is no current options built in for padding
	int stride = 0; //stride length between neurons in vis layer
	int lenKer = 0;
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
		int valPos = begVals;
		int weiPos = 0;
		int kerPos = begValsVis;
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
					kerPos = begValsVis + j * stride * lenVisValsWid + k * stride;
					kerDepStop = kerPos + lenVisValsImg * (lenVisValsDep - 1);
					while(kerPos <= kerDepStop) //iterate through each img in the vis layer 
					{
						kerHeiStop = kerPos + lenKerDim * lenVisValsWid;
						for(int m = 0; m <= kerHeiStop; m++) //iterate through kernel dim
						{
							kerWidStop = kerPos + lenKerDim;
							while(kerPos < kerWidStop) //iterate through kernel dim
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
		int valPos = begVals;
		int weiPos = 0;
		int kerPos = begValsVis;
		int kerDepStop = 0;
		int kerWidStop = 0;
		int kerHeiStop = 0;
		while(valPos < endVals)
		{
			valsReq[valPos] = Functions.stepNeg(valsReq[valPos]);
			valPos++;
		}
		for(int i = 0; i < lenValsDep; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerDim * lenKerDim * lenVisValsDep;
			for(int j = 0; j < lenValsHei; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsWid; k++) //iterate through each row
				{
					kerPos = begValsVis + j * stride * lenVisValsWid + k * stride;
					kerDepStop = kerPos + lenVisValsImg * (lenVisValsDep - 1);
					while(kerPos <= kerDepStop) //iterate through each img in the vis layer 
					{
						kerHeiStop = kerPos + lenKerDim * lenVisValsWid;
						for(int m = 0; m <= kerHeiStop; m++) //iterate through kernel dim
						{
							kerWidStop = kerPos + lenKerDim;
							while(kerPos < kerWidStop) //iterate through kernel dim
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
					weiPos -= lenKerDim * lenKerDim * lenVisValsDep;
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

	public int getStride() {
		return stride;
	}

	public void setStride(int stride) {
		this.stride = stride;
	}

	public int getLenKer() {
		return lenKer;
	}

	public void setLenKer(int lenKer) {
		this.lenKer = lenKer;
	}

	public int getLenKerDim() {
		return lenKerDim;
	}

	public void setLenKerDim(int lenKerDim) {
		this.lenKerDim = lenKerDim;
	}

	public int getLenValsWid() {
		return lenValsWid;
	}

	public void setLenValsWid(int lenValsWid) {
		this.lenValsWid = lenValsWid;
	}

	public int getLenValsHei() {
		return lenValsHei;
	}

	public void setLenValsHei(int lenValsHei) {
		this.lenValsHei = lenValsHei;
	}

	public int getLenValsDep() {
		return lenValsDep;
	}

	public void setLenValsDep(int lenValsDep) {
		this.lenValsDep = lenValsDep;
	}

	public int getLenValsLayer() {
		return lenValsLayer;
	}

	public void setLenValsLayer(int lenValsLayer) {
		this.lenValsLayer = lenValsLayer;
	}

	public int getLenVisValsWid() {
		return lenVisValsWid;
	}

	public void setLenVisValsWid(int lenVisValsWid) {
		this.lenVisValsWid = lenVisValsWid;
	}

	public int getLenVisValsHei() {
		return lenVisValsHei;
	}

	public void setLenVisValsHei(int lenVisValsHei) {
		this.lenVisValsHei = lenVisValsHei;
	}

	public int getLenVisValsDep() {
		return lenVisValsDep;
	}

	public void setLenVisValsDep(int lenVisValsDep) {
		this.lenVisValsDep = lenVisValsDep;
	}

	public int getLenVisValsImg() {
		return lenVisValsImg;
	}

	public void setLenVisValsImg(int lenVisValsImg) {
		this.lenVisValsImg = lenVisValsImg;
	}

	public int getWeiLen() {
		return weiLen;
	}

	public void setWeiLen(int weiLen) {
		this.weiLen = weiLen;
	}
}

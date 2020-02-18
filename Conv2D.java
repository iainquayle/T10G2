package engine;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	//there is no current options built in for padding
	int stride = 0; //stride length between neurons in vis layer
	int lenKer = 0;
	int lenKerDim = 0; //individual kernel dimensions, they are square so it acts for X and Y
	int lenValsX = 0; //width of vals
	int lenValsY = 0; //height of vals
	int lenValsZ = 0; //depth of vals/number of kernels
	int lenValsLayer = 0; //total length of img, may not use and just go with X x Y cache hit dependent 
	int lenVisValsX = 0; //X of vis layer
	int lenVisValsY = 0; //Y of vis layer
	int lenVisValsZ = 0; //Zth of vis layer/Zth of each kernel
	int lenVisValsImg = 0;
	float[] aveKer = null;
	
	public Conv2D()
	{
	}
	public void init(String loc, Scanner in, int pos) throws IOException
	{
		layerNum = pos;
		visLayerNum = in.nextInt();
		lenValsX = in.nextInt();
		lenValsY = in.nextInt();
		lenValsZ = in.nextInt();
		lenWeis = in.nextInt();
		stride = in.nextInt();
		lenVals = lenValsX * lenValsY * lenValsZ;
		Scanner wIn = new Scanner(new File("weights" + layerNum));
		weights = new float[lenWeis];
		for(int i = 0; i < lenWeis; i++)
		{
			weights[i] = wIn.nextFloat();
		}
		wIn.close();
	}
	
	public void eval()
	{
		int valPos = begVals;
		int weiPos = 0;
		int kerPos = begValsVis;
		int kerZStop = 0;
		int kerXStop = 0;
		int kerYStop = 0;
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerDim * lenKerDim * lenVisValsZ;
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valPos] = 0;
					kerPos = begValsVis + j * stride * lenVisValsX + k * stride;
					kerZStop = kerPos + lenVisValsImg * (lenVisValsZ - 1);
					while(kerPos <= kerZStop) //iterate through each img in the vis layer 
					{
						kerYStop = kerPos + lenKerDim * lenVisValsX;
						for(int m = 0; m <= kerYStop; m++) //iterate through kernel dim
						{
							kerXStop = kerPos + lenKerDim;
							while(kerPos < kerXStop) //iterate through kernel dim
							{
								valsAch[valPos] += visValsAch[kerPos] * weights[weiPos];
								kerPos++;
								weiPos++;
							}
							kerPos += lenVisValsX - lenKerDim;
							weiPos++;
						}
						kerPos += lenVisValsImg - (lenKerDim * lenVisValsX + lenKerDim);
						weiPos++;
					}
					valsAch[valPos] = Functions.sigmoid(valsAch[valPos]);
					weiPos -= lenKerDim * lenKerDim * lenVisValsZ;
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
		int kerZStop = 0;
		int kerXStop = 0;
		int kerYStop = 0;
		while(valPos < endVals)
		{
			valsReq[valPos] = Functions.stepNeg(valsReq[valPos]);
			valPos++;
		}
		if(visValsReq != null)
		{
			for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
			{
				weiPos = i * lenKerDim * lenKerDim * lenVisValsZ;
				for(int j = 0; j < lenValsY; j++) //iterate through each layer
				{
					for(int k = 0; k < lenValsX; k++) //iterate through each row
					{
						kerPos = begValsVis + j * stride * lenVisValsX + k * stride;
						kerZStop = kerPos + lenVisValsImg * (lenVisValsZ - 1);
						while(kerPos <= kerZStop) //iterate through each img in the vis layer 
						{
							kerYStop = kerPos + lenKerDim * lenVisValsX;
							for(int m = 0; m <= kerYStop; m++) //iterate through kernel dim
							{
								kerXStop = kerPos + lenKerDim;
								while(kerPos < kerXStop) //iterate through kernel dim
								{
									visValsReq[kerPos] += valsReq[valPos] * weights[weiPos];
									weights[weiPos] += (valsReq[valPos] - visValsAch[kerPos]) * learnRate;
									kerPos++;
									weiPos++;
								}
								kerPos += lenVisValsX - lenKerDim;
								weiPos++;
							}
							kerPos += lenVisValsImg - (lenKerDim * lenVisValsX + lenKerDim);
							weiPos++;
						}
						weiPos -= lenKerDim * lenKerDim * lenVisValsZ;
						valPos++;
					}
					valPos++;
				}
				valPos++;
			}
		}
		else
		{
			for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
			{
				weiPos = i * lenKerDim * lenKerDim * lenVisValsZ;
				for(int j = 0; j < lenValsY; j++) //iterate through each layer
				{
					for(int k = 0; k < lenValsX; k++) //iterate through each row
					{
						kerPos = begValsVis + j * stride * lenVisValsX + k * stride;
						kerZStop = kerPos + lenVisValsImg * (lenVisValsZ - 1);
						while(kerPos <= kerZStop) //iterate through each img in the vis layer 
						{
							kerYStop = kerPos + lenKerDim * lenVisValsX;
							for(int m = 0; m <= kerYStop; m++) //iterate through kernel dim
							{
								kerXStop = kerPos + lenKerDim;
								while(kerPos < kerXStop) //iterate through kernel dim
								{
									weights[weiPos] += (valsReq[valPos] - visValsAch[kerPos]) * learnRate;
									kerPos++;
									weiPos++;
								}
								kerPos += lenVisValsX - lenKerDim;
								weiPos++;
							}
							kerPos += lenVisValsImg - (lenKerDim * lenVisValsX + lenKerDim);
							weiPos++;
						}
						weiPos -= lenKerDim * lenKerDim * lenVisValsZ;
						valPos++;
					}
					valPos++;
				}
				valPos++;
			}
		}
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

	public int getLenValsX() {
		return lenValsX;
	}

	public void setLenValsX(int lenValsX) {
		this.lenValsX = lenValsX;
	}

	public int getLenValsY() {
		return lenValsY;
	}

	public void setLenValsY(int lenValsY) {
		this.lenValsY = lenValsY;
	}

	public int getLenValsZ() {
		return lenValsZ;
	}

	public void setLenValsZ(int lenValsZ) {
		this.lenValsZ = lenValsZ;
	}

	public int getLenValsLayer() {
		return lenValsLayer;
	}

	public void setLenValsLayer(int lenValsLayer) {
		this.lenValsLayer = lenValsLayer;
	}

	public int getLenVisValsX() {
		return lenVisValsX;
	}

	public void setLenVisValsX(int lenVisValsX) {
		this.lenVisValsX = lenVisValsX;
	}

	public int getLenVisValsY() {
		return lenVisValsY;
	}

	public void setLenVisValsY(int lenVisValsY) {
		this.lenVisValsY = lenVisValsY;
	}

	public int getLenVisValsZ() {
		return lenVisValsZ;
	}

	public void setLenVisValsZ(int lenVisValsZ) {
		this.lenVisValsZ = lenVisValsZ;
	}

	public int getLenVisValsImg() {
		return lenVisValsImg;
	}

	public void setLenVisValsImg(int lenVisValsImg) {
		this.lenVisValsImg = lenVisValsImg;
	}
}

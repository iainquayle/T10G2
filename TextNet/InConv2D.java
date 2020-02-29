package engine;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class InConv2D extends Layer
{
	//there is no current options built in for padding
	private int stride = 0; //stride length between neurons in vis layer
	private int lenKer = 0;
	private int lenKerX = 0; //individual kernel dimensions, they are square so it acts for X and Y
	private int lenValsX = 0; //width of vals
	private int lenValsY = 0; //height of vals
	private int lenValsZ = 0; //depth of vals/number of kernels
	private int lenValsLayer = 0; //total length of img, may not use and just go with X x Y cache hit dependent 
	private int lenValsVisX = 0; //X of vis layer
	private int lenValsVisY = 0; //Y of vis layer
	private int lenValsVisZ = 0; //Zth of vis layer/Zth of each kernel
	private int lenValsVisImg = 0;
	
	float data[] = null;
	int dataNum = 0;
	
	public InConv2D()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num) throws IOException
	{
		layerNum = num; //(layer reference, width, height, depth, vis depth, length weights, kernel width/height, stride)
		dataNum = in.nextInt();
		lenValsX = in.nextInt();
		lenValsY = in.nextInt();
		lenValsZ = in.nextInt();
		lenValsVisX = in.nextInt();
		lenValsVisY = in.nextInt();
		lenValsVisZ = in.nextInt();
		lenWeis = in.nextInt();
		lenKerX = in.nextInt();
		stride = in.nextInt();
		lenVals = lenValsX * lenValsY * lenValsZ;
		lenValsVis = lenValsVisX * lenValsVisY * lenValsVisZ;
		lenValsVisImg = lenValsVisX * lenValsVisY;
		lenKer = lenKerX * lenKerX * lenValsZ;
		Scanner wFile = new Scanner(new File("weights" + layerNum));
		weights = new float[lenWeis];
		for(int i = 0; i < lenWeis; i++)
		{
			weights[i] = wFile.nextFloat();
		}
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
		}
		data = io[dataNum];
		wFile.close();
	}
	
	public void eval()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int kerZStop = 0;
		int kerXStop = 0;
		int kerYStop = 0;
		begValsVis = rndIndex * lenValsVis;  //sets the beg val for the data
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerX * lenKerX * lenValsVisZ;
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsReq[valsPos] = 0;
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					kerZStop = valsVisPos + lenValsVisImg * (lenValsVisZ - 1);
					while(valsVisPos <= kerZStop) //iterate through each img in the vis layer 
					{
						kerYStop = valsVisPos + lenKerX * lenValsVisX;
						while(valsVisPos <= kerYStop) //iterate through kernel dim
						{
							kerXStop = valsVisPos + lenKerX;
							while(valsVisPos < kerXStop) //iterate through kernel dim
							{
								valsAch[valsPos] += data[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += lenValsVisX - lenKerX;
							weiPos++;
						}
						valsVisPos += lenValsVisImg - (lenKerX * lenValsVisX + lenKerX);
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					weiPos -= lenKerX * lenKerX * lenValsVisZ;
					valsPos++;
				}
				valsPos++;
			}
			valsPos++;
		}
	}
	public void train()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int kerZStop = 0;
		int kerXStop = 0;
		int kerYStop = 0;
		while(valsPos < endVals)
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]);
			valsPos++;
		}
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerX * lenKerX * lenValsVisZ;
			for(int j = 0; j < lenValsY; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each row
				{
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					kerZStop = valsVisPos + lenValsVisImg * (lenValsVisZ - 1);
					while(valsVisPos <= kerZStop) //iterate through each img in the vis layer 
					{
						kerYStop = valsVisPos + lenKerX * lenValsVisX;
						for(int m = 0; m <= kerYStop; m++) //iterate through kernel dim
						{
							kerXStop = valsVisPos + lenKerX;
							while(valsVisPos < kerXStop) //iterate through kernel dim
							{
								weights[weiPos] += (valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += lenValsVisX - lenKerX;
							weiPos++;
						}
						valsVisPos += lenValsVisImg - (lenKerX * lenValsVisX + lenKerX);
						weiPos++;
					}
					weiPos -= lenKer;
					valsPos++;
				}
				valsPos++;
			}
			valsPos++;
		}
	}
	
	public int getLayerType()
	{
		return 4;
	}
	public String toString()
	{
		return null;
	}

	public int getStride() 
	{
		return stride;
	}
	public void setStride(int stride) 
	{
		this.stride = stride;
	}
	public int getLenKer() 
	{
		return lenKer;
	}
	public void setLenKer(int lenKer) 
	{
		this.lenKer = lenKer;
	}
	public int getlenKerX() 
	{
		return lenKerX;
	}
	public void setlenKerX(int lenKerX) 
	{
		this.lenKerX = lenKerX;
	}
	public int getLenValsX() 
	{
		return lenValsX;
	}
	public void setLenValsX(int lenValsX) 
	{
		this.lenValsX = lenValsX;
	}
	public int getLenValsY() 
	{
		return lenValsY;
	}
	public void setLenValsY(int lenValsY) 
	{
		this.lenValsY = lenValsY;
	}
	public int getLenValsZ() 
	{
		return lenValsZ;
	}
	public void setLenValsZ(int lenValsZ) 
	{
		this.lenValsZ = lenValsZ;
	}
	public int getLenValsLayer() 
	{
		return lenValsLayer;
	}
	public void setLenValsLayer(int lenValsLayer) 
	{
		this.lenValsLayer = lenValsLayer;
	}
	public int getLenValsVisX() 
	{
		return lenValsVisX;
	}
	public void setLenValsVisX(int lenValsVisX) 
	{
		this.lenValsVisX = lenValsVisX;
	}
	public int getLenValsVisY() 
	{
		return lenValsVisY;
	}
	public void setLenValsVisY(int lenValsVisY) 
	{
		this.lenValsVisY = lenValsVisY;
	}
	public int getLenValsVisZ() 
	{
		return lenValsVisZ;
	}
	public void setLenValsVisZ(int lenValsVisZ) 
	{
		this.lenValsVisZ = lenValsVisZ;
	}
	public int getLenValsVisImg() 
	{
		return lenValsVisImg;
	}
	public void setLenValsVisImg(int lenValsVisImg) 
	{
		this.lenValsVisImg = lenValsVisImg;
	}
}

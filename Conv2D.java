package engine;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	//there is no current options built in for padding
	static float[] aveWei = null;
	int stride = 0; //stride length between neurons in vis layer
	int lenKer = 0;
	int lenKerDim = 0; //individual kernel dimensions, they are square so it acts for X and Y
	int lenValsX = 0; //width of vals
	int lenValsY = 0; //height of vals
	int lenValsZ = 0; //depth of vals/number of kernels
	int lenValsLayer = 0; //total length of img, may not use and just go with X x Y cache hit dependent 
	int lenValsVisX = 0; //X of vis layer
	int lenValsVisY = 0; //Y of vis layer
	int lenValsVisZ = 0; //Zth of vis layer/Zth of each kernel
	int lenValsVisImg = 0;
	
	public Conv2D()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num) throws IOException
	{
		layerNum = num; //(layer reference, width, height, depth, length weights, kernel width/height, stride)
		visLayerNum = in.nextInt();
		lenValsX = in.nextInt();
		lenValsY = in.nextInt();
		lenValsZ = in.nextInt();
		lenWeis = in.nextInt();
		lenKerDim = in.nextInt();
		stride = in.nextInt();
		lenVals = lenValsX * lenValsY * lenValsZ;
		lenKer = lenKerDim * lenKerDim * lenValsZ;
		Scanner wIn = new Scanner(new File("weights" + layerNum));
		weights = new float[lenWeis];
		for(int i = 0; i < lenWeis; i++)
		{
			weights[i] = wIn.nextFloat();
		}
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[visLayerNum].getBegVals();
		}
		aveWei = new float[lenKer];
		wIn.close();
	}
	
	public void eval()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int kerZStop = 0;
		int kerXStop = 0;
		int kerYStop = 0;
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/img
		{
			weiPos = i * lenKerDim * lenKerDim * lenValsVisZ;
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
						kerYStop = valsVisPos + lenKerDim * lenValsVisX;
						while(valsVisPos <= kerYStop) //iterate through kernel dim
						{
							kerXStop = valsVisPos + lenKerDim;
							while(valsVisPos < kerXStop) //iterate through kernel dim
							{
								valsAch[valsPos] += visValsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += lenValsVisX - lenKerDim;
							weiPos++;
						}
						valsVisPos += lenValsVisImg - (lenKerDim * lenValsVisX + lenKerDim);
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					weiPos -= lenKerDim * lenKerDim * lenValsVisZ;
					valsPos++;
				}
				valsPos++;
			}
			valsPos++;
		}
	}
	@SuppressWarnings("unused")
	public void train() //backpass and train, will optimize later
	{
		//look at iterating through the vis layer again 
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int aveWeiPos = 0;
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
			weiPos = i * lenKerDim * lenKerDim * lenValsVisZ;
			for(int j = 0; j < lenValsY; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each row
				{
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					aveWeiPos = 0;
					kerZStop = valsVisPos + lenValsVisImg * (lenValsVisZ - 1);
					while(valsVisPos <= kerZStop) //iterate through each img in the vis layer 
					{
						kerYStop = valsVisPos + lenKerDim * lenValsVisX;
						for(int m = 0; m <= kerYStop; m++) //iterate through kernel dim
						{
							kerXStop = valsVisPos + lenKerDim;
							while(valsVisPos < kerXStop) //iterate through kernel dim
							{
								visValsReq[valsVisPos] += valsReq[valsPos] * weights[weiPos];
								aveWei[aveWeiPos] += (valsReq[valsPos] - visValsAch[valsVisPos]) * learnRate;
								valsVisPos++;
								weiPos++;
								aveWeiPos++;
							}
							valsVisPos += lenValsVisX - lenKerDim;
							weiPos++;
							aveWeiPos++;
						}
						valsVisPos += lenValsVisImg - (lenKerDim * lenValsVisX + lenKerDim);
						weiPos++;
						aveWeiPos++;
					}
					weiPos -= lenKer;
					valsPos++;
				}
				valsPos++;
			}
			for(int j = 0; j < lenKer; j++)
			{
				weights[weiPos] += aveWei[j];
				weiPos++;
			}
			valsPos++;
		}
	}
	
	public int getLayerType()
	{
		return 1;
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
	public int getLenKerDim() 
	{
		return lenKerDim;
	}
	public void setLenKerDim(int lenKerDim) 
	{
		this.lenKerDim = lenKerDim;
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

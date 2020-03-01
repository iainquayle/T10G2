package engine;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	//there is no current options built in for padding
	private static float[] aveWei = null;
	private int stride = 0; //stride length between neurons in vis layer
	private int lenKer = 0;
	private int lenKerX = 0; //individual kernel dimensions, they are square so it acts for X and Y
	private int lenValsX = 0; //width of vals
	private int lenValsY = 0; //height of vals
	private int lenValsZ = 0; //depth of vals/number of kernels
	private int lenValsVisX = 0; //X of vis layer
	private int lenValsVisY = 0; //Y of vis layer
	private int lenValsVisZ = 0; //Zth of vis layer/Zth of each kernel
	private int lenValsVisXY = 0;
	private int jumpKerY = 0;
	private int jumpKerXY = 0;
	private int jumpKerZ = 0;
	
	public Conv2D()
	{
	}
	public void init(Layer[] l, String loc, Scanner in, float[][] io, int num) throws IOException
	{
		layerNum = num; //(layer reference, width, height, depth, vis wid, vis ehi, vis dep, length weights, kernel width/height, stride)
		layerVisNum = in.nextInt();
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
		lenValsVisXY = lenValsVisX * lenValsVisY;
		lenKer = lenKerX * lenKerX * lenValsVisZ;
		Scanner wFile = new Scanner(new File(loc + "weights" + layerNum));
		weights = new float[lenWeis];
		for(int i = 0; i < lenWeis; i++)
		{
			weights[i] = wFile.nextFloat();
		}
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		endVals = begVals + lenVals;
		endValsVis = begValsVis + lenValsVis;
		aveWei = new float[lenKer];
		wFile.close();
	}
	
	public void eval()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int stopKerX = 0;
		int stopKerY = 0;
		int stopKerZ = 0;
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsReq[valsPos] = 0;
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					stopKerZ = valsVisPos + lenValsVisXY * (lenValsVisZ - 1);
					while(valsVisPos <= stopKerZ) //iterate through each XY in the vis layer 
					{
						stopKerY = valsVisPos + lenKerX * lenValsVisX;
						while(valsVisPos <= stopKerY) //iterate through kernel dim
						{
							stopKerX = valsVisPos + lenKerX;
							while(valsVisPos < stopKerX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += lenValsVisX - lenKerX;
							weiPos++;
						}
						valsVisPos += lenValsVisXY - (lenKerX * lenValsVisX + lenKerX);
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					weiPos -= lenKerX * lenKerX * lenValsVisZ;
					valsPos++;
				}
				valsPos++;
			}
			weiPos += lenKerX * lenKerX * lenValsVisZ;
			valsPos++;
		}
		
		
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsReq[valsPos] = 0;
					stopKerZ = valsVisPos + jumpKerZ;
					while(valsVisPos < stopKerZ) //iterate through each XY in the vis layer 
					{
						stopKerY = valsVisPos + jumpKerY;
						while(valsVisPos <= stopKerY) //iterate through kernel dim
						{
							stopKerX = valsVisPos + lenKerX;
							while(valsVisPos < stopKerX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpKerY;
							weiPos++;
						}
						valsVisPos += jumpKerXY;
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					valsVisPos = valsVisPos - jumpKerZ + stride;                      //maybe have jumpkerz also take into account stride for the jump back
					weiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += lenValsVisX * (stride - 1) + lenKerX - stride;
				valsPos++;
			}
			valsVisPos = begValsVis;
			weiPos += lenKer;
			valsPos++;
		}
	}
	@SuppressWarnings("unused")
	public void train()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int aveWeiPos = 0;
		int stopKerZ = 0;
		int stopKerX = 0;
		int stopKerY = 0;
		while(valsPos < endVals)
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]);
			valsPos++;
		}
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each row
				{
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					aveWeiPos = 0;
					stopKerZ = valsVisPos + lenValsVisXY * (lenValsVisZ - 1);
					while(valsVisPos <= stopKerZ) //iterate through each XY in the vis layer 
					{
						stopKerY = valsVisPos + lenKerX * lenValsVisX;
						for(int m = 0; m <= stopKerY; m++) //iterate through kernel dim
						{
							stopKerX = valsVisPos + lenKerX;
							while(valsVisPos < stopKerX) //iterate through kernel dim
							{
								valsReq[valsVisPos] += valsReq[valsPos] * weights[weiPos];
								aveWei[aveWeiPos] += (valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;
								valsVisPos++;
								weiPos++;
								aveWeiPos++;
							}
							valsVisPos += lenValsVisX - lenKerX;
							weiPos++;
							aveWeiPos++;
						}
						valsVisPos += lenValsVisXY - (lenKerX * lenValsVisX + lenKerX);
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
	public int getLenValsVisXY() 
	{
		return lenValsVisXY;
	}
	public void setLenValsVisXY(int lenValsVisXY) 
	{
		this.lenValsVisXY = lenValsVisXY;
	}
}

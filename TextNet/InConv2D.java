package engine;

import java.util.Scanner;
import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class InConv2D extends Layer
{
	private int stride = 0; //stride length between neurons in vis layer
	private int lenKer = 0;
	private int lenKerX = 0; //individual kernel dimensions, they are square so it acts for X and Y
	private int lenValsX = 0; //width of vals
	private int lenValsY = 0; //height of vals
	private int lenValsZ = 0; //depth of vals/number of kernels
	@SuppressWarnings("unused")
	private int lenValsXY = 0;
	private int lenValsVisX = 0; //X of vis layer
	private int lenValsVisY = 0; //Y of vis layer
	private int lenValsVisZ = 0; //Zth of vis layer/Zth of each kernel
	private int lenValsVisXY = 0;
	private int jumpValsVisY = 0; //moves down one row
	private int jumpValsVisXY = 0; //moves down all rows on one img
	private int jumpValsVisZ = 0; //moves down one img
	private int jumpValsVisBack = 0; //moves back to start of ker + stride
	private int jumpValsVisStride = 0; //moves vertical stride length
	
	float data[] = null;
	int dataNum = 0;
	int jumpData = 0;
	
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
		lenValsVisXY = lenValsVisX * lenValsVisY;
		lenKer = lenKerX * lenKerX * lenValsVisZ;
		jumpValsVisY = lenValsVisX - lenKerX;
		jumpValsVisXY = lenValsVisX * (lenKerX - 1);
		jumpValsVisZ = lenValsVisXY - jumpValsVisXY - lenKerX;
		jumpValsVisBack = lenValsVisXY * (lenKerX - 1) + lenKerX + jumpValsVisXY + jumpValsVisZ - stride;
		jumpValsVisStride = lenValsVisX * (stride - 1) - stride;
		super.loadWeights(loc);
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
		}
		data = io[dataNum];
	}
	
	public void eval()
	{
		begValsVis = rndIndex * lenValsVis;  //sets the beg val for the data
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
					stopKerZ = valsVisPos + jumpValsVisBack;   
					while(valsVisPos <= stopKerZ) //iterate through each XY in the vis layer 
					{
						stopKerY = valsVisPos + jumpValsVisXY;  
						while(valsVisPos <= stopKerY) //iterate through kernel dim
						{
							stopKerX = valsVisPos + lenKerX;
							while(valsVisPos < stopKerX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY;
							weiPos++;
						}
						valsVisPos += jumpValsVisZ;
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					valsVisPos -= jumpValsVisBack;
					weiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
				valsPos++;
			}
			valsVisPos = begValsVis;
			weiPos += lenKer;
			valsPos++;
		}
	}
	public void train()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int stopKerX = 0;
		int stopKerY = 0;
		int stopKerZ = 0;
		while(valsPos < endVals)
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]);
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
					stopKerZ = valsVisPos + jumpValsVisBack;   
					while(valsVisPos <= stopKerZ) //iterate through each XY in the vis layer 
					{
						stopKerY = valsVisPos + jumpValsVisXY;  
						while(valsVisPos <= stopKerY) //iterate through kernel dim
						{
							stopKerX = valsVisPos + lenKerX;
							while(valsVisPos < stopKerX) //iterate through kernel dim
							{
								weights[weiPos] += (valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY;
							weiPos++;
						}
						valsVisPos += jumpValsVisZ;
						weiPos++;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					valsVisPos -= jumpValsVisBack;
					weiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
				valsPos++;
			}
			valsVisPos = begValsVis;
			weiPos += lenKer;
			valsPos++;
		}
	}
	
	public int getLayerType()
	{
		return 4;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + lenKerX + "\n";
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
	public int getlenValsVisXY() 
	{
		return lenValsVisXY;
	}
	public void setlenValsVisXY(int lenValsVisXY) 
	{
		this.lenValsVisXY = lenValsVisXY;
	}
}

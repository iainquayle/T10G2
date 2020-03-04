package engine;

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
	
	public Conv2D()
	{
	}
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num; //(layer reference, width, height, depth, vis wid, vis ehi, vis dep, kernel width/height, stride)
		layerVisNum = in.nextInt();
		lenValsX = in.nextInt();
		lenValsY = in.nextInt();
		lenValsZ = in.nextInt();
		lenValsVisX = in.nextInt();
		lenValsVisY = in.nextInt();
		lenValsVisZ = in.nextInt();
		lenKerX = in.nextInt();
		stride = in.nextInt();
		lenVals = lenValsX * lenValsY * lenValsZ;
		lenValsVis = lenValsVisX * lenValsVisY * lenValsVisZ;
		lenWeis = lenKerX * lenKerX * lenValsZ * lenValsVisZ;
		lenValsVisXY = lenValsVisX * lenValsVisY;
		lenKer = lenKerX * lenKerX * lenValsVisZ;
		jumpValsVisY = lenValsVisX - lenKerX;
		jumpValsVisXY = lenValsVisX * (lenKerX - 1);
		jumpValsVisZ = lenValsVisXY - jumpValsVisXY - jumpValsVisY - lenKerX;
		jumpValsVisBack = lenValsVisXY * lenValsVisZ - stride;
		jumpValsVisStride = lenValsVisX * (stride - 1) - stride;
		super.loadWeights(loc);
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		endVals = begVals + lenVals;
		endValsVis = begValsVis + lenValsVis;
		aveWei = new float[lenKer];
	}
	
	public void eval()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		int stopValsVisZ = 0;
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsReq[valsPos] = 0;
					stopValsVisZ = valsVisPos + jumpValsVisBack;   
					while(valsVisPos < stopValsVisZ) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY;  
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY;
						}
						valsVisPos += jumpValsVisZ;
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					valsVisPos -= jumpValsVisBack;
					weiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
			}
			valsVisPos = begValsVis;
			weiPos += lenKer;
		}
	}
	@SuppressWarnings("unused")
	public void train()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int aveWeiPos = 0;
		int stopValsVisZ = 0;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endVals)
		{
			valsReq[valsPos] = Functions.stepNeg(valsReq[valsPos]);
			valsPos++;
		}
		valsPos = begVals;
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					aveWeiPos = 0;
					stopValsVisZ = valsVisPos + jumpValsVisBack;                                             ///This needs fixing
					while(valsVisPos < stopValsVisZ) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY;                                             ///This needs fixing jump y is not correct for this
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								valsReq[valsVisPos] += valsReq[valsPos] * weights[weiPos];
								aveWei[aveWeiPos] += (valsReq[valsPos] - valsAch[valsVisPos]) * learnRate;
								valsVisPos++;
								weiPos++;
								aveWeiPos++;
							}
							valsVisPos += jumpValsVisY;
						}
						valsVisPos += jumpValsVisZ;
					}
					valsVisPos -= jumpValsVisBack;
					weiPos -= lenKer;
					aveWeiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
			}
			aveWeiPos = 0;
			while(aveWeiPos < lenKer)
			{
				weights[weiPos] += aveWei[aveWeiPos];
				weiPos++;
				aveWeiPos++;
			}
			valsVisPos = begValsVis;
		}
	}
	
	public int getLayerType()
	{
		return 1;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + lenKerX + ", " + lenWeis + "\n";
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



/* 		while(valsPos < endVals) //iterate through each weight set/kernel/XY
		{
			valsAch[valsPos] = 0;
			valsReq[valsPos] = 0;
			stopValsVisZ = valsVisPos + jumpValsVisBack;
			while(valsVisPos <= stopValsVisZ) //iterate through each XY in the vis layer 
			{
				stopValsVisY = valsVisPos + jumpValsVisXY;
				while(valsVisPos <= stopValsVisY) //iterate through kernel dim
				{
					stopValsVisX = valsVisPos + lenKerX;
					while(valsVisPos < stopValsVisX) //iterate through kernel dim
					{
						valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
						valsVisPos++;
						weiPos++;
					}
					valsVisPos += jumpValsVisY;
				}
				valsVisPos += jumpValsVisZ;
			}
			valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
			if((valsPos - begVals) % lenValsXY != 0)
			{
				valsVisPos -= jumpValsVisBack;
				weiPos -= lenKer;
			}
			else if((valsPos - begVals) % lenVals != 0)
			{
				valsVisPos += jumpValsVisStride;
			}
			else
			{
				valsVisPos = begValsVis;
			}
			valsPos++;
		}
 */
/*
 		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsReq[valsPos] = 0;
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					stopValsVisZ = valsVisPos + lenValsVisXY * (lenValsVisZ - 1);
					while(valsVisPos <= stopValsVisZ) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + lenKerX * lenValsVisX;
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos];
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += lenValsVisX - lenKerX;
						}
						valsVisPos += lenValsVisXY - (lenKerX * lenValsVisX + lenKerX);
					}
					valsAch[valsPos] = Functions.sigmoid(valsAch[valsPos]);
					weiPos -= lenKerX * lenKerX * lenValsVisZ;
					valsPos++;
				}
			}
			weiPos += lenKerX * lenKerX * lenValsVisZ;
		}
*/
/*
		for(int i = 0; i < lenValsZ; i++) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each layer
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each row
				{
					valsVisPos = begValsVis + j * stride * lenValsVisX + k * stride;
					aveWeiPos = 0;
					stopValsVisZ = valsVisPos + lenValsVisXY * (lenValsVisZ - 1);
					while(valsVisPos <= stopValsVisZ) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + lenKerX * lenValsVisX;
						for(int m = 0; m <= stopValsVisY; m++) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
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
*/

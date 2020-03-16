package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class Conv2D extends Layer
{
	private static float[] aveWei = null;
	
	private int stride = 0; //stride length between neurons in vis layer
	
	private int lenKer = 0;
	private int lenKerX = 0; //individual kernel dimensions, they are square so it acts for X and Y
	private int lenKerXY = 0;
	
	private int lenValsX = 0; //width of vals
	private int lenValsY = 0; //height of vals
	private int lenValsZ = 0; //depth of vals/number of kernels
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
		layerNum = num; //(layer reference, width, height, depth, vis wid, vis hei, vis dep, kernel width/height, stride)
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
		lenValsXY = lenValsX * lenValsY;
		lenValsVis = lenValsVisX * lenValsVisY * lenValsVisZ;
		lenWeis = lenKerX * lenKerX * lenValsZ * lenValsVisZ;
		lenValsVisXY = lenValsVisX * lenValsVisY;
		lenKer = lenKerX * lenKerX * lenValsVisZ;
		lenKerXY = lenKerX * lenKerX;
		
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
		if(aveWei == null || lenKer > aveWei.length)
		{
			aveWei = new float[lenKer];
		}
	}
	
	public void eval(int threadNum)
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsZ + (float)0.5) * lenValsXY;
		int weiPos = (int)(threadSplits * threadNum * lenValsZ + (float)0.5) * lenKer;
		int valsPos = begVals + (int)(threadSplits * threadNum * lenValsZ + (float)0.5) * lenValsXY;
		int valsVisPos = begValsVis;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endValsTemp) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					valsAch[valsPos] = 0;
					valsErr[valsPos] = 0; 
					while(valsVisPos < endValsVis) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY;  
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								valsAch[valsPos] += valsAch[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY; //moving down to start of next row in kernel
						}
						valsVisPos += jumpValsVisZ; //moving back to start of next img in kernel
					}
					valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]); //sigmoid activation on vals ach
					valsVisPos -= jumpValsVisBack; //moving back to the starting position minus the stride
					weiPos -= lenKer; //moving weipos back to start
					valsPos++;
				}
				valsVisPos += jumpValsVisStride; //moving down a row in the local layer
			}
			valsVisPos = begValsVis; //moving back to the start of vis layer to start new kernel
			weiPos += lenKer; //moving to new kernel
		}
	}
	public void train(int threadNum)
	{
		int begValsVisTemp = (int)(threadSplits * threadNum * lenValsVisZ + (float)0.5);
		int endValsVisTemp = (int)(threadSplits * (threadNum + 1) * lenValsVisZ + (float)0.5);
		int jumpValsVisBackTemp = lenValsVisXY * (endValsVisTemp - begValsVisTemp) - stride;
		int jumpKerBackTemp = lenKerXY * (endValsVisTemp - begValsVisTemp);
		int weiPos = begValsVisTemp * lenKerXY;
		begValsVisTemp = begValsVisTemp * lenValsVisXY + begValsVis;
		endValsVisTemp = endValsVisTemp * lenValsVisXY + begValsVis;
		int valsVisPos = begValsVisTemp;
		int valsPos = begVals;
		int aveWeiPos = weiPos;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		//float errTemp;
		while(valsPos < endVals) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					//errTemp = valsErr[valsPos] - valsAch[valsPos];
					while(valsVisPos < endValsVisTemp) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY; 
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								valsErr[valsVisPos] += valsErr[valsPos] * weights[weiPos]; //backpropagation of error
								aveWei[aveWeiPos] += valsErr[valsPos] * valsAch[valsVisPos] * learnRate; //accumuling adjustments to not disturb backpropagation 
								valsVisPos++;
								weiPos++;
								aveWeiPos++;
							}
							valsVisPos += jumpValsVisY;
						}
						valsVisPos += jumpValsVisZ;
					}
					valsVisPos -= jumpValsVisBackTemp;
					weiPos -= jumpKerBackTemp;
					aveWeiPos -= jumpKerBackTemp;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
			}
			while(aveWeiPos < jumpKerBackTemp) //iterating through the current kernel   //needs work here
			{
				weights[weiPos] += aveWei[aveWeiPos]; //adding adjustments to weights
				aveWei[aveWeiPos] = 0;
				weiPos++;
				aveWeiPos++;
			}
			weiPos += lenKer - jumpKerBackTemp;
			aveWeiPos -= jumpKerBackTemp;
			valsVisPos = begValsVisTemp;
		}
	}
	
	public int getLayerType()
	{
		return 1;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " + lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + lenKerX + ", " + lenWeis + "\n";
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
			valsErr[valsPos] = 0;
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
					valsErr[valsPos] = 0;
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
								valsErr[valsVisPos] += valsErr[valsPos] * weights[weiPos];
								aveWei[aveWeiPos] += (valsErr[valsPos] - valsAch[valsVisPos]) * learnRate;
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

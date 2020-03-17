package engine;

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
	
	private int lenValsVisX = 0; //X of vis layer
	private int lenValsVisY = 0; //Y of vis layer
	private int lenValsVisZ = 0; //Zth of vis layer/Zth of each kernel
	private int lenValsVisXY = 0;
	
	private int jumpValsVisY = 0; //moves down one row
	private int jumpValsVisXY = 0; //moves down all rows on one img
	private int jumpValsVisZ = 0; //moves down one img
	private int jumpValsVisBack = 0; //moves back to start of ker + stride
	private int jumpValsVisStride = 0; //moves vertical stride length
	
	private float data[] = null; //training labels for data inputed
	private int dataNum = 0; //array number to look at
	
	public InConv2D()
	{
	}
	public void init(Layer[] l, String loc, InputData in, float[][] io, int num) throws IOException
	{
		layerNum = num; //(layer reference, width, height, depth, vis depth, length weights, kernel width/height, stride)
		dataNum = in.nextInt();
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
		jumpValsVisStride = lenValsVisX * (stride - 1) + (lenValsVisX - (lenValsX * stride));
		
		data = io[dataNum];
		super.loadWeights(loc);
		if(num != 0)
		{
			begVals = l[num - 1].getBegVals() + l[num - 1].getLenVals();
		}
		endVals = begVals + lenVals;
		data = io[dataNum];
	}
	
	public void eval()
	{
		begValsVis = rndIndex * lenValsVis;  //sets the beg val for the data
		endValsVis = begValsVis + lenValsVis;
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endVals) //iterate through each weight set/kernel/XY
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
								valsAch[valsPos] += data[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY; //moving down to start of next row in kernel
						}
						valsVisPos += jumpValsVisZ; //moving to start of next img in kernel
					}
					valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]); //sigmoid activation on vals ach
					valsVisPos -= jumpValsVisBack; //moving back to the starting position minus the stride
					weiPos -= lenKer;  //moving weipos back to start
					valsPos++;
				}
				valsVisPos += jumpValsVisStride; //moving down a row in the local layer
			}
			valsVisPos = begValsVis; //moving back to the start of vis layer to start new kernel
			weiPos += lenKer; //moving to new kernel
		}
	}
	public void train()
	{
		int valsPos = begVals;
		int weiPos = 0;
		int valsVisPos = begValsVis;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endVals)
		{
			valsErr[valsPos] = Functions.stepZer(valsErr[valsPos]) - valsAch[valsPos];
			valsPos++;
		}
		valsPos = begVals;
		while(valsPos < endVals) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					while(valsVisPos < endValsVis) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY; //sets stop based on position in vis layer
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX; //sets stop based on position in vis layer
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								weights[weiPos] += valsErr[valsPos] * data[valsVisPos] * learnRate; //adjusting weights based on ach/rew error
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY; //jump down one row in kernel in vis layer
						}
						valsVisPos += jumpValsVisZ; //jump down on img in kernel in vis layer
					}
					valsVisPos -= jumpValsVisBack; //jump back to start - stride
					weiPos -= lenKer; //jump back to start of kernel
					valsPos++;
				}
				valsVisPos += jumpValsVisStride; //jump down one row in vis layer
			}
			valsVisPos = begValsVis; //resetting back to the start of the the vis layer 
			weiPos += lenKer; //moving to next kernel
		}
	}
	
	public int getLayerType()
	{
		return 4;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " +
				lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + 
				lenKerX + ", " + lenWeis + "\n";
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

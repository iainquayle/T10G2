package engine;

import java.io.IOException;

public class MaxPool extends Layer 
{	
	//weights are unused by this layer
	private int[] larIndex = null; //stores that index of the largest value in kernel
	
	private int stride = 0; //stride length between neurons in vis layer
	
	private int lenKer = 0; //individual kernel length
	private int lenKerX = 0; //individual kernel x and y length
	
	private int lenValsX = 0; //width of vals
	private int lenValsY = 0; //height of vals
	private int lenValsZ = 0; //depth of vals/number of kernels
	private int lenValsXY = 0;
	
	private int lenValsVisX = 0; //X length of vis layer
	private int lenValsVisY = 0; //Y length of vis layer
	private int lenValsVisZ = 0; //Z length of vis layer/length of each kernel
	private int lenValsVisXY = 0; //img length in vis layer
	
	private int jumpValsVisY = 0; //moves down one row
	private int jumpValsVisXY = 0; //moves down all rows on one img
	private int jumpValsVisZ = 0; //moves down one img
	private int jumpValsVisBack = 0; //moves back to start of ker + stride
	private int jumpValsVisStride = 0; //moves vertical stride length
	
	public MaxPool()
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
		
		jumpValsVisY = lenValsVisX - lenKerX;
		jumpValsVisXY = lenValsVisX * (lenKerX - 1);
		jumpValsVisZ = lenValsVisXY - jumpValsVisXY - jumpValsVisY - lenKerX;
		jumpValsVisBack = lenValsVisXY * lenValsVisZ - stride;
		jumpValsVisStride = lenValsVisX * (stride - 1) - stride;
		
		larIndex = new int[lenVals];
		
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		endVals = begVals + lenVals;
		endValsVis = begValsVis + lenValsVis;
	}
	
	public void eval(int threadNum)
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsZ + (float)0.5) * lenValsXY;
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
								if(valsAch[valsVisPos] > valsAch[valsPos])
								{
									valsAch[valsPos] = valsAch[valsVisPos];
									larIndex[valsPos - begVals] = valsVisPos;
								}
								valsVisPos++;
							}
							valsVisPos += jumpValsVisY; //moving down to start of next row in kernel
						}
						valsVisPos += jumpValsVisZ; //moving back to start of next img in kernel
					}
					valsVisPos -= jumpValsVisBack; //moving back to the starting position minus the stride
					valsPos++;
				}
				valsVisPos += jumpValsVisStride; //moving down a row in the local layer
			}
			valsVisPos = begValsVis; //moving back to the start of vis layer to start new kernel
		}
	}
	public void train(int threadNum)
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsY * lenValsZ + (float)0.5) * lenValsX;
		int endValsVisTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsVisY * lenValsVisZ + (float)0.5) * lenValsVisX;
		int valsPos = begVals + (int)(threadSplits * threadNum * lenValsY * lenValsZ + (float)0.5) * lenValsX;
		int valsVisPos = begVals + (int)(threadSplits * threadNum * lenValsVisY * lenValsVisZ + (float)0.5) * lenValsVisX;
		while(valsPos < endValsTemp)
		{
			valsErr[larIndex[valsPos - begVals]] = valsErr[valsPos];
			valsPos++;
		}
		while(valsVisPos < endValsVisTemp)
		{
			if(valsErr[valsVisPos] == 0) //this will result in false flags but extremely rarely
			{
				valsErr[valsVisPos] = valsAch[valsVisPos];
			}
			valsVisPos++;
		}
	}
	
	public int getLayerType()
	{
		return 7;
	}
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + begValsVis + ", " + endValsVis + ", " +
				lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + 
				lenKerX + "\n";
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

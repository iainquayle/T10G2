package engine;

import java.io.IOException;

public class MaxPool extends Layer 
{	
	//weights are unused by this layer
	private int[] larIndex = null; //stores that index of the largest value in kernel
	
	private int stride = 0; //stride length between neurons in vis layer
	
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
		
		jumpValsVisY = lenValsVisX - lenKerX;
		jumpValsVisXY = lenValsVisX * (lenKerX - 1);
		jumpValsVisBack = lenValsVisX * lenKerX - stride;
		jumpValsVisStride = lenValsVisX * (stride - 1) + (lenValsVisX - (lenValsX * stride));
		
		larIndex = new int[lenVals];
		
		if(num != 0)
		{
			begVals = l[num -1].getBegVals() + l[num - 1].getLenVals();
			begValsVis = l[layerVisNum].getBegVals();
		}
		endVals = begVals + lenVals;
		endValsVis = begValsVis + lenValsVis;
	}
	public void save(String loc) throws IOException
	{
	}
	
	public void eval(int threadNum)
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsZ + (float)0.5) * lenValsXY;
		int valsPos = begVals + (int)(threadSplits * threadNum * lenValsZ + (float)0.5) * lenValsXY;
		int valsVisPos = begValsVis + (int)(threadSplits * threadNum * lenValsVisZ + (float)0.5) * lenValsVisXY;;
		int stopValsX = 0;
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endValsTemp)
		{
			stopValsX = valsPos + lenValsX;
			while(valsPos < stopValsX)
			{
				valsAch[valsPos] = valsAch[valsVisPos];
				valsErr[valsPos] = 0;
				stopValsVisY = valsVisPos + jumpValsVisXY;
				while(valsVisPos < stopValsVisY)
				{
					stopValsVisX = valsVisPos + lenKerX;
					while(valsVisPos < stopValsVisX)
					{
						if(valsAch[valsVisPos] > valsAch[valsPos])
						{
							valsAch[valsPos] = valsAch[valsVisPos];
							larIndex[valsPos - begVals] = valsVisPos;
						}
						valsVisPos++;
					}
					valsVisPos += jumpValsVisY;
				}
				valsVisPos -= jumpValsVisBack;
				valsPos++;
			}
			valsVisPos += jumpValsVisStride;
		}
	}
	public void error(int threadNum)
	{
	}
	public void train(int threadNum)
	{
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsZ + (float)0.5) * lenValsXY; //these can be changed to a more accurate split later
		int valsPos = begVals + (int)(threadSplits * threadNum * lenValsZ + (float)0.5) * lenValsXY;
		int endValsVisTemp = begValsVis + (int)(threadSplits * (threadNum + 1) * lenValsVisZ + (float)0.5) * lenValsVisXY;
		int valsVisPos = begValsVis + (int)(threadSplits * threadNum * lenValsVisZ + (float)0.5) * lenValsVisXY;
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

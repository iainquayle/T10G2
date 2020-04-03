package engine;

import java.io.IOException;
import engine.Layer;
import engine.Functions;

public class InConv2D extends Layer
{
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
	
	float data[] = null;
	int dataNum = 0;
	/**
	 * empty constructor
	 */
	public InConv2D()
	{
	}
	
	/**
	 * initializtion of inConv2d
	 * @param l - layer array
	 * @param loc - location of data
	 * @param in - input data object to parse the data
	 * @param io - images data float labels and input data
	 * @param num - layer reference
	 * @throws IOException
	 */
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
	/**
	 * This method is used to evalaute the weights of the different features of the images
	 * @param threadNum
	 */
	public void eval(int threadNum)
	{
		begValsVis = rndIndex * lenValsVis;  //sets the beg val for the data
		endValsVis = begValsVis + lenValsVis;
		int endValsTemp = begVals + (int)(threadSplits * (threadNum + 1) * lenValsZ + (float)0.5)  * lenValsXY;
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
								valsAch[valsPos] += data[valsVisPos] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY;
						}
						valsVisPos += jumpValsVisZ;
					}
					valsAch[valsPos] = Functions.leakyRelu(valsAch[valsPos]); //sigmoid activation on vals ach
					valsVisPos -= jumpValsVisBack;
					weiPos -= lenKer;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
			}
			valsVisPos = begValsVis;
			weiPos += lenKer;
		}
		
		/**
		 * Trains on each image
		 * @param threadNum
		 */
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
		int stopValsVisX = 0;
		int stopValsVisY = 0;
		while(valsPos < endVals) //iterate through each weight set/kernel/XY
		{
			for(int j = 0; j < lenValsY; j++) //iterate through each row
			{
				for(int k = 0; k < lenValsX; k++) //iterate through each col
				{
					while(valsVisPos < endValsVisTemp) //iterate through each XY in the vis layer 
					{
						stopValsVisY = valsVisPos + jumpValsVisXY;  
						while(valsVisPos <= stopValsVisY) //iterate through kernel dim
						{
							stopValsVisX = valsVisPos + lenKerX;
							while(valsVisPos < stopValsVisX) //iterate through kernel dim
							{
								weights[weiPos] += valsErr[valsPos] * data[valsVisPos] * learnRate; //adjusting weights based on ach/rew error
								valsVisPos++;
								weiPos++;
							}
							valsVisPos += jumpValsVisY;
						}
						valsVisPos += jumpValsVisZ;
					}
					valsVisPos -= jumpValsVisBackTemp;
					weiPos -= jumpKerBackTemp;
					valsPos++;
				}
				valsVisPos += jumpValsVisStride;
			}
			valsVisPos = begValsVisTemp;
			weiPos += lenKer;
		}
	}
	/**
	 * gets layer type
	 * @return 4 should not be changed
	 */
	public int getLayerType()
	{
		return 4;
	}
	/**
	 * coverts the important numbers to string
	 * @return
	 */
	public String toString()
	{
		return layerNum + ", " + layerVisNum + ", " + begVals + ", " + endVals + ", " + lenValsX + ", " + lenValsY + ", " + lenValsZ + ", " + lenValsVisX + ", " + lenValsVisY + ", " + lenValsVisZ + ", " + lenKerX + ", " + lenWeis + "\n";
	}
	/**
	 * gets the stride
	 * @return the stride
	 */
	public int getStride() 
	{
		return stride;
	}
	/**
	 * sets the stride
	 * @param stride
	 */
	public void setStride(int stride) 
	{
		this.stride = stride;
	}
	/**
	 * gets kernel length
	 * @return
	 */
	public int getLenKer() 
	{
		return lenKer;
	}
	/**
	 * sets kernel length
	 * @param lenKer
	 */
	public void setLenKer(int lenKer) 
	{
		this.lenKer = lenKer;
	}
	/**
	 * gets kernel dimensions
	 * @return
	 */

	public int getlenKerX() 
	{
		return lenKerX;
	}
	/**
	 * sets kernel dimensions
	 * @param lenKerX
	 */
	public void setlenKerX(int lenKerX) 
	{
		this.lenKerX = lenKerX;
	}
	/**
	 * gets width of vals
	 * @return
	 */
	
	public int getLenValsX() 
	{
		return lenValsX;
	}
	
	/**
	 * sets width of vals
	 * @param lenValsX
	 */
	
	public void setLenValsX(int lenValsX) 
	{
		this.lenValsX = lenValsX;
	}
	
	/**
	 * gets height of vals
	 * @return
	 */
	
	public int getLenValsY() 
	{
		return lenValsY;
	}
	
	/**
	 * sets height of vals
	 * @param lenValsY
	 */
	
	public void setLenValsY(int lenValsY) 
	{
		this.lenValsY = lenValsY;
	}
	
	/**
	 * gets depth of vals
	 * @return
	 */
	
	public int getLenValsZ() 
	{
		return lenValsZ;
	}
	
	/**
	 * sets depth of vals
	 * @param lenValsZ
	 */
	
	public void setLenValsZ(int lenValsZ) 
	{
		this.lenValsZ = lenValsZ;
	}
	
	/**
	 * gets x length of visible layer
	 * @param lenValsVisX
	 */
	
	public int getLenValsVisX() 
	{
		return lenValsVisX;
	}
	
	/**
	 * sets x length of visible layer
	 * @return
	 */
	
	public void setLenValsVisX(int lenValsVisX) 
	{
		this.lenValsVisX = lenValsVisX;
	}
	
	/**
	 * gets y length of visible layer
	 * @return
	 */
	public int getLenValsVisY() 
	{
		return lenValsVisY;
	}
	/**
	 * sets y of visible layer
	 * @param lenValsVisY
	 */
	public void setLenValsVisY(int lenValsVisY) 
	{
		this.lenValsVisY = lenValsVisY;
	}
	/**
	 * gets z length of visible layer
	 * @return
	 */
	public int getLenValsVisZ() 
	{
		return lenValsVisZ;
	}
	/**
	 * sets z length of visible layer
	 * @param lenValsVisZ
	 */
	public void setLenValsVisZ(int lenValsVisZ) 
	{
		this.lenValsVisZ = lenValsVisZ;
	}
	
	/**
	 * gets img length in visible layer
	 * @return
	 */
	public int getLenValsVisXY() 
	{
		return lenValsVisXY;
	}
	/**
	 * sets img length in visible layer
	 * @param lenValsVisXY
	 */
	public void setLenValsVisXY(int lenValsVisXY) 
	{
		this.lenValsVisXY = lenValsVisXY;
	}
}

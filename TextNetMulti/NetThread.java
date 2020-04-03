package engine;

import java.lang.Thread;
import java.io.IOException;
import java.util.Random;

public class NetThread extends Thread
{
	private static Layer[] layers = null;
	private static int lenLayers = 0;
	private static int lenIoPuts = 0;
	
	private static int lenThreads = 0;
	private int threadNum = 0;
	
	private static String loc = null;
	private static String netName = null;
	
	private static Random rnd = new Random();
	
	private static int epoch = 0;
	private static int stop = -1;
	
	private static volatile int[] consIndex = null;
	private static volatile boolean run = true;
	private static volatile boolean relaySave = false;
	private static boolean exit = false;
	private static boolean save = false;
	
	private static long interTime = 0;
	private static long preTime = 0;
	/**
	 * Construction for initializing instance variables
	 * @param locIn - inputted save path location
	 * @param net - net name
	 * @param n - number of threads
	 * @param l - layers array
	 * @param c - consesnus array
	 * @param lenl - length of layer
	 * @param lent - length of threads
	 * @param lenio -length of io
	 */
	public NetThread(String locIn, String net, int n, Layer[] l, int[] c, int lenl, int lent, int lenio)
	{
		loc = locIn;
		netName = net;
		threadNum = n;
		consIndex = c;
		layers = l;
		lenLayers = lenl;
		lenThreads = lent;
		lenIoPuts = lenio;
	}
	
	@Override
	
	/**
	 * This method is used to run the neural network's layers and puts it in its training mode
	 * It causes each layer to evaluate the images
	 * Backpropagates for error in each layer and adjusts the weights (importance of different feautures within the image)
	 * Multiple threads are used to make the process faster and more effecient
	 * The original thread is used to take in user commands
	 * 
	 */
	public void run() 
	{
		while(run)
		{
			for(int j = 0; j < lenLayers; j++)
			{
				layers[j].eval(threadNum);
				cons();
			}
			for(int j = lenLayers - 1; j >= 0; j--)
			{
				layers[j].error(threadNum);
				cons();
				layers[j].train(threadNum);
				cons();
			}
			if(threadNum == 0)
			{
				layers[0].setRndIndex(rnd.nextInt(lenIoPuts));
				epoch++;
				if(epoch == stop)
				{
					run = false;
					relaySave = true;
					System.out.println(layers[0].errString());
					System.out.println("saving and exiting...");
				}
				if(exit)
				{
					run = false;
					System.out.println("exiting...");
				}
				if(save)
				{
					relaySave = true;
					save = false;
					System.out.println("saving...");
				}
				interTime = System.nanoTime() - preTime;
				preTime = System.nanoTime();
			}
			cons();
			if(relaySave)
			{
				save();
				if(threadNum == 0)
				{
					relaySave = false;
				}
				cons();
			}
		}
	}
	/**
	 * The consensus method is used to ensure that each thread knows what the other threads have finished a section
	 * This is done to ensure that each thread is working on something
	 * 
	 */
	private void cons()
	{
		int i = 0;
		consIndex[threadNum]++;
		while(i != lenThreads)
		{
			i = 0;
			while(i == threadNum || (i < lenThreads && consIndex[i] >= consIndex[threadNum]))
			{
				i++;
			}
		}
	}
	/**
	 * Saves layers current weights
	 */
	private void save()
	{
		for(int i = threadNum; i < lenLayers; i += lenThreads)
		{
			try 
			{
				layers[i].save(loc + "//" + netName + "//" + netName);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			System.out.println("Layer " + i + " saved");
		}
	}
	/**
	 * sets the exit state
	 */
	public void setExit()
	{
		exit = true;
	}
	/**
	 * sets the save state
	 */
	public void setSave()
	{
		save = true;
	}
	/**
	 * gets the epoch number
	 * @return epoch number
	 */
	
	public int getEpoch()
	{
		return epoch;
	}
	/**
	 * gets the current index that a thread points to in the consensus index
	 * @return the current index at that point
	 */
	public int getConsIndex()
	{
		return consIndex[threadNum];
	}
	/**
	 *  gets the time
	 * @return the time
	 */
	public long getInterTime()
	{
		return interTime;
	}
	/**
	 * virtual method
	 */
	public void debug()
	{
		
	}
}

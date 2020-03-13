package engine;

import java.lang.Thread;
import java.io.IOException;
import java.util.Random;

public class NetThread extends Thread
{
	private static Layer[] layers = null;
	private static int lenLayers = 0;
	
	private static int lenThreads = 0;
	private int threadNum = 0;
	
	private static String loc = null;
	private static String netName = null;
	
	private static Random rnd = new Random();
	
	private static int epoch = 0;
	private static int stop = 10000;
	
	private static volatile int[] consIndex = null;
	private static volatile boolean run = true;
	private static volatile boolean relaySave = false;
	private static boolean exit = false;
	private static boolean save = false;
	
	private static long interTime = 0;
	private static long preTime = 0;
	
	public NetThread(int n, Layer[] l, int[] c, int lenl, int lent)
	{
		threadNum = n;
		consIndex = c;
		layers = l;
		lenLayers = lenl;
		lenThreads = lent;
	}
	
	@Override
	public void run() 
	{
		System.out.println("run");
		while(run)
		{
			for(int j = 0; j < lenLayers; j++)
			{
				layers[j].eval(threadNum);
				cons();
			}
			for(int j = lenLayers - 1; j >= 0; j++)
			{
				layers[j].error(threadNum);
				cons();
				layers[j].train(threadNum);
				cons();
			}
			if(threadNum == 0)
			{
				layers[0].setRndIndex(rnd.nextInt());
				epoch++;
				if(epoch == stop)
				{
					run = false;
					relaySave = true;
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
				interTime = System.currentTimeMillis() - preTime;
				preTime = System.currentTimeMillis();
				System.out.println(interTime);
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
	
	private void cons()
	{
		int i = 0;
		consIndex[threadNum]++; //should make a buffered state set up
		while(i != lenThreads)
		{
			i = 0;
			while(i == threadNum || (i < lenThreads && consIndex[i] >= consIndex[threadNum]))
			{
				i++;
			}
		}
	}
	
	private void save()
	{
		for(int i = threadNum; i < lenLayers; i += lenThreads)
		{
			try 
			{
				layers[i].save(loc + "//" + netName);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setExit()
	{
		exit = true;
	}
	public void setSave()
	{
		save = true;
	}
	public int getEpoch()
	{
		return epoch;
	}
	public int getConsIndex()
	{
		return consIndex[threadNum];
	}
	public long getInterTime()
	{
		return interTime;
	}
	
	public void debug()
	{
		
	}
}

package engine;

import java.lang.Thread;
import java.io.IOException;
import java.lang.Runnable;
import java.util.Random;

public class NetThread implements Runnable
{
	private Layer[] layers = null;
	private int lenLayers = 0;
	
	private Thread thread = null;
	private int lenThreads = 0;
	private int threadNum = 0;
	
	private static String loc = null;
	private static String netName = null;
	
	private static Random rnd = new Random();
	
	private int epoch = 0;
	private static int stop = 0;
	
	private static volatile boolean[] states = null;
	private static volatile boolean run = true;
	private static volatile boolean relaySave = false;
	private static boolean exit = false;
	private static boolean save = false;
	
	private static long interTime = 0;
	private static long preTime = 0;
	
	public NetThread(int n)
	{
		threadNum = n;
		thread = new Thread();
		thread.setPriority(Thread.MAX_PRIORITY);
	}
	
	public void start() 
	{
		thread.run();
	}
	@Override
	public void run() 
	{
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
			epoch++;
			if(threadNum == 0)
			{
				layers[0].setRndIndex(rnd.nextInt());
				if(epoch == stop)
				{
					run = false;
					relaySave = true;
				}
				if(exit)
				{
					run = false;
				}
				if(save)
				{
					relaySave = true;
					save = false;
				}
				interTime = System.currentTimeMillis() - preTime;
				preTime = System.currentTimeMillis();
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
		states[threadNum] = !states[threadNum]; //should make a buffered state set up
		while(i != lenThreads)
		{
			i = 0;
			while(i < lenThreads && states[i] == states[threadNum])
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
	
	public void setStatics(Layer[] l, int lenl, int lent)
	{
		layers = l;
		lenLayers = lenl;
		lenThreads = lent;
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
	public boolean getState()
	{
		return states[threadNum];
	}
	public long getInterTime()
	{
		return interTime;
	}
	
	public void debug()
	{
		
	}
}

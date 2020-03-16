package engine;

import java.lang.System;
import java.lang.Thread;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import engine.Layer;
import engine.Dense;
import engine.Conv2D;
import engine.Input;
import engine.Output;
import engine.NetThread;
@SuppressWarnings("unused")

public class TextNet
{
	private static Thread thisThread = Thread.currentThread();
	
	private static Scanner com = new Scanner(System.in);
	
	private static Layer[] layers = null;
	private static int lenLayers = 0;
	
	private static Thread[] threads = null;
	private static NetThread mainNetThread = null;
	private static int lenThreads = 0;
	private static boolean exit = false;
	
	private static String loc = System.getProperty("user.dir") + "\\";
	private static String netName = null;
	private static String dataName = null;
	
	private static float[][] ioPuts = null;
	private static int lenIoPuts = 0;
	
	public static void main(String[] args) 
	{
		try 
		{
			init();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.gc();
		print();
		thisThread.setPriority(Thread.MIN_PRIORITY);
		
		run();

		command();
	}
	
	private static void init() throws IOException
	{
		int temp = 0; //use can be for anything, be careful
		
		System.out.println("Running Text Net Multi training enviroment.");
		System.out.print("Training data name (must be in csv save, but dont add .csv): ");
		dataName = com.nextLine();
		System.out.print("Net config name (must be in csv save, but dont add .csv): ");
		netName = com.nextLine();
		System.out.print("Number of threads to run (1/2 num of cores suggested)(> 0): ");
		lenThreads = com.nextInt();
		
		System.out.println("Data");
		InputData file = new InputData(loc + dataName + "Splits.csv");
		int[] splits = file.toIntArray();
		file.setFile(loc + dataName + ".csv");
		ioPuts = file.toFloatArray2D(splits);
		ioPuts[1] = file.normalizeFloatArray(ioPuts[1]);               //this is currently just for mnist_train, will make better set up in future
		lenIoPuts = ioPuts[0].length / splits[0];
		file.close();
		System.gc();
		
		System.out.println("Init");
		file.setFile(loc + netName + "\\" + netName + ".csv");
		lenLayers = file.nextInt();
		layers = new Layer[lenLayers];
		for(int i = 0; i < lenLayers; i++)
		{
			temp = file.nextInt();
			if(temp == 0)
			{
				layers[i] = new Dense();
			}
			else if(temp == 1)
			{
				layers[i] = new Conv2D();
			}
			else if(temp == 2)
			{
				//layers[i] = new Input();
			}
			else if(temp == 3)
			{
				layers[i] = new InDense();
			}
			else if(temp == 4)
			{
				layers[i] = new InConv2D();
			}
			else if(temp == 5)
			{
				//layers[i] = new Output();
			}
			else if(temp == 6)
			{
				layers[i] = new OutDense();
			}
			else if(temp == 7)
			{
				layers[i] = new MaxPool();
			}
		}
		temp = 0;
		for(int i = 0; i < lenLayers; i++)
		{
			System.out.println("Init layer " + i);
			layers[i].init(layers, loc + netName + "\\" + netName, file, ioPuts, i);
			temp += layers[i].getLenVals();
		}
		layers[0].setStatics(new float[temp], new float[temp], lenThreads);
		file.close();
		
		threads = new NetThread[lenThreads];
		int[] arr = new int[lenThreads];
		for(int i = lenThreads - 1; i >= 0; i--)
		{
			mainNetThread = new NetThread(loc, netName, i, layers, arr, lenLayers, lenThreads, lenIoPuts); 
			threads[i] = mainNetThread; //the new object is set to main thread so when the last one is created, the zeroth object is referenced
		}
	}
	
	private static void run()
	{
		for(int i = 0; i < lenThreads; i++)
		{
			threads[i].start();
		}
	}
	private static void command()
	{
		String command = null;
		while(!exit)
		{
			System.out.print("\nerror, interTime, epoch, setLearn, setStop, save, saveExit, exit: ");
			command = com.nextLine();
			if(command.equals("error"))
			{
				System.out.println(layers[0].errString());
			}
			else if(command.equals("interTime"))
			{
				System.out.println(mainNetThread.getInterTime());
			}
			else if(command.equals("epoch"))
			{
				System.out.print(mainNetThread.getEpoch());
			}
			else if(command.equals("setLearn"))
			{
				System.out.println("Current rate: " + layers[0].getLearnRate() + "   New rate: ");
				layers[0].setLearnRate(com.nextFloat());
			}
			else if(command.equals("save"))
			{
				mainNetThread.setSave();
			}
			else if(command.equals("saveExit"))
			{
				mainNetThread.setSave();
				mainNetThread.setExit();
				exit = true;
			}
			else if(command.equals("exit"))
			{
				mainNetThread.setExit();
				exit = true;
			}
			else
			{
				System.out.println();
			}
		}
	}
	
	public static void print()
	{
		String str = "";
		for(int i = 0; i < lenLayers; i++)
		{
			str += layers[i];
		}
		System.out.println(str);
	}
}

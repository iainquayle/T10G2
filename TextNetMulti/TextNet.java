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
	
	private static Layer[] layers = null;
	private static NetThread[] threads = null;
	private static int lenThreads = 0;
	private static boolean exit = false;
	
	private static String loc = System.getProperty("user.dir") + "\\";
	private static String netName = null;
	private static String dataName = null;
	
	private static float[][] ioPuts = null;
	private static int lenIoPuts = 0;
	private static int lenLayers = 0;
	
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
		
		try 
		{
			save();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static void init() throws IOException
	{
		Scanner com = new Scanner(System.in);
		System.out.print("Training data name: ");
		dataName = com.nextLine();
		System.out.print("Net config name: ");
		netName = com.nextLine();
		System.out.print("Number of threads to run (number of cores suggested)(> 0):");
		lenThreads = com.nextInt();
		com.close();
		
		System.out.println("Data");
		InputData file = new InputData(loc + dataName + "Splits.csv");
		int[] splits = file.toIntArray();
		file.setFile(loc + dataName + ".csv");
		ioPuts = file.toFloatArray2D(splits);
		lenIoPuts = ioPuts[0].length / splits[0];
		file.close();
		
		System.out.println("Init");
		int temp = 0;
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
				layers[i] = new Input();
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
				layers[i] = new Output();
			}
			else if(temp == 6)
			{
				layers[i] = new OutDense();
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
		com.close();
		
		threads = new NetThread[lenThreads];
		for(int i = 0; i < lenThreads; i++)
		{
			threads[i] = new NetThread(i);
		}
	}
	private static void save() throws IOException
	{
		System.out.println("Save");
		for(int i = 0; i < lenLayers; i++)
		{
			System.out.println("Save Layer" + i);
			layers[i].save(loc + netName + "\\" + netName);
		}
	}
	
	private static void run()
	{
		for(int i = 0; i < lenThreads; i++)
		{
			threads[i].run();
		}
	}
	private static void command()
	{
		Scanner in = new Scanner(System.in);
		String command = null;
		while(!exit)
		{
			System.out.print("error, interTime, epoch, setLearn, save, saveExit, exit");
			command = in.nextLine();
			if(command.equals("error"))
			{
				System.out.println(layers[0].errString());
			}
			else if(command.equals("time"))
			{
				System.out.println(threads[0].getInterTime());
			}
			else if(command.equals("epoch"))
			{
				for(int i = 0; i < lenThreads; i++)
				{
					System.out.print(threads[i].getEpoch() + "   ");
					System.out.print('\n');
				}
			}
			else if(command.equals("setLearn"))
			{
				System.out.println("Current rate: " + layers[0].getLearnRate() + "   New rate: ");
				layers[0].setLearnRate(in.nextFloat());
			}
			else if(command.equals("save"))
			{
				threads[0].setSave();
			}
			else if(command.equals("saveExit"))
			{
				threads[0].setSave();
				exit = true;
				threads[0].setExit();
			}
			else if(command.equals("exit"))
			{
				exit = true;
				threads[0].setExit();
			}
			else
			{
				System.out.println();
			}
		}
		in.close();
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

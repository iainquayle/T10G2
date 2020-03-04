package engine;

import java.lang.System;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
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
	private static Layer[] layers = null;
	private static NetThread[] threads = null;
	@SuppressWarnings("deprecation")
	private static Integer rndIndex = new Integer(0);
	private static Random rnd = new Random();
	private static String loc = System.getProperty("user.dir") + "\\";
	private static String netName = null;
	private static String dataName = null;
	private static float[][] ioPuts = null;
	private static int lenIoPuts = 0;
	private static int lenLayers = 0;
	private volatile static int threadPos = 0;
	
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
		
		run();
		
		try 
		{
			save();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException
	{
		Scanner com = new Scanner(System.in);
		System.out.print("Training data name: ");
		dataName = com.nextLine();
		System.out.print("Net config name: ");
		netName = com.nextLine();
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
		file.setFile(loc + netName + ".csv");
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
			layers[i].init(layers, loc + netName, file, ioPuts, i);
			temp += layers[i].getLenVals();
		}
		layers[0].setStatRefs(rndIndex, new float[temp], new float[temp]);
		file.close();
		com.close();
	}
	public static void save() throws IOException
	{
		System.out.println("Save");
		for(int i = 0; i < lenLayers; i++)
		{
			System.out.println("Save Layer" + i);
			layers[i].save(loc + netName);
		}
	}
	
	public static void run()
	{
		long preTime = 0;
		long curTime = System.nanoTime();
		for(int i = 0; i < 100; i++)
		{
			for(int j = 0; j < lenLayers; j++)
			{
				preTime = curTime;
				layers[j].eval();
				curTime = System.nanoTime();
				System.out.println("Eval " + j + " time " + (curTime - preTime));
			}
			for(int j = 0; j < lenLayers; j++)
			{
				layers[j].train();
				System.out.println("Train " + j);
			}
			System.out.println(i);
			rndIndex = rnd.nextInt(lenIoPuts);
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

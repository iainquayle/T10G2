package engine;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
	private static Date time = null;
	private static Integer rndIndex = null;
	private static float[][] IOputs;
	private static int lenData;
	private static int lenLayers = 0;
	private volatile static int threadPos = 0;
	
	public static void main(String[] args) 
	{
		try 
		{
			init(args[0]);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		run();
		
		try 
		{
			save(args[0]);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void init(String loc) throws IOException
	{
		int temp = 0;
		Scanner in = new Scanner(new File(loc + "config"));
		int layersLen = in.nextInt();
		layers = new Layer[layersLen];
		for(int i = 0; i < layersLen; i++)
		{
			temp = in.nextInt();
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
		for(int i = 0; i < layersLen; i++)
		{
			layers[i].init(layers, loc, in, IOputs, i);
			temp += layers[i].getLenVals();
		}
		layers[0].setStatRefs(rndIndex, new float[temp], new float[temp]);
		in.close();
	}
	public static void save(String loc) throws IOException
	{
		for(int i = 0; i < lenLayers; i++)
		{
				layers[i].save(loc);
		}
	}
	
	public static void run()
	{
		for(int i = 0; i < 10000; i++)
		{
			for(int j = 0; j < lenLayers; j++)
			{
				layers[j].eval();
			}
			for(int j = 0; j < lenLayers; j++)
			{
				layers[j].train();
			}
		}
	}
}

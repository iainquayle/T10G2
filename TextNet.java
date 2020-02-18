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
@SuppressWarnings("unused")

public class TextNet implements Runnable
{
	static Layer[] layers = null;
	static Thread[] threads = null;
	static Date time = null;
	static int layersLen = 0;
	static int layerCoun = 0;
	static int neurCoun = 0;
	volatile static int threadPos = 0;
	
	public static void main(String[] args) 
	{
		//init(args[0])
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
		}
		for(int i = 0; i < layersLen; i++)
		{
			layers[i].init(loc, in, i);
		}
		in.close();
	}
	public static void save()
	{
		
	}
	
	@Override
	public void run() 
	{
	}
	public void start() 
	{
	}
}

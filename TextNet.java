package engine;

import java.lang.Thread;
import java.lang.Runnable;
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
		
		//
	}
	
	public static void init(String loc) throws IOException
	{
		File file = null;
		int data[] = null;
		int filePos = 0;
		@SuppressWarnings("null")
		
		int layersLen = data[0];
		filePos++;
		
		layers = new Layer[layersLen];
		for(int i = 0; i < layersLen; i++)
		{
			if(data[filePos + i] == 0)
			{
				layers[i] = new Dense();
			}
			else if(data[filePos + 1] == 1)
			{
				layers[i] = new Conv2D();
			}
		}
		filePos += layersLen;
	}
	public static void save()
	{
		
	}
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
	}
	public void start() 
	{
	}
}

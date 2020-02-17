package engine;

import java.lang.Thread;
import java.lang.Runnable;
import engine.Layer;
import engine.Dense;
import engine.Conv2D;
@SuppressWarnings("unused")

public class TextNet implements Runnable
{
	static Layer[] layers = null;
	static int layersLen = 0;
	static int layerCoun = 0;
	static int neurCoun = 0;
	volatile int threadPos = 0;
	
	public static void main(String[] args) 
	{

	}
	
	public static void init()
	{
		String file = null;
		int layersLen = 0;
		layers = new Layer[layersLen];
		for(int i = 0; i < 0; i++)
		{
			if(false)
			{
				layers[i] = new Dense();
			}
			else if(false)
			{
				layers[i] = new Conv2D();
			}
		}
	}
	public static void save()
	{
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
}

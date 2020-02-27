package engine;

import java.lang.Thread;
import java.lang.Runnable;

@SuppressWarnings("unused")
public class NetThread implements Runnable
{
	private NetThread[] selfRef = null;
	private Thread thread = null;
	private int num = 0;
	private boolean state = false;
	private boolean seen = false;
	
	public NetThread(String str, int n)
	{
		thread = new Thread(str);
		num = n;
	}
	
	@Override
	public void run() 
	{
	}
	public void start() 
	{
	}
}

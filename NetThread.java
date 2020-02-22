package engine;

import java.lang.Thread;
import java.lang.Runnable;

public class NetThread implements Runnable
{
	NetThread[] selfRef = null;
	Thread thread = null;
	int num = 0;
	boolean state = false;
	
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

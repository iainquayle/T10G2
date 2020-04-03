package engine;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * 
 * @author Osama Bamatraf
 * @documenter Osama Bamatraf
 * @reviewer Iain Quayle
 *
 */
public class InputData
{

	InputStream file = null;
	BufferedInputStream reader = null;

	/**
	 * initializes the file and the reader
	 * 
	 * @param loc - location of data file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public InputData(String loc) throws IOException, FileNotFoundException
	{
		file = new FileInputStream(loc);
		reader = new BufferedInputStream(file);
	}
	
	/**
	 * reads the data file looking for the next float number
	 * @return the float value it encounters
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public float nextFloat() throws IOException, FileNotFoundException
	{
		String hold = "";
		char cur = (char)reader.read();
		boolean neg = false;
		boolean dec = false;
		boolean exp = false;
		boolean num = false;
		boolean broken = false;
		while(!broken)
		{
			if(!num && !neg && !dec && cur == '-')
			{
				hold += '-';
				neg = true;
				cur = (char)reader.read();
			}
			else if(!dec && cur == '.')
			{
				hold += '.';
				dec = true;
				cur = (char)reader.read();
			}
			else if(num && !exp && cur == 'E')
			{
				hold += 'E';
				exp = true;
				cur = (char)reader.read();
			}
			else if(exp && hold.charAt(hold.length() - 1) == 'E' && cur == '-')
			{
				hold += '-';
				exp = true;
				cur = (char)reader.read();
			}
			else if(cur >= '0' && cur <= '9')
			{
				hold += cur;
				num = true;
				cur = (char)reader.read();
			}
			else if((cur == '.' || cur == '-') && (neg || dec) && !num)
			{
				hold = "";
				neg = false;
				dec = false;
			}
			else if(!num)
			{
				cur = (char)reader.read();
				hold = "";
				neg = false;
				dec = false;
			}
			else
			{
				broken = true;
			}
		}
		return Float.parseFloat(hold);
	}
	
	/**
	 * reads the data file looking for the next int number
	 * @return the int value it encounters
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int nextInt() throws IOException, FileNotFoundException
	{
		String hold = "";
		char cur = (char)reader.read();
		boolean neg = false;
		boolean num = false;
		boolean broken = false;
		while(!broken)
		{
			if(!num && !neg && cur == '-')
			{
				hold += '-';
				neg = true;
				cur = (char)reader.read();
			}
			else if(cur >= '0' && cur <= '9')
			{
				hold += cur;
				num = true;
				cur = (char)reader.read();
			}
			else if(cur == '-' && neg && !num)
			{
				hold = "";
				neg = false;
			}
			else if(!num)
			{
				cur = (char)reader.read();
				hold = "";
				neg = false;
			}
			else
			{
				broken = true;
			}
		}
		return Integer.parseInt(hold);
	}
	/**
	 * check whether the file has the next number or not
	 * @return true or false depending on whether there is a num or not
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public boolean hasNextNum() throws IOException, FileNotFoundException
	{
		reader.mark(10000);
		char cur = (char)reader.read();
		while(cur != 65535)
		{
			if(cur >= '0' && cur <= '9')
			{
				reader.reset();
				return true;
			}
			cur = (char)reader.read();
		}
		reader.reset();
		return false;
	}
	
	/**
	 * converts data to an int array
	 * @return int array of data
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int[] toIntArray() throws IOException, FileNotFoundException
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		while(hasNextNum())
		{
			list.add(nextInt());
		}
		int[] arr = new int[list.size()];
		for(int i = 0; i < list.size(); i++)
		{
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	/**
	 * converts data to a float array
	 * @return float array
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public float[] toFloatArray() throws IOException, FileNotFoundException
	{
		ArrayList<Float> list = new ArrayList<Float>();
		while(hasNextNum())
		{
			list.add(nextFloat());
		}
		float[] arr = new float[list.size()];
		for(int i = 0; i < list.size(); i++)
		{
			arr[i] = list.get(i);
		}
		return arr;
	}
	
	/**
	 * converts a 1 dimensional float array into a 2 dimensional float array
	 * @param splits 
	 * @return 2 dimensional float array
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public float[][] toFloatArray2D(int[] splits) throws IOException, FileNotFoundException
	{
		int total = 0;
		for(int i = 0; i < splits.length; i++)
		{
			total += splits[i];
		}
		float[] inter = toFloatArray();
		int indeces = inter.length / total;
		float[][] arr = new float[splits.length][];
		for(int i = 0; i < splits.length; i++)
		{
			arr[i] = new float[splits[i] * indeces];
		}
		int pos = 0;
		for(int i = 0; i < indeces; i++)
		{
			for(int j = 0; j < splits.length; j++)
			{
				for(int k = 0; k < splits[j]; k++)
				{
					arr[j][i * splits[j] + k] = inter[pos];
					pos++;
				}
			}
		}
		return arr;
	}
	
	/**
	 * normalizes the array
	 * @param arr array to be normalized
	 * @return the normalized float array
	 */
	public float[] normalizeFloatArray(float[] arr)
	{
		float larVal = 0;
		for(int i = 0; i < arr.length; i++)
		{
			if(arr[i] > larVal)
			{
				larVal = arr[i];
			}
		}
		larVal = 1/larVal;
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] *= larVal;
		}
		return arr;
	}
	/**
	 * 
	 * @param loc
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void setFile(String loc) throws IOException, FileNotFoundException
	{
		reader.close();
		file.close();
		file = new FileInputStream(loc);
		reader = new BufferedInputStream(file);
	}
	
	/**
	 * closes the reader and file
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void close() throws IOException, FileNotFoundException
	{
		reader.close();
		file.close();
	}
	/**
	 * prints the values of the array
	 * mostly for debugging purposes
	 * @param arr array to be printed
	 */
	public void printArr(float[] arr)
	{
		for(int i = 0; i < arr.length; i++)
		{
			System.out.println(arr[i]);
		}
	}
}

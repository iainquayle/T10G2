package engine;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InputData {
	/**
	 * @param file is a string indicating the file path for FileReader
	 * @return a single float array containing all the data for the images
	 * @throws IOException 
	 */
	public static float[][] csvToFloatArray(String file, int[] splits) throws IOException, FileNotFoundException
	{
		int total = 0;
		for(int i = 0; i < splits.length; i++)
		{
			total += splits[i];
		}
		Scanner in = new Scanner(new File(file));
		ArrayList<Float> list = new ArrayList<Float>();
		while(in.hasNextFloat())
		{
			list.add(in.nextFloat());
		}
		float[][] arr = new float[splits.length][];
		int len = list.size() / total;
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] = new float[splits[i] * len];
		}
		int pos = 0;
		for(int i = 0; i < len; i++)
		{
			for(int j = 0; i < arr.length; j++)
			{
				for(int k = 0; k < splits[j]; k++)
				{
					arr[j][i * splits[j] + k] = list.get(pos);
					pos++;
				}
				pos++;
			}
			pos++;
		}
		in.close();
		return arr;
	}
}

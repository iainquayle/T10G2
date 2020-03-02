package engine;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * This class uses the external openCSV library.
 * http://opencsv.sourceforge.net/
 */
public class InputData {
	/**
	 * Hideous code ahead...
	 * @param file is a string indicating the file path for FileReader
	 * @return a single float array containing all the data for the images
	 * @throws IOException 
	 * @throws CsvException
	 */
	public float[] csvToFloatArray(String file) throws IOException, FileNotFoundException
	{
		Scanner in = new Scanner(new File(file));
		ArrayList<Float> list = new ArrayList<Float>();
		while(in.hasNextFloat())
		{
			list.add(in.nextFloat());
		}
		float[] arr = new float[list.size()];
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] = list.get(i);
		}
		in.close();
		return arr;
	}
}

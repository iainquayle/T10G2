package inputData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
/*
 * This class uses the external openCSV library.
 * http://opencsv.sourceforge.net/
 */
public class inputData {
	
	@SuppressWarnings("deprecation")
	/**
	 * Hideous code ahead...
	 * @param file is a string indicating the file path for FileReader
	 * @return a single float array containing all the data for the images
	 * @throws IOException 
	 * @throws CsvException
	 */
	
	public Float[] csvToFloatArray(String file) throws IOException, CsvException {
		 try {
			FileReader rFile = new FileReader(file);
			
			CSVReader readCSV = new CSVReader(rFile);
			String[] dataString;
			List<Float> dataFloatList = new ArrayList<Float>();
			while ((dataString = readCSV.readNext()) != null) { 					//reading the CSV data into a string array
	            for (String cell : dataString) { 									//adding each element of the string array into a Float ArrayList
	            	dataFloatList.add(new BigDecimal(cell).setScale(1, BigDecimal.ROUND_CEILING).floatValue());

	                
	            } 
	            
			}
			Float[] dataFloatArray = new Float[dataFloatList.size()];  
			dataFloatArray = dataFloatList.toArray(dataFloatArray);  //Converting the Float ArrayList into a Float Array
			readCSV.close();
			return dataFloatArray;
			
		
			} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		 
		 
		 
	}

}

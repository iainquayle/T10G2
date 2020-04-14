import java.util.ArrayList;

public class Brackets extends Equation {
	
	
	public ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();

 	int indexOfOpen= 0;
	int indexOfClose = 0;
	
	
	
	/**
	 * constructor
	 * @param testCase
	 */
	public Brackets(String testCase) {
		
		super(testCase);
	}
	
	
	
	
	/**
	 * Sets formulaSplitInBrackets
	 */
	public void setFormulaSplitInBrackets() {
		
		for(String b : super.getFormulaSplitAll()) 
		{
			formulaSplitInBrackets.add(b);
		}

	}
	
	
	
	/**
	 * gets formulaSplitInBrackets
	 * @return 
	 */
	public ArrayList<String> getFormulaSplitInBrackets() { 
		
		return this.formulaSplitInBrackets;
	}
	
	
	
	/**
	 * 
	 * @param formulaSplitInBrack
	 * @return
	 * @throws Exception
	 */
public ArrayList<String> converStrToListWithBrack(ArrayList<String> formulaSplitInBrack) throws Exception{
		try {
		
		int count = 0 ;
		
		int indexOfOpen= 0;
		
		int indexOfClose = 0;

		while(formulaSplitInBrack.contains("(")) 
		{
			indexOfOpen = formulaSplitInBrack.indexOf("(");
			
			for(int i = 0 ; i < formulaSplitInBrack.size(); i ++)
			{
				if (formulaSplitInBrack.get(i).equals("(")) 
				{
					count++;
				}
				if(formulaSplitInBrack.get(i).equals(")")) 
				{
					count--;
					
					if (count == 0) {
						indexOfClose = i;
					


						ArrayList<String> sibling = new ArrayList<String>(formulaSplitInBrack.subList(indexOfOpen + 1 , indexOfClose));
						if(sibling.size() == 0 ) 
						{
							throw new Exception();
						}
						
						if(sibling.contains("("))
						{
							converStrToListWithBrack(sibling);	
						}
						
						
							try 
							{
								Run.run(sibling);
							} catch (Exception e) 
							{
								throw new Exception();
							}
							int indClose = indexOfClose;
							int indOpen = indexOfOpen;
							

							formulaSplitInBrack.subList(indOpen + 1, indClose).clear();
						
							formulaSplitInBrack.addAll(indOpen + 1, sibling);
							
					
							
							if (sibling.size() == 1 ) 
							{
								if( indexOfOpen > 0) {
								if(!formulaSplitInBrack.get(indOpen - 1).equals("*") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("-") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("+") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("/") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("^"))
								{
									
									double numInBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen - 1));
									double numOutBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen + 1));
									
									numOutBrack = numInBrack * numOutBrack;
									
									if(numOutBrack % 1 == 0) 
									{
										int numOut = (int) numOutBrack;
										String changes = Integer.toString(numOut);
										formulaSplitInBrack.subList(indOpen - 1, indOpen + 3).clear();
										formulaSplitInBrack.add(indexOfOpen - 1, changes);
									}
									
									else 
									{
									String changes = String.format("%.3f", numOutBrack);
								
									formulaSplitInBrack.subList(indOpen - 1, indOpen + 3).clear();
									formulaSplitInBrack.add(indexOfOpen - 1, changes);
									}
									
								}
								
								else 
								{
									formulaSplitInBrack.remove("(");
									formulaSplitInBrack.remove(")");
									
								}
								
								}
								
								else 
								{
									formulaSplitInBrack.remove("(");
									formulaSplitInBrack.remove(")");
								}

							}
							
							if(formulaSplitInBrack.contains("(")) 
							{
								try {
									 converStrToListWithBrack(formulaSplitInBrack);
								}
								catch (Exception e) {
									throw new Exception();
								}
								
							}

						}
					
				}
				}
		
			if (count > 0 || count < 0) 
			{
				
				throw new Exception();
			}
		
		if (!formulaSplitInBrack.contains("(")) 
		{
			Run.run(formulaSplitInBrack);
		}
		
	}
		
		
		
		return formulaSplitInBrack;


}
		catch (Exception e) 
		{
			throw new Exception();
	    }
}
	
}

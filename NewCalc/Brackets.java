import java.util.ArrayList;

public class Brackets extends Equation {
	
	protected static ArrayList<String> formulaSplitInBrackets = new ArrayList<String>();
//	protected static ArrayList<String> sibling = new ArrayList<String>();
 	int indexOfOpen= 0;
	int indexOfClose = 0;

	public Brackets(String testCase) {
		super(testCase);
	}
	
	public void setFormulaSplitInBrackets() {
		for(String b : super.getFormulaSplitAll()) {
			formulaSplitInBrackets.add(b);
		}

	}
	
	public ArrayList<String> getFormulaSplitInBrackets() { 
		return Brackets.formulaSplitInBrackets;
	}
	
public ArrayList<String> converStrToListWithBrack(ArrayList<String> formulaSplitInBrack) throws Exception{
		
		int count = 0 ;
		int indexOfOpen= 0;
		int indexOfClose = 0;

		while(formulaSplitInBrack.contains("(")) {
			
			indexOfOpen = formulaSplitInBrack.indexOf("(");
			for(int i = 0 ; i < formulaSplitInBrack.size(); i ++) {
				if (formulaSplitInBrack.get(i).equals("(")) {
					count++;
				}
				if(formulaSplitInBrack.get(i).equals(")")) {
					count--;
					
					if (count == 0) {
						indexOfClose = i;
					


						ArrayList<String> sibling = new ArrayList<String>(formulaSplitInBrack.subList(indexOfOpen + 1 , indexOfClose));
						
						if(sibling.contains("(")) {
							converStrToListWithBrack(sibling);	
						}
						
						
							Run.run(sibling.toString(), sibling);
							int indClose = indexOfClose;
							int indOpen = indexOfOpen;
							

							formulaSplitInBrack.subList(indOpen + 1, indClose).clear();
						
							formulaSplitInBrack.addAll(indOpen + 1, sibling);
							
					
							
							if (sibling.size() == 1 ) {
								if( indexOfOpen > 0) {
								if(!formulaSplitInBrack.get(indOpen - 1).equals("*") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("-") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("+") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("/") &&
										!formulaSplitInBrack.get(indOpen - 1).equals("^")) {
									double numInBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen - 1));
									double numOutBrack = Double.parseDouble(formulaSplitInBrack.get(indOpen + 1));
									numOutBrack = numInBrack * numOutBrack;
									String changes = Double.toString(numOutBrack);
									formulaSplitInBrack.subList(indOpen - 1, indOpen + 3).clear();
									formulaSplitInBrack.add(indexOfOpen - 1, changes);
								
									
								}
								else {
									formulaSplitInBrack.remove("(");
									formulaSplitInBrack.remove(")");
									
								}
								
								}
								else {
									formulaSplitInBrack.remove("(");
									formulaSplitInBrack.remove(")");
								}

							}
							
							if(formulaSplitInBrack.contains("(")) {
								 converStrToListWithBrack(formulaSplitInBrack);
							}

						}
	
				}
				}
			
	

				}
		

		if (!formulaSplitInBrack.contains("(")) {
			Run.run(formulaSplitInBrack.toString(), formulaSplitInBrack);
		}
		
		
		

		return formulaSplitInBrack;
	}


}

package engine;
import engine.Layer;
import engine.Functions;

public class Dense extends Layer
{
	//other than indexing values, this is multi threading safe (not yet implemented if ever)
	int lenWeiSet = 0; //number of weights per neuron
	
	public Dense()
	{
	}
	
	public void eval()
	{
		int weiPos = 0;
		for(int i = begVals; i < endVals; i++) //iterate through the vals
		{
			valsAch[i] = 0;
			for(int j = begValsVis; j < endValsVis; j++) //iterate through the vis layer vals
			{
				valsAch[i] += visValsAch[j] * weights[weiPos]; //summing the activations of the vis layer mulled by weights
				valsReq[i] = 0; //clearing req vals for the training pass
				weiPos++;
			}
			valsAch[i] = Functions.sigmoid(valsAch[i]);  //sigmoid activation on value achieve
			weiPos++;
		}
	}
	public void train()
	{
		int weiPos = 0;
		for(int i = begVals; i < endVals; i++)
		{
			valsReq[i] = Functions.stepNeg(valsReq[i]); //step applied to req vals
		}
		for(int i = begVals; i < endVals; i++) //iterate through vals
		{
			for(int j = begValsVis; j < endValsVis; j++) //iterate through vis layer vals
			{
				visValsReq[j] += valsReq[i] * weights[weiPos];  //updating the vis layers requested values
				weights[weiPos] += (valsReq[i] - visValsAch[j]) * learnRate;   //updating the weights based on requested values and vis layer achieved values
				weiPos++;
			}
		}
	}
	
	public int getLenWeiSet() 
	{
		return lenWeiSet;
	}
	public void setLenWeiSet(int lenWeiSet) 
	{
		this.lenWeiSet = lenWeiSet;
	}
}

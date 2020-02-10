package engine;
import engine.Layer;
import engine.Functions;

public class Dense extends Layer
{
	//other than indexing values, this is multi threading safe (not yet implemented if ever)
	public Dense()
	{
	}
	
	public void eval()
	{
		i = 0;
		int weiPos = 0;
		while(i < lenVals)
		{
			valsAch[i] = 0;
			j = 0;
			while(j < lenVisVals)
			{
				valsAch[i] += visValsAch[j] * weights[weiPos];   //summing the activations of the vis layer mulled by weights
				j++;
				weiPos++;
			}
			valsAch[i] = Functions.sigmoid(valsAch[i]);  //sigmoid function on value achieved
			i++;
			weiPos++;
		}
	}
	public void train()
	{
		//the roles of each loop have been reversed to allow for the possibility of multi-threading without 
		i = 0;
		while(i < lenVisVals)
		{
			visValsReq[i] = 0;
			j = 0;
			while(j < lenVals)
			{
				visValsReq[i] += valsReq[j] - valsAch[j];  //updating the vis layers requested values
				weights[w] += visValsAch[i] * valsReq[j] * learnRate;   //updating the weights based on requested values and vis layer achieved values
				j++;
				w++;
			}
			visValsReq[i] = Functions.stepNeg(visValsReq[i]);   //stepping the requested value of the vis layer for the future weight learning
			i++;
		}
	}
	
	public void setValsRefs(float[] a, float[] r)
	{
	}
	public void setVisRefs(float[] a, float[] r)
	{
	}
	public void setWeightsRef(float[] w)
	{
	}
}

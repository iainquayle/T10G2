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
		int weiPos = 0;
		for(int i = 0; i < lenVals; i++)
		{
			valsAch[i] = 0;
			for(int j = 0; j < lenVisVals; j++)
			{
				valsAch[i] += visValsAch[j] * weights[weiPos];   //summing the activations of the vis layer mulled by weights
				weiPos++;
			}
			valsAch[i] = Functions.sigmoid(valsAch[i]);  //sigmoid function on value achieve
			weiPos++;
		}
	}
	public void train()
	{
		//the roles of each loop have been reversed to allow for the possibility of multi-threading without backfeed steping on each other
		int weiPos = 0;
		for(int i = 0; i < lenVisVals; i++)
		{
			visValsReq[i] = 0;
			for(int j = 0; j < lenVals; j++)
			{
				visValsReq[i] += valsReq[j] * weights[weiPos];  //updating the vis layers requested values
				weights[weiPos] += valsReq[j] - visValsAch[i] * learnRate;   //updating the weights based on requested values and vis layer achieved values
				weiPos++;
			}
			visValsReq[i] = Functions.stepNeg(visValsReq[i]);   //stepping the requested value of the vis layer for the future weight learning
			weiPos++;
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

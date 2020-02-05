package engine;
import engine.Layer;

public class Dense extends Layer
{
	//other than indexing values, this is multi threading safe (not yet implemented if ever)
	
	public Dense()
	{
	}
	
	public void eval()
	{
		w = 0;
		i = 0;
		while(i < lenVals)
		{
			valsAch[i] = 0;
			j = 0;
			while(j < lenVisVals)
			{
				valsAch[i] += valsVisAch[j] * weights[w];   //summing the activations of the vis layer mulled by weights
				j++;
				w++;
			}
			valsAch[i] = (float)(valsAch[i]/Math.sqrt(1 + valsAch[i] * valsAch[i]));  //sigmoid function on value achieved
			i++;
		}
	}
	public void train()
	{
		//the roles of each loop have been reversed to allow for the possibility of multi-threading without 
		i = 0;
		while(i < lenVisVals)
		{
			valsVisReq[i] = 0;
			j = 0;
			while(j < lenVals)
			{
				valsVisReq[i] += valsReq[j] - valsAch[j];  //updating the vis layers requested values
				weights[w] += valsVisAch[i] * valsReq[j] * learnRate;   //updating the weights based on requested values and vis layer achieved values
				j++;
				w++;
			}
			if(valsVisReq[i] >= 0)   //stepping the requested value of the vis layer for the future weight learning
				valsVisReq[i] = 1;
			else
				valsVisReq[i] = -1;
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

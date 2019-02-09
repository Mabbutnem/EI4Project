package utility;

import java.util.Random;

import com.google.common.base.Preconditions;

public class Proba
{
	private static Random r = new Random();
	
	private Proba()
	{
		throw new IllegalStateException("Utility class");
	}

	public static float[] convertFrequencyToProbability(int[] frequencies)
	{
		float total = 0f;
		for(int f : frequencies) { total += (float)f; }
		
		Preconditions.checkArgument(total != 0f, "total of frequences was %s but expected not null", total);
		
		float[] probabilities = new float[frequencies.length];
		float totalProbabilities = 0f;
		for(int i = 0; i < frequencies.length; i++)
		{
			float currentProba = (float)frequencies[i] / total;
			probabilities[i] = totalProbabilities + currentProba;
			totalProbabilities += currentProba;
		}
		
		probabilities[probabilities.length-1] = 1f;
		
		return probabilities;
	}
	
	public static int getRandomIndexFrom(float[] probabilities)
	{
		Preconditions.checkArgument(probabilities != null,
				"probabilities was null but expected not null");
		
		Preconditions.checkArgument(probabilities.length > 0,
				"probabilities length was %s but expected strictly positive", probabilities.length);
		
		Preconditions.checkArgument(probabilities[0] >= 0f,
				"first probability was %s but expected positive", probabilities[0]);
		
		Preconditions.checkArgument(probabilities[probabilities.length-1] == 1f, 
				"last probability was %s but expected 1f", probabilities[probabilities.length-1]);
		
		if(probabilities.length > 1)
		{
			for(int i = 1; i < probabilities.length; i++)
			{
				Preconditions.checkArgument(probabilities[i-1] <= probabilities[i],
						"probability %s (%s) was lower that probability %s (%s) but expected higher",
						i, probabilities[i],
						i-1, probabilities[i-1]);
			}
		}
		
		float randomNumber = r.nextFloat();
		
		int i = 0;
		while(i < probabilities.length && randomNumber >= probabilities[i])
		{
			i++;
		}
		
		return i;
	}
	
	public static boolean willHappen(float probability)
	{
		Preconditions.checkArgument(probability >= 0 && probability <= 1,"probability was %s but expected between 0 and 1", probability);
		
		return r.nextFloat() < probability;
	}
	
}

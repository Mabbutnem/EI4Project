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
			probabilities[i] = totalProbabilities + (float)frequencies[i] / total;
			totalProbabilities += probabilities[i];
		}
		
		return probabilities;
	}
	
	public static int getRandomIndexFrom(float[] probabilities)
	{
		float randomNumber = r.nextFloat();
		
		int i = 0;
		while(i < probabilities.length && randomNumber < probabilities[i] )
		{
			i++;
		}
		
		return i;
	}
	
}

package boardelement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;

import spell.Incantation;
import utility.Proba;

public class Monster extends Character
{
	private int maxHealth;
	private int initArmor;
	private int baseMove;
	private int baseRange;
	private String name;
	private float rebornProbability;
	
	private Incantation[] incantations;
	private float[] incantationProbabilities;
	
	
	
	//incantations: all the incantations from the JSON file
	public Monster(MonsterFactory monsterFactory, Incantation[] incantations)
	{
		super();
		
		Preconditions.checkArgument(monsterFactory != null, "monsterFactory was null but expected not null");
		Preconditions.checkArgument(incantations != null, "incantations was null but expected not null");
		
		maxHealth = monsterFactory.getMaxHealth();
		initArmor = monsterFactory.getInitArmor();
		baseMove = monsterFactory.getBaseMove();
		baseRange = monsterFactory.getBaseRange();
		setHealth(getMaxHealth());
		setArmor(getInitArmor());
		resetMove();
		resetRange();
		
		name = monsterFactory.getName();
		rebornProbability = monsterFactory.getRebornProbability();

		
		/*
		 * Incantations
		 */
		//On transforme incantations en incantationsMap (plus facile de chercher les incantations dans une Map que dans un tableau)
		Map<String, Incantation> incantationsMap = new HashMap<>();
		for(Incantation i : incantations) { incantationsMap.put(i.getName(), i); }
		
		List<Incantation> li = new LinkedList<>();
		
		//On ajoute les incantations dans li comme spécifié par monsterFactory
		for(String incantationName : monsterFactory.getIncantations().keySet())
		{
			if(!incantationsMap.containsKey(incantationName)) { throw new IllegalArgumentException("a (or more) incantation is missing from incantations"); }

			//Ajoute une COPIE de l'incantation
			li.add(new Incantation(incantationsMap.get(incantationName)));
		}
		
		//Ajout de la liste (li) à this.incantations
		this.incantations = li.toArray(new Incantation[0]);
		/*
		 * 
		 */
		
		
		/*
		 * Probabilities
		 */
		incantationProbabilities = Proba.convertFrequencyToProbability( //...puis transforme ce dernier en tableau de probabilités
				//Transforme la Map de fréquence en tableau de fréquence...
				monsterFactory.getIncantations().values().stream().mapToInt(i->i).toArray()
				);
		/*
		 * 
		 */
		
	}
	
	
	
	public String getName() {
		return name;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getInitArmor() {
		return initArmor;
	}

	public int getBaseMove() {
		return baseMove;
	}

	public int getBaseRange() {
		return baseRange;
	}

	public float getRebornProbability() {
		return rebornProbability;
	}



	@Override
	public void resetMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetRange() {
		// TODO Auto-generated method stub
		
	}

}
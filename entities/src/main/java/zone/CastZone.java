package zone;

import java.util.PriorityQueue;
import java.util.Queue;

import spell.ISpell;

public class CastZone
{
	Queue<ISpell> spells;
	
	
	
	public CastZone()
	{
		spells = new PriorityQueue<>();
	}
	
	
	
	public void add(ISpell spell)
	{
		spells.add(spell);
	}
	
	public boolean canCast()
	{
		return !spells.isEmpty();
	}
	
	public ISpell cast()
	{
		return spells.poll();
	}
	
	
}

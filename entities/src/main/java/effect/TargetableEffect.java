package effect;

import game.Game;
import spell.ISpell;
import target.TargetType;

public abstract class TargetableEffect implements IApplicableEffect
{
	private TargetType targetType;
	
	
	
	public TargetableEffect(TargetType targetType)
	{
		this.targetType = targetType;
	}

	

	public TargetType getTargetType() {
		return targetType;
	}
	
	
	
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prepare(ISpell spell) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clean() {
		// TODO Auto-generated method stub
	}

	@Override
	public void apply(Game game) {
		// TODO Auto-generated method stub
	}

}

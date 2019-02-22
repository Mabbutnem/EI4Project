package effect;

import spell.ISpell;

public interface IEffect
{
	public String getDescription();
	public void prepare(ISpell spell);
	public void clean();
}

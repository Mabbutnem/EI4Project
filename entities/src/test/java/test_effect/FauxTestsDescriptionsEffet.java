package test_effect;


import static org.mockito.Mockito.mock;

import org.hamcrest.Condition;
import org.junit.Test;

import condition.HigherManaCondition;
import condition.ICondition;
import effect.*;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import zone.ZonePick;
import zone.ZoneType;

public class FauxTestsDescriptionsEffet {

	@Test
	public void test() {
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		TargetConstraint[] constraints = new TargetConstraint[1];
		constraints[0] = TargetConstraint.NOTENEMY;
		Target target = new Target(constraints, TargetType.AREA);
		/*IEffect e = new AddWordEffect(new Target(constraints, TargetType.AREA), Word.LIFELINK);
		System.out.print(e.getDescription());*/
		IEffect[] effects = new IEffect[] {
				new GainHealthEffect(target, 5),
				new LoseHealthEffect(target, 2)
		};
		IApplicableEffect effect = new InflictEffect(target, 100);
		ICondition c = new HigherManaCondition(4);
		IEffect e = new YouCanEffect(effects, effect);
		System.out.print(e.getDescription());
	}

}

package test_dao;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.MonsterFactory;
import boardelement.WizardFactory;
import config.DataGameModifierConfig;
import constant.AllConstant;
import constant.CorpseConstant;
import constant.GameConstant;
import constant.WizardConstant;
import dao.IDataGameModifierDao;
import effect.AddWordEffect;
import effect.BurnEffect;
import effect.BurnItselfEffect;
import effect.GainHealthEffect;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.LoseManaEffect;
import effect.PredictionEffect;
import effect.PutAfterCastEffect;
import effect.RevealEffect;
import effect.TargetableEffect;
import effect.Word;
import effect.WordEffect;
import effect.YouCanEffect;
import game.Horde;
import game.Level;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import spell.Incantation;
import spell.Power;
import spell.Spell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import zone.ZonePick;
import zone.ZoneType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (DataGameModifierConfig.class))
public class TestJSONDataGameModifierDao
{
	@Autowired
	IDataGameModifierDao dao;

	@Test
	public final void test()
	{
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		
		try
		{
			IEffect inflict = new InflictEffect(
					new Target(new TargetConstraint[0], TargetType.CHOICE),
					10);
			
			IEffect pierce = new WordEffect(Word.ACID);
			
			IEffect gainLifelink = new AddWordEffect(
					new Target(new TargetConstraint[0], TargetType.CHOICE), Word.ACID);
			
			IEffect[] effects = new IEffect[]
			{
					inflict,
					gainLifelink,
					pierce
			};
			
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("card1", 7);
			map.put("card2", 7);
			map.put("card3", 7);
			map.put("card4", 7);
			map.put("card5", 7);
			map.put("card6", 4);
			
			Power p = new Power("Consume", effects, 1);
			
			System.out.println(dao.wizardExists("firee"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
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
import config.DataGameModifierConfig;
import constant.AllConstant;
import constant.CorpseConstant;
import constant.GameConstant;
import constant.WizardConstant;
import dao.IDataGameModifierDao;
import effect.BurnEffect;
import effect.GainHealthEffect;
import effect.IApplicableEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.LoseManaEffect;
import effect.TargetableEffect;
import effect.Word;
import effect.WordEffect;
import effect.YouCanEffect;
import game.Horde;
import game.Level;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
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
		try
		{
			TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
			YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
			
			IEffect inflict10 = new InflictEffect(
					new Target(new TargetConstraint[0], TargetType.CHOICE),
					10);
			
			IEffect burn = new BurnEffect(
					new Target(new TargetConstraint[0], TargetType.YOU),
					1,
					ZoneType.DECK,
					ZonePick.TOP);
			
			IEffect inflict1More = new InflictEffect(
					new Target(new TargetConstraint[0], TargetType.MORE),
					1);
			
			IEffect pierce = new WordEffect(Word.PIERCE);
			
			IEffect youCan = new YouCanEffect(
					new IEffect[] {inflict1More, pierce},
					(IApplicableEffect) burn);
			
			IEffect[] effects = new IEffect[]
			{
					inflict10,
					youCan
			};
			
			Card c = new Card("Consume", effects, 1);
			
			dao.addCard(c);
			
			System.out.println(c.getDescription());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
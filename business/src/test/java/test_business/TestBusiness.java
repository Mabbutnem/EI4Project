package test_business;

import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.WizardFactory;
import business.IBusiness;
import config.BusinessConfig;
import game.Game;
import listener.ICardArrayRequestListener;
import listener.ICardDaoListener;
import zone.Zone;
import zone.ZoneGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (BusinessConfig.class))
public class TestBusiness
{
	@Autowired
	IBusiness business;

	@Test
	public final void test()
	{
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		
		try
		{
			business.initAllConstant();
			
			List<WizardFactory> alreadyChoosens = new LinkedList<>();
			
			WizardFactory[] choice = business.getRandomWizards(1, alreadyChoosens.toArray(new WizardFactory[0]));
			alreadyChoosens.add(choice[0]);
			
			choice = business.getRandomWizards(2, alreadyChoosens.toArray(new WizardFactory[0]));
			alreadyChoosens.add(choice[0]);
			
			choice = business.getRandomWizards(3, alreadyChoosens.toArray(new WizardFactory[0]));
			alreadyChoosens.add(choice[0]);
			
			Game g = new Game("thibault", business.createWizards(alreadyChoosens.toArray(new WizardFactory[0])));
			
			business.newGame(g);
			
			while(!business.isFinished())
			{
				if(business.levelFinished())
				{
					business.nextLevel();
				}
				
				business.beginWizardsTurn();
				
				business.saveGame(); //SAVE
				
				business.endWizardsTurn();
				
				business.nextMonsterWave();
				
				while(!business.monstersTurnEnded())
				{
					business.playMonstersTurnPart1();
					business.playMonstersTurnPart2();
					business.nextMonster();
					
					business.saveGame(); //SAVE
				}

				business.saveGame(); //SAVE
			}
			
			System.out.println(business.isVictory() ? "you win" : "you loose");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

package test_business;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.WizardFactory;
import boardelement.Character;
import boardelement.Wizard;
import business.IBusiness;
import config.BusinessConfig;
import game.Game;
import listener.ICardArrayRequestListener;
import listener.ICardDaoListener;
import spell.Card;
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
		ICardArrayRequestListener listener = mock(ICardArrayRequestListener.class);
		when(listener.chooseCards(any())).thenReturn(new Card[0]);
		
		Zone.setCardArrayRequestListener(listener);
		ZoneGroup.setCardArrayRequestListener(listener);
		
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
			
			Game g = new Game("someone", business.createWizards(alreadyChoosens.toArray(new WizardFactory[0])));
			
			business.newGame(g);
			
			//while(!business.isFinished())
			{
				if(business.levelFinished())
				{
					business.nextLevel();
				}
				
				business.beginWizardsTurn();
				
				business.setSelectedCharacter((Character) business.getGame().getBoard()[2]);
				((Wizard) business.getSelectedCharacter()).setDash(20);
				if(business.canDash()) {
					business.rightDash();
				}
				((Wizard) business.getSelectedCharacter()).setDash(15);
				if(business.canDash()) {
					business.leftDash();
				}
				
				business.saveGame();
				
				business.endWizardsTurn();
				
				business.nextMonsterWave();
				
				while(!business.monstersTurnEnded())
				{
					business.playMonstersTurnPart1();
					business.playMonstersTurnPart2();
					business.nextMonster();
				}
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

package test_business;

import static org.mockito.Mockito.mock;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.Wizard;
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
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

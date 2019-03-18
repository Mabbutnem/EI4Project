package test_business;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	//@Autowired
	//IBusiness business;

	@Test
	public final void test()
	{
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		
		try
		{
			//System.out.println(business);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

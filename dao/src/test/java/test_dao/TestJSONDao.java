package test_dao;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.Wizard;
import config.DaoConfig;
import dao.IDao;
import game.Game;
import listener.ICardArrayRequestListener;
import listener.ICardDaoListener;
import zone.Zone;
import zone.ZoneGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (DaoConfig.class))
public class TestJSONDao
{
	@Autowired
	IDao dao;

	@Test
	public final void test()
	{
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		
		try
		{
			dao.getConstant().initAllConstant();
			
			Wizard f = new Wizard(dao.getWizards()[1], dao.getCards());
			Wizard t = new Wizard(dao.getWizards()[2], dao.getCards());
			Wizard l = new Wizard(dao.getWizards()[3], dao.getCards());
			
			Game g = new Game("game1", new Wizard[] { f, t, l });
			
			dao.newGame(g);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

package test_dao;

import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.WizardFactory;
import config.DataGameReaderConfig;
import dao.IDataGameReaderDao;
import effect.TargetableEffect;
import effect.YouCanEffect;
import listener.ICardArrayRequestListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import zone.Zone;
import zone.ZoneGroup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (DataGameReaderConfig.class))
public class TestJSONDataGameReaderDao
{
	@Autowired
	IDataGameReaderDao dao;

	@Test
	public final void test()
	{
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		try
		{
			LinkedList<WizardFactory> list = new LinkedList<>(Arrays.asList(dao.getWizards()));
			list.stream().filter(wf -> wf.getName().compareTo("Fire")==0);
			System.out.println(list.get(8).getBasePower().getDescription());
			System.out.println(list.get(8).getTransformedPower().getDescription());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

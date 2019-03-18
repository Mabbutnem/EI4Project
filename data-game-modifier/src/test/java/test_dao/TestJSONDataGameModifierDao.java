package test_dao;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import config.DataGameModifierConfig;
import dao.IDataGameModifierDao;
import effect.TargetableEffect;
import effect.YouCanEffect;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;

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
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
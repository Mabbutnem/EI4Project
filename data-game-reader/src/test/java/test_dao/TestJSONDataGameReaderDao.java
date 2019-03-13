package test_dao;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.MonsterFactory;
import config.DataGameReaderConfig;
import constant.AllConstant;
import dao.IDataGameReaderDao;
import game.Horde;
import game.Level;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (DataGameReaderConfig.class))
public class TestJSONDataGameReaderDao
{
	@Autowired
	IDataGameReaderDao dao;

	@Test
	public final void test()
	{
		try
		{
			Level[] array = dao.getLevels();
			for(Level o : array)
			{
				System.out.println(o);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

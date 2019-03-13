package test_dao;

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
import game.Horde;
import game.Level;

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
			Map<String, Integer> map = new HashMap<>();
			map.put("horde1", 1);
			map.put("horde2", 2);
			map.put("horde3", 3);
			
			dao.addLevel(new Level(10, map));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
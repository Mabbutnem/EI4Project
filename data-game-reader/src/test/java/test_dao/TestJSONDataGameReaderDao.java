package test_dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import boardelement.MonsterFactory;
import config.DataGameConfig;
import dao.IDataGameReaderDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= (DataGameConfig.class))
public class TestJSONDataGameReaderDao
{
	@Autowired
	IDataGameReaderDao dao;

	@Test
	public final void test()
	{
		Map<String, Integer> mapIncantationsFrequencies = new HashMap<>();
		mapIncantationsFrequencies.put("inc1", 100);
		mapIncantationsFrequencies.put("inc2", 100);
		mapIncantationsFrequencies.put("inc3", 50);
		
		dao.addMonsterFactory(new MonsterFactory("monstre2", 50, 20, 3, 4, mapIncantationsFrequencies, 0.4f));
	}

}

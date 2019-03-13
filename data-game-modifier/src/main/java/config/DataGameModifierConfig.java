package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.IDataGameModifierDao;
import dao.JSONDataGameModifierDao;

@Configuration
public class DataGameModifierConfig extends DataGameReaderConfig
{

	@Bean
	public IDataGameModifierDao dataGameModifierDao() { return new JSONDataGameModifierDao(); }
}

package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.IDao;
import dao.JSONDao;

@Configuration
public class DaoConfig extends DataGameReaderConfig
{
	@Bean
	public String savesDirectoryName() { return "\\dao\\src\\main\\resources\\saves\\";}
	
	@Bean
	public String savesFileName() { return "saves"; }
	
	@Bean
	public IDao dao() { return new JSONDao(); }
}

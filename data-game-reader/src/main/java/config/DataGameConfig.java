package config;

import org.springframework.context.annotation.Configuration;

import dao.IDataGameReaderDao;
import dao.JSONDataGameReaderDao;

import org.springframework.context.annotation.Bean;

@Configuration
public class DataGameConfig
{
	
	@Bean
	public String directoryName() { return "\\data-game-reader\\src\\main\\resources\\data-game\\";}
	
	@Bean
	public String extentionName() { return ".json"; }
	
	@Bean
	public String fileName() { return "file"; }
	
	
	
	@Bean
	public IDataGameReaderDao dataGameReaderDao() { return new JSONDataGameReaderDao(); }
}

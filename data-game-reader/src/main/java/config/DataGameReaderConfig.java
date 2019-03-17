package config;

import org.springframework.context.annotation.Configuration;

import dao.IDataGameReaderDao;
import dao.JSONDataGameReaderDao;

import org.springframework.context.annotation.Bean;

@Configuration
public class DataGameReaderConfig
{
	
	@Bean
	public String dataGameDirectoryName() { return "\\data-game-reader\\src\\main\\resources\\data-game\\";}
	
	@Bean
	public String extentionName() { return ".json"; }
	
	
	
	@Bean
	public String cardsFileName() { return "cards"; }
	
	@Bean
	public String constantsFileName() { return "constants"; }
	
	@Bean
	public String hordesFileName() { return "hordes"; }
	
	@Bean
	public String incantationsFileName() { return "incantations"; }
	
	@Bean
	public String levelsFileName() { return "levels"; }
	
	@Bean
	public String monstersFileName() { return "monsters"; }
	
	@Bean
	public String wizardsFileName() { return "wizards"; }
	
	
	
	@Bean
	public IDataGameReaderDao dataGameReaderDao() { return new JSONDataGameReaderDao(); }
}

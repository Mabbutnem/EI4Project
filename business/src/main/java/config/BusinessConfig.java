package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import business.Business;
import business.IBusiness;

@Configuration
public class BusinessConfig extends DaoConfig
{
	@Bean
	public IBusiness business() { return new Business(); }
	
}

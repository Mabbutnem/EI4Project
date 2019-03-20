package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ui.IUI;
import ui.UISwing;

@Configuration
public class UIConfig extends BusinessConfig
{
	@Bean
	public IUI ui() { return new UISwing(); }
}

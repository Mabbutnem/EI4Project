package launcher;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.UIConfig;
import ui.IUI;

public class Launcher
{
	public static void main(String[] args)
	{
		IUI ui = null;
		AnnotationConfigApplicationContext ctx = null;
		try
		{
			ctx = new AnnotationConfigApplicationContext(UIConfig.class);
			ui = ctx.getBean(IUI.class);
			ui.set();
			ui.run();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
		finally{ if(ctx!=null) ctx.close();}
	}
}
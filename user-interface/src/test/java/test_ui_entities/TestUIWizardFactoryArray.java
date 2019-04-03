package test_ui_entities;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import boardelement.WizardFactory;
import business.IBusiness;
import config.BusinessConfig;
import ui_entities.UIWizardFactoryArray;
import ui_entities.UIWizardFactoryChoice;

public class TestUIWizardFactoryArray {

	private JFrame frame;
	
	private static IBusiness business;
	
	private List<WizardFactory> choosenWf;
	
	private UIWizardFactoryArray uiWfArray;
	
	private UIWizardFactoryChoice wizardFactoryChoice;

	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BusinessConfig.class);
					business = ctx.getBean(IBusiness.class);
					ctx.close();
					
					TestUIWizardFactoryArray window = new TestUIWizardFactoryArray();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestUIWizardFactoryArray() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		choosenWf = new LinkedList<>();
		
		uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
		frame.getContentPane().add(uiWfArray);
		
		wizardFactoryChoice = new UIWizardFactoryChoice(frame.getContentPane());
		
		try
		{
			business.initAllConstant();
			
			

			choosenWf.add(wizardFactoryChoice.chooseWizardFactory(
					business.getRandomWizards(1, choosenWf.toArray(new WizardFactory[0]))));
			
			frame.getContentPane().remove(uiWfArray);
			uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
			frame.getContentPane().add(uiWfArray);
			frame.getContentPane().repaint();
			
			
			
			choosenWf.add(wizardFactoryChoice.chooseWizardFactory(
					business.getRandomWizards(2, choosenWf.toArray(new WizardFactory[0]))));
			
			frame.getContentPane().remove(uiWfArray);
			uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
			frame.getContentPane().add(uiWfArray);
			frame.getContentPane().repaint();

			
			
			choosenWf.add(wizardFactoryChoice.chooseWizardFactory(
					business.getRandomWizards(3, choosenWf.toArray(new WizardFactory[0]))));
			
			frame.getContentPane().remove(uiWfArray);
			uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
			frame.getContentPane().add(uiWfArray);
			frame.getContentPane().repaint();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

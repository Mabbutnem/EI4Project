package ui;

import java.io.IOException;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import business.IBusiness;

public class UISwing extends JFrame implements IUI{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IBusiness business;


	
	public UISwing() {
		super();
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	}
	
	public void set() {
		try {
			business.initAllConstant();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

	
	
	public void run()
	{
		try {
			setVisible(true);
		}
		catch (Exception e) {
			//Must be empty
		}
	}
}

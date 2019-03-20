package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import effect.FreezeEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import ui_entities.UISpell;
import spell.ISpell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import java.awt.Color;

public class TestUISpell {

	private JFrame frame;
	
	private UISpell[] spell;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUISpell window = new TestUISpell();
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
	public TestUISpell() {
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

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		
		IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		Card card1 = new Card("Name", effects, 1);
		card1.setDescription();
		
		Card card2 = new Card("Carte 2", effects, 100);
		card1.setDescription();
		
		spell = new UISpell[4];
		
		for(int i = 0; i < spell.length; i++)
		{
			spell[i] = new UISpell(card1);
			spell[i].setBackground(new Color(211, 211, 211));
			spell[i].setLocation(38 + i*150, 29);
			frame.getContentPane().add(spell[i]);
		}
		frame.getContentPane().remove(spell[1]);
		spell[1] = new UISpell(card2);
		spell[1].setBackground(new Color(211, 211, 211));
		spell[1].setLocation(38 + 1*150, 29);
		frame.getContentPane().add(spell[1]);
		
		
	}
}

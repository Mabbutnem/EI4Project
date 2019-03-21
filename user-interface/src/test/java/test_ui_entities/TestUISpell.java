package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import effect.FreezeEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import spell.Incantation;
import ui_entities.UISpell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;

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
		frame.setBounds(100, 100, 450, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		
		IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		Card card1 = new Card("Card1", effects, 1);
		card1.setDescription();
		
		Card card2 = new Card("Card2", effects, 1);
		card2.setDescription();
		
		Card card3 = new Card("Card3", effects, 1);
		card3.setDescription();
		card3.setRevealed(false);
		
		Incantation inc1 = new Incantation("Incantation", effects);
		inc1.setDescription();
		
		spell = new UISpell[]
				{
						new UISpell(card1),
						new UISpell(card2),
						new UISpell(card3),
						new UISpell(inc1)
				};
		
		for(int i = 0; i < spell.length; i++)
		{
			spell[i].setLocation(38 + i*150, 29);
			spell[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for(int i = 0; i < spell.length; i++) {
						if(spell[i].isWaitingToConfirmSelected()) {
							spell[i].confirmSelected();
						}
						else if(spell[i].isSelected()) {
							spell[i].setSelected(false);
						}
					}
				}
			});
			frame.getContentPane().add(spell[i]);
		}
		
		JButton buttonHide = new JButton("Hide card2");
		buttonHide.setBounds(10, 274, 115, 23);
		buttonHide.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				((Card) spell[1].getSpell()).setRevealed(false);
			}
			
		});
		frame.getContentPane().add(buttonHide);
		
		JButton buttonReveal = new JButton("Reveal card2");
		buttonReveal.setBounds(135, 274, 115, 23);
		buttonReveal.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				((Card) spell[1].getSpell()).setRevealed(true);
			}
			
		});
		frame.getContentPane().add(buttonReveal);
	}
}

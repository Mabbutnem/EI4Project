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
import listener.ICardArrayRequestListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import ui_entities.UICardArray;
import ui_entities.UISpell;
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;
import spell.Card;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import javax.swing.JLabel;

public class TestUICardArray {

	private JFrame frame;
	private JLabel label;
	
	private Zone zone;
	
	private MouseAdapter mouseAdapter;
	
	private UICardArray cardArray;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUICardArray window = new TestUICardArray();
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
	public TestUICardArray() {
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
		
		label = new JLabel("");
		label.setBounds(116, 236, 81, 14);
		frame.getContentPane().add(label);

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		Card[] cards = new Card[] {
				new Card("Card1", effects, 1),
				new Card("Card2", effects, 1),
				new Card("Card3", effects, 1)
		};
		for(Card c : cards) {
			c.setDescription();
		}
		
		zone = new Zone(cards, ZoneType.DECK, ZonePick.BOTTOM);
		
		cardArray = new UICardArray(zone);
		cardArray.setLocation(20, 20);
		frame.getContentPane().add(cardArray);
		
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				UISpell selectedUispell = cardArray.getSelectedUispell();
				if(selectedUispell != null) {
					label.setText(selectedUispell.getSpell().getName());
					zone.remove((Card) selectedUispell.getSpell());
					cardArray.addMouseListenerToUISpells(mouseAdapter);
				}
				else {
					label.setText("");
				}
			}
		};
		
		
		cardArray.addMouseListenerToUISpells(mouseAdapter);
	}
}

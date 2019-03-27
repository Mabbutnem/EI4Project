package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;

import javax.swing.JFrame;
import effect.FreezeEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import game.Game;
import listener.ICardDaoListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import ui_entities.UICardArray;
import ui_entities.UICardArrayRequestListener;
import zone.Zone;
import zone.ZoneGroup;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestUICardArrayRequestListener {

	private JFrame frame;
	
	private UICardArray cardArray;
	private UICardArrayRequestListener listener;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUICardArrayRequestListener window = new TestUICardArrayRequestListener();
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
	public TestUICardArrayRequestListener() {
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

		
		listener = new UICardArrayRequestListener(frame.getContentPane());
		
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		Zone.setCardArrayRequestListener(listener);
		ZoneGroup.setCardArrayRequestListener(listener);
		
		
		
		final IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		final Card[] cards = new Card[] {
				new Card("Card0", effects, 1),
				new Card("Card1", effects, 1),
				new Card("Card2", effects, 1),
				new Card("Card3", effects, 1),
				new Card("Card4", effects, 1),
				new Card("Card5", effects, 1),
				new Card("Card6", effects, 1),
				new Card("Card7", effects, 1),
				new Card("Card8", effects, 1),
				new Card("Card9", effects, 1),
		};
		for(Card c : cards) {
			c.setDescription();
		}
		
		cardArray = new UICardArray(new Card[0]);
		cardArray.setLocation(10000, 10000);
		frame.getContentPane().add(cardArray);
		
		JButton btnListener = new JButton("Listener");
		btnListener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UICardArray cardArrayCopy = cardArray;
				cardArray = new UICardArray(listener.chooseCards(2, cards));
				cardArrayCopy.clearListeners();
				frame.getContentPane().remove(cardArrayCopy);
				frame.getContentPane().add(cardArray);
				cardArray.setLocation(100, 100);
				cardArray.setTitle("Listened cards");
			}
		});
		btnListener.setBounds(44, 66, 89, 23);
		frame.getContentPane().add(btnListener);
	}
}

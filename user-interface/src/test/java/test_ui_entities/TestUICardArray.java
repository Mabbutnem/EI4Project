package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;

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
import zone.Zone;
import zone.ZonePick;
import zone.ZoneType;
import spell.Card;
import spell.ISpell;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestUICardArray {

	private JFrame frame;
	
	private Zone zone;
	
	private UICardArray cardArray;
	private JTextField textField;
	private JButton btnAddCardOn;
	private JButton button;
	private JButton button_1;

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
		frame.setBounds(100, 100, 711, 532);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		final IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		Card[] cards = new Card[] {
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
		
		zone = new Zone(cards, ZoneType.DECK, ZonePick.BOTTOM);

		cardArray = new UICardArray(zone);
		cardArray.setLocation(20, 20);
		frame.getContentPane().add(cardArray);
		
		
		
		cardArray.setNbCanBeSelected(3);
		
		JButton btnDeleteCards = new JButton("Delete cards");
		btnDeleteCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(ISpell s : cardArray.getSelectedSpells()) {
					zone.remove((Card) s);
				}
			}
		});
		btnDeleteCards.setBounds(383, 391, 123, 23);
		frame.getContentPane().add(btnDeleteCards);
		
		textField = new JTextField();
		textField.setBounds(98, 392, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnLimitOfSelected = new JButton("Limit of selection");
		btnLimitOfSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					cardArray.setNbCanBeSelected(Integer.parseInt(textField.getText()));
				} catch (NumberFormatException e) {}
			}
		});
		btnLimitOfSelected.setBounds(194, 391, 138, 23);
		frame.getContentPane().add(btnLimitOfSelected);
		
		btnAddCardOn = new JButton("Add card on top");
		btnAddCardOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zone.add(new Card[] {new Card("CardTop", effects, 1)}, ZonePick.TOP);
			}
		});
		btnAddCardOn.setBounds(537, 425, 148, 23);
		frame.getContentPane().add(btnAddCardOn);
		
		button = new JButton("Add card on bottom");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zone.add(new Card[] {new Card("CardBottom", effects, 1)}, ZonePick.BOTTOM);
			}
		});
		button.setBounds(537, 459, 148, 23);
		frame.getContentPane().add(button);
		
		button_1 = new JButton("Add card randomly");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				zone.add(new Card[] {new Card("CardRandom", effects, 1)}, ZonePick.RANDOM);
			}
		});
		button_1.setBounds(537, 391, 148, 23);
		frame.getContentPane().add(button_1);
	}
}

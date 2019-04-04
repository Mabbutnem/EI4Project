package test_ui_entities;

import static org.mockito.Mockito.*;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import boardelement.Corpse;
import boardelement.Monster;
import boardelement.MonsterFactory;
import boardelement.Wizard;
import boardelement.WizardFactory;
import constant.CorpseConstant;
import constant.WizardConstant;
import effect.FreezeEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import listener.ICardArrayRequestListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import spell.Incantation;
import spell.Power;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import ui_entities.UIBoardElement;
import ui_entities.UICorpse;
import ui_entities.UIMonster;
import ui_entities.UIWizard;
import zone.Zone;
import zone.ZoneGroup;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;
import java.awt.event.MouseEvent;

public class TestUIBoardElement {

	private JFrame frame;
	
	private UIBoardElement uiBoardElement;
	private JTextField textField;
	private JButton btnSetRange;
	private JToggleButton tglbtnInWizardsRange;
	private JToggleButton tglbtnInCurrentRange;
	private JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUIBoardElement window = new TestUIBoardElement();
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
	public TestUIBoardElement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 505, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		ICardArrayRequestListener cardArrayRequestListener = mock(ICardArrayRequestListener.class);
		when(cardArrayRequestListener.chooseCards((Card[]) any())).thenReturn(new Card[0]);
		ZoneGroup.setCardArrayRequestListener(cardArrayRequestListener);
		Zone.setCardArrayRequestListener(cardArrayRequestListener);
		
		Wizard.setWizardConstant(new WizardConstant(80, 0, 3, 6, 3, 4));
		Corpse.setCorpseConstant(new CorpseConstant(3));
		
		final IEffect[] effects = new IEffect[]
				{
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10),
						new FreezeEffect(new Target(new TargetConstraint[0], TargetType.YOU))
				};
		
		Power power = new Power("Power", effects, 1);
		
		Card[] cards = new Card[] {
				new Card("Inc0", effects, 1),
				new Card("Inc1", effects, 1),
				new Card("Inc2", effects, 1),
				new Card("Inc3", effects, 1),
				new Card("Inc4", effects, 1),
		};
		for(Card c : cards) {
			c.setDescription();
		}
		
		Incantation[] incantations = new Incantation[] {
				new Incantation("Inc0", effects),
				new Incantation("Inc1", effects),
				new Incantation("Inc2", effects),
				new Incantation("Inc3", effects),
				new Incantation("Inc4", effects),
		};
		for(Incantation i : incantations) {
			i.setDescription();
		}
		
		
		Map<String, Integer> map = new HashMap<>();
		map.put("Inc0", 100);
		
		WizardFactory wf = new WizardFactory("Wizard1", power, power, map);
		final Wizard wizard = new Wizard(wf, cards);
		
		MonsterFactory mf = new MonsterFactory("Monster1", 80, 20, 3, 6, map, 0f);
		final Monster monster = new Monster(mf, incantations);
		
		final Corpse corpse = new Corpse(monster);
		
		uiBoardElement = new UICorpse(corpse);
		uiBoardElement.setState(false, false);
		frame.getContentPane().add(uiBoardElement);
		
		uiBoardElement.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(uiBoardElement.isWaitingToConfirmSelected()) {
					uiBoardElement.confirmSelected();
				}
			}
		});
		//((UIWizard) uiBoardElement).setCanMoveWithArrow(true);
		
		textField = new JTextField();
		textField.setBounds(227, 11, 107, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSetArmor = new JButton("Set Armor");
		btnSetArmor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wizard.setArmor(Integer.parseInt(textField.getText()));
			}
		});
		btnSetArmor.setBounds(227, 42, 107, 23);
		frame.getContentPane().add(btnSetArmor);
		
		JButton btnSetHealth = new JButton("Set Health");
		btnSetHealth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wizard.setHealth(Integer.parseInt(textField.getText()));
			}
		});
		btnSetHealth.setBounds(227, 76, 107, 23);
		frame.getContentPane().add(btnSetHealth);
		
		btnSetRange = new JButton("Set Range");
		btnSetRange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wizard.setRange(Integer.parseInt(textField.getText()));
			}
		});
		btnSetRange.setBounds(227, 110, 107, 23);
		frame.getContentPane().add(btnSetRange);
		
		tglbtnInWizardsRange = new JToggleButton("In wizard's range");
		tglbtnInWizardsRange.setBounds(227, 236, 121, 23);
		frame.getContentPane().add(tglbtnInWizardsRange);
		
		tglbtnInCurrentRange = new JToggleButton("In current range");
		tglbtnInCurrentRange.setBounds(227, 270, 121, 23);
		frame.getContentPane().add(tglbtnInCurrentRange);
		
		JButton btnChangeState = new JButton("Change state");
		btnChangeState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				uiBoardElement.setState(tglbtnInWizardsRange.isSelected(), tglbtnInCurrentRange.isSelected());
			}
		});
		btnChangeState.setBounds(227, 304, 121, 23);
		frame.getContentPane().add(btnChangeState);
		
		button = new JButton("Set Move");
		button.setBounds(227, 144, 107, 23);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wizard.setMove(Integer.parseInt(textField.getText()));
			}
		});
		frame.getContentPane().add(button);
		
		JButton button_1 = new JButton("Set Mana");
		button_1.setBounds(227, 178, 107, 23);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wizard.setMana(Integer.parseInt(textField.getText()));
			}
		});
		frame.getContentPane().add(button_1);
		
		JButton btnIncr = new JButton("Incr");
		btnIncr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				corpse.incrCounterToReborn();
			}
		});
		btnIncr.setBounds(390, 110, 89, 23);
		frame.getContentPane().add(btnIncr);
	}
}

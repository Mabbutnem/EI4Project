package test_ui_entities;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import boardelement.Character;
import boardelement.Corpse;
import boardelement.IBoardElement;
import boardelement.Wizard;
import boardelement.WizardFactory;
import constant.CorpseConstant;
import constant.GameConstant;
import constant.WizardConstant;
import effect.FreezeEffect;
import effect.IEffect;
import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import game.Game;
import listener.ICardArrayRequestListener;
import listener.ICardDaoListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import spell.Card;
import spell.Incantation;
import spell.Power;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import ui_entities.UIBoardElementArray;
import zone.Zone;
import zone.ZoneGroup;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class TestUIBoardElementArray {

	private JFrame frame;
	
	private UIBoardElementArray uiBoardElementArray;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUIBoardElementArray window = new TestUIBoardElementArray();
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
	public TestUIBoardElementArray() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		ICardArrayRequestListener cardArrayRequestListener = mock(ICardArrayRequestListener.class);
		when(cardArrayRequestListener.chooseCards((Card[]) any())).thenReturn(new Card[0]);
		ZoneGroup.setCardArrayRequestListener(cardArrayRequestListener);
		Zone.setCardArrayRequestListener(cardArrayRequestListener);
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		Game.setGameConstant(new GameConstant(30, 3, 10, 5, 0, 2, 4, 12));
		
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
		
		final Wizard w1 = new Wizard(wf, cards);
		
		Wizard[] wizards = new Wizard[] { new Wizard(wf, cards), w1, new Wizard(wf, cards)};
		
		final Game game = new Game("Game name", wizards);
		
		uiBoardElementArray = new UIBoardElementArray(game, true);
		frame.getContentPane().add(uiBoardElementArray);
		uiBoardElementArray.setLeftArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.leftWalk(game.getCurrentCharacter());
			}
		});
		uiBoardElementArray.setRightArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.rightWalk(game.getCurrentCharacter());
			}
		});
		uiBoardElementArray.setBusinessChangeMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				IBoardElement be = uiBoardElementArray.getSelectedCharacter();
				if(be instanceof Character) {
					game.setCurrentCharacter((Character) be);
				} else {
					game.setCurrentCharacter(null);
				}
			}
		});
	}
}

package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import boardelement.Monster;
import boardelement.MonsterFactory;
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
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import ui_entities.UIBoardElement;
import zone.Zone;

public class TestUIBoardElement {

	private JFrame frame;
	
	private UIBoardElement uiBoardElement;

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
		frame.setBounds(100, 100, 450, 300);
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
		
		MonsterFactory mf = new MonsterFactory("Monster1", 80, 20, 3, 6, map, 0f);
		
		uiBoardElement = new UIBoardElement(new Monster(mf, incantations));
		frame.getContentPane().add(uiBoardElement);
	}

}

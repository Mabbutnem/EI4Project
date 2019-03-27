package test_ui_entities;

import static org.mockito.Mockito.mock;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import effect.InflictEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import game.Game;
import listener.ICardArrayRequestListener;
import listener.ICardDaoListener;
import listener.ITargetRequestListener;
import listener.IYouCanEffectListener;
import target.Target;
import target.TargetConstraint;
import target.TargetType;
import ui_entities.UIYouCanEffectListener;
import zone.Zone;
import zone.ZoneGroup;
import javax.swing.JLabel;

public class TestUIYouCanEffectListener {

	private JFrame frame;
	
	private UIYouCanEffectListener listener;
	
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestUIYouCanEffectListener window = new TestUIYouCanEffectListener();
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
	public TestUIYouCanEffectListener() {
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
		
		
		listener = new UIYouCanEffectListener(frame.getContentPane());
		
		Game.setCardDaoListener(mock(ICardDaoListener.class));
		TargetableEffect.setTargetRequestListener(mock(ITargetRequestListener.class));
		YouCanEffect.setYouCanEffectListener(mock(IYouCanEffectListener.class));
		Zone.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		ZoneGroup.setCardArrayRequestListener(mock(ICardArrayRequestListener.class));
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(44, 132, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnListener = new JButton("Listener");
		btnListener.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean result = listener.wantToApply(
						new InflictEffect(new Target(new TargetConstraint[] {TargetConstraint.NOTYOU}, TargetType.AREA), 10));
				
				lblNewLabel.setText(Boolean.toString(result));
			}
		});
		btnListener.setBounds(44, 66, 89, 23);
		frame.getContentPane().add(btnListener);
	}
}

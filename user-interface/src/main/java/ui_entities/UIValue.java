package ui_entities;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class UIValue extends JPanel
{
	private static final long serialVersionUID = 853495019283250375L;

	private JLabel valueLabel;
	private JLabel textLabel;
	
	
	
	public UIValue(int width, int height, int value, String text)
	{
		setOpaque(false);
		setSize(width, height);
		setLayout(null);
		
		valueLabel = new JLabel();
		valueLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		valueLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setValue(value);
		valueLabel.setSize(width, height);
		valueLabel.setLocation(0, 2);
		add(valueLabel);
		
		textLabel = new JLabel();
		textLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		textLabel.setHorizontalAlignment(SwingConstants.CENTER);
		setText(text);
		textLabel.setVisible(false);
		textLabel.setSize(width, height);
		textLabel.setLocation(0, -10);
		add(textLabel);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				textLabel.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				textLabel.setVisible(false);
			}
		});
	}
	
	
	
	public void setValue(int value) {
		valueLabel.setText(Integer.toString(value));
	}
	
	public void setText(String text) {
		textLabel.setText(text);
	}
	
	public void setAllForeground(Color fg) {
		valueLabel.setForeground(fg);
		textLabel.setForeground(fg);
	}
}

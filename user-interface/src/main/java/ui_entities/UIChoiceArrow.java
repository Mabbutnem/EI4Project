package ui_entities;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UIChoiceArrow extends JPanel
{
	private static final long serialVersionUID = -4706969261814754821L;
	
	public static final int SIZE_X = 30;
	private static final int DIST_ARROW_TO_ARROW = 20;
	
	private JButton arrow;
	private JLabel nbNotDisplayedElementsLabel;
	private JButton directArrow;

	public UIChoiceArrow()
	{
		setOpaque(false);
		setBorder(null);
		setLayout(null);
		setSize(SIZE_X, UISpell.SIZE_Y);
		
		arrow = new JButton();
		arrow.setFont(new Font("Tahoma", Font.BOLD, 11));
		arrow.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		arrow.setBackground(UIConstants.BASE_COLOR);
		arrow.setLocation(0, 0);
		arrow.setSize(SIZE_X, UISpell.SIZE_Y - DIST_ARROW_TO_ARROW - SIZE_X);
		arrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				arrow.setBackground(UIConstants.MOUSE_ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				arrow.setBackground(UIConstants.BASE_COLOR);
			}
		});
		add(arrow);
		
		nbNotDisplayedElementsLabel = new JLabel("0");
		nbNotDisplayedElementsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nbNotDisplayedElementsLabel.setLocation(0, UISpell.SIZE_Y - DIST_ARROW_TO_ARROW - SIZE_X);
		nbNotDisplayedElementsLabel.setSize(SIZE_X, DIST_ARROW_TO_ARROW);
		add(nbNotDisplayedElementsLabel);
		
		directArrow = new JButton();
		directArrow.setFont(new Font("Tahoma", Font.BOLD, 11));
		directArrow.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		directArrow.setBackground(UIConstants.BASE_COLOR);
		directArrow.setLocation(0, 140);
		directArrow.setSize(SIZE_X, SIZE_X);
		directArrow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				directArrow.setBackground(UIConstants.MOUSE_ENTERED_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				directArrow.setBackground(UIConstants.BASE_COLOR);
			}
		});
		add(directArrow);
	}
	
	public void addArrowActionListener(ActionListener l) {
		arrow.addActionListener(l);
	}
	
	public void addDirectArrowActionListener(ActionListener l) {
		directArrow.addActionListener(l);
	}
	
	public void setToLeftChoiceArrow() {
		arrow.setText("<");
		directArrow.setText("|<");
	}
	
	public void setToRightChoiceArrow() {
		arrow.setText(">");
		directArrow.setText(">|");
	}
	
	public void setNbNotDisplayedElements(int nbNotDisplayedElements) {
		nbNotDisplayedElementsLabel.setText(Integer.toString(nbNotDisplayedElements));
	}
}

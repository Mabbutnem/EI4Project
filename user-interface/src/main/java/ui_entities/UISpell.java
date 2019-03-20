package ui_entities;

import javax.swing.JPanel;

import spell.ISpell;
import spell.ManaCostSpell;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Font;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UISpell extends JPanel
{
	private static final long serialVersionUID = 7970067395778858488L;
	
	private static final Color baseColor = new Color(211, 211, 211);
	private static final Color mouseEnteredColor = new Color(237, 237, 150);
	private static final Color selectedColor = new Color(135, 206, 235);
	
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextPane textPane;

	private ISpell spell;
	
	private boolean selected;
	
	

	public UISpell(ISpell spell)
	{
		this.spell = spell;
		
		setBorder(new LineBorder(Color.BLACK, 2));
		setLayout(null);
		this.setSize(120, 200);
		
		lblNewLabel = new JLabel();
		if(this.spell instanceof ManaCostSpell) {
			lblNewLabel.setText(Integer.toString(((ManaCostSpell) this.spell).getCost()));
		}
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel.setBounds(1, 1, 37, 37);
		add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel(this.spell.getName());
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel_1.setBounds(37, 1, 83, 37);
		add(lblNewLabel_1);

		textPane = new JTextPane();
		textPane.setText(this.spell.getDescription());
		textPane.setForeground(Color.BLACK);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPane.setEditable(false);
		textPane.setHighlighter(null);
		//Centrer le texte
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		//Centrer le texte fin
		textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textPane.setBorder(new MatteBorder(1, 2, 2, 2, Color.BLACK));
		textPane.setBounds(0, 37, 120, 163);
		add(textPane);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!selected) {
					setAllBackgrounds(mouseEnteredColor);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(selected) {
					setAllBackgrounds(selectedColor);
				}
				else {
					setAllBackgrounds(baseColor);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				setSelected(!selected);
				if(!selected) {
					setAllBackgrounds(mouseEnteredColor);
				}
			}
		});
		
		setAllBackgrounds(baseColor);
	}
	
	private void setAllBackgrounds(Color color) {
		setBackground(color);
		textPane.setBackground(color);
	}

	
	
	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		textPane.addMouseListener(l);
	}
	
	
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		
		if(selected) {
			setAllBackgrounds(selectedColor);
		}
		else {
			setAllBackgrounds(baseColor);
		}
	}
}

package ui_entities;

import javax.swing.JPanel;

import spell.Card;
import spell.ISpell;
import spell.ManaCostSpell;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import listener.ICardListener;

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
	
	private JLabel costLabel;
	private JLabel nameLabel;
	private JTextPane descriptionTextPane;

	private ISpell spell;
	
	private boolean waitingToConfirmSelected;
	private boolean selected;
	private boolean canBeSelected;
	
	

	public UISpell(ISpell spell)
	{
		this.spell = spell;
		waitingToConfirmSelected = false;
		selected = false;
		canBeSelected = true;
		
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setLayout(null);
		setSize(120, 200);

		if(spell instanceof ManaCostSpell) {
			costLabel = new JLabel();
			costLabel.setText(Integer.toString(((ManaCostSpell) spell).getCost()));
			costLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			costLabel.setHorizontalAlignment(SwingConstants.CENTER);
			costLabel.setBorder(new MatteBorder(0, 0, 2, 1, (Color) new Color(0, 0, 0)));
			costLabel.setBounds(0, 0, 37, 37);
			add(costLabel);
		}
		
		nameLabel = new JLabel();
		nameLabel.setText(spell.getName());
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		if(spell instanceof ManaCostSpell) { nameLabel.setBounds(37, 0, 83, 37); }
		else { nameLabel.setBounds(0, 0, 120, 37); }
		add(nameLabel);

		descriptionTextPane = new JTextPane();
		descriptionTextPane.setText(spell.getDescription());
		descriptionTextPane.setOpaque(false);
		descriptionTextPane.setForeground(Color.BLACK);
		descriptionTextPane.setFont(new Font("Tahoma", Font.PLAIN, 12));
		descriptionTextPane.setEditable(false);
		descriptionTextPane.setHighlighter(null);
		//Centrer le texte
		StyledDocument doc = descriptionTextPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		//Centrer le texte fin
		descriptionTextPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		descriptionTextPane.setBorder(null);
		descriptionTextPane.setBounds(4, 40, 112, 160);
		add(descriptionTextPane);
		
		addMouseListener(getUISpellSelectionMouseListener());
		
		setAllBackgrounds(baseColor);
		
		if(spell instanceof Card) {
			((Card) spell).addCardListener(new ICardListener() {

				public void onRevealedChange(boolean actual) {
					setRevealed(actual);
				}
				
			});
			
			setRevealed(((Card) spell).isRevealed());
		}
	}
	
	private MouseListener getUISpellSelectionMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(mouseEnteredColor);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(baseColor);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(!canBeSelected) { return; }
				if(selected) {
					setSelected(false);
					setAllBackgrounds(mouseEnteredColor);
				}
				else {
					waitingToConfirmSelected = true;
				}
			}
		};
	}
	
	private void setAllBackgrounds(Color color) {
		setBackground(color);
		descriptionTextPane.setBackground(color);
	}
	
	public void setRevealed(boolean revealed) {
		costLabel.setVisible(revealed);
		nameLabel.setVisible(revealed);
		descriptionTextPane.setVisible(revealed);
	}
	
	@Override
	public void addMouseListener(MouseListener l) {
		super.addMouseListener(l);
		descriptionTextPane.addMouseListener(l);
	}
	
	

	public ISpell getSpell() {
		return spell;
	}
	
	public boolean isWaitingToConfirmSelected() {
		return waitingToConfirmSelected;
	}
	
	public void confirmSelected() {
		waitingToConfirmSelected = false;
		setSelected(true);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if(selected) {
			setAllBackgrounds(selectedColor);
		}
		else {
			setAllBackgrounds(baseColor);
		}
		
		this.selected = selected;
	}

	public boolean isCanBeSelected() {
		return canBeSelected;
	}

	public void setCanBeSelected(boolean canBeSelected) {
		this.canBeSelected = canBeSelected;
	}
}

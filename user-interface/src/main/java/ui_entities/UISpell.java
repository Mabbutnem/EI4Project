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
	
	public static final int SIZE_X = 120;
	public static final int SIZE_Y = 170;
	private static final int SIZE_HEADER = 34;
	
	private JLabel costLabel;
	private JLabel nameLabel;
	private JTextPane descriptionTextPane;
	
	private ICardListener cardListener;

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
		setSize(SIZE_X, SIZE_Y);

		if(spell instanceof ManaCostSpell) {
			costLabel = new JLabel();
			costLabel.setText(Integer.toString(((ManaCostSpell) spell).getCost()));
			costLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			costLabel.setHorizontalAlignment(SwingConstants.CENTER);
			costLabel.setBorder(new MatteBorder(0, 0, 2, 1, (Color) new Color(0, 0, 0)));
			costLabel.setBounds(0, 0, SIZE_HEADER, SIZE_HEADER);
			add(costLabel);
		}
		
		nameLabel = new JLabel();
		nameLabel.setText(spell.getName());
		nameLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 0)));
		if(spell instanceof ManaCostSpell) { nameLabel.setBounds(SIZE_HEADER, 0, SIZE_X - SIZE_HEADER, SIZE_HEADER); }
		else { nameLabel.setBounds(0, 0, SIZE_X, SIZE_HEADER); }
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
		descriptionTextPane.setLocation(5, SIZE_HEADER + 5);
		descriptionTextPane.setSize(SIZE_X - 10, SIZE_Y - SIZE_HEADER);
		add(descriptionTextPane);
		
		addMouseListener(getUISpellSelectionMouseListener());
		
		setAllBackgrounds(UIConstants.BASE_COLOR);
		
		if(spell instanceof Card) {
			
			cardListener = new ICardListener() {

				public void onRevealedChange(boolean actual) {
					setRevealed(actual);
				}
			};
			
			((Card) spell).addCardListener(cardListener);
			
			setRevealed(((Card) spell).isRevealed());
		}
	}
	
	private MouseListener getUISpellSelectionMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(UIConstants.MOUSE_ENTERED_COLOR);
				}
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				if(!canBeSelected) { return; }
				if(!selected) {
					setAllBackgrounds(UIConstants.BASE_COLOR);
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(!canBeSelected) { return; }
				if(selected) {
					setSelected(false);
					setAllBackgrounds(UIConstants.MOUSE_ENTERED_COLOR);
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
	
	public void clearListeners() {
		if(spell instanceof Card) {
			((Card) spell).removeCardListener(cardListener);
		}
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
	
	public void cancelSelected() {
		waitingToConfirmSelected = false;
		selected = false;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		if(selected) {
			setAllBackgrounds(UIConstants.SELECTED_COLOR);
		}
		else {
			setAllBackgrounds(UIConstants.BASE_COLOR);
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

package ui_entities;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import spell.Card;
import spell.ISpell;
import zone.Zone;
import zone.ZoneType;

import javax.swing.border.TitledBorder;

import javafx.collections.ListChangeListener;
import javax.swing.SwingConstants;

public class UICardArray extends JPanel
{
	private static final long serialVersionUID = -5065484882461285196L;

	private static final int NB_CARDS_DISPLAYED = 6;
	private static final int TOP_DIST_TO_BORDER_Y = 34;
	private static final int BOTTOM_DIST_TO_BORDER_Y = 20;
	private static final int DIST_CARD_TO_CARD = 18;
	
	private ListChangeListener<Card> zoneListener;
	
	private Border border;
	
	private Zone zone;
	private UISpell[] uispells;
	
	private int nbSelected;
	private int nbCanBeSelected;

	//Arrows
	private int actualDisplayedFirstIdx;
	private UIChoiceArrow leftArrow;
	private UIChoiceArrow rightArrow;

	private JLabel bottomLabel;
	private JLabel topLabel;
	
	

	/**
	 * @wbp.parser.constructor
	 */
	public UICardArray(Card[] cards)
	{
		initialize(cards);
	}
	
	public UICardArray(Zone zoneToDisplay)
	{
		this.zone = zoneToDisplay;
		
		initialize(zoneToDisplay.getCards());
		
		zoneListener = new ListChangeListener<Card>() {

			public void onChanged(Change<? extends Card> arg0) {
				arg0.next();
				
				//On décale l'ensemble des afficheurs pour aller tout à gauche
				shiftUISpells(actualDisplayedFirstIdx, 0, uispells.length, true);

				if(arg0.wasAdded()) {
					
					shiftUISpells(arg0.getAddedSize(), arg0.getFrom(), uispells.length, false);
					
					List<UISpell> addedList = new LinkedList<>();
					for(int i = 0; i < arg0.getAddedSubList().size(); i++) {
						addedList.add(new UISpell(arg0.getAddedSubList().get(i)));
						addedList.get(i).setLocation(
								UIChoiceArrow.SIZE_X + (i+arg0.getFrom())*(UISpell.SIZE_X+DIST_CARD_TO_CARD) + 2*DIST_CARD_TO_CARD,
								TOP_DIST_TO_BORDER_Y);
						addedList.get(i).addMouseListener(getUICardArrayMouseListener());
						add(addedList.get(i));
					}
					
					List<UISpell> list = new LinkedList<>(Arrays.asList(uispells));
					list.addAll(arg0.getFrom(), addedList);
					
					uispells = list.toArray(new UISpell[0]);
				}
				else if(arg0.wasRemoved()) {

					shiftUISpells(-arg0.getRemovedSize(), arg0.getFrom(), uispells.length, false);
					
					List<UISpell> removedList = new LinkedList<>();
					for(UISpell uispell : uispells) {
						if(arg0.getRemoved().contains(uispell.getSpell())){
							removedList.add(uispell);
							uispell.clearListeners();
							remove(uispell);
						}
					}
					
					List<UISpell> list = new LinkedList<>(Arrays.asList(uispells));
					list.removeAll(removedList);
					
					uispells = list.toArray(new UISpell[0]);
				}
				
				//On déselectionne tout
				nbSelected = 0;
				for(int i = 0; i < uispells.length; i++) {
					uispells[i].setSelected(false);
				}

				((TitledBorder) border).setTitle(zone.getZoneType() + " (" + zone.size() + ")");
				refreshTopLabel();
				refreshArrows();
				refreshUISpellsVisibility();
				refreshPanelSize();
				
				repaint(); //On redéssine le tout !
			}
			
		};
		
		zoneToDisplay.addListener(zoneListener);

		((TitledBorder) border).setTitle(zoneToDisplay.getZoneType() + " (" + zoneToDisplay.size() + ")");
		
		bottomLabel.setVisible(zoneToDisplay.getZoneType() == ZoneType.DECK);
		topLabel.setVisible(zoneToDisplay.getZoneType() == ZoneType.DECK);
	}
	
	private void initialize(Card[] cards)
	{
		nbSelected = 0;
		nbCanBeSelected = 1;
		
		border = new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "", TitledBorder.TRAILING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0));
		setBorder(border);
		setLayout(null);
		
		bottomLabel = new JLabel("Bottom");
		bottomLabel.setVerticalAlignment(SwingConstants.TOP);
		bottomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bottomLabel.setSize(UIChoiceArrow.SIZE_X + 2*DIST_CARD_TO_CARD, BOTTOM_DIST_TO_BORDER_Y);
		bottomLabel.setLocation(0, TOP_DIST_TO_BORDER_Y + UISpell.SIZE_Y);
		bottomLabel.setVisible(false);
		add(bottomLabel);
		
		topLabel = new JLabel("Top");
		topLabel.setVerticalAlignment(SwingConstants.TOP);
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setSize(UIChoiceArrow.SIZE_X + 2*DIST_CARD_TO_CARD, BOTTOM_DIST_TO_BORDER_Y);
		topLabel.setVisible(false);
		add(topLabel);
		
		initializeUISpells(cards);
		
		actualDisplayedFirstIdx = 0;
		
		leftArrow = new UIChoiceArrow();
		leftArrow.setLocation(DIST_CARD_TO_CARD, TOP_DIST_TO_BORDER_Y);
		leftArrow.setToLeftChoiceArrow();
		leftArrow.addArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(1, 0, uispells.length, true);
			}
		});
		leftArrow.addDirectArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(actualDisplayedFirstIdx, 0, uispells.length, true);
			}
		});
		add(leftArrow);
		
		rightArrow = new UIChoiceArrow();
		rightArrow.setLocation(UIChoiceArrow.SIZE_X + NB_CARDS_DISPLAYED*(UISpell.SIZE_X+DIST_CARD_TO_CARD) + 2*DIST_CARD_TO_CARD, TOP_DIST_TO_BORDER_Y);
		rightArrow.setToRightChoiceArrow();
		rightArrow.addArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(-1, 0, uispells.length, true);
			}
		});
		rightArrow.addDirectArrowActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				shiftUISpells(NB_CARDS_DISPLAYED + actualDisplayedFirstIdx - uispells.length, 0, uispells.length, true);
			}
		});
		add(rightArrow);
		


		refreshTopLabel();
		refreshArrows();
		refreshUISpellsVisibility();
		refreshPanelSize();
	}
	
	private void initializeUISpells(Card[] cards)
	{
		uispells = new UISpell[cards.length];
		
		for(int i = 0; i < uispells.length; i++)
		{
			uispells[i] = new UISpell(cards[i]);
			uispells[i].setLocation( UIChoiceArrow.SIZE_X + i*(UISpell.SIZE_X+DIST_CARD_TO_CARD) + 2*DIST_CARD_TO_CARD, TOP_DIST_TO_BORDER_Y);
			uispells[i].addMouseListener(getUICardArrayMouseListener());
			add(uispells[i]);
		}
	}
	
	private MouseAdapter getUICardArrayMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
					if(nbCanBeSelected == 1) {
						confirmSelectionAndUnselectAllOthers();
					}
					else if(nbSelected < nbCanBeSelected) {
						confirmSelection();
					}
					else {
						cancelSelection();
					}
					
					//Maj de nbSelected
					nbSelected = getSelectedSpells().length;
			}
		};
	}
	
	private void confirmSelectionAndUnselectAllOthers() {
		for(int j = 0; j < uispells.length; j++) {
			if(uispells[j].isWaitingToConfirmSelected()) {
				uispells[j].confirmSelected();
			}
			else if(uispells[j].isSelected()) {
				uispells[j].setSelected(false);
			}
		}
	}
	
	private void confirmSelection() {
		for(int j = 0; j < uispells.length; j++) {
			if(uispells[j].isWaitingToConfirmSelected()) {
				uispells[j].confirmSelected();
			}
		}
	}
	
	private void cancelSelection() {
		for(int j = 0; j < uispells.length; j++) {
			if(uispells[j].isWaitingToConfirmSelected()) {
				uispells[j].cancelSelected();
			}
		}
	}
	
	private void shiftUISpells(int delta, int firstIdx, int lastIdx, boolean modifyActualDisplayedFirstIdx) { //lastIdx is not included
		if(modifyActualDisplayedFirstIdx) { actualDisplayedFirstIdx -= delta; }
		
		for(int i = firstIdx; i < lastIdx; i++) {
			uispells[i].setLocation(uispells[i].getX() + delta*(UISpell.SIZE_X + DIST_CARD_TO_CARD), uispells[i].getY());
			uispells[i].setVisible(actualDisplayedFirstIdx <= i && i < actualDisplayedFirstIdx+NB_CARDS_DISPLAYED);
		}
		
		refreshArrows();
		
		repaint();
	}
	
	private void refreshArrows() {
		leftArrow.setVisible(actualDisplayedFirstIdx > 0);
		leftArrow.setNbNotDisplayedElements(actualDisplayedFirstIdx);
		
		rightArrow.setVisible(actualDisplayedFirstIdx < uispells.length - NB_CARDS_DISPLAYED);
		rightArrow.setNbNotDisplayedElements(uispells.length - NB_CARDS_DISPLAYED - actualDisplayedFirstIdx);
	}
	
	private void refreshUISpellsVisibility() { //Seulement utilisée dans zone listener
		for(int i = 0; i < uispells.length; i++) {
			uispells[i].setVisible(actualDisplayedFirstIdx <= i && i < actualDisplayedFirstIdx+NB_CARDS_DISPLAYED);
		}
	}
	
	private void refreshPanelSize() {
		setSize( 2*UIChoiceArrow.SIZE_X + Math.min(NB_CARDS_DISPLAYED, uispells.length)*(UISpell.SIZE_X+DIST_CARD_TO_CARD) + 3*DIST_CARD_TO_CARD,
				TOP_DIST_TO_BORDER_Y + BOTTOM_DIST_TO_BORDER_Y + UISpell.SIZE_Y);
	}
	
	private void refreshTopLabel() {
		topLabel.setLocation(
				UIChoiceArrow.SIZE_X + Math.min(NB_CARDS_DISPLAYED, uispells.length)*(UISpell.SIZE_X+DIST_CARD_TO_CARD) + DIST_CARD_TO_CARD,
				TOP_DIST_TO_BORDER_Y + UISpell.SIZE_Y);
	}
	
	public void clearListeners() {
		for(UISpell uispell : uispells) {
			uispell.clearListeners();
		}
		if(zone != null) { zone.removeListener(zoneListener); }
	}
	
	
	
	public int getNbCanBeSelected() {
		return nbCanBeSelected;
	}

	public void setNbCanBeSelected(int nbCanBeSelected) {
		this.nbCanBeSelected = nbCanBeSelected;
	}
	
	public void setTitle(String title) {
		border = new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), title, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0));
		setBorder(border);
	}

	public ISpell getFirstSelectedSpell()
	{
		for(UISpell uispell : uispells) {
			if(uispell.isSelected()) {
				return uispell.getSpell();
			}
		}
		
		return null;
	}
	
	public ISpell[] getSelectedSpells() {
		List<ISpell> list = new LinkedList<>();
		
		int i = 0;
		while(i < uispells.length && list.size() < nbCanBeSelected) {
			if(uispells[i].isSelected()) {
				list.add(uispells[i].getSpell());
			}
			i++;
		}
		
		return list.toArray(new ISpell[0]);
	}
	

	public void addMouseListenerToUISpells(MouseAdapter mouseAdapter) {
		for(UISpell uispell : uispells) {
			uispell.addMouseListener(mouseAdapter);
		}
	}
}

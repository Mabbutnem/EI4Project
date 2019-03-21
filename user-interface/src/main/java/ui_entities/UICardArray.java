package ui_entities;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import spell.Card;
import zone.Zone;
import javax.swing.border.TitledBorder;

import javafx.collections.ListChangeListener;

public class UICardArray extends JPanel
{
	private static final long serialVersionUID = -5065484882461285196L;

	public static final int NB_CARDS_DISPLAYED = 3;
	public static final int DIST_TO_BORDER_X = 15;
	public static final int DIST_TO_BORDER_Y = 20;
	public static final int DIST_CARD_TO_CARD = 30;
	
	//private Zone zone;
	private UISpell[] uispells;
	
	

	/**
	 * @wbp.parser.constructor
	 */
	public UICardArray(Card[] cards)
	{
		initialize(cards);
	}
	
	public UICardArray(Zone zone)
	{
		//this.zone = zone;
		
		initialize(zone.getCards());
		
		zone.addListener(new ListChangeListener<Card>() {

			public void onChanged(Change<? extends Card> arg0) {
				arg0.next();
				
				//On détruit l'ensemble des composants
				for(UISpell uispell : uispells) {
					remove(uispell);
				}

				initializeUISpells(arg0.getList().toArray(new Card[0]));
				
				repaint(); //On redéssine le tout !
			}
			
		});
	}
	
	private void initialize(Card[] cards)
	{
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Zone", TitledBorder.TRAILING, TitledBorder.BELOW_TOP, null, new Color(0, 0, 0)));
		setLayout(null);
		setSize(800, 2*DIST_TO_BORDER_Y + 300);
		
		initializeUISpells(cards);
	}
	
	private void initializeUISpells(Card[] cards)
	{
		uispells = new UISpell[cards.length];
		
		for(int i = 0; i < uispells.length; i++)
		{
			uispells[i] = new UISpell(cards[i]);
			uispells[i].setLocation(10 + i*150, DIST_TO_BORDER_Y);
			uispells[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for(int j = 0; j < uispells.length; j++) {
						if(uispells[j].isWaitingToConfirmSelected()) {
							uispells[j].confirmSelected();
						}
						else if(uispells[j].isSelected()) {
							uispells[j].setSelected(false);
						}
					}
				}
			});
			add(uispells[i]);
		}
	}
	
	
	
	public UISpell getSelectedUispell()
	{
		for(UISpell uispell : uispells) {
			if(uispell.isSelected()) {
				return uispell;
			}
		}
		
		return null;
	}
	

	public void addMouseListenerToUISpells(MouseAdapter mouseAdapter) {
		for(UISpell uispell : uispells) {
			uispell.addMouseListener(mouseAdapter);
		}
	}
}

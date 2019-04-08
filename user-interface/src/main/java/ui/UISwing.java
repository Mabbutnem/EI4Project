package ui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import boardelement.Wizard;
import boardelement.WizardFactory;
import business.IBusiness;
import effect.IEffect;
import effect.TargetableEffect;
import effect.YouCanEffect;
import game.Game;
import listener.ICardArrayRequestListener;
import spell.Card;
import spell.Incantation;
import ui_entities.UIBoardElementArray;
import ui_entities.UICardArray;
import ui_entities.UICardArrayRequestListener;
import ui_entities.UIConstants;
import ui_entities.UISpell;
import ui_entities.UITargetRequestListener;
import ui_entities.UIWizardFactoryArray;
import ui_entities.UIWizardFactoryChoice;
import ui_entities.UIYouCanEffectListener;
import zone.Zone;
import zone.ZoneGroup;
import zone.ZoneType;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class UISwing extends JFrame implements IUI{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IBusiness business;
	
	private Rectangle screenSize;
	
	private JLabel exceptionMessageLabel;
	
	//Main menu :
	private DefaultListModel<String> gameNameList;
	private JList<String> gameNameJList;
	private JScrollPane savedGamesScrollPane;
	private JButton deleteGameButton;
	private JButton loadGameButton;
	private JTextField newGameNameTextField;
	private JButton newGameButton;
	
	//Game :
	private UIBoardElementArray uiBoard;
	private UICardArray uiCards;
	private UISpell uiCastZone;
	private UISpell uiDeck;
	private UISpell uiDiscardZone;
	private JButton continueButton;
	
	private JButton handButton;
	private JButton deckButton; //TODO remove
	private JButton discardZoneButton; //TODO remove
	private JButton burnZoneButton;
	private JButton voidZoneButton;
	private JButton banishZoneButton;
	
	private JButton endTurnButton;


	
	public UISwing() {
		super();
		
		screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		
		setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		exceptionMessageLabel = new JLabel("");
		exceptionMessageLabel.setForeground(Color.RED);
		exceptionMessageLabel.setLocation(10, 795);
		exceptionMessageLabel.setSize(1564, 14);
		getContentPane().add(exceptionMessageLabel);
		
		savedGamesScrollPane = new JScrollPane();
		savedGamesScrollPane.setBorder(null);
		savedGamesScrollPane.setViewportBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Saved games", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		savedGamesScrollPane.setBounds(10, 11, 300, 192);
		getContentPane().add(savedGamesScrollPane);
		
		gameNameList = new DefaultListModel<>();
		
		gameNameJList = new JList<>(gameNameList);
		gameNameJList.setSelectionBackground(UIConstants.SELECTED_COLOR);
		gameNameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		savedGamesScrollPane.setViewportView(gameNameJList);
		
		newGameNameTextField = new JTextField();
		newGameNameTextField.setBounds(10, 249, 145, 20);
		getContentPane().add(newGameNameTextField);
		newGameNameTextField.setColumns(10);
		
		newGameButton = new JButton("New game");
		newGameButton.setBorder(new LineBorder(Color.BLUE));
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Preconditions.checkState(newGameNameTextField.getText().compareTo("") != 0, "You must enter a game name");
					
					Preconditions.checkState(!business.gameExists(newGameNameTextField.getText()), "A game with the same name already exists");
					
					setMainMenuVisible(false);

					List<WizardFactory> choosenWf = new LinkedList<>();
					UIWizardFactoryArray uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
					UIWizardFactoryChoice wizardFactoryChoice = new UIWizardFactoryChoice(getContentPane());
					
					for(int i = 0; i < Game.getGameConstant().getNbWizard(); i++) {
						choosenWf.add(wizardFactoryChoice.chooseWizardFactory(
								business.getRandomWizards(i+1, choosenWf.toArray(new WizardFactory[0]))));
				
						getContentPane().remove(uiWfArray);
						uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
						uiWfArray.setLocation((int)screenSize.getWidth()/2 - uiWfArray.getWidth()/2, 11);
						getContentPane().add(uiWfArray);
						getContentPane().repaint();
					}
					getContentPane().remove(uiWfArray);
					
					business.newGame(new Game(
							newGameNameTextField.getText(),
							business.createWizards(choosenWf.toArray(new WizardFactory[0]))
							));
					refreshGameNameList();

					business.nextLevel();
					business.beginWizardsTurn();
					
					initGameMenu();
					setGameMenuVisible(true);
					
					business.saveGame();
				
				} catch(Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
					setMainMenuVisible(true);
				}
			}
		});
		newGameButton.setBounds(165, 248, 145, 23);
		getContentPane().add(newGameButton);
		
		loadGameButton = new JButton("Load game");
		loadGameButton.setBorder(new LineBorder(Color.BLUE));
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
						business.loadGame(gameNameJList.getSelectedValue());

						setMainMenuVisible(false);
						
						initGameMenu();
						setGameMenuVisible(true);
					} catch (Exception e) {
						exceptionMessageLabel.setText(e.getMessage());
						e.printStackTrace();
					}
			}
		});
		loadGameButton.setBounds(165, 214, 145, 23);
		getContentPane().add(loadGameButton);
		
		deleteGameButton = new JButton("Delete game");
		deleteGameButton.setBorder(new LineBorder(new Color(255, 0, 0)));
		deleteGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					business.deleteGame(gameNameJList.getSelectedValue());
					refreshGameNameList();
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		deleteGameButton.setBounds(10, 214, 145, 23);
		getContentPane().add(deleteGameButton);
		
		endTurnButton = new JButton("End turn");
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					business.endWizardsTurn();
				
					business.nextMonsterWave();

					while(!business.monstersTurnEnded())
					{
						business.playMonstersTurnPart1();
						if(business.currentCharacterInWizardsRange())
						{
							refreshUICastZone();
							break;
						}
						while(!business.castZoneIsEmpty())
						{
							business.castNextSpell();
							refreshUICastZone();
						}
						business.playMonstersTurnPart2();
						business.nextMonster();
					}

					if(business.monstersTurnEnded())
					{
						business.beginWizardsTurn();
					}
					
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		endTurnButton.setSize(89, 23);
		endTurnButton.setLocation((int)(0.9*screenSize.getWidth()), (int)(0.9*screenSize.getHeight()));
		endTurnButton.setVisible(false);
		getContentPane().add(endTurnButton);
		
		continueButton = new JButton("Continue");
		continueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					while(!business.castZoneIsEmpty())
					{
						business.castNextSpell();
						refreshUICastZone();
					}
					business.playMonstersTurnPart2();
					business.nextMonster();
					
					while(!business.monstersTurnEnded())
					{
						business.playMonstersTurnPart1();
						if(business.currentCharacterInWizardsRange())
						{
							refreshUICastZone();
							break;
						}
						while(!business.castZoneIsEmpty())
						{
							business.castNextSpell();
							refreshUICastZone();
						}
						business.playMonstersTurnPart2();
						business.nextMonster();
					}

					if(business.monstersTurnEnded())
					{
						business.beginWizardsTurn();
					}
				
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		continueButton.setBounds(1419, 666, 89, 23);
		continueButton.setVisible(false);
		getContentPane().add(continueButton);
		
		handButton = new JButton("Hand");
		handButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.HAND);
				refreshUICards();
			}
		});
		handButton.setBounds(10, 727, 89, 23);
		handButton.setVisible(false);
		getContentPane().add(handButton);

		deckButton = new JButton("Deck");
		deckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.DECK);
				refreshUICards();
			}
		});
		deckButton.setBounds(10, 761, 89, 23);
		deckButton.setVisible(false);
		getContentPane().add(deckButton);

		discardZoneButton = new JButton("Discard zone");
		discardZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.DISCARD);
				refreshUICards();
			}
		});
		discardZoneButton.setBounds(10, 693, 89, 23);
		discardZoneButton.setVisible(false);
		getContentPane().add(discardZoneButton);

		burnZoneButton = new JButton("Burn zone");
		burnZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.BURN);
				refreshUICards();
			}
		});
		burnZoneButton.setBounds(109, 761, 89, 23);
		burnZoneButton.setVisible(false);
		getContentPane().add(burnZoneButton);

		voidZoneButton = new JButton("Void zone");
		voidZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.VOID);
				refreshUICards();
			}
		});
		voidZoneButton.setBounds(109, 693, 89, 23);
		voidZoneButton.setVisible(false);
		getContentPane().add(voidZoneButton);

		banishZoneButton = new JButton("Banish zone");
		banishZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.BANISH);
				refreshUICards();
			}
		});
		banishZoneButton.setBounds(109, 727, 89, 23);
		banishZoneButton.setVisible(false);
		getContentPane().add(banishZoneButton);
	}
	
	public void set() {
		try {
			ICardArrayRequestListener cardArrayRequestListener = new UICardArrayRequestListener(getContentPane());
			Zone.setCardArrayRequestListener(cardArrayRequestListener);
			ZoneGroup.setCardArrayRequestListener(cardArrayRequestListener);
			Game.setCardDaoListener(business.getDao());
			YouCanEffect.setYouCanEffectListener(new UIYouCanEffectListener(getContentPane()));
			TargetableEffect.setTargetRequestListener(new UITargetRequestListener(getContentPane()));
			
			business.initAllConstant();
			refreshGameNameList();
		}
		catch (Exception e) {
			exceptionMessageLabel.setText(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		setVisible(true);
	}
	
	
	
	
	
	/*
	 * Main menu
	 */
	
	private void refreshGameNameList() throws IOException {
		gameNameList.clear();
		for(Game g : business.getGames()) {
			gameNameList.addElement(g.getName());
		}
	}
	
	private void setMainMenuVisible(boolean arg) {
		savedGamesScrollPane.setVisible(arg);
		deleteGameButton.setVisible(arg);
		loadGameButton.setVisible(arg);
		newGameNameTextField.setVisible(arg);
		newGameButton.setVisible(arg);
	}
	
	/*
	 * Main menu
	 */
	
	
	
	
	
	/*
	 * Game menu
	 */
	
	private void initGameMenu() {
		uiBoard = new UIBoardElementArray(business.getGame(), true);
		uiBoard.setLeftArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(business.canMove()) {
					business.leftWalk();
				}
			}
		});
		uiBoard.setRightArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(business.canMove()) {
					business.rightWalk();
				}
			}
		});
		uiBoard.setBusinessChangeMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				business.setSelectedCharacter(uiBoard.getSelectedCharacter());
				
				refreshUICards();
			}
		});
		uiBoard.setLocation((int)(screenSize.getWidth()-uiBoard.getWidth())/2, 0);
		getContentPane().add(uiBoard);
		
		uiCards = new UICardArray(new Card[0]);
		getContentPane().add(uiCards);
		uiDeck = new UISpell(new Incantation("Empty deck", new IEffect[0]));
		getContentPane().add(uiDeck);
		refreshUICards();
		
		uiCastZone = new UISpell(new Incantation("Cast zone", new IEffect[0]));
		getContentPane().add(uiCastZone);
		refreshUICastZone();
		
		repaint();
	}
	
	private void setGameMenuVisible(boolean arg) {
		uiBoard.setVisible(arg);
		uiCards.setVisible(arg);
		
		endTurnButton.setVisible(arg);
		
		handButton.setVisible(arg);
		deckButton.setVisible(arg);
		discardZoneButton.setVisible(arg);
		burnZoneButton.setVisible(arg);
		voidZoneButton.setVisible(arg);
		banishZoneButton.setVisible(arg);
	}
	
	private void refreshUICards()
	{
		getContentPane().remove(uiCards);
		getContentPane().remove(uiDeck);
		
		if(business.getSelectedCharacter() instanceof Wizard)
		{
			Wizard w = (Wizard) business.getSelectedCharacter();
			
			uiCards = new UICardArray(w.getZoneGroup().getZone(business.getSelectedZone()));
			if(business.getSelectedZone() != ZoneType.HAND) {
				uiCards.setNbCanBeSelected(0);
			}
			uiCards.setLocation((int)(screenSize.getWidth()-uiCards.getWidth())/2, 
								(int)(0.9*(screenSize.getHeight()-uiCards.getHeight())));
			uiCards.setBusinessChangeMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					business.setSelectedCard((Card) uiCards.getFirstSelectedSpell());
				}
			});
			getContentPane().add(uiCards);
			
			if(w.getZoneGroup().size(ZoneType.DECK) > 0) {
				uiDeck = new UISpell(w.getZoneGroup().getCards(ZoneType.DECK)[0]);
			}
			else {
				uiDeck = new UISpell(new Incantation("Empty deck", new IEffect[0]));
			}
			uiDeck.setLocation((int)(0.1*(screenSize.getWidth()-uiDeck.getWidth())), 
								(int)(0.5*(screenSize.getHeight()-uiDeck.getHeight())));
			uiDeck.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					business.setSelectedZone(ZoneType.DECK);
					refreshUICards();
				}
			});
			getContentPane().add(uiDeck);
		}

		repaint();
	}
	
	private void refreshUICastZone()
	{
		getContentPane().remove(uiCastZone);
		
		if(business.isWizardsTurn())
		{
			uiBoard.setEnabled(true);
			continueButton.setVisible(false);
			endTurnButton.setVisible(true);
			
			if(business.getNextSpellToCast() == null)
			{
				uiCastZone = new UISpell(new Incantation("Cast zone", new IEffect[0]));
				uiCastZone.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						business.addSelectedCardToCast();
						refreshUICastZone();
						while(!business.castZoneIsEmpty())
						{
							business.castNextSpell();
							refreshUICastZone();
						}
					}
				});
			}
			else
			{
				uiCastZone = new UISpell(business.getNextSpellToCast());
			}
		}
		else
		{
			uiBoard.setEnabled(false);
			continueButton.setVisible(true);
			endTurnButton.setVisible(false);
			
			if(business.getNextSpellToCast() == null)
			{
				uiCastZone = new UISpell(new Incantation("No incantation thrown", new IEffect[0]));
			}
			else
			{
				uiCastZone = new UISpell(business.getNextSpellToCast());
			}
		}

		uiCastZone.setLocation((int)(screenSize.getWidth() - uiCastZone.getWidth())/2, 
								(int)(0.5*(screenSize.getHeight() - uiCastZone.getHeight())));
		getContentPane().add(uiCastZone);

		repaint();
	}
}

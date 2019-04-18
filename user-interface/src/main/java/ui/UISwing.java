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
import javafx.collections.ListChangeListener;
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
import java.awt.Dimension;
import javax.swing.SwingConstants;

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
	private JLabel deckLabel;
	private UISpell uiDeck;
	private JLabel discardZoneLabel;
	private UISpell uiDiscardZone;
	private JButton continueButton;
	
	private JButton handButton;
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
		endTurnButton.setBorder(new LineBorder(new Color(153, 204, 0)));
		endTurnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					business.endWizardsTurn();
				
					business.nextMonsterWave();

					while(!business.monstersTurnEnded())
					{
						business.setSelectedCharacter(business.getCurrentCharacter());
						uiBoard.setSelectedCharacter(business.getSelectedCharacter());
						
						business.playMonstersTurnPart1();
						if(business.currentCharacterInWizardsRange())
						{
							refreshUICastZone();
							return;
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

						business.setSelectedCharacter(business.getCurrentCharacter());
						uiBoard.setSelectedCharacter(business.getSelectedCharacter());
					}
					
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		endTurnButton.setSize(89, 23);
		endTurnButton.setLocation(1460, 248);
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
						business.setSelectedCharacter(business.getCurrentCharacter());
						uiBoard.setSelectedCharacter(business.getSelectedCharacter());
						
						business.playMonstersTurnPart1();
						if(business.currentCharacterInWizardsRange())
						{
							refreshUICastZone();
							return;
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

						refreshUICastZone();
						business.setSelectedCharacter(business.getCurrentCharacter());
						uiBoard.setSelectedCharacter(business.getSelectedCharacter());
					}
				
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		continueButton.setBounds(982, 525, 89, 23);
		continueButton.setVisible(false);
		getContentPane().add(continueButton);
		
		deckLabel = new JLabel("Deck");
		deckLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		deckLabel.setHorizontalAlignment(SwingConstants.CENTER);
		deckLabel.setSize(40, 14);
		deckLabel.setVisible(false);
		getContentPane().add(deckLabel);
		
		discardZoneLabel = new JLabel("Discard zone");
		discardZoneLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		discardZoneLabel.setHorizontalAlignment(SwingConstants.CENTER);
		discardZoneLabel.setSize(90, 14);
		discardZoneLabel.setVisible(false);
		getContentPane().add(discardZoneLabel);
		
		handButton = new JButton("Hand");
		handButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		handButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.HAND);
				refreshUICards();
				refreshUIDeck();
				refreshUIDiscardZone();
			}
		});
		handButton.setSize(114, 23);
		handButton.setLocation((int)(screenSize.getWidth()-handButton.getWidth())/2,
								(int)(0.65*(screenSize.getHeight()-handButton.getHeight())));
		handButton.setVisible(false);
		getContentPane().add(handButton);

		burnZoneButton = new JButton("Burn zone");
		burnZoneButton.setBorder(new LineBorder(new Color(255, 102, 0)));
		burnZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.BURN);
				refreshUICards();
				refreshUIDeck();
				refreshUIDiscardZone();
			}
		});
		burnZoneButton.setBounds(1460, 446, 114, 23);
		burnZoneButton.setVisible(false);
		getContentPane().add(burnZoneButton);

		voidZoneButton = new JButton("Void zone");
		voidZoneButton.setBorder(new LineBorder(new Color(102, 0, 102)));
		voidZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.VOID);
				refreshUICards();
				refreshUIDeck();
				refreshUIDiscardZone();
			}
		});
		voidZoneButton.setBounds(1460, 480, 114, 23);
		voidZoneButton.setVisible(false);
		getContentPane().add(voidZoneButton);

		banishZoneButton = new JButton("Banish zone");
		banishZoneButton.setBorder(new LineBorder(new Color(255, 255, 255)));
		banishZoneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				business.setSelectedZone(ZoneType.BANISH);
				refreshUICards();
				refreshUIDeck();
				refreshUIDiscardZone();
			}
		});
		banishZoneButton.setBounds(1460, 514, 114, 23);
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
				
				if(uiBoard.getSelectedCharacter() instanceof Wizard) {
					
					((Wizard) uiBoard.getSelectedCharacter()).getZoneGroup().addListener(
							new ListChangeListener<Card>() {
								@Override
								public void onChanged(Change<? extends Card> arg0) {
									refreshUIDeck();
								}
							},
							ZoneType.DECK);

					((Wizard) uiBoard.getSelectedCharacter()).getZoneGroup().addListener(
							new ListChangeListener<Card>() {
								@Override
								public void onChanged(Change<? extends Card> arg0) {
									refreshUIDiscardZone();
								}
							},
							ZoneType.DISCARD);
				}
				
				refreshUICards();
				refreshUIDeck();
				refreshUIDiscardZone();
			}
		});
		uiBoard.setLocation((int)(screenSize.getWidth()-uiBoard.getWidth())/2, 0);
		getContentPane().add(uiBoard);
		
		uiCards = new UICardArray(new Card[0]);
		getContentPane().add(uiCards);
		refreshUICards();
		
		uiDeck = new UISpell(new Incantation("Empty deck", new IEffect[0]));
		getContentPane().add(uiDeck);
		refreshUIDeck();
		
		uiDiscardZone = new UISpell(new Incantation("Empty discard zone", new IEffect[0]));
		getContentPane().add(uiDiscardZone);
		refreshUIDiscardZone();
		
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
		burnZoneButton.setVisible(arg);
		voidZoneButton.setVisible(arg);
		banishZoneButton.setVisible(arg);
	}
	
	private void refreshUICards()
	{
		getContentPane().remove(uiCards);
		
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
		}

		repaint();
	}
	
	private void refreshUIDeck()
	{
		getContentPane().remove(uiDeck);
		deckLabel.setVisible(false);
		
		if(business.getSelectedCharacter() instanceof Wizard)
		{
			Wizard w = (Wizard) business.getSelectedCharacter();

			int size = w.getZoneGroup().size(ZoneType.DECK);
			if(size > 0) {
				uiDeck = new UISpell(w.getZoneGroup().getCards(ZoneType.DECK)[size-1]);
				deckLabel.setVisible(true);
			}
			else {
				uiDeck = new UISpell(new Incantation("Empty deck", new IEffect[0]));
				uiDeck.setVisible(false);
			}
			uiDeck.setLocation((int)(0.075*(screenSize.getWidth()-uiDeck.getWidth())), 
								(int)(0.9*(screenSize.getHeight()-uiDeck.getHeight())));
			uiDeck.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					business.setSelectedZone(ZoneType.DECK);
					refreshUICards();
				}
			});
			
			deckLabel.setLocation(uiDeck.getX() + (uiDeck.getWidth()-deckLabel.getWidth())/2, 
									uiDeck.getY() - deckLabel.getHeight());
		
			getContentPane().add(uiDeck);
		}
		
		repaint();
	}
	
	private void refreshUIDiscardZone()
	{
		getContentPane().remove(uiDiscardZone);
		discardZoneLabel.setVisible(false);
		
		if(business.getSelectedCharacter() instanceof Wizard)
		{
			Wizard w = (Wizard) business.getSelectedCharacter();
		
			int size = w.getZoneGroup().size(ZoneType.DISCARD);
			if(size > 0) {
				uiDiscardZone = new UISpell(w.getZoneGroup().getCards(ZoneType.DISCARD)[size-1]);
				discardZoneLabel.setVisible(true);
			}
			else {
				uiDiscardZone = new UISpell(new Incantation("Empty dicard zone", new IEffect[0]));
				uiDiscardZone.setVisible(false);
			}
			uiDiscardZone.setLocation((int)(0.925*(screenSize.getWidth()-uiDiscardZone.getWidth())), 
								(int)(0.9*(screenSize.getHeight()-uiDiscardZone.getHeight())));
			uiDiscardZone.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {

					business.setSelectedZone(ZoneType.DISCARD);
					refreshUICards();
				}
			});
			
			discardZoneLabel.setLocation(uiDiscardZone.getX() + (uiDiscardZone.getWidth()-discardZoneLabel.getWidth())/2, 
									uiDiscardZone.getY() - discardZoneLabel.getHeight());
		
			getContentPane().add(uiDiscardZone);
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
						refreshUICards();
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
				uiCastZone.setVisible(false);
			}
			else
			{
				uiCastZone = new UISpell(business.getNextSpellToCast());
			}
		}

		uiCastZone.setLocation((int)(screenSize.getWidth() - uiCastZone.getWidth())/2, 
								(int)(0.4*(screenSize.getHeight() - uiCastZone.getHeight())));
		getContentPane().add(uiCastZone);

		repaint();
	}
}

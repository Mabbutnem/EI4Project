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
					
					initGameMenu();
					setGameMenuVisible(true);

					business.nextLevel();
					business.beginWizardsTurn();
				
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

						business.nextLevel();
						business.beginWizardsTurn();
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
						business.playMonstersTurnPart2();
						business.nextMonster();
					}
					
					business.beginWizardsTurn();
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		});
		endTurnButton.setBounds(118, 749, 89, 23);
		endTurnButton.setVisible(false);
		getContentPane().add(endTurnButton);
		
		
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
		getContentPane().add(uiBoard);
		uiBoard.setLeftArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				business.leftWalk();
			}
		});
		uiBoard.setRightArrowButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				business.rightWalk();
			}
		});
		uiBoard.setBusinessChangeMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				business.setSelectedCharacter(uiBoard.getSelectedCharacter());
				
				refreshUICards();
			}
		});
		
		uiCards = new UICardArray(new Card[0]);
		getContentPane().add(uiCards);
		
		uiCastZone = new UISpell(new Incantation("Cast zone", new IEffect[0]));
		uiCastZone.setLocation(0, 150);
		getContentPane().add(uiCastZone);
		
		repaint();
	}
	
	private void setGameMenuVisible(boolean arg) {
		uiBoard.setVisible(arg);
		uiCards.setVisible(arg);
		endTurnButton.setVisible(arg);
	}
	
	private void refreshUICards() {

		getContentPane().remove(uiCards);
		
		if(business.getSelectedCharacter() instanceof Wizard)
		{
			
			Wizard w = (Wizard) business.getSelectedCharacter();
			uiCards = new UICardArray(w.getZoneGroup().getCards(business.getSelectedZone()));
			uiCards.setLocation(0, 300);

			getContentPane().add(uiCards);
			
		}

		repaint();
	}
}

package ui;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import boardelement.WizardFactory;
import business.IBusiness;
import game.Game;
import listener.ICardArrayRequestListener;
import ui_entities.UICardArrayRequestListener;
import ui_entities.UIConstants;
import ui_entities.UIWizardFactoryArray;
import ui_entities.UIWizardFactoryChoice;
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
				
				} catch(Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					System.out.println(e.getMessage());
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
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
					System.out.println(e.getMessage());
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
					System.out.println(e.getMessage());
				}
			}
		});
		deleteGameButton.setBounds(10, 214, 145, 23);
		getContentPane().add(deleteGameButton);
		
		
	}
	
	public void set() {
		try {
			ICardArrayRequestListener cardArrayRequestListener = new UICardArrayRequestListener(getContentPane());
			Zone.setCardArrayRequestListener(cardArrayRequestListener);
			ZoneGroup.setCardArrayRequestListener(cardArrayRequestListener);
			Game.setCardDaoListener(business.getDao());
			//TargetableEffect.setTargetRequestListener(targetRequestListener);
			
			business.initAllConstant();
			refreshGameNameList();
		}
		catch (Exception e) {
			exceptionMessageLabel.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void run()
	{
		setVisible(true);
	}
	
	
	
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
}

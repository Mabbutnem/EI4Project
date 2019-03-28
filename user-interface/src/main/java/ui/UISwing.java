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
	
	private JLabel exceptionMessageLabel;
	
	private DefaultListModel<String> gameNameList;
	private JList<String> gameNameJList;
	
	private JTextField newGameNameTextField;


	
	public UISwing() {
		super();
		
		setBounds(100, 100, 450, 300); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		exceptionMessageLabel = new JLabel("");
		exceptionMessageLabel.setForeground(Color.RED);
		exceptionMessageLabel.setBounds(10, 236, 414, 14);
		getContentPane().add(exceptionMessageLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setViewportBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), "Saved games", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(0, 0, 0)));
		scrollPane.setBounds(10, 11, 188, 140);
		getContentPane().add(scrollPane);
		
		gameNameList = new DefaultListModel<>();
		
		gameNameJList = new JList<>(gameNameList);
		gameNameJList.setSelectionBackground(UIConstants.SELECTED_COLOR);
		gameNameJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(gameNameJList);
		
		newGameNameTextField = new JTextField();
		newGameNameTextField.setBounds(239, 27, 86, 20);
		getContentPane().add(newGameNameTextField);
		newGameNameTextField.setColumns(10);
		
		JButton newGameButton = new JButton("New game");
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Preconditions.checkState(newGameNameTextField.getText().compareTo("") != 0, "You must enter a game name");
					
					Preconditions.checkState(!business.gameExists(newGameNameTextField.getText()), "A game with the same name already exists");

					List<WizardFactory> choosenWf = new LinkedList<>();
					UIWizardFactoryArray uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
					UIWizardFactoryChoice wizardFactoryChoice = new UIWizardFactoryChoice(getContentPane());
					
					for(int i = 0; i < Game.getGameConstant().getNbWizard(); i++) {
						choosenWf.add(wizardFactoryChoice.chooseWizardFactory(
								business.getRandomWizards(i+1, choosenWf.toArray(new WizardFactory[0]))));
				
						getContentPane().remove(uiWfArray);
						uiWfArray = new UIWizardFactoryArray(choosenWf.toArray(new WizardFactory[0]));
						uiWfArray.setLocation(239, 60);
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
				}
			}
		});
		newGameButton.setBounds(335, 26, 89, 23);
		getContentPane().add(newGameButton);
		
		JButton loadGameButton = new JButton("Load game");
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					business.loadGame(gameNameJList.getSelectedValue());
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
				}
			}
		});
		loadGameButton.setBounds(10, 162, 89, 23);
		getContentPane().add(loadGameButton);
		
		JButton deleteGameButton = new JButton("Delete game");
		deleteGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					business.deleteGame(gameNameJList.getSelectedValue());
					refreshGameNameList();
				} catch (Exception e) {
					exceptionMessageLabel.setText(e.getMessage());
				}
			}
		});
		deleteGameButton.setBounds(109, 162, 89, 23);
		getContentPane().add(deleteGameButton);
		
		
	}
	
	public void set() {
		try {
			ICardArrayRequestListener cardArrayRequestListener = new UICardArrayRequestListener(getContentPane());
			Zone.setCardArrayRequestListener(cardArrayRequestListener);
			ZoneGroup.setCardArrayRequestListener(cardArrayRequestListener);
			Game.setCardDaoListener(business.getDao());
			
			business.initAllConstant();
			refreshGameNameList();
		}
		catch (Exception e) {
			exceptionMessageLabel.setText(e.getMessage());
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
}

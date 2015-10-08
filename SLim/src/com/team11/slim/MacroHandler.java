package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MacroHandler implements ItemListener, ActionListener, WindowListener {

	private static BufferedReader macroFinder;
	private static FileReader fileIn;
	private static BufferedWriter macroWriter;
	private static ArrayList<String[]> macroList;
	private static String input;
	private static String[] inputBuff;
	private JFrame macroFrame, spawnedFrame;
	private JPanel macroPanel, mButtonPanel;
	private Label shortcut, replacement;
	private JTextField shortcutTF, replacementTF;
	private JButton cancel, ok;
	private Button newMacro, editMacro, delMacro, nullButton1, nullButton2;
	private TextArea macroDisplay;
	private Checkbox macroCheck;
	private static File macroFile;
	public static boolean isOpen = false, guiSpawned = false;

	public static void generateMacros(){
		// Load up existing macros, create shortcuts.txt to store macros if it doesn't exist
		macroFile = new File("shortcuts.txt");
		macroList = new ArrayList<String[]>();
		inputBuff = new String[2];

		try {
			macroFile.createNewFile();
			fileIn = new FileReader(macroFile);
			macroFinder = new BufferedReader(fileIn);

			while ((input = macroFinder.readLine()) != null){
				inputBuff = input.split("\t", 2);
				macroList.add(inputBuff);
			}

			macroFinder.close();
			fileIn.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public MacroHandler(){

		isOpen = true;

		shortcut = new Label("Shortcut   ");
		shortcutTF = new JTextField(SLim.screenWidth/40);
		replacement = new Label("Replacement");
		replacementTF = new JTextField(SLim.screenWidth/40);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		ok = new JButton("Okay");
		ok.addActionListener(this);

		// Launch macro GUI
		macroFrame = new JFrame("Shortcuts Manager");
		macroFrame.setSize(SLim.screenWidth/4, SLim.screenHeight*3/8);
		macroFrame.setLocation(SLim.screenWidth/3, SLim.screenHeight/5);
		macroFrame.setResizable(false);
		macroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		macroPanel = new JPanel(new BorderLayout());

		macroCheck = new Checkbox("Enable text shortcuts");
		if(SLim.shortcut){
			macroCheck.setState(true);
		}
		macroCheck.addItemListener(this);
		macroPanel.add(macroCheck,BorderLayout.NORTH);

		macroDisplay = new TextArea();
		if(!macroList.isEmpty()){
			for (String[] s : macroList){
				macroDisplay.append(s[0] + '\t' + s[1] + '\n');
			}
		}
		macroDisplay.setEditable(false);

		newMacro = new Button("New shortcut");
		newMacro.addActionListener(this);
		editMacro = new Button("Edit shortcut");
		editMacro.addActionListener(this);
		delMacro = new Button("Delete shortcut");
		delMacro.addActionListener(this);

		// null buttons provide proper size for the other buttons
		nullButton1 = new Button();
		nullButton2 = new Button();
		nullButton1.setVisible(false);
		nullButton2.setVisible(false);

		GridLayout buttonGrid = new GridLayout(5,1);
		buttonGrid.setVgap(SLim.screenHeight/25);
		mButtonPanel = new JPanel(buttonGrid);
		mButtonPanel.add(newMacro);
		mButtonPanel.add(editMacro);
		mButtonPanel.add(delMacro);
		mButtonPanel.add(nullButton1);
		mButtonPanel.add(nullButton2);
		mButtonPanel.setVisible(true);
		macroPanel.add(mButtonPanel, BorderLayout.EAST);
		macroPanel.add(macroDisplay, BorderLayout.WEST);  // Must come after the other additions to the panel or it covers them

		macroFrame.add(macroPanel);
		macroFrame.setVisible(true);
		macroFrame.addWindowListener(this);

	} // End constructor

	private void spawnGUI(){
		spawnedFrame = new JFrame("Spawned GUI");
		spawnedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		spawnedFrame.setLocation(SLim.screenWidth/4, SLim.screenHeight/3);
		spawnedFrame.setResizable(false);

		JPanel newPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridLayout(2,1));
		JPanel textfieldPanel = new JPanel(new GridLayout(2,1));
		JPanel aButtonPanel = new JPanel(new BorderLayout());

		GridLayout apLayout = new GridLayout(1,2);
		apLayout.setHgap(4);
		JPanel actionPanel = new JPanel(apLayout);

		labelPanel.add(shortcut);
		labelPanel.add(replacement);
		textfieldPanel.add(shortcutTF);
		textfieldPanel.add(replacementTF);

		actionPanel.add(cancel);
		actionPanel.add(ok);
		aButtonPanel.add(actionPanel, BorderLayout.EAST);

		newPanel.add(labelPanel, BorderLayout.WEST);
		newPanel.add(textfieldPanel, BorderLayout.EAST);
		newPanel.add(aButtonPanel, BorderLayout.SOUTH);

		spawnedFrame.add(newPanel);
		spawnedFrame.pack();
	}

	private void newMacroGUI(){
		spawnedFrame.setTitle("New shortcut");
		replacement.setVisible(true);
		replacementTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void editMacroGUI(){
		spawnedFrame.setTitle("Edit shortcut");
		replacement.setVisible(true);
		replacementTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void deleteMacroGUI(){
		spawnedFrame.setTitle("Delete shortcut");
		replacement.setVisible(false);
		replacementTF.setVisible(false);
		replacementTF.setText("tmp");
		spawnedFrame.setVisible(true);
	}

	private void rewriteFile(){
		try {
			macroWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(macroFile)));
			for(String[] s : macroList){
				macroWriter.write(s[0] + '\t' + s[1] + '\n');
			}
			macroWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String macroReplace(String message){
		if(SLim.shortcut){
			String response = message;
			for(String[] s : macroList){
				int sLength = s[0].length();
				if(response.startsWith(s[0]) && (response.length() == sLength || response.charAt(sLength) == ' ')){
					response = response.replaceFirst(s[0], s[1]);
				}
				response = response.replaceAll(" " + s[0] + " ", " " + s[1] + " ");
				if(response.endsWith(s[0]) && ((response.length() - response.lastIndexOf(" " + s[0])) - 1 == sLength)){
					response = response.substring(0, response.lastIndexOf(" " + s[0]) + 1) + s[1]; 
				}
			}
			return response;
		} else {
			return message;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e){
		Object src = e.getSource();
		if(src.equals(macroCheck)){
			if(SLim.shortcut){
				SLim.shortcut = false;
			} else {
				SLim.shortcut = true;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e){
		Object src = e.getSource();
		if(!guiSpawned){
			spawnGUI();
			guiSpawned = true;
		}
		if(src.equals(newMacro)){
			newMacroGUI();
		} else if(src.equals(editMacro)){
			editMacroGUI();
		} else if(src.equals(delMacro)){
			deleteMacroGUI();
		} else if(src.equals(cancel)){
			spawnedFrame.setVisible(false);
			shortcutTF.setText("");
			replacementTF.setText("");
		} else if(src.equals(ok)){
			String title = spawnedFrame.getTitle();
			if(shortcutTF.getText().trim().equals("") || replacementTF.getText().trim().equals("")){
				cancel.doClick();

			} else if(title.equals("New shortcut")){
				boolean stop = false;
				String findMe = shortcutTF.getText().trim();
				for(String[] s : macroList){
					if(s[0].equals(findMe)){
						cancel.doClick();
						stop = true;
						JOptionPane.showMessageDialog(null, "Message shortcut " + findMe + " already exists.");
						break;
					}
				}
				if(!stop){
					String[] newMacro = new String[2];
					newMacro[0] = shortcutTF.getText().trim();
					newMacro[1] = replacementTF.getText();	
					macroList.add(newMacro);
					rewriteFile();
					macroDisplay.append(newMacro[0] + '\t' + newMacro[1] + '\n');
					cancel.doClick();
				}
			} else if(title.equals("Edit shortcut")){
				boolean found = false;
				String findMe = shortcutTF.getText().trim();
				for(String[] s : macroList){
					if(s[0].equals(findMe)){

						s[1] = replacementTF.getText();
						macroDisplay.setCaretPosition(0);
						macroDisplay.setText("");
						for(String[] str : macroList){
							macroDisplay.append(str[0] + '\t' + str[1] + '\n');
						}
						rewriteFile();
						cancel.doClick();
						found = true;
						break;
					}
				}
				if(!found){
					JOptionPane.showMessageDialog(null, "Message shortcut " + findMe + " does not exist.");
					
					cancel.doClick();
				}


			} else if(title.equals("Delete shortcut")){
				boolean found = false;
				String findMe = shortcutTF.getText().trim();
				for(String[] s : macroList){
					if(s[0].equals(findMe)){
						macroList.remove(macroList.indexOf(s));
						macroDisplay.setCaretPosition(0);
						macroDisplay.setText("");
						for(String[] str : macroList){
							macroDisplay.append(str[0] + '\t' + str[1] + '\n');
						}
						found = true;
						rewriteFile();
						break;
					}
				} // end for each
				if(!found){
					JOptionPane.showMessageDialog(null, "Message shortcut " + findMe + " does not exist.");
					cancel.doClick();
				}
				cancel.doClick();
			}
		}
	} // End actionPerformed

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		isOpen = false;
		guiSpawned = false;
		//JOptionPane.showMessageDialog(null, macroReplace("y a b c yabc"));  //TODO Enable this line for testing replacement
	}

	@Override
	public void windowClosing(WindowEvent arg0) {

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}

	@Override
	public void windowIconified(WindowEvent arg0) {

	}

	@Override
	public void windowOpened(WindowEvent arg0) {

	}

}

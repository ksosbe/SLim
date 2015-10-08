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

public class Contacts implements ItemListener, ActionListener, WindowListener {

	private static BufferedReader finder;
	private static FileReader fileIn;
	private static BufferedWriter writer;
	private static ArrayList<String[]> contactList;
	private static String input;
	private static String[] inputBuff;
	private JFrame frame, spawnedFrame;
	private JPanel contactPanel, buttonPanel;
	private Label contact, edit;
	private JTextField contactTF, editTF;
	private JButton cancel, ok;
	private Button newContact, editContact, delContact, groupContact;
	private TextArea display;
	private Checkbox check;
	private static File contactFile;
	public static boolean isOpen = false, guiSpawned = false, gisOpen = false;
	private static File groupFile;
	private static ArrayList<String[]> groupList;

	public static void generateContacts(){
		// Load up existing macros, create shortcuts.txt to store macros if it doesn't exist
		contactFile = new File("contacts.txt");
		groupFile = new File("groups.txt");
		contactList = new ArrayList<String[]>();
		groupList = new ArrayList<String[]>();
		inputBuff = new String[2];

		try {
			contactFile.createNewFile();
			fileIn = new FileReader(contactFile);
			finder = new BufferedReader(fileIn);

			while ((input = finder.readLine()) != null){
				inputBuff = input.split("\t", 2);
				contactList.add(inputBuff);
			}

			finder.close();
			fileIn.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		try {
			groupFile.createNewFile();
			fileIn = new FileReader(groupFile);
			finder = new BufferedReader(fileIn);
			
			while ((input = finder.readLine()) != null) {
				inputBuff = input.split("\t", 2);
				groupList.add(inputBuff);
			}
			finder.close();
			fileIn.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public Contacts(){

		isOpen = true;

		contact = new Label("Name   ");
		contactTF = new JTextField(SLim.screenWidth/40);
		edit = new Label("Alias   ");
		editTF = new JTextField(SLim.screenWidth/40);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		ok = new JButton("Okay");
		ok.addActionListener(this);

		// Launch macro GUI
		frame = new JFrame("Contacts Manager");
		frame.setSize(SLim.screenWidth/4, SLim.screenHeight*3/8);
		frame.setLocation(SLim.screenWidth/3, SLim.screenHeight/5);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		contactPanel = new JPanel(new BorderLayout());

		display = new TextArea();
		if(!contactList.isEmpty()){
			for (String[] s : contactList){
				display.append(s[0] + '\t' + s[1] + '\n');
			}
		}
		display.setEditable(false);

		newContact = new Button("New contact");
		newContact.addActionListener(this);
		editContact = new Button("Edit contact");
		editContact.addActionListener(this);
		delContact = new Button("Delete contact");
		delContact.addActionListener(this);
		groupContact = new Button("Contact groups");
		groupContact.addActionListener(this);
		
		// null buttons provide proper size for the other buttons

		GridLayout buttonGrid = new GridLayout(5,1);
		buttonGrid.setVgap(SLim.screenHeight/25);
		buttonPanel = new JPanel(buttonGrid);
		buttonPanel.add(newContact);
		buttonPanel.add(editContact);
		buttonPanel.add(delContact);
		buttonPanel.add(groupContact);

		buttonPanel.setVisible(true);
		contactPanel.add(buttonPanel, BorderLayout.EAST);
		contactPanel.add(display, BorderLayout.WEST);  // Must come after the other additions to the panel or it covers them

		frame.add(contactPanel);
		frame.setVisible(true);
		frame.addWindowListener(this);

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

		labelPanel.add(contact);
		labelPanel.add(edit);
		textfieldPanel.add(contactTF);
		textfieldPanel.add(editTF);

		actionPanel.add(cancel);
		actionPanel.add(ok);
		aButtonPanel.add(actionPanel, BorderLayout.EAST);

		newPanel.add(labelPanel, BorderLayout.WEST);
		newPanel.add(textfieldPanel, BorderLayout.EAST);
		newPanel.add(aButtonPanel, BorderLayout.SOUTH);

		spawnedFrame.add(newPanel);
		spawnedFrame.pack();
	}
	/*private void groupContactGUI(){
		
		gisOpen = true;

		gcontact = new Label("Group Name   ");
		gcontactTF = new JTextField(SLim.screenWidth/40);
		gedit = new Label("Contact Name   ");
		geditTF = new JTextField(SLim.screenWidth/40);
		gcancel = new JButton("Cancel");
		gcancel.addActionListener(this);
		gok = new JButton("Okay");
		gok.addActionListener(this);

		// Launch macro GUI
		gframe = new JFrame("Contact Group Manager");
		gframe.setSize(SLim.screenWidth/4, SLim.screenHeight*3/8);
		gframe.setLocation(SLim.screenWidth/3, SLim.screenHeight/5);
		gframe.setResizable(false);
		gframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		ggroupPanel = new JPanel(new BorderLayout());

		gdisplay = new TextArea();
		if(!groupList.isEmpty()){
			for (String[] s : groupList){
				display.append(s[0] + '\t' + s[1] + '\n');
			}
		}
		gdisplay.setEditable(false);

		newGroup = new Button("New group");
		newGroup.addActionListener(this);
		editGroup = new Button("Edit group");
		editGroup.addActionListener(this);
		delGroup = new Button("Delete group");
		delGroup.addActionListener(this);

		GridLayout gbuttonGrid = new GridLayout(5,1);
		gbuttonGrid.setVgap(SLim.screenHeight/25);
		gbuttonPanel = new JPanel(gbuttonGrid);
		gbuttonPanel.add(newGroup);
		gbuttonPanel.add(editGroup);
		gbuttonPanel.add(delGroup);

		gbuttonPanel.setVisible(true);
		ggroupPanel.add(gbuttonPanel, BorderLayout.EAST);
		ggroupPanel.add(gdisplay, BorderLayout.WEST);

		gframe.add(ggroupPanel);
		gframe.setVisible(true);
		gframe.addWindowListener(this);
		
	}*/
	
	private void newContactGUI(){
		spawnedFrame.setTitle("New contact");
		edit.setVisible(true);
		editTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void editContactGUI(){
		spawnedFrame.setTitle("Edit contact");
		edit.setVisible(true);
		editTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void deleteContactGUI(){
		spawnedFrame.setTitle("Delete contact");
		edit.setVisible(false);
		editTF.setVisible(false);
		editTF.setText("tmp");
		spawnedFrame.setVisible(true);
	}
	/*
	private void newGroupGUI(){
		spawnedFrame.setTitle("New group");
		gedit.setVisible(true);
		geditTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}
	private void editGroupGUI(){
		spawnedFrame.setTitle("Edit group");
		gedit.setVisible(true);
		geditTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}
	private void delGroupGUI(){
		spawnedFrame.setTitle("Delete group");
		gedit.setVisible(true);
		geditTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}
	*/
	


	private void rewriteFile(){
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(contactFile)));
			for(String[] s : contactList){
				writer.write(s[0] + '\t' + s[1] + '\n');
			}
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e){
		Object src = e.getSource();
		if(src.equals(check)){
			if(SLim.contacts){
				SLim.contacts = false;
			} else {
				SLim.contacts = true;
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
		if(src.equals(newContact)){
			newContactGUI();
		} else if(src.equals(editContact)){
			editContactGUI();
		} else if(src.equals(delContact)){
			deleteContactGUI();
		}else if (src.equals(groupContact)) {
			new Groups();
		}
		
		/* else if(src.equals(groupContact)) {
			groupContactGUI();
		} else if(src.equals(newGroup)){
			newGroupGUI();
		} else if(src.equals(editGroup)){
			editGroupGUI();
		} else if(src.equals(delGroup)){
			delGroupGUI();
		}*/ else if(src.equals(cancel)){
			spawnedFrame.setVisible(false);
			contactTF.setText("");
			editTF.setText("");
		} else if(src.equals(ok)){
			String title = spawnedFrame.getTitle();
			if(contactTF.getText().trim().equals("") || editTF.getText().trim().equals("")){
				cancel.doClick();

			} else if(title.equals("New contact")){
				boolean stop = false;
				String findMe = contactTF.getText().trim();
				for(String[] s : contactList){
					if(s[0].equals(findMe)){
						cancel.doClick();
						stop = true;
						JOptionPane.showMessageDialog(null, "Contact " + findMe + "already exists.");
						break;
					}
				}
				if(!stop){
					String[] newContact = new String[2];
					newContact[0] = contactTF.getText().trim();
					newContact[1] = editTF.getText();	
					contactList.add(newContact);
					rewriteFile();
					display.append(newContact[0] + '\t' + newContact[1] + '\n');
					cancel.doClick();
				}
			} else if(title.equals("Edit contact")){

				String findMe = contactTF.getText().trim();
				for(String[] s : contactList){
					if(s[0].equals(findMe)){
						s[1] = editTF.getText();
						break;
					}
				}
				display.setText("");
				for(String[] str : contactList){
					display.append(str[0] + '\t' + str[1] + '\n');
				}
				rewriteFile();
				cancel.doClick();
				
			} else if (title.equals("Delete contact")) {
				String findMe = contactTF.getText().trim();

				for(String[] s : contactList){
					if(s[0].equals(findMe)){
						contactList.remove(contactList.indexOf(s));
						display.setText("");
						for(String[] str : contactList){
							display.append(str[0] + '\t' + str[1] + '\n');
						}
						rewriteFile();
						break;
					}
				} // end for each
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
		//JOptionPane.showMessageDialog(null, macroReplace("y a b c yabc"));  //TODO Enable this line for testing edit
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

package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Groups implements ItemListener, ActionListener, WindowListener {

	private JFrame groupFrame;
	private JPanel groupPanel, buttonPanel;
	private TextArea groupDisplay;
	private Button create, edit, remove, nullButton1, nullButton2;
	private Label groupName;
	private JTextField groupNameTF;
	private JButton okay, cancel;
	private JFrame spawnedFrame;
	
	public Groups() {
		
		groupName = new Label("Name:   ");
		groupNameTF = new JTextField(SLim.screenWidth/40);
		okay = new JButton("Okay");
		cancel = new JButton("Cancel");
		okay.addActionListener(this);
		cancel.addActionListener(this);
		
		
		groupFrame = new JFrame("Contact Groups");
		groupFrame.setSize(SLim.screenWidth/4, SLim.screenHeight*3/8);
		groupFrame.setLocation(SLim.screenWidth/3, SLim.screenHeight/5);
		groupFrame.setResizable(false);
		groupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		groupPanel = new JPanel(new BorderLayout());

		groupDisplay = new TextArea();

		groupDisplay.setEditable(false);

		create = new Button("Create");
		create.addActionListener(this);
		edit = new Button("Edit");
		edit.addActionListener(this);
		remove = new Button("Remove");
		remove.addActionListener(this);
		
		nullButton1 = new Button();
		nullButton2 = new Button();
		nullButton1.setVisible(false);
		nullButton2.setVisible(false);

		GridLayout buttonGrid = new GridLayout(5,1);
		buttonGrid.setVgap(SLim.screenHeight/25);
		buttonPanel = new JPanel(buttonGrid);
		buttonPanel.add(create);
		buttonPanel.add(edit);
		buttonPanel.add(remove);
		buttonPanel.add(nullButton1);
		buttonPanel.add(nullButton2);
		buttonPanel.setVisible(true);
		groupPanel.add(buttonPanel, BorderLayout.EAST);
		groupPanel.add(groupDisplay, BorderLayout.WEST);

		groupFrame.add(groupPanel);
		groupFrame.setVisible(true);
		groupFrame.addWindowListener(this);
		
		
		
		
	}

	private void spawnGUI(){
		spawnedFrame = new JFrame("GUI");
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

		labelPanel.add(groupName);
		textfieldPanel.add(groupNameTF);

		actionPanel.add(cancel);
		actionPanel.add(okay);
		aButtonPanel.add(actionPanel, BorderLayout.EAST);

		newPanel.add(labelPanel, BorderLayout.WEST);
		newPanel.add(textfieldPanel, BorderLayout.EAST);
		newPanel.add(aButtonPanel, BorderLayout.SOUTH);

		spawnedFrame.add(newPanel);
		spawnedFrame.pack();
	}

	private void createGroup(){
		spawnedFrame.setTitle("Create Contact Group");
		groupName.setVisible(true);
		groupNameTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void editGroup(){
		spawnedFrame.setTitle("Edit Contact Group");
		groupName.setVisible(true);
		groupNameTF.setVisible(true);
		spawnedFrame.setVisible(true);
	}

	private void removeGroup(){
		spawnedFrame.setTitle("Remove Contact Group");
		spawnedFrame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		Object src = e.getSource();
		spawnGUI();
		if (src.equals(create)) {
			createGroup();
		}
		
		if (src.equals(edit)) {
			editGroup();
		}
		
		if (src.equals(remove)) {
			removeGroup();
		}
		
		
		
		
		
		
		
		
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
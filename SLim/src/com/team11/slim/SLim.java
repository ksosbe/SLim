package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SLim implements ActionListener
{
	static short screenHeight;
	static short screenWidth;
	// TODO store these four booleans as settings so they are remembered as being on or off
	public static boolean translate = false;
	public static boolean shortcut = false;
	public static boolean filter = false;
	public static boolean log = false;
	public static boolean contacts = false;
	public Translate myLangSet;
	private JFrame clientFrame;
	private JButton sendButton;
	private JTextField textField;
	public JTextArea textArea;
	public JList<String> userList;
	private boolean serverStarted = false;
	private boolean connectedToServer = false;
	public Server localServer = null;
	private Client localClient = null;
	private Vector<Peer> peersOnServer;
	public DefaultListModel<String> listMod;
	public static ProfanityFilter cleanupText;
	
	//JMenu Stuff
	JMenuBar menuBar;
	
	//Top-level menus
	JMenu slim;
	JMenu messaging;
	JMenu help;
	
	//Contents of "SLim" Menu
	JMenuItem startServer;
	JMenuItem joinServer;
	JMenu logging;
	JMenuItem exit;
	
	//Contents of "Messaging" Menu
	JMenuItem contactsMenu;
	JMenuItem emoticons;
	JMenuItem shortcuts;
	JMenuItem fileTransfer;
	JMenuItem filterText;
	JMenuItem translateText;
	
	//Contents of "Help" Menu
	JMenuItem about;
	
	//Contents of "Logging" Menu
	JRadioButtonMenuItem noLogging;
	JRadioButtonMenuItem logChats;
	ButtonGroup loggingGroup;

	private void generateClientGUI() {
		clientFrame = new JFrame("SLim");
		clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // TODO check to see if this can be overridden, to make it migrate host if client is the current host ** WindowListener?
		clientFrame.setSize(800, 300);

		/*** JMenu Instantiation ***/
		menuBar = new JMenuBar();
		
		//Top-level Menus
		slim = new JMenu("SLim");
		menuBar.add(slim);
		messaging = new JMenu("Messaging");
		menuBar.add(messaging);
		help = new JMenu("Help");
		menuBar.add(help);
		
		//The "SLim" menu
		startServer = new JMenuItem("Start Server...");
		slim.add(startServer);
		joinServer = new JMenuItem("Join Server...");
		slim.add(joinServer);
		logging = new JMenu("Logging");
		slim.add(logging);
		exit = new JMenuItem("Exit");
		slim.add(exit);
		
		//The "Logging" menu(a submenu to "SLim")
		noLogging = new JRadioButtonMenuItem("Disabled(Default)");
		logging.add(noLogging);
		logChats = new JRadioButtonMenuItem("Enabled");		
		logging.add(logChats);
		loggingGroup = new ButtonGroup();
		noLogging.setSelected(true);
		loggingGroup.add(noLogging);
		loggingGroup.add(logChats);
		
		//The "Messaging" menu
		contactsMenu = new JMenuItem("Contacts...");
		messaging.add(contactsMenu);
		emoticons = new JMenuItem("Emoticons...");
		messaging.add(emoticons);
		shortcuts = new JMenuItem("Shortcuts...");
		messaging.add(shortcuts);
		filterText = new JMenuItem("Filter Text...");
		messaging.add(filterText);
		translateText = new JMenuItem("Translate...");
		messaging.add(translateText);
		fileTransfer = new JMenuItem("File Transfer...");
		messaging.add(fileTransfer);
		
		//The "Help" menu
		about = new JMenuItem("About");
		help.add(about);
		
		//Action Listener Stuff
		startServer.addActionListener(this);
		joinServer.addActionListener(this);
		exit.addActionListener(this);
		contactsMenu.addActionListener(this);
		emoticons.addActionListener(this);
		shortcuts.addActionListener(this);
		fileTransfer.addActionListener(this);
		filterText.addActionListener(this);
		translateText.addActionListener(this);
		about.addActionListener(this);
		noLogging.addActionListener(this);
		logChats.addActionListener(this);
		
		
		// Message Area
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// Send Message Panel
		sendButton = new JButton();
		sendButton = new JButton("Send");
		textField = new JTextField();
		sendButton.addActionListener(this);
		textField.addActionListener(this);
		JPanel sendPanel = new JPanel();
		sendPanel.setLayout(new BorderLayout());
		sendPanel.add(textField, BorderLayout.CENTER);
		sendPanel.add(sendButton, BorderLayout.EAST);
		
		// Messaging Area
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(scrollPane, BorderLayout.CENTER);
		messagePanel.add(sendPanel, BorderLayout.SOUTH);
		
		// User List
		userList = new JList<String>();
		listMod = new DefaultListModel<String>();
		userList.setModel(listMod);
		listMod.addElement("Peers on server:    ");
		
		// Center Pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, userList, messagePanel);
		
		// Main panel
		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(new BorderLayout());
//		clientPanel.add(buttonPanel, BorderLayout.NORTH);		
		clientPanel.add(splitPane, BorderLayout.CENTER);
		clientFrame.setJMenuBar(menuBar);
		clientFrame.getContentPane().add(clientPanel);
		clientFrame.setVisible(true);
		
	} // End generateClientGUI

	public static void main(String[] args) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		screenHeight = (short) screen.getHeight();
		screenWidth = (short) screen.getWidth();

		SLim mainClient = new SLim();
		mainClient.generateClientGUI();
		MacroHandler.generateMacros();
		mainClient.myLangSet = new Translate();
		Contacts.generateContacts();
		cleanupText = new ProfanityFilter();
	} // End main

	public void updatePeers( Vector<Peer> peers )
	{
		this.peersOnServer = peers;
		listMod.clear();
		listMod.addElement("Peers on server:    ");
		for( Peer peer : this.peersOnServer )
			listMod.addElement( peer.name );
	}
	
	/**
	 * This function takes a String as an argument
	 * and writes that String, along with a timestamp,
	 * to the SLim logfile, "slim_log.txt".
	 * @param message The String to log.
	 */
	public static void logMessage(String message) {
		//Check for logging enabled here to streamline things.
		if(log) {
			try {
				//Create File I/O stuff
				File logFile = new File("slim_log.txt");
				BufferedWriter output = new BufferedWriter(new FileWriter(logFile));
				
				//Create timestamp stuff
				Calendar cal = Calendar.getInstance();
				Date now = cal.getTime();
				Timestamp timestamp = new Timestamp(now.getTime());
				
				//Write the timestamp and log message to the log file
				output.write(timestamp + ": " + message + "\n");
				output.close();
				
			}catch(IOException e) {
				System.out.println("Error: Could not write to log file.");
			}
		}else {
			//Logging is diabled--nothing to do.
			return;
		}
	}
	
	public void migrateHost()
	{
		// If we are the first peer TODO: get better check than name
		if( peersOnServer.get(0).name.equals( localClient.myName ) && serverStarted == false )
		{
			try
			{
				// We are becoming the host so start a server
				localServer = new Server( localClient.client.getPort() );
				serverStarted = true;
			}
			catch (IOException ioe)
			{
				JOptionPane.showMessageDialog(null,
						"Could not open port: " + localClient.client.getPort(),
						"Server Error", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
		try
		{
			// Connect to the new host
			localClient = new Client(peersOnServer.get(0).ipAddress,
					localClient.client.getPort(),
					localClient.myName,
					this);
			logMessage("Successfully migrated hosts to " + localClient.myName + " at "
					+ peersOnServer.get(0).ipAddress + ":" + localClient.client.getPort());
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
					"Failed to migrate hosts",
					"Error",
					JOptionPane.INFORMATION_MESSAGE);
			logMessage("Could not migrate host to " + localClient.myName + " at "
					+ peersOnServer.get(0).ipAddress + ":" + localClient.client.getPort());
			connectedToServer = false;
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src.equals(shortcuts)) {
			if (!MacroHandler.isOpen) {
				new MacroHandler();
			}
		}
		if (src.equals(startServer)) {
			if (serverStarted == true) {
				JOptionPane.showMessageDialog(null,
						"Server already started",
						"Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				StartServerDialog dialog = new StartServerDialog();
				dialog.setModal(true);
				dialog.setVisible(true);
				if (dialog.portEntered != 0) {
					try {
						localServer = new Server( dialog.portEntered );
						serverStarted = true;
						logMessage("Started server on port " + dialog.portEntered);
					}
					catch (IOException ioe) {
						JOptionPane.showMessageDialog(null,
								"Could not open port: " + dialog.portEntered,
								"Server Error",
								JOptionPane.INFORMATION_MESSAGE);
						logMessage("Could not start server on port " + dialog.portEntered);
					}
				}
			}
		}
		if (src.equals(joinServer)) {
			if (connectedToServer == true) {
				JOptionPane.showMessageDialog(null,
						"Already connected to server",
						"Error",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				ConnectToServerDialog dialog = new ConnectToServerDialog();
				dialog.setModal(true);
				dialog.setVisible(true);
				if (dialog.ipAddress != null && dialog.serverPort != 0 && dialog.name != null) {
					try {
						localClient = new Client(dialog.ipAddress, dialog.serverPort, dialog.name, this);
						connectedToServer = true;
						//Log this
						logMessage("Connected to server: " + dialog.ipAddress + " at " + dialog.serverPort);
					} catch (IOException e1) {
						if (localClient != null)
							localClient.disconect();
						logMessage("Could not connect to server: " + dialog.ipAddress + " at " + dialog.serverPort);
						JOptionPane.showMessageDialog(null,
								"Could not connect to server",
								"Error",
								JOptionPane.INFORMATION_MESSAGE);
						
					}
				}
			}
		}
		if (src.equals(sendButton) || src.equals(textField)) {
			if (connectedToServer == true) {
				String outgoingMessage = textField.getText();
				logMessage(localClient.myName + ": " + outgoingMessage); //Log messages sent
				outgoingMessage = Emoticons.emoticonReplace(cleanupText.cleanText(outgoingMessage));
				if(shortcut){
					localClient.send(new Message(null, MessageType.Text, MacroHandler.macroReplace(outgoingMessage)));
				} else {
					localClient.send(new Message(null, MessageType.Text, outgoingMessage));
				}
			}
			textField.setText("");
		}
		if(src.equals(contactsMenu)) {
			new Contacts();
		}
		if (src.equals(emoticons)) {
			new Emoticons();
		}
		if(src.equals(fileTransfer)) {
			new FileTransfer();
		}
		if(src.equals(translateText)) {
			if(!Translate.isOpen){
				myLangSet.generateGUI();
			}
		}
		if(src.equals(noLogging)) {
			log = false;
		}
		if(src.equals(logChats)) {
			log = true;
		}
		if(src.equals(about)) {
			JOptionPane.showMessageDialog(clientFrame, "SLim is a lightweight instant messenger designed for portability. It offers"
					+ "automatic translation, text macros, server migration, and file transfer. It is written by:\n"
					+ "Andrew Davis\n"
					+ "Kris Sosbe\n"
					+ "Alex Ryker\n"
					+ "Mark Yang\n"
					+ "Nick Houser\n"
					+ "This is a project for the Purdue course CS 408, in Fall of 2014. The above-mentioned authors form Group 11.");
		}
		if(src.equals(exit)) {
			System.exit(0);
		}
	} // End actionPerformed
}

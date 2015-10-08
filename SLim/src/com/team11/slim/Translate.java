package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.javanetworkframework.rb.util.AbstractWebTranslator;


public class Translate implements WindowListener,ActionListener, ItemListener{
	
	public static boolean isOpen = false;
	private Checkbox langBox;
	private static JComboBox<String> myLangList;
	private static JComboBox<String> theirLangList;
	private Label myLang, theirLang;
	private JFrame langFrame;
	private JButton cancel, ok;
	private String toLang, fromLang;
	private static String[] langList = {"Dutch", "English", "French", "German", "Italian", "Norwegian", "Portuguese", "Spanish"};
	private static String[] langAbbrev = {"nl", "en", "fr", "de", "it", "no", "pt", "es"};
	
	public void generateLanguages(){
		myLangList = new JComboBox<String>(langList);
		myLangList.setSelectedItem("English");
		theirLangList = new JComboBox<String>(langList);
		theirLangList.setSelectedItem("English");
		toLang = langAbbrev[myLangList.getSelectedIndex()];
		fromLang = langAbbrev[theirLangList.getSelectedIndex()];
	}
	
	public void generateGUI(){
		
		isOpen = true;
		
		langFrame = new JFrame("Translate GUI");
		langFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		langFrame.setLocation(SLim.screenWidth/4, SLim.screenHeight/3);
		langFrame.setResizable(false);
		langFrame.addWindowListener(this);
		
		myLang = new Label("Choose your language");
		theirLang = new Label("Choose their language");
		
		myLangList.addActionListener(this);
		theirLangList.addActionListener(this);
		
		toLang = "English";
		fromLang = "English";
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		ok = new JButton("Okay");
		ok.addActionListener(this);

		JPanel newPanel = new JPanel(new BorderLayout());
		JPanel labelPanel = new JPanel(new GridLayout(2,1));
		JPanel langlistPanel = new JPanel(new GridLayout(2,1));
		JPanel aButtonPanel = new JPanel(new BorderLayout());

		GridLayout apLayout = new GridLayout(1,2);
		apLayout.setHgap(4);
		JPanel actionPanel = new JPanel(apLayout);

		labelPanel.add(myLang);
		labelPanel.add(theirLang);
		langlistPanel.add(myLangList);
		langlistPanel.add(theirLangList);

		actionPanel.add(cancel);
		actionPanel.add(ok);
		aButtonPanel.add(actionPanel, BorderLayout.EAST);
		
		langBox = new Checkbox("Enable incoming message translation");
		if(SLim.translate){
			langBox.setState(true);
		}
		langBox.addItemListener(this);

		newPanel.add(langBox, BorderLayout.NORTH);
		newPanel.add(labelPanel, BorderLayout.WEST);
		newPanel.add(langlistPanel, BorderLayout.EAST);
		newPanel.add(aButtonPanel, BorderLayout.SOUTH);

		langFrame.setVisible(true);
		langFrame.add(newPanel);
		langFrame.pack();
	}
	
	public String translateMessage(String message){
		Locale fromLoc = new Locale(fromLang);
		Locale toLoc = new Locale(toLang);
		AbstractWebTranslator translator = (AbstractWebTranslator)ResourceBundle.getBundle("com.javanetworkframework.rb.webtranslator.WebTranslator", toLoc);
		String response = translator.getString(message, fromLoc);
		if(response == null){
			return message;
		} else {
			return response;
		}
	}
	
	public Translate(){
		generateLanguages();
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		isOpen = false;
		//JOptionPane.showMessageDialog(null, translateMessage("ciao"));  //TODO Enable this line for testing translation
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


	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src.equals(ok)){
			toLang = langAbbrev[myLangList.getSelectedIndex()];
			fromLang = langAbbrev[theirLangList.getSelectedIndex()];
			cancel.doClick();
		} else if(src.equals(cancel)){
			isOpen = false;
			langFrame.dispose();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Object src = e.getSource();
		if(src.equals(langBox)){
			if(SLim.translate){
				SLim.translate = false;
			} else {
				SLim.translate = true;
			}
		}
	}

}

package com.team11.slim;

import java.io.*;
import javax.swing.*;
 
public class FileTransfer extends JPanel {

	private static final long serialVersionUID = 1L;
	JButton openButton, saveButton;
    JTextArea log;
    JFileChooser fc;
 
    public FileTransfer() {
    	
    	fc = new JFileChooser();
    	int returnVal = fc.showOpenDialog(FileTransfer.this);
    	 
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
        }
        
    }
    
}
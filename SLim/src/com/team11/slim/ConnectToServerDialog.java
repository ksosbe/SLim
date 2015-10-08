package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConnectToServerDialog extends JDialog
{
	private static final long serialVersionUID = -8549441432207718292L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtLocalhost;
	private JTextField textField_1;
	private JTextField txtAnonymous;
	
	public String ipAddress = null;
	public int serverPort = 0;
	public String name = null;
	
	/**
	 * Create the dialog.
	 */
	public ConnectToServerDialog() {
		setResizable(false);
		setBounds(100, 100, 300, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{49, 193, 0};
		gbl_contentPanel.rowHeights = new int[]{20, 20, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblServerIp = new JLabel("Server address:");
		GridBagConstraints gbc_lblServerIp = new GridBagConstraints();
		gbc_lblServerIp.anchor = GridBagConstraints.EAST;
		gbc_lblServerIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblServerIp.gridx = 0;
		gbc_lblServerIp.gridy = 0;
		contentPanel.add(lblServerIp, gbc_lblServerIp);
		
		txtLocalhost = new JTextField();
		txtLocalhost.setText("localhost");
		GridBagConstraints gbc_txtLocalhost = new GridBagConstraints();
		gbc_txtLocalhost.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtLocalhost.anchor = GridBagConstraints.NORTH;
		gbc_txtLocalhost.insets = new Insets(0, 0, 5, 0);
		gbc_txtLocalhost.gridx = 1;
		gbc_txtLocalhost.gridy = 0;
		contentPanel.add(txtLocalhost, gbc_txtLocalhost);
		txtLocalhost.setColumns(10);
		{
			JLabel lblPort = new JLabel("Port:");
			GridBagConstraints gbc_lblPort = new GridBagConstraints();
			gbc_lblPort.anchor = GridBagConstraints.EAST;
			gbc_lblPort.insets = new Insets(0, 0, 5, 5);
			gbc_lblPort.gridx = 0;
			gbc_lblPort.gridy = 1;
			contentPanel.add(lblPort, gbc_lblPort);
		}
		{
			textField_1 = new JTextField();
			textField_1.setText("14554");
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 1;
			contentPanel.add(textField_1, gbc_textField_1);
			textField_1.setColumns(10);
		}
		{
			JLabel lblConnectAs = new JLabel("Connect as:");
			GridBagConstraints gbc_lblConnectAs = new GridBagConstraints();
			gbc_lblConnectAs.anchor = GridBagConstraints.EAST;
			gbc_lblConnectAs.insets = new Insets(0, 0, 0, 5);
			gbc_lblConnectAs.gridx = 0;
			gbc_lblConnectAs.gridy = 2;
			contentPanel.add(lblConnectAs, gbc_lblConnectAs);
		}
		{
			txtAnonymous = new JTextField();
			txtAnonymous.setText("anonymous");
			GridBagConstraints gbc_txtAnonymous = new GridBagConstraints();
			gbc_txtAnonymous.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtAnonymous.gridx = 1;
			gbc_txtAnonymous.gridy = 2;
			contentPanel.add(txtAnonymous, gbc_txtAnonymous);
			txtAnonymous.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ipAddress = txtLocalhost.getText();
						name = txtAnonymous.getText();
						try {
							serverPort = Integer.parseInt(textField_1.getText());
							setVisible(false);
						}
						catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(null, "Please enter a valid port", "Connect Error", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}

package com.team11.slim;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Emoticons implements ItemListener, ActionListener, WindowListener {
	
	private JFrame emotFrame;
	private JPanel emotPanel;
	private TextArea Display;
	public Emoticons() {


		
		
		emotFrame = new JFrame("List of emoticon shortcuts");
		emotFrame.setSize(SLim.screenWidth/4, SLim.screenHeight*3/8);
		emotFrame.setLocation(SLim.screenWidth/3, SLim.screenHeight/5);
		emotFrame.setResizable(false);
		emotFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		emotPanel = new JPanel(new BorderLayout());
	
		Display = new TextArea();
		
		/*Display.append(":)\t\t=\t\t☺\n");
		Display.append(":(\t\t=\t\t☹\n");
		Display.append("<3\t\t=\t\t♥\n");
		Display.append(";)\t\t=\t\t◕‿↼\n");
		Display.append(":3\t\t=\t\t(•ω•)\n");
		Display.append("\\(o_o)/\t\t=\t\t┻━┻ ︵ヽ(`Д´)ﾉ︵ ┻━┻\n");
		Display.append("_|_\t\t=\t\t╭∩╮(-_-)╭∩╮\n");
		Display.append("*donger\t\t=\t\tヽ༼ຈل͜ຈ༽ﾉ\n");
		Display.append("*shrug\t\t=\t\t¯\\_(ツ)_/¯\n");
		Display.append("*kiss\t\t=\t\t(づ￣ ³￣)づ\n");
		Display.append("*tired\t\t=\t\t(¬_¬)\n");
		Display.append("*emo\t\t=\t\t(･.◤)\n");
		Display.append("*cry\t\t=\t\t(ಥ﹏ಥ)\n");
		Display.append("*gym\t\t=\t\tᕙ(⇀‸↼‶)ᕗ\n");
		Display.append("*wtf\t\t=\t\tლ(ಠ益ಠლ)\n");
		Display.append("*bear\t\t=\t\tʕ•ᴥ•ʔ\n");
		Display.append("*dog\t\t=\t\t(ᵔᴥᵔ)\n");
		Display.append("*flipperson\t\t=\t\t（╯°□°）╯︵( .o.)\n");
		Display.append("*gift\t\t=\t\t(´・ω・)っ由\n");
		Display.append("*sunglasses\t\t=\t\t(⌐■_■)\n");
		Display.append("*handsup\t\t=\t\t٩◔.◔۶\n");
		Display.append("*peace\t\t=\t\t✌\n");
		Display.append("*box\t\t=\t\t⚘\n");
		Display.append("*snowman\t\t=\t\t☃\n");
		Display.append("*sun\t\t=\t\t☀\n");
		Display.append("*snowflake\t\t=\t\t❄\n");
		Display.append("*star\t\t=\t\t★\n");
		Display.append("*umbrella\t\t=\t\t☂\n");
		Display.append("*nyan\t\t=\t\t\n~~~ ╔͎═͓═͙╗\n~~~ ╚̨̈́═̈́﴾ ̥̂˖̫˖̥  ̂ )\n");
		*/
		Display.setEditable(false);
	
		GridLayout buttonGrid = new GridLayout(5,1);
		buttonGrid.setVgap(SLim.screenHeight/25);

		emotPanel.add(Display, BorderLayout.WEST);
	
		emotFrame.add(emotPanel);
		emotFrame.setVisible(true);
		emotFrame.addWindowListener(this);
		
	}
	
	public static String emoticonReplace(String message){
		
		String toReturn = message;
		/*
		toReturn = toReturn.replaceAll("<3", "♥");
		toReturn = toReturn.replaceAll(";\\)", "◕‿↼");
		toReturn = toReturn.replaceAll(":3", "(•ω•)");
		toReturn = toReturn.replaceAll("\\(o_o\\)", "┻━┻ ︵ヽ(`Д´)ﾉ︵ ┻━┻");
		toReturn = toReturn.replaceAll("_|_", "╭∩╮(-_-)╭∩╮");
		toReturn = toReturn.replaceAll("/*shrug", "¯\\_(ツ)_/¯");
		toReturn = toReturn.replaceAll("/*kiss", "(づ￣ ³￣)づ");
		toReturn = toReturn.replaceAll("/*tired", "(¬_¬)");
		toReturn = toReturn.replaceAll("/*emo", "(･.◤)");
		toReturn = toReturn.replaceAll("/*cry", "(ಥ﹏ಥ)");
		toReturn = toReturn.replaceAll("/*gym", "ᕙ(⇀‸↼‶)ᕗ");
		toReturn = toReturn.replaceAll("/*wtf", "ლ(ಠ益ಠლ)");
		toReturn = toReturn.replaceAll("/*bear", "ʕ•ᴥ•ʔ");
		toReturn = toReturn.replaceAll("/*flipperson", "（╯°□°）╯︵( .o.)");
		toReturn = toReturn.replaceAll("/*gift", "(´・ω・)っ由");
		toReturn = toReturn.replaceAll("/*nyan", "\n~~~ ╔͎═͓═͙╗\n~~~ ╚̨̈́═̈́﴾ ̥̂˖̫˖̥  ̂ )");
		toReturn = toReturn.replaceAll("/*sunglasses", "(⌐■_■)");
		toReturn = toReturn.replaceAll("/*handsup", "٩◔.◔۶");
		*/
		
		return toReturn;

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
		
}

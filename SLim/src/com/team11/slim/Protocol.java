package com.team11.slim;

/**
 * This class provides a protocol for communication within the SLim
 * program. It should be expanded as the program evolves. The primary
 * purpose of this class is to provide a collection of constants that
 * can be referred to by the Client and Server classes when they are
 * sending and receiving messages. Any network communication classes
 * should use the constants provided by this class when using the SLim
 * protocol to minimize the chance of typos or inconsistencies.
 *
 */

public class Protocol 
{
	static public Boolean serverProccessMessage( Message message, Server server )
	{
		// We basically just send out anything we get to the users
		// This would be a good spot to do message parsing for server
		// side translate, etc..
		server.sendToAllClients( message );
		return true;
	}
	
	static public Boolean clientProccessMessage( Message message, SLim slim )
	{
		// If it is a text type message we display it on the screen
		if( message.type == MessageType.Text )
		{
			
				if(message.translateThis){
					slim.textArea.append( message.peer.name + ": " + slim.myLangSet.translateMessage(message.messageText) + "\n");
					SLim.logMessage(message.peer.name + ": " + slim.myLangSet.translateMessage(message.messageText));
				} else {
					slim.textArea.append( message.peer.name + ": " + message.messageText + "\n" );
					SLim.logMessage(message.peer.name + ": " + message.messageText);
				}
		}
		// If it is a new user message
		else if( message.type == MessageType.UpdateList )
		{
			// Update user list
			slim.updatePeers( message.userList );
		}
		return true;
	}
}

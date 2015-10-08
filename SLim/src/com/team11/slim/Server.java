package com.team11.slim;

import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class Server implements Runnable
{
	private ServerSocket server = null;
	private Vector<ServerThread> connectedClients = new Vector<ServerThread>();
	
	Server( int portNumber ) throws IOException
	{
		// Start a listener socket
		server = new ServerSocket( portNumber );
		(new Thread( this )).start();
	}
	
	public void sendToAllClients( Message messageFromClient )
	{
		// Don't send new user message to the new user
		for( ServerThread client : connectedClients )
			if(messageFromClient.type == MessageType.Text && !client.peerOnThread.name.equals(messageFromClient.peer.name)){
				messageFromClient.translateThis = true;
				client.sendMessage( messageFromClient );
				messageFromClient.translateThis = false;
			} else {
				client.sendMessage( messageFromClient );
			}
	}
	
	// Generate and send an updated user list to all users
	public void sendUpdatedClientList()
	{
		Vector<Peer> userList = new Vector<Peer>();
		for( ServerThread client : connectedClients )
			userList.add( client.peerOnThread );
		// For each peer on the server
		for( ServerThread client : connectedClients )
		{
			// Create UpdateList message
			Message message = new Message(null, MessageType.UpdateList, null);
			message.userList = userList;
			// Send the peer to the user
			client.sendMessage( message );
		}
	}
	
	public void removeThread( ServerThread thread )
	{
		// Remove the thread from the user list
		connectedClients.remove( thread );
		// Send this change to the remaining users
		sendUpdatedClientList();
	}
	
	@Override
	public void run()
	{
		while( true )
		{
				try
				{
					// Wait for a new connection
					ServerThread thread = new ServerThread( server.accept(), this );
					// Add this connection to the list
					connectedClients.add(thread);
				}
				catch (IOException e)
				{
					// Server error
				}
		}
	}
}

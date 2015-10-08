package com.team11.slim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class ServerThread extends Thread
{
	private Socket socket;
	private Server parent;
	public Peer peerOnThread;
	private BufferedReader input;
	private BufferedWriter output;
    
	public ServerThread( Socket socket, Server parent )
	{
		this.socket = socket;
		this.parent = parent;
		start();
	}
	
	public void sendMessage( Message messageFromClient )
	{
		try
		{
			output.write( (new Gson()).toJson( messageFromClient ) );
			output.write( '\n' );
			output.flush(); // Send now
		}
		catch (IOException e)
		{
			// failed to send message to client
		}
	}
	
	public void run()
	{
		try
		{
			// Create an output object stream
			output = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
			// Send a welcome message from the server
			Message welcomeMessage = new Message(new Peer("Server", ""), MessageType.Text, "Welcome to the server");
			sendMessage( welcomeMessage );
			// Create an input object stream
			input = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			// Read in user name and add as a peer on the server
			peerOnThread = new Peer((String)input.readLine(), socket.getInetAddress().getHostAddress());
			// Send out the new user to other connected users
			Protocol.serverProccessMessage( new Message(peerOnThread, MessageType.NewUser, null ), parent );
			// Send user all other connected users
			parent.sendUpdatedClientList();
			while( true )
			{
				// Wait for input from client
				Message messageFromClient = (new Gson()).fromJson(input.readLine(), Message.class);
				// Enforce correct sender information
				messageFromClient.peer = peerOnThread;
				// Message contains text for others
				if( messageFromClient.type == MessageType.Text )
					Protocol.serverProccessMessage( messageFromClient, parent );
			}
		}
		catch( IOException e )
		{
			parent.removeThread( this );
		}
	}
}

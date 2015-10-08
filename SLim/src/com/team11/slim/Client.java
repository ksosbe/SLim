package com.team11.slim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

public class Client implements Runnable
{
	public String myName = null;
	private SLim parent = null;
	public Socket client = null;
	private BufferedReader input;
	private BufferedWriter output;
	
	Client( String serverAddress, int serverPort, String myName, SLim parent ) throws IOException
	{
		this.parent = parent;
		this.myName = myName;
		client = new Socket( serverAddress, serverPort );
		(new Thread( this )).start();
	}
	
	public void disconect()
	{
		try
		{
			client.close();
		}
		catch (IOException e)
		{
			// cannot close socket!?
		}
	}
	
	public void send( Message message )
	{
		try
		{
			output.write( (new Gson()).toJson( message ) );
			output.write( '\n' );
			output.flush();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run()
	{
		try
		{
			output = new BufferedWriter( new OutputStreamWriter( client.getOutputStream() ) );
			output.write( myName );
			output.write( '\n' );
			output.flush();
			input = new BufferedReader( new InputStreamReader(client.getInputStream() ) );
			while( true )
			{
				Message inputFromServer = (new Gson()).fromJson(input.readLine(), Message.class);
				Protocol.clientProccessMessage( inputFromServer, parent );
			}
		}
		catch( IOException e )
		{
			// Find new host
			parent.migrateHost();
		}
		/*finally
		{
			try
			{
				if( client != null )
					client.close();
				if( input != null )
					input.close();
				if( output != null )
					output.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
}

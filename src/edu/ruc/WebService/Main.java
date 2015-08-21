package edu.ruc.WebService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {
	public void run() { 
	   Socket client;
	   boolean b;
	   try { 
		   while (true) {
			   client=ResponseSocket();     
			   while(true) {
				   b=ReceiveMsg(client);  
				//   SendMsg(client,b);
				   if(true)break;
			   }
	     CloseSocket(client);
		   }   
	   } 
	   catch (IOException e) {
		   System.out.println(e);
	   }
	}
	private ServerSocket server = null;
	private static final int PORT = 5000;
	private DataOutputStream writer;
	private DataInputStream reader;
	private void CreateSocket() throws IOException
	{
	   server = new ServerSocket(PORT, 100);
	   System.out.println("Server starting..");  
	}

	private Socket ResponseSocket() throws IOException
	{
	   Socket client = server.accept(); 
	   System.out.println("client connected..");
	  
	   return client;
	}

	private void CloseSocket(Socket socket) throws IOException
	{
	   reader.close();
	   writer.close();
	   socket.close();
	   System.out.println("client closed..");
	}

	private void SendMsg(Socket socket,boolean b) throws IOException 
	{
	   writer = new DataOutputStream(socket.getOutputStream()); 
	   writer.writeBoolean(b);
	    writer.flush();
	   
	}

	private boolean ReceiveMsg(Socket socket) throws IOException
	{
		reader = new DataInputStream(socket.getInputStream());     
	    String t = reader.readUTF();
	    return deal(t, socket);
	}
	public boolean deal(String function, Socket socket) throws IOException {
		DealMsg dm = new DealMsg(function);
		return false;
	}
}

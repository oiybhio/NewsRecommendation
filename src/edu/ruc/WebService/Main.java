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
		if(function.equals("Register")){
			return Register(socket);
		}
		if(function.equals("Login")){
			return Login(socket);
		}
		if(function.equals("Modify")){
			return Modify(socket);
		}
		if(function.equals("Recommend")){
			return Recommend(socket);
		}
		if(function.equals("Click")){
			return Click(socket);
		}
		if(function.equals("Refresh")){
			return Refresh(socket);
		}
		if(function.equals("Query")){
			return Query(socket);
		}
		if(function.equals("Comment")){
			return Comment(socket);
		}
		if(function.equals("Log")){
			return Log(socket);
		}
		return false;
	}
	private boolean Register(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String Password = reader.readUTF();
		String Info = reader.readUTF();
		SendMsg(socket,true);
		return false;
	}
	private boolean Login(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String Password = reader.readUTF();
		SendMsg(socket,true);
		return false;
	}
	private boolean Modify(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String Info = reader.readUTF();
		SendMsg(socket,true);
		return false;
	}
	private boolean Recommend(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String t = reader.readUTF();
		Date date = new Date(t);
		SendMsg(socket,true);
		return false;
	}
	private boolean Click(Socket socket) throws IOException {
		String NewsID = reader.readUTF();
		String t = reader.readUTF();
		Date date = new Date(t);
		SendMsg(socket,true);
		return false;
	}
	private boolean Refresh(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String t = reader.readUTF();
		Date date = new Date(t);
		SendMsg(socket,true);
		return false;
	}
	private boolean Query(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String Topic = reader.readUTF();
		String t = reader.readUTF();
		Date date = new Date(t);
		String RankMode = reader.readUTF();
		SendMsg(socket,true);
		return false;
	}
	private boolean Comment(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String NewsID = reader.readUTF();
		String comment = reader.readUTF();
		String t = reader.readUTF();
		Date date = new Date(t);
		SendMsg(socket,true);
		return false;
	}
	private boolean Log(Socket socket) throws IOException {
		String UserID = reader.readUTF();
		String NewsID = reader.readUTF();
		String t = reader.readUTF();
		Date StartTime = new Date(t);
		t = reader.readUTF();
		//不知道怎么转成long
		long time;
		int action = reader.readInt();
		String Info = reader.readUTF();
		SendMsg(socket,true);	
		return false;
	}	
}

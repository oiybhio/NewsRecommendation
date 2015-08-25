package Server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Vector;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class ClientThread extends Thread{
	//ClientThread
	Socket clientSocket;
	DataInputStream in = null;
	DataOutputStream out = null;
	
	ServerThread serverThread;
	String str;
	
	public static int ConnectNumber = 0;
	
	public ClientThread(Socket socket, ServerThread serverThread){
		clientSocket = socket;
		this.serverThread = serverThread;
		try {
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error:IO setup fails!");
			System.exit(3);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				String task = in.readUTF();
				System.out.println("ppppppin"+task);
				Server.jTextArea1.append(task);
				out.writeUTF("jdsdj"+task+"\n");
			} catch (Exception e) {
				System.out.println(e.toString());
				break;
			}
		}
	}
	
}

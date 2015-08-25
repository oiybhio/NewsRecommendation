package Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class ServerThread extends Thread{
	ServerSocket serverSocket;
	public static final int PORT = 8521;
	
	Vector<ClientThread> clients;
	Vector<Object> messages;
	
	String ip;
	InetAddress myIPaddress = null;
	
	public ServerThread(){
		clients = new Vector<ClientThread>();
		messages = new Vector<Object>();
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			myIPaddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ip = myIPaddress.getHostAddress();
		Server.jTextArea1.append("Server: "+ip+"\tPort: "+String.valueOf(serverSocket.getLocalPort())+"\n");		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				Socket socket = serverSocket.accept();
				Server.jTextArea1.append("server:"+socket.getInetAddress().getHostAddress()+"\n");
				System.out.println("server:"+socket.getInetAddress().getHostAddress());
				ClientThread clientThread = new ClientThread(socket,this);
				clientThread.start();
				if(socket!=null){
					synchronized(clients){
						clients.addElement(clientThread);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("error:client link fails");
				System.exit(2);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		serverSocket.close();
		serverSocket = null;
	}
	
	

}

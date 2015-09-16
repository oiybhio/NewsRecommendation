package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import org.apache.solr.client.solrj.SolrServerException;

import test.Main;

public class ServerMain extends Thread {
private Main main;
private ServerMain() throws Exception {
	main = new Main();
	main.Initialize();
	main.Preprocess();
	 
   CreateSocket();
} 

public void run() { 
   Socket client;
   String b;
   try { 
    while (true)
    {
     client=ResponseSocket();    
     while(true)
     {
      b=ReceiveMsg(client);  
      b = main.testWebservice(b);
      SendMsg(client,b);
      if(true)break;
     }
     CloseSocket(client);
    }   
   } 
   catch (IOException e) {
    System.out.println(e);
   } catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

}

private ServerSocket server = null;
private static final int PORT = 8521;
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

private void SendMsg(Socket socket,String b) throws IOException 
{
   writer = new DataOutputStream(socket.getOutputStream()); 
   writer.writeUTF(b);
    writer.flush();
   
}

private String ReceiveMsg(Socket socket) throws IOException
{
   reader = new DataInputStream(socket.getInputStream());     
    System.out.println("server get input from client socket..");
    long a = System.currentTimeMillis();
    String b = reader.readUTF();
    return b;
}

public static void main(final String args[]) throws Exception {
   ServerMain yaochatserver = new ServerMain();
   if (yaochatserver != null) {
    yaochatserver.start();
   }
}

}

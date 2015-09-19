package Server; 
 
import java.io.BufferedReader;  
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;  

import test.Main;
 
public class MyServer {  
    public static void main(String[] args) throws Exception {  
    	Main main = new Main();
    	main.Initialize();
    	main.Preprocess();
        ServerSocket server = new ServerSocket(8520);  
          System.out.println("Server starting..");
        while (true) {  
            Socket socket = server.accept();  
            invoke(socket, main);  
        }  
   }  
      
    private static void invoke(final Socket client, final Main main) throws Exception {  
        new Thread(new Runnable() {  
            public void run() {  
                DataInputStream in = null;  
                DataOutputStream out = null;  
                try {  
                    in = new DataInputStream(client.getInputStream());  
                    out = new DataOutputStream(client.getOutputStream());  
 
                	String b=in.readUTF();  
                    b = main.testWebservice(b);
                    out.writeUTF(b);
                    out.flush();
                } catch(Exception ex) {  
                    ex.printStackTrace();  
                } finally {  
                    try {  
                        in.close();  
                    } catch (Exception e) {}  
                    try {  
                        out.close();  
                    } catch (Exception e) {}  
                    try {  
                    	client.close();  
                    } catch (Exception e) {}  
                }  
           }  
        }).start();  
    }  
} 

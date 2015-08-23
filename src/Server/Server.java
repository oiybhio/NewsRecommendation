package Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Server extends JFrame implements ActionListener{

	/**
	 * @param args
	 */
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JScrollPane jScrollPane1 = new JScrollPane();
	
	static JTextArea jTextArea1 = new JTextArea();
	boolean bool = false, start = false;
	
	ServerThread serverThread;
	Thread thread;
	
	public Server()
	{
		super("Server");
		getContentPane().setLayout(borderLayout1);
		jButton1.setText("Start Server");
		jButton1.addActionListener(this);
		jButton2.setText("stop Server");
		jButton2.addActionListener(this);
		this.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);
		jPanel1.add(jButton1);
		jPanel1.add(jButton2);
		
		jTextArea1.setText("");
		jPanel2.setLayout(borderLayout2);
		jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);
		jScrollPane1.getViewport().add(jTextArea1);
		this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
		
		this.setSize(400, 400);
		this.setVisible(true);

		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server server = new Server();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == jButton1){
			serverThread = new ServerThread();
			serverThread.start();
		}else if(e.getSource() == jButton2){
			bool = false;
			start = false;
			try {
				serverThread.finalize();
			} catch (Throwable e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.setVisible(false);
		}
	}

}

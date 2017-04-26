
import java.net.*;

import javax.swing.text.BadLocationException;

import java.io.*; 
import java.awt.*;
public class TCPConnect extends Thread{ 
		ServerSocket listen;
		int port;
		String name;
		Stateframe sf;
		public TCPConnect(int port, String name,Stateframe sf) { 
			this.port = port;
			this.name = name;
			this.sf=sf; 
			this.start(); 
		}
		public void run(){ 
			try{
				/*Runtime run = Runtime.getRuntime();   
			    String cmdText="kill netstat |grep 8000";
				Process process = run.exec(cmdText);*/
				listen=new ServerSocket(port); //¼àÌýTCP¶Ë¿Ú
				while(true){ 
					Socket client=listen.accept();
					Chatframe mf=new Chatframe(client,name);
				}
			}catch (HeadlessException e) { 
				e.printStackTrace(); 
			}catch (IOException e) { 
				e.printStackTrace(); 
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}


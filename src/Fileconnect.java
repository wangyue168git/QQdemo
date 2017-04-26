
import java.net.*;

import java.io.*; 
import java.awt.*;
public class Fileconnect extends Thread{ 
		ServerSocket listen;
		public Fileconnect() { 
			this.start(); 
		}
		public void run(){ 
			try{
				listen=new ServerSocket(8080); //¼àÌýTCP¶Ë¿Ú
				while(true){ 
					Socket client=listen.accept();
					file1 f=new file1(client);
				}
			}catch (HeadlessException e) { 
				e.printStackTrace(); 
			}catch (IOException e) { 
				e.printStackTrace(); 
			}
		}
}



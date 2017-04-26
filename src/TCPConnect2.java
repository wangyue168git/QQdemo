
import java.net.*; 
import java.io.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TCPConnect2 extends Thread{ 
		ServerSocket listen;
		int port;
		String name;
		Chatframe sf;
		Talk2 talk1=null;
		Talk1 talk=null;
		Socket client;
		public TCPConnect2(int port, String name, Chatframe sf) { 
			this.port = port;
			this.name = name;
			this.sf=sf; 
			this.start(); 
			sf.item2.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {
					 sf.item1.setEnabled(true);
					 sf.item2.setEnabled(false);
					if(talk!=null&&talk1!=null){
						/*try {
							//talk.captrueOutputStream.flush();
							talk.captrueOutputStream.close();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}*/
				      talk.stop();
				      talk1.stop();
				      talk1=null;
				      talk=null;
				      try {
						talkconnect();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}}
	    	});
		}
	    public void talkconnect() throws IOException{
	    	DatagramPacket packet1;
			DatagramSocket socket1;
			socket1=new DatagramSocket(); 
			byte notify[]=new byte[100]; 
			notify=new String("L" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
		    packet1=new DatagramPacket(notify,notify.length,client.getInetAddress(),8003); 
			socket1.send(packet1);
	    }
		public void run(){ 
			try{
				listen=new ServerSocket(port); //¼àÌýTCP¶Ë¿Ú
				while(true){ 
				    client=listen.accept();
					//System.out.println(client.getPort());
				    talk1=new Talk2(client);
					talk1.start();
				    talk=new Talk1(client);
                    talk.start();
				}
			}catch (HeadlessException e) { 
				e.printStackTrace(); 
			}catch (IOException e) { 
				e.printStackTrace(); 
			}
		}
}


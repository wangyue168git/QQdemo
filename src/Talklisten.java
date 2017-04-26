

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.media.rtp.SessionAddress;
import javax.swing.JOptionPane; 
public class Talklisten extends Thread{
	Socket CheckSocket;
	Chatframe sf;
	Socket client,client1;
	DatagramPacket packet;
	DatagramSocket socket;
	String judge;
	InetAddress address;
	Talk1 talk=null;
	Talk2 talk2=null;
	video1 v1;
	video2 v2;
	public Talklisten(Chatframe sf) throws SocketException{
		socket=new DatagramSocket(8003);
		this.sf=sf;
		this.start();
		sf.item2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
           	 sf.item1.setEnabled(true);
			 sf.item2.setEnabled(false);
			 try {
				client.close();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				if(talk!=null&&talk2!=null){
			      talk.stop();
			      talk2.stop();
			      talk=null;
			      talk2=null;
			      try {
					talkconnect();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}}
    	});
		sf.item5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sf.menu.setEnabled(true);
				 try {
						client.close();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
						if(talk!=null&&talk2!=null){
					      talk.stop();
					      talk2.stop();
					      talk=null;
					      talk2=null;
					      }
						if(v1!=null){
							sf.v1.bool=false;
							v2.dispose();
							try {
								videoconnect();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
				
			}
			
		});
	}
    public void talkconnect() throws IOException{
    	DatagramPacket packet1;
		DatagramSocket socket1;
		socket1=new DatagramSocket(); 
		byte notify[]=new byte[100]; 
		notify=new String("D" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	    packet1=new DatagramPacket(notify,notify.length,client.getInetAddress(),8003); 
		socket1.send(packet1);
    }
    public void videoconnect() throws IOException{
    	DatagramPacket packet1;
		DatagramSocket socket1;
		socket1=new DatagramSocket(); 
		byte notify[]=new byte[100]; 
		notify=new String("D1" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	    packet1=new DatagramPacket(notify,notify.length,client.getInetAddress(),8003); 
		socket1.send(packet1);
    }
	@SuppressWarnings("resource")
	public void run(){ 
		try{
			while(true){ 
				byte[] Rdata=new byte[256]; 
				packet=new DatagramPacket(Rdata,Rdata.length); 
				socket.receive(packet); 
				String check=new String(packet.getData()).substring(1,packet.getLength()); 
			    judge=new String(packet.getData()).substring(0,1); 
			    address=InetAddress.getByName(check);
			    
				if(judge.equals("A")){ 
                   int n=JOptionPane.showConfirmDialog(sf, "对方请求语音聊天是否接受？","语音聊天请求",
                		   JOptionPane.YES_NO_OPTION);
                   if(n==JOptionPane.YES_OPTION){
                	   sf.item1.setEnabled(false);
   					   sf.item2.setEnabled(true);
                	   DatagramPacket packet1;
               		  DatagramSocket socket1;
               		  socket1=new DatagramSocket(); 
               		  byte notify[]=new byte[100]; 
               		  notify=new String("Y"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
               	      packet1=new DatagramPacket(notify,notify.length,address,8003); 
               		  socket1.send(packet1);
                	  client=new Socket(address,8001);
                	  talk=new Talk1(client);
                      talk.start();
                      talk2=new Talk2(client);
   					  talk2.start();
                   }
                   else{
                	 sf.item1.setEnabled(true);
   					 sf.item2.setEnabled(false);
                   	DatagramPacket packet1;
            		DatagramSocket socket1;
            		socket1=new DatagramSocket(); 
            		byte notify[]=new byte[100]; 
            		notify=new String("N"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
            	    packet1=new DatagramPacket(notify,notify.length,address,8003); 
            		socket1.send(packet1);
                   }
				}
				else if(judge.equals("M")){
					int n=JOptionPane.showConfirmDialog(sf, "对方请求视屏聊天是否接受？","视屏聊天请求",
	                		   JOptionPane.YES_NO_OPTION);
	                   if(n==JOptionPane.YES_OPTION){
	                	      DatagramPacket packet1;
	                		  DatagramSocket socket1;
	                		  socket1=new DatagramSocket(); 
	                		  byte notify[]=new byte[100]; 
	                		  notify=new String("K"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	                	      packet1=new DatagramPacket(notify,notify.length,address,8003); 
	                		  socket1.send(packet1);
	                		  client=new Socket(address,8001);
	                    	  talk=new Talk1(client);
	                          talk.start();
	                          talk2=new Talk2(client);
	       					  talk2.start();
	                		  SessionAddress target = new SessionAddress(address,50000);  
	                		  v2=new video2(target);
	                		  v1=new video1(address);
	                   }
	                   else{	
	                	DatagramPacket packet1;
	            		DatagramSocket socket1;
	            		socket1=new DatagramSocket(); 
	            		byte notify[]=new byte[100]; 
	            		notify=new String("N1"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	            	    packet1=new DatagramPacket(notify,notify.length,address,8003); 
	            		socket1.send(packet1);
	                	   
	                   }
				}
				else if(judge.equals("N")){
					sf.item1.setEnabled(true);
					sf.item2.setEnabled(false);
					JOptionPane.showMessageDialog(sf, "对方拒绝语音聊天","聊天请求",JOptionPane.WARNING_MESSAGE);
				}
				else if(judge.equals("N1")){
					JOptionPane.showMessageDialog(sf, "对方拒绝视屏聊天","聊天请求",JOptionPane.WARNING_MESSAGE);
					sf.v1.bool=false;
				}
				else if(judge.equals("Y")){
					sf.item1.setEnabled(false);
					sf.item2.setEnabled(true);
					JOptionPane.showMessageDialog(sf, "语音聊天连接成功，进入语音模式","聊天请求",JOptionPane.WARNING_MESSAGE);
				}
				else if(judge.equals("D")){
					sf.item1.setEnabled(true);
					sf.item2.setEnabled(false);
					JOptionPane.showMessageDialog(sf, "对方结束语音聊天","聊天请求",JOptionPane.WARNING_MESSAGE);
				}
				else if(judge.equals("D1")){
					JOptionPane.showMessageDialog(sf, "对方结束视屏聊天","聊天请求",JOptionPane.WARNING_MESSAGE);
					this.v2.dispose();
					v1.bool=false;
					
				}
				else if(judge.equals("K")){
					JOptionPane.showMessageDialog(sf, "视屏聊天连接成功，进入视屏模式","聊天请求",JOptionPane.WARNING_MESSAGE);
					SessionAddress target1 = new SessionAddress(address,50000);  
          		    v2=new video2(target1); 
				}
			}
		}catch (Exception e) { 
			 e.printStackTrace(); 
		}
}
 
}


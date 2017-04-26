
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException; 
public class check1 extends Thread{
	Socket CheckSocket;
	groupchat gf;
	InetAddress Cgroup;
	DatagramPacket packet;
	DatagramSocket socket;
	String tag;
	int i=0,k; 
	public final static int CHECK_PORT=5001;
	public check1(groupchat gf) throws SocketException{
		this.gf=gf;
		this.start();
	}
	public void run(){ 
		try {
			socket=new DatagramSocket(5003);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			while(true){ 
				byte[] Rdata=new byte[256]; 
				packet=new DatagramPacket(Rdata,Rdata.length); 
				socket.receive(packet);
				String c=new String(packet.getData());
				String []a=c.split("/");
				String check=new String(a[2]); 
				String judge=new String(a[0]); 
				String usertag=new String (a[1]);

				if(judge.equals("Q")){ 
					i=gf.list.getItemCount(); 
					boolean bool=false;
					for(int j=0;j<i;j++){
						if(gf.list.getItem(j).toString().equals(usertag)||check.equals(InetAddress.getLocalHost().getHostName().toString())){
							bool=true;
							break;
						}
					}
					if(bool==false){
					processMsg(usertag); 
					//ring();
				}
				}
			}
		}catch (Exception e) { 
			 e.printStackTrace(); 
		}
	

}
	public void processMsg(String str){ 
		gf.list.add(str);
	}  
}


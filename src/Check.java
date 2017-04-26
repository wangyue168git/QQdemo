
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket; 
public class Check extends Thread{
	Socket CheckSocket;
	Stateframe sf;
	InetAddress Cgroup;
	DatagramPacket packet;
	DatagramSocket socket;
	String tag;
	int i=0,k; 
	User[] userinfo;
	public final static int CHECK_PORT=5001;
	public Check(DatagramSocket socket1,Stateframe sf, User[] userinfo){
		socket=socket1;
		this.sf=sf;
		this.userinfo=userinfo;
		this.start();
	}
	public void run(){ 
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

				if(judge.equals("F")){ 
					i=sf.list.getItemCount(); 
					boolean bool=false;
					for(int j=0;j<i;j++){
						if(sf.list.getItem(j).toString().equals(usertag)){
							bool=true;
							break;
						}
					}
					if(bool==false){
					processMsg(usertag); 
					userinfo[i]=new User(usertag,check);
					//ring();
				}
				}
			}
		}catch (Exception e) { 
			 e.printStackTrace(); 
		}
	

}
	public void processMsg(String str){ 
		sf.list.add(str);
	}  
}
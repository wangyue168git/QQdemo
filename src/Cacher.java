
import java.io.*;
import java.net.*;
public class Cacher extends Thread {
	MulticastSocket Csocket; 
	Socket CheckSocket;
	Stateframe sf;
	InetAddress Cgroup;
	DatagramPacket packet;
	DatagramSocket socket;
	DatagramSocket socket1;
	String tag;
	int i=0,k; 
	//DataInputStream in=null;
	//DataOutputStream out=null;
	BufferedReader in; 
	PrintWriter out; 
	User[] userinfo;
	public final static int CATCH_PORT=7322;
	public final static int CHECK_PORT=5001;
	public Cacher(MulticastSocket csocket, Stateframe sf, String tag, User[] userinfo)
	{
		Csocket = csocket;
		this.sf = sf;
		this.tag = tag;
		this.userinfo = userinfo; 
		try{
			Cgroup=InetAddress.getByName("239.0.0.0");
		}catch (UnknownHostException e) { 
			e.printStackTrace(); 
		}
		start();
	}
	public void run(){ 
		try{
			while(true){ 
				byte[] Rdata=new byte[256]; 
				packet=new DatagramPacket(Rdata,Rdata.length); 
				Csocket.receive(packet);
				String c=new String(packet.getData());
				String []a=c.split("/");
				String check=new String(a[2]); 
				String judge=new String(a[0]); 
				String usertag=new String(a[1]); 
				
				if(judge.equals("C")){ 
					if(!check.equals(InetAddress.getLocalHost().getHostName())){
					i=sf.list.getItemCount(); 
					boolean bool=false;
					for(int j=0;j<i;j++){
						if(sf.list.getItem(j).toString().equals(usertag)||tag.equals(usertag)){
							bool=true;
							break;
						}
					}
					if(bool==false){
					processMsg(usertag); 
					userinfo[i]=new User(usertag,check);
					//ring();
					Connect(check,tag);
					}}
				}
				if(judge.equals("D")){ 
					i=sf.list.getItemCount(); 
					int j=0;
					while(j<i){
						if(sf.user[j].getName().equals(usertag)){
							break;
						}
						j++;
					}
					while(userinfo[j]!=null){
						String l=userinfo[j].getIP(); 
						if(l.equals(check)){ 
							k=j;
							while(userinfo[k+1]!=null){ 
								userinfo[k]=userinfo[k+1]; 
								k++; 
							}
							break;
						}
					}
					try { 
						removeMsg(usertag); 
					}catch (Exception n) {
						try { 
							removeMsg(usertag); 
						}catch (Exception n2) {} 
					}
				}
			}
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}
	public void Connect(String check,String name) throws IOException{ 
		
		DatagramPacket packet1;
		DatagramSocket socket1;
		socket1=new DatagramSocket(); 
		InetAddress address=InetAddress.getByName(check);
		byte notify[]=new byte[100]; 
		notify=new String("F"+"/"+name +"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	    packet1=new DatagramPacket(notify,notify.length,address,CHECK_PORT); 
		socket1.send(packet1); 
	}
	public void processMsg(String str){ 
		sf.list.add(str);
	}  
	public void removeMsg(String str){ 
		sf.list.remove(str);
	}
}


import java.io.IOException;  
import java.net.*;  
import javax.sound.sampled.*;

 
public class Talk extends Thread{
	Chatframe sf;
	int length;
	public static final int PORT = 3333;
	private InetAddress toIp;
	private TargetDataLine targetDataLine;
	private byte[] data;// 存放每次从麦克获得的数据  
	private AudioFormat format = new AudioFormat(44100,16,2,true,true);
    public Talk(InetAddress toip,Chatframe sf) throws LineUnavailableException{
    	this.toIp=toip;
    	this.sf=sf;
		
    	DataLine.Info dataLineInfo = new DataLine.Info( TargetDataLine.class, format); 
        
        targetDataLine=(TargetDataLine)AudioSystem.getLine(dataLineInfo); 
    	targetDataLine.open(format);
    
    	targetDataLine.start(); 
    }
    	 public void run(){
    	    	System.out.println("send threading start"); 
    	    	data=new byte[1024];
    	    	while (true) {
    	    		int cnt = targetDataLine.read(data, 0, data.length);
                    if(cnt>0){
                    	sendData();
                    }
    	    	}
    	    	
    }
    private void sendData(){
    	try{
    		DatagramPacket dp=new DatagramPacket(data,data.length,toIp,PORT);
    		DatagramSocket ds=new DatagramSocket();
    		ds.send(dp);
    		//System.out.println(1);
    		ds.close();
    	}catch (SocketException e) { 
    		e.printStackTrace();  
    	}catch (IOException e) { 
    		e.printStackTrace();  
    	}
    }

}

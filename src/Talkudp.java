
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;  
import javax.sound.sampled.*;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;  
public class Talkudp extends Thread{
	Chatframe sf;
	public static final int PORT = 3333;
	private InetAddress toIp;
	private byte[] data;  
	private byte[] data1=new byte[1024]; 
	SourceDataLine  sourceDataLine;
	private AudioFormat format = new AudioFormat(44100,16,2,true,true);
    public Talkudp(InetAddress toip,Chatframe sf) throws LineUnavailableException{
    	this.toIp=toip;
    	this.sf=sf;
		
    	  DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format); 
    	  sourceDataLine  =  (SourceDataLine) AudioSystem.getLine(dataLineInfo); 
    	  sourceDataLine.open(format);
    	  sourceDataLine.start(); 
    	  this.start();

}
    public void run(){
    	System.out.println("receive threading start");  
    	reciveData();
    	InputStream inputstream=new ByteArrayInputStream(data);
    	AudioInputStream audioInputStream = new AudioInputStream(inputstream,format,data.length /format.getFrameSize());
    	int ct;
    	try {
			while((ct=audioInputStream.read(data1,0,data1.length))!=-1){
				if(ct>0){
					sourceDataLine.write(data1, 0, ct); 
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    private void  reciveData(){
    	try{
    		data=new byte[1024]; 
    		DatagramPacket dp=new DatagramPacket(data,data.length);
    		DatagramSocket ds=new DatagramSocket(PORT);
    		ds.receive(dp);;
    		//System.out.println(1);
    		ds.close();
    	}catch (SocketException e) { 
    		e.printStackTrace();  
    	}catch (IOException e) { 
    		e.printStackTrace();  
    	}
    }

}   
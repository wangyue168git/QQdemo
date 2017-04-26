import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class file extends Thread {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Boolean B=true;
	DataOutputStream dos;
	DataInputStream dis;
	FileInputStream fis;
	File file;
	boolean ForD;   //区分传输的是文件还是文件夹
	Socket client1 = null;
	InetAddress a;
	DataOutputStream dos1;
	DataInputStream dis1;
	public file(InetAddress a,File f){
		this.a=a;
		this.file=f;
		this.start();
	}
	@Override
	public void run() {
		try {
			    client1=new Socket(a,8080);
			    dos1=new DataOutputStream(client1.getOutputStream());
			    dis1=new DataInputStream(client1.getInputStream());
				transmit(file);
				String s="/]00";
				byte b[]=s.getBytes();    
				dos1.write(b, 0, b.length);
				dos1.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void transmit(File f)throws IOException//这是传输的核心,而且将被递归
    {
		byte b[];
        String ts;
        int ti;
        String Sfile=file.toString();
        
        
        File F=f;
        if(F.isDirectory())
        {
        	for(File f1:f.listFiles())
            {   //首先通过if语句判断f1是文件还是文件夹             
            	
                if(f1.isDirectory()) //fi是文件夹,则向服务器端传送一条信息
                {
                    ts="/]0f"+(f1.getPath().replace(Sfile,""));
                	
                    b=ts.getBytes();
                    dos1.write(b,0,b.length);
                    dos1.flush();
                    dis1.read();
                    transmit(f1);//由于f1是文件夹(即目录),所以它里面很有可能还有文件或者文件夹,所以进行递归
                }
                else 
                {
                    fis=new FileInputStream(f1);
                    ts="/]0c"+(f1.getPath().replace(Sfile,""));
                    b=ts.getBytes();
                    dos1.write(b,0,b.length);
                    dos1.flush();
                    dis1.read();
                    dos1.writeInt(fis.available());//传输一个整型值,指明将要传输的文件的大小
                    dos1.flush();
                    dis1.read();
                    byte []send=new byte[10000];
                    while(fis.available()>0)//开始传送文件
                    {
                         ti=fis.read(send);
                         dos1.write(send,0,ti);
                         dos1.flush();
                    }
                    dos1.flush();
                    fis.close();
                    dis1.read();
                }
            }
        	
        }else
    	{
            fis=new FileInputStream(F);
           
            ts="/]0c\\"+(F.getName());
            b=ts.getBytes();
            dos1.write(b,0,b.length);
            dos1.flush();
            dis1.read();
            dos1.writeInt(fis.available());//传输一个整型值,指明将要传输的文件的大小
            dos1.flush();
            dis1.read();
           byte []send=new byte[10000];
            while(fis.available()>0)//开始传送文件
            {
                ti=fis.read(send);
                dos1.write(send,0,ti);
                 dos1.flush();
            }
            dos1.flush();
            fis.close();
            dis1.read();
    	}
       
    }

}

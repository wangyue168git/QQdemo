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
	boolean ForD;   //���ִ�������ļ������ļ���
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
	public void transmit(File f)throws IOException//���Ǵ���ĺ���,���ҽ����ݹ�
    {
		byte b[];
        String ts;
        int ti;
        String Sfile=file.toString();
        
        
        File F=f;
        if(F.isDirectory())
        {
        	for(File f1:f.listFiles())
            {   //����ͨ��if����ж�f1���ļ������ļ���             
            	
                if(f1.isDirectory()) //fi���ļ���,����������˴���һ����Ϣ
                {
                    ts="/]0f"+(f1.getPath().replace(Sfile,""));
                	
                    b=ts.getBytes();
                    dos1.write(b,0,b.length);
                    dos1.flush();
                    dis1.read();
                    transmit(f1);//����f1���ļ���(��Ŀ¼),������������п��ܻ����ļ������ļ���,���Խ��еݹ�
                }
                else 
                {
                    fis=new FileInputStream(f1);
                    ts="/]0c"+(f1.getPath().replace(Sfile,""));
                    b=ts.getBytes();
                    dos1.write(b,0,b.length);
                    dos1.flush();
                    dis1.read();
                    dos1.writeInt(fis.available());//����һ������ֵ,ָ����Ҫ������ļ��Ĵ�С
                    dos1.flush();
                    dis1.read();
                    byte []send=new byte[10000];
                    while(fis.available()>0)//��ʼ�����ļ�
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
            dos1.writeInt(fis.available());//����һ������ֵ,ָ����Ҫ������ļ��Ĵ�С
            dos1.flush();
            dis1.read();
           byte []send=new byte[10000];
            while(fis.available()>0)//��ʼ�����ļ�
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

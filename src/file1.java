import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class file1 extends JFrame  implements Runnable {
	JFileChooser fileDialog;
	ServerSocket server  =  null;  
	Socket client = null;
	    DataInputStream dis;
	    DataOutputStream dos;
	    String select;
	    ServerSocket server1  =  null;  
		Socket client1 = null;
	    DataInputStream dis1;
	    DataOutputStream dos1;
	    FileOutputStream fos;
	    File file;
	    boolean B=true;
	    Socket client2 = null;
		FileInputStream fis;
	    Thread th;
	public file1(Socket socket) throws IOException{
		this.client1=socket;
		th=new Thread(this);
		th.start();
	}
	public void run() {
		                int i=0;
					    byte receive[]=new byte[1024];
					    int ti;
							try {
								dis1=new DataInputStream(client1.getInputStream());
								dos1=new DataOutputStream(client1.getOutputStream());
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							boolean flag=true;
							JFileChooser jfc=new JFileChooser();
							jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
							jfc.setDialogType(JFileChooser.FILES_ONLY);
							jfc.showDialog(new JLabel(), "����");
						   // int state=jfc.showSaveDialog(jfc);
					       
							file=jfc.getSelectedFile();
							if(file!=null){
							try
					        {
					            String answer="g";
					            byte ans[]=answer.getBytes();    //String��getBytes()�����ǵõ�һ��ϵͳĬ�ϵı����ʽ���ֽ�����
					            byte b[]=new byte[1024];
					            new File(file.toString()).mkdirs();
					            //mkdirs()����һ��Ŀ¼������·�����ɵ�ǰ File ����ָ����������һ����ĸ�·��
					            while(flag)
					            {
					                ti=dis1.read(b);
					                dos1.write(ans);
					                String select1=new String(b,0,ti);
					                if(select1.contains("/]0f"))
					                {
					                   
					                	File f=new File(file+(select1.replace("/]0f","")));//�����ļ���
					                    //System.out.println("creat directory");
					                    f.mkdirs();
					                }
					                else if(select1.contains("/]0c"))
					                {
					                    
					                	fos=new FileOutputStream(file+(select1.replace("/]0c","")));
					                   // System.out.println(file+(select1.replace("/]0c","")));
					                    String cs;
					                    boolean cflag=true;
					                    int tip=dis1.readInt();
					                    dos1.write(ans);
					                    while(tip>0)
					                    {
					                        ti=dis1.read(b,0,(tip>1000?1000:tip));
					                        tip=tip-ti; 
					                        cs=new String(b,0,4);
					                        fos.write(b,0,ti);
					                    }
					                    fos.flush();
					                    fos.close();
					                    dos1.write(ans);
					                }
					                else if(select1.contains("/]00"))
					                {
					                    flag=false;
					                }
					            	
					                
					            }
					            dis1.close();
					            client1.close();
					         }
					         catch(IOException e)
					         {
					             //System.out.println("Error");
					         }
							
	}}
}
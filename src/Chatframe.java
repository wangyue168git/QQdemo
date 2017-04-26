
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*; 
import java.awt.event.*; 
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import sun.audio.*; 
public class Chatframe extends JFrame implements Runnable  {
	public final static int TCP_PORT=8000; 
	public final static int TCP_PORT2=8001;
	Font f= new Font("微软雅黑", Font.PLAIN, 12);
	JFileChooser fileDialog;
	video1 v1;
	File file;
	String name; 
	Talklisten l;
	Socket client,client1; 
	Thread th; 
	String msg; 
    SimpleAttributeSet attrset = new SimpleAttributeSet();
	JTextPane tp=new JTextPane();
	JPanel sends=new JPanel();
	JPanel men=new JPanel();
	JLabel Label2=new JLabel();
	JScrollPane js=new JScrollPane(tp);
	JPanel northPanel=new JPanel(); 
	JPanel southPanel=new JPanel(); 
	JPanel eastPanel=new JPanel(); 
	JPanel westPanel=new JPanel(); 
	JTextField input=new JTextField(21); 
	JMenuBar menbar=new JMenuBar();
	JMenuBar menbar1=new JMenuBar();
	JMenu menu =new JMenu("  语 音   ");
	JMenu menu1=new JMenu("表情");
	JMenuItem item1=new JMenuItem("开启语音");
	JMenuItem item2=new JMenuItem("结束语音");
	JMenu menu2 =new JMenu("  视屏   ");
	JMenuItem item4=new JMenuItem("  开启视频  ");
	JMenuItem item5=new JMenuItem("  结束视频  ");
	JMenu menu3 =new JMenu("   文件   ");
	JMenuItem item3=new JMenuItem("    发送文件  ");
	JButton button4=new JButton("表情");
    JButton send=new JButton("发送"); 
    protected BufferedReader in;
    protected PrintWriter out; 
   // Icon icon=new ImageIcon(Stateframe.class.getResource("user.png"));
    public Chatframe(Socket client,String name) throws HeadlessException, BadLocationException, IOException { 
    	this.name = new String(name); 
    	this.client = client;
    	init(name); 
    	startConnect(); 
    	TCPListener2();
    	fileconnet();
        l=new Talklisten(this);
    	enableEvents(AWTEvent.WINDOW_EVENT_MASK); 
    }
    public void init(String name) throws BadLocationException{ 
    	send.setFont(f);
    	send.setIcon(new ImageIcon(Stateframe.class.getResource("send.png")));
    	menu.setIcon(new ImageIcon(Stateframe.class.getResource("yuyin.png")));
    	menu2.setIcon(new ImageIcon(Stateframe.class.getResource("shiping.png")));
    	menu3.setIcon( new ImageIcon(Stateframe.class.getResource("folder.png")));
    	this.setBounds(410, 130, 460, 500); 
    	ImageIcon background = new ImageIcon(Stateframe.class.getResource("16.jpg"));
        JLabel   picLabel   =   new   JLabel(background); 
        picLabel.setBounds(0, 0, this.getWidth(), this.getHeight()); 
        JPanel jp= (JPanel) this.getContentPane();
        jp.setOpaque(false);
        this.getLayeredPane().add(picLabel, new Integer(Integer.MIN_VALUE)); 
        //northPanel.setLayout(new GridLayout(2,2));
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);
        eastPanel.setOpaque(false);
        westPanel.setOpaque(false);
        js.setOpaque(false);
        js.getViewport().setOpaque(false);
        js.setBackground(new Color(0,0,0,0));
        tp.setOpaque(false);
        menbar.setOpaque(false);
        menbar1.setOpaque(false);
        sends.setOpaque(false);
       // tp.setBackground(new Color(0,0,0,0));
        //input.setOpaque(false);
    	tp.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    	input.setFont(new Font("微软雅黑", Font.PLAIN, 17));
    	this.setLayout(new BorderLayout(10,10));
    	//menu.setEnabled(false);
    	//menu.setIcon();
    	menu.add(item1);
    	menu.addSeparator();
    	menu.add(item2);
    	menbar.add(menu);
    	northPanel.setLayout(new GridLayout(1,1));
    	menbar.add(menu2);
    	menbar.add(menu3);
    	northPanel.add(menbar);
    	//tp.setEnabled(false);
    	//tp.setForeground(new Color(160,32,240));
    	//tp.setOpaque(false);
    	this.add(northPanel,BorderLayout.NORTH);
    	this.add(js,BorderLayout.CENTER); 
    	this.add(eastPanel,BorderLayout.EAST); 
    	this.add(westPanel,BorderLayout.WEST); 
    	southPanel.setLayout(new GridLayout(2,1));
    	menbar1.add(menu1);
    	southPanel.add(menbar1);
    	sends.add(input);
    	item1.setEnabled(true);
		item2.setEnabled(false);
		menu3.add(item3);
		menu2.add(item4);
		menu2.add(item5);
		send.setContentAreaFilled(false);
		send.setForeground(Color.BLACK);
		item4.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				menu.setEnabled(false);
				try {
					videoconnect();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		item3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
				jfc.showDialog(new JLabel(), "选择");
				file=jfc.getSelectedFile();
				if(file!=null){
				   new file(client.getInetAddress(),file);
				}
			}
			
		});
    	input.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					sendMsg(input.getText());
					Date date=new Date();
					SimpleDateFormat matter=new SimpleDateFormat("HH:mm:ss");
					try {    
						StyleConstants.setForeground(attrset, new Color(160,32,240));
						//StyleConstants.setForeground(attrset,  Color.orange);
						StyleConstants.setAlignment(attrset, StyleConstants.ALIGN_RIGHT);  
						tp.getDocument().insertString(tp.getDocument().getLength(), "I :       "+matter.format(date).toString()+"\n"+input.getText()+"\n", attrset);
						tp.setCaretPosition(tp.getDocument().getLength());
                    
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					input.setText(""); 
				}catch (IOException e1) { 
					e1.printStackTrace(); 
				}
			} 
    	});
    	sends.add(send);
    	send.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					sendMsg(input.getText()); 
					Date date=new Date();
					SimpleDateFormat matter=new SimpleDateFormat("HH:mm:ss");
					try {
						 StyleConstants.setForeground(attrset, new Color(160,32,240));
						tp.getDocument().insertString(tp.getDocument().getLength(), "I :       "+matter.format(date).toString()+"\n"+input.getText()+"\n", attrset);
						tp.setCaretPosition(tp.getDocument().getLength());
					
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					input.setText(""); 
				}catch (IOException e1) { 
					e1.printStackTrace(); 
				}
			} 
    		
    	});
    	southPanel.add(sends);
    	this.add(southPanel,BorderLayout.SOUTH); 
    	this.setVisible(true); 
    	this.setTitle("与 "+name+" 会话中"); 
    	this.setResizable(false); 
    	item1.addActionListener(new ActionListener(){//语音聊天请求
			public void actionPerformed(ActionEvent e) {
				try {
					talkconnect();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
    	});
    	}
    public void talkconnect() throws IOException{//发送语音请求
    	DatagramPacket packet1;
		DatagramSocket socket1;
		socket1=new DatagramSocket(); 
		byte notify[]=new byte[100]; 
		notify=new String("A" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	    packet1=new DatagramPacket(notify,notify.length,client.getInetAddress(),8003); 
		socket1.send(packet1);
    }
    public void videoconnect() throws IOException{
    	DatagramPacket packet1;
		DatagramSocket socket1;
		socket1=new DatagramSocket(); 
		byte notify[]=new byte[100]; 
		notify=new String("M" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	    packet1=new DatagramPacket(notify,notify.length,client.getInetAddress(),8003); 
		socket1.send(packet1);
		v1=new video1(client.getInetAddress());
    }
    public void startConnect() throws BadLocationException{ 
    	try{
    		in=new BufferedReader(new InputStreamReader(client.getInputStream())); 
    		out=new PrintWriter(client.getOutputStream()); 
    		tp.getDocument().insertString(tp.getDocument().getLength()," --------------------------开始聊天-------------------------"+ "\n" , null);
    		out.println("----------------------"); 
    		out.flush();  
    	}catch (IOException e) {  
    		e.printStackTrace(); 
    	}
    	if(th==null){ 
    		th=new Thread(this);
    		th.start();
    	}
    }
	
	public void run() {
		// TODO Auto-generated method stub
		try{
			msg=receiveMsg();
			while(!msg.equals("User have been disconnect!")){
				 try{
					 msg=receiveMsg();
					 th.sleep(100L); 
				     Date date=new Date();
				     SimpleDateFormat matter=new SimpleDateFormat("HH:mm:ss");
				     try {
				    	    StyleConstants.setForeground(attrset, Color.red);
							tp.getDocument().insertString(tp.getDocument().getLength(), name+":       "+matter.format(date).toString()+"\n" +msg + "\n", attrset);
							tp.setCaretPosition(tp.getDocument().getLength());
				     } catch (BadLocationException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				 }catch (Exception e) { 
					 in.close();
					 out.close();
					 client.close();
					 msg=new String("User have been disconnect!"); 
				 }
			}
			//System.out.println(msg);
			if(msg.equals("User have been disconnect!")){
				sendMsg("User have been disconnect!");
			}
			in.close(); 
			out.close();
			client.close();
			this.setVisible(false); 
			this.dispose();
			
		}catch (IOException e) {
			try{
				in.close(); 
				out.close();
				client.close();
			}catch (IOException e1) { 
				e1.printStackTrace();
			}
		}finally{ 
			try { 
				in.close(); 
				out.close();
				client.close();
			} catch (IOException e1) { 
				e1.printStackTrace(); 
			}
		}
	}
    public void fileconnet() throws IOException{
 	  new Fileconnect();
    }
	protected void processWindowEvent(WindowEvent e){ 
		super.processWindowEvent(e); 
		if(e.getID()==WindowEvent.WINDOW_CLOSING){ 
			try{
				sendMsg("User have been disconnect!"); 
				in.close(); 
				out.close();
				client.close();
			} catch (IOException e1) { 
				e1.printStackTrace(); 
			}
		}
	}
	public String receiveMsg()throws IOException{ 
		String msg=new String(); 
		try{
			msg=in.readLine(); 
		}catch (IOException e) { 
			e.printStackTrace(); 
			in.close(); 
			out.checkError(); 
			client.close(); 
			msg=new String("User have been disconnect!"); 
		}
		return msg; 
	}
	public void sendMsg(String str)throws IOException{
		out.println(str); 
		out.flush(); 
	}
    public void TCPListener2(){
   	 TCPConnect2 tcp=new TCPConnect2(TCP_PORT2,name,this);
   	
   }

}

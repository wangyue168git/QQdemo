 import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

public class groupchat extends JFrame implements Runnable {
	String name; 
	List list=new List(15);
	public final static int PORT=5002;
	MulticastSocket Csocket; 
	Socket CheckSocket;
	InetAddress Cgroup;
	DatagramPacket packet;
	DatagramSocket socket;
	DatagramSocket socket1;
	Thread th=new Thread(this);
	JPanel textPanel=new JPanel(new BorderLayout(10,10)); 
	JPanel inputPanel=new JPanel(); 
	JTextPane tp=new JTextPane();
	JScrollPane js=new JScrollPane(tp);
	JTextArea area=new JTextArea(10,15);
	JTextField input=new JTextField(20); 
	JButton send=new JButton("发送"); 
	JMenuBar menubar;
	JMenu menu;
	JMenuItem item1,item2;
	JPanel eastPanel=new JPanel(new GridLayout(2,1)); 
	Stateframe sf;
	JLabel notice=new JLabel(); 
	public groupchat(){ 
		
	}
	public groupchat(Stateframe sf) throws UnknownHostException, SocketException{
		Cgroup=InetAddress.getByName("238.0.0.0");
		this.sf=sf;
		this.name=new String(sf.name);
		init();
		check1 ck=new check1(this);
		th.start();
	}
	public void init(){
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds(width/2-250, height/2-225, 545, 450); 
	  	  this.setResizable(false);
		
    	ImageIcon background = new ImageIcon(Stateframe.class.getResource("5.jpg"));
        JLabel   picLabel   =   new   JLabel(background); 
        picLabel.setBounds(0, 0, this.getWidth(), this.getHeight()); 
        JPanel jp= (JPanel) this.getContentPane();
        jp.setOpaque(false);
        this.getLayeredPane().add(picLabel, new Integer(Integer.MIN_VALUE)); 
        
        eastPanel.setOpaque(false);
        js.setOpaque(false);
        js.getViewport().setOpaque(false);
        js.setBackground(new Color(0,0,0,0));
        tp.setOpaque(false);
    	tp.setFont(new Font("微软雅黑", Font.PLAIN, 15));
    	input.setFont(new Font("微软雅黑", Font.PLAIN, 17));
    	this.setLayout(new BorderLayout(10,10)); 
		
		area.setEditable(true);
		area.setEnabled(false);
		area.setOpaque(false);
		menubar=new JMenuBar();
		menu=new JMenu("菜单");
		item1=new JMenuItem("编辑通知栏");
		item2=new JMenuItem("其他");
		menu.add(item1);
		menu.add(item2);
		menubar.add(menu);
		//menubar.setBackground(Color.WHITE);
		menu.setBackground(Color.WHITE);
		menubar.setOpaque(false);
		textPanel.setOpaque(false);
		inputPanel.setOpaque(false);
		this.setLayout(new BorderLayout(0,5));
		

		textPanel.add(js,BorderLayout.CENTER); 
		inputPanel.add(input); 
		inputPanel.add(send); 
		textPanel.add(inputPanel,BorderLayout.SOUTH); 
        textPanel.add(menubar,BorderLayout.NORTH);
		this.add(textPanel,BorderLayout.CENTER); 
		eastPanel.add(notice); 
		eastPanel.add(list);
		this.setVisible(true); 		
 		this.add(eastPanel,BorderLayout.EAST);
 		send.setBorderPainted(false);
 		send.setBackground(Color.green);
 		/*item1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Notice();
				String s=new String();
				notice.setText(s);
			}
 			
 		});*/
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(input.getText()==null){
					input.setText("");
				}
				 try {
					//Csocket=new MulticastSocket(PORT);
					//Csocket.joinGroup(Cgroup);
					socket=new DatagramSocket();
			  		 byte notify[]=new byte[256]; 
			  		 notify=new String("X"+"/"+name+"/" +input.getText()).getBytes(); 
			  		 packet=new DatagramPacket(notify,notify.length,Cgroup,PORT); 
			  		 socket.send(packet); 
			  		 input.setText(null);
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
			
		});
		input.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				send.doClick();
			}	
		});
	}
	protected void processWindowEvent(WindowEvent e){ 
	  	  super.processWindowEvent(e); 
	  	  if(e.getID()==WindowEvent.WINDOW_CLOSING){ 
	  		  try { 
	  			  socket=new DatagramSocket();
	  			  byte[]notice=new String("P"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
	  			  packet=new DatagramPacket(notice,notice.length,Cgroup,PORT); 
	  			  socket.send(packet);  
	  		  }
	  		  catch (Exception e1) {
	  			  e1.printStackTrace(); 
	  			  
	  		  }
	  		  this.dispose();
	  		  socket.close(); 
	  	  }
	   }

	public void run() {
		try {
			Csocket=new MulticastSocket(PORT);
			Csocket.joinGroup(Cgroup);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
			byte[] Rdata=new byte[256]; 
			packet=new DatagramPacket(Rdata,Rdata.length); 
			try {
				Csocket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String c=new String(packet.getData());
			String []a=c.split("/");
			String text=new String(a[2]); 
			String judge=new String(a[0]); 
			String usertag=new String(a[1]);
			if(judge.equals("X")){
				 Date date=new Date();
				 SimpleDateFormat matter=new SimpleDateFormat("HH:mm:ss");
				try {
					tp.getDocument().insertString(tp.getDocument().getLength(), usertag+":      "+matter.format(date).toString()+"\n" +text+ "\n", null);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				tp.setCaretPosition(tp.getDocument().getLength());
			}
			else if(judge.equals("Y")){
				list.add(usertag);
				 try {
					 socket=new DatagramSocket();
			  		 byte notify[]=new byte[256]; 
			  		 notify=new String("Q"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
			  		 packet=new DatagramPacket(notify,notify.length,InetAddress.getByName(text),5003); 
			  		 socket.send(packet); 
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(judge.equals("P")){
				try {
					if(!text.equals(InetAddress.getLocalHost().getHostName().toString())){
					list.remove(usertag);
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	} 
	
}

class Notice extends JFrame{
	Notice(){ 
		JButton button1,button2;
		button1=new JButton("取消");
		button2=new JButton("确定");
		JTextArea area=new JTextArea();
		this.add(area);
		this.setBounds(200, 200, 400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		button1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				area.setText(null);
			}
			
		});
		button2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				dispose(); 
			}
			
		});
	}
}



import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;
import java.net.*; 
@SuppressWarnings("serial")
public class Stateframe extends JFrame {
	String name="匿名";  
	Font f= new Font("隶书", Font.PLAIN, 20);
	Font f1= new Font("楷体", Font.PLAIN, 17);
	JPanel northPanel=new JPanel(new GridLayout(1,2));
	JPanel centerPanel=new JPanel(new GridLayout(1,1));
	JPanel southPanel=new JPanel(new GridLayout(1,2));
	JPanel labelPanel=new JPanel(new GridLayout(4,1));
	JPanel lan=new JPanel();
	JPanel lan2=new JPanel();
	ImageIcon icons = new ImageIcon(Stateframe.class.getResource("4.png"));
	JLabel pic =new JLabel(icons);
	JLabel nameLabel=new JLabel(); 
	JLabel Label2=new JLabel();
	JLabel Label3=new JLabel();
	JLabel Label4=new JLabel();
	TCPConnect tcp;
	JComboBox<String> state=new JComboBox<String>();
	List list=new List(); 
	JButton button1=new JButton("在线好友");
	JButton button2=new JButton("讨论组");
	ServerSocket listensocket;
	User[] user=new User[50]; 
	public final static int TCP_PORT=8000;
	public final static int DEFAULT_PORT=8322; 
	public final static int CATCH_PORT=7322; 
	public final static int CHECK_PORT=5001;
	DatagramSocket socket=null;
	InetAddress group;
	InetAddress groupC;
	DatagramPacket packet;
	DatagramSocket s=null;
	MulticastSocket socketr; 
	MulticastSocket socketC; 
	Socket Csocket; 
    public Stateframe(String s)  {
    	this.name=s;
        init();
        JoinGroup();
        TCPListener();
        enableEvents(AWTEvent.WINDOW_EVENT_MASK); 
    }
	protected void processWindowEvent(WindowEvent e){ 
  	  super.processWindowEvent(e); 
  	  if(e.getID()==WindowEvent.WINDOW_CLOSING){ 
  		  try { 
  			  byte[]notice=new String("D"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
  			  packet=new DatagramPacket(notice,notice.length,groupC,CATCH_PORT); 
  			  socket.send(packet);  
  		  }
  		  catch (Exception e1) {
  			  e1.printStackTrace(); 
  		  }
  		  socket.close();
  		  tcp.interrupt();
  		  this.dispose();
  		  System.exit(0); 
  	  }
    }
    @SuppressWarnings("deprecation")
	public void init(){
      this.setBounds(920, 100, 238, 534);
      ImageIcon background = new ImageIcon(Stateframe.class.getResource("3.jpg"));//加背景图片
      JLabel   picLabel   =   new   JLabel(background); 
      picLabel.setBounds(0, 0, this.getWidth(), this.getHeight()); 
      JPanel jp= (JPanel) this.getContentPane();
      jp.setOpaque(false);
      this.getLayeredPane().add(picLabel, new Integer(Integer.MIN_VALUE)); 
      labelPanel.setOpaque(false);
      centerPanel.setOpaque(false);
      CardLayout card=new CardLayout();
      JButton button3=new JButton("加入讨论组");
      button3.setBounds(10, 40,200, 95);
      lan2.setLayout(null);
      button3.setFont(f1);
      lan2.add(button3);
      lan2.setOpaque(false);
      lan.setOpaque(false);
      lan.setLayout(card);
      centerPanel.add(list); 
      lan.add("1",centerPanel);
      lan.add("2",lan2);  
	  list.setFont(f);
	  list.setBackground(new Color(221,160,221));
  	  setIconImage((new ImageIcon( )).getImage()); 
  	  this.setLayout(new BorderLayout(5,5)); 
  	  northPanel.add(pic); 
  	  northPanel.setOpaque(false);
  	  nameLabel.setText(name); 
  	  nameLabel.setFont(new Font("楷体", Font.PLAIN, 19));
  	  state.setOpaque(false);
  	  state.setFont(new Font("黑体", Font.PLAIN, 12));
  	  state.addItem("在线");
  	  state.addItem("隐身");
      state.setForeground(Color.blue);
  	  labelPanel.add(Label2);
  	  labelPanel.add(nameLabel); 
  	 // labelPanel.add(Label4);
  	  labelPanel.add(state);
  	  //labelPanel.add(Label3);
  	  northPanel.add(labelPanel); 
  	  this.add(northPanel,BorderLayout.NORTH); 
  	  button1.setForeground(Color.BLACK);
  	  button2.setForeground(Color.WHITE);
  	  button3.setForeground(Color.BLACK);
      button1.setBorderPainted(false);
      button3.setBorderPainted(false);
      button1.setContentAreaFilled(false);
      button2.setContentAreaFilled(false);
      button3.setContentAreaFilled(false);
      //button1.setPressedIcon();
      //button1.setRolloverIcon();
      //button1.setBackground(new Color(221,160,221));
      southPanel.setOpaque(false);
  	  button1.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			card.show(lan,"1");
			//button1.setBackground(new Color(221,160,221));
			button2.setForeground(Color.WHITE);
			button1.setForeground(Color.BLACK);
			button1.setBorderPainted(false);
			button2.setBorderPainted(true);
		} 
  	  });
  	  button2.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			button1.setForeground(Color.WHITE);
			button2.setForeground(Color.BLACK);
			button2.setBorderPainted(false);
			button1.setBorderPainted(true);
			card.show(lan,"2");	
		}
  	  });
  	  
  	  button3.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				 Groupchat();
				 group=InetAddress.getByName("238.0.0.0");
				 socketr=new MulticastSocket ();
				 socketr.joinGroup(group);
		  		 byte notify[]=new byte[256]; 
		  		 notify=new String("Y"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
		  		 packet=new DatagramPacket(notify,notify.length,group,5002); 
		  		 socketr.send(packet); 
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
  		  
  	  });
  	  state.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			if(state.getSelectedItem().toString().equals("隐身")){
				try { 
		  			  byte[]notice=new String("D"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
		  			  packet=new DatagramPacket(notice,notice.length,groupC,CATCH_PORT); 
		  			  socket.send(packet);  
		  		  }
		  		  catch (Exception e1) {
		  			  e1.printStackTrace(); 
		  		  }
			}
			if(state.getSelectedItem().toString().equals("在线")){
				try { 
		  			  byte[]notice=new String("C"+"/"+name+"/"+InetAddress.getLocalHost().getHostAddress()).getBytes(); 
		  			  packet=new DatagramPacket(notice,notice.length,groupC,CATCH_PORT); 
		  			  socket.send(packet);  
		  		  }
		  		  catch (Exception e1) {
		  			  e1.printStackTrace(); 
		  		  }
			}
		}
  		  
  	  });
  	  list.addActionListener(new ActionListener(){

  		@Override
  		public void actionPerformed(ActionEvent e) {
  			Socket socket;
  			int s=list.getSelectedIndex();
  			String address=user[s].getIP();
  			try{
  				socket=new Socket(address,TCP_PORT);
  				Chatframe mf=new Chatframe(socket,user[s].getName()); 
  			}catch (Exception e1) { 
  				e1.printStackTrace(); 
  			}
  		}
  		  
  	  });
  	  

  
     //centerPanel.add(lan3);
  	 // centerPanel.add(list); 
  	  this.add(lan,BorderLayout.CENTER);
  	  southPanel.add(button1);
  	  button1.setFont(new Font("黑体", Font.PLAIN, 14));
  	  button2.setFont(new Font("黑体", Font.PLAIN, 14));
  	  southPanel.add(button1);
  	  southPanel.add(button2);
  	  this.add(southPanel,BorderLayout.SOUTH); 
  	 // this.setBounds(920, 100, 200, 480); 
  	  this.setVisible(true); 
  	  this.setResizable(false);
    }
    public void TCPListener(){
    	tcp=new TCPConnect(TCP_PORT,name,this);
    
    }
 	public void  Groupchat() throws UnknownHostException, SocketException{
  		groupchat gc =new groupchat(this);
  	}
    public void JoinGroup(){ 
  	  try{
  		  groupC=InetAddress.getByName("239.0.0.0");
  		  socket=new DatagramSocket(); 
  		  byte notify[]=new byte[256]; 
  		  notify=new String("C"+"/"+name+"/" +InetAddress.getLocalHost().getHostAddress()).getBytes(); 
  		  packet=new DatagramPacket(notify,notify.length,groupC,CATCH_PORT); 
  		  socket.send(packet); 
  		  socketC=new MulticastSocket(CATCH_PORT); 
  		  socketC.joinGroup(groupC); 
  		  DatagramSocket socket2=new DatagramSocket(CHECK_PORT);
  		  Cacher catcher=new Cacher(socketC,this,name,user); 
  		  Check check=new Check(socket2,this,user);
  		  
  	  }catch (Exception e) { 
  		  e.printStackTrace();
  	  }
    }

}

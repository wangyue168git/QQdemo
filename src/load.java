
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class interfa1 extends JFrame{
	private int xx, yy;
	private boolean isDraging = false;
	JPanel board;
	ImageIcon image,closeimg;
	JButton button,button1,button2;
	JLabel lab;
	JTextField text;
	JCheckBox chbox;
	Font f= new Font("楷体", Font.PLAIN, 16);
	String user=new String();
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	interfa1(){
		lab=new JLabel("用户名");
		lab.setBounds(75,83, 50, 20);
		text=new JTextField();
		text.setBounds(140, 80, 190,29);
		chbox=new JCheckBox("匿名");
		chbox.setBounds(260, 120, 60, 30);
		chbox.setContentAreaFilled(false);
		chbox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//user="匿名";
				text.setText("匿名");
			}
			
		});
		button=new JButton();
		button1=new JButton("登录");
		button2=new JButton("取消");
		image=new ImageIcon(Stateframe.class.getResource("12.jpg"));
		closeimg=new ImageIcon(Stateframe.class.getResource("button.gif"));
		button.setBounds(385, 3, 12, 12);
		button.setContentAreaFilled(false);//透明
		button.setBorderPainted(false);//去掉按钮边框
		button.setIcon(closeimg);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//System.exit(0);//关闭所有进程
				dispose();//释放窗体
			}
			
		});
		button1.setBackground(Color.green);
		button1.setBounds(240, 160, 80, 25);
		//button2.setBorderPainted(false);
		button1.setFont(f);
		button2.setFont(f);
		//button1.setOpaque(false);
		//button1.setContentAreaFilled(false);
		button1.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!text.getText().equals("")){
				if(user!=text.getText()&&user!="匿名"){
					user=text.getText();
				}
				Stateframe s=new Stateframe(user);
				//text.setText(user);
				//System.exit(0);
				dispose();
				}
			}
			
		});
		text.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				button1.doClick();
				
			}
			
		});
		//button2.setBorderPainted(false);
		button2.setBackground(Color.orange);
		button2.setBounds(95, 160, 80, 25);
		button2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				text.setText(null);
			}
			
		});
		JLabel label=new JLabel(image); 
		label.add(lab);
		label.add(text);
		label.add(chbox);
		label.add(button);
		label.add(button1); 
		label.add(button2);
		  setUndecorated(true);
		  setContentPane(label);
		  setVisible(true); 
		  setBounds(width/2-200,height/2-125,400,250);
		  validate();
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      this.addMouseListener(new MouseAdapter() {
	    	  public void mousePressed(MouseEvent e) {
	    	  isDraging = true;
	    	  xx = e.getX();
	    	  yy = e.getY();
	    	  }
	    	  public void mouseReleased(MouseEvent e) {
	    	  isDraging = false;
	    	  }
	    	  });
	      this.addMouseMotionListener(new MouseMotionAdapter() {
	    	  public void mouseDragged(MouseEvent e) {
	    	  if (isDraging) {
	    	  int left = getLocation().x;
	    	  int top = getLocation().y;
	    	  setLocation(left + e.getX() - xx, top + e.getY() - yy);
	    	  } 
	    	  }
	    	  });
	}
}




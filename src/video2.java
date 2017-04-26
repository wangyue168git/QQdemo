
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
 
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.media.control.BufferControl;
import javax.media.protocol.DataSource;
import javax.media.rtp.InvalidSessionAddressException;
import javax.media.rtp.RTPManager;
import javax.media.rtp.ReceiveStream;
import javax.media.rtp.ReceiveStreamListener;
import javax.media.rtp.SessionAddress;
import javax.media.rtp.event.ByeEvent;
import javax.media.rtp.event.NewReceiveStreamEvent;
import javax.media.rtp.event.ReceiveStreamEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
 
public class video2 extends JFrame implements ReceiveStreamListener,ControllerListener{
	SessionAddress local;
	SessionAddress target;
    
    public video2(SessionAddress target) throws UnknownHostException {
        this.local=new SessionAddress(InetAddress.getLocalHost(),60000);
        this.target=target;
    	this.setLayout(new BorderLayout());
    	this.setVisible(true);
        this.setBounds(860, 130, 400, 500);
  
        mgr = RTPManager.newInstance();
        mgr.addReceiveStreamListener(this);
        try {
            mgr.initialize(local);
            mgr.addTarget(target);
        } catch (InvalidSessionAddressException | IOException e) {
            e.printStackTrace();
        }
        BufferControl bc = (BufferControl)mgr.getControl("javax.media.control.BufferControl");
        if (bc != null)
            bc.setBufferLength(350);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnect();
            }
        });
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    @Override
    public void update(ReceiveStreamEvent e) {
        if(e instanceof NewReceiveStreamEvent) {
            ReceiveStream rs = ((NewReceiveStreamEvent)e).getReceiveStream();
            DataSource ds = rs.getDataSource();
            try {
                player = Manager.createPlayer(ds);
            } catch (NoPlayerException | IOException e1) {
                e1.printStackTrace();
            }
            player.addControllerListener(this);
            player.start();         
        } else if(e instanceof ByeEvent) {
            disconnect();
        }
    }
    protected void processWindowEvent(WindowEvent e){ 
		super.processWindowEvent(e); 
		if(e.getID()==WindowEvent.WINDOW_CLOSING){ 
			JOptionPane.showMessageDialog(this, " 请结束视屏聊天 ","视屏聊天",JOptionPane.WARNING_MESSAGE);
            disconnect();
		}
	}
    public void disconnect() {
        if(player!=null) {
            player.stop();
            player.close();
        }
        if(mgr!=null) {
            mgr.removeTargets("closing session");
            mgr.dispose();
            mgr = null;
        }       
    }
    @Override
    public void controllerUpdate(ControllerEvent e) {
    	
        if(e instanceof RealizeCompleteEvent) {
        	  Component comp;
            if((comp = player.getVisualComponent()) !=null)
                //add(player.getVisualComponent());
            	this.getContentPane().remove(comp);  
                 this.getContentPane().add(comp,BorderLayout.CENTER);
            if((comp = player.getControlPanelComponent()) != null)
                //add(player.getControlPanelComponent(),BorderLayout.SOUTH);
            	add(comp,BorderLayout.SOUTH);
            this.validate();
            pack();
        }
    }
    JPanel panel=new JPanel();
    Player player;
    RTPManager mgr;
}



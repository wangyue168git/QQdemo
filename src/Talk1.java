import java.io.*; 
import javax.sound.sampled.*; 
import java.net.*;
class Talk1 implements Runnable {

    TargetDataLine line; 
    Thread thread; 
    Socket s; 
    BufferedOutputStream captrueOutputStream;

    Talk1(Socket s){//������ ȡ��socket�Ի����������� 
      this.s=s; 
    }

    public void start() {

        thread = new Thread(this); 
        thread.setName("Capture"); 
        thread.start(); 
    }

    public void stop() { 
        thread = null; 
    }

    public void run() {

        try { 
          captrueOutputStream=new BufferedOutputStream(s.getOutputStream());//��������� �˴����Լ���ѹ��������ѹ������ 
        } 
        catch (IOException ex) { 
            return; 
        }

        AudioFormat format =new AudioFormat(44100,16,2,true,true);//AudioFormat(float sampleRate, int sampleSizeInBits, int channels, boolean signed, boolean bigEndian�� 
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
        try { 
            line = (TargetDataLine) AudioSystem.getLine(info); 
            line.open(format, line.getBufferSize()); 
        } catch (Exception ex) { 
            return; 
        }

        byte[] data = new byte[128];//�˴���1024����������е�����Ӧ�������1024Ӧ����һ�� 
        int numBytesRead=0; 
        line.start();

        while (thread != null) { 
            numBytesRead = line.read(data, 0,128);//ȡ���ݣ�1024���Ĵ�Сֱ�ӹ�ϵ��������ٶȣ�һ��ԽСԽ�죬 
            try { 
              captrueOutputStream.write(data, 0, numBytesRead);//д�������� 
            } 
            catch (Exception ex) { 
                break; 
            } 
        }
        line.stop(); 
        line.close(); 
        line = null;

       try { 
           captrueOutputStream.flush(); 
           captrueOutputStream.close(); 
        } catch (IOException ex) { 
        	return;
           // ex.printStackTrace(); 
        } 
    } 
  
}

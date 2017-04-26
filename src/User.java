
public class User {
	private String name; 
	private String IP;
	public User(String name, String ip) {
	this.name = name;IP = ip;
	} 
	public String getIP() {return IP;} 
	public void setIP(String ip) {IP = ip;} 
	public String getName() {return name;}  
	public void setName(String name) {this.name = name;} 
}


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

//class for our client
public class BasicChatClient extends TCPP{
	//we'll extend our TCPP class so we can use our protocol methods
	public static OutputStream out;
	public static InputStream in = null;
	public static Socket s = null;
	public static Gui g = null;
	public static String ip;
	public static void main(String args[]) throws UnknownHostException,
			IOException {
		// show a dialog to get server ip from the user
		ip = (String) JOptionPane
				.showInputDialog("Input the IP of the server you wish to connect to : ");
		// bind our listening socket to the server ip at port 2020

		try {
			// try to connect to the server
			s = new Socket(InetAddress.getByName(ip), 2020);
			out = (s.getOutputStream());
			g = new Gui();
			in = s.getInputStream();
		} catch (IOException e) {
			// tell the user we couldn't connect
			JOptionPane.showInputDialog("Error connecting to server ");
		}
		//tell the server our hostname for our username
		String message = "/USER=" + InetAddress.getLocalHost().getHostName();
		byte[] output = getMessagePacket(message);
		out.write(output);
		//write a hellow world to the server
		message = "Hello World! ";
		out.write(getMessagePacket(message));
		while(true){
		try {
			//wait for the server to send messages
			while (true) {
				String mess = "";
				char currChar = ' ';
				//read until end character
				while ((currChar = (char) in.read()) != '~') {
					if (currChar == 65535) {
						Thread.sleep(30);
						continue;
					}
					mess += currChar;
				}
				//print message to the chat window
				g.chatWindow.setText(g.chatWindow.getText() + "\n" + mess);
				System.out.println("MESSAGE END " + mess);
				//sleep so we don't melt the cpu
				Thread.sleep(30);
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION CAUGHT CLOSING SOCKET ");
			e.printStackTrace();
		}
		}
	}
	public static void recoverConnection(){
	
		try {
			// try to connect to the server
			System.out.println("attempint recovery");
			s.close();
			s = new Socket(InetAddress.getByName(ip), 2020);
			out = (s.getOutputStream());
			in = s.getInputStream();
		} catch (IOException e) {
			System.out.println("Connectin could not be recovered");
			JOptionPane.showInputDialog("Error connecting to server ");
			System.exit(1);
		}
	}
}

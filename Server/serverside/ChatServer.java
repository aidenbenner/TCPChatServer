package serverside;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import Gui.MainWindow;
public class ChatServer extends TCPP{	
	//create an array list of clients
	//we'll use an arraylist over an array because we do not have a definite size of our data
	//structure
	public static ArrayList<ClientServerThread> clients = new ArrayList<ClientServerThread>();
	//declare the main gui
	public static MainWindow mw;
	//we will throw IOEception and Interuppteed exception
	public static void main(String args[]) throws IOException, InterruptedException{
		//create the server socket and bind it to the local address on port 2020
		ServerSocket s = new ServerSocket(2020,2020,InetAddress.getByName(getIpAddress()));
		//initialize the main gui
		mw = new MainWindow();
		//print to gui that the server is running  at  the local IP
		mw.printToWindow("SERVER RUNNING AT " + getIpAddress());
		while(true){
			//main loop waits for a connection on the ServerSocket then accepts it
			//and creates a new client server thread
			ClientServerThread c = new ClientServerThread(s.accept());
			//adds the thread to our client list
			clients.add(c);
			(new Thread(c)).start();
			//start the clientserverthread
			//sleep so we don't melt the CPU 
			Thread.sleep(200);
		}
	}
	
	public static String getIpAddress() { 
		//gets the IP address from the current machine
        try {
        	//gets network interfaces 
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
            	//take each network interface
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    //makes sure that ip is not loopback and is the actual local ip 
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        String ipAddress=inetAddress.getHostAddress().toString();
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        	//this shouldn't happen
        	System.out.println("UNEXPECTED ERROR GETTING IP");
        }
        //if all else fails 
        return null; 
	}
	//we'll throw these exceptions because they should be handled higher up
	public static void ServerOut(String user, String message) throws UnsupportedEncodingException, IOException{
		//get the iterator from our arraylist
		Iterator<ClientServerThread>  iter = clients.iterator();
		//array list to store user names since we don't have a definte size
		ArrayList<String> users = new ArrayList<String>();
		//loop through our clientserver threads
		while(iter.hasNext()){
			try{
				//for each CST send the user preppeneded to the message
				ClientServerThread currentCST = iter.next();
				System.out.println("updating with user name " + currentCST.user);
				currentCST.broadcast(user + ": " + message);
				users.add(currentCST.user);
			}
			catch(IOException e){
				//if an IOException has occured there has been a connection issue
				//CST should terminate the threaa
				continue;
			}
		}
		//send the username updates to the GUI
		mw.updateUsers(users);
		//print the message to the server GUI
		mw.printToWindow(user + ": " + message);
	}
}
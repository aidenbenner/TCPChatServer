package serverside;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ClientServerThread implements Runnable {
	//we'll have to multithread each client so we'll implement the runnable class to create
	//more than one thread
	public Socket clientSock;
	public String user;
	//constructor for the client server threads
	ClientServerThread(Socket client) {
		clientSock = client;
	}
	//broadcast the given message to the client
	public void broadcast(String message) throws UnsupportedEncodingException,
			IOException {
		OutputStream out = clientSock.getOutputStream();
		byte[] outBytes = TCPP.getMessagePacket(message);
		out.write(outBytes);
		System.out.println(new String(outBytes, "UTF-8"));

	}
	//end the thread
	public void cancel(){
		Thread.currentThread().interrupt();
	}
	
	@Override
	public void run() {
		//this is the code that will run periodically when we create a new client-server thread
		InputStream in = null;
		try {
			// handshake
			in = clientSock.getInputStream();
			String message = "";
			user = clientSock.getInetAddress().getHostName();
			while (true) {
				String mess = "";
				char currChar = ' ';
				//this reads from the input stream and waits until we send our end character ~
				while((currChar = (char) in.read()) != '~'){
					if (currChar == 65535) {
						Thread.sleep(30);
						continue;
					}
					mess += currChar;
				}
				//this is for the initial handshake 
				if(mess.equals("/k")){
					broadcast("y");
				}
				if(mess.contains("/USER=")){
					user = mess.substring(mess.indexOf('=')+1, mess.length());
				}
				if(mess.charAt(0) != '/'){
					if(mess.length() < 2){
						mess = " " + mess;
					}
					ChatServer.ServerOut(user, mess);
				}
				//give it a rest so we don't melt the CPU
				Thread.sleep(20);
			}
			//if anything goes wrong (i.e client disconnects) we will want to deal with it
		} catch (Exception e) {
			//print the error, probable an IOException, to the console
			//this is for debugging only
			System.out.println(e.getMessage());
			//make sure we close the streams
			//first we need to check if the stream has been created or else we will 
			//throw a null reference exception
			if(in!=null){
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			//if we couldn't close the stream just close the socket
			try{
				clientSock.close();
			}catch (IOException e1) {
				//if that doesn't work the socket should already be close but we'll print out everything anyway
				e1.printStackTrace();
			}
			
		}
	}

}
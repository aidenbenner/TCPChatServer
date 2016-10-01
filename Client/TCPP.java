
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class TCPP {
	//This is our Transmission control protocol - protocol
	//the idea is pretty primitive, the server/client will listen until a ~ is sent
	//then the info can be parsed.
	public byte[] output;
	public static byte[] getMessagePacket(String mess) throws UnsupportedEncodingException{
		//this message converts the string to a byte array and appends a ~
		byte[] output = new byte[mess.length()+1]; 
		for(int i = 0; i<mess.length(); i++){
			output[i] = (byte)mess.charAt(i);
		}
		output[mess.length()] = '~';
		return output;
	}
	
	
	
	public static byte[][] getMultiMessage(String[] mess) throws UnsupportedEncodingException{
		byte[][] multiOut = new byte[mess.length][];
		//get and return multiple strings to a 2d byte array
		//we'll need to store a 2d array here because each message is a byte array and we need more than one seperated.
		int i = 0;
		for(String s : mess){
			multiOut[i] = getMessagePacket(s);
			i++;
		}
		return multiOut;
	}
}
 
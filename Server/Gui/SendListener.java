package Gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextPane;

import serverside.ChatServer;

//We'll use the action listener interface so we can 
//get a snippet of code to run when the user presses the enter key
public class SendListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		//this method will wait until the enter key is pressed, then send out 
		//the current text in the send box to the clients
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextPane jtp = (JTextPane) e.getSource();
			try {
				ChatServer.ServerOut("SERVER", jtp.getText());
			} catch (UnsupportedEncodingException e1) {
				// if the server throws an exception error
				e1.printStackTrace();
			} catch (IOException e1) {
				// if the server throws an IOError we'll print it for debug but it should 
				// already be handled higher up.
				e1.printStackTrace();
			}
			jtp.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//this just sets the text in the send box to be blank when the key is release
		//if this isn't here the sendbox will start with a line break
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextPane jtp = (JTextPane) e.getSource();
			jtp.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//don't need anything here
	}

}

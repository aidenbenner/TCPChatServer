package Gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import serverside.ChatServer;

//main gui for the server side window, this is what the user running the server will see
public class MainWindow {
	//initialize all our components for later
	public JTextPane chatWindow = new JTextPane();
	public JButton btnSend = new JButton("Send");
	public JTextPane sendBox = new JTextPane();
	private final JTextPane userTextPane = new JTextPane();
	public JScrollPane scrollPane = new JScrollPane(chatWindow);
	public JScrollPane sendPane = new JScrollPane(sendBox);
	public JScrollPane userPane = new JScrollPane(userTextPane);
	public MainWindow() {
		//create our actual window and set our default parameters
		JFrame jf = new JFrame();
		jf.setSize(800, 500);
		jf.setVisible(true);
		//make sure the program ends when the user closes the window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setResizable(false);
		chatWindow.setEditable(false);
		scrollPane.setBounds(12,0,593,346);//chatWindow.setBounds(12, 0, 593, 346);
		jf.getContentPane().add(scrollPane);
		//add a simple click listener
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//implementing the action listener 
				try {
					ChatServer.ServerOut("SERVER : ", sendBox.getText());
					sendBox.setText("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		//set more bounds and locations for windows
		btnSend.setBounds(488, 358, 117, 70);
		jf.getContentPane().add(btnSend);

		sendPane.setBounds(12, 358, 464, 102);
		jf.getContentPane().add(sendPane);
		userPane.setBounds(617, 0, 171, 460);
		
		jf.getContentPane().add(userPane);
		sendBox.addKeyListener(new SendListener());
		userTextPane.setText("USERS ONLINE ");
		userTextPane.setEditable(false);
	}
	
	public static ArrayList<String> sortUsers(ArrayList<String> usernames){
		//method for sorting usernames, uses a form of insertion sort
		int wall = 0;
		int indexofmin = 0;
		while(wall<usernames.size()){
			//find min and swap it before the wall, continue until sorted
			for(int i = wall; i<usernames.size(); i++){
				//System.out.println("Comparing " + usernames.get(indexofmin) + " with " + usernames.get(i));
				if(usernames.get(indexofmin)
						.compareTo(usernames.get(i))>0){
					//System.out.println("Swapping " + usernames.get(indexofmin) + " with " + usernames.get(i));
					indexofmin = i;
				}
				//System.out.println("LOWEST PAST WALL IS " + usernames.get(indexofmin));
			}
			//System.out.println("BEFORE " + usernames.toString());
			String hold = usernames.get(wall);
			usernames.set(wall, usernames.get(indexofmin));
			usernames.set(indexofmin, hold);
			//System.out.println(usernames.toString());
			wall++;
			indexofmin = wall;
		}
		return usernames;
	}
	
	
	//method prints the names of all users online to the user box
	public void updateUsers(ArrayList<String> usernames){
		userTextPane.setText("USERS:");
		//sort the usernames first 
		usernames = sortUsers(usernames);
		//print the usernames
		for(String name : usernames){
			userTextPane.setText(userTextPane.getText() + "\n" + name);
		}
	}
	//method takes in the inputted message and prints it to the chat window
	public void printToWindow(String message) {
		chatWindow.setText(chatWindow.getText() + "\n" + message);
		chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
	}

}

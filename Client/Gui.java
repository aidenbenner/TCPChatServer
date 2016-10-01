
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Gui {
	//this is the gui for the client application
	public JTextPane chatWindow = new JTextPane();
	public JButton btnSend = new JButton("Send");
	public JTextPane sendBox = new JTextPane();
	private final JTextPane userTextPane = new JTextPane();
	public JScrollPane scrollPane = new JScrollPane(chatWindow);
	public JScrollPane sendPane = new JScrollPane(sendBox);
	public JScrollPane userPane = new JScrollPane(userTextPane);
	public Gui() {
		//main jfram component
		JFrame jf = new JFrame();
		jf.setSize(800, 500);
		jf.setVisible(true);
		//make sure the program closes when we exit
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setResizable(false);
		chatWindow.setEditable(false);
		scrollPane.setBounds(12,0,593,346);//chatWindow.setBounds(12, 0, 593, 346);
		jf.getContentPane().add(scrollPane);
		//action listener
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					
					BasicChatClient.out.write(TCPP.getMessagePacket(sendBox.getText()));
					sendBox.setText("");

					chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnSend.setBounds(488, 358, 117, 70);
		jf.getContentPane().add(btnSend);

		sendPane.setBounds(12, 358, 464, 102);
		jf.getContentPane().add(sendPane);
		userPane.setBounds(617, 0, 171, 460);
		
		sendBox.addKeyListener(new SendListener());

	}
	public void updateUsers(ArrayList<String> usernames){
		//the gui for the user pane if implemented
		userTextPane.setText("USERS:");
		for(String name : usernames){
			userTextPane.setText(userTextPane.getText() + "\n" + name);
		}
	}
	public void printToWindow(String message) {
		//method to print a message to the chat window
		chatWindow.setText(chatWindow.getText() + "\n" + message);
		chatWindow.setCaretPosition(chatWindow.getDocument().getLength());
	}

}

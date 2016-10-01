

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JTextPane;


public class SendListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			JTextPane jtp = (JTextPane) e.getSource();
			try {
				if(jtp.getText().length() >= 1){
				BasicChatClient.out.write(TCPP.getMessagePacket(jtp.getText()));
				BasicChatClient.g.chatWindow.setCaretPosition(BasicChatClient.g.chatWindow.getDocument().getLength());
				}
			} catch (UnsupportedEncodingException e1) {
				BasicChatClient.recoverConnection();
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				BasicChatClient.recoverConnection();
				e1.printStackTrace();
			}
			jtp.setText("");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			BasicChatClient.g.chatWindow.setCaretPosition(BasicChatClient.g.chatWindow.getDocument().getLength());
			JTextPane jtp = (JTextPane) e.getSource();
			jtp.setText("");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			JTextPane jtp = (JTextPane) e.getSource();
			jtp.setText("");
		}
	}

}

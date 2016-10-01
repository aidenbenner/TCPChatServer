package Gui;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

public class MainWindowTest {

	@Test
	public void test() {
		ArrayList<String> users = new ArrayList<String>();
		users.add("c");
		users.add("a");
		users.add("d");
		users.add("j");
		users.add("k");
		users.add("c");
		System.out.println(MainWindow.sortUsers(users).toString());;
	}
}

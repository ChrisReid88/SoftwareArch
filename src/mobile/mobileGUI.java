package mobile;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MobileGUI {
	// First set up the panel with the labels and text boxes
	private JPanel inputPanel = new JPanel();
	private JLabel regNumberLabel = new JLabel("Nhs Registration No.");
	private JTextField regNumberTxt = new JTextField(10);

	private JLabel firstnameLabel = new JLabel("First name");
	private JTextField firstnameTxt = new JTextField(10);

	private JLabel lastnameLabel = new JLabel("Last name");
	private JTextField lastnameTxt = new JTextField(10);

	private JLabel timeLabel = new JLabel("Time");
	private JTextField timeTxt = new JTextField(10);

	private JLabel locationLabel = new JLabel("Location");
	private JTextField locationTxt = new JTextField(10);

	private JLabel reasonLabel = new JLabel("Reason");
	private JTextField reasonTxt = new JTextField(10);

	private JLabel actionLabel = new JLabel("Action Taken");
	private JTextField actionTxt = new JTextField(10);
	private String data;
	private String callData;
	String[] lines = null;

	{
		// Initialise the panel
		inputPanel.setLayout(new GridLayout(7, 1));
		inputPanel.add(regNumberLabel);
		inputPanel.add(regNumberTxt);

		inputPanel.add(firstnameLabel);
		inputPanel.add(firstnameTxt);

		inputPanel.add(lastnameLabel);
		inputPanel.add(lastnameTxt);

		inputPanel.add(timeLabel);
		inputPanel.add(timeTxt);

		inputPanel.add(locationLabel);
		inputPanel.add(locationTxt);

		inputPanel.add(reasonLabel);
		inputPanel.add(reasonTxt);

		inputPanel.add(actionLabel);
		inputPanel.add(actionTxt);
	}

	// Next the panel with the buttons
	private JPanel buttonPanel = new JPanel();
	private JButton sendButton = new JButton("Send");
	private JButton clearButton = new JButton("Clear Fields");
	private JButton exitButton = new JButton("Exit");
	
	{
		// Initialise the panel
		buttonPanel.setLayout(new GridLayout(3, 1));
		buttonPanel.add(sendButton);
	}

	// Now create a panel with the input and button panels in. This is the top panel
	private JPanel topPanel = new JPanel();
	{
		topPanel.setLayout(new FlowLayout());
		topPanel.add(inputPanel);
		topPanel.add(buttonPanel);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
	}

	// Create the panel which will display the feedback text
	private JPanel feedbackPanel = new JPanel();
	private JTextArea feedbackArea = new JTextArea(5, 30);
	{
		feedbackArea.setEditable(false);
		feedbackPanel.setLayout(new GridLayout(1, 1));
		feedbackPanel.add(feedbackArea);
	}

	// Finally create the window to display the panels
	private JFrame window = new JFrame("Ambulance Mobile");
	{
		window.setLayout(new GridLayout(2, 1));
		window.add(topPanel);
		window.add(feedbackPanel);
		window.pack();
	}

	public MobileGUI() {
		// Add your custom action listeners here
		sendButton.addActionListener(new SendButtonListener());
		clearButton.addActionListener(new ClearButtonListener());
		exitButton.addActionListener(new ExitButtonListener());

		// The default close action
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		receiveCallOut();
	}

	// Create a server and format the string recieved from client
	private void receiveCallOut() {
		try {

			int serverPort = 6000;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				ConnectionMob c = new ConnectionMob(clientSocket);
				data = c.getData();
				lines = data.split("\\s*\\r?\\n\\s*");
				feedbackArea.setText("Emergency call out details!" + "\nNHS Registration No.:    " + lines[0]
						+ "\nFirst name:                        " + lines[1] + "\nLast name:                        "
						+ lines[2] + "\nAddress:                            " + lines[3]
						+ "\nCondition:                          " + lines[4]);
				c.sendData("Mobile Data Sent " + callData);
				regNumberTxt.setText(lines[0]);
			}
		} catch (IOException e) {
			System.out.println("Listen: " + e.getMessage());
		}
	}

	// The action listener on the Add button.
	private class SendButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			String fname = firstnameTxt.getText();
			String lname = lastnameTxt.getText();
			String time = timeTxt.getText();
			String location = locationTxt.getText();
			String reason = reasonTxt.getText();
			String action = actionTxt.getText();

			String calloutDetails = (fname + ", " + lname + ", " + time + ", " + location + ", " + reason + "," + action
					+ "," + lines[0]);

			// Clinet to send the callout details to the reg office.
			Socket s2 = null;
			try {
				int serverPort = 6001;
				s2 = new Socket("localhost", serverPort);
				DataOutputStream out = new DataOutputStream(s2.getOutputStream());
				out.writeUTF(calloutDetails); // UTF is a string encoding format
				s2.close();
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
	}
	private class ClearButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			//clear textboxes
			firstnameTxt.setText("");
			lastnameTxt.setText("");
			regNumberTxt.setText("");
			locationTxt.setText("");
			timeTxt.setText("");
			reasonTxt.setText("");
			actionTxt.setText("");
		}
	}
	private class ExitButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			//Close the program
			System.exit(0);

		}
	}

}

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
	private JLabel FirstnameLabel = new JLabel("First name");
	private JTextField FirstnameTxt = new JTextField(10);
	
	private JLabel LastnameLabel = new JLabel("Last name");
	private JTextField LastnameTxt = new JTextField(10);
	
	private JLabel TimeLabel = new JLabel("Time");
	private JTextField TimeTxt = new JTextField(10);
	
	private JLabel LocationLabel = new JLabel("Location");
	private JTextField LocationTxt = new JTextField(10);
	
	private JLabel ReasonLabel = new JLabel("Reason");
	private JTextField ReasonTxt = new JTextField(10);
	
	private JLabel ActionLabel = new JLabel("Action Taken");
	private JTextField ActionTxt = new JTextField(10);
	private String data;
	private String callData;

	{
		// Initialise the panel
		inputPanel.setLayout(new GridLayout(6, 1));
		inputPanel.add(FirstnameLabel);
		inputPanel.add(FirstnameTxt);
		
		inputPanel.add(LastnameLabel);
		inputPanel.add(LastnameTxt);
		
		inputPanel.add(TimeLabel);
		inputPanel.add(TimeTxt);
		
		inputPanel.add(LocationLabel);
		inputPanel.add(LocationTxt);
		
		inputPanel.add(ReasonLabel);
		inputPanel.add(ReasonTxt);
		
		inputPanel.add(ActionLabel);
		inputPanel.add(ActionTxt);
	}

	// Next the panel with the buttons
	private JPanel buttonPanel = new JPanel();
	private JButton sendButton = new JButton("Send");

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

		// The default close action
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		receiveCallOut();
	}

	private void receiveCallOut() {
		try {
			String[] lines = null;
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
				c.sendData("Mobile Data Sent " + callData );
				
			}
		} catch (IOException e) {
			System.out.println("Listen: " + e.getMessage());
		}
	}

	// The action listener on the Add button.
	private class SendButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			String fname = FirstnameTxt.getText();
			String lname = LastnameTxt.getText();
			String time = TimeTxt.getText();
			String location = LocationTxt.getText();
			String reason = ReasonTxt.getText();
			String action = ActionTxt.getText();
			
			String calloutDetails = (fname + ", " + lname + ", " + time + ", " + location + ", " + reason + "," + action );
			
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

}

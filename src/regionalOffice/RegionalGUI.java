package regionalOffice;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
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


public class RegionalGUI {
	
	// First set up the panel with the labels and text boxes
	private JPanel inputPanel = new JPanel();
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
	private String result;
	String[] callOutDetails = null;
	{
		// Initialise the panel
		inputPanel.setLayout(new GridLayout(6, 1));
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
	private JButton addButton = new JButton("Enter");
	private JButton sendButton = new JButton("Send");
	private JButton clearButton = new JButton("Clear Fields");
	private JButton exitButton = new JButton("Exit");
	{
		// Initialise the panel
		buttonPanel.setLayout(new GridLayout(4, 1));
		buttonPanel.add(addButton);
		buttonPanel.add(sendButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);
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
	private JFrame window = new JFrame("Regional Hospital");
	{
		window.setLayout(new GridLayout(2, 1));
		window.add(topPanel);
		window.add(feedbackPanel);
		window.pack();
	}

	// The application layer that the GUI layer will talk to
	private RegAppLayerInterface appLayer;

	/**
	 * Default constructor. Requires only the application layer to connect to
	 * 
	 * @param appLayer The application layer that the GUI is connected to
	 */
	public RegionalGUI(RegAppLayerInterface appLayer) {
		this.appLayer = appLayer;
		
		
		// Add your custom action listeners here
		addButton.addActionListener(new AddButtonListener());
		sendButton.addActionListener(new SendButtonListener());
		exitButton.addActionListener(new ExitButtonListener());
		clearButton.addActionListener(new ClearButtonListener());

		// The default close action
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		receiveCallDetails();
		
		
	}
	private class ClearButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			//clear textboxes
			firstnameTxt.setText("");
			lastnameTxt.setText("");
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
	public void receiveCallDetails() {
		String[] lines = null;
		
		try {
			int serverPort = 7896;
			int serverPort2 = 6001;
			ServerSocket listenSocket = new ServerSocket(serverPort);
			ServerSocket listenSocket2 = new ServerSocket(serverPort2);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
				System.out.println("Thread:" + c.getName());
				data = c.getData();
				result = appLayer.getPatient(data);
				lines = result.split("\\s*\\r?\\n\\s*");
				
				feedbackArea.setText("RECEIVED FROM HEAD OFFICE!" + 
									"\nNHS Registration No.:    " +lines[0] +
									"\nFirst name:                        " + lines[1] + 
									"\nLast name:                        " + lines[2] +
									"\nAddress:                            " + lines[3] +
									"\nCondition:                          " + lines[4]);
				
				Socket clientSocket2 = listenSocket2.accept();
				Connection c2 = new Connection(clientSocket2);
				System.out.println("Thread:" + c2.getName());
			
				
				String cOut = c2.getData();
				callOutDetails = cOut.split(",");
				
				System.out.println(cOut);
				firstnameTxt.setText(callOutDetails[0]);
				lastnameTxt.setText(callOutDetails[1]);
				timeTxt.setText(callOutDetails[2]);
				locationTxt.setText(callOutDetails[3]);
				reasonTxt.setText(callOutDetails[4]);
				actionTxt.setText(callOutDetails[5]);
				System.out.println(callOutDetails);
			}
		} 
		catch(IOException e) {
			System.out.println("Listen: " + e.getMessage());
		}	
		
	}
	
	
	public void sendToAmbulance() {
		//client to send result to ambulance
		Socket s = null;
		try {
			int serverPort = 6000;
			s = new Socket("localhost", serverPort);
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out = new DataOutputStream( s.getOutputStream());
			System.out.println(result);
			out.writeUTF(result); // UTF is a string encoding format
			s.close();
		} 
		catch (Exception e){
			System.out.println("Error:"+e.getMessage());
		}
	}
	// The action listener on the Add button.
	private class AddButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Get the required values from the text fields
			String firstname = firstnameTxt.getText();
			String lastname = lastnameTxt.getText();
			String time = timeTxt.getText();
			String location = locationTxt.getText();
			String reason = reasonTxt.getText();
			String action = actionTxt.getText();
			
			String result = appLayer.addCall(firstname, lastname,time,location,reason, action, callOutDetails[6]);
			//System.out.println(data+" DONE");
			// Set the text in the feedback area to the result
			feedbackArea.setText(result);
		}
	}
	private class SendButtonListener implements ActionListener {
		// Called when the Add button is clicked
		public void actionPerformed(ActionEvent arg0) {
			// Get the required values from the text fields
	
			sendToAmbulance();
		
		}
	}
}
